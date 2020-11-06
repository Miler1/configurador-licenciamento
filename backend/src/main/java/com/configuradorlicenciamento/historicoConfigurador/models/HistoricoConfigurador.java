package com.configuradorlicenciamento.historicoConfigurador.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "historico_configurador")
public class HistoricoConfigurador implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull(message = "{validacao.notnull}")
    public Integer idItem;

    Integer idItemAntigo;

    @NotNull(message = "{validacao.notnull}")
    @OneToOne
    @JoinColumn(name = "id_funcionalidade_configurador", referencedColumnName = "id")
    public FuncionalidadeConfigurador funcionalidade;

    @NotNull(message = "{validacao.notnull}")
    @OneToOne
    @JoinColumn(name = "id_acao_configurador", referencedColumnName = "id")
    public AcaoConfigurador acao;

    String justificativa;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @OneToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public HistoricoConfigurador(Integer idItem,
                                 FuncionalidadeConfigurador funcionalidade,
                                 AcaoConfigurador acao,
                                 Date dataCadastro,
                                 UsuarioLicenciamento usuarioLicenciamento) {

        this.idItem = idItem;
        this.funcionalidade = funcionalidade;
        this.acao = acao;
        this.dataCadastro = dataCadastro;
        this.usuarioLicenciamento = usuarioLicenciamento;

    }

    public HistoricoConfigurador(Integer idItemAtual,
                                 Integer idItemAntigo,
                                 FuncionalidadeConfigurador funcionalidade,
                                 AcaoConfigurador acao,
                                 String justificativa,
                                 Date dataCadastro,
                                 UsuarioLicenciamento usuarioLicenciamento) {

        this.idItem = idItemAtual;
        this.idItemAntigo = idItemAntigo;
        this.funcionalidade = funcionalidade;
        this.acao = acao;
        this.justificativa = justificativa;
        this.dataCadastro = dataCadastro;
        this.usuarioLicenciamento = usuarioLicenciamento;

    }

}