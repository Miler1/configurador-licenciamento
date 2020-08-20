package com.configuradorlicenciamento.parametro.interfaces;

import com.configuradorlicenciamento.parametro.dtos.ParametroDTO;
import com.configuradorlicenciamento.parametro.models.Parametro;

import javax.servlet.http.HttpServletRequest;

public interface IParametroService {

   Parametro salvar(HttpServletRequest request, ParametroDTO parametroDTO) throws Exception;

}
