package com.configuradorlicenciamento.tipologia.interfaces;

import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import javax.servlet.http.HttpServletRequest;

public interface ITipologiaService {

    Tipologia salvar(HttpServletRequest request, TipologiaDTO tipologiaDTO) throws Exception;

}
