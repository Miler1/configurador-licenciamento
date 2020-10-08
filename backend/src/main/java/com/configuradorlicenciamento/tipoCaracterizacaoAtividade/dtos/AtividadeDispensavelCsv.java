package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos;

import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
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

    public AtividadeDispensavelCsv(TipoCaracterizacaoAtividade tipoCaracterizacaoAtividade) {

        this.codigoCnae = tipoCaracterizacaoAtividade.getAtividadeCnae().getCodigo();
        this.descricaoCnae = tipoCaracterizacaoAtividade.getAtividadeCnae().getNome();
        this.tipologiaCnae = tipoCaracterizacaoAtividade.getAtividade().getTipologia().getNome();
        this.ativo = tipoCaracterizacaoAtividade.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = tipoCaracterizacaoAtividade.getDataCadastro() != null ? DateUtil.formataBrSimples(tipoCaracterizacaoAtividade.getDataCadastro()) : "-";

        this.usuarioLicenciamento = tipoCaracterizacaoAtividade.getUsuarioLicenciamento() != null ? getNomeUsuario(tipoCaracterizacaoAtividade.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }
}
