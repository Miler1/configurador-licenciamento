package com.configuradorlicenciamento.atividadeCnae.dtos;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeCnaeCsv implements Serializable {

    @CsvBindByName(column = "Nome")
    private String nome;

    @CsvBindByName(column = "Código")
    private String codigo;

    @CsvBindByName(column = "Ativo")
    private String ativo;

    @CsvBindByName(column = "Data de cadastro")
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    private String usuarioLicenciamento;

    public AtividadeCnaeCsv(AtividadeCnae cnae) {

        this.codigo = cnae.getCodigo();
        this.nome = cnae.getNome();
        this.ativo = cnae.getAtivo() ? "Ativo" : "Inativo";
        Date teste = cnae.getDataCadastro();
        this.dataCadastro = cnae.getDataCadastro() != null ? DateUtil.formataBrSimples(cnae.getDataCadastro()) : " -> Sem data <-";
        this.usuarioLicenciamento = cnae.getUsuarioLicenciamento() != null ? getNomeUsuario(cnae.getUsuarioLicenciamento()) : " -> Sem usuário <-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarUsuarioPorLogin(usuario.getLogin()).nome;
    }

}
