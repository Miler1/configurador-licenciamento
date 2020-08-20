package com.configuradorlicenciamento.tipologia.controllers;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaCsv;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
import com.configuradorlicenciamento.tipologia.interfaces.ITipologiaService;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
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
@RequestMapping("/tipologia")
public class TipologiaController extends DefaultController {

    @Autowired
    ITipologiaService tipologiaService;

    @RequestMapping(method = RequestMethod.POST, value = "/salvar")
    public ResponseEntity<Tipologia> salvar (HttpServletRequest request, @Valid @RequestBody TipologiaDTO tipologiaDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Tipologia tipologia = tipologiaService.salvar(request, tipologiaDTO);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(tipologia);

    }

    @RequestMapping(method = RequestMethod.POST, value="/editar")
    public ResponseEntity<Tipologia> editar(HttpServletRequest request, @Valid @RequestBody TipologiaDTO tipologiaDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Tipologia tipologia = tipologiaService.editar(request, tipologiaDTO);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(tipologia);

    }

    @GetMapping("/relatorio")
    public void relatorioCSV (HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_Tipologias_" + data + ".csv";

        CustomMappingStrategy<TipologiaCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(TipologiaCsv.class);

        downloadCsv(tipologiaService.listarTipologiaParaCsv(), nome, mappingStrategy, response);
    }

    @RequestMapping(method = RequestMethod.POST, value="/lista")
    public ResponseEntity<Page<Tipologia>> lista(HttpServletRequest request,
                                                     @PageableDefault(size = 20) Pageable pageable,
                                                     @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<Tipologia> tipologias = tipologiaService.lista(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", VariaveisAmbientes.baseUrlFrontend())
                .body(tipologias);

    }

}
