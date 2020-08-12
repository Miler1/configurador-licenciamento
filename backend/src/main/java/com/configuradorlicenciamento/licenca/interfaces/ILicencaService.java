package com.configuradorlicenciamento.licenca.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.licenca.dtos.LicencaDTO;
import com.configuradorlicenciamento.licenca.models.Licenca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface ILicencaService {

    Licenca salvar(HttpServletRequest request, LicencaDTO licencaDTO) throws Exception;
    Page<Licenca> lista(Pageable pageable, FiltroPesquisa filtro);

}