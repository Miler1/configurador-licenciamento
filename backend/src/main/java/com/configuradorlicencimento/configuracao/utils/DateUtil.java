package com.configuradorlicencimento.configuracao.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Date setHours(Date date, int horas, int minutos, int segundos, int milisegundos) {

		Date data = DateUtils.setHours(date, horas);
		data = DateUtils.setMinutes(data, minutos);
		data = DateUtils.setSeconds(data, segundos);
		data = DateUtils.setMilliseconds(data, milisegundos);

		return data;
	}

	public static String formataBrCompleto(Date data){
		return new SimpleDateFormat(Constants.DATA_FORMATO_BR_COMPLETO).format(data);
	}

	public static String formataBrHoraMinuto(Date data){
		return new SimpleDateFormat(Constants.DATA_FORMATO_BR_COM_HORA).format(data);
	}

	public static String formataBrSimples(Date data){
		return new SimpleDateFormat(Constants.DATA_FORMATO_BR_SIMPLES).format(data);
	}

}
