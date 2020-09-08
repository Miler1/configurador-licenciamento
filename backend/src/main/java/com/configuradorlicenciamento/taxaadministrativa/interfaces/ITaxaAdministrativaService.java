package com.configuradorlicenciamento.taxaadministrativa.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaCsv;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaDTO;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ITaxaAdministrativaService {

    TaxaAdministrativa salvar(HttpServletRequest request, TaxaAdministrativaDTO taxaAdministrativaDTO);

    Page<TaxaAdministrativa> listar(Pageable pageable, FiltroPesquisa filtro);

    List<TaxaAdministrativa> listarTaxaAdministrativa();

    List<TaxaAdministrativaCsv> listarTaxaAdministrativaParaCsv();

    TaxaAdministrativa editar(HttpServletRequest request, TaxaAdministrativaDTO taxaAdministrativaDTO);

}