package com.configuradorlicenciamento.potencialPoluidor.services;

import com.configuradorlicenciamento.potencialPoluidor.interfaces.IPotencialPoluidorService;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.potencialPoluidor.repositories.PotencialPoluidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotencialPoluidorService implements IPotencialPoluidorService {

    @Autowired
    PotencialPoluidorRepository potencialPoluidorRepository;

    @Override
    public List<PotencialPoluidor> findAll() {

        return potencialPoluidorRepository.findAll();

    }
}
