package com.configuradorlicenciamento.parametro.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.parametro.models.Parametro;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParametroCsv {

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 0)
    private String codigo;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 1)
    private String nome;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 2)
    private Integer casasDecimais;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 3)
    private String ativo;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 4)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 5)
    private String usuarioLicenciamento;

    public ParametroCsv(Parametro parametro) {

        this.codigo = parametro.getCodigo();
        this.nome = parametro.getNome();
        this.casasDecimais = parametro.getCasasDecimais();
        this.ativo = parametro.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = parametro.getDataCadastro() != null ? DateUtil.formataBrSimples(parametro.getDataCadastro()) : "-";

        this.usuarioLicenciamento = parametro.getUsuarioLicenciamento() != null ? getNomeUsuario(parametro.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}
