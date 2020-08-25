package com.configuradorlicenciamento.requisitoadministrativo.services;

import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.configuracao.exceptions.ConstraintUniqueViolationException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.documento.repositories.DocumentoRepository;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.requisitoadministrativo.dtos.RequisitoAdministrativoDTO;
import com.configuradorlicenciamento.requisitoadministrativo.interfaces.IRequisitoAdministrativoService;
import com.configuradorlicenciamento.requisitoadministrativo.models.RequisitoAdministrativo;
import com.configuradorlicenciamento.requisitoadministrativo.repositories.RequisitoAdministrativoRepository;
import com.configuradorlicenciamento.requisitoadministrativo.specifications.RequisitoAdministrativoSpecification;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequisitoAdministrativoService implements IRequisitoAdministrativoService {

    private static final String REQUISITO_EXISTENTE = "O documento já foi cadastrado para o tipo de licença.";

    @Autowired
    RequisitoAdministrativoRepository requisitoAdministrativoRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Autowired
    DocumentoRepository documentoRepository;

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
    public RequisitoAdministrativo editar(HttpServletRequest request, RequisitoAdministrativoDTO requisitoAdministrativoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<RequisitoAdministrativo> requisitoAdministrativoSalvo = requisitoAdministrativoRepository.findById(requisitoAdministrativoDTO.getId());

        if(requisitoAdministrativoSalvo.isEmpty()){
            throw new ConfiguradorNotFoundException("O requisito não foi encontrado no sistema. Atualize a página e tente novamente.");
        }

        RequisitoAdministrativo requisitoAdministrativo = requisitoAdministrativoSalvo.get();

        if(requisitoAdministrativo.getAtivo() != requisitoAdministrativoDTO.getAtivo()){
            requisitoAdministrativo.setAtivo(requisitoAdministrativoDTO.getAtivo());
        } else {

            Documento novoDocumento = requisitoAdministrativoDTO.getDocumento();

            requisitoAdministrativo.setDocumento(novoDocumento);
            requisitoAdministrativo.setObrigatorio(requisitoAdministrativoDTO.getObrigatorio());

            long alreadyExistents = requisitoAdministrativoRepository.count(RequisitoAdministrativoSpecification.documentoAndLicenca(novoDocumento.getId(), requisitoAdministrativo.getLicenca().getId()));

            if(alreadyExistents > 0){
                throw new ConstraintUniqueViolationException(REQUISITO_EXISTENTE);
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

    private Specification<RequisitoAdministrativo> preparaFiltro(FiltroPesquisa filtro) {

        Specification<RequisitoAdministrativo> specification = Specification.where(RequisitoAdministrativoSpecification.padrao());

        if (filtro.getStringPesquisa() != null) {
            specification = specification.and(RequisitoAdministrativoSpecification.documento(filtro.getStringPesquisa()));
        }

        return specification;

    }

}
