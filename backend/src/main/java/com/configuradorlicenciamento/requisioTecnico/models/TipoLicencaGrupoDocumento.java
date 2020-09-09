package com.configuradorlicenciamento.requisioTecnico.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.requisioTecnico.dtos.TipoLicencaGrupoDocumentoDTO;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "rel_tipo_licenca_grupo_documento")
public class TipoLicencaGrupoDocumento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_grupo_documento", referencedColumnName = "id")
    private RequisitoTecnico requisitoTecnico;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_tipo_licenca", referencedColumnName = "id")
    private Licenca licenca;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id")
    private Documento documento;

    @NotNull(message = "{validacao.notnull}")
    private Boolean obrigatorio;

    public TipoLicencaGrupoDocumento(TipoLicencaGrupoDocumento.TipoLicencaGrupoDocumentoBuilder builder) {
        this.requisitoTecnico = builder.requisitoTecnico;
        this.licenca = builder.licenca;
        this.documento = builder.documento;
        this.obrigatorio = builder.obrigatorio;
    }

    public static class TipoLicencaGrupoDocumentoBuilder {

        private RequisitoTecnico requisitoTecnico;
        private Licenca licenca;
        private Documento documento;
        private final Boolean obrigatorio;

        public TipoLicencaGrupoDocumentoBuilder(TipoLicencaGrupoDocumentoDTO tipoLicencaGrupoDocumentoDTO) {
            this.obrigatorio = tipoLicencaGrupoDocumentoDTO.getObrigatorio();
        }

        public TipoLicencaGrupoDocumento.TipoLicencaGrupoDocumentoBuilder setRequisitoTecnico(RequisitoTecnico requisitoTecnico) {
            this.requisitoTecnico = requisitoTecnico;
            return this;
        }

        public TipoLicencaGrupoDocumento.TipoLicencaGrupoDocumentoBuilder setLicenca(Licenca licenca) {
            this.licenca = licenca;
            return this;
        }

        public TipoLicencaGrupoDocumento.TipoLicencaGrupoDocumentoBuilder setDocumento(Documento documento) {
            this.documento = documento;
            return this;
        }

        public TipoLicencaGrupoDocumento build() { return new TipoLicencaGrupoDocumento(this); }
    }

}
