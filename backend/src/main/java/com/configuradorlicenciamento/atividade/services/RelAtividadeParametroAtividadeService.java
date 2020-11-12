package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.dtos.PorteAtividadeDTO;
import com.configuradorlicenciamento.atividade.interfaces.IRelAtividadeParametroAtividadeService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.RelAtividadeParametroAtividade;
import com.configuradorlicenciamento.atividade.repositories.RelAtividadeParametroAtividadeRepository;
import com.configuradorlicenciamento.parametro.dtos.ParametroDTO;
import com.configuradorlicenciamento.parametro.models.Parametro;
import com.configuradorlicenciamento.parametro.repositories.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelAtividadeParametroAtividadeService implements IRelAtividadeParametroAtividadeService {

    @Autowired
    ParametroRepository parametroRepository;

    @Autowired
    RelAtividadeParametroAtividadeRepository relAtividadeParametroAtividadeRepository;

    @Override
    public void salvar(Atividade atividade, PorteAtividadeDTO parametro) {

        if (parametro.getParametroUm() != null) {

            this.salvarParametro(atividade, parametro.getParametroUm(), parametro.getDescricaoUnidadeUm());

        }

        if (parametro.getParametroDois() != null) {

            this.salvarParametro(atividade, parametro.getParametroDois(), parametro.getDescricaoUnidadeDois());

        }

    }

    @Override
    public void editar(Atividade atividade, PorteAtividadeDTO parametro) {

        List<RelAtividadeParametroAtividade> relAtividadeParametroAtividades = relAtividadeParametroAtividadeRepository.findByAtividade(atividade);

        relAtividadeParametroAtividades.forEach(relAtividadeParametroAtividade -> relAtividadeParametroAtividadeRepository.delete(relAtividadeParametroAtividade));


        if (parametro.getParametroUm() != null) {

            this.salvarParametro(atividade, parametro.getParametroUm(), parametro.getDescricaoUnidadeUm());

        }

        if (parametro.getParametroDois() != null) {

            this.salvarParametro(atividade, parametro.getParametroDois(), parametro.getDescricaoUnidadeDois());

        }

    }

    @Override
    public void excluir(Atividade atividade) {

        List<RelAtividadeParametroAtividade> relAtividadeParametroAtividades = relAtividadeParametroAtividadeRepository.findByAtividade(atividade);

        if (!relAtividadeParametroAtividades.isEmpty()) {
            relAtividadeParametroAtividades.forEach(relAtividadeParametroAtividade -> relAtividadeParametroAtividadeRepository.delete(relAtividadeParametroAtividade));
        }

    }

    public void salvarParametro(Atividade atividade, ParametroDTO parametro, String descricaoUnidade) {

        Parametro parametroSalvo = parametroRepository.findById(parametro.getId()).get();

        String descricao = (descricaoUnidade == null || descricaoUnidade.equals("")) ? parametroSalvo.getNome() : descricaoUnidade;

        RelAtividadeParametroAtividade relAtividadeParametroAtividade = new RelAtividadeParametroAtividade
                .RelAtividadeParametroAtividadeBuilder(descricao)
                .setAtividade(atividade)
                .setParametro(parametroSalvo)
                .build();

        relAtividadeParametroAtividadeRepository.save(relAtividadeParametroAtividade);

    }

}