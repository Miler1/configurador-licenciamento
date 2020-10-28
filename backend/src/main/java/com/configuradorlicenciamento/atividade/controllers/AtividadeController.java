package com.configuradorlicenciamento.atividade.controllers;

import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelCsv;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelDTO;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelEdicaoDTO;
import com.configuradorlicenciamento.atividade.interfaces.IAtividadeService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
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

@RestController
@RequestMapping("/atividadeLicenciavel/")
public class AtividadeController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IAtividadeService atividadeService;

    @PostMapping(value = "salvar")
    public ResponseEntity<Atividade> salvarAtividadeLicenciavel(HttpServletRequest request, @Valid @RequestBody AtividadeLicenciavelDTO atividadeLicenciavelDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Atividade atividade = atividadeService.salvarAtividadeLicenciavel(request, atividadeLicenciavelDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividade);

    }

    @PostMapping(value = "editar")
    public ResponseEntity<Atividade> editar(HttpServletRequest request, @Valid @RequestBody AtividadeLicenciavelDTO atividadeLicenciavelDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Atividade atividade = atividadeService.editarAtividadeLicenciavel(request, atividadeLicenciavelDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividade);

    }

    @PostMapping(value = "salvarRascunho")
    public ResponseEntity<Atividade> salvarRascunhoAtividadeLicenciavel(HttpServletRequest request, @Valid @RequestBody AtividadeLicenciavelDTO atividadeLicenciavelDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Atividade atividade = atividadeService.salvarRascunhoAtividadeLicenciavel(request, atividadeLicenciavelDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividade);

    }

    @PostMapping(value = "listar")
    public ResponseEntity<Page<Atividade>> listarAtividadesLicenciaveis(HttpServletRequest request,
                                                                        @PageableDefault(size = 20) Pageable pageable,
                                                                        @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<Atividade> atividades = atividadeService.listarAtividadesLicenciaveis(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividades);

    }

    @GetMapping(value = "relatorio")
    public void relatorioCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_de_atividades_licenciaveis_" + data + ".csv";

        CustomMappingStrategy<AtividadeLicenciavelCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(AtividadeLicenciavelCsv.class);

        downloadCsv(atividadeService.listarAtividadesLicenciaveisParaCsv(), nome, mappingStrategy, response);
    }

    @GetMapping(value = "findById/{idAtividadeLicenciavel}")
    public ResponseEntity<AtividadeLicenciavelEdicaoDTO> findById(HttpServletRequest request,
                                                                  @PathVariable("idAtividadeLicenciavel") Integer idAtividadeDispensavel) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        AtividadeLicenciavelEdicaoDTO atividadeLicenciavelEdicaoDTO = atividadeService.findById(idAtividadeDispensavel);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividadeLicenciavelEdicaoDTO);

    }

}
