package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.TaxaLicenciamentoDTO;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeDispensavelDTO {

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private List<RelacaoCnaeTipologia> cnaesTipologia;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private List<PerguntaDTO> perguntas;

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RelacaoCnaeTipologia {

        @NotNull(message = "{validacao.notnull}")
        @NotBlank(message = "{validacao.notBlank}")
        private AtividadeCnaeDTO cnae;

        @NotNull(message = "{validacao.notnull}")
        @NotBlank(message = "{validacao.notBlank}")
        private TipologiaDTO tipologia;

        @NotNull(message = "{validacao.notnull}")
        @NotBlank(message = "{validacao.notBlank}")
        private Boolean foraMunicipio;

    }

}
