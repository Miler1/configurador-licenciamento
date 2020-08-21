package com.configuradorlicenciamento.requisitotecnico.services;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitotecnico.interfaces.IRequisitoTecnicoService;
import com.configuradorlicenciamento.requisitotecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisitotecnico.repositories.RequisitoTecnicoRepository;
import com.configuradorlicenciamento.requisitotecnico.specifications.RequisitoTecnicoSpecification;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class RequisitoTecnicoService implements IRequisitoTecnicoService {

    @Autowired
    RequisitoTecnicoRepository RequisitoTecnicoRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public Page<RequisitoTecnico> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<RequisitoTecnico> specification = preparaFiltro(filtro);

        return RequisitoTecnicoRepository.findAll(specification, pageable);
    }

    private Specification<RequisitoTecnico> preparaFiltro(FiltroPesquisa filtro) {

        Specification<RequisitoTecnico> specification = Specification.where(RequisitoTecnicoSpecification.padrao());

        if(filtro.getStringPesquisa() != null) {
            specification = specification.and(RequisitoTecnicoSpecification.codigo(filtro.getStringPesquisa())
                    .or(RequisitoTecnicoSpecification.descricao(filtro.getStringPesquisa())));
        }

        return specification;

    }

}