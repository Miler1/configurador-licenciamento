package com.configuradorlicenciamento.taxaAdministrativa.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.taxaAdministrativa.models.TaxaAdministrativa;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxaAdministrativaCsv implements Serializable {

    @CsvBindByName(column = "Ano")
    @CsvBindByPosition(position = 0)
    private Integer ano;

    @CsvBindByName(column = "Isento")
    @CsvBindByPosition(position = 1)
    private String isento;

    @CsvBindByName(column = "Valor")
    @CsvBindByPosition(position = 2)
    private String valor;

    @CsvBindByName(column = "Atividades dispensáveis")
    @CsvBindByPosition(position = 3)
    private String atividadeDispensavel;

    @CsvBindByName(column = "Atividades licenciáveis")
    @CsvBindByPosition(position = 4)
    private String atividadeLicenciavel;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 5)
    private String ativo;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 6)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 7)
    private String usuarioLicenciamento;

    public TaxaAdministrativaCsv(TaxaAdministrativa taxaAdministrativa) {

        this.ano = taxaAdministrativa.getAno();
        this.isento = taxaAdministrativa.getIsento() ? "Sim" : "Não";
        this.valor = StringUtil.formatarDecimal(taxaAdministrativa.getValor());
        this.atividadeDispensavel = taxaAdministrativa.getAtividadeDispensavel() == null ?
                "-" : taxaAdministrativa.getAtividadeDispensavel() ? "Sim" : "Não";
        this.atividadeLicenciavel = taxaAdministrativa.getAtividadeLicenciavel() == null ?
                "-" : taxaAdministrativa.getAtividadeLicenciavel() ? "Sim" : "Não";
        this.ativo = taxaAdministrativa.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = taxaAdministrativa.getDataCadastro() != null ? DateUtil.formataBrSimples(taxaAdministrativa.getDataCadastro()) : "-";
        this.usuarioLicenciamento = taxaAdministrativa.getUsuarioLicenciamento() != null ? getNomeUsuario(taxaAdministrativa.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}
