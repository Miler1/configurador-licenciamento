package com.configuradorlicenciamento.documento.services;

import com.configuradorlicenciamento.documento.dtos.DocumentoDTO;
import com.configuradorlicenciamento.documento.interfaces.IDocumentoService;
import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.documento.repositories.DocumentoRepository;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class DocumentoService implements IDocumentoService {

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public Documento salvar(HttpServletRequest request, DocumentoDTO documentoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Documento documento = new Documento.DocumentoBuilder(documentoDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        documentoRepository.save(documento);

        return documento;

    }

}
