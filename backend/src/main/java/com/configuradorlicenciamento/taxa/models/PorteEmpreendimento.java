package com.configuradorlicenciamento.taxa.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "porte_empreendimento")
public class PorteEmpreendimento implements Serializable {

    private String nome;

    private String codigo;

}
