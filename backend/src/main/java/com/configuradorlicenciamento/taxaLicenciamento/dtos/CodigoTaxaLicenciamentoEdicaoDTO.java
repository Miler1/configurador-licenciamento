package com.configuradorlicenciamento.taxaLicenciamento.dtos;

import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodigoTaxaLicenciamentoEdicaoDTO {

        private String codigo;
        private String descricao;
        private Boolean ativo;
        private final List<TaxaLicenciamento> taxasLicenciamento;

        public CodigoTaxaLicenciamentoEdicaoDTO(CodigoTaxaLicenciamento codigoTaxaLicenciamento, List<TaxaLicenciamento> taxasLicenciamento) {

            this.codigo = codigoTaxaLicenciamento.getCodigo();
            this.descricao = codigoTaxaLicenciamento.getDescricao();
            this.ativo = codigoTaxaLicenciamento.getAtivo();
            this.taxasLicenciamento = taxasLicenciamento;

        }

}