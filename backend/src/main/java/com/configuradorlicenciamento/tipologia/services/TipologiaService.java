package com.configuradorlicenciamento.tipologia.services;

import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
import com.configuradorlicenciamento.tipologia.interfaces.ITipologiaService;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.tipologia.repositories.TipologiaRepository;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class TipologiaService implements ITipologiaService {

    @Autowired
    TipologiaRepository tipologiaRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    public Tipologia salvar(HttpServletRequest request, TipologiaDTO tipologiaDTO) throws Exception{

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Tipologia tipologia = new Tipologia.TipologiaBuilder(tipologiaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        tipologiaRepository.save(tipologia);

        return tipologia;

    }

}
