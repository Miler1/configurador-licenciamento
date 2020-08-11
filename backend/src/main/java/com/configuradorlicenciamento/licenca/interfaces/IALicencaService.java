package com.configuradorlicenciamento.licenca.interfaces;

import com.configuradorlicenciamento.licenca.dtos.LicencaDTO;
import com.configuradorlicenciamento.licenca.models.Licenca;

import javax.servlet.http.HttpServletRequest;

public interface IALicencaService {

    Licenca salvar(HttpServletRequest request, LicencaDTO licencaDTO) throws Exception;

}
