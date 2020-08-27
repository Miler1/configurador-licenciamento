package com.configuradorlicenciamento.requisitotecnico.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitotecnico.dtos.RequisitoTecnicoDTO;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.requisitotecnico.dtos.RequisitoTecnicoCsv;
import com.configuradorlicenciamento.requisitotecnico.dtos.RequisitoTecnicoEdicaoDTO;
import com.configuradorlicenciamento.requisitotecnico.interfaces.IRequisitoTecnicoService;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/requisitoTecnico")
public class RequisitoTecnicoController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IRequisitoTecnicoService requisitoTecnicoService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<RequisitoTecnico> salvar(HttpServletRequest request, @Valid @RequestBody RequisitoTecnicoDTO requisitoTecnicoDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        RequisitoTecnico requisitoTecnico = requisitoTecnicoService.salvar(request, requisitoTecnicoDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitoTecnico);

    }

    @PostMapping(value = "/editar")
    public ResponseEntity<RequisitoTecnico> editar(HttpServletRequest request, @Valid @RequestBody RequisitoTecnicoDTO requisitoTecnicoDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        RequisitoTecnico requisitoTecnico = requisitoTecnicoService.editar(request, requisitoTecnicoDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitoTecnico);

    }

    @PostMapping(value = "/ativarDesativar/{idRequisito}")
    public ResponseEntity<RequisitoTecnico> ativarDesativar(HttpServletRequest request, @PathVariable("idRequisito") Integer idRequisito) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        RequisitoTecnico requisitoTecnico = requisitoTecnicoService.ativarDesativar(idRequisito);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitoTecnico);

    }

    @PostMapping(value = "/listar")
    public ResponseEntity<Page<RequisitoTecnico>> lista(HttpServletRequest request,
                                                        @PageableDefault(size = 20) Pageable pageable,
                                                        @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<RequisitoTecnico> requisitosTecnicos = requisitoTecnicoService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitosTecnicos);

    }

    @GetMapping("/relatorio")
    public void relatorioCSV (HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_Requisitos_Tecnicos_" + data + ".csv";

        CustomMappingStrategy<RequisitoTecnicoCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(RequisitoTecnicoCsv.class);

        downloadCsv(requisitoTecnicoService.listarDocumentoParaCsv(), nome, mappingStrategy, response);
    }

    @GetMapping(value = "/findById/{idRequisito}")
    public ResponseEntity<RequisitoTecnicoEdicaoDTO> findById(HttpServletRequest request,
                                                        @PathVariable("idRequisito") Integer idRequisito) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        RequisitoTecnicoEdicaoDTO requisitoTecnicoDTO = requisitoTecnicoService.findById(idRequisito);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitoTecnicoDTO);

    }

}
