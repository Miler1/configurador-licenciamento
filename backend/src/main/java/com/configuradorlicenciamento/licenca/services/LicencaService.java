package com.configuradorlicenciamento.licenca.services;

import com.configuradorlicenciamento.atividadeCnae.dtos.FiltroAtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.specifications.AtividadeCnaeSpecification;
import com.configuradorlicenciamento.licenca.dtos.FiltroLicencaDTO;
import com.configuradorlicenciamento.licenca.dtos.LicencaDTO;
import com.configuradorlicenciamento.licenca.interfaces.ILicencaService;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.licenca.repositories.LicencaRepository;
import com.configuradorlicenciamento.licenca.specifications.LicencaSpecification;
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
public class LicencaService implements ILicencaService {

    @Autowired
    LicencaRepository licencaRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public Licenca salvar(HttpServletRequest request, LicencaDTO licencaDTO) throws Exception {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Licenca licenca = new Licenca.LicencaBuilder(licencaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        licencaRepository.save(licenca);

        return licenca;
    }

    public Page<Licenca> lista(Pageable pageable, FiltroLicencaDTO filtro) {

        Specification<Licenca> specification = preparaFiltro(filtro);

        Page<Licenca> atividadeCnaes = licencaRepository.findAll(specification, pageable);

        return atividadeCnaes;
    }

    private Specification<Licenca> preparaFiltro(FiltroLicencaDTO filtro) {

        Specification specification = Specification.where(LicencaSpecification.padrao());

        if(filtro.getSiglaOrNome() != null) {
            specification = specification.and(LicencaSpecification.nome(filtro.getSiglaOrNome())
                    .or(LicencaSpecification.sigla(filtro.getSiglaOrNome())));
        }

        return specification;

    }
}