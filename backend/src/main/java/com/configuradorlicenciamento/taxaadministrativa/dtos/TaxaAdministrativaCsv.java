package com.configuradorlicenciamento.taxaadministrativa.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
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

    @CsvBindByName(column = "Valor")
    @CsvBindByPosition(position = 1)
    private Float valor;

    @CsvBindByName(column = "Atividade Dispensável")
    @CsvBindByPosition(position = 2)
    private String atividadeDispensavel;

    @CsvBindByName(column = "Atividade Licenciável")
    @CsvBindByPosition(position = 3)
    private String atividadeLicenciavel;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 4)
    private String ativo;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 5)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 6)
    private String usuarioLicenciamento;

    public TaxaAdministrativaCsv(TaxaAdministrativa taxaAdministrativa) {
        this.ano = taxaAdministrativa.getAno();
        this.valor = taxaAdministrativa.getValor();
        this.atividadeDispensavel = taxaAdministrativa.getAtividadeDispensavel() ? "Sim" : "Não";
        this.atividadeLicenciavel = taxaAdministrativa.getAtividadeLicenciavel() ? "Sim" : "Não";
        this.ativo = taxaAdministrativa.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = taxaAdministrativa.getDataCadastro() != null ? DateUtil.formataBrSimples(taxaAdministrativa.getDataCadastro()) : "-";
        this.usuarioLicenciamento = taxaAdministrativa.getUsuarioLicenciamento() != null ? getNomeUsuario(taxaAdministrativa.getUsuarioLicenciamento()) : "-";
    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}
