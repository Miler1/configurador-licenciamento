package com.configuradorlicenciamento.atividade.dtos;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.RelAtividadeParametroAtividade;
import com.configuradorlicenciamento.configuracao.utils.DateUtil;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeLicenciavelCsv implements Serializable {

    @CsvBindByName(column = "Código")
    @CsvBindByPosition(position = 0)
    private final String codigo;

    @CsvBindByName(column = "Nome")
    @CsvBindByPosition(position = 1)
    private final String nome;

    @CsvBindByName(column = "Tipologia")
    @CsvBindByPosition(position = 2)
    private final String tipologia;

    @CsvBindByName(column = "Parâmetros")
    @CsvBindByPosition(position = 3)
    private final String parametros;

    @CsvBindByName(column = "Tipos de licenças")
    @CsvBindByPosition(position = 4)
    private final String tiposLicencas;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 5)
    private final String ativo;

    @CsvBindByName(column = "Data de cadastro")
    @CsvBindByPosition(position = 6)
    private String dataCadastro;

    @CsvBindByName(column = "Usuário")
    @CsvBindByPosition(position = 7)
    private String usuarioLicenciamento;

    public AtividadeLicenciavelCsv(Atividade atividade) {

        this.codigo = atividade.getCodigo();
        this.nome = atividade.getNome();
        this.tipologia = atividade.getTipologia().getNome();
        this.parametros = tratarParametrosParaCsv(atividade.getParametros());
        this.tiposLicencas = tratarLicencasParaCsv(atividade.getTiposLicencas());
        this.ativo = atividade.getAtivo() ? "Ativo" : "Inativo";
        this.dataCadastro = atividade.getDataCadastro() != null ? DateUtil.formataBrSimples(atividade.getDataCadastro()) : "-";

        this.usuarioLicenciamento = atividade.getUsuarioLicenciamento() != null ? getNomeUsuario(atividade.getUsuarioLicenciamento()) : "-";

    }

    private String getNomeUsuario(UsuarioLicenciamento usuario){
        return EntradaUnicaWS.ws.buscarPessoaFisicaPeloCpf(usuario.getLogin()).nome;
    }

    private String tratarParametrosParaCsv(List<RelAtividadeParametroAtividade> parametrosExistentes) {

        StringBuilder listaparametros = new StringBuilder();

        for (int i = 0; i < parametrosExistentes.size(); i++) {

            listaparametros.append(parametrosExistentes.get(i).getParametro().getCodigo());

            if (i != parametrosExistentes.size() - 1) {
                listaparametros.append(" - ");
            }
        }


        return listaparametros.toString();

    }

    private String tratarLicencasParaCsv(List<Licenca> licencasExistentes) {

        StringBuilder listalicencas = new StringBuilder();

        for (int i = 0; i < licencasExistentes.size(); i++) {

            listalicencas.append(licencasExistentes.get(i).getSigla());

            if (i != licencasExistentes.size() - 1) {
                listalicencas.append(" - ");
            }
        }

        return listalicencas.toString();

    }

}