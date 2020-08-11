package com.configuradorlicenciamento.licenca.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LicencaDTO implements Serializable {

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String id;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String tipo;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String nomenclatura;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Boolean podeRenovar;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Boolean naoExpira;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String descricao;

}
