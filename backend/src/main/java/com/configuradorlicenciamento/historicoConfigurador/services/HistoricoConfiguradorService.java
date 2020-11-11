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
import java.util.List;

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
    public void salvar(HttpServletRequest request, Integer idTabela, String funcionalidade, String acao) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        FuncionalidadeConfigurador funcionalidadeConfigurador = funcionadeRepository.findByTipo(funcionalidade);

        AcaoConfigurador acaoConfigurador = acaoRepository.findByAcao(acao);

        HistoricoConfigurador historico = new HistoricoConfigurador(
                idTabela, funcionalidadeConfigurador, acaoConfigurador, new Date(), usuarioLicenciamento);

        historicoRepository.save(historico);

    }

    @Override
    public void editar(HttpServletRequest request, Integer idItemAtual, Integer idItemAntigo, String funcionalidade, String acao, String justificativa) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        FuncionalidadeConfigurador funcionalidadeConfigurador = funcionadeRepository.findByTipo(funcionalidade);

        AcaoConfigurador acaoConfigurador = acaoRepository.findByAcao(acao);

        HistoricoConfigurador historicoConfigurador = new HistoricoConfigurador(
                idItemAtual, idItemAntigo, funcionalidadeConfigurador, acaoConfigurador, justificativa, new Date(), usuarioLicenciamento);

        historicoRepository.save(historicoConfigurador);

    }

    @Override
    public List<HistoricoConfigurador> buscarHistoricoItem(String funcionalidade, Integer idItem) {

        FuncionalidadeConfigurador funcionalidadeConfigurador = funcionadeRepository.findByTipo(funcionalidade);

        return historicoRepository.findByFuncionalidadeAndIdItemOrderByDataAcaoDesc(
                funcionalidadeConfigurador, idItem);

    }

}