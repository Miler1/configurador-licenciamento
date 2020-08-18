package com.configuradorlicenciamento.atividadeCnae.dtos;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeCnaeCsv implements Serializable {

    @CsvBindByName(column = "Nome")
    @CsvBindByPosition(position = 1)
    private String nome;

    @CsvBindByName(column = "Código")
    @CsvBindByPosition(position = 0)
    private String codigo;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 2)
    private String ativo;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 3)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 4)
    private String usuarioLicenciamento;

    public AtividadeCnaeCsv(AtividadeCnae cnae) {

        this.codigo = cnae.getCodigo();
        this.nome = cnae.getNome();
        this.ativo = cnae.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = cnae.getDataCadastro() != null ? DateUtil.formataBrSimples(cnae.getDataCadastro()) : "-";

        this.usuarioLicenciamento = cnae.getUsuarioLicenciamento() != null ? getNomeUsuario(cnae.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}
