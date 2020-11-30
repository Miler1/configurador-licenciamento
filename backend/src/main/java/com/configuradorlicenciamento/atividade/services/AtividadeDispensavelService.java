package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelDTO;
import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelEdicaoDTO;
import com.configuradorlicenciamento.atividade.interfaces.IAtividadeDispensavelService;
import com.configuradorlicenciamento.atividade.interfaces.IRelAtividadePerguntaService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.RelAtividadePergunta;
import com.configuradorlicenciamento.atividade.models.TipoAtividade;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.atividade.repositories.RelAtividadePerguntaRepository;
import com.configuradorlicenciamento.atividade.repositories.TipoAtividadeRepository;
import com.configuradorlicenciamento.atividade.specifications.AtividadeSpecification;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.historicoConfigurador.interfaces.IHistoricoConfiguradorService;
import com.configuradorlicenciamento.historicoConfigurador.models.AcaoConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.FuncionalidadeConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.HistoricoConfigurador;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.pergunta.repositories.PerguntaRepository;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.potencialPoluidor.repositories.PotencialPoluidorRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.services.TipoCaracterizacaoAtividadeService;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.tipologia.repositories.TipologiaRepository;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @Autowired
    IHistoricoConfiguradorService historicoConfiguradorService;

    @Override
    public List<Atividade> salvar(HttpServletRequest request, AtividadeDispensavelDTO atividadeDispensavelDTO) {

        boolean statusAtivoAtual;

        boolean statusAtivoAntigo = true;

        //caso exista justificativa, logo é uma atividade editada que precisa manter histórico
        boolean existeJustificativa = atividadeDispensavelDTO.getJustificativa() != null;

        if (existeJustificativa) {

            Atividade atividadeAntiga = buscarAtividade(atividadeDispensavelDTO.getId());

            statusAtivoAntigo = atividadeAntiga.getAtivo();

            salvarAtividadeAntiga(atividadeDispensavelDTO, atividadeAntiga);

            atividadeDispensavelDTO.setId(null);

        }

        statusAtivoAtual = statusAtivoAntigo;

        List<Atividade> atividades = new ArrayList<>();

        atividadeDispensavelDTO.getCnaesTipologia().forEach(cnaeTipologia -> {

            AtividadeCnae atividadeCnae = new AtividadeCnae();

            Optional<AtividadeCnae> atividadeCnaeSalva = atividadeCnaeRepository.findById(cnaeTipologia.getCnae().getId());

            if (atividadeCnaeSalva.isPresent()) {
                atividadeCnae = atividadeCnaeSalva.get();
            }

            Tipologia tipologia = new Tipologia();

            Optional<Tipologia> tipologiaSalva = tipologiaRepository.findById(cnaeTipologia.getTipologia().getId());

            if (tipologiaSalva.isPresent()) {
                tipologia = tipologiaSalva.get();
            }

            PotencialPoluidor potencialPoluidor = potencialPoluidorRepository.findByCodigo("I");

            List<TipoAtividade> tiposAtividades = tipoAtividadeRepository.findAll();

            Atividade atividade = new Atividade.AtividadeBuilder(!cnaeTipologia.getForaMunicipio())
                    .setNome(atividadeCnae.getNome())
                    .setCodigo("0000")
                    .setTipologia(tipologia)
                    .setGeoLinha(true)
                    .setGeoPonto(true)
                    .setGeoPoligono(true)
                    .setPotencialPoluidor(potencialPoluidor)
                    .setAtivo(statusAtivoAtual)
                    .setItemAntigo(false)
                    .setDentroEmpreendimento(false)
                    .setV1(false)
                    .setRascunho(false)
                    .setTiposAtividades(tiposAtividades)
                    .build();

            atividadeRepository.save(atividade);

            tipoCaracterizacaoAtividadeService.salvarAtividadeDispensavel(cnaeTipologia.getCnae(), atividade);

            relAtividadePerguntaService.salvar(atividade, atividadeDispensavelDTO.getPerguntas());

            atividades.add(atividade);

            if (!existeJustificativa) {

                historicoConfiguradorService.salvar(
                        request,
                        atividade.getId(),
                        FuncionalidadeConfigurador.Funcionalidades.CNAES_DISPENSAVEIS.getTipo(),
                        AcaoConfigurador.Acoes.CADASTRAR.getAcao()
                );

            }

        });

        return atividades;

    }

    @Override
    public Atividade editar(HttpServletRequest request, AtividadeDispensavelDTO atividadeDispensavelDTO) {

        Integer idAtividadeAntiga = atividadeDispensavelDTO.getId();

        List<Atividade> atividades = salvar(request, atividadeDispensavelDTO);

        Atividade atividadeAtual = atividades.get(0);

        historicoConfiguradorService.editar(
                request,
                atividadeAtual.getId(),
                idAtividadeAntiga,
                FuncionalidadeConfigurador.Funcionalidades.CNAES_DISPENSAVEIS.getTipo(),
                AcaoConfigurador.Acoes.EDITAR.getAcao(),
                atividadeDispensavelDTO.getJustificativa());

        return atividadeAtual;

    }

    @Override
    public Atividade ativarDesativar(HttpServletRequest request, Integer idAtividadeDispensavel) {

        Atividade atividade = buscarAtividade(idAtividadeDispensavel);

        atividade.setAtivo(!atividade.getAtivo());

        boolean ativo = atividade.getAtivo();

        String acao = ativo ? AcaoConfigurador.Acoes.ATIVAR.getAcao() : AcaoConfigurador.Acoes.DESATIVAR.getAcao();

        atividadeRepository.save(atividade);

        historicoConfiguradorService.salvar(
                request,
                atividade.getId(),
                FuncionalidadeConfigurador.Funcionalidades.CNAES_DISPENSAVEIS.getTipo(),
                acao
        );

        return atividade;

    }

    @Override
    public Page<Atividade> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Atividade> specification = prepararFiltroAtividadeDispensavel(filtro);

        return atividadeRepository.findAll(specification, pageable);

    }

    @Override
    public List<Atividade> listarAtividadesDispensaveis() {
        return atividadeRepository.findAll(getFiltro(), Sort.by("nome"));
    }

    @Override
    public List<AtividadeDispensavelCsv> listarAtividadesParaCsv() {

        List<Atividade> atividades = listarAtividadesDispensaveis();
        List<AtividadeDispensavelCsv> dtos = new ArrayList<>();

        for (Atividade atividade : atividades) {

            List<HistoricoConfigurador> historicos = historicoConfiguradorService.buscarHistoricoItem(
                    FuncionalidadeConfigurador.Funcionalidades.CNAES_DISPENSAVEIS.getTipo(),
                    atividade.getId()
            );

            dtos.add(atividade.preparaAtividadeDispensavelParaCsv(
                    !historicos.isEmpty() ? historicos.get(0).getDataAcao() : null,
                    !historicos.isEmpty() ? historicos.get(0).getUsuarioLicenciamento() : null
            ));

        }

        return dtos;

    }

    @Override
    public AtividadeDispensavelEdicaoDTO findById(Integer idAtividadeDispensavel) {

        Atividade atividade = buscarAtividade(idAtividadeDispensavel);

        List<RelAtividadePergunta> relAtividadePerguntas = relAtividadePerguntaRepository.findByAtividade(atividade);

        List<Pergunta> perguntas = new ArrayList<>();

        for (int i = 0; i < relAtividadePerguntas.size(); i++) {

            Optional<Pergunta> perguntaExistente = perguntaRepository.findById(relAtividadePerguntas.get(i).getPergunta().getId());

            perguntaExistente.ifPresent(perguntas::add);

            int ordemPergunta = relAtividadePerguntas.get(i).getOrdem();

            if (ordemPergunta > 0) {
                perguntas.get(i).setOrdem(ordemPergunta);
            }

        }

        return new AtividadeDispensavelEdicaoDTO(
                atividade.getId(),
                atividade.getAtividadesCnae().get(0).getAtividadeCnae(),
                atividade.getTipologia(),
                atividade.getDentroMunicipio(),
                perguntas);

    }

    private Atividade buscarAtividade(Integer idAtividadeDispensavel) {

        return atividadeRepository.findById(idAtividadeDispensavel).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não foi possível encontrar a atividade dispensável com id: " + idAtividadeDispensavel));

    }

    private void salvarAtividadeAntiga(AtividadeDispensavelDTO atividadeDispensavelDTO, Atividade atividadeAntiga) {

        atividadeAntiga.setAtivo(false);
        atividadeAntiga.setItemAntigo(true);

        atividadeRepository.save(atividadeAntiga);

        atividadeDispensavelDTO.getCnaesTipologia().forEach(cnaeTipologia -> {
                    tipoCaracterizacaoAtividadeService.editarAtividadeDispensavel(cnaeTipologia.getCnae(), atividadeAntiga);
                }
        );

    }

    private Specification<Atividade> getFiltro() {

        return Specification.where(AtividadeSpecification.padrao()
                .and(AtividadeSpecification.filtrarAtividadesDispensaveis())
                .and(AtividadeSpecification.filtrarAtividadesAtuais()));

    }

    private Specification<Atividade> prepararFiltroAtividadeDispensavel(FiltroPesquisa filtro) {

        Specification<Atividade> specification = getFiltro();

        if (filtro.getStringPesquisa() != null) {

            specification = specification.and(AtividadeSpecification.atividadeNomeCnae(filtro.getStringPesquisa())
                    .or(AtividadeSpecification.atividadeCodigoCnae(filtro.getStringPesquisa())));

        }

        return specification;

    }

}