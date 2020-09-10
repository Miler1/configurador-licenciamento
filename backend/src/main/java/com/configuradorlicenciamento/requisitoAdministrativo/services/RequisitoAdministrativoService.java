package com.configuradorlicenciamento.requisitoAdministrativo.services;

import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.configuracao.exceptions.ConstraintUniqueViolationException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;

import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.documento.repositories.DocumentoRepository;

import com.configuradorlicenciamento.licenca.models.Licenca;

import com.configuradorlicenciamento.requisitoAdministrativo.dtos.RequisitoAdministrativoCsv;
import com.configuradorlicenciamento.requisitoAdministrativo.dtos.RequisitoAdministrativoDTO;
import com.configuradorlicenciamento.requisitoAdministrativo.interfaces.IRequisitoAdministrativoService;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import com.configuradorlicenciamento.requisitoAdministrativo.repositories.RequisitoAdministrativoRepository;
import com.configuradorlicenciamento.requisitoAdministrativo.specifications.RequisitoAdministrativoSpecification;

import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;

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
import java.util.Optional;

@Service
public class RequisitoAdministrativoService implements IRequisitoAdministrativoService {

    private static final String REQUISITO_ADMINISTRATIVO_EXISTENTE = "Já existe um requisito administrativo com o mesmo documento para esse tipo de licença.";

    @Autowired
    RequisitoAdministrativoRepository requisitoAdministrativoRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Autowired
    DocumentoRepository documentoRepository;

    @Override
    public List<RequisitoAdministrativo> salvar(HttpServletRequest request, RequisitoAdministrativoDTO requisitoAdministrativoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        List<RequisitoAdministrativo> requisitoAdministrativoList = new ArrayList<>();

        for (Licenca licenca : requisitoAdministrativoDTO.getLicencas()){

            boolean existsRequisitoAdministrativo = requisitoAdministrativoRepository.existsByLicencaAndDocumento(licenca, requisitoAdministrativoDTO.getDocumento());

            if (existsRequisitoAdministrativo) {
                throw new ConstraintUniqueViolationException(REQUISITO_ADMINISTRATIVO_EXISTENTE);
            }
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
    public RequisitoAdministrativo editar(HttpServletRequest request, RequisitoAdministrativoDTO requisitoAdministrativoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<RequisitoAdministrativo> requisitoAdministrativoSalvo = requisitoAdministrativoRepository.findById(requisitoAdministrativoDTO.getId());

        if(requisitoAdministrativoSalvo.isEmpty()){
            throw new ConfiguradorNotFoundException("O requisito não foi encontrado no sistema. Atualize a página e tente novamente.");
        }

        RequisitoAdministrativo requisitoAdministrativo = requisitoAdministrativoSalvo.get();

        if(!requisitoAdministrativo.getAtivo().equals(requisitoAdministrativoDTO.getAtivo())){
            requisitoAdministrativo.setAtivo(requisitoAdministrativoDTO.getAtivo());
        } else {

            Documento novoDocumento = requisitoAdministrativoDTO.getDocumento();

            requisitoAdministrativo.setDocumento(novoDocumento);
            requisitoAdministrativo.setObrigatorio(requisitoAdministrativoDTO.getObrigatorio());
            requisitoAdministrativo.setTipoPessoa(requisitoAdministrativoDTO.getTipoPessoa());
            requisitoAdministrativo.setObrigatorio(requisitoAdministrativoDTO.getObrigatorio());

            List<RequisitoAdministrativo> alreadyExistents = requisitoAdministrativoRepository.findAll(RequisitoAdministrativoSpecification.documentoAndLicenca(novoDocumento.getId(), requisitoAdministrativo.getLicenca().getId(), requisitoAdministrativo.getTipoPessoa()));

            if(alreadyExistents.size() > 1 || !alreadyExistents.isEmpty() && !alreadyExistents.get(0).getId().equals(requisitoAdministrativo.getId())){
                throw new ConstraintUniqueViolationException(REQUISITO_ADMINISTRATIVO_EXISTENTE);
            }
        }

        requisitoAdministrativo.setUsuarioLicenciamento(usuarioLicenciamento);
        requisitoAdministrativo.setDataCadastro(new Date());

        requisitoAdministrativoRepository.save(requisitoAdministrativo);

        return requisitoAdministrativo;

    }

    @Override
    public Page<RequisitoAdministrativo> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<RequisitoAdministrativo> specification = preparaFiltro(filtro);

        return requisitoAdministrativoRepository.findAll(specification, pageable);
    }

    @Override
    public List<RequisitoAdministrativoCsv> listarRequisitosAdministrativosParaCsv() {

        List<RequisitoAdministrativo> requisitosAdministrativos = requisitoAdministrativoRepository.findAll(Sort.by("documento"));
        List<RequisitoAdministrativoCsv> dtos = new ArrayList<>();

        for (RequisitoAdministrativo requisitoAdministrativo: requisitosAdministrativos) {
            dtos.add(requisitoAdministrativo.preparaParaCsv());
        }

        return dtos;

    }

    @Override
    public List<RequisitoAdministrativo> findAll() {
        return requisitoAdministrativoRepository.findAll();
    }

    private Specification<RequisitoAdministrativo> preparaFiltro(FiltroPesquisa filtro) {

        Specification<RequisitoAdministrativo> specification = Specification.where(RequisitoAdministrativoSpecification.padrao());

        if (filtro.getStringPesquisa() != null) {

            specification = specification.and(RequisitoAdministrativoSpecification.licencaSigla(filtro.getStringPesquisa()));

            if(requisitoAdministrativoRepository.findAll(specification).isEmpty()){
                specification = specification.or(RequisitoAdministrativoSpecification.documento(filtro.getStringPesquisa()));
            }
        }

        return specification;

    }

}
