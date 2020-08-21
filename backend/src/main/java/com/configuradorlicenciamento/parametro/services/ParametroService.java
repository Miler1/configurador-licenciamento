package com.configuradorlicenciamento.parametro.services;

import com.configuradorlicenciamento.configuracao.exceptions.ConstraintUniqueViolationException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.parametro.dtos.ParametroCsv;
import com.configuradorlicenciamento.parametro.dtos.ParametroDTO;
import com.configuradorlicenciamento.parametro.interfaces.IParametroService;
import com.configuradorlicenciamento.parametro.models.Parametro;
import com.configuradorlicenciamento.parametro.repositories.ParametroRepository;
import com.configuradorlicenciamento.parametro.specifications.ParametroSpecification;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ParametroService implements IParametroService {

    @Autowired
    ParametroRepository parametroRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public Parametro salvar(HttpServletRequest request, ParametroDTO parametroDTO) throws Exception {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Parametro parametro = new Parametro.ParametroBuilder(parametroDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        String codigo = parametroDTO.getCodigo();

        boolean existsCodigo = parametroRepository.existsByCodigo(codigo);

        if (existsCodigo) {

            throw new ConstraintUniqueViolationException("Um parâmetro com código '" + codigo + "' já está cadastrado.");
        }

        parametroRepository.save(parametro);

        return parametro;
    }

    private Specification<Parametro> preparaFiltro(FiltroPesquisa filtro) {

        Specification<Parametro> specification = Specification.where(ParametroSpecification.padrao());

        if (filtro.getStringPesquisa() != null) {
            specification = specification.and(ParametroSpecification.nome(filtro.getStringPesquisa())
                    .or(ParametroSpecification.codigo(filtro.getStringPesquisa())));
        }

        return specification;

    }

    public Page<Parametro> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Parametro> specification = preparaFiltro(filtro);

        return parametroRepository.findAll(specification, pageable);
    }

    @Override
    public List<ParametroCsv> listarParametrosParaCsv() {

        List<Parametro> parametros = listarParametros();
        List<ParametroCsv> dtos = new ArrayList<>();

        for (Parametro parametro : parametros) {
            dtos.add(parametro.preparaParaCsv());
        }

        return dtos;
    }

    @Override
    public List<Parametro> listarParametros() {
        return parametroRepository.findAll(Sort.by("codigo"));
    }

}