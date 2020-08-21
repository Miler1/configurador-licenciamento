package com.configuradorlicenciamento.tipologia.services;

import com.configuradorlicenciamento.configuracao.exceptions.ConstraintUniqueViolationException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaCsv;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
import com.configuradorlicenciamento.tipologia.interfaces.ITipologiaService;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.tipologia.repositories.TipologiaRepository;
import com.configuradorlicenciamento.tipologia.specifications.TipologiaSpecification;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TipologiaService implements ITipologiaService {

    @Autowired
    TipologiaRepository tipologiaRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    public Tipologia salvar(HttpServletRequest request, TipologiaDTO tipologiaDTO) throws Exception {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Tipologia tipologia = new Tipologia.TipologiaBuilder(tipologiaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        boolean existsCodigo = tipologiaRepository.existsByCodigo(tipologia.getCodigo());

        if (existsCodigo) {

            throw new ConstraintUniqueViolationException("Já existe uma tipologia com o nome semelhante.");
        }

        tipologiaRepository.save(tipologia);

        return tipologia;

    }

    @Override
    public Page<Tipologia> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Tipologia> specification = preparaFiltro(filtro);

        return tipologiaRepository.findAll(specification, pageable);

    }

    private Specification<Tipologia> preparaFiltro(FiltroPesquisa filtro) {

        Specification<Tipologia> specification = Specification.where(TipologiaSpecification.padrao());

        if (filtro.getStringPesquisa() != null) {
            specification = specification.and(TipologiaSpecification.nome(filtro.getStringPesquisa()));
        }

        return specification;

    }

    @Override
    public List<Tipologia> listarTipologia() {
        return tipologiaRepository.findAll(Sort.by("codigo"));
    }

    @Override
    public List<TipologiaCsv> listarTipologiaParaCsv() {

        List<Tipologia> tipologias = listarTipologia();
        List<TipologiaCsv> dtos = new ArrayList<>();

        for (Tipologia tipologia : tipologias) {
            dtos.add(tipologia.preparaParaCsv());
        }

        return dtos;
    }
}
