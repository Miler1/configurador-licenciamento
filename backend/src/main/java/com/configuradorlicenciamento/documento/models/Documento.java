package com.configuradorlicenciamento.documento.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.documento.dtos.DocumentoCsv;
import com.configuradorlicenciamento.documento.dtos.DocumentoDTO;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "tipo_documento")
public class Documento implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull(message = "{validacao.notnull}")
    private String nome;

    @NotNull(message = "{validacao.notnull}")
    private String caminhoPasta;

    @NotNull(message = "{validacao.notnull}")
    private String prefixoNomeArquivo;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public Documento(Documento.DocumentoBuilder builder) {

        this.nome = builder.nome;
        this.caminhoPasta = builder.caminhoPasta;
        this.prefixoNomeArquivo = builder.prefixoNomeArquivo;
        this.ativo = builder.ativo;
        this.dataCadastro = builder.dataCadastro;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;

    }

    public static class DocumentoBuilder {

        private final String nome;
        private final String caminhoPasta;
        private final String prefixoNomeArquivo;
        private Boolean ativo;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public DocumentoBuilder(DocumentoDTO documentoDTO) {

            this.nome = documentoDTO.getNome();

            //Ele pega o prefixo nome arquivo, pois no banco os dois sempre são iguais
            this.caminhoPasta = documentoDTO.getPrefixoNomeArquivo();
            this.prefixoNomeArquivo = documentoDTO.getPrefixoNomeArquivo();
            this.ativo = documentoDTO.getAtivo();

        }

        public Documento.DocumentoBuilder setDataCadastro(Date dataCadastro) {

            this.dataCadastro = dataCadastro;
            return this;

        }

        public Documento.DocumentoBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {

            this.usuarioLicenciamento = usuarioLicencimento;
            return this;

        }

        public Documento build() { return new Documento(this); }

    }

    public DocumentoCsv preparaParaCsv(){
        return new DocumentoCsv(this);
    }

}
