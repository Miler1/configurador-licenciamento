package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
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
    private Atividade atividade;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_atividade_cnae", referencedColumnName = "id")
    private AtividadeCnae atividadeCnae;

    @NotNull(message = "{validacao.notnull}")
    private Boolean dispensaLicenciamento;

    @NotNull(message = "{validacao.notnull}")
    private Boolean licenciamentoSimplificado;

    @NotNull(message = "{validacao.notnull}")
    private Boolean licenciamentoDeclaratorio;

}
