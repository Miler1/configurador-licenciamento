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
import java.util.Date;

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

    public AtividadeDispensavelCsv(Atividade atividade, Date dataCadastro, UsuarioLicenciamento usuarioLicenciamento) {

        this.codigoCnae = atividade.getAtividadesCnae().isEmpty() ? "-" : atividade.getAtividadesCnae().get(0).getAtividadeCnae().getCodigo();
        this.descricaoCnae = atividade.getNome();
        this.tipologiaCnae = atividade.getTipologia().getNome();
        this.ativo = atividade.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = dataCadastro != null ? DateUtil.formataBrSimples(dataCadastro) : "-";
        this.usuarioLicenciamento = usuarioLicenciamento != null ? getNomeUsuario(usuarioLicenciamento) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario) {
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

}