package com.configuradorlicenciamento.licenca.repositories;

import com.configuradorlicenciamento.licenca.models.Licenca;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicencaRepository extends JpaRepository<Licenca, Integer>, JpaSpecificationExecutor<Licenca> {

    Boolean existsBySigla(String sigla);

    Licenca findBySigla(String sigla);

    List<Licenca> findByAtivo(boolean ativo, Sort sort);

}