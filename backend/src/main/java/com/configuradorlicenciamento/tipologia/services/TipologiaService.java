package com.configuradorlicenciamento.tipologia.services;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaCsv;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
import com.configuradorlicenciamento.tipologia.interfaces.ITipologiaService;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.tipologia.repositories.TipologiaRepository;
import com.configuradorlicenciamento.tipologia.specifications.TipologiaSpecification;
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
public class TipologiaService implements ITipologiaService {

    private static final String TIPOLOGIA_EXISTENTE = "Já existe uma tipologia com o mesmo nome ou semelhante.";
    private static final String VINCULO_ATIVIDADE_LICENCIAVEL = "Erro! Não foi possível desativar/ativar a tipologia. Ela se encontra vinculada a uma atividade licenciável ativa no sistema.";
    private static final String VINCULO_ATIVIDADE_DISPENSAVEL = "Erro! Não foi possível desativar/ativar a tipologia. Ela se encontra vinculada a uma atividade dispensável ativa no sistema.";

    @Autowired
    TipologiaRepository tipologiaRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    public Tipologia salvar(HttpServletRequest request, TipologiaDTO tipologiaDTO) throws Exception {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Tipologia tipologia = new Tipologia.TipologiaBuilder(tipologiaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        boolean codigoExistente = tipologiaRepository.existsByCodigo(tipologia.getCodigo());

        if (codigoExistente) {
            throw new ConflictException(TIPOLOGIA_EXISTENTE);
        }

        tipologiaRepository.save(tipologia);

        return tipologia;
    }

    @Override
    public Tipologia editar(HttpServletRequest request, TipologiaDTO tipologiaDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<Tipologia> tipologiaSalva = tipologiaRepository.findById(tipologiaDTO.getId());

        if (tipologiaSalva.isEmpty()) {
            throw new ConfiguradorNotFoundException("Por algum motivo a tipologia não foi encontrada.");
        }

        Tipologia tipologia = tipologiaSalva.get();

        if (!tipologiaDTO.getAtivo().equals(tipologia.getAtivo())) {
            tipologia.setAtivo(tipologiaDTO.getAtivo());
        } else {

            tipologia.setNome(tipologiaDTO.getNome());
            tipologia.setCodigo(Tipologia.TipologiaBuilder.gerarCodigo(tipologia.getNome()));
            tipologia.setUsuarioLicenciamento(usuarioLicenciamento);
            tipologia.setDataCadastro(new Date());

            boolean codigoExistente = tipologiaRepository.existsByCodigo(tipologia.getCodigo());

            if (codigoExistente) {

                Tipologia tipologiaExistente = tipologiaRepository.findByCodigo(tipologia.getCodigo());

                if (tipologiaExistente != null && !tipologia.getId().equals(tipologiaExistente.getId())) {
                    throw new ConflictException(TIPOLOGIA_EXISTENTE);
                }
            }
        }

        tipologiaRepository.save(tipologia);

        return tipologia;

    }

    @Override
    public Tipologia ativarDesativar(HttpServletRequest request, Integer idTipologia) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Tipologia tipologiaExistente = tipologiaRepository.findById(idTipologia).get();

        List<Atividade> atividadesList = atividadeRepository.findByTipologia(tipologiaExistente);

        atividadesList.forEach(atividade -> {

            boolean ativo = atividade.getAtivo();

            String codigo = atividade.getCodigo();

            if (ativo && !codigo.equals("0000")) {
                throw new ConflictException(VINCULO_ATIVIDADE_LICENCIAVEL);
            } else if (ativo) {
                throw new ConflictException(VINCULO_ATIVIDADE_DISPENSAVEL);
            }

        });

        Optional<Tipologia> tipologiaSalva = tipologiaRepository.findById(idTipologia)
                .map(tipologia -> {
                    tipologia.setAtivo(!tipologiaExistente.getAtivo());
                    tipologia.setUsuarioLicenciamento(usuarioLicenciamento);
                    tipologia.setDataCadastro(new Date());
                    return tipologia;
                });

        tipologiaRepository.save(tipologiaSalva.get());

        return tipologiaSalva.get();
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

    @Override
    public List<Tipologia> findAtivos() {

        return tipologiaRepository.findByAtivo(true);

    }

}
