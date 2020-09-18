package com.configuradorlicenciamento.historicoConfigurador.interfaces;

import com.configuradorlicenciamento.historicoConfigurador.models.Funcionalidade;

public interface IFuncionalidadeService {

    Funcionalidade findByid(Integer idFuncionalidade);

}
