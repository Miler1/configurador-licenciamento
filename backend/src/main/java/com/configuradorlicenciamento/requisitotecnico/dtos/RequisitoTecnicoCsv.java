package com.configuradorlicenciamento.requisitotecnico.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisitoTecnicoCsv implements Serializable {

    @CsvBindByName(column = "Codigo")
    @CsvBindByPosition(position = 0)
    private String codigo;

    @CsvBindByName(column = "Descrição")
    @CsvBindByPosition(position = 1)
    private String descricao;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 2)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 3)
    private String usuarioLicenciamento;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 4)
    private String status;

    public RequisitoTecnicoCsv(RequisitoTecnico requisitoTecnico) {

        this.codigo = requisitoTecnico.getCodigo();
        this.descricao = requisitoTecnico.getDescricao();
        this.dataCadastro = requisitoTecnico.getDataCadastro() != null ? DateUtil.formataBrSimples(requisitoTecnico.getDataCadastro()) : "-";
        this.status = requisitoTecnico.getAtivo() ? "Ativo" : "Inativo";
        this.usuarioLicenciamento = requisitoTecnico.getUsuarioLicenciamento() != null ? getNomeUsuario(requisitoTecnico.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}
