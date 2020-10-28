package com.configuradorlicenciamento.requisitoTecnico.repositories;

import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequisitoTecnicoRepository extends JpaRepository<RequisitoTecnico, Integer>, JpaSpecificationExecutor<RequisitoTecnico> {

    List<RequisitoTecnico> findByCodigo(String codigo);

    List<RequisitoTecnico> findByAtivo(Boolean ativo, Sort codigo);

}
