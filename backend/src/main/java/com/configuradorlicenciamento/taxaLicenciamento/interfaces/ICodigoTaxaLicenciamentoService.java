package com.configuradorlicenciamento.taxaLicenciamento.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.historicoConfigurador.models.HistoricoConfigurador;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoCsv;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoEdicaoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import javax.servlet.http.HttpServletRequest;


public interface ICodigoTaxaLicenciamentoService {

    CodigoTaxaLicenciamento salvar(HttpServletRequest request, CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO);

    CodigoTaxaLicenciamento editar(HttpServletRequest request, CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO);

    CodigoTaxaLicenciamento ativarDesativar(HttpServletRequest request, Integer idTaxaLicenciamento);

    Page<CodigoTaxaLicenciamento> listar(Pageable pageable, FiltroPesquisa filtro);

    List<CodigoTaxaLicenciamento> listarCodigoTaxaLicenciamento();

    List<CodigoTaxaLicenciamentoCsv> listarCodigoTaxaLicenciamentoParaCsv();

    CodigoTaxaLicenciamentoEdicaoDTO findById(Integer codigoTaxaLicenciamento);

}