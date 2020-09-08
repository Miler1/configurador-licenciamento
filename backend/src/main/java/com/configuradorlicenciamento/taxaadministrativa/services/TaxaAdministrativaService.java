package com.configuradorlicenciamento.taxaadministrativa.services;

import com.configuradorlicenciamento.configuracao.exceptions.ConstraintUniqueViolationException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaCsv;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaDTO;
import com.configuradorlicenciamento.taxaadministrativa.interfaces.ITaxaAdministrativaService;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import com.configuradorlicenciamento.taxaadministrativa.repositories.TaxaAdministrativaRepository;
import com.configuradorlicenciamento.taxaadministrativa.specifications.TaxaAdministrativaSpecification;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaxaAdministrativaService implements ITaxaAdministrativaService {

    private static final String TAXA_EXISTENTE = "Já existe uma taxa administrativa com o mesmo ano.";

    @Autowired
    TaxaAdministrativaRepository taxaAdministrativaRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public TaxaAdministrativa salvar(HttpServletRequest request, TaxaAdministrativaDTO taxaAdministrativaDTO) {

        boolean existsAno = taxaAdministrativaRepository.existsByAno(taxaAdministrativaDTO.getAno());

        if (existsAno) {
            throw new ConstraintUniqueViolationException(TAXA_EXISTENTE);
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
    public Page<TaxaAdministrativa> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<TaxaAdministrativa> specification = preparaFiltro(filtro);

        return taxaAdministrativaRepository.findAll(specification, pageable);
    }

    private Specification<TaxaAdministrativa> preparaFiltro(FiltroPesquisa filtro) {

        Specification<TaxaAdministrativa> specification = Specification.where(TaxaAdministrativaSpecification.padrao());

        if (filtro.getStringPesquisa() != null && !filtro.getStringPesquisa().isEmpty()) {
            Integer ano = Integer.parseInt(filtro.getStringPesquisa());
            specification = specification.and(TaxaAdministrativaSpecification.ano(ano));
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
