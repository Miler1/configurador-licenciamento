package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "rel_atividade_pergunta")
public class RelAtividadePergunta implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_atividade", referencedColumnName = "id")
    private Atividade atividade;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_pergunta", referencedColumnName = "id")
    private Pergunta pergunta;

    @NotNull(message = "{validacao.notnull}")
    private Integer ordem;

    public RelAtividadePergunta(Atividade atividade, Pergunta pergunta, Integer ordem) {
        this.atividade = atividade;
        this.pergunta = pergunta;
        this.ordem = ordem;
    }
}
