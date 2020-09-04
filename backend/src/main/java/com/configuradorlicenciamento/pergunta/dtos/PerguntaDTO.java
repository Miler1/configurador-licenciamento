package com.configuradorlicenciamento.pergunta.dtos;

import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import resposta.dtos.RespostaDTO;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerguntaDTO implements Serializable {

    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String texto;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String codigo;

    private String ordem;

    private Integer tipoLocalizacaoEmpreendimento;

    @NotNull(message = "{validacao.notnull}")
    private String tipoPergunta;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Boolean ativo;

    private Date dataCadastro;

    private List<RespostaDTO> respostas;

    private UsuarioLicenciamento usuarioLicenciamento;

}
