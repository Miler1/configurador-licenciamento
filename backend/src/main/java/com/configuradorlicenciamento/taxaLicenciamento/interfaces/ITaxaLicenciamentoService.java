package com.configuradorlicenciamento.taxaLicenciamento.interfaces;

import com.configuradorlicenciamento.taxaLicenciamento.dtos.TaxaLicenciamentoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;

import java.util.List;

public interface ITaxaLicenciamentoService {

    void salvar(List<TaxaLicenciamentoDTO> taxasLicenciamentoDTO, CodigoTaxaLicenciamento codigoTaxaLicenciamento);

    void editar(List<TaxaLicenciamentoDTO> taxasLicenciamentoDTO, CodigoTaxaLicenciamento codigoTaxaLicenciamento);

    List<TaxaLicenciamento> findByCodigo(CodigoTaxaLicenciamento codigoTaxaLicenciamento);

}
