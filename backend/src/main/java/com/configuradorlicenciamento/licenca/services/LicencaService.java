package com.configuradorlicenciamento.licenca.services;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.licenca.dtos.LicencaCsv;
import com.configuradorlicenciamento.licenca.dtos.LicencaDTO;
import com.configuradorlicenciamento.licenca.interfaces.ILicencaService;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.licenca.repositories.LicencaRepository;
import com.configuradorlicenciamento.licenca.specifications.LicencaSpecification;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import com.configuradorlicenciamento.requisitoAdministrativo.repositories.RequisitoAdministrativoRepository;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisitoTecnico.models.TipoLicencaGrupoDocumento;
import com.configuradorlicenciamento.requisitoTecnico.repositories.RequisitoTecnicoRepository;
import com.configuradorlicenciamento.requisitoTecnico.repositories.TipoLicencaGrupoDocumentoRepository;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.repositories.CodigoTaxaLicenciamentoRepository;
import com.configuradorlicenciamento.taxaLicenciamento.repositories.TaxaLicenciamentoRepository;
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
public class LicencaService implements ILicencaService {

    private static final String LICENCA_EXISTENTE = "Já existe uma licença com o mesmo tipo.";
    private static final String VINCULO_TAXA_LICENCIAMENTO = "Erro! Não foi possível desativar/ativar a licença. Ela se encontra vinculada a uma tabela de taxas de licenciamento ativa no sistema.";
    private static final String VINCULO_REQUISITO_ADMINISTRATIVO = "Erro! Não foi possível desativar/ativar a licença. Ela se encontra vinculada a um requisito administrativo ativo no sistema.";
    private static final String VINCULO_REQUISITO_TECNICO = "Erro! Não foi possível desativar/ativar a licença. Ela se encontra vinculada a um grupo de requisito técnico ativo no sistema.";
    private static final String VINCULO_ATIVIDADE_LICENCIAVEL = "Erro! Não foi possível desativar/ativar a licença. Ela se encontra vinculada a uma atividade licenciável ativa no sistema.";

    @Autowired
    LicencaRepository licencaRepository;

    @Autowired
    TaxaLicenciamentoRepository taxaLicenciamentoRepository;

    @Autowired
    RequisitoAdministrativoRepository requisitoAdministrativoRepository;

    @Autowired
    RequisitoTecnicoRepository requisitoTecnicoRepository;

    @Autowired
    TipoLicencaGrupoDocumentoRepository tipoLicencaGrupoDocumentoRepository;

