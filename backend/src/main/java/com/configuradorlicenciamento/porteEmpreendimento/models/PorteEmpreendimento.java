package com.configuradorlicenciamento.porteEmpreendimento.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = GlobalReferences.ESQUEMA, name = "porte_empreendimento")
public class PorteEmpreendimento implements Serializable{

    @Id
    private Integer id;

    private String nome;

    private String codigo;

}
