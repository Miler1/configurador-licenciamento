package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.services;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.specifications.AtividadeCnaeSpecification;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces.ITipoCaracterizacaoAtividadeService;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories.TipoCaracterizacaoAtividadeRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.specifications.TipoCaracterizacaoAtividadeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TipoCaracterizacaoAtividadeService implements ITipoCaracterizacaoAtividadeService {

    @Autowired
    TipoCaracterizacaoAtividadeRepository tipoCaracterizacaoAtividadeRepository;

    @Override
    public Page<TipoCaracterizacaoAtividade> listarAtividadesDispensaveis(Pageable pageable, FiltroPesquisa filtro) {

        Specification<TipoCaracterizacaoAtividade> specification = preparaFiltro(filtro);

        return tipoCaracterizacaoAtividadeRepository.findAll(specification, pageable);

    }

    private Specification<TipoCaracterizacaoAtividade> preparaFiltro(FiltroPesquisa filtro) {

        Specification<TipoCaracterizacaoAtividade> specification = Specification.where(TipoCaracterizacaoAtividadeSpecification.padrao()
                .and(TipoCaracterizacaoAtividadeSpecification.filtrarAtividadesDispensaveis()));

        if(filtro.getStringPesquisa() != null) {
            specification = specification.and(TipoCaracterizacaoAtividadeSpecification.atividadeCnaeNome(filtro.getStringPesquisa())
                    .or(TipoCaracterizacaoAtividadeSpecification.atividadeCnaeCodigo(filtro.getStringPesquisa())));

        }

        return specification;

    }
}
