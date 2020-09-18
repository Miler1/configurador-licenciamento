package com.configuradorlicenciamento.parametro.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.parametro.dtos.ParametroCsv;
import com.configuradorlicenciamento.parametro.dtos.ParametroDTO;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "parametro_atividade")
public class Parametro implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String nome;

    @NotNull(message = "{validacao.notnull}")
    @Column(unique = true, name="codigo")
    private String codigo;

    @Column(nullable = true)
    private Integer casasDecimais;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;


    public Parametro(Parametro.ParametroBuilder builder) {
        this.codigo = builder.codigo;
        this.nome = builder.nome;
        this.casasDecimais = builder.casasDecimais;
        this.ativo = builder.ativo;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
        this.dataCadastro = builder.dataCadastro;
    }

    public static class ParametroBuilder {
        private String codigo;
        private String nome;
        private Integer casasDecimais;
        private Boolean ativo;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public ParametroBuilder(ParametroDTO parametroDTO) {
            this.codigo = StringUtil.tratarEspacos(parametroDTO.getCodigo());
            this.nome = parametroDTO.getNome();
            this.casasDecimais = parametroDTO.getCasasDecimais();
            this.ativo = parametroDTO.getAtivo();
        }

        public Parametro.ParametroBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public Parametro.ParametroBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public Parametro build(){return new Parametro(this);}

    }

    public ParametroCsv preparaParaCsv() {

        return new ParametroCsv(this);
    }

}
