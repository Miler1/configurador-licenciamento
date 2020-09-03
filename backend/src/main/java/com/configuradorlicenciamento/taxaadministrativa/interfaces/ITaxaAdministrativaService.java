package com.configuradorlicenciamento.taxaadministrativa.interfaces;

import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaCsv;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaDTO;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ITaxaAdministrativaService {

    TaxaAdministrativa salvar(HttpServletRequest request, TaxaAdministrativaDTO taxaAdministrativaDTO);

    List<TaxaAdministrativa> listarTaxaAdministrativa();

    List<TaxaAdministrativaCsv> listarTaxaAdministrativaParaCsv();

}
