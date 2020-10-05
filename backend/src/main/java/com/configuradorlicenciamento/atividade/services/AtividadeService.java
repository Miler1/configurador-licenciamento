package com.configuradorlicenciamento.atividade.services;

import com.configuradorlicenciamento.atividade.interfaces.IAtividadeService;
import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividade.models.TipoAtividade;
import com.configuradorlicenciamento.atividade.repositories.AtividadeRepository;
import com.configuradorlicenciamento.atividade.repositories.TipoAtividadeRepository;
import com.configuradorlicenciamento.atividade.specifications.AtividadeSpecification;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.potencialPoluidor.repositories.PotencialPoluidorRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelDTO;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.tipologia.repositories.TipologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtividadeService implements IAtividadeService {

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    AtividadeCnaeRepository atividadeCnaeRepository;

    @Autowired
    TipologiaRepository tipologiaRepository;

    @Autowired
    PotencialPoluidorRepository potencialPoluidorRepository;

    @Autowired
    TipoAtividadeRepository tipoAtividadeRepository;

    @Override
    public Atividade salvar(AtividadeDispensavelDTO.RelacaoCnaeTipologia atividadeDispensavelDTO) {

        Optional<AtividadeCnae> atividadeCnae = atividadeCnaeRepository.findById(atividadeDispensavelDTO.getCnae().getId());

        Optional<Tipologia> tipologia = tipologiaRepository.findById(atividadeDispensavelDTO.getTipologia().getId());

        PotencialPoluidor potencialPoluidor = potencialPoluidorRepository.findByCodigo("I");

        List<TipoAtividade> tiposAtividades = tipoAtividadeRepository.findAll();

        Atividade atividade = new Atividade.AtividadeBuilder(atividadeDispensavelDTO)
                .setNome(atividadeCnae.get().getNome())
                .setTipologia(tipologia.get())
                .setGeoLinha(true)
                .setGeoPonto(true)
                .setGeoPoligono(true)
                .setCodigo("0000")
                .setPotencialPoluidor(potencialPoluidor)
                .setAtivo(true)
                .setDentroEmpreendimento(false)
                .setV1(false)
                .setTiposAtividades(tiposAtividades)
                .build();

        atividadeRepository.save(atividade);

        return atividade;

    }

    @Override
    public Atividade editar(AtividadeDispensavelDTO.RelacaoCnaeTipologia atividadeDispensavelDTO, Atividade atividade) {

        Optional<AtividadeCnae> atividadeCnae = atividadeCnaeRepository.findById(atividadeDispensavelDTO.getCnae().getId());

        Optional<Tipologia> tipologia = tipologiaRepository.findById(atividadeDispensavelDTO.getTipologia().getId());

        PotencialPoluidor potencialPoluidor = potencialPoluidorRepository.findByCodigo("I");

        List<TipoAtividade> tiposAtividades = tipoAtividadeRepository.findAll();

        atividade.setNome(atividadeCnae.get().getNome());
        atividade.setTipologia(tipologia.get());
        atividade.setGeoLinha(true);
        atividade.setGeoPonto(true);
        atividade.setGeoPoligono(true);
        atividade.setCodigo("0000");
        atividade.setPotencialPoluidor(potencialPoluidor);
        atividade.setDentroEmpreendimento(false);
        atividade.setDentroMunicipio(!atividadeDispensavelDTO.getForaMunicipio());
        atividade.setV1(false);
        atividade.setTiposAtividades(tiposAtividades);

        atividadeRepository.save(atividade);

        return atividade;

    }

    @Override
    public Page<Atividade> listarAtividadesLicenciaveis(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Atividade> specification = preparaFiltroAtividadeLicenciavel(filtro);

        return atividadeRepository.findAll(specification, pageable);

    }

    private Specification<Atividade> preparaFiltroAtividadeLicenciavel(FiltroPesquisa filtro) {

        Specification<Atividade> specification = Specification.where(AtividadeSpecification.padrao()
                .and(AtividadeSpecification.filtrarAtividadesLicenciaveis()));

        if (filtro.getStringPesquisa() != null) {

            specification = specification.and(AtividadeSpecification.atividadeNome(filtro.getStringPesquisa())
                    .or(AtividadeSpecification.atividadeCodigo(filtro.getStringPesquisa())));

        }

        return specification;

    }

}