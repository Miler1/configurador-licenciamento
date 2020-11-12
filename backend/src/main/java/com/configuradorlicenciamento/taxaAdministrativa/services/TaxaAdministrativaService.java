package com.configuradorlicenciamento.taxaAdministrativa.services;

import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxaAdministrativa.dtos.TaxaAdministrativaCsv;
import com.configuradorlicenciamento.taxaAdministrativa.dtos.TaxaAdministrativaDTO;
import com.configuradorlicenciamento.taxaAdministrativa.interfaces.ITaxaAdministrativaService;
import com.configuradorlicenciamento.taxaAdministrativa.models.TaxaAdministrativa;
import com.configuradorlicenciamento.taxaAdministrativa.repositories.TaxaAdministrativaRepository;
import com.configuradorlicenciamento.taxaAdministrativa.specifications.TaxaAdministrativaSpecification;
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
public class TaxaAdministrativaService implements ITaxaAdministrativaService {

    private static final String TAXA_EXISTENTE = "Já existe uma taxa administrativa com o mesmo ano.";

    @Autowired
    TaxaAdministrativaRepository taxaAdministrativaRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public TaxaAdministrativa salvar(HttpServletRequest request, TaxaAdministrativaDTO taxaAdministrativaDTO) {

        boolean existeAno = taxaAdministrativaRepository.existsByAno(taxaAdministrativaDTO.getAno());

        if (existeAno) {
            throw new ConflictException(TAXA_EXISTENTE);
        }

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        TaxaAdministrativa taxaAdministrativa = new TaxaAdministrativa.TaxaAdministrativaBuilder(taxaAdministrativaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        taxaAdministrativaRepository.save(taxaAdministrativa);

        return taxaAdministrativa;

    }

    @Override
    public TaxaAdministrativa editar(HttpServletRequest request, TaxaAdministrativaDTO taxaAdministrativaDTO) {

        Integer ano = taxaAdministrativaDTO.getAno();

        boolean existeAno = taxaAdministrativaRepository.existsByAno(ano);

        if (existeAno) {

            TaxaAdministrativa taxaExistente = taxaAdministrativaRepository.findByAno(ano);

            if (taxaExistente != null && !taxaAdministrativaDTO.getId().equals(taxaExistente.getId())) {
                throw new ConflictException(TAXA_EXISTENTE);
            }

        }

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<TaxaAdministrativa> taxaAdministrativaSalva = taxaAdministrativaRepository.findById(taxaAdministrativaDTO.getId())
                .map(taxaAdministrativa -> {
                    taxaAdministrativa.setAno(taxaAdministrativaDTO.getAno());
                    taxaAdministrativa.setIsento(taxaAdministrativaDTO.getIsento());
                    taxaAdministrativa.setValor(taxaAdministrativaDTO.getValor());
                    taxaAdministrativa.setAtividadeDispensavel(taxaAdministrativaDTO.getAtividadeDispensavel());
                    taxaAdministrativa.setAtividadeLicenciavel(taxaAdministrativaDTO.getAtividadeLicenciavel());
                    taxaAdministrativa.setAtivo(taxaAdministrativaDTO.getAtivo());
                    taxaAdministrativa.setUsuarioLicenciamento(usuarioLicenciamento);
                    taxaAdministrativa.setDataCadastro(new Date());
                    return taxaAdministrativa;
                });

        taxaAdministrativaRepository.save(taxaAdministrativaSalva.get());

        return taxaAdministrativaSalva.get();

    }

    @Override
    public TaxaAdministrativa ativarDesativar(HttpServletRequest request, Integer idTaxaAdministrativa) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        TaxaAdministrativa taxaAdministrativaExistente = taxaAdministrativaRepository.findById(idTaxaAdministrativa).get();

        Optional<TaxaAdministrativa> taxaAdministrativaSalva = taxaAdministrativaRepository.findById(idTaxaAdministrativa)
                .map(taxaAdministrativa -> {
                    taxaAdministrativa.setAtivo(!taxaAdministrativaExistente.getAtivo());
                    taxaAdministrativa.setUsuarioLicenciamento(usuarioLicenciamento);
                    taxaAdministrativa.setDataCadastro(new Date());
                    return taxaAdministrativa;
                });

        taxaAdministrativaRepository.save(taxaAdministrativaSalva.get());

        return taxaAdministrativaSalva.get();

    }

    @Override
    public Page<TaxaAdministrativa> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<TaxaAdministrativa> specification = preparaFiltro(filtro);

        return taxaAdministrativaRepository.findAll(specification, pageable);
    }

    private Specification<TaxaAdministrativa> preparaFiltro(FiltroPesquisa filtro) {

        Specification<TaxaAdministrativa> specification = Specification.where(TaxaAdministrativaSpecification.padrao());

        if (filtro.getStringPesquisa() != null && !filtro.getStringPesquisa().isEmpty()) {
            specification = specification.and(TaxaAdministrativaSpecification.ano(filtro.getStringPesquisa()));
        }

        return specification;

    }

    @Override
    public List<TaxaAdministrativa> listarTaxaAdministrativa() {
        return taxaAdministrativaRepository.findAll(Sort.by("ano"));
    }

    @Override
    public List<TaxaAdministrativaCsv> listarTaxaAdministrativaParaCsv() {

        List<TaxaAdministrativa> taxas = listarTaxaAdministrativa();
        List<TaxaAdministrativaCsv> dtos = new ArrayList<>();

        for (TaxaAdministrativa taxaAdministrativa : taxas) {
            dtos.add(taxaAdministrativa.prepararParaCsv());
        }

        return dtos;

    }

}