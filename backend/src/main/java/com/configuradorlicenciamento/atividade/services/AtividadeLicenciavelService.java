package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelCsv;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelDTO;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelEdicaoDTO;
import com.configuradorlicenciamento.atividade.interfaces.IAtividadeLicenciavelService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.PorteAtividade;
import com.configuradorlicenciamento.atividade.models.RelAtividadeParametroAtividade;
import com.configuradorlicenciamento.atividade.models.TipoAtividade;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.atividade.repositories.RelAtividadeParametroAtividadeRepository;
import com.configuradorlicenciamento.atividade.repositories.TipoAtividadeRepository;
import com.configuradorlicenciamento.atividade.specifications.AtividadeSpecification;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.historicoConfigurador.interfaces.IHistoricoConfiguradorService;
import com.configuradorlicenciamento.historicoConfigurador.models.AcaoConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.FuncionalidadeConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.HistoricoConfigurador;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.licenca.repositories.LicencaRepository;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.potencialPoluidor.repositories.PotencialPoluidorRepository;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisitoTecnico.repositories.RequisitoTecnicoRepository;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.repositories.CodigoTaxaLicenciamentoRepository;
import com.configuradorlicenciamento.taxaLicenciamento.repositories.TaxaLicenciamentoRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories.TipoCaracterizacaoAtividadeRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.services.TipoCaracterizacaoAtividadeService;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.tipologia.repositories.TipologiaRepository;
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
public class AtividadeLicenciavelService implements IAtividadeLicenciavelService {

    private static final String ATIVIDADE_EXISTENTE = "Já existe uma atividade com o mesmo código.";

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    AtividadeCnaeRepository atividadeCnaeRepository;

    @Autowired
    TipologiaRepository tipologiaRepository;

    @Autowired
    PotencialPoluidorRepository potencialPoluidorRepository;

    @Autowired
    TipoAtividadeRepository tipoAtividadeRepository;

    @Autowired
    RequisitoTecnicoRepository requisitoTecnicoRepository;

    @Autowired
    LicencaRepository licencaRepository;

    @Autowired
    TaxaLicenciamentoRepository taxaLicenciamentoRepository;

    @Autowired
    CodigoTaxaLicenciamentoRepository codigoTaxaLicenciamentoRepository;

    @Autowired
    TipoCaracterizacaoAtividadeRepository tipoCaracterizacaoAtividadeRepository;

    @Autowired
    RelAtividadeParametroAtividadeRepository relAtividadeParametroAtividadeRepository;

    @Autowired
    RelAtividadeParametroAtividadeService relAtividadeParametroAtividadeService;

    @Autowired
    PorteAtividadeService porteAtividadeService;

    @Autowired
    TipoCaracterizacaoAtividadeService tipoCaracterizacaoAtividadeService;

    @Autowired
    IHistoricoConfiguradorService historicoConfiguradorService;

    @Override
    public Atividade salvar(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        boolean ativo = true;

        boolean existeAtividade = atividadeRepository.existsByCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade().trim());

        //caso exista justificativa, logo é uma atividade editada que precisa manter histórico
        boolean existeJustificativa = atividadeLicenciavelDTO.getJustificativa() != null;

        if (existeAtividade && !existeJustificativa) {
            throw new ConflictException(ATIVIDADE_EXISTENTE);
        }

        Optional<Tipologia> tipologiaAtividade = tipologiaRepository.findById(
                atividadeLicenciavelDTO.getDados().getTipologia().getId());

        Optional<PotencialPoluidor> potencialPoluidor = potencialPoluidorRepository.findById(
                atividadeLicenciavelDTO.getDados().getPotencialPoluidor().getId());

        Optional<RequisitoTecnico> requisitoTecnico = requisitoTecnicoRepository.findById(
                atividadeLicenciavelDTO.getDados().getRequisitoTecnico().getId());

        List<TipoAtividade> tiposAtividade = new ArrayList<>();

        atividadeLicenciavelDTO.getDados().getTiposAtividade().forEach(tipoAtividade ->
                tiposAtividade.add(tipoAtividadeRepository.findByCodigo(tipoAtividade))
        );

        List<Licenca> licencasAtividade = new ArrayList<>();

        atividadeLicenciavelDTO.getDados().getLicencas().forEach(licenca ->
                licencasAtividade.add(licencaRepository.findById(licenca.getId()).get())
        );

        List<TaxaLicenciamento> taxasLicenciamentoAtividade;

        CodigoTaxaLicenciamento codigoTaxaLicenciamento = codigoTaxaLicenciamentoRepository.findByCodigo(
                atividadeLicenciavelDTO.getDados().getTaxaLicenciamento().getCodigo());

        taxasLicenciamentoAtividade = taxaLicenciamentoRepository.findByCodigoAndAtivo(codigoTaxaLicenciamento, true);

        List<PorteAtividade> portesAtividade = porteAtividadeService.salvar(atividadeLicenciavelDTO.getParametros());

