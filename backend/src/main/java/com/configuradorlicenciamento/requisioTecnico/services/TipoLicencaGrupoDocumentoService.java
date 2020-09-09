package com.configuradorlicenciamento.requisioTecnico.services;

import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.documento.repositories.DocumentoRepository;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.licenca.repositories.LicencaRepository;
import com.configuradorlicenciamento.requisioTecnico.dtos.TipoLicencaGrupoDocumentoDTO;
import com.configuradorlicenciamento.requisioTecnico.interfaces.ITipoLicencaGrupoDocumentoService;
import com.configuradorlicenciamento.requisioTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisioTecnico.models.TipoLicencaGrupoDocumento;
import com.configuradorlicenciamento.requisioTecnico.repositories.TipoLicencaGrupoDocumentoRepository;
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
    public void salvar(List<TipoLicencaGrupoDocumentoDTO> listRequisitos, RequisitoTecnico requisitoTecnico) {

        listRequisitos.forEach(tipoLicencaGrupoDocumentoDTO ->
                tipoLicencaGrupoDocumentoRepository.save(montaObjetoParaSalvar(tipoLicencaGrupoDocumentoDTO, requisitoTecnico))
        );
    }

    @Override
    public void editar(List<TipoLicencaGrupoDocumentoDTO> listRequisitos, RequisitoTecnico requisitoTecnico) {

        List<TipoLicencaGrupoDocumento> tipoLicencaGrupoDocumentoList = tipoLicencaGrupoDocumentoRepository.findByRequisitoTecnico(requisitoTecnico);

        tipoLicencaGrupoDocumentoList.forEach(tipoLicencaGrupoDocumento -> {
            tipoLicencaGrupoDocumentoRepository.delete(tipoLicencaGrupoDocumento);
        });

        listRequisitos.forEach(tipoLicencaGrupoDocumentoDTO ->
                tipoLicencaGrupoDocumentoRepository.save(montaObjetoParaSalvar(tipoLicencaGrupoDocumentoDTO, requisitoTecnico))
        );
    }

    public TipoLicencaGrupoDocumento montaObjetoParaSalvar(TipoLicencaGrupoDocumentoDTO tDTO, RequisitoTecnico requisitoTecnico) {

        Documento documento = documentoRepository.findById(tDTO.getIdTipoDocumento()).orElseThrow(RuntimeException::new);

        Licenca licenca = licencaRepository.findById(tDTO.getIdTipoLicenca()).orElseThrow(RuntimeException::new);

        return new TipoLicencaGrupoDocumento.TipoLicencaGrupoDocumentoBuilder(tDTO)
                .setDocumento(documento)
                .setLicenca(licenca)
                .setRequisitoTecnico(requisitoTecnico)
                .build();
    }

    @Override
    public List<TipoLicencaGrupoDocumento> findByRequisito(RequisitoTecnico requisitoTecnico) {

        return tipoLicencaGrupoDocumentoRepository.findByRequisitoTecnico(requisitoTecnico);

    }

}
