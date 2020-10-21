package com.configuradorlicenciamento.atividade.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelAtividadeParametroAtividadeDTO {

    private Integer idAtividade;

    private Integer idParametro;

    private String descricaoUnidade;

}
