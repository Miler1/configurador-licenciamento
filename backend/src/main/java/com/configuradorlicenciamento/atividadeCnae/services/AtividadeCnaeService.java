package com.configuradorlicenciamento.atividadeCnae.services;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.interfaces.IAtividadeCnaeService;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class AtividadeCnaeService implements IAtividadeCnaeService {

    @Autowired
    AtividadeCnaeRepository atividadeCnaeRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    public AtividadeCnae salvar(HttpServletRequest request, AtividadeCnaeDTO atividadeCnaeDTO) throws Exception{

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        AtividadeCnae atividadeCnae = new AtividadeCnae.AtividadeCnaeBuilder(atividadeCnaeDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        atividadeCnaeRepository.save(atividadeCnae);

        return atividadeCnae;

    }

}
