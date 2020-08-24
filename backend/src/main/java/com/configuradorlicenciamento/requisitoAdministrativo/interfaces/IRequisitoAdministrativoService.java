package com.configuradorlicenciamento.requisitoAdministrativo.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitoAdministrativo.dtos.RequisitoAdministrativoDTO;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IRequisitoAdministrativoService {

    List<RequisitoAdministrativo> salvar(HttpServletRequest request, RequisitoAdministrativoDTO requisitoAdministrativoDTO) throws Exception;

    Page<RequisitoAdministrativo> listar(Pageable pageable, FiltroPesquisa filtro);

}
