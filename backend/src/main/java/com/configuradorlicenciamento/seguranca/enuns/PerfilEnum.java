package com.configuradorlicenciamento.seguranca.enuns;

import lombok.Getter;

public enum PerfilEnum {

	ADMINISTRADOR(1, "ADMINISTRADOR");

	private Integer id;

	@Getter
	private String codigo;

	PerfilEnum(Integer id, String codigo) {

		this.id = id;
		this.codigo = codigo;

	}

}
