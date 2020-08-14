package com.configuradorlicenciamento.licenca.services;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.licenca.dtos.LicencaCsv;
import com.configuradorlicenciamento.licenca.dtos.LicencaDTO;
import com.configuradorlicenciamento.licenca.interfaces.ILicencaService;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.licenca.repositories.LicencaRepository;
import com.configuradorlicenciamento.licenca.specifications.LicencaSpecification;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
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
public class LicencaService implements ILicencaService {

    @Autowired
    LicencaRepository licencaRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public Licenca salvar(HttpServletRequest request, LicencaDTO licencaDTO) throws Exception {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Licenca licenca = new Licenca.LicencaBuilder(licencaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        licencaRepository.save(licenca);

        return licenca;
    }

    @Override
    public Licenca editar(HttpServletRequest request, LicencaDTO licencaDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<Licenca> licencaSalva = licencaRepository.findById(licencaDTO.getId())
                .map(licenca -> {
                    licenca.setSigla(licencaDTO.getSigla());
                    licenca.setNome(licencaDTO.getNome());
                    licenca.setFinalidade(licencaDTO.getFinalidade());
                    licenca.setValidadeEmAnos(licencaDTO.getValidadeEmAnos());
                    licenca.setUsuarioLicenciamento(usuarioLicenciamento);
                    licenca.setDataCadastro(new Date());
                    licenca.setAtivo(licencaDTO.getAtivo());
                    return licenca;
                });

        licencaRepository.save(licencaSalva.get());

        return licencaSalva.get();

    }

    public Page<Licenca> lista(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Licenca> specification = preparaFiltro(filtro);

        Page<Licenca> licenca = licencaRepository.findAll(specification, pageable);

        return licenca;
    }

    private Specification<Licenca> preparaFiltro(FiltroPesquisa filtro) {

        Specification specification = Specification.where(LicencaSpecification.padrao());

        if(filtro.getStringPesquisa() != null) {
            specification = specification.and(LicencaSpecification.nome(filtro.getStringPesquisa())
                    .or(LicencaSpecification.sigla(filtro.getStringPesquisa())));
        }

        return specification;

    }

    @Override
    public List<Licenca> listarLicencas() {
        return licencaRepository.findAll(Sort.by("sigla"));
    }

    @Override
    public List<LicencaCsv> listarLicencasParaCsv(){

        List<Licenca> licencas = listarLicencas();
        List<LicencaCsv> dtos = new ArrayList<>();

        for (Licenca licenca : licencas) {
            dtos.add(licenca.preparaParaCsv());
        }

        return dtos;
    }
}