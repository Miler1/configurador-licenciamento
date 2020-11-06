package com.configuradorlicenciamento.atividade.controllers;

import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.atividade.interfaces.IAtividadeDispensavelService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelDTO;
import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelEdicaoDTO;
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
@RequestMapping("/atividadeDispensavel/")
public class AtividadeDispensavelController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    IAtividadeDispensavelService atividadeDispensavelService;

    @PostMapping(value = "salvar")
    public ResponseEntity<List<Atividade>> salvarAtividadeDispensavel(HttpServletRequest request, @Valid @RequestBody AtividadeDispensavelDTO atividadeDispensavelDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        List<Atividade> atividades = atividadeDispensavelService.salvarAtividadeDispensavel(request, atividadeDispensavelDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividades);

    }

    @PostMapping(value = "editar")
    public ResponseEntity<Atividade> editar(HttpServletRequest request, @Valid @RequestBody AtividadeDispensavelDTO atividadeDispensavelDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Atividade atividade = atividadeDispensavelService.editarAtividadeDispensavel(request, atividadeDispensavelDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividade);

    }

    @PostMapping(value = "listar")
    public ResponseEntity<Page<Atividade>> listar(HttpServletRequest request,
                                                                        @PageableDefault(size = 20) Pageable pageable,
                                                                        @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<Atividade> atividades = atividadeDispensavelService.listarAtividadesDispensaveis(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividades);

    }

    @GetMapping(value = "relatorio")
    public void relatorioCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_de_CNAEs_dispensaveis_" + data + ".csv";

        CustomMappingStrategy<AtividadeDispensavelCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(AtividadeDispensavelCsv.class);

        downloadCsv(atividadeDispensavelService.listarAtividadesDispensaveisParaCsv(), nome, mappingStrategy, response);
    }

    @GetMapping(value = "findById/{idAtividadeDispensavel}")
    public ResponseEntity<AtividadeDispensavelEdicaoDTO> findById(HttpServletRequest request,
                                                                  @PathVariable("idAtividadeDispensavel") Integer idAtividadeDispensavel) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        AtividadeDispensavelEdicaoDTO atividadeDispensavelEdicaoDTO = atividadeDispensavelService.findById(idAtividadeDispensavel);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividadeDispensavelEdicaoDTO);

    }

    @PostMapping(value = "ativarDesativar/{idAtividadeDispensavel}")
    public ResponseEntity<Atividade> ativarDesativar(HttpServletRequest request, @PathVariable("idAtividadeDispensavel") Integer idAtividadeDispensavel) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Atividade atividade = atividadeDispensavelService.ativarDesativar(idAtividadeDispensavel);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividade);

    }

}
