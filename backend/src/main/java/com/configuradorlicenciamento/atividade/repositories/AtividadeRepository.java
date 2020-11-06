package com.configuradorlicenciamento.atividade.repositories;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.parametro.models.Parametro;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Integer>, JpaSpecificationExecutor<Atividade> {

    Boolean existsByCodigo(String codigo);

    Atividade findByCodigo(String codigo);

    List<Atividade> findByTiposLicencas(Licenca licenca);

    List<Atividade> findByTipologia(Tipologia tipologia);

    List<Atividade> findByTaxasLicenciamento(TaxaLicenciamento taxaLicenciamento);

    List<Atividade> findByParametros(Parametro parametro);

    List<Atividade> findByRequisitoTecnico(RequisitoTecnico requisitoTecnico);

}