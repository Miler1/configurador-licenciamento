package com.configuradorlicenciamento.requisioTecnico.repositories;

import com.configuradorlicenciamento.requisioTecnico.models.RequisitoTecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisitoTecnicoRepository extends JpaRepository<RequisitoTecnico, Integer>, JpaSpecificationExecutor<RequisitoTecnico> {
}
