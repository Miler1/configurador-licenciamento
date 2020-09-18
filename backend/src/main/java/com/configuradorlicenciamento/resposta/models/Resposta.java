package com.configuradorlicenciamento.resposta.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.resposta.dtos.RespostaDTO;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(schema = GlobalReferences.ESQUEMA, name = "resposta")
public class Resposta implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String texto;

    @NotNull(message = "{validacao.notnull}")
    private Boolean permiteLicenciamento;

    private String tipoValidacao;

    private String codigo;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public Resposta(RespostaBuilder builder) {

        this.texto = builder.texto;
        this.permiteLicenciamento = builder.permiteLicenciamento;
        this.tipoValidacao = null;
        this.codigo = "";
        this.dataCadastro = builder.dataCadastro;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
    }

    public static class RespostaBuilder {

        private final String texto;
        private final Boolean permiteLicenciamento;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public RespostaBuilder(RespostaDTO respostaDTO) {
            this.texto = respostaDTO.getTexto();
            this.permiteLicenciamento = respostaDTO.getPermiteLicenciamento();
        }

        public RespostaBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public RespostaBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public Resposta build() { return new Resposta(this); }

    }
}
