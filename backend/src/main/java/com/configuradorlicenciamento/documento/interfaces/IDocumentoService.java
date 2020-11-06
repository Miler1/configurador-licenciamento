package com.configuradorlicenciamento.documento.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.documento.dtos.DocumentoCsv;
import com.configuradorlicenciamento.documento.dtos.DocumentoDTO;
import com.configuradorlicenciamento.documento.models.Documento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IDocumentoService {

    Documento salvar(HttpServletRequest request, DocumentoDTO documentoDTO);

    Page<Documento> listar(Pageable pageable, FiltroPesquisa filtro);

    Documento editar(HttpServletRequest request, DocumentoDTO documentoDTO);

    Documento ativarDesativar(HttpServletRequest request, Integer idDocumento);

    List<DocumentoCsv> listarDocumentoParaCsv() throws Exception;

    List<Documento> findAll();

    List<Documento> findByAtivos();
}
