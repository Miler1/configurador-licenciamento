package com.configuradorlicenciamento.taxaLicenciamento.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxaLicenciamentoDTO {

    private Integer idTipoLicenca;

    private Integer idPorteEmpreendimento;

    private Integer idPotencialPoluidor;

    private String valor;

}
