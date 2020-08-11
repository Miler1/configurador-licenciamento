package com.configuradorlicenciamento.licenca.repositories;

import com.configuradorlicenciamento.licenca.models.Licenca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicencaRepository extends JpaRepository<Licenca, Integer> {

}
