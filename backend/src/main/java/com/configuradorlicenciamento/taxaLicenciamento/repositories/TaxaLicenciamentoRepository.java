package com.configuradorlicenciamento.taxaLicenciamento.repositories;

import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface TaxaLicenciamentoRepository extends JpaRepository<TaxaLicenciamento, Integer>, JpaSpecificationExecutor<TaxaLicenciamento> {

    List<TaxaLicenciamento> findByCodigo(CodigoTaxaLicenciamento codigoTaxaLicenciamento);

    Boolean existsByPorteEmpreendimentoAndPotencialPoluidorAndLicenca(
            @NotNull(message = "{validacao.notnull}") PorteEmpreendimento porteEmpreendimento,
            @NotNull(message = "{validacao.notnull}") PotencialPoluidor potencialPoluidor,
            @NotNull(message = "{validacao.notnull}") Licenca licenca
    );

}
