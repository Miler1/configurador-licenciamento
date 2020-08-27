package com.configuradorlicenciamento.requisitotecnico.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisitoTecnicoDTO {

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
    private List<TipoLicencaGrupoDocumentoDTO> listRequisitos;

}
