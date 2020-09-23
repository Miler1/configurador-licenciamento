package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.services;

import com.configuradorlicenciamento.atividade.interfaces.IAtividadeService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelDTO;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces.IRelAtividadePerguntaService;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces.ITipoCaracterizacaoAtividadeService;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories.RelAtividadePerguntaRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories.TipoCaracterizacaoAtividadeRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.specifications.TipoCaracterizacaoAtividadeSpecification;
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
public class TipoCaracterizacaoAtividadeService implements ITipoCaracterizacaoAtividadeService {

    @Autowired
    TipoCaracterizacaoAtividadeRepository tipoCaracterizacaoAtividadeRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Autowired
    AtividadeCnaeRepository atividadeCnaeRepository;

    @Autowired
    RelAtividadePerguntaRepository relAtividadePerguntaRepository;

    @Autowired
    IAtividadeService atividadeService;

    @Autowired
    IRelAtividadePerguntaService relAtividadePerguntaService;

    @Override
    public List<TipoCaracterizacaoAtividade> salvarAtividadeDispensavel(HttpServletRequest request, AtividadeDispensavelDTO atividadeDispensavelDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        List<TipoCaracterizacaoAtividade> tipoCaracterizacaoAtividadeList = new ArrayList<>();

        atividadeDispensavelDTO.getCnaesTipologia().forEach(cnaeTipologia -> {

            Atividade atividade = atividadeService.salvar(cnaeTipologia);

            Optional<AtividadeCnae> atividadeCnae = atividadeCnaeRepository.findById(cnaeTipologia.getCnae().getId());

            TipoCaracterizacaoAtividade tipoCaracterizacaoAtividade = new TipoCaracterizacaoAtividade.TipoCaracterizacaoAtividadeBuilder()
                    .setAtividade(atividade)
                    .setAtividadeCnae(atividadeCnae.get())
                    .setDataCadastro(new Date())
                    .setDispensaLicenciamento(true)
                    .setLicenciamentoDeclaratorio(false)
                    .setLicenciamentoSimplificado(false)
                    .setUsuarioLicencimento(usuarioLicenciamento)
                    .setAtivo(true)
                    .build();

            tipoCaracterizacaoAtividadeRepository.save(tipoCaracterizacaoAtividade);

            relAtividadePerguntaService.salvar(atividade, atividadeDispensavelDTO.getPerguntas());

            tipoCaracterizacaoAtividadeList.add(tipoCaracterizacaoAtividade);

        });

        return tipoCaracterizacaoAtividadeList;
    }

    @Override
    public Page<TipoCaracterizacaoAtividade> listarAtividadesDispensaveis(Pageable pageable, FiltroPesquisa filtro) {

        Specification<TipoCaracterizacaoAtividade> specification = preparaFiltroAtividadeDispensavel(filtro);

        return tipoCaracterizacaoAtividadeRepository.findAll(specification, pageable);

    }

    private Specification<TipoCaracterizacaoAtividade> preparaFiltroAtividadeDispensavel(FiltroPesquisa filtro) {

        Specification<TipoCaracterizacaoAtividade> specification = Specification.where(TipoCaracterizacaoAtividadeSpecification.padrao()
                .and(TipoCaracterizacaoAtividadeSpecification.filtrarAtividadesDispensaveis()));

        if(filtro.getStringPesquisa() != null) {
            specification = specification.and(TipoCaracterizacaoAtividadeSpecification.atividadeCnaeNome(filtro.getStringPesquisa())
                    .or(TipoCaracterizacaoAtividadeSpecification.atividadeCnaeCodigo(filtro.getStringPesquisa())));

        }

        return specification;

    }

    public List<TipoCaracterizacaoAtividade> listarAtividadesDispensaveis() {

        Specification<TipoCaracterizacaoAtividade> specification = Specification.where(TipoCaracterizacaoAtividadeSpecification.padrao()
                .and(TipoCaracterizacaoAtividadeSpecification.filtrarAtividadesDispensaveis()));

        return tipoCaracterizacaoAtividadeRepository.findAll(specification, Sort.by("id"));
    }

    @Override
    public List<AtividadeDispensavelCsv> listarAtividadesDispensaveisParaCsv(){

        List<TipoCaracterizacaoAtividade> tiposCaracterizacaoAtividade = listarAtividadesDispensaveis();
        List<AtividadeDispensavelCsv> dtos = new ArrayList<>();

        for (TipoCaracterizacaoAtividade tipoCaracterizacaoAtividade : tiposCaracterizacaoAtividade) {
            dtos.add(tipoCaracterizacaoAtividade.preparaAtividadeDispensavelParaCsv());
        }

        return dtos;
    }

}
