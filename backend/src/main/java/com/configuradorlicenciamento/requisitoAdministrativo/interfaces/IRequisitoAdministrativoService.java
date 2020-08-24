package com.configuradorlicenciamento.requisitoAdministrativo.interfaces;

import com.configuradorlicenciamento.requisitoAdministrativo.dtos.RequisitoAdministrativoDTO;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;

import javax.servlet.http.HttpServletRequest;

public interface IRequisitoAdministrativoService {

    RequisitoAdministrativo salvar(HttpServletRequest request, RequisitoAdministrativoDTO requisitoAdministrativoDTO) throws Exception;

}
