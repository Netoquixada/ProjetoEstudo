package br.com.controlpro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {

	public static Date formatarData(String dateString) {
		Date data = new Date();
		try {
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

			data = formatador.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static String formatarData(Date date) {
		String data = null;
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		data = formatador.format(date);
		return data;
	}

	public static Date setandoInicioDia(Date date) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date()); // colocando o objeto Date no Calendar
		calendar.set(Calendar.HOUR_OF_DAY, 0); // zerando as horas, minuots e segundos..
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		return calendar.getTime();
	}
	public static Date setandoFinalDia(Date date) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(new Date()); // colocando o objeto Date no Calendar
		calendar.set(Calendar.HOUR_OF_DAY, 23); // zerando as horas, minuots e segundos..
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		
		return calendar.getTime();
	}
	

}
