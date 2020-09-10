package com.configuradorlicenciamento.requisitoAdministrativo.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisitoAdministrativoCsv {

    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "Documento")
    private String documento;

    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "Tipo da licença")
    private String licenca;

    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "Pessoa")
    private String tipoPessoa;

    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "Tipo do requisito")
    private String obrigatorio;

    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "Status")
    private String status;

    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "Data de Cadastro")
    private String dataCadastro;

    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "Usuário")
    private String usuarioLicenciamento;

    public RequisitoAdministrativoCsv(RequisitoAdministrativo requisitoAdministrativo) {

        this.documento = requisitoAdministrativo.getDocumento().getNome();
        this.licenca = requisitoAdministrativo.getLicenca().getSigla();
        this.tipoPessoa = requisitoAdministrativo.getTipoPessoa().equals("PF") ? "Física" : "Jurídica";
        this.obrigatorio = requisitoAdministrativo.getObrigatorio() ? "Básico" : "Complementar";
        this.status = requisitoAdministrativo.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = requisitoAdministrativo.getDataCadastro() != null ?
                DateUtil.formataBrSimples(requisitoAdministrativo.getDataCadastro()) : "-";

        this.usuarioLicenciamento = requisitoAdministrativo.getUsuarioLicenciamento() != null ?
                getNomeUsuario(requisitoAdministrativo.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario) {
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}
