package com.configuradorlicenciamento.configuracao.deserializer;

import com.configuradorlicenciamento.configuracao.utils.Constants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

	private static DateTimeFormatter DATA_FORMATO_BR_SIMPLES = DateTimeFormatter.ofPattern(Constants.DATA_FORMATO_BR_SIMPLES);
	private static DateTimeFormatter DATA_FORMATO_BR_COM_HORA = DateTimeFormatter.ofPattern(Constants.DATA_FORMATO_BR_COM_HORA);
	private static DateTimeFormatter DATA_FORMATO_BR_COMPLETO = DateTimeFormatter.ofPattern(Constants.DATA_FORMATO_BR_COMPLETO);
	private static DateTimeFormatter DATA_FORMATO_EUA_SIMPLES = DateTimeFormatter.ofPattern(Constants.DATA_FORMATO_EUA_SIMPLES);
	private static DateTimeFormatter DATA_FORMATO_EUA_COM_HORA = DateTimeFormatter.ofPattern(Constants.DATA_FORMATO_EUA_COM_HORA);
	private static DateTimeFormatter DATA_FORMATO_EUA_COMPLETO = DateTimeFormatter.ofPattern(Constants.DATA_FORMATO_EUA_COMPLETO);

	@Override
	public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

		JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

		String date = jsonNode.asText();

		if(StringUtils.isNumeric(date) || date.substring(0,1).equals("-")) {

			return new java.sql.Date(Long.valueOf(date)).toLocalDate();

		} else if(date.matches("(\\d{4})-(\\d{2})-(\\d{2})T((\\d{2}):(\\d{2}):(\\d{2}))\\.(\\d{3})Z")) {

			return LocalDate.parse(date, DATA_FORMATO_EUA_COMPLETO);

		} else if(date.matches("(\\d{2}):(\\d{2})")) {

			return LocalDate.parse(date, DATA_FORMATO_BR_COM_HORA);

		} else if(date.matches("(\\d{4})-(\\d{2})-(\\d{2})")) {

			return LocalDate.parse(date, DATA_FORMATO_EUA_SIMPLES);

		} else if(date.matches("(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})")) {

			return LocalDate.parse(date, DATA_FORMATO_EUA_COM_HORA);

		} else if(date.length() <= 10) {

			return LocalDate.parse(date, DATA_FORMATO_BR_SIMPLES);

		} else {

			return LocalDate.parse(date, DATA_FORMATO_EUA_COMPLETO);
		}

	}

}
