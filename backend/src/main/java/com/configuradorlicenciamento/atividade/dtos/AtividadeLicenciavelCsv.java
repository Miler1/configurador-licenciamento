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

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeLicenciavelCsv implements Serializable {

    @CsvBindByName(column = "Código")
    @CsvBindByPosition(position = 0)
    private String codigo;

    @CsvBindByName(column = "Nome")
    @CsvBindByPosition(position = 1)
    private String nome;

    @CsvBindByName(column = "Tipologia")
    @CsvBindByPosition(position = 2)
    private String tipologia;

    @CsvBindByName(column = "Parâmetros")
    @CsvBindByPosition(position = 3)
    private String parametros;

    @CsvBindByName(column = "Tipos de licença")
    @CsvBindByPosition(position = 4)
    private String tiposLicencas;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 5)
    private String ativo;

    public AtividadeLicenciavelCsv(Atividade atividade) {

        this.codigo = atividade.getCodigo();
        this.nome = atividade.getNome();
        this.tipologia = atividade.getTipologia().getNome();
        this.parametros = tratarParametrosParaCsv(atividade.getParametros());
        this.tiposLicencas = tratarLicencasParaCsv(atividade.getTiposLicencas());
        this.ativo = atividade.getAtivo() ? "Ativo" : "Inativo";

    }

    private String tratarParametrosParaCsv(List<RelAtividadeParametroAtividade> parametrosExistentes){

        StringBuilder parametros = new StringBuilder();

        for(int i = 0; i < parametrosExistentes.size(); i++){

            parametros.append(parametrosExistentes.get(i).getParametro().getCodigo());

            if(i != parametrosExistentes.size() - 1){
                parametros.append(" - ");
            }
        }

        return parametros.toString();
    }

    private String tratarLicencasParaCsv(List<Licenca> licencasExistentes){

        StringBuilder licencas = new StringBuilder();

        for(int i = 0; i < licencasExistentes.size(); i++){

            licencas.append(licencasExistentes.get(i).getSigla());

            if(i != licencasExistentes.size() - 1){
                licencas.append(" - ");
            }
        }

        return licencas.toString();
    }

}
