package com.configuradorlicenciamento.tipologia.dtos;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipologiaCsv implements Serializable {

    @CsvBindByName(column = "Nome", required = true)
    @CsvBindByPosition(position = 1)
    private String nome;

    @CsvBindByName(column = "Código", required = true)
    @CsvBindByPosition(position = 0)
    private String codigo;

    @CsvBindByName(column = "Status", required = true)
    @CsvBindByPosition(position = 2)
    private String ativo;

    @CsvBindByName(column = "Data de cadastro", required = true)
    @CsvBindByPosition(position = 3)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário", required = true)
    @CsvBindByPosition(position = 4)
    private String usuarioLicenciamento;

    public TipologiaCsv(Tipologia tipologia) {

        this.codigo = tipologia.getCodigo();
        this.nome = tipologia.getNome();
        this.ativo = tipologia.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = tipologia.getDataCadastro() != null ? DateUtil.formataBrSimples(tipologia.getDataCadastro()) : "-";

        this.usuarioLicenciamento = tipologia.getUsuarioLicenciamento() != null ? getNomeUsuario(tipologia.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}
