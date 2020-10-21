package com.configuradorlicenciamento.atividade.repositories;

import com.configuradorlicenciamento.atividade.models.TipoAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAtividadeRepository extends JpaRepository<TipoAtividade, Integer> {

    TipoAtividade findByCodigo(String codigo);

}
