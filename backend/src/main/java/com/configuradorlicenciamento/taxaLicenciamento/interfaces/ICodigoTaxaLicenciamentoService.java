package com.configuradorlicenciamento.taxaLicenciamento.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICodigoTaxaLicenciamentoService {

    Page<CodigoTaxaLicenciamento> listar(Pageable pageable, FiltroPesquisa filtro);

}