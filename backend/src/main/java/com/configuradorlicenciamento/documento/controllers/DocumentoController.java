package com.configuradorlicenciamento.documento.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.documento.dtos.DocumentoCsv;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.documento.dtos.DocumentoDTO;
import com.configuradorlicenciamento.documento.interfaces.IDocumentoService;
import com.configuradorlicenciamento.documento.models.Documento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IDocumentoService documentoService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<Documento> salvar (HttpServletRequest request, @Valid @RequestBody DocumentoDTO documentoDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Documento documento = documentoService.salvar(request, documentoDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(documento);

    }

    @GetMapping("/relatorio")
    public void relatorioCSV (HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_Documentos_" + data + ".csv";

        CustomMappingStrategy<DocumentoCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(DocumentoCsv.class);

        downloadCsv(documentoService.listarDocumentoParaCsv(), nome, mappingStrategy, response);
    }
    
    @PostMapping(value="/listar")
    public ResponseEntity<Page<Documento>> listar(HttpServletRequest request,
                                                 @PageableDefault(size = 20) Pageable pageable,
                                                 @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<Documento> documentos = documentoService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(documentos);

    }

    @GetMapping( value="/findAll")
    public ResponseEntity<List<Documento>> findAll(HttpServletRequest request) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        List<Documento> documentos = documentoService.findAll();

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(documentos);

    }

    @PostMapping(value = "/editar")
    public ResponseEntity <Documento> editar(HttpServletRequest request,
                                             @Valid @RequestBody DocumentoDTO documentoDTO) throws Exception{

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Documento documento = documentoService.editar(request, documentoDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(documento);
    }

}
