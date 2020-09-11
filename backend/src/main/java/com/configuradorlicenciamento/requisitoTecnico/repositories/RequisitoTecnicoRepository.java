package com.configuradorlicenciamento.requisitoTecnico.repositories;

import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisitoTecnicoRepository extends JpaRepository<RequisitoTecnico, Integer>, JpaSpecificationExecutor<RequisitoTecnico> {
}