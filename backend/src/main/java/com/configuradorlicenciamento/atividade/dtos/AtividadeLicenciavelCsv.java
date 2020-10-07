package com.configuradorlicenciamento.atividade.dtos;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.RelAtividadeParametroAtividade;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.IntStream;

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

    }

    private String tratarParametrosParaCsv(List<RelAtividadeParametroAtividade> parametrosExistentes) {

        StringBuilder parametros = new StringBuilder();

        IntStream.range(0, parametrosExistentes.size())
                .forEach(idx -> {
                    parametros.append(parametrosExistentes.get(idx).getParametro().getCodigo());
                    if (idx != parametrosExistentes.size() - 1) {
                        parametros.append(" - ");
                    }
                });

        return parametros.toString();
    }

    private String tratarLicencasParaCsv(List<Licenca> licencasExistentes) {

        StringBuilder licencas = new StringBuilder();

        IntStream.range(0, licencasExistentes.size())
                .forEach(idx -> {
                    licencas.append(licencasExistentes.get(idx).getSigla());
                    if (idx != licencasExistentes.size() - 1) {
                        licencas.append(" - ");
                    }
                });

        return licencas.toString();
    }

}
