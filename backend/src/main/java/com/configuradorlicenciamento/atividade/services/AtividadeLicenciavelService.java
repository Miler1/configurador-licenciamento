package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelCsv;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelDTO;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelEdicaoDTO;
import com.configuradorlicenciamento.atividade.interfaces.IAtividadeService;
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
public class AtividadeLicenciavelService implements IAtividadeService {

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
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

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
    public Page<Atividade> listarAtividadesLicenciaveis(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Atividade> specification = preparaFiltroAtividadeLicenciavel(filtro);

        return atividadeRepository.findAll(specification, pageable);

    }

    @Override
    public List<AtividadeLicenciavelCsv> listarAtividadesLicenciaveisParaCsv() {

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
                        !historicos.isEmpty() ? historicos.get(0).getDataCadastro() : null,
                        !historicos.isEmpty() ? historicos.get(0).getUsuarioLicenciamento() : null
                ));

            }

        }

        return dtos;

    }

    @Override
    public List<Atividade> listarAtividadesLicenciaveis() {

        Specification<Atividade> specification = Specification.where(AtividadeSpecification.padrao()
                .and(AtividadeSpecification.filtrarAtividadesLicenciaveis())
                .and(AtividadeSpecification.filtrarAtividadesAtuais()));

        return atividadeRepository.findAll(specification, Sort.by("id"));

    }

    @Override
    public Atividade salvarAtividadeLicenciavel(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        if (atividadeLicenciavelDTO.getDados().getId() != null) {
            return this.editarAtividadeLicenciavel(request, atividadeLicenciavelDTO);
        }

        boolean existeAtividade = atividadeRepository.existsByCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade().trim());

        if (existeAtividade && atividadeLicenciavelDTO.getJustificativa() == null) {
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

        List<TaxaLicenciamento> taxasLicenciamentoAtividade = new ArrayList<>();

        Optional<CodigoTaxaLicenciamento> codigoTaxaLicenciamento = codigoTaxaLicenciamentoRepository.findById(
                atividadeLicenciavelDTO.getDados().getTaxaLicenciamento().getId());

        if (codigoTaxaLicenciamento.isPresent()) {
            taxasLicenciamentoAtividade = taxaLicenciamentoRepository.findByCodigo(codigoTaxaLicenciamento.get());
        }

        List<PorteAtividade> portesAtividade = porteAtividadeService.salvar(atividadeLicenciavelDTO.getParametros());

        Atividade atividade = new Atividade.AtividadeBuilder(false)
                .setNome(atividadeLicenciavelDTO.getDados().getNomeAtividade())
                .setCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade().trim())
                .setTipologia(tipologiaAtividade.orElse(null))
                .setGeoPonto(atividadeLicenciavelDTO.getDados().getGeoPonto())
                .setGeoLinha(atividadeLicenciavelDTO.getDados().getGeoLinha())
                .setGeoPoligono(atividadeLicenciavelDTO.getDados().getGeoPoligono())
                .setPotencialPoluidor(potencialPoluidor.orElse(null))
                .setSiglaSetor(atividadeLicenciavelDTO.getDados().getSetor())
                .setAtivo(true)
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

        tipoCaracterizacaoAtividadeService.salvarAtividadeLicenciavel(atividadeLicenciavelDTO.getCnaesAtividade(), atividade);

        relAtividadeParametroAtividadeService.salvar(atividade, atividadeLicenciavelDTO.getParametros().get(0));

        if (atividadeLicenciavelDTO.getJustificativa() == null) {

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
    public Atividade editarAtividadeLicenciavel(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        verificarCodigoParaEdicao(atividadeLicenciavelDTO);

        Optional<Atividade> atividadeSalva = atividadeRepository.findById(atividadeLicenciavelDTO.getDados().getId());

        Atividade atividadeAntiga;

        Atividade atividadeAtual = new Atividade();

        if (atividadeSalva.isPresent()) {

            atividadeAntiga = atividadeSalva.get();

            atividadeAntiga.setAtivo(false);

            atividadeAntiga.setItemAntigo(true);

            atividadeRepository.save(atividadeAntiga);

            atividadeLicenciavelDTO.getDados().setId(null);

            atividadeAtual = salvarAtividadeLicenciavel(request, atividadeLicenciavelDTO);

            historicoConfiguradorService.editar(
                    request,
                    atividadeAtual.getId(),
                    atividadeAntiga.getId(),
                    FuncionalidadeConfigurador.Funcionalidades.ATIVIDADES_LICENCIAVEIS.getTipo(),
                    AcaoConfigurador.Acoes.EDITAR.getAcao(),
                    atividadeLicenciavelDTO.getJustificativa());

        }

        return atividadeAtual;

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

    @Override
    public void excluirRascunhoAtividadeLicenciavel(HttpServletRequest request, Integer idAtividade) {

        Atividade atividadeRascunho = atividadeRepository.findById(idAtividade).get();

        tipoCaracterizacaoAtividadeService.excluirAtividadeLicenciavel(atividadeRascunho);

        relAtividadeParametroAtividadeService.excluir(atividadeRascunho);

        atividadeRepository.delete(atividadeRascunho);

    }

    public Atividade salvarRascunhoAtividadeLicenciavel(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        if (atividadeLicenciavelDTO.getDados().getId() != null) {
            return this.editarRascunhoAtividadeLicenciavel(request, atividadeLicenciavelDTO);
        }

        boolean existeAtividade = atividadeRepository.existsByCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade().trim());

        if (existeAtividade) {
            throw new ConflictException(ATIVIDADE_EXISTENTE);
        }

        Optional<Tipologia> tipologiaAtividade = Optional.ofNullable(null);

        if (atividadeLicenciavelDTO.getDados().getTipologia() != null) {
            tipologiaAtividade = tipologiaRepository.findById(atividadeLicenciavelDTO.getDados().getTipologia().getId());
        }

        Optional<PotencialPoluidor> potencialPoluidor = Optional.ofNullable(null);

        if (atividadeLicenciavelDTO.getDados().getPotencialPoluidor() != null) {
            potencialPoluidor = potencialPoluidorRepository.findById(atividadeLicenciavelDTO.getDados().getPotencialPoluidor().getId());
        }

        Optional<RequisitoTecnico> requisitoTecnico = Optional.ofNullable(null);

        if (atividadeLicenciavelDTO.getDados().getRequisitoTecnico() != null) {
            requisitoTecnico = requisitoTecnicoRepository.findById(atividadeLicenciavelDTO.getDados().getRequisitoTecnico().getId());
        }

        Optional<Boolean> foraEmpreendimento = Optional.ofNullable(null);

        if (atividadeLicenciavelDTO.getDados().getForaEmpreendimento() != null) {
            foraEmpreendimento = Optional.of(!atividadeLicenciavelDTO.getDados().getForaEmpreendimento());
        }

        List<TipoAtividade> tiposAtividade = new ArrayList<>();

        if (atividadeLicenciavelDTO.getDados().getTiposAtividade() != null) {
            atividadeLicenciavelDTO.getDados().getTiposAtividade().forEach(tipoAtividade ->
                    tiposAtividade.add(tipoAtividadeRepository.findByCodigo(tipoAtividade))
            );
        }

        List<Licenca> licencasAtividade = new ArrayList<>();

        if (atividadeLicenciavelDTO.getDados().getLicencas() != null) {
            atividadeLicenciavelDTO.getDados().getLicencas().forEach(licenca ->
                    licencasAtividade.add(licencaRepository.findById(licenca.getId()).get())
            );
        }

        List<TaxaLicenciamento> taxasLicenciamentoAtividade = new ArrayList<>();

        if (atividadeLicenciavelDTO.getDados().getTaxaLicenciamento() != null) {

            Optional<CodigoTaxaLicenciamento> codigoTaxaLicenciamento = codigoTaxaLicenciamentoRepository.findById(atividadeLicenciavelDTO.getDados().getTaxaLicenciamento().getId());

            if (codigoTaxaLicenciamento.isPresent()) {
                taxasLicenciamentoAtividade = taxaLicenciamentoRepository.findByCodigo(codigoTaxaLicenciamento.get());
            }

        }

        List<PorteAtividade> portesAtividade = new ArrayList<>();

        if (!atividadeLicenciavelDTO.getParametros().isEmpty()) {
            portesAtividade = porteAtividadeService.salvar(atividadeLicenciavelDTO.getParametros());
        }

        Atividade atividade = new Atividade.AtividadeBuilder(false)
                .setNome(atividadeLicenciavelDTO.getDados().getNomeAtividade())
                .setCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade())
                .setTipologia(tipologiaAtividade.orElse(null))
                .setGeoPonto(atividadeLicenciavelDTO.getDados().getGeoPonto())
                .setGeoLinha(atividadeLicenciavelDTO.getDados().getGeoLinha())
                .setGeoPoligono(atividadeLicenciavelDTO.getDados().getGeoPoligono())
                .setPotencialPoluidor(potencialPoluidor.orElse(null))
                .setSiglaSetor(atividadeLicenciavelDTO.getDados().getSetor())
                .setAtivo(false)
                .setItemAntigo(false)
                .setDentroEmpreendimento(foraEmpreendimento.orElse(null))
                .setRequisitoTecnico(requisitoTecnico.orElse(null))
                .setV1(false)
                .setRascunho(true)
                .setTiposAtividades(tiposAtividade)
                .setTiposLicencas(licencasAtividade)
                .setTaxasLicenciamento(taxasLicenciamentoAtividade)
                .setPortesAtividade(portesAtividade)
                .build();

        atividadeRepository.save(atividade);

        tipoCaracterizacaoAtividadeService.salvarAtividadeLicenciavel(atividadeLicenciavelDTO.getCnaesAtividade(), atividade);

        if (!atividadeLicenciavelDTO.getParametros().isEmpty()) {
            relAtividadeParametroAtividadeService.salvar(atividade, atividadeLicenciavelDTO.getParametros().get(0));
        }

        return atividade;

    }

    @Override
    public Atividade editarRascunhoAtividadeLicenciavel(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        verificarCodigoParaEdicao(atividadeLicenciavelDTO);

        Optional<Tipologia> tipologiaAtividade = Optional.ofNullable(null);

        if (atividadeLicenciavelDTO.getDados().getTipologia() != null) {
            tipologiaAtividade = tipologiaRepository.findById(atividadeLicenciavelDTO.getDados().getTipologia().getId());
        }

        Optional<PotencialPoluidor> potencialPoluidor = Optional.ofNullable(null);

        if (atividadeLicenciavelDTO.getDados().getPotencialPoluidor() != null) {
            potencialPoluidor = potencialPoluidorRepository.findById(atividadeLicenciavelDTO.getDados().getPotencialPoluidor().getId());
        }

        Optional<RequisitoTecnico> requisitoTecnico = Optional.ofNullable(null);

        if (atividadeLicenciavelDTO.getDados().getRequisitoTecnico() != null) {
            requisitoTecnico = requisitoTecnicoRepository.findById(atividadeLicenciavelDTO.getDados().getRequisitoTecnico().getId());
        }

        Optional<Boolean> foraEmpreendimento = Optional.ofNullable(null);

        if (atividadeLicenciavelDTO.getDados().getForaEmpreendimento() != null) {
            foraEmpreendimento = Optional.of(!atividadeLicenciavelDTO.getDados().getForaEmpreendimento());
        }

        List<TipoAtividade> tiposAtividade = new ArrayList<>();

        if (atividadeLicenciavelDTO.getDados().getTiposAtividade() != null) {

            atividadeLicenciavelDTO.getDados().getTiposAtividade().forEach(tipoAtividade ->
                    tiposAtividade.add(tipoAtividadeRepository.findByCodigo(tipoAtividade))
            );

        }

        List<Licenca> licencasAtividade = new ArrayList<>();

        if (atividadeLicenciavelDTO.getDados().getLicencas() != null) {

            atividadeLicenciavelDTO.getDados().getLicencas().forEach(licenca ->
                    licencasAtividade.add(licencaRepository.findById(licenca.getId()).get())
            );

        }

        List<TaxaLicenciamento> taxasLicenciamentoAtividade = new ArrayList<>();

        if (atividadeLicenciavelDTO.getDados().getTaxaLicenciamento() != null) {

            Optional<CodigoTaxaLicenciamento> codigoTaxaLicenciamento = codigoTaxaLicenciamentoRepository.findById(atividadeLicenciavelDTO.getDados().getTaxaLicenciamento().getId());

            if (codigoTaxaLicenciamento.isPresent()) {
                taxasLicenciamentoAtividade = taxaLicenciamentoRepository.findByCodigo(codigoTaxaLicenciamento.get());
            }

        }

        List<PorteAtividade> portesAtividade = new ArrayList<>();

        if (!atividadeLicenciavelDTO.getParametros().isEmpty()) {
            portesAtividade = porteAtividadeService.editar(atividadeLicenciavelDTO.getParametros());
        }

        Atividade atividadeRascunhoSalva = atividadeRepository.findById(atividadeLicenciavelDTO.getDados().getId()).get();

        atividadeRascunhoSalva.setNome(atividadeLicenciavelDTO.getDados().getNomeAtividade());
        atividadeRascunhoSalva.setCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade());
        atividadeRascunhoSalva.setTipologia(tipologiaAtividade.orElse(null));
        atividadeRascunhoSalva.setGeoPonto(atividadeLicenciavelDTO.getDados().getGeoPonto());
        atividadeRascunhoSalva.setGeoLinha(atividadeLicenciavelDTO.getDados().getGeoLinha());
        atividadeRascunhoSalva.setGeoPoligono(atividadeLicenciavelDTO.getDados().getGeoPoligono());
        atividadeRascunhoSalva.setPotencialPoluidor(potencialPoluidor.orElse(null));
        atividadeRascunhoSalva.setSiglaSetor(atividadeLicenciavelDTO.getDados().getSetor());
        atividadeRascunhoSalva.setAtivo(false);
        atividadeRascunhoSalva.setItemAntigo(false);
        atividadeRascunhoSalva.setDentroEmpreendimento(foraEmpreendimento.orElse(null));
        atividadeRascunhoSalva.setRequisitoTecnico(requisitoTecnico.orElse(null));
        atividadeRascunhoSalva.setV1(false);
        atividadeRascunhoSalva.setRascunho(true);
        atividadeRascunhoSalva.setTiposAtividades(tiposAtividade);
        atividadeRascunhoSalva.setTiposLicencas(licencasAtividade);
        atividadeRascunhoSalva.setTaxasLicenciamento(taxasLicenciamentoAtividade);
        atividadeRascunhoSalva.setPortesAtividade(portesAtividade);
        atividadeRascunhoSalva.setDentroMunicipio(false);

        atividadeRepository.save(atividadeRascunhoSalva);

        tipoCaracterizacaoAtividadeService.editarAtividadeLicenciavel(atividadeLicenciavelDTO.getCnaesAtividade(), atividadeRascunhoSalva);

        if (!atividadeLicenciavelDTO.getParametros().isEmpty()) {
            relAtividadeParametroAtividadeService.editar(atividadeRascunhoSalva, atividadeLicenciavelDTO.getParametros().get(0));
        }

        return atividadeRascunhoSalva;

    }

    private Specification<Atividade> preparaFiltroAtividadeLicenciavel(FiltroPesquisa filtro) {

        Specification<Atividade> specification = Specification.where(AtividadeSpecification.padrao()
                .and(AtividadeSpecification.filtrarAtividadesLicenciaveis())
                .and(AtividadeSpecification.filtrarAtividadesAtuais()));

        if (filtro.getStringPesquisa() != null) {

            specification = specification.and(AtividadeSpecification.atividadeNome(filtro.getStringPesquisa())
                    .or(AtividadeSpecification.atividadeCodigo(filtro.getStringPesquisa())));

        }

        return specification;

    }


    @Override
    public AtividadeLicenciavelEdicaoDTO findById(Integer idAtividadeLicenciavel) {

        Atividade atividade = atividadeRepository.findById(idAtividadeLicenciavel).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não foi possível encontrar a atividade licenciável. "));

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
    public Atividade ativarDesativar(HttpServletRequest request, Integer idAtividadeLicenciavel) {

        Atividade atividade = atividadeRepository.findById(idAtividadeLicenciavel).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não foi possível encontrar a atividade licenciável com id: " + idAtividadeLicenciavel));

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

}