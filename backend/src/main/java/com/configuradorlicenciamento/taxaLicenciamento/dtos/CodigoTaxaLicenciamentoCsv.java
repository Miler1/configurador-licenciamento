package com.configuradorlicenciamento.taxaLicenciamento.dtos;

import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;

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

    public CodigoTaxaLicenciamentoCsv(CodigoTaxaLicenciamento codigoTaxaLicenciamento) {
        this.codigo = codigoTaxaLicenciamento.getCodigo();
        this.descricao = codigoTaxaLicenciamento.getDescricao();
        this.status = codigoTaxaLicenciamento.getAtivo() ? "Ativo" : "Inativo";
//        this.dataCadastro = codigoTaxaLicenciamento.getDataCadastro() != null ? DateUtil.formataBrSimples(codigoTaxaLicenciamento.getDataCadastro()) : "-";
//        this.usuarioLicenciamento = codigoTaxaLicenciamento.getUsuarioLicenciamento() != null ? getNomeUsuario(codigoTaxaLicenciamento.getUsuarioLicenciamento()) : "-";
    }

//    private String getNomeUsuario(UsuarioLicenciamento usuario){
//        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
//    }

}
