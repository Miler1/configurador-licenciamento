package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public AtividadeDispensavelCsv preparaAtividadeDispensavelParaCsv() {

        return new AtividadeDispensavelCsv(this);
    }

}
