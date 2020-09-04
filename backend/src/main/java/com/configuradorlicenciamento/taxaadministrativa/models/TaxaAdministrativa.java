package com.configuradorlicenciamento.taxaadministrativa.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaCsv;
import com.configuradorlicenciamento.taxaadministrativa.dtos.TaxaAdministrativaDTO;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "taxa_administrativa_dae")
public class TaxaAdministrativa implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @Column(unique = true, name="ano")
    private Integer ano;

    @NotNull(message = "{validacao.notnull}")
    private Float valor;

    @NotNull(message = "{validacao.notnull}")
    private Boolean atividadeDispensavel;

    @NotNull(message = "{validacao.notnull}")
    private Boolean atividadeLicenciavel;

    @NotNull(message = "{validacao.notnull}")
    private String linkTaxasLicenciamento;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public TaxaAdministrativa(TaxaAdministrativaBuilder taxaAdministrativaBuilder) {

        this.ano = taxaAdministrativaBuilder.ano;
        this.valor = taxaAdministrativaBuilder.valor;
        this.atividadeDispensavel = taxaAdministrativaBuilder.atividadeDispensavel;
        this.atividadeLicenciavel = taxaAdministrativaBuilder.atividadeLicenciavel;
        this.linkTaxasLicenciamento = "";
        this.ativo = taxaAdministrativaBuilder.ativo;
        this.dataCadastro = taxaAdministrativaBuilder.dataCadastro;
        this.usuarioLicenciamento = taxaAdministrativaBuilder.usuarioLicenciamento;

    }

    public static class TaxaAdministrativaBuilder {

        private Integer ano;
        private Float valor;
        private Boolean atividadeDispensavel;
        private Boolean atividadeLicenciavel;
        private Boolean ativo;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public TaxaAdministrativaBuilder(TaxaAdministrativaDTO taxaAdministrativaDTO) {
            this.ano = taxaAdministrativaDTO.getAno();
            this.valor = taxaAdministrativaDTO.getValor();
            this.atividadeDispensavel = taxaAdministrativaDTO.getAtividadeDispensavel();
            this.atividadeLicenciavel = taxaAdministrativaDTO.getAtividadeLicenciavel();
            this.ativo = taxaAdministrativaDTO.getAtivo();
        }

        public TaxaAdministrativa.TaxaAdministrativaBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public TaxaAdministrativa.TaxaAdministrativaBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public TaxaAdministrativa build() { return new TaxaAdministrativa(this); }

    }

    public TaxaAdministrativaCsv prepararParaCsv() {
        return new TaxaAdministrativaCsv(this);
    }
}
