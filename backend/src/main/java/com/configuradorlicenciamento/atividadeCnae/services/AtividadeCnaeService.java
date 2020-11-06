package com.configuradorlicenciamento.atividadeCnae.services;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.interfaces.IAtividadeCnaeService;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.atividadeCnae.specifications.AtividadeCnaeSpecification;
import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories.TipoCaracterizacaoAtividadeRepository;
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
public class AtividadeCnaeService implements IAtividadeCnaeService {

    private static final String VINCULO_EXISTENTE = "Não é possível inativar o registro pois, ele se encontra vinculado a uma atividade ativa no sistema.";

    @Autowired
    AtividadeCnaeRepository atividadeCnaeRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Autowired
    TipoCaracterizacaoAtividadeRepository tipoCaracterizacaoAtividadeRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Override
    public AtividadeCnae salvar(HttpServletRequest request, AtividadeCnaeDTO atividadeCnaeDTO) {

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
    public AtividadeCnae editar(HttpServletRequest request, AtividadeCnaeDTO atividadeCnaeDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<AtividadeCnae> atividadeCnaeSalva = atividadeCnaeRepository.findById(atividadeCnaeDTO.getId())
                .map(atividadeCnae -> {
                    atividadeCnae.setNome(atividadeCnaeDTO.getNome());
                    atividadeCnae.setCodigo(atividadeCnaeDTO.getCodigo());
                    atividadeCnae.setUsuarioLicenciamento(usuarioLicenciamento);
                    atividadeCnae.setDataCadastro(new Date());
                    atividadeCnae.setAtivo(atividadeCnaeDTO.getAtivo());
                    return atividadeCnae;
                });

        atividadeCnaeRepository.save(atividadeCnaeSalva.get());

        return atividadeCnaeSalva.get();

    }

    @Override
    public AtividadeCnae ativarDesativar(HttpServletRequest request, Integer idAtividadeCnae) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        AtividadeCnae atividadeCnaeExistente = atividadeCnaeRepository.findById(idAtividadeCnae).get();

        List<TipoCaracterizacaoAtividade> tipoCaracterizacaoAtividadeList = tipoCaracterizacaoAtividadeRepository.findByAtividadeCnae(atividadeCnaeExistente);

        tipoCaracterizacaoAtividadeList.forEach(tipoCaracterizacaoAtividade -> {

            Atividade atividade = atividadeRepository.findById(tipoCaracterizacaoAtividade.getAtividade().getId()).get();

            boolean ativo = atividade.getAtivo();

            if (ativo) {
                throw new ConflictException(VINCULO_EXISTENTE);
            }

        });

        Optional<AtividadeCnae> atividadeCnaeSalva = atividadeCnaeRepository.findById(idAtividadeCnae)
                .map(atividadeCnae -> {
                    atividadeCnae.setAtivo(!atividadeCnaeExistente.getAtivo());
                    atividadeCnae.setDataCadastro(new Date());
                    atividadeCnae.setUsuarioLicenciamento(usuarioLicenciamento);
                    return atividadeCnae;
                });

        atividadeCnaeRepository.save(atividadeCnaeSalva.get());

        return atividadeCnaeSalva.get();

    }

    @Override
    public Page<AtividadeCnae> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<AtividadeCnae> specification = preparaFiltro(filtro);

        return atividadeCnaeRepository.findAll(specification, pageable);

    }

    private Specification<AtividadeCnae> preparaFiltro(FiltroPesquisa filtro) {

        Specification<AtividadeCnae> specification = Specification.where(AtividadeCnaeSpecification.padrao());

        if (filtro.getStringPesquisa() != null) {
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
    public List<AtividadeCnaeCsv> listarCnaesParaCsv() {

        List<AtividadeCnae> cnaes = listarCnaes();
        List<AtividadeCnaeCsv> dtos = new ArrayList<>();

        for (AtividadeCnae cnae : cnaes) {
            dtos.add(cnae.preparaParaCsv());
        }

        return dtos;
    }

    @Override
    public List<AtividadeCnae> findAtivosNaoVinculados() {
        return atividadeCnaeRepository.findAllWithDescriptionQuery();
    }

    @Override
    public List<AtividadeCnae> findAtividadesCnaesByAtivos() {
        return atividadeCnaeRepository.findByAtivo(true);
    }

}