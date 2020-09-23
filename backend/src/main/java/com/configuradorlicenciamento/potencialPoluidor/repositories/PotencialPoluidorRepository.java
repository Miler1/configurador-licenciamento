package com.configuradorlicenciamento.potencialPoluidor.repositories;

import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotencialPoluidorRepository extends JpaRepository<PotencialPoluidor, Integer> {

    PotencialPoluidor findByCodigo(String codigo);

}
