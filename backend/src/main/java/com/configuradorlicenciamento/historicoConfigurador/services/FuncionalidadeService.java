package com.configuradorlicenciamento.historicoConfigurador.services;

import com.configuradorlicenciamento.historicoConfigurador.interfaces.IFuncionalidadeService;
import com.configuradorlicenciamento.historicoConfigurador.models.Funcionalidade;
import com.configuradorlicenciamento.historicoConfigurador.repositories.FuncionalidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionalidadeService implements IFuncionalidadeService {

    @Autowired
    FuncionalidadeRepository funcionalidadeRepository;

    @Override
    public Funcionalidade findByid(Integer idFuncionalidade) {
        return null;
    }

}
