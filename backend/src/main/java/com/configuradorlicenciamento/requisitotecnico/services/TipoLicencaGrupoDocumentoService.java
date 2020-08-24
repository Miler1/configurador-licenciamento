package com.configuradorlicenciamento.requisitotecnico.services;

import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.documento.repositories.DocumentoRepository;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.licenca.repositories.LicencaRepository;
import com.configuradorlicenciamento.requisitotecnico.dtos.TipoLicencaGrupoDocumentoDTO;
import com.configuradorlicenciamento.requisitotecnico.interfaces.ITipoLicencaGrupoDocumentoService;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisitotecnico.models.TipoLicencaGrupoDocumento;
import com.configuradorlicenciamento.requisitotecnico.repositories.TipoLicencaGrupoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoLicencaGrupoDocumentoService implements ITipoLicencaGrupoDocumentoService {

    @Autowired
    TipoLicencaGrupoDocumentoRepository tipoLicencaGrupoDocumentoRepository;

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    LicencaRepository licencaRepository;

    @Override
    public void salvar(List<TipoLicencaGrupoDocumentoDTO> listTipoLicencaGrupoDocumentoDTO, RequisitoTecnico requisitoTecnico) {

        listTipoLicencaGrupoDocumentoDTO.stream().forEach(tipoLicencaGrupoDocumentoDTO -> {
            tipoLicencaGrupoDocumentoRepository.save(montaObjetoParaSalvar(tipoLicencaGrupoDocumentoDTO, requisitoTecnico));
        });
    }

    public TipoLicencaGrupoDocumento montaObjetoParaSalvar(TipoLicencaGrupoDocumentoDTO tDTO, RequisitoTecnico requisitoTecnico) {

        Documento documento = documentoRepository.findById(tDTO.getIdTipoDocumento()).get();

        Licenca licenca = licencaRepository.findById(tDTO.getIdTipoLicenca()).get();

        return new TipoLicencaGrupoDocumento.TipoLicencaGrupoDocumentoBuilder(tDTO)
                .setDocumento(documento)
                .setLicenca(licenca)
                .setRequisitoTecnico(requisitoTecnico)
                .build();
    }
}
