package com.configuradorlicenciamento.atividade.dtos;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeDispensavelCsv implements Serializable {

    @CsvBindByName(column = "Código")
    @CsvBindByPosition(position = 0)
    private String codigoCnae;

    @CsvBindByName(column = "Nome")
    @CsvBindByPosition(position = 1)
    private String descricaoCnae;

    @CsvBindByName(column = "Tipologia")
    @CsvBindByPosition(position = 2)
    private String tipologiaCnae;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 3)
    private String ativo;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 4)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 5)
    private String usuarioLicenciamento;

    public AtividadeDispensavelCsv(Atividade atividade) {

        this.codigoCnae = atividade.getAtividadesCnae().isEmpty() ? "-" : atividade.getAtividadesCnae().get(0).getAtividadeCnae().getCodigo();
        this.descricaoCnae = atividade.getNome();
        this.tipologiaCnae = atividade.getTipologia().getNome();
        this.ativo = atividade.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = atividade.getDataCadastro() != null ? DateUtil.formataBrSimples(atividade.getDataCadastro()) : "-";

        this.usuarioLicenciamento = atividade.getUsuarioLicenciamento() != null ? getNomeUsuario(atividade.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}
