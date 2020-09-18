package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.services;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces.ITipoCaracterizacaoAtividadeService;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories.TipoCaracterizacaoAtividadeRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.specifications.TipoCaracterizacaoAtividadeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoCaracterizacaoAtividadeService implements ITipoCaracterizacaoAtividadeService {

    @Autowired
    TipoCaracterizacaoAtividadeRepository tipoCaracterizacaoAtividadeRepository;

    @Override
    public Page<TipoCaracterizacaoAtividade> listarAtividadesDispensaveis(Pageable pageable, FiltroPesquisa filtro) {

        Specification<TipoCaracterizacaoAtividade> specification = preparaFiltroAtividadeDispensavel(filtro);

        return tipoCaracterizacaoAtividadeRepository.findAll(specification, pageable);

    }

    private Specification<TipoCaracterizacaoAtividade> preparaFiltroAtividadeDispensavel(FiltroPesquisa filtro) {

        Specification<TipoCaracterizacaoAtividade> specification = Specification.where(TipoCaracterizacaoAtividadeSpecification.padrao()
                .and(TipoCaracterizacaoAtividadeSpecification.filtrarAtividadesDispensaveis()));

        if(filtro.getStringPesquisa() != null) {
            specification = specification.and(TipoCaracterizacaoAtividadeSpecification.atividadeCnaeNome(filtro.getStringPesquisa())
                    .or(TipoCaracterizacaoAtividadeSpecification.atividadeCnaeCodigo(filtro.getStringPesquisa())));

        }

        return specification;

    }

    @Override
    public List<TipoCaracterizacaoAtividade> listarAtividadesDispensaveis() {

        Specification<TipoCaracterizacaoAtividade> specification = Specification.where(TipoCaracterizacaoAtividadeSpecification.padrao()
                .and(TipoCaracterizacaoAtividadeSpecification.filtrarAtividadesDispensaveis()));

        return tipoCaracterizacaoAtividadeRepository.findAll(specification, Sort.by("id"));
    }

    @Override
    public List<AtividadeDispensavelCsv> listarAtividadesDispensaveisParaCsv(){

        List<TipoCaracterizacaoAtividade> tiposCaracterizacaoAtividade = listarAtividadesDispensaveis();
        List<AtividadeDispensavelCsv> dtos = new ArrayList<>();

        for (TipoCaracterizacaoAtividade tipoCaracterizacaoAtividade : tiposCaracterizacaoAtividade) {
            dtos.add(tipoCaracterizacaoAtividade.preparaAtividadeDispensavelParaCsv());
        }

        return dtos;
    }

}
