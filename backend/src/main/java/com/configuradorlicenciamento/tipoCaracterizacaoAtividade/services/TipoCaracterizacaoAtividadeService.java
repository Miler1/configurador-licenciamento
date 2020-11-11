package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.services;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeDTO;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.atividadeCnae.repositories.AtividadeCnaeRepository;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.interfaces.ITipoCaracterizacaoAtividadeService;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.repositories.TipoCaracterizacaoAtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoCaracterizacaoAtividadeService implements ITipoCaracterizacaoAtividadeService {

    @Autowired
    TipoCaracterizacaoAtividadeRepository tipoCaracterizacaoAtividadeRepository;

    @Autowired
    AtividadeCnaeRepository atividadeCnaeRepository;

    @Override
    public void salvarAtividadeDispensavel(AtividadeCnaeDTO atividadeCnaeDTO, Atividade atividade) {

        Optional<AtividadeCnae> atividadeCnae = atividadeCnaeRepository.findById(atividadeCnaeDTO.getId());

        TipoCaracterizacaoAtividade tipoCaracterizacaoAtividade = new TipoCaracterizacaoAtividade.TipoCaracterizacaoAtividadeBuilder()
                .setAtividade(atividade)
                .setAtividadeCnae(atividadeCnae.get())
                .setDispensaLicenciamento(true)
                .setLicenciamentoDeclaratorio(false)
                .setLicenciamentoSimplificado(false)
                .setAtivo(!atividade.getItemAntigo())
                .build();

        tipoCaracterizacaoAtividadeRepository.save(tipoCaracterizacaoAtividade);

    }

    @Override
    public void salvarAtividadeLicenciavel(List<AtividadeCnaeDTO> atividadesCnae, Atividade atividade) {

        atividadesCnae.forEach(cnae -> {

            Optional<AtividadeCnae> atividadeCnae = atividadeCnaeRepository.findById(cnae.getId());

            TipoCaracterizacaoAtividade tipoCaracterizacaoAtividade = new TipoCaracterizacaoAtividade.TipoCaracterizacaoAtividadeBuilder()
                    .setAtividade(atividade)
                    .setAtividadeCnae(atividadeCnae.get())
                    .setDispensaLicenciamento(false)
                    .setLicenciamentoDeclaratorio(false)
                    .setLicenciamentoSimplificado(true)
                    .setAtivo(!atividade.getItemAntigo())
                    .build();

            tipoCaracterizacaoAtividadeRepository.save(tipoCaracterizacaoAtividade);

        });

    }

    @Override
    public void editarAtividadeDispensavel(AtividadeCnaeDTO atividadesCnae, Atividade atividade) {

        List<TipoCaracterizacaoAtividade> tipoCaracterizacaoAtividades = tipoCaracterizacaoAtividadeRepository.findByAtividade(atividade);

        tipoCaracterizacaoAtividades.forEach(tipoCaracterizacaoAtividade -> tipoCaracterizacaoAtividadeRepository.delete(tipoCaracterizacaoAtividade));

        this.salvarAtividadeDispensavel(atividadesCnae, atividade);

    }

    @Override
    public void editarAtividadeLicenciavel(List<AtividadeCnaeDTO> atividadesCnae, Atividade atividade) {

        List<TipoCaracterizacaoAtividade> tipoCaracterizacaoAtividades = tipoCaracterizacaoAtividadeRepository.findByAtividade(atividade);

        tipoCaracterizacaoAtividades.forEach(tipoCaracterizacaoAtividade -> tipoCaracterizacaoAtividadeRepository.delete(tipoCaracterizacaoAtividade));

        this.salvarAtividadeLicenciavel(atividadesCnae, atividade);

    }

    @Override
    public void excluirAtividadeLicenciavel(Atividade atividade) {

        List<TipoCaracterizacaoAtividade> tipoCaracterizacaoAtividades = tipoCaracterizacaoAtividadeRepository.findByAtividade(atividade);

        if (!tipoCaracterizacaoAtividades.isEmpty()) {
            tipoCaracterizacaoAtividades.forEach(tipoCaracterizacaoAtividade -> tipoCaracterizacaoAtividadeRepository.delete(tipoCaracterizacaoAtividade));
        }

    }

}