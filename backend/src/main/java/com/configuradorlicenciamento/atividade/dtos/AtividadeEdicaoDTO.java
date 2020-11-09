package com.configuradorlicenciamento.atividade.dtos;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeEdicaoDTO {

    private final Integer id;

    private final String nomeAtividade;

    private final String codigoAtividade;

    private final Tipologia tipologia;

    private final Boolean geoPonto;

    private final Boolean geoLinha;

    private final Boolean geoPoligono;

    private final PotencialPoluidor potencialPoluidor;

    private final String setor;

    private final Boolean foraEmpreendimento;

    private final RequisitoTecnico requisitoTecnico;

    private final CodigoTaxaLicenciamento taxaLicenciamento;

    private final List<String> tiposAtividade;

    private final List<Licenca> licencas;

    private final Boolean rascunho;

    public AtividadeEdicaoDTO(Atividade atividade) {

        this.id = atividade.getId();
        this.nomeAtividade = atividade.getNome();
        this.codigoAtividade = atividade.getCodigo();
        this.tipologia = atividade.getTipologia();
        this.geoPonto = atividade.getGeoPonto();
        this.geoLinha = atividade.getGeoLinha();
        this.geoPoligono = atividade.getGeoPoligono();
        this.potencialPoluidor = atividade.getPotencialPoluidor();
        this.setor = atividade.getSiglaSetor();
        this.foraEmpreendimento = atividade.getDentroEmpreendimento() != null ? !atividade.getDentroEmpreendimento() : null;
        this.requisitoTecnico = atividade.getRequisitoTecnico();
        this.taxaLicenciamento = atividade.getTaxasLicenciamento().isEmpty() ? null : atividade.recuperaCodigoTaxaLicenciamentobyTaxas();
        this.tiposAtividade = atividade.recuperaCodigosTiposAtividade();
        this.licencas = atividade.getTiposLicencas();
        this.rascunho = atividade.getRascunho();

    }

}