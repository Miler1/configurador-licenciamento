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
import java.util.stream.Collectors;
import java.util.Optional;

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

            portesAtividade.add(this.salvarPorte(porte, codigoPorte));

        });

        return portesAtividade;
    }

    public PorteAtividade salvarPorte(PorteAtividadeDTO porte, Integer codigoPorte) {

        Parametro parametroUm = null;

        Parametro parametroDois = null;

        Optional<PorteEmpreendimento> porteEmpreendimento = Optional.ofNullable(null);

        if (porte.getPorte() != null) {

            Optional<PorteEmpreendimento> porteEmpreendimentoSalvo = porteEmpreendimentoRepository.findById(porte.getPorte().getId());

            if (porteEmpreendimentoSalvo.isPresent()) {
                porteEmpreendimento = Optional.of(porteEmpreendimentoSalvo.get());
            }

        }

        Optional<Parametro> parametroUmSalvo;

        if (porte.getParametroUm() != null) {

            parametroUmSalvo = parametroRepository.findById(porte.getParametroUm().getId());

            if (parametroUmSalvo.isPresent()) {
                parametroUm = parametroUmSalvo.get();
            }

        }

        Optional<Parametro> parametroDoisSalvo;

        if (porte.getParametroDois() != null) {

            parametroDoisSalvo = parametroRepository.findById(porte.getParametroDois().getId());

            if (parametroDoisSalvo.isPresent()) {
                parametroDois = parametroDoisSalvo.get();
            }

        }

        PorteAtividade porteAtividade = new PorteAtividade.PorteAtividadeBuilder()
                .setPorteEmpreendimento(porteEmpreendimento.orElse(null))
                .setLimiteInferiorUm(porte.getLimiteInferiorUm() != null && porte.getLimiteInferiorUm() == 0.0 ? null : porte.getLimiteInferiorUm())
                .setLimiteSuperiorUm(porte.getLimiteSuperiorUm())
                .setLimiteInferiorDois(porte.getLimiteInferiorDois() != null && porte.getLimiteInferiorDois() == 0.0 ? null : porte.getLimiteInferiorDois())
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

        return porteAtividade;

    }

    @Override
    public List<PorteAtividade> editar(List<PorteAtividadeDTO> portes) {

        List<PorteAtividade> portesAtividade = new ArrayList<>();

        List<PorteAtividadeDTO> portesDTOSalvos = portes.stream().filter(porte -> porte.getId() != null).collect(Collectors.toList());

        Integer codigoPorte;

        if (portesDTOSalvos.isEmpty()) {
            codigoPorte = porteAtividadeRepository.max() + 1;
        } else {
            codigoPorte = porteAtividadeRepository.findById(portesDTOSalvos.get(0).getId()).get().getCodigo();
        }

        portes.forEach(porte -> {

            if (porte.getId() == null) {

                portesAtividade.add(this.salvarPorte(porte, codigoPorte));

            } else {

                PorteAtividade porteAtividadesalvo = porteAtividadeRepository.findById(porte.getId()).orElse(null);

                PorteEmpreendimento porteEmpreendimento = porte.getPorte() != null ? porteEmpreendimentoRepository.findById(porte.getPorte().getId()).get() : null;

                Parametro parametroUm = porte.getParametroUm() != null ? parametroRepository.findById(porte.getParametroUm().getId()).get() : null;
                Parametro parametroDois = porte.getParametroDois() != null ? parametroRepository.findById(porte.getParametroDois().getId()).get() : null;

                porteAtividadesalvo.setPorteEmpreendimento(porteEmpreendimento);
                porteAtividadesalvo.setLimiteInferiorUm(porte.getLimiteInferiorUm() != null && porte.getLimiteInferiorUm() == 0.0 ? null : porte.getLimiteInferiorUm());
                porteAtividadesalvo.setLimiteSuperiorUm(porte.getLimiteSuperiorUm());
                porteAtividadesalvo.setLimiteInferiorDois(porte.getLimiteInferiorDois() != null && porte.getLimiteInferiorDois() == 0.0 ? null : porte.getLimiteInferiorDois());
                porteAtividadesalvo.setLimiteSuperiorDois(porte.getLimiteSuperiorDois());
                porteAtividadesalvo.setParametroUm(parametroUm);
                porteAtividadesalvo.setParametroDois(parametroDois);
                porteAtividadesalvo.setLimiteInferiorUmIncluso(porte.getLimiteInferiorUmIncluso());
                porteAtividadesalvo.setLimiteSuperiorUmIncluso(porte.getLimiteSuperiorUmIncluso());
                porteAtividadesalvo.setLimiteInferiorDoisIncluso(porte.getLimiteInferiorDoisIncluso());
                porteAtividadesalvo.setLimiteSuperiorDoisIncluso(porte.getLimiteSuperiorDoisIncluso());

                porteAtividadeRepository.save(porteAtividadesalvo);

                portesAtividade.add(porteAtividadesalvo);
            }

        });

        return portesAtividade;
    }

}
