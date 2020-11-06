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

    Licenca editar(HttpServletRequest request, LicencaDTO licencaDTO);

    Licenca ativarDesativar(HttpServletRequest request, Integer idLicenca);

    Page<Licenca> listar(Pageable pageable, FiltroPesquisa filtro);

    List<LicencaCsv> listarLicencasParaCsv() throws Exception;

    List<Licenca> listarLicencas() throws Exception;

    List<Licenca> findAll();

    List<Licenca> findAllByAtivos();

}