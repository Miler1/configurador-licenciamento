package com.configuradorlicenciamento.historicoConfigurador.services;

import com.configuradorlicenciamento.historicoConfigurador.interfaces.IHistoricoConfiguradorService;
import com.configuradorlicenciamento.historicoConfigurador.models.Historico;
import com.configuradorlicenciamento.historicoConfigurador.repositories.HistoricoConfiguradorRepository;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class HistoricoConfiguradorService implements IHistoricoConfiguradorService {

    @Autowired
    HistoricoConfiguradorRepository historicoConfiguradorRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public void salvar(HttpServletRequest request, Integer idTabela, Integer idFuncionalidade, Integer idAcaoConfigurador) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

    }

    @Override
    public void editar(HttpServletRequest request, Integer idTabela, String justificativa, Integer funcionalidade, Integer idAcaoConfigurador) {

    }

    @Override
    public Historico findbyId(Integer idTabela) {

        return null;

    }

}