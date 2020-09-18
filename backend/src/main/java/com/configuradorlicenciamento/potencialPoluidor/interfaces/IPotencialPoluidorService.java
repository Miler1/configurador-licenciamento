package com.configuradorlicenciamento.potencialPoluidor.interfaces;

import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;

import java.util.List;

public interface IPotencialPoluidorService {
    List<PotencialPoluidor> findAll();
}
