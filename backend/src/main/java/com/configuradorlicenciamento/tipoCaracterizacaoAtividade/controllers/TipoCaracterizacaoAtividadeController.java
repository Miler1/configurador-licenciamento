package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.controllers;

import com.configuradorlicenciamento.configuracao.components.VariaveisAmbientes;
import com.configuradorlicenciamento.configuracao.controllers.DefaultController;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.csv.CustomMappingStrategy;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelDTO;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelEdicaoDTO;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces.ITipoCaracterizacaoAtividadeService;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
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
@RequestMapping("/tipoCaracterizacaoAtividade/atividadeDispensavel/")
public class TipoCaracterizacaoAtividadeController extends DefaultController {

    private static final String HEADER_CORS = "Access-Control-Allow-Origin";

    @Autowired
    ITipoCaracterizacaoAtividadeService tipoCaracterizacaoAtividadeService;

    @PostMapping(value = "salvar")
    public ResponseEntity<List<TipoCaracterizacaoAtividade>> salvarAtividadeDispensavel(HttpServletRequest request, @Valid @RequestBody AtividadeDispensavelDTO atividadeDispensavelDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        List<TipoCaracterizacaoAtividade> tipoCaracterizacoesAtividades = tipoCaracterizacaoAtividadeService.salvarAtividadeDispensavel(request, atividadeDispensavelDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(tipoCaracterizacoesAtividades);

    }

    @PostMapping(value = "editar")
    public ResponseEntity<TipoCaracterizacaoAtividade> editar(HttpServletRequest request,  @Valid @RequestBody AtividadeDispensavelDTO atividadeDispensavelDTO) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        TipoCaracterizacaoAtividade tipoCaracterizacaoAtividade = tipoCaracterizacaoAtividadeService.editar(request, atividadeDispensavelDTO);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(tipoCaracterizacaoAtividade);

    }

    @PostMapping(value = "ativarDesativar/{idAtividadeDispensavel}")
    public ResponseEntity<TipoCaracterizacaoAtividade> ativarDesativar(HttpServletRequest request, @PathVariable("idAtividadeDispensavel") Integer idAtividadeDispensavel) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        TipoCaracterizacaoAtividade tipoCaracterizacaoAtividade = tipoCaracterizacaoAtividadeService.ativarDesativar(idAtividadeDispensavel);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(tipoCaracterizacaoAtividade);

    }

    @PostMapping(value = "listar")
    public ResponseEntity<Page<TipoCaracterizacaoAtividade>> listarAtividadesDispensaveis(HttpServletRequest request,
                                                                                          @PageableDefault(size = 20) Pageable pageable,
                                                                                          @RequestBody FiltroPesquisa filtroPesquisa) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        Page<TipoCaracterizacaoAtividade> tiposCaracterizacaoAtividade = tipoCaracterizacaoAtividadeService.listarAtividadesDispensaveis(pageable, filtroPesquisa);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(tiposCaracterizacaoAtividade);

    }

    @GetMapping(value = "relatorio")
    public void atividadeDispensavelRelatorioCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        String data = DateUtil.formataBrHoraMinuto(new Date());
        String nome = "Relatorio_de_CNAEs_dispensaveis_" + data + ".csv";

        CustomMappingStrategy<AtividadeDispensavelCsv> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(AtividadeDispensavelCsv.class);

        downloadCsv(tipoCaracterizacaoAtividadeService.listarAtividadesDispensaveisParaCsv(), nome, mappingStrategy, response);

    }

    @GetMapping(value = "findById/{idAtividadeDispensavel}")
    public ResponseEntity<AtividadeDispensavelEdicaoDTO> findById(HttpServletRequest request,
                                                                  @PathVariable("idAtividadeDispensavel") Integer idAtividadeDispensavel) throws Exception {

        verificarPermissao(request, Acao.GERENCIAR_LICENCIAMENTO);

        AtividadeDispensavelEdicaoDTO atividadeDispensavelEdicaoDTO = tipoCaracterizacaoAtividadeService.findById(idAtividadeDispensavel);

        return ResponseEntity.ok()
                .header(HEADER_CORS, VariaveisAmbientes.baseUrlFrontend())
                .body(atividadeDispensavelEdicaoDTO);

    }

}