package com.configuradorlicenciamento.atividade.interfaces;

import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelCsv;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelDTO;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelEdicaoDTO;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAtividadeLicenciavelService {

    Atividade salvar(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO);

    Atividade editar(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO);

    Page<Atividade> listar(Pageable pageable, FiltroPesquisa filtro);

    List<Atividade> listarAtividadesLicenciaveis();

    List<AtividadeLicenciavelCsv> listarAtividadesParaCsv();

    Atividade ativarDesativar(HttpServletRequest request, Integer idAtividadeLicenciavel);

    AtividadeLicenciavelEdicaoDTO findById(Integer idAtividadeLicenciavel);

}