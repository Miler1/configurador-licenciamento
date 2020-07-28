package com.configuradorlicencimento.configuracao.deserializer;

import com.configuradorlicencimento.configuracao.utils.Constants;
import com.configuradorlicencimento.configuracao.utils.DateUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer extends JsonDeserializer<Date> {

	private static SimpleDateFormat DATA_FORMATO_BR_SIMPLES = new SimpleDateFormat(Constants.DATA_FORMATO_BR_SIMPLES);
	private static SimpleDateFormat DATA_FORMATO_BR_COM_HORA = new SimpleDateFormat(Constants.DATA_FORMATO_BR_COM_HORA);
	private static SimpleDateFormat DATA_FORMATO_BR_COMPLETO = new SimpleDateFormat(Constants.DATA_FORMATO_BR_COMPLETO);
	private static SimpleDateFormat DATA_FORMATO_EUA_SIMPLES = new SimpleDateFormat(Constants.DATA_FORMATO_EUA_SIMPLES);
	private static SimpleDateFormat DATA_FORMATO_EUA_COM_HORA = new SimpleDateFormat(Constants.DATA_FORMATO_EUA_COM_HORA);
	private static SimpleDateFormat DATA_FORMATO_EUA_COMPLETO = new SimpleDateFormat(Constants.DATA_FORMATO_EUA_COMPLETO);

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

		JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

		String date = jsonNode.asText();

		if(StringUtils.isNumeric(date) || date.substring(0,1).equals("-")) {

			return new Date(Long.valueOf(date));

		} else if(date.matches("(\\d{4})-(\\d{2})-(\\d{2})T((\\d{2}):(\\d{2}):(\\d{2}))\\.(\\d{3})Z")) {

			return parseDateISO(date);

		} else if(date.matches("(\\d{2}):(\\d{2})")) {

			return parseTimeOnly(date);

		} else if(date.matches("(\\d{4})-(\\d{2})-(\\d{2})")) {

			return parseDateUS(date);

		} else if(date.length() <= 10) {

			return parseDate(date);

		} else {

			return parseDateTimetable(date);
		}

	}

	public static Date parseTimeOnly(String s) {

		Long primeirosMilisegundos = 0l;

		return DateUtil.setHours(new Date(primeirosMilisegundos), Integer.parseInt(s.split(":")[0]), Integer.parseInt(s.split(":")[1]), 0, 0);
	}

	public Date parseDateUS(String s) {

		try {

			return DATA_FORMATO_EUA_SIMPLES.parse(s);

		} catch(ParseException e) {

			e.printStackTrace();

			return null;
		}
	}

	public Date parseDate(String s) {

		try {

			return DATA_FORMATO_BR_SIMPLES.parse(s);

		} catch(ParseException e) {

			e.printStackTrace();

			return null;
		}
	}

	public Date parseDateTimetable(String s) {

		try {

			return DATA_FORMATO_EUA_COM_HORA.parse(s);

		} catch(ParseException e) {

			e.printStackTrace();

			return null;
		}
	}

	public Date parseDateISO(String s) {

		try {

			return DATA_FORMATO_EUA_COMPLETO.parse(s);

		} catch(ParseException e) {

			e.printStackTrace();

			return null;
		}
	}

}
