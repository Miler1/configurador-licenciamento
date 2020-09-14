package com.configuradorlicenciamento.taxaLicenciamento.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoCsv;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoDTO;
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
@NoArgsConstructor
@Entity
@Table(schema = GlobalReferences.ESQUEMA, name = "codigos_taxa_licenciamento")
public class CodigoTaxaLicenciamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public CodigoTaxaLicenciamento(CodigoTaxaLicenciamento.CodigoTaxaLicenciamentoBuilder builder) {

        this.codigo = builder.codigo;
        this.descricao = builder.descricao;
        this.ativo = builder.ativo;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
        this.dataCadastro = builder.dataCadastro;

    }

    public static class CodigoTaxaLicenciamentoBuilder {

        private String codigo;
        private String descricao;
        private Boolean ativo;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public CodigoTaxaLicenciamentoBuilder(CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO) {

            this.codigo = codigoTaxaLicenciamentoDTO.getCodigo();
            this.descricao = codigoTaxaLicenciamentoDTO.getDescricao();
            this.ativo = codigoTaxaLicenciamentoDTO.getAtivo();

        }

        public CodigoTaxaLicenciamento.CodigoTaxaLicenciamentoBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public CodigoTaxaLicenciamento.CodigoTaxaLicenciamentoBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public CodigoTaxaLicenciamento build() {
            return new CodigoTaxaLicenciamento(this);
        }

    }

    public CodigoTaxaLicenciamentoCsv prepararParaCsv() {
        return new CodigoTaxaLicenciamentoCsv(this);
    }

}
