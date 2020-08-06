package com.configuradorlicenciamento.usuarioLicenciamento.interfaces;

import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;

public interface IUsuarioLicenciamentoService {

    UsuarioLicenciamento cadastraOuAtualiza(br.ufla.lemaf.beans.pessoa.Usuario usuarioEntradaUnica);

}
