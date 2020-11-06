package com.configuradorlicenciamento.documento.repositories;

import com.configuradorlicenciamento.documento.models.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer>, JpaSpecificationExecutor<Documento> {

    Boolean existsByNome(String nome);

    Documento findByNome(String nome);

    List<Documento> findByAtivo(boolean ativo);

}
