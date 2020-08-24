package com.configuradorlicenciamento.requisitoAdministrativo.interfaces;

import com.configuradorlicenciamento.requisitoAdministrativo.dtos.RequisitoAdministrativoDTO;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IRequisitoAdministrativoService {

    List<RequisitoAdministrativo> salvar(HttpServletRequest request, RequisitoAdministrativoDTO requisitoAdministrativoDTO) throws Exception;

}
