package com.configuradorlicenciamento.requisitoTecnico.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoLicencaGrupoDocumentoDTO {

    private Integer idTipoLicenca;

    private Integer idTipoDocumento;

    private Boolean obrigatorio;

}
