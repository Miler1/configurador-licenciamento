package com.configuradorlicenciamento.taxaadministrativa.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxa.models.Taxa;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaDTO;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface ITaxaAdministrativaService {

    TaxaAdministrativa salvar(HttpServletRequest request, TaxaAdministrativaDTO taxaAdministrativaDTO);

    Page<TaxaAdministrativa> listar(Pageable pageable, FiltroPesquisa filtro);

}
