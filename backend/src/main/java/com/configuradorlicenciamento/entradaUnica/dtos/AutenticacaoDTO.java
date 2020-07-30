package com.configuradorlicenciamento.entradaUnica.dtos;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class AutenticacaoDTO {

	@NotNull(message = "{validacao.notnull}")
	@NotBlank(message = "{validacao.notBlank}")
	private String login;

	@NotNull(message = "{validacao.notnull}")
	@NotBlank(message = "{validacao.notBlank}")
	private String password;

}
