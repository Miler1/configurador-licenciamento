package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.pergunta.repositories.PerguntaRepository;
import com.configuradorlicenciamento.atividade.interfaces.IRelAtividadePerguntaService;
import com.configuradorlicenciamento.atividade.models.RelAtividadePergunta;
import com.configuradorlicenciamento.atividade.repositories.RelAtividadePerguntaRepository;
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

        IntStream.range(0, perguntas.size()).forEach(index -> {

            Optional<Pergunta> pergunta = perguntaRepository.findById(perguntas.get(index).getId());

            RelAtividadePergunta relAtividadePergunta = new RelAtividadePergunta(atividade, pergunta.get(), (index + 1));

            relAtividadePerguntaRepository.save(relAtividadePergunta);

        });

    }

    @Override
    public void editar(Atividade atividade, List<PerguntaDTO> perguntas) {

        List<RelAtividadePergunta> relAtividadePerguntaList = relAtividadePerguntaRepository.findByAtividade(atividade);

        relAtividadePerguntaList.forEach(atividadePergunta -> relAtividadePerguntaRepository.delete(atividadePergunta));

        IntStream.range(0, perguntas.size()).forEach(index -> {

            Optional<Pergunta> pergunta = perguntaRepository.findById(perguntas.get(index).getId());

            RelAtividadePergunta relAtividadePergunta = new RelAtividadePergunta(atividade, pergunta.get(), (index + 1));

            relAtividadePerguntaRepository.save(relAtividadePergunta);

        });

    }

}