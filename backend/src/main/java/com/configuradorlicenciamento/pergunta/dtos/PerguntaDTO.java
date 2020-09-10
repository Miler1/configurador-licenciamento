package com.configuradorlicenciamento.pergunta.dtos;

import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import com.configuradorlicenciamento.resposta.dtos.RespostaDTO;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerguntaDTO implements Serializable {

    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String texto;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String codigo;

    private Integer ordem;

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
