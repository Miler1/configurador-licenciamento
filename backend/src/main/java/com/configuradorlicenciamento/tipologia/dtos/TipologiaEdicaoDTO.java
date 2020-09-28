package com.configuradorlicenciamento.tipologia.dtos;

import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipologiaEdicaoDTO {

    private Integer id;

    private String nome;

    private Boolean ativo;

    public TipologiaEdicaoDTO(Tipologia tipologia) {

        this.id = tipologia.getId();
        this.nome = tipologia.getNome();
        this.ativo = tipologia.getAtivo();

    }

}