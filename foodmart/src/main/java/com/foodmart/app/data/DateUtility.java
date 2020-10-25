package com.foodmart.app.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.stereotype.Component;



@Component
public class DateUtility {

	
	
/*
 *   Reformate Java.Util.Date format saved back to local TZ (AEDT)
 *   For internationalisation; This can be improved to use the TZ from the stored vers of orig date string; 
 * 
 * 	
 */
public String getLocalDateConversion(Date d) {
	
	
  SimpleDateFormat df = new SimpleDateFormat();
  df.setTimeZone(TimeZone.getTimeZone("AEDT"));
	
	return df.format(d);
}
	
/*
 *  Sat Oct 17 2020 18:05:00 GMT 1100 (Australian Eastern Daylight Time)
 *  strip TZ abreviation (Australian Eastern Daylight Time)
 * 	
 */
public String getFormatedDTStr(String date) {
		
    	String pc[]= this.getDatePieces(date,"");	  
    	String formatteddate= ""; 
    	formatteddate= pc[0]+" "+pc[1]+" "+pc[2]+" "+pc[3]+" "+pc[4]+":"+pc[5]+":"+pc[6]+" "+pc[7]+" "+pc[8];
    	return formatteddate;
}	
	



/*  
 *  Date String format;
 *  Sat Oct 17 2020 18:05:00 GMT 1100 (Australian Eastern Daylight Time)
 *  strip TZ offset, TZ abreviation
 */
public Date strDatetoDate(String startdate,String date_format /* "EE MMM dd yyyy HH:mm:ss zz" */){	
  Date senddate=null;
   	 SimpleDateFormat readFormat = new SimpleDateFormat(date_format, Locale.ENGLISH);
   	    readFormat.setTimeZone(TimeZone.getTimeZone("UTC" ));
   	    try {
   	    	String pc[]= this.getDatePieces(startdate,"");	   	    	

   	    	String fdate= ""; 
   	    	fdate= pc[0]+" "+pc[1]+" "+pc[2]+" "+pc[3]+" "+pc[4]+":"+pc[5]+":"+pc[6]+" "+pc[7];
   			senddate = readFormat.parse(fdate);
   		} catch (ParseException e1) {
   			// TODO Auto-generated catch block
   			e1.printStackTrace();
   		}
   	    
   	    return senddate;
}








	
/*
 *  Build string array of date peices
 * 
 * 	
 */
private String[] getDatePieces(String date,String duration) {

		
		String[] pieces = date.split("\\s") ;
		String[] time = pieces[4].split(":");

		String[] segments = new String[] {pieces[0],pieces[1],pieces[2],pieces[3],time[0],time[1],time[2],pieces[5],pieces[6]};
				
			
		return segments;
	}
	




	
	
	private Date getDate(String startdate,String duration) {

		
		String[] pieces = startdate.split("\\s") ;
		String[] time = pieces[4].split(":");
		
	
		int HOUR_OF_DAY =Integer.parseInt(time[0].trim());
		if(duration!=null) {
			if(!duration.equals(""))
			HOUR_OF_DAY+=Integer.parseInt(duration);
		}
		
		int year =Integer.parseInt(pieces[3].trim());
		int month =getMonthForName(pieces[1].trim());
		int dom =Integer.parseInt(pieces[2].trim());
		int dow =getDayForName(pieces[0].trim()); 
		int hod =HOUR_OF_DAY;
		int min =Integer.parseInt(time[1].trim()); 


	
		
		Calendar calendar = Calendar.getInstance();
		//calendar.setTimeZone(TimeZone.getTimeZone(pieces[5].trim() + pieces[6].trim())); 
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH,dom);
		calendar.set(Calendar.DAY_OF_WEEK, dow);
		calendar.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY );
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);

		Date alarmTime = calendar.getTime();		

		return alarmTime;
		
	}
	
	private int getMonthForName(String name) {
		
		int month=0;
		
		switch (name) {	
			case "Jan":  month = Calendar.JANUARY;
				break;
			case "Feb":  month =  Calendar.FEBRUARY;
				break;
			case "Mar":  month = Calendar.MARCH;
				break;
			case "Apr":  month = Calendar.APRIL;
			    break;
			case "May":  month = Calendar.MAY;
			    break;
			case "Jun":  month = Calendar.JUNE;
			    break;
			case "Jul":  month = Calendar.JULY;
			    break;
			case "Aug":  month = Calendar.AUGUST;
			    break;
			case "Sep":  month = Calendar.SEPTEMBER;
			    break;
			case "Oct": month = Calendar.OCTOBER;
			    break;
			case "Nov": month = Calendar.NOVEMBER;
			    break;
			case  "Dec": month =Calendar.DECEMBER;
			    break;
			default: month = Calendar.JANUARY;
			    break;
		
		}
		
		
		
		
		return month;
	}
	
	
	
	
private int getDayForName(String name) {
		
		int day=0;
		
		switch (name) {	
			case "Mon":  day = 1;
				break;
			case "Tue":  day = 2;
				break;
			case "Wed":  day = 3;
				break;
			case "Thu":  day = 4;
			    break;
			case "Fri":  day = 5;
			    break;
			case "Sat":  day = 6;
			    break;
			case "Sun":  day = 7;
			    break;

			default: day = 0;
			    break;
		
		}
		
		
		
		
		return day;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
