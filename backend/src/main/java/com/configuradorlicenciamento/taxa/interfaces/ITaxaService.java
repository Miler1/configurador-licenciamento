package com.configuradorlicenciamento.taxa.interfaces;

import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.taxa.models.Taxa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITaxaService {


    Page<Taxa> listar(Pageable pageable, FiltroPesquisa filtro);

}