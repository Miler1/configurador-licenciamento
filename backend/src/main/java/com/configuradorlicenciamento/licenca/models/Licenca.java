package com.configuradorlicenciamento.licenca.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.licenca.dtos.LicencaDTO;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "tipo_licenca")
public class Licenca implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String sigla;

    @NotNull(message = "{validacao.notnull}")
    private String nome;

    @NotNull(message = "{validacao.notnull}")
    private Integer validadeEmAnos;

    @NotNull(message = "{validacao.notnull}")
    private String finalidade;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public Licenca(Licenca.LicencaBuilder builder) {
        this.nome = builder.nomenclatura;
        this.validadeEmAnos = builder.validade;
        this.sigla = builder.sigla;
        this.finalidade = builder.finalidade;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
        this.dataCadastro = builder.dataCadastro;
    }

    public static class LicencaBuilder {
        private String sigla;
        private String nomenclatura;
        private Integer validade;
        private String finalidade;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public LicencaBuilder(LicencaDTO licencaDTO) {
            this.sigla = licencaDTO.getSigla();
            this.nomenclatura = licencaDTO.getNome();
            this.validade = licencaDTO.getValidade();
            this.finalidade = licencaDTO.getFinalidade();
        }

        public LicencaBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public LicencaBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public Licenca build() { return new Licenca(this); }
    }
}
