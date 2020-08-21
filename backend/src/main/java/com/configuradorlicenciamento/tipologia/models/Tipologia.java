package com.configuradorlicenciamento.tipologia.models;

import com.configuradorlicenciamento.configuracao.utils.GlobalReferences;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaCsv;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
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
@Table(schema = GlobalReferences.ESQUEMA, name = "tipologia")
public class Tipologia implements Serializable {

    @Id
    @SuppressWarnings("unused")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String nome;

    @NotNull(message = "{validacao.notnull}")
    private String codigo;

    @NotNull(message = "{validacao.notnull}")
    private Boolean ativo;

    @NotNull(message = "{validacao.notnull}")
    private Date dataCadastro;

    @NotNull(message = "{validacao.notnull}")
    @ManyToOne
    @JoinColumn(name = "id_usuario_licenciamento", referencedColumnName = "id")
    private UsuarioLicenciamento usuarioLicenciamento;

    public Tipologia(TipologiaBuilder builder) {
        this.nome = builder.nome;
        this.codigo = builder.codigo;
        this.ativo = builder.ativo;
        this.dataCadastro = builder.dataCadastro;
        this.usuarioLicenciamento = builder.usuarioLicenciamento;
    }

    public static class TipologiaBuilder {

        private final String nome;
        private final String codigo;
        private Boolean ativo;
        private Date dataCadastro;
        private UsuarioLicenciamento usuarioLicenciamento;

        public TipologiaBuilder(TipologiaDTO tipologiaDTO) {
            this.nome = StringUtil.removerEspacosRepetidos(tipologiaDTO.getNome());
            this.ativo = tipologiaDTO.getAtivo();

//            if(tipologiaDTO.getCodigo().isBlank()){
//                this.codigo = gerarCodigo(this.nome);
//            } else {
//                this.codigo = gerarCodigo(tipologiaDTO.getCodigo());
//            }

            this.codigo = gerarCodigo(this.nome);
        }

        public TipologiaBuilder setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public TipologiaBuilder setUsuarioLicencimento(UsuarioLicenciamento usuarioLicencimento) {
            this.usuarioLicenciamento = usuarioLicencimento;
            return this;
        }

        public Tipologia build() { return new Tipologia(this); }

        public static String gerarCodigo(String string) {

            String codigo = "";

            codigo = StringUtil.removeAccents(string).toLowerCase();
            codigo = StringUtil.removerEspacosRepetidos(codigo);
            //codigo = codigo.replace("_", " ").toUpperCase();
            codigo = StringUtil.removeCaracteresEspeciais(codigo);

            for(String preposicao : StringUtil.preposicoes()) {
                codigo = codigo.replace(preposicao, " ");
            }

            codigo = codigo.replace(" ", "_").toUpperCase();

            return codigo;
        }
    }

    public TipologiaCsv preparaParaCsv() {

        return new TipologiaCsv(this);
    }
}
