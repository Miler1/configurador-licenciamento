package com.configuradorlicenciamento.resposta.services;

import com.configuradorlicenciamento.resposta.interfaces.IRespostaService;
import com.configuradorlicenciamento.resposta.models.Resposta;
import com.configuradorlicenciamento.resposta.repositories.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RespostaService implements IRespostaService {

    @Autowired
    RespostaRepository respostaRepository;

    @Override
    public Resposta buscaResposta(Integer idResposta) {

        Resposta resposta = new Resposta();

        Optional<Resposta> respostaSalva = respostaRepository.findById(idResposta);

        if (respostaSalva.isPresent()) {
            resposta = respostaSalva.get();
        }

        return resposta;
    }

}