    @Autowired
    CodigoTaxaLicenciamentoRepository codigoTaxaLicenciamentoRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public Licenca salvar(HttpServletRequest request, LicencaDTO licencaDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        licencaDTO.setSigla(StringUtil.tratarEspacos(licencaDTO.getSigla()));
        boolean existeSigla = licencaRepository.existsBySigla(licencaDTO.getSigla());

        if (existeSigla) {
            throw new ConflictException(LICENCA_EXISTENTE);
        }

        Licenca licenca = new Licenca.LicencaBuilder(licencaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        licencaRepository.save(licenca);

        return licenca;

    }

    @Override
    public Licenca editar(HttpServletRequest request, LicencaDTO licencaDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        licencaDTO.setSigla(StringUtil.tratarEspacos(licencaDTO.getSigla()));
        String sigla = licencaDTO.getSigla();

        boolean existeSigla = licencaRepository.existsBySigla(sigla);

        if (existeSigla) {

            Licenca licencaExistente = licencaRepository.findBySigla(sigla);

            if (licencaExistente != null && !licencaDTO.getId().equals(licencaExistente.getId())) {
                throw new ConflictException(LICENCA_EXISTENTE);
            }

        }

        Optional<Licenca> licencaSalva = licencaRepository.findById(licencaDTO.getId())
                .map(licenca -> {
                    licenca.setSigla(licencaDTO.getSigla());
                    licenca.setNome(licencaDTO.getNome());
                    licenca.setFinalidade(licencaDTO.getFinalidade());
                    licenca.setValidadeEmAnos(licencaDTO.getValidadeEmAnos());
                    licenca.setUsuarioLicenciamento(usuarioLicenciamento);
                    licenca.setDataCadastro(new Date());
                    licenca.setAtivo(licencaDTO.getAtivo());
                    return licenca;
                });

        licencaRepository.save(licencaSalva.get());

        return licencaSalva.get();

    }

    @Override
    public Licenca ativarDesativar(HttpServletRequest request, Integer idLicenca) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Licenca licencaExistente = licencaRepository.findById(idLicenca).get();

        List<TaxaLicenciamento> taxaLicenciamentoList = taxaLicenciamentoRepository.findByLicenca(licencaExistente);

        taxaLicenciamentoList.forEach(taxaLicenciamento -> {

            CodigoTaxaLicenciamento codigoTaxaLicenciamento = codigoTaxaLicenciamentoRepository.findById(taxaLicenciamento.getCodigo().getId()).get();

            boolean ativo = codigoTaxaLicenciamento.getAtivo();

            if (ativo) {
                throw new ConflictException(VINCULO_TAXA_LICENCIAMENTO);
            }

        });

        List<RequisitoAdministrativo> requisitoAdministrativoList = requisitoAdministrativoRepository.findByLicenca(licencaExistente);

        requisitoAdministrativoList.forEach(requisitoAdministrativo -> {

            boolean ativo = requisitoAdministrativo.getAtivo();

            if (ativo) {
                throw new ConflictException(VINCULO_REQUISITO_ADMINISTRATIVO);
            }

        });

        List<TipoLicencaGrupoDocumento> tipoLicencaGrupoDocumentoList = tipoLicencaGrupoDocumentoRepository.findByLicenca(licencaExistente);

        tipoLicencaGrupoDocumentoList.forEach(tipoLicencaGrupoDocumento -> {

            RequisitoTecnico requisitoTecnico = requisitoTecnicoRepository.findById(tipoLicencaGrupoDocumento.getRequisitoTecnico().getId()).get();

            boolean ativo = requisitoTecnico.getAtivo();

            if (ativo) {
                throw new ConflictException(VINCULO_REQUISITO_TECNICO);
            }

        });

        List<Atividade> atividadesList = atividadeRepository.findByTiposLicencas(licencaExistente);

        atividadesList.forEach(atividadeBusca -> {

            Atividade atividade = atividadeRepository.findById(atividadeBusca.getId()).get();

            String codigo = atividade.getCodigo();

            boolean ativo = atividade.getAtivo();

            if (ativo && !codigo.equals("0000")) {
                throw new ConflictException(VINCULO_ATIVIDADE_LICENCIAVEL);
            }

        });

        Optional<Licenca> licencaSalva = licencaRepository.findById(idLicenca)
                .map(licenca -> {
                    licenca.setAtivo(!licencaExistente.getAtivo());
                    licenca.setUsuarioLicenciamento(usuarioLicenciamento);
                    licenca.setDataCadastro(new Date());
                    return licenca;
                });

        licencaRepository.save(licencaSalva.get());

        return licencaSalva.get();

    }

    public Page<Licenca> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Licenca> specification = preparaFiltro(filtro);

        return licencaRepository.findAll(specification, pageable);

    }

    private Specification<Licenca> preparaFiltro(FiltroPesquisa filtro) {

        Specification<Licenca> specification = Specification.where(LicencaSpecification.padrao());

        if (filtro.getStringPesquisa() != null) {
            specification = specification.and(LicencaSpecification.nome(filtro.getStringPesquisa())
                    .or(LicencaSpecification.sigla(filtro.getStringPesquisa())));
        }

        return specification;

    }

    @Override
    public List<Licenca> listarLicencas() {
        return licencaRepository.findAll(Sort.by("sigla"));
    }

    @Override
    public List<Licenca> findAll() {
        return licencaRepository.findAll();
    }

    @Override
    public List<Licenca> findAllByAtivos() {
        return licencaRepository.findByAtivo(true, Sort.by("sigla"));
    }

    @Override
    public List<LicencaCsv> listarLicencasParaCsv() {

        List<Licenca> licencas = listarLicencas();
        List<LicencaCsv> dtos = new ArrayList<>();

        for (Licenca licenca : licencas) {
            dtos.add(licenca.preparaParaCsv());
        }

        return dtos;

    }

}