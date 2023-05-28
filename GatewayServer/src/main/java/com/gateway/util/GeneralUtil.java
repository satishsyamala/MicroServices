package com.gateway.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GeneralUtil {

	public static Date localDateTimeToDate(LocalDateTime ldt) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			return sd.parse(format.format(ldt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}

}
