package com.configuradorlicencimento.configuracao.components;

import com.configuradorlicencimento.configuracao.deserializer.DateDeserializer;
import com.configuradorlicencimento.configuracao.deserializer.LocalDateDeserializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class CustomizacaoObjetoMapper extends ObjectMapper {

	private SimpleModule simpleModule;
	private ObjectMapper mapper;

	public CustomizacaoObjetoMapper() {

		this.simpleModule = new SimpleModule();
		this.mapper = new ObjectMapper();

		setDeserializers();
		setSerializers();

		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.registerModule(simpleModule);
		this.registerModule(new Jdk8Module());
	}

	private void setDeserializers() {

		this.simpleModule.addDeserializer(Date.class, new DateDeserializer());
		this.simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
		this.simpleModule.addDeserializer(String.class, new StringDeserializer());
	}

	private void setSerializers() {

		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	}

}