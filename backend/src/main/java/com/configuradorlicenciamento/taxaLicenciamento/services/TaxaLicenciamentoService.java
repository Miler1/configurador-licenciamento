package com.configuradorlicenciamento.taxaLicenciamento.services;

import com.configuradorlicenciamento.atividade.interfaces.IAtividadeLicenciavelService;
import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
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
import java.util.Optional;

@Service
public class TaxaLicenciamentoService implements ITaxaLicenciamentoService {

    public static final String TAXA_VINCULADA = "É necessário desvincular a taxa antes de deletá-la";

    @Autowired
    TaxaLicenciamentoRepository taxaLicenciamentoRepository;

    @Autowired
    PorteEmpreendimentoRepository porteEmpreendimentoRepository;

    @Autowired
    PotencialPoluidorRepository potencialPoluidorRepository;

    @Autowired
    LicencaRepository licencaRepository;

    @Autowired
    IAtividadeLicenciavelService atividadeLicenciavelService;

    @Override
    public void salvar(List<TaxaLicenciamentoDTO> listTaxasLicenciamento, CodigoTaxaLicenciamento codigoTaxaLicenciamento) {

        listTaxasLicenciamento.forEach(taxaLicenciamentoDTO ->
                taxaLicenciamentoRepository.save(montaObjetoParaSalvar(taxaLicenciamentoDTO, codigoTaxaLicenciamento))
        );

    }

    @Override
    public void editar(List<TaxaLicenciamentoDTO> taxasLicenciamentoDTO, CodigoTaxaLicenciamento codigoTaxaLicenciamento) {

        List<TaxaLicenciamento> taxasLicenciamento = taxaLicenciamentoRepository.findByCodigo(codigoTaxaLicenciamento);

        taxasLicenciamento.forEach(taxaLicenciamento -> {

            if (taxasLicenciamentoDTO.stream().noneMatch(taxaLicenciamentoDTO ->
                    taxaLicenciamento.getId().equals(taxaLicenciamentoDTO.getId())
            )) {

                try {
                    taxaLicenciamentoRepository.delete(taxaLicenciamento);
                } catch (Exception e) {
                    throw new ConflictException(TAXA_VINCULADA);
                }

            }

        });

        taxasLicenciamentoDTO.forEach(taxaLicenciamento-> {

            if (taxaLicenciamento.getId() != null) {
                taxaLicenciamentoRepository.save(montaObjetoParaEditar(taxaLicenciamento, codigoTaxaLicenciamento));
            } else {

                taxaLicenciamentoRepository.save(montaObjetoParaSalvar(taxaLicenciamento, codigoTaxaLicenciamento));

                Optional<TaxaLicenciamento> taxa = taxaLicenciamentoRepository.findById(taxasLicenciamentoDTO.get(0).getId());

                taxa.ifPresent(licenciamento -> atividadeLicenciavelService.vincularNovaTaxa(taxa.get()));

            }

        });

    }

    @Override
    public List<TaxaLicenciamento> findByCodigo(CodigoTaxaLicenciamento codigoTaxaLicenciamento) {
        return taxaLicenciamentoRepository.findByCodigo(codigoTaxaLicenciamento);
    }

    public TaxaLicenciamento montaObjetoParaSalvar(TaxaLicenciamentoDTO tDTO, CodigoTaxaLicenciamento
            codigoTaxaLicenciamento) {

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

    public TaxaLicenciamento montaObjetoParaEditar(TaxaLicenciamentoDTO taxaLicenciamentoDTO, CodigoTaxaLicenciamento
            codigoTaxaLicenciamento) {

        PorteEmpreendimento porteEmpreendimento = porteEmpreendimentoRepository.findById(taxaLicenciamentoDTO.getIdPorteEmpreendimento()).orElseThrow(RuntimeException::new);

        PotencialPoluidor potencialPoluidor = potencialPoluidorRepository.findById(taxaLicenciamentoDTO.getIdPotencialPoluidor()).orElseThrow(RuntimeException::new);

        Licenca licenca = licencaRepository.findById(taxaLicenciamentoDTO.getIdTipoLicenca()).orElseThrow(RuntimeException::new);

        Optional<TaxaLicenciamento> taxaLicenciamentoSalvo = taxaLicenciamentoRepository.findById(taxaLicenciamentoDTO.getId())
                .map(taxaLicenciamento -> {
                    taxaLicenciamento.setCodigo(codigoTaxaLicenciamento);
                    taxaLicenciamento.setPorteEmpreendimento(porteEmpreendimento);
                    taxaLicenciamento.setLicenca(licenca);
                    taxaLicenciamento.setPotencialPoluidor(potencialPoluidor);
                    taxaLicenciamento.setValor(taxaLicenciamentoDTO.getValor());
                    return taxaLicenciamento;
                });

        return taxaLicenciamentoSalvo.get();

    }

}
