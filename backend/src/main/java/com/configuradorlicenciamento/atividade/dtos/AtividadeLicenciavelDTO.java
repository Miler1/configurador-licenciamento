package com.configuradorlicenciamento.atividade.dtos;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeLicenciavelDTO {

    private List<AtividadeCnaeDTO> cnaesAtividade;

    private AtividadeDTO dados;

    private List<PorteAtividadeDTO> parametros;

}