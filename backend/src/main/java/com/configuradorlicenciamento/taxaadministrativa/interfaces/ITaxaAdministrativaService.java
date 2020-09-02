package com.configuradorlicenciamento.taxaadministrativa.interfaces;

import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaDTO;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;

import javax.servlet.http.HttpServletRequest;

public interface ITaxaAdministrativaService {

    TaxaAdministrativa salvar(HttpServletRequest request, TaxaAdministrativaDTO taxaAdministrativaDTO);

}
