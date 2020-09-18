package com.configuradorlicenciamento.taxaLicenciamento.models;


import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.porteEmpreendimento.models.PorteEmpreendimento;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.taxaLicenciamento.dtos.TaxaLicenciamentoDTO;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "taxa_licenciamento")
public class TaxaLicenciamento implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name="id_porte_empreendimento")
    private PorteEmpreendimento porteEmpreendimento;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name="id_potencial_poluidor")
    public PotencialPoluidor potencialPoluidor;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name="id_tipo_licenca", referencedColumnName="id")
    private Licenca licenca;

    @NotNull(message = "{validacao.notnull}")
    @OneToOne
    @JoinColumn(name="codigo", referencedColumnName = "id")
    public CodigoTaxaLicenciamento codigo;

    private String valor;

    public TaxaLicenciamento(TaxaLicenciamento.TaxaLicenciamentoBuilder builder) {
        this.codigo = builder.codigo;
        this.licenca = builder.licenca;
        this.porteEmpreendimento = builder.porteEmpreendimento;
        this.potencialPoluidor = builder.potencialPoluidor;
        this.valor = builder.valor;
    }

    public static class TaxaLicenciamentoBuilder {

        private CodigoTaxaLicenciamento codigo;
        private Licenca licenca;
        private PorteEmpreendimento porteEmpreendimento;
        private PotencialPoluidor potencialPoluidor;
        private String valor;

        public TaxaLicenciamentoBuilder(TaxaLicenciamentoDTO taxaLicenciamentoDTO) {
            this.valor = taxaLicenciamentoDTO.getValor();
        }

        public TaxaLicenciamento.TaxaLicenciamentoBuilder setCodigoTaxaLicenciamento(CodigoTaxaLicenciamento codigo) {
            this.codigo = codigo;
            return this;
        }

        public TaxaLicenciamento.TaxaLicenciamentoBuilder setLicenca(Licenca licenca) {
            this.licenca = licenca;
            return this;
        }

        public TaxaLicenciamento.TaxaLicenciamentoBuilder setPorteEmpreendimento(PorteEmpreendimento porteEmpreendimento) {
            this.porteEmpreendimento = porteEmpreendimento;
            return this;
        }

        public TaxaLicenciamento.TaxaLicenciamentoBuilder setPotencialPoluidor(PotencialPoluidor potencialPoluidor) {
            this.potencialPoluidor = potencialPoluidor;
            return this;
        }

        public TaxaLicenciamento build() { return new TaxaLicenciamento(this); }
    }
}
