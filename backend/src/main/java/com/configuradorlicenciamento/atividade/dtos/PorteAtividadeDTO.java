package com.configuradorlicenciamento.atividade.dtos;

import com.configuradorlicenciamento.parametro.dtos.ParametroDTO;
import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PorteAtividadeDTO {

    private Integer id;
    private Float limiteInferiorUm;
    private Float limiteSuperiorUm;
    private Float limiteInferiorDois;
    private Float limiteSuperiorDois;
    private ParametroDTO parametroUm;
    private ParametroDTO parametroDois;
    private Boolean limiteInferiorUmIncluso;
    private Boolean limiteSuperiorUmIncluso;
    private Boolean limiteInferiorDoisIncluso;
    private Boolean limiteSuperiorDoisIncluso;
    private PorteEmpreendimento porte;
    private Boolean licenciamentoMunicipal;
    private Boolean repasseOutroOrgao;
    private String descricaoUnidadeUm;
    private String descricaoUnidadeDois;

}
