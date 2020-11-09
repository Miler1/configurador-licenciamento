package com.configuradorlicenciamento.atividade.dtos;

import com.configuradorlicenciamento.atividade.models.PorteAtividade;
import com.configuradorlicenciamento.parametro.models.Parametro;
import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PorteAtividadeEdicaoDTO {

    private final Integer id;
    private final Float limiteInferiorUm;
    private final Float limiteSuperiorUm;
    private final Float limiteInferiorDois;
    private final Float limiteSuperiorDois;
    private final Parametro parametroUm;
    private final Parametro parametroDois;
    private final Boolean limiteInferiorUmIncluso;
    private final Boolean limiteSuperiorUmIncluso;
    private final Boolean limiteInferiorDoisIncluso;
    private final Boolean limiteSuperiorDoisIncluso;
    private final PorteEmpreendimento porte;
    private final String descricaoUnidadeUm;
    private final String descricaoUnidadeDois;

    public PorteAtividadeEdicaoDTO(PorteAtividade porteAtividade, String descricaoUnidadeUm, String descricaoUnidadeDois) {

        this.id = porteAtividade.getId();
        this.limiteInferiorUm = porteAtividade.getLimiteInferiorUm();
        this.limiteSuperiorUm = porteAtividade.getLimiteSuperiorUm();
        this.limiteInferiorDois = porteAtividade.getLimiteInferiorDois();
        this.limiteSuperiorDois = porteAtividade.getLimiteSuperiorDois();
        this.parametroUm = porteAtividade.getParametroUm();
        this.parametroDois = porteAtividade.getParametroDois();
        this.limiteInferiorUmIncluso = porteAtividade.getLimiteInferiorUmIncluso();
        this.limiteSuperiorUmIncluso = porteAtividade.getLimiteSuperiorUmIncluso();
        this.limiteInferiorDoisIncluso = porteAtividade.getLimiteInferiorDoisIncluso();
        this.limiteSuperiorDoisIncluso = porteAtividade.getLimiteSuperiorDoisIncluso();
        this.porte = porteAtividade.getPorteEmpreendimento();
        this.descricaoUnidadeUm = descricaoUnidadeUm;
        this.descricaoUnidadeDois = descricaoUnidadeDois;
    }

}