package resposta.dtos;

import com.configuradorlicenciamento.pergunta.dtos.PerguntaDTO;
import com.configuradorlicenciamento.pergunta.models.Pergunta;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaDTO implements Serializable {

    private Integer id;

    @NotNull(message = "{validacao.notnull}")
    private String texto;

    @NotNull(message = "{validacao.notnull}")
    private Boolean permiteLicenciamento;

    private PerguntaDTO pergunta;

    private String tipoValidacao;

    private String codigo;

    private Boolean ativo;

    private Date dataCadastro;

    private UsuarioLicenciamento usuarioLicenciamento;

}
