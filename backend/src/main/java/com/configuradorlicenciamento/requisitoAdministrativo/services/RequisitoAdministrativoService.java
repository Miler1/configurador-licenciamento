package com.configuradorlicenciamento.requisitoAdministrativo.services;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.requisitoAdministrativo.dtos.RequisitoAdministrativoDTO;
import com.configuradorlicenciamento.requisitoAdministrativo.interfaces.IRequisitoAdministrativoService;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import com.configuradorlicenciamento.requisitoAdministrativo.repositories.RequisitoAdministrativoRepository;
import com.configuradorlicenciamento.requisitoAdministrativo.specifications.RequisitoAdministrativoSpecification;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.tipologia.specifications.TipologiaSpecification;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RequisitoAdministrativoService implements IRequisitoAdministrativoService {

    @Autowired
    RequisitoAdministrativoRepository requisitoAdministrativoRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public List<RequisitoAdministrativo> salvar(HttpServletRequest request, RequisitoAdministrativoDTO requisitoAdministrativoDTO) throws Exception {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        List<RequisitoAdministrativo> requisitoAdministrativoList = new ArrayList<>();

        for (Licenca licenca : requisitoAdministrativoDTO.getLicencas()){
            RequisitoAdministrativo requisitoAdministrativo = new RequisitoAdministrativo.RequisitoAdministrativoBuilder(requisitoAdministrativoDTO)
                    .setLicenca(licenca)
                    .setDataCadastro(new Date())
                    .setUsuarioLicencimento(usuarioLicenciamento)
                    .build();

            requisitoAdministrativoRepository.save(requisitoAdministrativo);

            requisitoAdministrativoList.add(requisitoAdministrativo);
        }

        return requisitoAdministrativoList;

    }

    @Override
    public Page<RequisitoAdministrativo> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<RequisitoAdministrativo> specification = preparaFiltro(filtro);

        return requisitoAdministrativoRepository.findAll(specification, pageable);

    }

    private Specification<RequisitoAdministrativo> preparaFiltro(FiltroPesquisa filtro) {

        Specification<RequisitoAdministrativo> specification = Specification.where(RequisitoAdministrativoSpecification.padrao());

        if (filtro.getStringPesquisa() != null) {
            specification = specification.and(RequisitoAdministrativoSpecification.documento(filtro.getStringPesquisa()));
        }

        return specification;

    }

}
