package com.configuradorlicenciamento.requisitoAdministrativo.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.requisitoAdministrativo.dtos.RequisitoAdministrativoDTO;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "tipo_documento_tipo_licenca")
public class RequisitoAdministrativo implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String tipoPessoa;

    @NotNull(message = "{validacao.notnull}")
    private Boolean obrigatorio;

    @NotNull(message = "{validacao.notnull}")
    private Documento documento;

    @NotNull(message = "{validacao.notnull}")
    private Licenca licenca;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public RequisitoAdministrativo(RequisitoAdministrativo.RequisitoAdministrativoBuilder builder) {
        this.documento = builder.documento;
        this.licenca = builder.licenca;
        this.tipoPessoa = builder.tipoPessoa;
        this.obrigatorio = builder.obrigatorio;
        this.dataCadastro = builder.dataCadastro;
        this.ativo = builder.ativo;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
    }

    public static class RequisitoAdministrativoBuilder {

        private Documento documento;
        private Licenca licenca;
        private String tipoPessoa;
        private Boolean obrigatorio;
        private Date dataCadastro;
        private Boolean ativo;
        private UsuarioLicenciamento usuarioLicenciamento;

        public RequisitoAdministrativoBuilder(RequisitoAdministrativoDTO requisitoAdministrativoDTO) {
            this.documento = requisitoAdministrativoDTO.getDocumento();
            this.licenca = requisitoAdministrativoDTO.getLicenca();
            this.obrigatorio = requisitoAdministrativoDTO.getObrigatorio();
            this.tipoPessoa = requisitoAdministrativoDTO.getTipoPessoa();

        }

        public RequisitoAdministrativo.RequisitoAdministrativoBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public RequisitoAdministrativo.RequisitoAdministrativoBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public RequisitoAdministrativo build() { return new RequisitoAdministrativo(this); }

    }
}
