package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.services;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.pergunta.repositories.PerguntaRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces.IRelAtividadePerguntaService;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.RelAtividadePergunta;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories.RelAtividadePerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class RelAtividadePerguntaService implements IRelAtividadePerguntaService {

    @Autowired
    RelAtividadePerguntaRepository relAtividadePerguntaRepository;

    @Autowired
    PerguntaRepository perguntaRepository;

    @Override
    public void salvar(Atividade atividade, List<PerguntaDTO> perguntas) {

        IntStream.range(0, perguntas.size()).forEach( index -> {

            Optional<Pergunta> pergunta = perguntaRepository.findById(perguntas.get(index).getId());

            RelAtividadePergunta relAtividadePergunta = new RelAtividadePergunta(atividade, pergunta.get(), (index + 1));

            relAtividadePerguntaRepository.save(relAtividadePergunta);

        });

    }
}
