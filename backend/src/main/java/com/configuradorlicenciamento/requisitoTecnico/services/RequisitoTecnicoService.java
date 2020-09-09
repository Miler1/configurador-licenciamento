package com.configuradorlicenciamento.requisitoTecnico.services;

import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.requisitoTecnico.dtos.RequisitoTecnicoDTO;
import com.configuradorlicenciamento.requisitoTecnico.dtos.RequisitoTecnicoCsv;
import com.configuradorlicenciamento.requisitoTecnico.dtos.RequisitoTecnicoEdicaoDTO;
import com.configuradorlicenciamento.requisitoTecnico.interfaces.IRequisitoTecnicoService;
import com.configuradorlicenciamento.requisitoTecnico.interfaces.ITipoLicencaGrupoDocumentoService;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisitoTecnico.models.TipoLicencaGrupoDocumento;
import com.configuradorlicenciamento.requisitoTecnico.repositories.RequisitoTecnicoRepository;
import com.configuradorlicenciamento.requisitoTecnico.specifications.RequisitoTecnicoSpecification;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequisitoTecnicoService implements IRequisitoTecnicoService {

    @Autowired
    RequisitoTecnicoRepository requisitoTecnicoRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Autowired
    ITipoLicencaGrupoDocumentoService tipoLicencaGrupoDocumentoService;

    @Override
    public RequisitoTecnico salvar(HttpServletRequest request, RequisitoTecnicoDTO requisitoTecnicoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        RequisitoTecnico requisitoTecnico = new RequisitoTecnico.RequisitoTecnicoBuilder(requisitoTecnicoDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        requisitoTecnicoRepository.save(requisitoTecnico);

        tipoLicencaGrupoDocumentoService.salvar(requisitoTecnicoDTO.getListRequisitos(), requisitoTecnico);

        return requisitoTecnico;

    }

    @Override
    public RequisitoTecnico editar(HttpServletRequest request, RequisitoTecnicoDTO requisitoTecnicoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        RequisitoTecnico requisitoTecnicosalvo = requisitoTecnicoRepository.findById(requisitoTecnicoDTO.getId())
                .map(requisitoTecnico -> {
                    requisitoTecnico.setCodigo(requisitoTecnicoDTO.getCodigo());
                    requisitoTecnico.setDescricao(requisitoTecnicoDTO.getDescricao());
                    requisitoTecnico.setUsuarioLicenciamento(usuarioLicenciamento);
                    requisitoTecnico.setDataCadastro(new Date());
                    requisitoTecnico.setAtivo(requisitoTecnicoDTO.getAtivo());
                    return requisitoTecnico;
                })
                .orElseThrow(() -> new ConfiguradorNotFoundException("Não Foi possível editar o Requisito"));

        requisitoTecnicoRepository.save(requisitoTecnicosalvo);

        tipoLicencaGrupoDocumentoService.editar(requisitoTecnicoDTO.getListRequisitos(), requisitoTecnicosalvo);

        return requisitoTecnicosalvo;

    }

    @Override
    public RequisitoTecnico ativarDesativar(Integer idRequisito) {

        RequisitoTecnico requisitoTecnico = requisitoTecnicoRepository.findById(idRequisito).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não Foi possível Ativar/Desativar o Requisito"));

        requisitoTecnico.setAtivo(!requisitoTecnico.getAtivo());

        requisitoTecnicoRepository.save(requisitoTecnico);

        return requisitoTecnico;
    }

    @Override
    public Page<RequisitoTecnico> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<RequisitoTecnico> specification = preparaFiltro(filtro);

        return requisitoTecnicoRepository.findAll(specification, pageable);
    }

    private Specification<RequisitoTecnico> preparaFiltro(FiltroPesquisa filtro) {

        Specification<RequisitoTecnico> specification = Specification.where(RequisitoTecnicoSpecification.padrao());

        if(filtro.getStringPesquisa() != null) {
            specification = specification.and(RequisitoTecnicoSpecification.codigo(filtro.getStringPesquisa())
                    .or(RequisitoTecnicoSpecification.descricao(filtro.getStringPesquisa())));
        }

        return specification;

    }

    @Override
    public List<RequisitoTecnicoCsv> listarDocumentoParaCsv() {

        List<RequisitoTecnico> requisitoTecnicoList = requisitoTecnicoRepository.findAll(Sort.by("codigo"));
        List<RequisitoTecnicoCsv> dtos = new ArrayList<>();

        for (RequisitoTecnico requisitoTecnico : requisitoTecnicoList) {
            dtos.add(requisitoTecnico.preparaParaCsv());
        }

        return dtos;
    }

    @Override
    public RequisitoTecnicoEdicaoDTO findById(Integer idRequisito) {

        RequisitoTecnico requisitoTecnico = requisitoTecnicoRepository.findById(idRequisito).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não foi encontrado requisito com o Id " + idRequisito));

        List<TipoLicencaGrupoDocumento> tipoLicencaGrupoDocumentoList = tipoLicencaGrupoDocumentoService.findByRequisito(requisitoTecnico);

        return new RequisitoTecnicoEdicaoDTO(requisitoTecnico, tipoLicencaGrupoDocumentoList);

    }
}