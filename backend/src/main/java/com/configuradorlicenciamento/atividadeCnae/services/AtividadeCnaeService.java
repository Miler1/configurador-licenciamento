package com.configuradorlicenciamento.atividadeCnae.services;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.interfaces.IAtividadeCnaeService;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AtividadeCnaeService implements IAtividadeCnaeService {

    @Autowired
    AtividadeCnaeRepository atividadeCnaeRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    public AtividadeCnae salvar(AtividadeCnaeDTO atividadeCnaeDTO) throws Exception{

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin("12739938616");

        AtividadeCnae atividadeCnae = new AtividadeCnae.AtividadeCnaeBuilder(atividadeCnaeDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        atividadeCnaeRepository.save(atividadeCnae);

        return atividadeCnae;

    }

    public List<AtividadeCnae> listarCnaes() {
        return atividadeCnaeRepository.findAll(Sort.by("codigo"));
    }

    public List<AtividadeCnaeCsv> listarCnaesParaCsv(){

        List<AtividadeCnae> cnaes = listarCnaes();
        List<AtividadeCnaeCsv> dtos = new ArrayList<>();

        for (AtividadeCnae cnae : cnaes) {
            dtos.add(cnae.preparaParaCsv());
        }

        return dtos;
    }
}
