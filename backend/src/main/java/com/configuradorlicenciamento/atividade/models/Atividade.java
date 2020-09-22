package com.configuradorlicenciamento.atividade.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.potencialPoluidor.models.PotencialPoluidor;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
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

    private Boolean licenciamentoMunicipal;

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

}
