package com.configuradorlicenciamento.atividade.controllers;

import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelCsv;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelDTO;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelEdicaoDTO;
import com.configuradorlicenciamento.atividade.interfaces.IAtividadeLicenciavelService;
import com.configuradorlicenciamento.atividade.interfaces.IRascunhoService;
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
public class AtividadeLicenciavelController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IAtividadeLicenciavelService atividadeLicenciavelService;

    @Autowired
    IRascunhoService rascunhoService;

    @PostMapping(value = "salvar")
    public ResponseEntity<Atividade> salvar(HttpServletRequest request, @Valid @RequestBody AtividadeLicenciavelDTO atividadeLicenciavelDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Atividade atividade;

        //caso exista id para atividade, logo é um rascunho para finalizar cadastro
        if (atividadeLicenciavelDTO.getDados().getId() != null) {
            atividade = rascunhoService.finalizarCadastro(request, atividadeLicenciavelDTO);
        } else {
            atividade = atividadeLicenciavelService.salvar(request, atividadeLicenciavelDTO);
        }

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividade);

    }

    @PostMapping(value = "editar")
    public ResponseEntity<Atividade> editar(HttpServletRequest request, @Valid @RequestBody AtividadeLicenciavelDTO atividadeLicenciavelDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Atividade atividade = atividadeLicenciavelService.editar(request, atividadeLicenciavelDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividade);

    }

    @PostMapping(value = "listar")
    public ResponseEntity<Page<Atividade>> listar(HttpServletRequest request,
                                                  @PageableDefault(size = 20) Pageable pageable,
                                                  @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<Atividade> atividades = atividadeLicenciavelService.listar(pageable, filtroPesquisa);

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

        downloadCsv(atividadeLicenciavelService.listarAtividadesParaCsv(), nome, mappingStrategy, response);

    }

    @GetMapping(value = "findById/{idAtividadeLicenciavel}")
    public ResponseEntity<AtividadeLicenciavelEdicaoDTO> findById(HttpServletRequest request,
                                                                  @PathVariable("idAtividadeLicenciavel") Integer idAtividadeDispensavel) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        AtividadeLicenciavelEdicaoDTO atividadeLicenciavelEdicaoDTO = atividadeLicenciavelService.findById(idAtividadeDispensavel);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividadeLicenciavelEdicaoDTO);

    }

    @PostMapping(value = "ativarDesativar/{idAtividadeLicenciavel}")
    public ResponseEntity<Atividade> ativarDesativar(HttpServletRequest request, @PathVariable("idAtividadeLicenciavel") Integer idAtividadeLicenciavel) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Atividade atividade = atividadeLicenciavelService.ativarDesativar(request, idAtividadeLicenciavel);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividade);

    }

    @PostMapping(value = "salvarRascunho")
    public ResponseEntity<Atividade> salvarRascunho(HttpServletRequest request, @Valid @RequestBody AtividadeLicenciavelDTO atividadeLicenciavelDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Atividade atividade;

        if (atividadeLicenciavelDTO.getDados().getId() == null) {
            atividade = rascunhoService.salvar(request, atividadeLicenciavelDTO);
        } else {
            atividade = rascunhoService.editar(request, atividadeLicenciavelDTO);
        }

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividade);

    }

    @PostMapping(value = "excluirRascunho/{idAtividadeLicenciavel}")
    public ResponseEntity<String> excluirRascunho(HttpServletRequest request, @PathVariable("idAtividadeLicenciavel") Integer idAtividadeLicenciavel) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        rascunhoService.excluir(request, idAtividadeLicenciavel);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body("{validacao.deleteRascunho}");

    }

}