package com.configuradorlicenciamento.usuarioLicenciamento.repositories;

import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioLicenciamentoRepository extends JpaRepository<UsuarioLicenciamento, Integer> {

    UsuarioLicenciamento findByLogin(String login);

}
