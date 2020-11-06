package com.configuradorlicenciamento.atividade.dtos;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeEdicaoDTO;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeLicenciavelEdicaoDTO {

    private final List<AtividadeCnaeEdicaoDTO> cnaesAtividade;

    private final AtividadeEdicaoDTO dados;

    private final List<PorteAtividadeEdicaoDTO> parametros;

    public AtividadeLicenciavelEdicaoDTO(Atividade atividade, List<TipoCaracterizacaoAtividade> tiposCaracterizacaoAtividade, String descricaoUnidadeUm, String descricaoUnidadeDois) {

        this.cnaesAtividade = new ArrayList<>();

        tiposCaracterizacaoAtividade.forEach(tipoCaracterizacaoAtividade -> {

            this.cnaesAtividade.add(new AtividadeCnaeEdicaoDTO(tipoCaracterizacaoAtividade.getAtividadeCnae()));

        });

        this.dados = new AtividadeEdicaoDTO(atividade);

        this.parametros = new ArrayList<>();

        atividade.getPortesAtividade().forEach(relAtividadeParametroAtividade -> {

            parametros.add(new PorteAtividadeEdicaoDTO(relAtividadeParametroAtividade, descricaoUnidadeUm, descricaoUnidadeDois));

        });

    }

}