package com.configuradorlicenciamento.atividade.interfaces;

import com.configuradorlicenciamento.atividade.dtos.PorteAtividadeDTO;
import com.configuradorlicenciamento.atividade.models.PorteAtividade;

import java.util.List;

public interface IPorteAtividadeService {

    List<PorteAtividade> salvar(List<PorteAtividadeDTO> portes);

    List<PorteAtividade> editar(List<PorteAtividadeDTO> portes);
}
