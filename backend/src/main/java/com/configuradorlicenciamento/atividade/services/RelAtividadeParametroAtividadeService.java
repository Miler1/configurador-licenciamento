package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.dtos.PorteAtividadeDTO;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.RelAtividadeParametroAtividade;
import com.configuradorlicenciamento.parametro.dtos.ParametroDTO;
import com.configuradorlicenciamento.parametro.models.Parametro;
import com.configuradorlicenciamento.parametro.repositories.ParametroRepository;
import com.configuradorlicenciamento.atividade.interfaces.IRelAtividadeParametroAtividadeService;
import com.configuradorlicenciamento.atividade.repositories.RelAtividadeParametroAtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelAtividadeParametroAtividadeService implements IRelAtividadeParametroAtividadeService {

    @Autowired
    ParametroRepository parametroRepository;

    @Autowired
    RelAtividadeParametroAtividadeRepository relAtividadeParametroAtividadeRepository;

    @Override
    public void salvar(Atividade atividade, PorteAtividadeDTO parametro) {

        if(parametro.getParametroUm() != null) {

            this.salvarParametro(atividade, parametro.getParametroUm(), parametro.getDescricaoUnidadeUm());

        }

        if(parametro.getParametroDois() != null) {

            this.salvarParametro(atividade, parametro.getParametroDois(), parametro.getDescricaoUnidadeDois());

        }

    }

    public void salvarParametro(Atividade atividade, ParametroDTO parametro, String descricaoUnidade){

        Parametro parametroSalvo = parametroRepository.findById(parametro.getId()).get();

        String descricaoUm = (descricaoUnidade != null) ? descricaoUnidade : parametroSalvo.getNome();

        RelAtividadeParametroAtividade relAtividadeParametroAtividade = new RelAtividadeParametroAtividade
                .RelAtividadeParametroAtividadeBuilder(descricaoUm)
                .setAtividade(atividade)
                .setParametro(parametroSalvo)
                .build();

        relAtividadeParametroAtividadeRepository.save(relAtividadeParametroAtividade);

    }
}
