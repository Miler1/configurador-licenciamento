package com.configuradorlicenciamento.documento.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentoCsv implements Serializable {

    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "Nome")
    private String nome;

    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "Nome para exibição")
    private String nomeExibicao;

    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "Status")
    private String status;

    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "Data de cadastro")
    private String dataCadastro;

    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "Usuário")
    private String usuarioLicenciamento;

    public DocumentoCsv(Documento documento) {

        this.nome = documento.getNome().replace("–", "-");
        this.nomeExibicao = documento.getPrefixoNomeArquivo();
        this.status = documento.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = documento.getDataCadastro() != null ? DateUtil.formataBrSimples(documento.getDataCadastro()) : "-";

        this.usuarioLicenciamento = documento.getUsuarioLicenciamento() != null ? getNomeUsuario(documento.getUsuarioLicenciamento()) : "-";
    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}
