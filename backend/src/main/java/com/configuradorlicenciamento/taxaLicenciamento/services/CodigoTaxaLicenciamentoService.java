package com.configuradorlicenciamento.taxaLicenciamento.services;

import com.configuradorlicenciamento.configuracao.exceptions.ConfiguradorNotFoundException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoCsv;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.CodigoTaxaLicenciamentoEdicaoDTO;
import com.configuradorlicenciamento.taxaLicenciamento.interfaces.ICodigoTaxaLicenciamentoService;
import com.configuradorlicenciamento.taxaLicenciamento.interfaces.ITaxaLicenciamentoService;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.repositories.CodigoTaxaLicenciamentoRepository;
import com.configuradorlicenciamento.taxaLicenciamento.specifications.CodigoTaxaLicenciamentoSpecification;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class CodigoTaxaLicenciamentoService implements ICodigoTaxaLicenciamentoService {

    @Autowired
    CodigoTaxaLicenciamentoRepository codigoTaxaLicenciamentoRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Autowired
    ITaxaLicenciamentoService taxaLicenciamentoService;

    @Override
    public CodigoTaxaLicenciamento salvar(HttpServletRequest request, CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        CodigoTaxaLicenciamento codigoTaxaLicenciamento = new CodigoTaxaLicenciamento.CodigoTaxaLicenciamentoBuilder(codigoTaxaLicenciamentoDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        codigoTaxaLicenciamentoRepository.save(codigoTaxaLicenciamento);

        taxaLicenciamentoService.salvar(codigoTaxaLicenciamentoDTO.getListTaxasLicenciamento(), codigoTaxaLicenciamento);

        return codigoTaxaLicenciamento;

    }

    @Override
    public CodigoTaxaLicenciamento editar(HttpServletRequest request, CodigoTaxaLicenciamentoDTO codigoTaxaLicenciamentoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        CodigoTaxaLicenciamento codigoTaxaLicenciamentoSalvo = codigoTaxaLicenciamentoRepository.findById(codigoTaxaLicenciamentoDTO.getId())
                .map(codigoTaxaLicenciamento -> {
                    codigoTaxaLicenciamento.setCodigo(codigoTaxaLicenciamentoDTO.getCodigo());
                    codigoTaxaLicenciamento.setDescricao(codigoTaxaLicenciamentoDTO.getDescricao());
                    codigoTaxaLicenciamento.setUsuarioLicenciamento(usuarioLicenciamento);
                    codigoTaxaLicenciamento.setDataCadastro(new Date());
                    codigoTaxaLicenciamento.setAtivo(codigoTaxaLicenciamentoDTO.getAtivo());
                    return codigoTaxaLicenciamento;
                })
                .orElseThrow(() -> new ConfiguradorNotFoundException("Não Foi possível editar a taxa de licenciamento"));

        codigoTaxaLicenciamentoRepository.save(codigoTaxaLicenciamentoSalvo);

        taxaLicenciamentoService.editar(codigoTaxaLicenciamentoDTO.getListTaxasLicenciamento(), codigoTaxaLicenciamentoSalvo);

        return codigoTaxaLicenciamentoSalvo;

    }

    @Override
    public CodigoTaxaLicenciamento ativarDesativar(Integer idTaxaLicenciamento) {

        CodigoTaxaLicenciamento codigoTaxaLicenciamento = codigoTaxaLicenciamentoRepository.findById(idTaxaLicenciamento).orElseThrow(() ->
                new ConfiguradorNotFoundException("Não Foi possível Ativar/Desativar a taxa de licenciamento"));

        codigoTaxaLicenciamento.setAtivo(!codigoTaxaLicenciamento.getAtivo());

        codigoTaxaLicenciamentoRepository.save(codigoTaxaLicenciamento);

        return codigoTaxaLicenciamento;

    }

    @Override
    public Page<CodigoTaxaLicenciamento> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<CodigoTaxaLicenciamento> specification = preparaFiltro(filtro);

        return codigoTaxaLicenciamentoRepository.findAll(specification, pageable);

    }

    @Override
    public List<CodigoTaxaLicenciamento> listarCodigoTaxaLicenciamento() {
        return codigoTaxaLicenciamentoRepository.findAll(Sort.by("dataCadastro"));
    }

    @Override
    public List<CodigoTaxaLicenciamentoCsv> listarCodigoTaxaLicenciamentoParaCsv() {

        List<CodigoTaxaLicenciamento> licenciamentos = listarCodigoTaxaLicenciamento();
        List<CodigoTaxaLicenciamentoCsv> dtos = new ArrayList<>();

        for (CodigoTaxaLicenciamento codigoTaxaLicenciamento : licenciamentos) {
            dtos.add(codigoTaxaLicenciamento.prepararParaCsv());
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


    private Specification<CodigoTaxaLicenciamento> preparaFiltro(FiltroPesquisa filtro) {

        Specification<CodigoTaxaLicenciamento> specification = Specification.where(CodigoTaxaLicenciamentoSpecification.padrao());

        if (filtro.getStringPesquisa() != null) {
            specification = specification.and(CodigoTaxaLicenciamentoSpecification.codigo(filtro.getStringPesquisa())
                    .or(CodigoTaxaLicenciamentoSpecification.descricao(filtro.getStringPesquisa())));
        }

        return specification;

    }

}
