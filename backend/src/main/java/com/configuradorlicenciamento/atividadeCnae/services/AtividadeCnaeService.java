package com.configuradorlicenciamento.atividadeCnae.services;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtividadeCnaeService {

    @Autowired
    AtividadeCnaeRepository atividadeCnaeRepository;

    public AtividadeCnae salvar(AtividadeCnaeDTO atividadeCnaeDTO) throws Exception{

        AtividadeCnae atividadeCnae = new AtividadeCnae.AtividadeCnaeBuilder(atividadeCnaeDTO).build();

        atividadeCnaeRepository.save(atividadeCnae);

        return atividadeCnae;

    }

}
