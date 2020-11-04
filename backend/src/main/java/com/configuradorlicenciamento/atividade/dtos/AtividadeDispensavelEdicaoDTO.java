package com.configuradorlicenciamento.atividade.dtos;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeEdicaoDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaEdicaoDTO;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaEdicaoDTO;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeDispensavelEdicaoDTO {

    private final Integer id;

    private final List<RelacaoCnaeTipologia> cnaesTipologia;

    private final List<PerguntaEdicaoDTO> perguntas;

    public AtividadeDispensavelEdicaoDTO(
            Integer id, AtividadeCnae atividadeCnae, Tipologia tipologia, Boolean foraMunicipio, List<Pergunta> perguntas) {

        this.id = id;

        this.cnaesTipologia = new ArrayList<>();
        this.cnaesTipologia.add(new AtividadeDispensavelEdicaoDTO.RelacaoCnaeTipologia(atividadeCnae, tipologia, foraMunicipio));

        this.perguntas = new ArrayList<>();
        perguntas.forEach(pergunta -> this.perguntas.add(new PerguntaEdicaoDTO(pergunta)));

    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RelacaoCnaeTipologia {

        private AtividadeCnaeEdicaoDTO cnae;

        private TipologiaEdicaoDTO tipologia;

        private Boolean foraMunicipio;

        public RelacaoCnaeTipologia(AtividadeCnae atividadeCnae, Tipologia tipologia, Boolean foraMunicipio) {

            this.cnae = new AtividadeCnaeEdicaoDTO(atividadeCnae);
            this.tipologia = new TipologiaEdicaoDTO(tipologia);
            this.foraMunicipio = foraMunicipio;

        }

    }

}
