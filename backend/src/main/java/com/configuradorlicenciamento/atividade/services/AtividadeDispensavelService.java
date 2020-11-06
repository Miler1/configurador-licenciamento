package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.atividade.interfaces.IAtividadeDispensavelService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.TipoAtividade;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.atividade.repositories.TipoAtividadeRepository;
import com.configuradorlicenciamento.atividade.specifications.AtividadeSpecification;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.pergunta.repositories.PerguntaRepository;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.potencialPoluidor.repositories.PotencialPoluidorRepository;
import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelDTO;
import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelEdicaoDTO;
import com.configuradorlicenciamento.atividade.interfaces.IRelAtividadePerguntaService;
import com.configuradorlicenciamento.atividade.models.RelAtividadePergunta;
import com.configuradorlicenciamento.atividade.repositories.RelAtividadePerguntaRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories.TipoCaracterizacaoAtividadeRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.services.TipoCaracterizacaoAtividadeService;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.tipologia.repositories.TipologiaRepository;
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
public class AtividadeDispensavelService implements IAtividadeDispensavelService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Autowired
    AtividadeCnaeRepository atividadeCnaeRepository;

    @Autowired
    TipologiaRepository tipologiaRepository;

    @Autowired
    RelAtividadePerguntaRepository relAtividadePerguntaRepository;

    @Autowired
    PotencialPoluidorRepository potencialPoluidorRepository;

    @Autowired
    TipoAtividadeRepository tipoAtividadeRepository;

    @Autowired
    PerguntaRepository perguntaRepository;

    @Autowired
    IRelAtividadePerguntaService relAtividadePerguntaService;

    @Autowired
    TipoCaracterizacaoAtividadeService tipoCaracterizacaoAtividadeService;

    @Override
    public List<Atividade> salvarAtividadeDispensavel(HttpServletRequest request, AtividadeDispensavelDTO atividadeDispensavelDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        List<Atividade> atividades = new ArrayList<>();

        atividadeDispensavelDTO.getCnaesTipologia().forEach(cnaeTipologia -> {

            Optional<AtividadeCnae> atividadeCnae = atividadeCnaeRepository.findById(cnaeTipologia.getCnae().getId());

            Optional<Tipologia> tipologia = tipologiaRepository.findById(cnaeTipologia.getTipologia().getId());

            PotencialPoluidor potencialPoluidor = potencialPoluidorRepository.findByCodigo("I");

            List<TipoAtividade> tiposAtividades = tipoAtividadeRepository.findAll();

            Atividade atividade = new Atividade.AtividadeBuilder(!cnaeTipologia.getForaMunicipio())
                    .setNome(atividadeCnae.get().getNome())
                    .setCodigo("0000")
                    .setTipologia(tipologia.get())
                    .setGeoLinha(true)
                    .setGeoPonto(true)
                    .setGeoPoligono(true)
                    .setPotencialPoluidor(potencialPoluidor)
                    .setAtivo(true)
                    .setDentroEmpreendimento(false)
                    .setV1(false)
                    .setRascunho(false)
                    .setTiposAtividades(tiposAtividades)
                    .setDataCadastro(new Date())
                    .setUsuarioLicenciamento(usuarioLicenciamento)
                    .build();

            atividadeRepository.save(atividade);

            tipoCaracterizacaoAtividadeService.salvarAtividadeDispensavel(cnaeTipologia.getCnae(), atividade);

            relAtividadePerguntaService.salvar(atividade, atividadeDispensavelDTO.getPerguntas());

            atividades.add(atividade);

        });

        return atividades;
    }

    @Override
    public Atividade editarAtividadeDispensavel(HttpServletRequest request, AtividadeDispensavelDTO atividadeDispensavelDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        AtividadeDispensavelDTO.RelacaoCnaeTipologia cnaeTipologia = atividadeDispensavelDTO.getCnaesTipologia().get(0);

        Atividade atividade = atividadeRepository.findById(atividadeDispensavelDTO.getId()).orElse(null);

        Optional<AtividadeCnae> atividadeCnae = atividadeCnaeRepository.findById(cnaeTipologia.getCnae().getId());

        Optional<Tipologia> tipologia = tipologiaRepository.findById(cnaeTipologia.getTipologia().getId());

        atividadeCnae.ifPresent(cnae -> {
            assert atividade != null;
            atividade.setNome(cnae.getNome());
        });
        assert atividade != null;
        tipologia.ifPresent(atividade::setTipologia);
        atividade.setDentroMunicipio(!cnaeTipologia.getForaMunicipio());
        atividade.setDataCadastro(new Date());
        atividade.setUsuarioLicenciamento(usuarioLicenciamento);

        atividadeRepository.save(atividade);

        relAtividadePerguntaService.editar(atividade, atividadeDispensavelDTO.getPerguntas());

        return atividade;
    }

    @Override
    public Atividade ativarDesativar(Integer idAtividadeDispensavel) {

        Atividade atividade = atividadeRepository.findById(idAtividadeDispensavel).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não foi possível encontrar a atividade dispensável com id: " + idAtividadeDispensavel));

        atividade.setAtivo(!atividade.getAtivo());

        return atividadeRepository.save(atividade);
    }

    @Override
    public Page<Atividade> listarAtividadesDispensaveis(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Atividade> specification = preparaFiltroAtividadeDispensavel(filtro);

        return atividadeRepository.findAll(specification, pageable);

    }

    private Specification<Atividade> preparaFiltroAtividadeDispensavel(FiltroPesquisa filtro) {

        Specification<Atividade> specification = Specification.where(AtividadeSpecification.padrao()
                .and(AtividadeSpecification.filtrarAtividadesDispensaveis()));

        if (filtro.getStringPesquisa() != null) {

            specification = specification.and(AtividadeSpecification.atividadeNome(filtro.getStringPesquisa())
                    .or(AtividadeSpecification.atividadeCodigo(filtro.getStringPesquisa())));

        }

        return specification;

    }

    @Override
    public List<AtividadeDispensavelCsv> listarAtividadesDispensaveisParaCsv() {

        List<Atividade> atividades = listarAtividadesDispensaveis();
        List<AtividadeDispensavelCsv> dtos = new ArrayList<>();

        for (Atividade atividade : atividades) {
            dtos.add(atividade.preparaAtividadeDispensavelParaCsv());
        }

        return dtos;

    }

    public List<Atividade> listarAtividadesDispensaveis() {

        Specification<Atividade> specification = Specification.where(AtividadeSpecification.padrao()
                .and(AtividadeSpecification.filtrarAtividadesDispensaveis()));

        return atividadeRepository.findAll(specification, Sort.by("id"));

    }

    @Override
    public AtividadeDispensavelEdicaoDTO findById(Integer idAtividadeDispensavel) {

        Atividade atividade = atividadeRepository.findById(idAtividadeDispensavel).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não foi possível encontrar o cnae dispensável. "));


        List<RelAtividadePergunta> relAtividadePerguntas = relAtividadePerguntaRepository.findByAtividade(atividade);

        List<Pergunta> perguntas = new ArrayList<>();

        relAtividadePerguntas.forEach(atividadePergunta -> perguntas.add(
                perguntaRepository.findById(atividadePergunta.getPergunta().getId()).get()));

        return new AtividadeDispensavelEdicaoDTO(
                atividade.getId(),
                atividade.getAtividadesCnae().get(0).getAtividadeCnae(),
                atividade.getTipologia(),
                atividade.getDentroMunicipio(),
                perguntas);

    }
}