        if (existeJustificativa) {
            ativo = atividadeLicenciavelDTO.getDados().getAtivo();
        }

        Atividade atividade = new Atividade.AtividadeBuilder(false)
                .setNome(atividadeLicenciavelDTO.getDados().getNomeAtividade())
                .setCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade().trim())
                .setTipologia(tipologiaAtividade.orElse(null))
                .setGeoPonto(atividadeLicenciavelDTO.getDados().getGeoPonto())
                .setGeoLinha(atividadeLicenciavelDTO.getDados().getGeoLinha())
                .setGeoPoligono(atividadeLicenciavelDTO.getDados().getGeoPoligono())
                .setPotencialPoluidor(potencialPoluidor.orElse(null))
                .setSiglaSetor(atividadeLicenciavelDTO.getDados().getSetor())
                .setAtivo(ativo)
                .setItemAntigo(false)
                .setDentroEmpreendimento(!atividadeLicenciavelDTO.getDados().getForaEmpreendimento())
                .setRequisitoTecnico(requisitoTecnico.orElse(null))
                .setV1(false)
                .setRascunho(false)
                .setTiposAtividades(tiposAtividade)
                .setTiposLicencas(licencasAtividade)
                .setTaxasLicenciamento(taxasLicenciamentoAtividade)
                .setPortesAtividade(portesAtividade)
                .build();

        atividadeRepository.save(atividade);

        relAtividadeParametroAtividadeService.salvar(atividade, atividadeLicenciavelDTO.getParametros().get(0));

        tipoCaracterizacaoAtividadeService.salvarAtividadeLicenciavel(atividadeLicenciavelDTO.getCnaesAtividade(), atividade);

        if (!existeJustificativa) {

            historicoConfiguradorService.salvar(
                    request,
                    atividade.getId(),
                    FuncionalidadeConfigurador.Funcionalidades.ATIVIDADES_LICENCIAVEIS.getTipo(),
                    AcaoConfigurador.Acoes.CADASTRAR.getAcao()
            );

        }

        return atividade;

    }

    @Override
    public Atividade editar(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        verificarCodigoParaEdicao(atividadeLicenciavelDTO);

        Atividade atividadeAntiga = buscarAtividade(atividadeLicenciavelDTO.getDados().getId());

        atividadeLicenciavelDTO.getDados().setAtivo(atividadeAntiga.getAtivo());

        salvarAtividadeAntiga(atividadeLicenciavelDTO, atividadeAntiga);

        atividadeLicenciavelDTO.getDados().setId(null);

        //Gerar novo registro para manter histórico
        Atividade atividadeAtual = salvar(request, atividadeLicenciavelDTO);

        historicoConfiguradorService.editar(
                request,
                atividadeAtual.getId(),
                atividadeAntiga.getId(),
                FuncionalidadeConfigurador.Funcionalidades.ATIVIDADES_LICENCIAVEIS.getTipo(),
                AcaoConfigurador.Acoes.EDITAR.getAcao(),
                atividadeLicenciavelDTO.getJustificativa());

        return atividadeAtual;

    }

    @Override
    public Page<Atividade> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Atividade> specification = prepararFiltro(filtro);

        return atividadeRepository.findAll(specification, pageable);

    }

    @Override
    public List<Atividade> listarAtividadesLicenciaveis() {
        return atividadeRepository.findAll(getFiltro(), Sort.by("nome"));
    }

    @Override
    public List<AtividadeLicenciavelCsv> listarAtividadesParaCsv() {

        List<Atividade> tiposAtividade = listarAtividadesLicenciaveis();

        List<AtividadeLicenciavelCsv> dtos = new ArrayList<>();

        for (Atividade tipoAtividade : tiposAtividade) {

            boolean isRascunho = tipoAtividade.getRascunho();

            if (!isRascunho) {

                List<HistoricoConfigurador> historicos = historicoConfiguradorService.buscarHistoricoItem(
                        FuncionalidadeConfigurador.Funcionalidades.ATIVIDADES_LICENCIAVEIS.getTipo(),
                        tipoAtividade.getId()
                );

                dtos.add(tipoAtividade.preparaAtividadeLicenciavelParaCsv(
                        !historicos.isEmpty() ? historicos.get(0).getDataAcao() : null,
                        !historicos.isEmpty() ? historicos.get(0).getUsuarioLicenciamento() : null
                ));

            }

        }

        return dtos;

    }

    @Override
    public Atividade ativarDesativar(HttpServletRequest request, Integer idAtividadeLicenciavel) {

        Atividade atividade = buscarAtividade(idAtividadeLicenciavel);

        atividade.setAtivo(!atividade.getAtivo());

        atividadeRepository.save(atividade);

        boolean ativo = atividade.getAtivo();

        String acao = ativo ? AcaoConfigurador.Acoes.ATIVAR.getAcao() : AcaoConfigurador.Acoes.DESATIVAR.getAcao();

        historicoConfiguradorService.salvar(
                request,
                atividade.getId(),
                FuncionalidadeConfigurador.Funcionalidades.ATIVIDADES_LICENCIAVEIS.getTipo(),
                acao
        );

        return atividade;

    }

    @Override
    public AtividadeLicenciavelEdicaoDTO findById(Integer idAtividadeLicenciavel) {

        Atividade atividade = buscarAtividade(idAtividadeLicenciavel);

        List<TipoCaracterizacaoAtividade> tiposCaracterizacaoAtividade = tipoCaracterizacaoAtividadeRepository.findByAtividade(atividade);

        RelAtividadeParametroAtividade relAtividadeParametroAtividadeUm = null;
        RelAtividadeParametroAtividade relAtividadeParametroAtividadeDois = null;

        if (!atividade.getPortesAtividade().isEmpty() && atividade.getPortesAtividade().get(0).getParametroUm() != null) {
            relAtividadeParametroAtividadeUm = relAtividadeParametroAtividadeRepository.findByAtividadeAndParametro(atividade, atividade.getPortesAtividade().get(0).getParametroUm()).get(0);
        }

        if (!atividade.getPortesAtividade().isEmpty() && atividade.getPortesAtividade().get(0).getParametroDois() != null) {
            relAtividadeParametroAtividadeDois = relAtividadeParametroAtividadeRepository.findByAtividadeAndParametro(atividade, atividade.getPortesAtividade().get(0).getParametroDois()).get(0);
        }

        return new AtividadeLicenciavelEdicaoDTO(atividade,
                tiposCaracterizacaoAtividade,
                relAtividadeParametroAtividadeUm != null ? relAtividadeParametroAtividadeUm.getDescricaoUnidade() : null,
                relAtividadeParametroAtividadeDois != null ? relAtividadeParametroAtividadeDois.getDescricaoUnidade() : null);

    }

    @Override
    public List<Atividade> buscarAtividadeTaxaVinculada(TaxaLicenciamento taxaLicenciamento) {
        return atividadeRepository.findAllByTaxasLicenciamento(taxaLicenciamento);
    }

    @Override
    public void vincularNovaTaxa(TaxaLicenciamento taxaLicenciamento) {

        //buscar todas as atividades vincuadas a taxaLicenciamento
        List<Atividade> atividadesLicenciaveis = buscarAtividadeTaxaVinculada(taxaLicenciamento);

        //verificar se existe atividade vinculada a essa tabela
        if (!atividadesLicenciaveis.isEmpty()) {

            //editar atividade com a nova lista de taxas
            atividadesLicenciaveis.forEach(atividade -> {

                List<TaxaLicenciamento> taxasLicenciamentoAtividade = new ArrayList<>();

                Optional<CodigoTaxaLicenciamento> codigoTaxaLicenciamento =
                        codigoTaxaLicenciamentoRepository.findById(taxaLicenciamento.codigo.getId());

                if (codigoTaxaLicenciamento.isPresent()) {
                    taxasLicenciamentoAtividade = taxaLicenciamentoRepository.findByCodigoAndAtivo(codigoTaxaLicenciamento.get(), true);
                }

                atividade.setTaxasLicenciamento(taxasLicenciamentoAtividade);

                atividadeRepository.save(atividade);

            });

        }

    }

    private Atividade buscarAtividade(Integer idAtividadeLicenciavel) {

        return atividadeRepository.findById(idAtividadeLicenciavel).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não foi possível encontrar a atividade licenciável. "));

    }

    private void salvarAtividadeAntiga(AtividadeLicenciavelDTO atividadeLicenciavelDTO, Atividade atividadeAntiga) {

        atividadeAntiga.setAtivo(false);
        atividadeAntiga.setItemAntigo(true);

        tipoCaracterizacaoAtividadeService.editarAtividadeLicenciavel(atividadeLicenciavelDTO.getCnaesAtividade(), atividadeAntiga);

        atividadeRepository.save(atividadeAntiga);

    }

    private void verificarCodigoParaEdicao(AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        boolean existeAtividade = atividadeRepository.existsByCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade().trim());

        if (existeAtividade) {

            Atividade atividade = atividadeRepository.findByCodigoAndItemAntigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade().trim(), false);

            if (!atividade.getId().equals(atividadeLicenciavelDTO.getDados().getId())) {
                throw new ConflictException(ATIVIDADE_EXISTENTE);
            }

        }

    }

    private Specification<Atividade> prepararFiltro(FiltroPesquisa filtro) {

        Specification<Atividade> specification = getFiltro();

        if (filtro.getStringPesquisa() != null) {

            specification = specification.and(AtividadeSpecification.atividadeNome(filtro.getStringPesquisa())
                    .or(AtividadeSpecification.atividadeCodigo(filtro.getStringPesquisa())));

        }

        return specification;

    }

    private Specification<Atividade> getFiltro() {

        return Specification.where(AtividadeSpecification.padrao()
                .and(AtividadeSpecification.filtrarAtividadesLicenciaveis())
                .and(AtividadeSpecification.filtrarAtividadesAtuais()));

    }

}