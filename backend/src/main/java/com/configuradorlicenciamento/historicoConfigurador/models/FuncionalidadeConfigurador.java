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
@Table(schema = GlobalReferences.ESQUEMA, name = "funcionalidade_configurador")
public class FuncionalidadeConfigurador implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String tipo;

    public enum Funcionalidades {

        CNAE(1, "CNAE"),
        LICENCA(2, "LICENÇA"),
        TIPOLOGIA(3, "TIPOLOCIA"),
        PARAMETRO(4, "PARÂMETRO"),
        TAXA_ADMINISTRATIVA(5, "TAXA_ADMINISTRATIVA"),
        TAXA_LICENCIAMENTO(6, "TAXA_LICENCIAMENTO"),
        REQUISITO_ADMINISTRATIVO(7, "REQUISITO_ADMINISTRATIVO"),
        REQUISITO_TECNICO(8, "REQUISITO_TECNICO"),
        ATIVIDADES_LICENCIAVEIS(9, "ATIVIDADES_LICENCIAVEIS"),
        PERGUNTAS(10, "PERGUNTAS"),
        CNAES_DISPENSAVEIS(11, "CNAES_DISPENSAVEIS");

        Integer id;
        String tipo;

        Funcionalidades(Integer id, String tipo) {
            this.id = id;
            this.tipo = tipo;
        }

        public String getTipo() {
            return this.tipo;
        }

    }

}
