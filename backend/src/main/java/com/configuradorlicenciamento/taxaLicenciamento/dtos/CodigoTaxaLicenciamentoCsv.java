package com.configuradorlicenciamento.taxaLicenciamento.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodigoTaxaLicenciamentoCsv implements Serializable {

    @CsvBindByName(column = "Código")
    @CsvBindByPosition(position = 0)
    private String codigo;

    @CsvBindByName(column = "Descrição")
    @CsvBindByPosition(position = 1)
    private String descricao;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 2)
    private String status;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 3)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 4)
    private String usuarioLicenciamento;

    public CodigoTaxaLicenciamentoCsv(CodigoTaxaLicenciamento codigoTaxaLicenciamento, Date dataCadastro, UsuarioLicenciamento usuarioLicenciamento) {
        this.codigo = codigoTaxaLicenciamento.getCodigo();
        this.descricao = codigoTaxaLicenciamento.getDescricao();
        this.status = codigoTaxaLicenciamento.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = dataCadastro != null ? DateUtil.formataBrSimples(dataCadastro) : "-";
        this.usuarioLicenciamento = usuarioLicenciamento != null ? getNomeUsuario(usuarioLicenciamento) : "-";
    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}