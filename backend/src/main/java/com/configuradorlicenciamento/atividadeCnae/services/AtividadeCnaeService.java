package com.configuradorlicenciamento.atividadeCnae.services;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.interfaces.IAtividadeCnaeService;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.atividadeCnae.specifications.AtividadeCnaeSpecification;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class AtividadeCnaeService implements IAtividadeCnaeService {

    @Autowired
    AtividadeCnaeRepository atividadeCnaeRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public AtividadeCnae salvar(HttpServletRequest request, AtividadeCnaeDTO atividadeCnaeDTO) throws Exception{

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        AtividadeCnae atividadeCnae = new AtividadeCnae.AtividadeCnaeBuilder(atividadeCnaeDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        atividadeCnaeRepository.save(atividadeCnae);

        return atividadeCnae;

    }

    @Override
    public Page<AtividadeCnae> lista(Pageable pageable, FiltroPesquisa filtro) {

        Specification<AtividadeCnae> specification = preparaFiltro(filtro);

        Page<AtividadeCnae> atividadeCnaes = atividadeCnaeRepository.findAll(specification, pageable);

        return atividadeCnaes;
    }

    private Specification<AtividadeCnae> preparaFiltro(FiltroPesquisa filtro) {

        Specification specification = Specification.where(AtividadeCnaeSpecification.padrao());

        if(filtro.getStringPesquisa() != null) {
            specification = specification.and(AtividadeCnaeSpecification.nome(filtro.getStringPesquisa())
                    .or(AtividadeCnaeSpecification.codigo(filtro.getStringPesquisa())));

        }

        return specification;

    }

    @Override
    public List<AtividadeCnae> listarCnaes() {
        return atividadeCnaeRepository.findAll(Sort.by("codigo"));
    }

    @Override
    public List<AtividadeCnaeCsv> listarCnaesParaCsv(){

        List<AtividadeCnae> cnaes = listarCnaes();
        List<AtividadeCnaeCsv> dtos = new ArrayList<>();

        for (AtividadeCnae cnae : cnaes) {
            dtos.add(cnae.preparaParaCsv());
        }

        return dtos;
    }

}
