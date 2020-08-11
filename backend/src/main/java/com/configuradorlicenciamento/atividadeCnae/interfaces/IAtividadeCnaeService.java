package com.configuradorlicenciamento.atividadeCnae.interfaces;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.dtos.FiltroAtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface IAtividadeCnaeService {

    AtividadeCnae salvar(HttpServletRequest request, AtividadeCnaeDTO atividadeCnaeDTO) throws Exception;

    Page<AtividadeCnae> lista(Pageable pageable, FiltroAtividadeCnaeDTO filtro);

}
