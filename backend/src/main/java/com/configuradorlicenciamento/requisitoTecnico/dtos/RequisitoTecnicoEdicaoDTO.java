package com.configuradorlicenciamento.requisitoTecnico.dtos;

import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisitoTecnico.models.TipoLicencaGrupoDocumento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisitoTecnicoEdicaoDTO {

    private Integer id;

    private String codigo;

    private String descricao;

    private Boolean ativo;

    private final List<TipoLicencaGrupoDocumento> tipoLicencaGrupoDocumentoList;

    public RequisitoTecnicoEdicaoDTO(RequisitoTecnico requisitoTecnico, List<TipoLicencaGrupoDocumento> tipoLicencaGrupoDocumentoList) {

        this.codigo = requisitoTecnico.getCodigo();
        this.descricao = requisitoTecnico.getDescricao();
        this.ativo = requisitoTecnico.getAtivo();

        this.tipoLicencaGrupoDocumentoList = tipoLicencaGrupoDocumentoList;

    }

}