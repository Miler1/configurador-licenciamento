package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelDTO;
import com.configuradorlicenciamento.atividade.interfaces.IRascunhoService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.PorteAtividade;
import com.configuradorlicenciamento.atividade.models.TipoAtividade;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.atividade.repositories.RelAtividadeParametroAtividadeRepository;
import com.configuradorlicenciamento.atividade.repositories.TipoAtividadeRepository;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
import com.configuradorlicenciamento.historicoConfigurador.interfaces.IHistoricoConfiguradorService;
import com.configuradorlicenciamento.historicoConfigurador.models.AcaoConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.FuncionalidadeConfigurador;
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
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories.TipoCaracterizacaoAtividadeRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.services.TipoCaracterizacaoAtividadeService;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.tipologia.repositories.TipologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RascunhoService implements IRascunhoService {

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
    public Atividade finalizarCadastro(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        verificarCodigoParaEdicao(atividadeLicenciavelDTO);

        Optional<Tipologia> tipologiaAtividade = tipologiaRepository.findById(atividadeLicenciavelDTO.getDados().getTipologia().getId());

        Optional<PotencialPoluidor> potencialPoluidor = potencialPoluidorRepository.findById(atividadeLicenciavelDTO.getDados().getPotencialPoluidor().getId());

        Optional<RequisitoTecnico> requisitoTecnico = requisitoTecnicoRepository.findById(atividadeLicenciavelDTO.getDados().getRequisitoTecnico().getId());

        List<TipoAtividade> tiposAtividade = new ArrayList<>();

        atividadeLicenciavelDTO.getDados().getTiposAtividade().forEach(tipoAtividade ->
                tiposAtividade.add(tipoAtividadeRepository.findByCodigo(tipoAtividade))
        );

        List<Licenca> licencasAtividade = new ArrayList<>();

        atividadeLicenciavelDTO.getDados().getLicencas().forEach(licenca ->
                licencasAtividade.add(licencaRepository.findById(licenca.getId()).get())
        );

        List<TaxaLicenciamento> taxasLicenciamentoAtividade = taxaLicenciamentoRepository.findByCodigo(
                codigoTaxaLicenciamentoRepository.findById(atividadeLicenciavelDTO.getDados().getTaxaLicenciamento().getId()).get());

        List<PorteAtividade> portesAtividade = porteAtividadeService.editar(atividadeLicenciavelDTO.getParametros());

        Optional<Atividade> atividadeSalva = atividadeRepository.findById(atividadeLicenciavelDTO.getDados().getId());

        Atividade atividadeRascunho = new Atividade();

        if (atividadeSalva.isPresent()) {

            atividadeRascunho = atividadeSalva.get();

            atividadeRascunho.setNome(atividadeLicenciavelDTO.getDados().getNomeAtividade());
            atividadeRascunho.setCodigo(atividadeLicenciavelDTO.getDados().getCodigoAtividade());
            atividadeRascunho.setTipologia(tipologiaAtividade.orElse(null));
            atividadeRascunho.setGeoPonto(atividadeLicenciavelDTO.getDados().getGeoPonto());
            atividadeRascunho.setGeoLinha(atividadeLicenciavelDTO.getDados().getGeoLinha());
            atividadeRascunho.setGeoPoligono(atividadeLicenciavelDTO.getDados().getGeoPoligono());
            atividadeRascunho.setPotencialPoluidor(potencialPoluidor.orElse(null));
            atividadeRascunho.setSiglaSetor(atividadeLicenciavelDTO.getDados().getSetor());
            atividadeRascunho.setDentroEmpreendimento(!atividadeLicenciavelDTO.getDados().getForaEmpreendimento());
            atividadeRascunho.setRequisitoTecnico(requisitoTecnico.orElse(null));
            atividadeRascunho.setV1(false);
            atividadeRascunho.setRascunho(false);
            atividadeRascunho.setAtivo(true);
            atividadeRascunho.setItemAntigo(false);
            atividadeRascunho.setTiposAtividades(tiposAtividade);
            atividadeRascunho.setTiposLicencas(licencasAtividade);
            atividadeRascunho.setTaxasLicenciamento(taxasLicenciamentoAtividade);
            atividadeRascunho.setPortesAtividade(portesAtividade);

            atividadeRepository.save(atividadeRascunho);

            tipoCaracterizacaoAtividadeService.editarAtividadeLicenciavel(atividadeLicenciavelDTO.getCnaesAtividade(), atividadeRascunho);

            relAtividadeParametroAtividadeService.editar(atividadeRascunho, atividadeLicenciavelDTO.getParametros().get(0));

            historicoConfiguradorService.salvar(
                    request,
                    atividadeRascunho.getId(),
                    FuncionalidadeConfigurador.Funcionalidades.ATIVIDADES_LICENCIAVEIS.getTipo(),
                    AcaoConfigurador.Acoes.CADASTRAR.getAcao()
            );

        }

        return atividadeRascunho;

    }

    @Override
    public Atividade salvar(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

        if (atividadeLicenciavelDTO.getDados().getId() != null) {
            return this.editar(request, atividadeLicenciavelDTO);
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
    public Atividade editar(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO) {

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

    @Override
    public void excluir(HttpServletRequest request, Integer idAtividade) {

        Optional<Atividade> atividadeSalva = atividadeRepository.findById(idAtividade);

        if (atividadeSalva.isPresent()) {

            Atividade atividadeRascunho = atividadeSalva.get();

            tipoCaracterizacaoAtividadeService.excluirAtividadeLicenciavel(atividadeRascunho);

            relAtividadeParametroAtividadeService.excluir(atividadeRascunho);

            atividadeRepository.delete(atividadeRascunho);

        }

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

}