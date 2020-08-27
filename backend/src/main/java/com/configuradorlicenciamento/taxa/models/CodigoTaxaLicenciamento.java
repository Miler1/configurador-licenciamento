package com.configuradorlicenciamento.taxa.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = GlobalReferences.ESQUEMA, name = "codigos_taxa_licenciamento")
public class CodigoTaxaLicenciamento implements Serializable {

    @Id
    private Integer id;

    @Column(name="codigo")
    private String codigo;

    @Column(name="descricao")
    private String descricao;

}
