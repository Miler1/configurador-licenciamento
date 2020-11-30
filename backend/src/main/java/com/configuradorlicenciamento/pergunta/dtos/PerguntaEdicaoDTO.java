package com.configuradorlicenciamento.pergunta.dtos;

import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.resposta.dtos.RespostaDTO;
import com.configuradorlicenciamento.resposta.dtos.RespostaEdicaoDTO;
import com.configuradorlicenciamento.resposta.models.Resposta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerguntaEdicaoDTO {

    private Integer id;

    private String texto;

    private String codigo;

    private Boolean ativo;

    private Integer ordem;

    private List<RespostaEdicaoDTO> respostas;

    public PerguntaEdicaoDTO(Pergunta pergunta) {

        this.id = pergunta.getId();
        this.texto = pergunta.getTexto();
        this.codigo = pergunta.getCodigo();
        this.ativo = pergunta.getAtivo();
        this.ordem = pergunta.getOrdem();

        this.respostas = new ArrayList<>();

        setRespostas(pergunta.getRespostas());

    }

    public void setRespostas(List<Resposta> respostas) {
        respostas.forEach(resposta -> this.respostas.add(new RespostaEdicaoDTO(resposta)));
    }

}