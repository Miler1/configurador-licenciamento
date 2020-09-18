package com.configuradorlicenciamento.taxaLicenciamento.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodigoTaxaLicenciamentoDTO {

    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String codigo;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String descricao;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private List<TaxaLicenciamentoDTO> listTaxasLicenciamento;

}
