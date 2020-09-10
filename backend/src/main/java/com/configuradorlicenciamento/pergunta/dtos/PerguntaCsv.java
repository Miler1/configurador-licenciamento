package com.configuradorlicenciamento.pergunta.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerguntaCsv implements Serializable {

    @CsvBindByName(column = "Pergunta")
    @CsvBindByPosition(position = 0)
    private String texto;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 1)
    private String ativo;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 2)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 3)
    private String usuarioLicenciamento;

    public PerguntaCsv(Pergunta pergunta) {

        this.texto = pergunta.getTexto();
        this.ativo = pergunta.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = pergunta.getDataCadastro() != null ? DateUtil.formataBrSimples(pergunta.getDataCadastro()) : "-";

        this.usuarioLicenciamento = pergunta.getUsuarioLicenciamento() != null ? getNomeUsuario(pergunta.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}
