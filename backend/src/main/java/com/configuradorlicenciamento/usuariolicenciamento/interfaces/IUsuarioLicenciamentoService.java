package com.configuradorlicenciamento.usuariolicenciamento.interfaces;

import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;

public interface IUsuarioLicenciamentoService {

    UsuarioLicenciamento cadastraOuAtualiza(br.ufla.lemaf.beans.pessoa.Usuario usuarioEntradaUnica);

}
