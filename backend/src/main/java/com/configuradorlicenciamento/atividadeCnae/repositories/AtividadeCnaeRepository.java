package com.configuradorlicenciamento.atividadeCnae.repositories;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeCnaeRepository extends JpaRepository<AtividadeCnae, Integer>, JpaSpecificationExecutor<AtividadeCnae> {

    @Query("FROM AtividadeCnae AS ac WHERE ac.ativo = true AND ac.id NOT IN " +
            "(SELECT tca.atividadeCnae.id FROM ac.tipoCaracterizacaoAtividadeList AS tca WHERE tca.dispensaLicenciamento = true)")
    List<AtividadeCnae> findAllWithDescriptionQuery();

}