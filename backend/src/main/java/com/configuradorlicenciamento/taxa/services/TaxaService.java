package com.configuradorlicenciamento.taxa.services;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxa.interfaces.ITaxaService;
import com.configuradorlicenciamento.taxa.models.Taxa;
import com.configuradorlicenciamento.taxa.repositories.TaxaRepository;
import com.configuradorlicenciamento.taxa.specifications.TaxaSpecification;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TaxaService implements ITaxaService {

    @Autowired
    TaxaRepository taxaRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public Page<Taxa> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Taxa> specification = preparaFiltro(filtro);

        return taxaRepository.findAll(specification, pageable);
    }

    private Specification<Taxa> preparaFiltro(FiltroPesquisa filtro) {

        Specification<Taxa> specification = Specification.where(TaxaSpecification.padrao());

        if(filtro.getStringPesquisa() != null) {
            specification = specification.and(TaxaSpecification.codigoTaxa(filtro.getStringPesquisa())
                    .or(TaxaSpecification.descricao(filtro.getStringPesquisa())));
        }

        return specification;

    }
}
