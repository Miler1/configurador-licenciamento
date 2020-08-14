package com.configuradorlicenciamento.licenca.dtos;

import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LicencaDTO implements Serializable {

    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String nome;

    @NotBlank(message = "{validacao.notBlank}")
    @Column(nullable = true)
    private Integer validadeEmAnos;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String sigla;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String finalidade;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Boolean ativo;

    private Date dataCadastro;

    private UsuarioLicenciamento usuarioLicenciamento;

}
