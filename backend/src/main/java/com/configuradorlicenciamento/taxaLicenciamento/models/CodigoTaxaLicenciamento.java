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

    public CodigoTaxaLicenciamento(CodigoTaxaLicenciamento.CodigoTaxaLicenciamentoBuilder builder) {

        this.codigo = builder.codigo;
        this.descricao = builder.descricao;
        this.ativo = builder.ativo;

    }

    public static class CodigoTaxaLicenciamentoBuilder {

        private String codigo;
        private String descricao;
        private Boolean ativo;

        public CodigoTaxaLicenciamentoBuilder(CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO) {

            this.codigo = codigoTaxaLicenciamentoDTO.getCodigo();
            this.descricao = codigoTaxaLicenciamentoDTO.getDescricao();
            this.ativo = codigoTaxaLicenciamentoDTO.getAtivo();

        }

        public CodigoTaxaLicenciamento build() {
            return new CodigoTaxaLicenciamento(this);
        }

    }

    public CodigoTaxaLicenciamentoCsv prepararParaCsv() {
        return new CodigoTaxaLicenciamentoCsv(this);
    }

}
