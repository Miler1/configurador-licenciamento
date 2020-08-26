package com.configuradorlicenciamento.requisitotecnico.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoLicencaGrupoDocumentoDTO {

//    @NotNull(message = "{validacao.notnull}")
//    @NotBlank(message = "{validacao.notBlank}")
    private Integer idTipoLicenca;

//    @NotNull(message = "{validacao.notnull}")
//    @NotBlank(message = "{validacao.notBlank}")
    private Integer idTipoDocumento;

//    @NotNull(message = "{validacao.notnull}")
//    @NotBlank(message = "{validacao.notBlank}")
    private Boolean obrigatorio;

}
