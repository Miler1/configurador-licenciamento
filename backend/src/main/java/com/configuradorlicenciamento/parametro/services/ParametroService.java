package com.configuradorlicenciamento.parametro.services;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

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

        if (parametroRepository.existsByCodigo(codigo)) {

            throw new RuntimeException("Um parâmetro com código '" + codigo + "' já está cadastrado.");
        }

        parametroRepository.save(parametro);

        return parametro;
    }

    @Override
    public Parametro editar(HttpServletRequest request, ParametroDTO parametroDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<Parametro> parametroSalvo = parametroRepository.findById(parametroDTO.getId())
                .map(parametro -> {
                    parametro.setCodigo(parametroDTO.getCodigo());
                    parametro.setNome(parametroDTO.getNome());
                    parametro.setCasasDecimais(parametroDTO.getCasasDecimais());
                    parametro.setUsuarioLicenciamento(usuarioLicenciamento);
                    parametro.setDataCadastro(new Date());
                    parametro.setAtivo(parametroDTO.getAtivo());
                    return parametro;
                });

        String codigo = parametroDTO.getCodigo();

        if(parametroRepository.existsByCodigo(codigo)) {

            Parametro parametroExistente = parametroRepository.findByCodigo(codigo);

            if (parametroExistente != null && !parametroDTO.getId().equals(parametroExistente.getId())) {

                throw new RuntimeException("Um parâmetro com código '" + codigo + "' já está cadastrado.");
            }

        }

        parametroRepository.save(parametroSalvo.get());

        return parametroSalvo.get();

    }

    private Specification<Parametro> preparaFiltro(FiltroPesquisa filtro) {

        Specification specification = Specification.where(ParametroSpecification.padrao());

        if(filtro.getStringPesquisa() != null) {
            specification = specification.and(ParametroSpecification.nome(filtro.getStringPesquisa())
                    .or(ParametroSpecification.codigo(filtro.getStringPesquisa())));
        }

        return specification;

    }

    public Page<Parametro> lista(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Parametro> specification = preparaFiltro(filtro);

        Page<Parametro> parametros = parametroRepository.findAll(specification, pageable);

        return parametros;
    }
}