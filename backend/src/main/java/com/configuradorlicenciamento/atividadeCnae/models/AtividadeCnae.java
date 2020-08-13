package com.configuradorlicenciamento.atividadeCnae.models;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
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

    public AtividadeCnae(AtividadeCnaeBuilder builder) {
        this.nome = builder.nome;
        this.codigo = builder.codigo;
        this.ativo = builder.ativo;
        this.dataCadastro = builder.dataCadastro;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
    }

    public static class AtividadeCnaeBuilder {

        private Integer id;
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

        public AtividadeCnaeBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public AtividadeCnaeBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public AtividadeCnaeBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public AtividadeCnae build() { return new AtividadeCnae(this); }

    }

    public AtividadeCnaeCsv preparaParaCsv() {

        AtividadeCnaeCsv dto = new AtividadeCnaeCsv(this);
        return dto;
    }
}
