package com.configuradorlicenciamento.usuariolicenciamento.repositories;

import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioLicenciamentoRepository extends JpaRepository<UsuarioLicenciamento, Integer> {

    UsuarioLicenciamento findByLogin(String login);

}
