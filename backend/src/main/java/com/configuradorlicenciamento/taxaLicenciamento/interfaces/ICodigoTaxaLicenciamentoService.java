package com.configuradorlicenciamento.taxaLicenciamento.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;


public interface ICodigoTaxaLicenciamentoService {

    CodigoTaxaLicenciamento salvar(HttpServletRequest request, CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO);

    Page<CodigoTaxaLicenciamento> listar(Pageable pageable, FiltroPesquisa filtro);

}