package com.configuradorlicenciamento.atividade.models;

import com.configuradorlicenciamento.atividade.dtos.AtividadeDispensavelCsv;
import com.configuradorlicenciamento.atividade.dtos.AtividadeLicenciavelCsv;
import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import com.configuradorlicenciamento.taxaLicenciamento.models.TaxaLicenciamento;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

    @ManyToOne
    @JoinColumn(name = "id_tipologia", referencedColumnName = "id")
    private Tipologia tipologia;

    @NotNull(message = "{validacao.notnull}")
    private Boolean geoLinha;

    @NotNull(message = "{validacao.notnull}")
    private Boolean geoPonto;

    @NotNull(message = "{validacao.notnull}")
    private Boolean geoPoligono;

    @ManyToOne
    @JoinColumn(name = "id_potencial_poluidor", referencedColumnName = "id")
    private PotencialPoluidor potencialPoluidor;

    private String siglaSetor;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Boolean itemAntigo;

    private Boolean dentroEmpreendimento;

    @NotNull(message = "{validacao.notnull}")
    private Boolean dentroMunicipio;

    @ManyToOne
    @JoinColumn(name = "id_grupo_documento", referencedColumnName = "id")
    private RequisitoTecnico requisitoTecnico;

    @NotNull(message = "{validacao.notnull}")
    private Boolean v1;

    @NotNull(message = "{validacao.notnull}")
    private Boolean rascunho;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(schema = GlobalReferences.ESQUEMA, name = "rel_atividade_taxa_licenciamento", joinColumns =
            {@JoinColumn(name = "id_atividade")}, inverseJoinColumns =
            {@JoinColumn(name = "id_taxa_licenciamento")})
    private List<TaxaLicenciamento> taxasLicenciamento;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(schema = GlobalReferences.ESQUEMA, name = "rel_atividade_porte_atividade", joinColumns =
            {@JoinColumn(name = "id_atividade")}, inverseJoinColumns =
            {@JoinColumn(name = "id_porte_atividade")})
    private List<PorteAtividade> portesAtividade;

    @OneToMany(mappedBy = "atividade")
    @JsonManagedReference
    private List<RelAtividadeParametroAtividade> parametros;

    @OneToMany(mappedBy="atividade")
    @JsonManagedReference
    private List<TipoCaracterizacaoAtividade> atividadesCnae;

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
        this.itemAntigo = atividadeBuilder.itemAntigo;
        this.dentroEmpreendimento = atividadeBuilder.dentroEmpreendimento;
        this.dentroMunicipio = atividadeBuilder.dentroMunicipio;
        this.requisitoTecnico = atividadeBuilder.requisitoTecnico;
        this.v1 = atividadeBuilder.v1;
        this.rascunho = atividadeBuilder.rascunho;
        this.tiposAtividades = atividadeBuilder.tiposAtividades;
        this.portesAtividade = atividadeBuilder.portesAtividade;
        this.tiposLicencas = atividadeBuilder.tiposLicencas;
        this.taxasLicenciamento = atividadeBuilder.taxasLicenciamento;

    }

    public AtividadeLicenciavelCsv preparaAtividadeLicenciavelParaCsv(Date dataCadastro, UsuarioLicenciamento usuarioLicenciamento) {
        return new AtividadeLicenciavelCsv(this, dataCadastro, usuarioLicenciamento);
    }

    public AtividadeDispensavelCsv preparaAtividadeDispensavelParaCsv(Date dataCadastro, UsuarioLicenciamento usuarioLicenciamento) {
        return new AtividadeDispensavelCsv(this, dataCadastro, usuarioLicenciamento);
    }

    public CodigoTaxaLicenciamento recuperaCodigoTaxaLicenciamentobyTaxas() {
        return this.taxasLicenciamento.get(0).getCodigo();
    }

    public List<String> recuperaCodigosTiposAtividade() {

        List<String> codigosTiposAtividade = new ArrayList<>();

        this.tiposAtividades.forEach(tipoAtividade -> {
            codigosTiposAtividade.add(tipoAtividade.getCodigo());
        });

        return codigosTiposAtividade;

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
        private Boolean itemAntigo;
        private Boolean dentroEmpreendimento;
        private final Boolean dentroMunicipio;
        private RequisitoTecnico requisitoTecnico;
        private Boolean v1;
        private Boolean rascunho;
        private List<TipoAtividade> tiposAtividades;
        private List<Licenca> tiposLicencas;
        private List<TaxaLicenciamento> taxasLicenciamento;
        private List<PorteAtividade> portesAtividade;

        public AtividadeBuilder(Boolean dentroMunicipio) {
            this.dentroMunicipio = dentroMunicipio;
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

        public AtividadeBuilder setSiglaSetor(String siglaSetor) {
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

        public AtividadeBuilder setItemAntigo(Boolean itemAntigo) {
            this.itemAntigo = itemAntigo;
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

        public AtividadeBuilder setRascunho(Boolean rascunho) {
            this.rascunho = rascunho;
            return this;
        }

        public AtividadeBuilder setTiposAtividades(List<TipoAtividade> tiposAtividades) {
            this.tiposAtividades = tiposAtividades;
            return this;
        }

        public AtividadeBuilder setTiposLicencas(List<Licenca> tiposLicencas) {
            this.tiposLicencas = tiposLicencas;
            return this;
        }

        public AtividadeBuilder setTaxasLicenciamento(List<TaxaLicenciamento> taxasLicenciamento) {
            this.taxasLicenciamento = taxasLicenciamento;
            return this;
        }

        public AtividadeBuilder setPortesAtividade(List<PorteAtividade> portesAtividade) {
            this.portesAtividade = portesAtividade;
            return this;
        }

        public Atividade build() {
            return new Atividade(this);
        }

    }

}