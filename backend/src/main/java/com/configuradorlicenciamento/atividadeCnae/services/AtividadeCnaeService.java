package com.configuradorlicenciamento.atividadeCnae.services;

import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.dtos.FiltroAtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.interfaces.IAtividadeCnaeService;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.atividadeCnae.specifications.AtividadeCnaeSpecification;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    public Page<AtividadeCnae> lista(Pageable pageable, FiltroAtividadeCnaeDTO filtro) {

        Specification<AtividadeCnae> specification = preparaFiltro(filtro);

        Page<AtividadeCnae> apreensao = atividadeCnaeRepository.findAll(specification, pageable);

        return apreensao;
    }

    private Specification<AtividadeCnae> preparaFiltro(FiltroAtividadeCnaeDTO filtro) {

        Specification specification = Specification.where(AtividadeCnaeSpecification.padrao());

        if(filtro.getCodigoOrNome() != null) {
            specification = specification.and(AtividadeCnaeSpecification.nome(filtro.getCodigoOrNome())
                    .or(AtividadeCnaeSpecification.codigo(filtro.getCodigoOrNome())));

        }

        return specification;

    }

}
