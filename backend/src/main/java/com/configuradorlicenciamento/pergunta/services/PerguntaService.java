package com.configuradorlicenciamento.pergunta.services;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.interfaces.IPerguntaService;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.pergunta.repositories.PerguntaRepository;
import com.configuradorlicenciamento.pergunta.specifications.PerguntaSpecification;
import com.configuradorlicenciamento.resposta.dtos.RespostaDTO;
import com.configuradorlicenciamento.resposta.models.Resposta;
import com.configuradorlicenciamento.resposta.repositories.RespostaRepository;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import com.configuradorlicenciamento.taxaadministrativa.specifications.TaxaAdministrativaSpecification;
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

@Service
public class PerguntaService implements IPerguntaService {

    @Autowired
    PerguntaRepository perguntaRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public Pergunta salvar(HttpServletRequest request, PerguntaDTO perguntaDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Pergunta pergunta = new Pergunta.PerguntaBuilder(perguntaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        perguntaRepository.save(pergunta);

        setRespostas(pergunta, perguntaDTO.getRespostas());

        perguntaRepository.save(pergunta);

        return pergunta;

    }

    @Override
    public Page<Pergunta> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Pergunta> specification = preparaFiltro(filtro);

        return perguntaRepository.findAll(specification, pageable);

    }

    private Specification<Pergunta> preparaFiltro(FiltroPesquisa filtro) {

        Specification<Pergunta> specification = Specification.where(PerguntaSpecification.padrao());

        if (filtro.getStringPesquisa() != null && !filtro.getStringPesquisa().isEmpty()) {
            specification = specification.and(PerguntaSpecification.titulo(filtro.getStringPesquisa()));
        }

        return specification;

    }

    private void setRespostas(Pergunta pergunta, List<RespostaDTO> respostas) {

        if(pergunta.getRespostas() != null)
            pergunta.getRespostas().clear();

        List<Resposta> entidades = new ArrayList<>();

        for (RespostaDTO resposta : respostas){

            Resposta entidade = new Resposta.RespostaBuilder(resposta)
                    .setPergunta(pergunta)
                    .setDataCadastro(pergunta.getDataCadastro())
                    .setUsuarioLicencimento(pergunta.getUsuarioLicenciamento())
                    .build();

            entidades.add(entidade);

            respostaRepository.save(entidade);
        }

        pergunta.setRespostas(entidades);
    }

}
