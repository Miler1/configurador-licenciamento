package com.configuradorlicenciamento.documento.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.documento.dtos.DocumentoDTO;
import com.configuradorlicenciamento.documento.models.Documento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface IDocumentoService {

    Documento salvar(HttpServletRequest request, DocumentoDTO documentoDTO);

    Page<Documento> listar(Pageable pageable, FiltroPesquisa filtro);

    Documento editar(HttpServletRequest request, DocumentoDTO documentoDTO);

}
