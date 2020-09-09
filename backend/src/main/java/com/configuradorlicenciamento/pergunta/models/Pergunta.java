package com.configuradorlicenciamento.pergunta.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.repositories.PerguntaRepository;
import com.configuradorlicenciamento.resposta.dtos.RespostaDTO;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.configuradorlicenciamento.resposta.models.Resposta;
import org.springframework.beans.factory.annotation.Autowired;

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
@Table(schema = GlobalReferences.ESQUEMA, name = "pergunta")
public class Pergunta implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String texto;

    @NotNull(message = "{validacao.notnull}")
    private String codigo;

    private Integer ordem;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    private Integer tipoLocalizacaoEmpreendimento;

    @NotNull(message = "{validacao.notnull}")
    private String tipoPergunta;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    @NotNull(message = "{validacao.notnull}")
    @OneToMany
    @JoinColumn(name = "id_pergunta")
    private List<Resposta> respostas;

    public Pergunta(PerguntaBuilder builder) {

        this.texto = builder.texto;
        this.codigo = builder.codigo;
        this.ativo = builder.ativo;
        this.tipoPergunta = "SIM_NAO";
        this.ordem = 1;
        this.dataCadastro = builder.dataCadastro;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
    }

    public static class PerguntaBuilder {

        private final String texto;
        private final String codigo;
        private final Boolean ativo;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public PerguntaBuilder(PerguntaDTO perguntaDTO) {
            this.texto = perguntaDTO.getTexto();
            this.codigo = perguntaDTO.getCodigo();
            this.ativo = perguntaDTO.getAtivo();
        }

        public PerguntaBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public PerguntaBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public Pergunta build() { return new Pergunta(this); }

    }
}