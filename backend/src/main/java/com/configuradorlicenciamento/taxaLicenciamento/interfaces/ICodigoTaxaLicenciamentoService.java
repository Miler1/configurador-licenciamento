package com.configuradorlicenciamento.taxaLicenciamento.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoCsv;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaCsv;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ICodigoTaxaLicenciamentoService {

    Page<CodigoTaxaLicenciamento> listar(Pageable pageable, FiltroPesquisa filtro);

    List<CodigoTaxaLicenciamento> listarCodigoTaxaLicenciamento();

    List<CodigoTaxaLicenciamentoCsv> listarCodigoTaxaLicenciamentoParaCsv();

}