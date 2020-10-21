package com.configuradorlicenciamento.atividade.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.parametro.models.Parametro;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "rel_atividade_parametro_atividade")
public class RelAtividadeParametroAtividade implements Serializable {

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
    @JoinColumn(name = "id_parametro_atividade", referencedColumnName = "id")
    private Parametro parametro;

    @NotNull(message = "{validacao.notnull}")
    private String descricaoUnidade;

    public RelAtividadeParametroAtividade(RelAtividadeParametroAtividade.RelAtividadeParametroAtividadeBuilder builder) {
        this.atividade = builder.atividade;
        this.parametro = builder.parametro;
        this.descricaoUnidade = builder.descricaoUnidade;
    }

    public static class RelAtividadeParametroAtividadeBuilder {
        private Atividade atividade;
        private Parametro parametro;
        private String descricaoUnidade;

        public RelAtividadeParametroAtividadeBuilder(String descricaoUnidade) {
            this.descricaoUnidade = descricaoUnidade;
        }

        public RelAtividadeParametroAtividade.RelAtividadeParametroAtividadeBuilder setAtividade(Atividade atividade) {
            this.atividade = atividade;
            return this;
        }

        public RelAtividadeParametroAtividade.RelAtividadeParametroAtividadeBuilder setParametro(Parametro parametro) {
            this.parametro = parametro;
            return this;
        }

        public RelAtividadeParametroAtividade build(){return new RelAtividadeParametroAtividade(this);}

    }
}
