package com.configuradorlicenciamento.atividade.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.dtos.AtividadeDispensavelDTO;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "atividade")
public class Atividade implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String nome;

    @NotNull(message = "{validacao.notnull}")
    private String codigo;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_tipologia", referencedColumnName = "id")
    private Tipologia tipologia;

    @NotNull(message = "{validacao.notnull}")
    private Boolean geoLinha;

    @NotNull(message = "{validacao.notnull}")
    private Boolean geoPonto;

    @NotNull(message = "{validacao.notnull}")
    private Boolean geoPoligono;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_potencial_poluidor", referencedColumnName = "id")
    private PotencialPoluidor potencialPoluidor;

    @NotNull(message = "{validacao.notnull}")
    private String siglaSetor;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Boolean dentroEmpreendimento;

    @NotNull(message = "{validacao.notnull}")
    private Boolean dentroMunicipio;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_grupo_documento", referencedColumnName = "id")
    private RequisitoTecnico requisitoTecnico;

    @NotNull(message = "{validacao.notnull}")
    private Boolean v1;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(schema = GlobalReferences.ESQUEMA, name = "rel_atividade_tipo_atividade", joinColumns =
            {@JoinColumn(name = "id_atividade")}, inverseJoinColumns =
            {@JoinColumn(name = "id_tipo_atividade")})
    private List<TipoAtividade> tiposAtividades;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(schema = GlobalReferences.ESQUEMA, name = "rel_atividade_tipo_licenca", joinColumns =
            {@JoinColumn(name = "id_atividade")}, inverseJoinColumns =
            {@JoinColumn(name = "id_tipo_licenca")})
    private List<Licenca> tiposLicencas;

    @OneToMany(mappedBy="atividade")
    @JsonManagedReference
    private List<RelAtividadeParametroAtividade> parametros;

    public Atividade(AtividadeBuilder atividadeBuilder) {
        this.nome = atividadeBuilder.nome;
        this.codigo = atividadeBuilder.codigo;
        this.tipologia = atividadeBuilder.tipologia;
        this.geoLinha = atividadeBuilder.geoLinha;
        this.geoPonto = atividadeBuilder.geoPonto;
        this.geoPoligono = atividadeBuilder.geoPoligono;
        this.potencialPoluidor = atividadeBuilder.potencialPoluidor;
        this.siglaSetor = atividadeBuilder.siglaSetor;
        this.ativo = atividadeBuilder.ativo;
        this.dentroEmpreendimento = atividadeBuilder.dentroEmpreendimento;
        this.dentroMunicipio = atividadeBuilder.dentroMunicipio;
        this.requisitoTecnico = atividadeBuilder.requisitoTecnico;
        this.v1 = atividadeBuilder.v1;
        this.tiposAtividades = atividadeBuilder.tiposAtividades;
    }

    public static class AtividadeBuilder {

        private String nome;
        private String codigo;
        private Tipologia tipologia;
        private Boolean geoLinha;
        private Boolean geoPonto;
        private Boolean geoPoligono;
        private PotencialPoluidor potencialPoluidor;
        private String siglaSetor;
        private Boolean ativo;
        private Boolean dentroEmpreendimento;
        private Boolean dentroMunicipio;
        private RequisitoTecnico requisitoTecnico;
        private Boolean v1;
        private List<TipoAtividade> tiposAtividades;

        public AtividadeBuilder() {};

        public AtividadeBuilder(AtividadeDispensavelDTO.RelacaoCnaeTipologia atividadeDispensavelDTO) {
            this.dentroMunicipio = !atividadeDispensavelDTO.getForaMunicipio();
        }

        public AtividadeBuilder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public AtividadeBuilder setCodigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public AtividadeBuilder setTipologia(Tipologia tipologia) {
            this.tipologia = tipologia;
            return this;
        }

        public AtividadeBuilder setGeoLinha(Boolean geoLinha) {
            this.geoLinha = geoLinha;
            return this;
        }

        public AtividadeBuilder setGeoPonto(Boolean geoPonto) {
            this.geoPonto = geoPonto;
            return this;
        }

        public AtividadeBuilder setGeoPoligono(Boolean geoPoligono) {
            this.geoPoligono = geoPoligono;
            return this;
        }

        public AtividadeBuilder setPotencialPoluidor(PotencialPoluidor potencialPoluidor) {
            this.potencialPoluidor = potencialPoluidor;
            return this;
        }

        public AtividadeBuilder setsiglaSetor(String siglaSetor) {
            this.siglaSetor = siglaSetor;
            return this;
        }

        public AtividadeBuilder setRequisitoTecnico(RequisitoTecnico requisitoTecnico) {
            this.requisitoTecnico = requisitoTecnico;
            return this;
        }

        public AtividadeBuilder setAtivo(Boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        public AtividadeBuilder setDentroEmpreendimento(Boolean dentroEmpreendimento) {
            this.dentroEmpreendimento = dentroEmpreendimento;
            return this;
        }

        public AtividadeBuilder setV1(Boolean v1) {
            this.v1 = v1;
            return this;
        }

        public AtividadeBuilder setTiposAtividades(List<TipoAtividade> tiposAtividades) {
            this.tiposAtividades = tiposAtividades;
            return this;
        }

        public Atividade build() {
            return new Atividade(this);
        }

    }

}