package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "tipo_caracterizacao_atividade")
public class TipoCaracterizacaoAtividade implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_atividade", referencedColumnName = "id")
    @JsonBackReference
    private Atividade atividade;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_atividade_cnae", referencedColumnName = "id")
    @JsonManagedReference
    private AtividadeCnae atividadeCnae;

    @NotNull(message = "{validacao.notnull}")
    private Boolean dispensaLicenciamento;

    @NotNull(message = "{validacao.notnull}")
    private Boolean licenciamentoSimplificado;

    @NotNull(message = "{validacao.notnull}")
    private Boolean licenciamentoDeclaratorio;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    public TipoCaracterizacaoAtividade(TipoCaracterizacaoAtividadeBuilder tipoCaracterizacaoAtividadeBuilder) {
        this.atividade = tipoCaracterizacaoAtividadeBuilder.atividade;
        this.atividadeCnae = tipoCaracterizacaoAtividadeBuilder.atividadeCnae;
        this.dispensaLicenciamento = tipoCaracterizacaoAtividadeBuilder.dispensaLicenciamento;
        this.licenciamentoSimplificado = tipoCaracterizacaoAtividadeBuilder.licenciamentoSimplificado;
        this.licenciamentoDeclaratorio = tipoCaracterizacaoAtividadeBuilder.licenciamentoDeclaratorio;
        this.ativo = tipoCaracterizacaoAtividadeBuilder.ativo;
    }

    public static class TipoCaracterizacaoAtividadeBuilder {

        private Atividade atividade;
        private AtividadeCnae atividadeCnae;
        private Boolean dispensaLicenciamento;
        private Boolean licenciamentoSimplificado;
        private Boolean licenciamentoDeclaratorio;
        private Boolean ativo;

        public TipoCaracterizacaoAtividadeBuilder() {
        }

        public TipoCaracterizacaoAtividadeBuilder setAtividade(Atividade atividade) {
            this.atividade = atividade;
            return this;
        }

        public TipoCaracterizacaoAtividadeBuilder setAtividadeCnae(AtividadeCnae atividadeCnae) {
            this.atividadeCnae = atividadeCnae;
            return this;
        }

        public TipoCaracterizacaoAtividadeBuilder setDispensaLicenciamento(Boolean dispensaLicenciamento) {
            this.dispensaLicenciamento = dispensaLicenciamento;
            return this;
        }

        public TipoCaracterizacaoAtividadeBuilder setLicenciamentoSimplificado(Boolean licenciamentoSimplificado) {
            this.licenciamentoSimplificado = licenciamentoSimplificado;
            return this;
        }

        public TipoCaracterizacaoAtividadeBuilder setLicenciamentoDeclaratorio(Boolean licenciamentoDeclaratorio) {
            this.licenciamentoDeclaratorio = licenciamentoDeclaratorio;
            return this;
        }

        public TipoCaracterizacaoAtividadeBuilder setAtivo(Boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        public TipoCaracterizacaoAtividade build() { return new TipoCaracterizacaoAtividade(this); }

    }

}
