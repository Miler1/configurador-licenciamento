package com.configuradorlicenciamento.atividadeCnae.models;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "atividade_cnae")
public class AtividadeCnae implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String nome;

    @NotNull(message = "{validacao.notnull}")
    private String codigo;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    @OneToMany(mappedBy="atividadeCnae")
    @JsonBackReference
    private List<TipoCaracterizacaoAtividade> tipoCaracterizacaoAtividadeList;

    public AtividadeCnae(AtividadeCnaeBuilder builder) {
        this.nome = builder.nome;
        this.codigo = builder.codigo;
        this.ativo = builder.ativo;
        this.dataCadastro = builder.dataCadastro;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
    }

    public static class AtividadeCnaeBuilder {

        private final String nome;
        private final String codigo;
        private Boolean ativo;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public AtividadeCnaeBuilder(AtividadeCnaeDTO atividadeCnaeDTO) {
            this.nome = atividadeCnaeDTO.getNome();
            this.codigo = atividadeCnaeDTO.getCodigo();
            this.ativo = atividadeCnaeDTO.getAtivo();
        }

        public AtividadeCnaeBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public AtividadeCnaeBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public AtividadeCnae build() {
            return new AtividadeCnae(this);
        }

    }

    public AtividadeCnaeCsv preparaParaCsv() {

        return new AtividadeCnaeCsv(this);
    }

}