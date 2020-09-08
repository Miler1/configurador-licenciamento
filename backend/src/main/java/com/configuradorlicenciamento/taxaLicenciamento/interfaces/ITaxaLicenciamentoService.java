package com.configuradorlicenciamento.taxaLicenciamento.interfaces;

import com.configuradorlicenciamento.taxaLicenciamento.dtos.TaxaLicenciamentoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;

import java.util.List;

public interface ITaxaLicenciamentoService {
    void salvar(List<TaxaLicenciamentoDTO> taxasLicenciamentoDTO, CodigoTaxaLicenciamento codigoTaxaLicenciamento);
}
