package com.configuradorlicenciamento.atividadeCnae.dtos;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeCnaeEdicaoDTO {

    private Integer id;

    private String nome;

    private String codigo;

    private Boolean ativo;

    public AtividadeCnaeEdicaoDTO(AtividadeCnae atividadeCnae) {

        this.id = atividadeCnae.getId();
        this.nome = atividadeCnae.getNome();
        this.codigo = atividadeCnae.getCodigo();
        this.ativo = atividadeCnae.getAtivo();

    }

}