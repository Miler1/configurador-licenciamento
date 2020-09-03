package com.configuradorlicenciamento.porteEmpreendimento.interfaces;

import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;

import java.util.List;

public interface IPorteEmpreendimentoService {
    List<PorteEmpreendimento> findAll();
}
