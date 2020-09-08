package com.configuradorlicenciamento.porteEmpreendimento.repositories;

import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorteEmpreendimentoRepository  extends JpaRepository<PorteEmpreendimento, Integer> {
}
