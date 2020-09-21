package com.configuradorlicenciamento.historicoConfigurador.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "acao_configurador")
public class AcaoConfigurador implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String acao;

    public enum Acoes {

        CADASTRAR(1, "CADASTRAR"),
        EDITAR(2, "EDITAR"),
        ATIVAR(3, "ATIVAR"),
        DESATIVAR(4, "DESATIVAR");

        Integer id;
        String acao;

        Acoes(Integer id, String acao) {
            this.id = id;
            this.acao = acao;
        }

        public Integer getId(){
            return this.id;
        }

    }

}