package com.configuradorlicenciamento.potencialPoluidor.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = GlobalReferences.ESQUEMA, name = "potencial_poluidor")
public class PotencialPoluidor implements Serializable {

    @Id
    private Integer id;

    private String nome;

    private String codigo;
}
