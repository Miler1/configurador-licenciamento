package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelCsv;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelDTO;
import com.configuradorlicenciamento.atividade.interfaces.IAtividadeService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.PorteAtividade;
import com.configuradorlicenciamento.atividade.models.TipoAtividade;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.atividade.repositories.TipoAtividadeRepository;
import com.configuradorlicenciamento.atividade.specifications.AtividadeSpecification;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
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
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelDTO;
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
public class AtividadeService implements IAtividadeService {

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
    RelAtividadeParametroAtividadeService relAtividadeParametroAtividadeService;

    @Autowired
    PorteAtividadeService porteAtividadeService;

    @Autowired
    TipoCaracterizacaoAtividadeService tipoCaracterizacaoAtividadeService;

    @Override
    public Atividade salvar(AtividadeDispensavelDTO.RelacaoCnaeTipologia atividadeDispensavelDTO) {

        Optional<AtividadeCnae> atividadeCnae = atividadeCnaeRepository.findById(atividadeDispensavelDTO.getCnae().getId());

        Optional<Tipologia> tipologia = tipologiaRepository.findById(atividadeDispensavelDTO.getTipologia().getId());

        PotencialPoluidor potencialPoluidor = potencialPoluidorRepository.findByCodigo("I");

        List<TipoAtividade> tiposAtividades = tipoAtividadeRepository.findAll();

        Atividade atividade = new Atividade.AtividadeBuilder(!atividadeDispensavelDTO.getForaMunicipio())
                .setNome(atividadeCnae.get().getNome())
                .setTipologia(tipologia.get())
                .setGeoLinha(true)
                .setGeoPonto(true)
                .setGeoPoligono(true)
                .setCodigo("0000")
                .setPotencialPoluidor(potencialPoluidor)
                .setAtivo(true)
                .setDentroEmpreendimento(false)
                .setV1(false)
                .setTiposAtividades(tiposAtividades)
                .build();

        atividadeRepository.save(atividade);

        return atividade;

    }

    @Override
    public Atividade editar(AtividadeDispensavelDTO.RelacaoCnaeTipologia atividadeDispensavelDTO, Atividade atividade) {

        Optional<AtividadeCnae> atividadeCnae = atividadeCnaeRepository.findById(atividadeDispensavelDTO.getCnae().getId());

        Optional<Tipologia> tipologia = tipologiaRepository.findById(atividadeDispensavelDTO.getTipologia().getId());

        PotencialPoluidor potencialPoluidor = potencialPoluidorRepository.findByCodigo("I");

        List<TipoAtividade> tiposAtividades = tipoAtividadeRepository.findAll();

        atividadeCnae.ifPresent(cnae -> atividade.setNome(cnae.getNome()));
        tipologia.ifPresent(atividade::setTipologia);
        atividade.setGeoLinha(true);
        atividade.setGeoPonto(true);
        atividade.setGeoPoligono(true);
        atividade.setCodigo("0000");
        atividade.setPotencialPoluidor(potencialPoluidor);
        atividade.setDentroEmpreendimento(false);
        atividade.setDentroMunicipio(!atividadeDispensavelDTO.getForaMunicipio());
        atividade.setV1(false);
        atividade.setTiposAtividades(tiposAtividades);

        atividadeRepository.save(atividade);

        return atividade;

    }

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
            dtos.add(tipoAtividade.preparaAtividadeLicenciavelParaCsv());
        }

        return dtos;

    }

    @Override
    public List<Atividade> listarAtividadesLicenciaveis() {

        Specification<Atividade> specification = Specification.where(AtividadeSpecification.padrao()
                .and(AtividadeSpecification.filtrarAtividadesLicenciaveis()));

        return atividadeRepository.findAll(specification, Sort.by("id"));

    }

    @Override
    public Atividade salvarAtividadeLicenciavel(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        Object login = request.getSession().getAttribute("login");

        boolean existeAtividade = atividadeRepository.existsByCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade().trim());

        if (existeAtividade) {
            throw new ConflictException(ATIVIDADE_EXISTENTE);
        }

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

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
                .setsiglaSetor(atividadeLicenciavelDTO.getDados().getSetor())
                .setAtivo(true)
                .setDentroEmpreendimento(!atividadeLicenciavelDTO.getDados().getForaEmpreendimento())
                .setRequisitoTecnico(requisitoTecnico.orElse(null))
                .setV1(false)
                .setRascunho(false)
                .setTiposAtividades(tiposAtividade)
                .setTiposLicencas(licencasAtividade)
                .setTaxasLicenciamento(taxasLicenciamentoAtividade)
                .setPortesAtividade(portesAtividade)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        atividadeRepository.save(atividade);

        tipoCaracterizacaoAtividadeService.salvarAtividadeLicenciavel(atividadeLicenciavelDTO.getCnaesAtividade(), atividade);

        relAtividadeParametroAtividadeService.salvar(atividade, atividadeLicenciavelDTO.getParametros().get(0));

        return atividade;

    }

    @Override
    public Atividade salvarRascunhoAtividadeLicenciavel(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        Object login = request.getSession().getAttribute("login");

        boolean existeAtividade = atividadeRepository.existsByCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade().trim());

        if (existeAtividade) {
            throw new ConflictException(ATIVIDADE_EXISTENTE);
        }

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

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
                .setsiglaSetor(atividadeLicenciavelDTO.getDados().getSetor())
                .setAtivo(false)
                .setDentroEmpreendimento(foraEmpreendimento.orElse(null))
                .setRequisitoTecnico(requisitoTecnico.orElse(null))
                .setV1(false)
                .setRascunho(true)
                .setTiposAtividades(tiposAtividade)
                .setTiposLicencas(licencasAtividade)
                .setTaxasLicenciamento(taxasLicenciamentoAtividade)
                .setPortesAtividade(portesAtividade)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        atividadeRepository.save(atividade);

        tipoCaracterizacaoAtividadeService.salvarAtividadeLicenciavel(atividadeLicenciavelDTO.getCnaesAtividade(), atividade);

        if (!atividadeLicenciavelDTO.getParametros().isEmpty()) {
            relAtividadeParametroAtividadeService.salvar(atividade, atividadeLicenciavelDTO.getParametros().get(0));
        }

        return atividade;

    }

    private Specification<Atividade> preparaFiltroAtividadeLicenciavel(FiltroPesquisa filtro) {

        Specification<Atividade> specification = Specification.where(AtividadeSpecification.padrao()
                .and(AtividadeSpecification.filtrarAtividadesLicenciaveis()));

        if (filtro.getStringPesquisa() != null) {

            specification = specification.and(AtividadeSpecification.atividadeNome(filtro.getStringPesquisa())
                    .or(AtividadeSpecification.atividadeCodigo(filtro.getStringPesquisa())));

        }

        return specification;

    }

}