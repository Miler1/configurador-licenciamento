package com.configuradorlicenciamento.atividade.dtos;

import com.configuradorlicenciamento.licenca.dtos.LicencaDTO;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.requisitoTecnico.dtos.RequisitoTecnicoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoDTO;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtividadeDTO {

    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String nomeAtividade;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String codigoAtividade;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private TipologiaDTO tipologia;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Integer idPorteEmpreendimento;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Boolean geoPonto;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Boolean geoLinha;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Boolean geoPoligono;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private PotencialPoluidor potencialPoluidor;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private String setor;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Boolean foraEmpreendimento;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private RequisitoTecnicoDTO requisitoTecnico;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private CodigoTaxaLicenciamentoDTO taxaLicenciamento;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private List<String> tiposAtividade;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private List<LicencaDTO> licencas;

    @NotNull(message = "{validacao.notnull}")
    @NotBlank(message = "{validacao.notBlank}")
    private Boolean rascunho;

    private List<RelAtividadeParametroAtividadeDTO> atividadesParametro;

    private List<PorteAtividadeDTO> portesAtividade;

}
