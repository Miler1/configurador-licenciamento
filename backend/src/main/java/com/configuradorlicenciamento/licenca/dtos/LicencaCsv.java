package com.configuradorlicenciamento.licenca.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.licenca.utils.StringUtil;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LicencaCsv implements Serializable {

    @CsvBindByName(column = "Nomenclatura")
    private String nome;

    @CsvBindByName(column = "Validade em anos")
    private String validadeEmAnos;

    @CsvBindByName(column = "Tipo")
    private String sigla;

    @CsvBindByName(column = "Finalidade")
    private String finalidade;

    @CsvBindByName(column = "Usuário")
    private String usuarioLicenciamento;

    @CsvBindByName(column = "Data de cadastro")
    private String dataCadastro;

    public LicencaCsv(Licenca licenca) {

        this.nome = licenca.getNome();
        this.validadeEmAnos = licenca.getValidadeEmAnos() != null ? licenca.getValidadeEmAnos().toString() : "-";
        this.sigla = licenca.getSigla();
        this.finalidade = StringUtil.valueOfLabel(licenca.getFinalidade());
        this.dataCadastro = licenca.getDataCadastro() != null ? DateUtil.formataBrSimples(licenca.getDataCadastro()) : "-";
        this.usuarioLicenciamento = licenca.getUsuarioLicenciamento() != null ? getNomeUsuario(licenca.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }
}
