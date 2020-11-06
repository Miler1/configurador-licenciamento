package com.configuradorlicenciamento.atividade.interfaces;

import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelCsv;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelDTO;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelEdicaoDTO;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAtividadeService {

    Atividade salvar(AtividadeDispensavelDTO.RelacaoCnaeTipologia atividadeDispensavelDTO);

    Atividade editar(AtividadeDispensavelDTO.RelacaoCnaeTipologia atividadeDispensavelDTO, Atividade atividade);

    Page<Atividade> listarAtividadesLicenciaveis(Pageable pageable, FiltroPesquisa filtro);

    List<AtividadeLicenciavelCsv> listarAtividadesLicenciaveisParaCsv();

    List<Atividade> listarAtividadesLicenciaveis();

    Atividade salvarAtividadeLicenciavel(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO);

    Atividade editarAtividadeLicenciavel(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO);

    Atividade editarRascunhoAtividadeLicenciavel(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO);

    void excluirRascunhoAtividadeLicenciavel(HttpServletRequest request, Integer idAtividade);

    AtividadeLicenciavelEdicaoDTO findById(Integer idAtividadeLicenciavel);

    Atividade salvarRascunhoAtividadeLicenciavel(HttpServletRequest request, AtividadeLicenciavelDTO atividadeLicenciavelDTO);

    Atividade ativarDesativar(Integer idAtividadeLicenciavel);
}