package pt.isel.pdm.birthdayreminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHandler {

	private final SimpleDateFormat _dateParser;
	private final SimpleDateFormat _datePrinter;
	private final Calendar _now;

	public DateHandler(){
		_dateParser = new SimpleDateFormat("--MM-dd");
		_datePrinter = new SimpleDateFormat("cccc, LLLL d");
		_now = Calendar.getInstance();		
	}
	
	public Date parse(String contactsDate) throws ParseException{
		Date bday = _dateParser.parse(contactsDate);
		Calendar cday = Calendar.getInstance();
		int year = cday.get(Calendar.YEAR);
		cday.setTime(bday);
		cday.set(Calendar.YEAR, year);
		bday = cday.getTime();
		return bday;
	}
	
	public boolean shouldShow(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return _now.get(Calendar.MONTH) == c.get(Calendar.MONTH) 
				&& _now.get(Calendar.DAY_OF_MONTH) <= c.get(Calendar.DAY_OF_MONTH);
	}
	
	public String format(Date date) {
		return _datePrinter.format(date);
	}	
}
