package com.configuradorlicenciamento.taxaadministrativa.services;

import com.configuradorlicenciamento.configuracao.exceptions.ConstraintUniqueViolationException;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaCsv;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaDTO;
import com.configuradorlicenciamento.taxaadministrativa.interfaces.ITaxaAdministrativaService;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import com.configuradorlicenciamento.taxaadministrativa.repositories.TaxaAdministrativaRepository;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaxaAdministrativaService implements ITaxaAdministrativaService {

    private static final String TAXA_EXISTENTE = "Já existe uma taxa administrativa com o mesmo ano";

    @Autowired
    TaxaAdministrativaRepository taxaAdministrativaRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public TaxaAdministrativa salvar(HttpServletRequest request, TaxaAdministrativaDTO taxaAdministrativaDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        TaxaAdministrativa taxaAdministrativa = new TaxaAdministrativa.TaxaAdministrativaBuilder(taxaAdministrativaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        boolean existsAno = taxaAdministrativaRepository.existsByAno(taxaAdministrativa.getAno());

        if (existsAno) {
            throw new ConstraintUniqueViolationException(TAXA_EXISTENTE);
        }

        taxaAdministrativaRepository.save(taxaAdministrativa);

        return taxaAdministrativa;

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
