package com.configuradorlicenciamento.requisitoTecnico.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisitoTecnicoCsv implements Serializable {

    @CsvBindByName(column = "Código")
    @CsvBindByPosition(position = 0)
    private String codigo;

    @CsvBindByName(column = "Descrição")
    @CsvBindByPosition(position = 1)
    private String descricao;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 2)
    private String ativo;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 3)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 4)
    private String usuarioLicenciamento;


    public RequisitoTecnicoCsv(RequisitoTecnico requisitoTecnico) {

        this.codigo = requisitoTecnico.getCodigo();
        this.descricao = requisitoTecnico.getDescricao();
        this.dataCadastro = requisitoTecnico.getDataCadastro() != null ? DateUtil.formataBrSimples(requisitoTecnico.getDataCadastro()) : "-";
        this.ativo = requisitoTecnico.getAtivo() ? "Ativo" : "Inativo";
        this.usuarioLicenciamento = requisitoTecnico.getUsuarioLicenciamento() != null ? getNomeUsuario(requisitoTecnico.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}