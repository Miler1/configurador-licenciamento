package com.configuradorlicenciamento.requisitotecnico.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.requisitotecnico.dtos.RequisitoTecnicoDTO;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "grupo_documento")
public class RequisitoTecnico implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @Column(unique = true, name="codigo")
    private String codigo;

    @NotNull(message = "{validacao.notnull}")
    private String descricao;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public RequisitoTecnico(RequisitoTecnico.RequisitoTecnicoBuilder builder) {
        this.codigo = builder.codigo;
        this.descricao = builder.descricao;
        this.ativo = builder.ativo;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
        this.dataCadastro = builder.dataCadastro;
    }

    public static class RequisitoTecnicoBuilder {

        private String codigo;
        private String descricao;
        private Boolean ativo;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public RequisitoTecnicoBuilder(RequisitoTecnicoDTO requisitoTecnicoDTO) {
            this.codigo = requisitoTecnicoDTO.getCodigo();
            this.descricao = requisitoTecnicoDTO.getDescricao();
            this.ativo = requisitoTecnicoDTO.getAtivo();
        }

        public RequisitoTecnico.RequisitoTecnicoBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public RequisitoTecnico.RequisitoTecnicoBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public RequisitoTecnico build() { return new RequisitoTecnico(this); }
    }

}
