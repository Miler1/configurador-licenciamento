package com.configuradorlicenciamento.documento.interfaces;

import com.configuradorlicenciamento.documento.dtos.DocumentoDTO;
import com.configuradorlicenciamento.documento.models.Documento;

import javax.servlet.http.HttpServletRequest;

public interface IDocumentoService {

    Documento salvar(HttpServletRequest request, DocumentoDTO documentoDTO);
}
