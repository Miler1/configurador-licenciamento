package com.configuradorlicenciamento.taxa.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "potencial_poluidor")
public class PotencialPoluidor {

    private String nome;

    private String codigo;
}
