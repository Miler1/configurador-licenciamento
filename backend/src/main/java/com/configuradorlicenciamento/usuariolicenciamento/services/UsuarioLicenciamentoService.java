package com.configuradorlicenciamento.usuariolicenciamento.services;

import br.ufla.lemaf.beans.pessoa.Usuario;
import com.configuradorlicenciamento.usuariolicenciamento.interfaces.IUsuarioLicenciamentoService;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLicenciamentoService implements IUsuarioLicenciamentoService {

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public UsuarioLicenciamento cadastraOuAtualiza(Usuario usuarioEntradaUnica) {

        UsuarioLicenciamento usuario = usuarioLicenciamentoRepository.findByLogin(usuarioEntradaUnica.login);

        if(usuario == null) {

            usuario = new UsuarioLicenciamento(usuarioEntradaUnica);
            usuarioLicenciamentoRepository.save(usuario);

        }

        return usuario;
    }
}
