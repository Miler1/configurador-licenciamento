package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.dtos.PorteAtividadeDTO;
import com.configuradorlicenciamento.atividade.interfaces.IPorteAtividadeService;
import com.configuradorlicenciamento.atividade.models.PorteAtividade;
import com.configuradorlicenciamento.atividade.repositories.PorteAtividadeRepository;
import com.configuradorlicenciamento.parametro.models.Parametro;
import com.configuradorlicenciamento.parametro.repositories.ParametroRepository;
import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;
import com.configuradorlicenciamento.porteEmpreendimento.repositories.PorteEmpreendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PorteAtividadeService implements IPorteAtividadeService {

    @Autowired
    PorteEmpreendimentoRepository porteEmpreendimentoRepository;

    @Autowired
    ParametroRepository parametroRepository;

    @Autowired
    PorteAtividadeRepository porteAtividadeRepository;

    @Override
    public List<PorteAtividade> salvar(List<PorteAtividadeDTO> portes) {

        List<PorteAtividade> portesAtividade = new ArrayList<>();

        Integer codigoPorte = porteAtividadeRepository.max() + 1;

        portes.forEach(porte -> {

            PorteEmpreendimento porteEmpreendimento = porteEmpreendimentoRepository.findById(porte.getPorte().getId()).get();

            Parametro parametroUm = porte.getParametroUm() != null ? parametroRepository.findById(porte.getParametroUm().getId()).get() : null;
            Parametro parametroDois = porte.getParametroDois() != null ? parametroRepository.findById(porte.getParametroDois().getId()).get() : null;

            PorteAtividade porteAtividade = new PorteAtividade.PorteAtividadeBuilder()
                    .setPorteEmpreendimento(porteEmpreendimento)
                    .setLimiteInferiorUm(porte.getLimiteInferiorUm() == 0.0 ? null : porte.getLimiteInferiorUm())
                    .setLimiteSuperiorUm(porte.getLimiteSuperiorUm())
                    .setLimiteInferiorDois(porte.getLimiteInferiorDois() == 0.0 ? null : porte.getLimiteInferiorDois())
                    .setLimiteSuperiorDois(porte.getLimiteSuperiorDois())
                    .setParametroUm(parametroUm)
                    .setParametroDois(parametroDois)
                    .setLimiteInferiorUmIncluso(porte.getLimiteInferiorUmIncluso())
                    .setLimiteSuperiorUmIncluso(porte.getLimiteSuperiorUmIncluso())
                    .setLimiteInferiorDoisIncluso(porte.getLimiteInferiorDoisIncluso())
                    .setLimiteSuperiorDoisIncluso(porte.getLimiteSuperiorDoisIncluso())
                    .setCodigo(codigoPorte)
                    .build();

            porteAtividadeRepository.save(porteAtividade);

            portesAtividade.add(porteAtividade);

        });

        return portesAtividade;
    }
}
