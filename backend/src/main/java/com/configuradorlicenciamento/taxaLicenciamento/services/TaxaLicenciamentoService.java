package com.configuradorlicenciamento.taxaLicenciamento.services;

import com.configuradorlicenciamento.atividade.interfaces.IAtividadeLicenciavelService;
import com.configuradorlicenciamento.atividade.models.Atividade;
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

    public static final String TAXA_VINCULADA = "É necessário desvincular a tabela de todas as atividades licenciáveis antes de deletá-la.";

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

        validarPrepararParaEditar(taxasLicenciamentoDTO, codigoTaxaLicenciamento);

        taxasLicenciamentoDTO.forEach(taxaLicenciamento ->
        {

            if (taxaLicenciamento.getId() != null) {
                taxaLicenciamentoRepository.save(montaObjetoParaEditar(taxaLicenciamento, codigoTaxaLicenciamento));
            } else {

                taxaLicenciamentoRepository.save(montaObjetoParaSalvar(taxaLicenciamento, codigoTaxaLicenciamento));

                //se não for a única taxa da tabela, então tentar vincular
                if (taxasLicenciamentoDTO.get(0).getId() != null) {

                    Optional<TaxaLicenciamento> taxa = taxaLicenciamentoRepository.findById(taxasLicenciamentoDTO.get(0).getId());

                    taxa.ifPresent(licenciamento -> atividadeLicenciavelService.vincularNovaTaxa(taxa.get()));

                }

            }

        });

    }

    @Override
    public List<TaxaLicenciamento> buscarTabelaTaxas(CodigoTaxaLicenciamento codigoTaxaLicenciamento) {
        return taxaLicenciamentoRepository.findByCodigoAndAtivo(codigoTaxaLicenciamento, true);
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
                .setAtivo(true)
                .build();

    }

    public TaxaLicenciamento montaObjetoParaEditar(TaxaLicenciamentoDTO
                                                           taxaLicenciamentoDTO, CodigoTaxaLicenciamento codigoTaxaLicenciamento) {

        PorteEmpreendimento porteEmpreendimento = porteEmpreendimentoRepository.findById(taxaLicenciamentoDTO.getIdPorteEmpreendimento()).orElseThrow(RuntimeException::new);

        PotencialPoluidor potencialPoluidor = potencialPoluidorRepository.findById(taxaLicenciamentoDTO.getIdPotencialPoluidor()).orElseThrow(RuntimeException::new);

        Licenca licenca = licencaRepository.findById(taxaLicenciamentoDTO.getIdTipoLicenca()).orElseThrow(RuntimeException::new);

        TaxaLicenciamento taxaLicenciamento = new TaxaLicenciamento();

        Optional<TaxaLicenciamento> taxaLicenciamentoSalvo = taxaLicenciamentoRepository.findById(taxaLicenciamentoDTO.getId());

        if (taxaLicenciamentoSalvo.isPresent()) {

            taxaLicenciamento = taxaLicenciamentoSalvo.get();

            taxaLicenciamento.setCodigo(codigoTaxaLicenciamento);
            taxaLicenciamento.setPorteEmpreendimento(porteEmpreendimento);
            taxaLicenciamento.setLicenca(licenca);
            taxaLicenciamento.setPotencialPoluidor(potencialPoluidor);
            taxaLicenciamento.setValor(taxaLicenciamentoDTO.getValor());

        }

        return taxaLicenciamento;

    }

    private void validarPrepararParaEditar(List<TaxaLicenciamentoDTO> taxasLicenciamentoDTO, CodigoTaxaLicenciamento codigoTaxaLicenciamento) {

        List<TaxaLicenciamento> taxasLicenciamento = taxaLicenciamentoRepository.findByCodigoAndAtivo(codigoTaxaLicenciamento, true);

        taxasLicenciamento.forEach(taxaLicenciamento -> {

            boolean removeuTaxa = taxasLicenciamentoDTO.stream().noneMatch(taxaLicenciamentoDTO ->
                    taxaLicenciamento.getId().equals(taxaLicenciamentoDTO.getId()));

            if (removeuTaxa) {

                List<Atividade> atividades = atividadeLicenciavelService.buscarAtividadeTaxaVinculada(taxaLicenciamento);

                if (!atividades.isEmpty()) {

                    //não é permitido remover taxa da tabela vinculada com uma atividade atual
                    boolean existeVinculoAtual = atividades.stream().anyMatch(atividade -> !atividade.getItemAntigo());

                    if (existeVinculoAtual) {
                        throw new ConflictException(TAXA_VINCULADA);
                    }

                    //inativar taxa removida
                    if (taxaLicenciamento.ativo) {

                        taxaLicenciamento.setAtivo(false);
                        taxaLicenciamentoRepository.save(taxaLicenciamento);

                    }

                } else {
                    taxaLicenciamentoRepository.delete(taxaLicenciamento);
                }

            }

        });

    }

    public List<TaxaLicenciamento> buscarTaxasAtivas(CodigoTaxaLicenciamento codigoTaxaLicenciamento) {
        return taxaLicenciamentoRepository.findByCodigoAndAtivo(codigoTaxaLicenciamento, true);
    }

}