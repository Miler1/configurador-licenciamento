package com.configuradorlicenciamento.documento.interfaces;

import com.configuradorlicenciamento.documento.dtos.DocumentoCsv;
import com.configuradorlicenciamento.documento.dtos.DocumentoDTO;
import com.configuradorlicenciamento.documento.models.Documento;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IDocumentoService {

    Documento salvar(HttpServletRequest request, DocumentoDTO documentoDTO);

    List<DocumentoCsv> listarDocumentoParaCsv() throws Exception;
}
