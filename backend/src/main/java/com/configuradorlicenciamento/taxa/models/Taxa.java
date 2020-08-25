package com.configuradorlicenciamento.taxa.models;


import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "taxa_licenciamento")
public class Taxa implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name="id_porte_empreendimento")
    private PorteEmpreendimento porteEmpreendimento;

    @ManyToOne
    @JoinColumn(name="id_potencial_poluidor")
    public PotencialPoluidor potencialPoluidor;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name="id_tipo_licenca", referencedColumnName="id")
    private Licenca licenca;

    @Column(name="codigo")
    public Integer codigo;

    public String valor;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;
}
