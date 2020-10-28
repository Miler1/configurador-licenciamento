package com.configuradorlicenciamento.atividade.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "tipo_atividade")
public class TipoAtividade implements Serializable {

    @Id
    private Integer id;

    private String nome;

    private String codigo;

}
