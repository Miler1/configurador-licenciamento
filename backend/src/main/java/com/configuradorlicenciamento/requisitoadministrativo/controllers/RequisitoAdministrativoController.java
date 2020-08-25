package com.configuradorlicenciamento.requisitoadministrativo.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;

import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.requisitoadministrativo.dtos.RequisitoAdministrativoCsv;
import com.configuradorlicenciamento.requisitoadministrativo.dtos.RequisitoAdministrativoDTO;
import com.configuradorlicenciamento.requisitoadministrativo.interfaces.IRequisitoAdministrativoService;
import com.configuradorlicenciamento.requisitoadministrativo.models.RequisitoAdministrativo;
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
@RequestMapping("/requisitoAdministrativo")
public class RequisitoAdministrativoController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IRequisitoAdministrativoService requisitoAdministrativoService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<List<RequisitoAdministrativo>> salvar(HttpServletRequest request, @Valid @RequestBody RequisitoAdministrativoDTO requisitoAdministrativoDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        List<RequisitoAdministrativo> requisitoAdministrativoList = requisitoAdministrativoService.salvar(request, requisitoAdministrativoDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitoAdministrativoList);

    }

    @PostMapping(value="/editar")
    public ResponseEntity<RequisitoAdministrativo> editar(HttpServletRequest request, @Valid @RequestBody RequisitoAdministrativoDTO requisitoAdministrativoDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        RequisitoAdministrativo requisitoAdministrativo = requisitoAdministrativoService.editar(request, requisitoAdministrativoDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitoAdministrativo);

    }

    @PostMapping(value="/listar")
    public ResponseEntity<Page<RequisitoAdministrativo>> listar(HttpServletRequest request,
                                                  @PageableDefault(size = 20) Pageable pageable,
                                                  @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<RequisitoAdministrativo> requisitosAdministrativos = requisitoAdministrativoService.listar(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(requisitosAdministrativos);

    }

    @GetMapping("/relatorio")
    public void relatorioCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_Requisito_Administrativo_" + data + ".csv";

        CustomMappingStrategy<RequisitoAdministrativoCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(RequisitoAdministrativoCsv.class);

        downloadCsv(requisitoAdministrativoService.listarRequisitosAdministrativosParaCsv(), nome, mappingStrategy, response);

    }

}
