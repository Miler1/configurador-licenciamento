package com.configuradorlicenciamento.historicoConfigurador.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(schema = GlobalReferences.ESQUEMA, name = "historico_configurador")
public class Historico implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull(message = "{validacao.notnull}")
    public Integer idTabela;

    @NotNull(message = "{validacao.notnull}")
    public Integer funcionalidade;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public Historico(Integer idTabela, Integer funcionalidade, Date dataCadastro, UsuarioLicenciamento usuarioLicenciamento) {

        this.idTabela = idTabela;
        this.funcionalidade = funcionalidade;
        this.dataCadastro = dataCadastro;
        this.usuarioLicenciamento = usuarioLicenciamento;

    }

}
