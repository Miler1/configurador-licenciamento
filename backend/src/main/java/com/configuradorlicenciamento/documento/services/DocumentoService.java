package com.configuradorlicenciamento.documento.services;

import com.configuradorlicenciamento.documento.dtos.DocumentoCsv;
import com.configuradorlicenciamento.documento.dtos.DocumentoDTO;
import com.configuradorlicenciamento.documento.interfaces.IDocumentoService;
import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.documento.repositories.DocumentoRepository;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaCsv;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        String nome = documentoDTO.getNome();

        if (documentoRepository.existsByNome(nome)) {

            throw new RuntimeException("Um Ducumento com o nome '" + nome + "' já está cadastrado.");
        }

        Documento documento = new Documento.DocumentoBuilder(documentoDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        documentoRepository.save(documento);

        return documento;

    }

    @Override
    public List<DocumentoCsv> listarDocumentoParaCsv() throws Exception {

        List<Documento> documentos = documentoRepository.findAll(Sort.by("nome"));
        List<DocumentoCsv> dtos = new ArrayList<>();

        for (Documento documento : documentos) {
            dtos.add(documento.preparaParaCsv());
        }

        return dtos;
    }
}
