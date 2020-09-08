package com.configuradorlicenciamento.porteEmpreendimento.services;

import com.configuradorlicenciamento.porteEmpreendimento.interfaces.IPorteEmpreendimentoService;
import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;
import com.configuradorlicenciamento.porteEmpreendimento.repositories.PorteEmpreendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PorteEmpreendimentoService implements IPorteEmpreendimentoService {

    @Autowired
    PorteEmpreendimentoRepository porteEmpreendimentoRepository;

    @Override
    public List<PorteEmpreendimento> findAll() {

        return porteEmpreendimentoRepository.findAll();

    }
}
