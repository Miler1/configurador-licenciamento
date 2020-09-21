package com.configuradorlicenciamento.historicoConfigurador.services;

import com.configuradorlicenciamento.historicoConfigurador.interfaces.IHistoricoConfiguradorService;
import com.configuradorlicenciamento.historicoConfigurador.models.AcaoConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.FuncionalidadeConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.HistoricoConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.repositories.AcaoConfiguradorRepository;
import com.configuradorlicenciamento.historicoConfigurador.repositories.FuncionalidadeConfiguradorRepository;
import com.configuradorlicenciamento.historicoConfigurador.repositories.HistoricoConfiguradorRepository;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class HistoricoConfiguradorService implements IHistoricoConfiguradorService {

    @Autowired
    HistoricoConfiguradorRepository historicoRepository;

    @Autowired
    FuncionalidadeConfiguradorRepository funcionadeRepository;

    @Autowired
    AcaoConfiguradorRepository acaoRepository;


    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public void salvar(HttpServletRequest request, Integer idFuncionalidade, Integer idAcao) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<FuncionalidadeConfigurador> funcionalidade = funcionadeRepository.findById(idFuncionalidade);

        Optional<AcaoConfigurador> acao = acaoRepository.findById(idAcao);

        HistoricoConfigurador historico = new HistoricoConfigurador(funcionalidade.get(), acao.get(), new Date(), usuarioLicenciamento);

        historicoRepository.save(historico);

    }

    @Override
    public void editar(HttpServletRequest request, Integer idFuncionalidade, Integer idAcao, String justificativa) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<FuncionalidadeConfigurador> funcionalidade = funcionadeRepository.findById(idFuncionalidade);

        Optional<AcaoConfigurador> acao = acaoRepository.findById(idAcao);

        HistoricoConfigurador historicoConfigurador = new HistoricoConfigurador(funcionalidade.get(), acao.get(), justificativa, new Date(), usuarioLicenciamento);

        historicoRepository.save(historicoConfigurador);

    }

    @Override
    public HistoricoConfigurador findbyId(Integer idTabela) {

        return null;

    }

}