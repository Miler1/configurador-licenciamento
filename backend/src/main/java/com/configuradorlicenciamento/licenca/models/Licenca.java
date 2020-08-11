package com.configuradorlicenciamento.licenca.models;


import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.licenca.dtos.LicencaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Entity
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "tipo_licenca")
public class Licenca implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String sigla;

    @NotNull(message = "{validacao.notnull}")
    private String nome;

    @NotNull(message = "{validacao.notnull}")
    private Integer validadeEmAnos;

    @NotNull(message = "{validacao.notnull}")
    private Boolean podeRenovar;

    @NotNull(message = "{validacao.notnull}")
    private Boolean naoExpira;

    @NotNull(message = "{validacao.notnull}")
    private String descricao;

    public Licenca(Licenca.LicencaBuilder builder) {

        this.sigla = builder.tipo;
        this.nome = builder.nomenclatura;
        this.validadeEmAnos = builder.validade;
        this.podeRenovar = builder.podeRenovar;
        this.naoExpira = builder.naoExpira;
        this.descricao = builder.descricao;
    }

    public static class LicencaBuilder {

        private String tipo;
        private String nomenclatura;
        private Integer validade;
        private Boolean podeRenovar;
        private Boolean naoExpira;
        private String descricao;

        public LicencaBuilder(LicencaDTO licencaDTO) {

            this.tipo = licencaDTO.getTipo();
            this.nomenclatura = licencaDTO.getNomenclatura();
            this.validade = licencaDTO.getValidade();
            this.podeRenovar = licencaDTO.getPodeRenovar();
            this.naoExpira = licencaDTO.getNaoExpira();
            this.descricao = licencaDTO.getDescricao();

        }
    }
}
