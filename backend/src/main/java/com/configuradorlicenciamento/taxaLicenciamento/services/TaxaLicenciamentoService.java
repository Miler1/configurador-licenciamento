package com.configuradorlicenciamento.taxaLicenciamento.services;

import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.licenca.repositories.LicencaRepository;
import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;
import com.configuradorlicenciamento.porteEmpreendimento.repositories.PorteEmpreendimentoRepository;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.potencialPoluidor.repositories.PotencialPoluidorRepository;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.TaxaLicenciamentoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.interfaces.ITaxaLicenciamentoService;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.repositories.TaxaLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxaLicenciamentoService implements ITaxaLicenciamentoService {

    @Autowired
    TaxaLicenciamentoRepository taxaLicenciamentoRepository;

    @Autowired
    PorteEmpreendimentoRepository porteEmpreendimentoRepository;

    @Autowired
    PotencialPoluidorRepository potencialPoluidorRepository;

    @Autowired
    LicencaRepository licencaRepository;

    @Override
    public void salvar(List<TaxaLicenciamentoDTO> listTaxasLicenciamento, CodigoTaxaLicenciamento codigoTaxaLicenciamento) {

        listTaxasLicenciamento.forEach(taxaLicenciamentoDTO -> {
            taxaLicenciamentoRepository.save(montaObjetoParaSalvar(taxaLicenciamentoDTO, codigoTaxaLicenciamento));
        });

    }

    @Override
    public void editar(List<TaxaLicenciamentoDTO> taxasLicenciamentoDTO, CodigoTaxaLicenciamento codigoTaxaLicenciamento) {

        List<TaxaLicenciamento> taxasLicenciamento = taxaLicenciamentoRepository.findByCodigo(codigoTaxaLicenciamento);

        taxasLicenciamento.forEach(taxaLicenciamento ->
                taxaLicenciamentoRepository.delete(taxaLicenciamento)
        );

        taxasLicenciamentoDTO.forEach(tipoLicencaGrupoDocumentoDTO ->
                taxaLicenciamentoRepository.save(montaObjetoParaSalvar(tipoLicencaGrupoDocumentoDTO, codigoTaxaLicenciamento))
        );

    }

    @Override
    public List<TaxaLicenciamento> findByCodigo(CodigoTaxaLicenciamento codigoTaxaLicenciamento) {
        return taxaLicenciamentoRepository.findByCodigo(codigoTaxaLicenciamento);
    }

    public TaxaLicenciamento montaObjetoParaSalvar(TaxaLicenciamentoDTO tDTO, CodigoTaxaLicenciamento codigoTaxaLicenciamento) {

        PorteEmpreendimento porteEmpreendimento = porteEmpreendimentoRepository.findById(tDTO.getIdPorteEmpreendimento()).orElseThrow(RuntimeException::new);

        PotencialPoluidor potencialPoluidor = potencialPoluidorRepository.findById(tDTO.getIdPotencialPoluidor()).orElseThrow(RuntimeException::new);

        Licenca licenca = licencaRepository.findById(tDTO.getIdTipoLicenca()).orElseThrow(RuntimeException::new);

        return new TaxaLicenciamento.TaxaLicenciamentoBuilder(tDTO)
                .setCodigoTaxaLicenciamento(codigoTaxaLicenciamento)
                .setLicenca(licenca)
                .setPorteEmpreendimento(porteEmpreendimento)
                .setPotencialPoluidor(potencialPoluidor)
                .build();

    }

}
