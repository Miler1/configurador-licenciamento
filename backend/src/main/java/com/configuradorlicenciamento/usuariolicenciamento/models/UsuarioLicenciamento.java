package com.configuradorlicenciamento.usuariolicenciamento.models;

import br.ufla.lemaf.beans.pessoa.Usuario;
import com.configuradorlicenciamento.configuracao.utils.CpfCnpjUtil;
import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Entity
@Table(schema = GlobalReferences.ESQUEMA, name = "usuario_licenciamento")
public class UsuarioLicenciamento implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String login;

    public UsuarioLicenciamento() {}

    public UsuarioLicenciamento(Usuario usuarioEntradaUnica) {
        this.login = CpfCnpjUtil.desformataCpfCnpj(usuarioEntradaUnica.login);
    }
}
