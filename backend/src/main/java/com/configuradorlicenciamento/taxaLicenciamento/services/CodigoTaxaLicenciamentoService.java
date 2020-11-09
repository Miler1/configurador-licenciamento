package com.configuradorlicenciamento.taxaLicenciamento.services;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.historicoConfigurador.interfaces.IHistoricoConfiguradorService;
import com.configuradorlicenciamento.historicoConfigurador.models.AcaoConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.FuncionalidadeConfigurador;
import com.configuradorlicenciamento.historicoConfigurador.models.HistoricoConfigurador;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoCsv;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoEdicaoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.interfaces.ICodigoTaxaLicenciamentoService;
import com.configuradorlicenciamento.taxaLicenciamento.interfaces.ITaxaLicenciamentoService;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.repositories.CodigoTaxaLicenciamentoRepository;
import com.configuradorlicenciamento.taxaLicenciamento.specifications.CodigoTaxaLicenciamentoSpecification;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodigoTaxaLicenciamentoService implements ICodigoTaxaLicenciamentoService {

    public static final String TAXA_EXISTENTE = "Já existe uma tabela com o mesmo código.";

    private static final String VINCULO_EXISTENTE = "Não é possível inativar o registro pois, ele se encontra vinculado a uma atividade ativa no sistema.";

    @Autowired
    CodigoTaxaLicenciamentoRepository codigoTaxaLicenciamentoRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    ITaxaLicenciamentoService taxaLicenciamentoService;

    @Autowired
    IHistoricoConfiguradorService historicoConfiguradorService;

    @Override
    public CodigoTaxaLicenciamento salvar(HttpServletRequest request, CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO) {

        codigoTaxaLicenciamentoDTO.setCodigo(StringUtil.tratarEspacos(codigoTaxaLicenciamentoDTO.getCodigo()));
        String codigo = codigoTaxaLicenciamentoDTO.getCodigo();

        boolean existeCodigo = codigoTaxaLicenciamentoRepository.existsByCodigo(codigo);

        if (existeCodigo) {
            throw new ConflictException(TAXA_EXISTENTE);
        }

        CodigoTaxaLicenciamento codigoTaxaLicenciamento =
                new CodigoTaxaLicenciamento.CodigoTaxaLicenciamentoBuilder(codigoTaxaLicenciamentoDTO).build();

        codigoTaxaLicenciamentoRepository.save(codigoTaxaLicenciamento);

        taxaLicenciamentoService.salvar(codigoTaxaLicenciamentoDTO.getListTaxasLicenciamento(), codigoTaxaLicenciamento);

        historicoConfiguradorService.salvar(
                request,
                codigoTaxaLicenciamento.getId(),
                FuncionalidadeConfigurador.Funcionalidades.TAXA_LICENCIAMENTO.getTipo(),
                AcaoConfigurador.Acoes.CADASTRAR.getAcao()
        );

        return codigoTaxaLicenciamento;

    }

    @Override
    public CodigoTaxaLicenciamento editar(HttpServletRequest request, CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO) {

        codigoTaxaLicenciamentoDTO.setCodigo(StringUtil.tratarEspacos(codigoTaxaLicenciamentoDTO.getCodigo()));

        String codigo = codigoTaxaLicenciamentoDTO.getCodigo();

        boolean existeCodigo = codigoTaxaLicenciamentoRepository.existsByCodigo(codigo);

        if (existeCodigo) {

            CodigoTaxaLicenciamento codigoTaxaLicenciamento = codigoTaxaLicenciamentoRepository.findByCodigo(codigo);

            if (codigoTaxaLicenciamento != null && !codigoTaxaLicenciamento.getId().equals(codigoTaxaLicenciamentoDTO.getId())) {
                throw new ConflictException(TAXA_EXISTENTE);
            }

        }

        CodigoTaxaLicenciamento codigoTaxaLicenciamentoSalvo = codigoTaxaLicenciamentoRepository.findById(codigoTaxaLicenciamentoDTO.getId())
                .map(codigoTaxaLicenciamento -> {
                    codigoTaxaLicenciamento.setCodigo(codigoTaxaLicenciamentoDTO.getCodigo());
                    codigoTaxaLicenciamento.setDescricao(codigoTaxaLicenciamentoDTO.getDescricao());
                    codigoTaxaLicenciamento.setAtivo(codigoTaxaLicenciamentoDTO.getAtivo());
                    return codigoTaxaLicenciamento;
                })
                .orElseThrow(() -> new ConfiguradorNotFoundException("Não Foi possível editar a taxa de licenciamento"));

        codigoTaxaLicenciamentoRepository.save(codigoTaxaLicenciamentoSalvo);

        taxaLicenciamentoService.editar(codigoTaxaLicenciamentoDTO.getListTaxasLicenciamento(), codigoTaxaLicenciamentoSalvo);

        historicoConfiguradorService.editar(
                request,
                null,
                codigoTaxaLicenciamentoSalvo.getId(),
                FuncionalidadeConfigurador.Funcionalidades.TAXA_LICENCIAMENTO.getTipo(),
                AcaoConfigurador.Acoes.EDITAR.getAcao(),
                codigoTaxaLicenciamentoDTO.getJustificativa());

        return codigoTaxaLicenciamentoSalvo;

    }

    @Override
    public CodigoTaxaLicenciamento ativarDesativar(HttpServletRequest request, Integer idTaxaLicenciamento) {

        CodigoTaxaLicenciamento codigoTaxaLicenciamento = codigoTaxaLicenciamentoRepository.findById(idTaxaLicenciamento).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não Foi possível Ativar/Desativar a taxa de licenciamento"));

        List<TaxaLicenciamento> taxasLicencasList = taxaLicenciamentoService.findByCodigo(codigoTaxaLicenciamento);

        taxasLicencasList.forEach(taxasLicencas -> {

            List<Atividade> atividadesList = atividadeRepository.findByTaxasLicenciamento(taxasLicencas);
            atividadesList.forEach(atividade -> {

                boolean ativo = atividade.getAtivo();

                if (ativo) {
                    throw new ConflictException(VINCULO_EXISTENTE);
                }

            });

        });

        codigoTaxaLicenciamento.setAtivo(!codigoTaxaLicenciamento.getAtivo());

        codigoTaxaLicenciamentoRepository.save(codigoTaxaLicenciamento);

        boolean ativo = codigoTaxaLicenciamento.getAtivo();

        String acao = ativo ? AcaoConfigurador.Acoes.ATIVAR.getAcao() : AcaoConfigurador.Acoes.DESATIVAR.getAcao();

        historicoConfiguradorService.salvar(
                request,
                codigoTaxaLicenciamento.getId(),
                FuncionalidadeConfigurador.Funcionalidades.TAXA_LICENCIAMENTO.getTipo(),
                acao
        );

        return codigoTaxaLicenciamento;

    }

    @Override
    public Page<CodigoTaxaLicenciamento> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<CodigoTaxaLicenciamento> specification = preparaFiltro(filtro);

        return codigoTaxaLicenciamentoRepository.findAll(specification, pageable);

    }

    @Override
    public List<CodigoTaxaLicenciamento> listarCodigoTaxaLicenciamento() {
        return codigoTaxaLicenciamentoRepository.findAll(Sort.by("codigo"));
    }

    @Override
    public List<CodigoTaxaLicenciamentoCsv> listarCodigoTaxaLicenciamentoParaCsv() {

        List<CodigoTaxaLicenciamento> licenciamentos = listarCodigoTaxaLicenciamento();
        List<CodigoTaxaLicenciamentoCsv> dtos = new ArrayList<>();

        for (CodigoTaxaLicenciamento codigoTaxaLicenciamento : licenciamentos) {

            List<HistoricoConfigurador> historicos = historicoConfiguradorService.buscarHistoricoItem(
                    FuncionalidadeConfigurador.Funcionalidades.TAXA_LICENCIAMENTO.getTipo(),
                    codigoTaxaLicenciamento.getId()
            );

            dtos.add(codigoTaxaLicenciamento.prepararParaCsv(
                    !historicos.isEmpty() ? historicos.get(0).getDataCadastro() : null,
                    !historicos.isEmpty() ? historicos.get(0).getUsuarioLicenciamento() : null)
            );

        }

        return dtos;

    }

    @Override
    public CodigoTaxaLicenciamentoEdicaoDTO findById(Integer idTaxaLicenciamento) {

        CodigoTaxaLicenciamento codigoTaxaLicenciamento = codigoTaxaLicenciamentoRepository.findById(idTaxaLicenciamento).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não foi encontrada taxa de licenciamento com o Id " + idTaxaLicenciamento));

        List<TaxaLicenciamento> taxasLicencas = taxaLicenciamentoService.findByCodigo(codigoTaxaLicenciamento);

        return new CodigoTaxaLicenciamentoEdicaoDTO(codigoTaxaLicenciamento, taxasLicencas);

    }

    @Override
    public List<CodigoTaxaLicenciamento> findAtivos() {
        return codigoTaxaLicenciamentoRepository.findByAtivo(true, Sort.by("codigo"));
    }

    private Specification<CodigoTaxaLicenciamento> preparaFiltro(FiltroPesquisa filtro) {

        Specification<CodigoTaxaLicenciamento> specification = Specification.where(CodigoTaxaLicenciamentoSpecification.padrao());

        if (filtro.getStringPesquisa() != null) {
            specification = specification.and(CodigoTaxaLicenciamentoSpecification.codigo(filtro.getStringPesquisa())
                    .or(CodigoTaxaLicenciamentoSpecification.descricao(filtro.getStringPesquisa())));
        }

        return specification;

    }

}