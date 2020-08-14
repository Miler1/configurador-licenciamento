package com.configuradorlicenciamento.tipologia.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "tipologia")
public class Tipologia implements Serializable {

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

    public Tipologia(TipologiaBuilder builder) {
        this.nome = builder.nome;
        this.codigo = builder.codigo;
        this.ativo = builder.ativo;
        this.dataCadastro = builder.dataCadastro;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
    }

    public static class TipologiaBuilder {

        private final String nome;
        private final String codigo;
        private Boolean ativo;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public TipologiaBuilder(TipologiaDTO tipologiaDTO) {
            this.nome = tipologiaDTO.getNome();
            this.codigo = tipologiaDTO.getCodigo();
            this.ativo = tipologiaDTO.getAtivo();
        }

        public TipologiaBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public TipologiaBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public Tipologia build() { return new Tipologia(this); }

    }
}
