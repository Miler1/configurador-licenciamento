package com.configuradorlicenciamento.atividade.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.parametro.models.Parametro;
import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "porte_atividade")
public class PorteAtividade implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_porte_empreendimento", referencedColumnName = "id")
    private PorteEmpreendimento porteEmpreendimento;

    @NotNull(message = "{validacao.notnull}")
    private Float limiteInferiorUm;

    @NotNull(message = "{validacao.notnull}")
    private Float limiteSuperiorUm;

    @NotNull(message = "{validacao.notnull}")
    private Float limiteInferiorDois;

    @NotNull(message = "{validacao.notnull}")
    private Float limiteSuperiorDois;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_parametro_um", referencedColumnName = "id")
    private Parametro parametroUm;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_parametro_dois", referencedColumnName = "id")
    private Parametro parametroDois;

    @NotNull(message = "{validacao.notnull}")
    private Boolean limiteInferiorUmIncluso;

    @NotNull(message = "{validacao.notnull}")
    private Boolean limiteInferiorDoisIncluso;

    @NotNull(message = "{validacao.notnull}")
    private Boolean limiteSuperiorUmIncluso;

    @NotNull(message = "{validacao.notnull}")
    private Boolean limiteSuperiorDoisIncluso;

    @NotNull(message = "{validacao.notnull}")
    private Integer codigo;

    public PorteAtividade(PorteAtividade.PorteAtividadeBuilder builder) {
        this.porteEmpreendimento = builder.porteEmpreendimento;
        this.limiteInferiorUm = builder.limiteInferiorUm;
        this.limiteSuperiorUm = builder.limiteSuperiorUm;
        this.limiteInferiorDois = builder.limiteInferiorDois;
        this.limiteSuperiorDois = builder.limiteSuperiorDois;
        this.parametroUm = builder.parametroUm;
        this.parametroDois = builder.parametroDois;
        this.limiteInferiorUmIncluso = builder.limiteInferiorUmIncluso;
        this.limiteInferiorDoisIncluso = builder.limiteInferiorDoisIncluso;
        this.limiteSuperiorUmIncluso = builder.limiteSuperiorUmIncluso;
        this.limiteSuperiorDoisIncluso = builder.limiteSuperiorDoisIncluso;
        this.codigo = builder.codigo;
    }

    public static class PorteAtividadeBuilder {

        private PorteEmpreendimento porteEmpreendimento;
        private Float limiteInferiorUm;
        private Float limiteSuperiorUm;
        private Float limiteInferiorDois;
        private Float limiteSuperiorDois;
        private Parametro parametroUm;
        private Parametro parametroDois;
        private Boolean limiteInferiorUmIncluso;
        private Boolean limiteInferiorDoisIncluso;
        private Boolean limiteSuperiorUmIncluso;
        private Boolean limiteSuperiorDoisIncluso;
        private Integer codigo;

        public PorteAtividadeBuilder() {}

        public PorteAtividadeBuilder setPorteEmpreendimento(PorteEmpreendimento porteEmpreendimento) {
            this.porteEmpreendimento = porteEmpreendimento;
            return this;
        }

        public PorteAtividadeBuilder setLimiteInferiorUm(Float limiteInferiorUm) {
            this.limiteInferiorUm = limiteInferiorUm;
            return this;
        }

        public PorteAtividadeBuilder setLimiteSuperiorUm(Float limiteSuperiorUm) {
            this.limiteSuperiorUm = limiteSuperiorUm;
            return this;
        }

        public PorteAtividadeBuilder setLimiteInferiorDois(Float limiteInferiorDois) {
            this.limiteInferiorDois = limiteInferiorDois;
            return this;
        }

        public PorteAtividadeBuilder setLimiteSuperiorDois(Float limiteSuperiorDois) {
            this.limiteSuperiorDois = limiteSuperiorDois;
            return this;
        }

        public PorteAtividadeBuilder setParametroUm(Parametro parametroUm) {
            this.parametroUm = parametroUm;
            return this;
        }

        public PorteAtividadeBuilder setParametroDois(Parametro parametroDois) {
            this.parametroDois = parametroDois;
            return this;
        }

        public PorteAtividadeBuilder setLimiteInferiorUmIncluso(Boolean limiteInferiorUmIncluso) {
            this.limiteInferiorUmIncluso = limiteInferiorUmIncluso;
            return this;
        }

        public PorteAtividadeBuilder setLimiteInferiorDoisIncluso(Boolean limiteInferiorDoisIncluso) {
            this.limiteInferiorDoisIncluso = limiteInferiorDoisIncluso;
            return this;
        }

        public PorteAtividadeBuilder setLimiteSuperiorUmIncluso(Boolean limiteSuperiorUmIncluso) {
            this.limiteSuperiorUmIncluso = limiteSuperiorUmIncluso;
            return this;
        }

        public PorteAtividadeBuilder setLimiteSuperiorDoisIncluso(Boolean limiteSuperiorDoisIncluso) {
            this.limiteSuperiorDoisIncluso = limiteSuperiorDoisIncluso;
            return this;
        }

        public PorteAtividadeBuilder setCodigo(Integer codigo) {
            this.codigo = codigo;
            return this;
        }

        public PorteAtividade build(){return new PorteAtividade(this);}

    }

}
