package com.configuradorlicenciamento.licenca.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.licenca.dtos.LicencaCsv;
import com.configuradorlicenciamento.licenca.dtos.LicencaDTO;
import com.configuradorlicenciamento.licenca.models.Licenca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ILicencaService {

    Licenca salvar(HttpServletRequest request, LicencaDTO licencaDTO) throws Exception;

    Page<Licenca> lista(Pageable pageable, FiltroPesquisa filtro);

    List<LicencaCsv> listarLicencasParaCsv() throws Exception;

    List<Licenca> listarLicencas() throws Exception;

}