package com.configuradorlicenciamento.requisitoadministrativo.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitoadministrativo.dtos.RequisitoAdministrativoDTO;
import com.configuradorlicenciamento.requisitoadministrativo.models.RequisitoAdministrativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IRequisitoAdministrativoService {

    List<RequisitoAdministrativo> salvar(HttpServletRequest request, RequisitoAdministrativoDTO requisitoAdministrativoDTO) throws Exception;

    RequisitoAdministrativo editar(HttpServletRequest request, RequisitoAdministrativoDTO requisitoAdministrativoDTO);

    Page<RequisitoAdministrativo> listar(Pageable pageable, FiltroPesquisa filtro);

}
