package com.configuradorlicenciamento.licenca.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.licenca.enums.Finalidade;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LicencaCsv implements Serializable {

    @CsvBindByName(column = "Nomenclatura")
    @CsvBindByPosition(position = 1)
    private String nome;

    @CsvBindByName(column = "Prazo de validade (anos)")
    @CsvBindByPosition(position = 3)
    private String validadeEmAnos;

    @CsvBindByName(column = "Tipo")
    @CsvBindByPosition(position = 0)
    private String sigla;

    @CsvBindByName(column = "Finalidade")
    @CsvBindByPosition(position = 2)
    private String finalidade;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 4)
    private String status;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 6)
    private String usuarioLicenciamento;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 5)
    private String dataCadastro;

    public LicencaCsv(Licenca licenca) {

        this.nome = licenca.getNome();
        this.validadeEmAnos = licenca.getValidadeEmAnos() != null ? licenca.getValidadeEmAnos().toString() : "-";
        this.sigla = licenca.getSigla();
        this.finalidade = licenca.getFinalidade() != null ? Finalidade.valueOfLabel(licenca.getFinalidade()) : "-";
        this.dataCadastro = licenca.getDataCadastro() != null ? DateUtil.formataBrSimples(licenca.getDataCadastro()) : "-";
        this.status = licenca.getAtivo() ? "Ativo" : "Inativo";
        this.usuarioLicenciamento = licenca.getUsuarioLicenciamento() != null ? getNomeUsuario(licenca.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }
}
