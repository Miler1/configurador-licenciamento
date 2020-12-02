package com.configuradorlicenciamento.pergunta.services;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.RelAtividadePergunta;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.atividade.repositories.RelAtividadePerguntaRepository;
import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaCsv;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.interfaces.IPerguntaService;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.pergunta.repositories.PerguntaRepository;
import com.configuradorlicenciamento.pergunta.specifications.PerguntaSpecification;
import com.configuradorlicenciamento.resposta.dtos.RespostaDTO;
import com.configuradorlicenciamento.resposta.interfaces.IRespostaService;
import com.configuradorlicenciamento.resposta.models.Resposta;
import com.configuradorlicenciamento.resposta.repositories.RespostaRepository;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PerguntaService implements IPerguntaService {

    private static final String VINCULO_ATIVIDADE_DISPENSAVEL = "Erro! Não foi possível desativar/ativar a pergunta. Ela se encontra vinculada a uma atividade dispensável ativa no sistema.";

    @Autowired
    PerguntaRepository perguntaRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    RelAtividadePerguntaRepository relAtividadePerguntaRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Autowired
    IRespostaService respostaService;

    @Override
    public Pergunta salvar(HttpServletRequest request, PerguntaDTO perguntaDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        tratarPergunta(perguntaDTO);

        Specification<Pergunta> specification = Specification.where(PerguntaSpecification.padrao());
        specification = specification.and(PerguntaSpecification.matchTitulo(perguntaDTO.getTexto()));

        if (!perguntaRepository.findAll(specification).isEmpty()) {
            throw new ConflictException("Já existe uma pergunta com o mesmo título.");
        }

        Pergunta pergunta = new Pergunta.PerguntaBuilder(perguntaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        pergunta.setRespostas(getRespostas(request, perguntaDTO.getRespostas()));

        perguntaRepository.save(pergunta);

        return pergunta;

    }

    @Override
    public Pergunta editar(HttpServletRequest request, PerguntaDTO perguntaDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<Pergunta> perguntaSalva = perguntaRepository.findById(perguntaDTO.getId())
                .map(pergunta -> {

                    tratarPergunta(perguntaDTO);

                    Specification<Pergunta> specification = Specification.where(PerguntaSpecification.padrao());
                    specification = specification.and(PerguntaSpecification.matchTitulo(perguntaDTO.getTexto()));

                    if (!perguntaRepository.findAll(specification).isEmpty() && !pergunta.getTexto().equals(perguntaDTO.getTexto())) {
                        throw new ConflictException("Já existe uma pergunta com o mesmo título.");
                    }

                    pergunta.setTexto(perguntaDTO.getTexto());
                    pergunta.setCodigo(perguntaDTO.getCodigo());
                    pergunta.setTipoPergunta(perguntaDTO.getTipoPergunta());
                    pergunta.setDataCadastro(new Date());
                    pergunta.setAtivo(perguntaDTO.getAtivo());
                    pergunta.setRespostas(this.getRespostas(request, perguntaDTO.getRespostas()));
                    pergunta.setUsuarioLicenciamento(usuarioLicenciamento);
                    pergunta.setTipoLocalizacaoEmpreendimento(perguntaDTO.getTipoLocalizacaoEmpreendimento());
                    return pergunta;
                });

        perguntaRepository.save(perguntaSalva.get());

        return perguntaSalva.get();

    }

    @Override
    public Pergunta ativarDesativar(HttpServletRequest request, Integer idPergunta) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Pergunta perguntaExistente = perguntaRepository.findById(idPergunta).get();

        List<RelAtividadePergunta> relAtividadePerguntaList = relAtividadePerguntaRepository.findByPergunta(perguntaExistente);

        relAtividadePerguntaList.forEach(relAtividadePergunta -> {

            Atividade atividade = atividadeRepository.findById(relAtividadePergunta.getAtividade().getId()).get();

            boolean ativo = atividade.getAtivo();

            String codigo = atividade.getCodigo();

            if (ativo && codigo.equals("0000")) {
                throw new ConflictException(VINCULO_ATIVIDADE_DISPENSAVEL);
            }

        });

        Optional<Pergunta> perguntaSalva = perguntaRepository.findById(idPergunta)
                .map(pergunta -> {
                    pergunta.setAtivo(!perguntaExistente.getAtivo());
                    pergunta.setUsuarioLicenciamento(usuarioLicenciamento);
                    pergunta.setDataCadastro(new Date());
                    return pergunta;
                });

        perguntaRepository.save(perguntaSalva.get());

        return perguntaSalva.get();

    }

    public void tratarPergunta(PerguntaDTO pergunta) {

        String textoPergunta = StringUtil.tratarEspacos(pergunta.getTexto());

        if (!textoPergunta.endsWith("?")) {
            textoPergunta = textoPergunta + "?";
        }

        pergunta.setTexto(textoPergunta);

    }

    public List<Pergunta> listarPerguntas() {
        return perguntaRepository.findByAtivo(true, Sort.by("texto"));
    }

    public List<PerguntaCsv> listarPerguntaParaCsv() {

        List<Pergunta> perguntas = listarPerguntas();
        List<PerguntaCsv> dtos = new ArrayList<>();

        for (Pergunta pergunta : perguntas) {
            dtos.add(pergunta.preparaParaCsv());
        }

        return dtos;

    }

    public Page<Pergunta> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Pergunta> specification = preparaFiltro(filtro);

        return perguntaRepository.findAll(specification, pageable);

    }

    @Override
    public List<Resposta> getRespostas(HttpServletRequest request, List<RespostaDTO> respostas) {

        Object login = request.getSession().getAttribute("login");
        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        if (respostas == null) {
            respostas = new ArrayList<>();
        }

        List<Resposta> novasRespostas = new ArrayList<>();

        for (RespostaDTO resposta : respostas) {

            if (resposta.getId() != null) {
                Resposta respostaSalva = respostaService.buscaResposta(resposta.getId());
                respostaSalva.setTexto(resposta.getTexto());
                respostaSalva.setPermiteLicenciamento(resposta.getPermiteLicenciamento());
                respostaSalva.setUsuarioLicenciamento(usuarioLicenciamento);
                respostaSalva.setDataCadastro(new Date());
                respostaRepository.save(respostaSalva);
                novasRespostas.add(respostaSalva);

            } else {
                Resposta entidade = new Resposta.RespostaBuilder(resposta)
                        .setDataCadastro(new Date())
                        .setUsuarioLicencimento(usuarioLicenciamento)
                        .build();
                novasRespostas.add(entidade);
            }

        }

        return novasRespostas;
    }

    private Specification<Pergunta> preparaFiltro(FiltroPesquisa filtro) {

        Specification<Pergunta> specification = Specification.where(PerguntaSpecification.padrao());

        if (filtro.getStringPesquisa() != null && !filtro.getStringPesquisa().isEmpty()) {
            specification = specification.and(PerguntaSpecification.titulo(filtro.getStringPesquisa()));
        }

        return specification;

    }

}