package com.configuradorlicenciamento.resposta.dtos;

import com.configuradorlicenciamento.resposta.models.Resposta;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaEdicaoDTO implements Serializable {

    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String texto;

    @NotNull(message = "{validacao.notnull}")
    private Boolean permiteLicenciamento;

    private String tipoValidacao;

    private String codigo;

    private Boolean ativo;

    private Date dataCadastro;

    private UsuarioLicenciamento usuarioLicenciamento;

    public RespostaEdicaoDTO(Resposta resposta){
        this.id = resposta.getId();
        this.texto = resposta.getTexto();
        this.permiteLicenciamento = resposta.getPermiteLicenciamento();
        this.tipoValidacao = resposta.getTipoValidacao();
    }

}
