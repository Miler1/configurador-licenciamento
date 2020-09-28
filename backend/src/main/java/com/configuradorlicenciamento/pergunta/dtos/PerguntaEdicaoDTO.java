package com.configuradorlicenciamento.pergunta.dtos;

import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerguntaEdicaoDTO {

    private Integer id;

    private String texto;

    private String codigo;

    private Boolean ativo;

    public PerguntaEdicaoDTO(Pergunta pergunta) {

        this.id = pergunta.getId();
        this.texto = pergunta.getTexto();
        this.codigo = pergunta.getCodigo();
        this.ativo = pergunta.getAtivo();

    }

}