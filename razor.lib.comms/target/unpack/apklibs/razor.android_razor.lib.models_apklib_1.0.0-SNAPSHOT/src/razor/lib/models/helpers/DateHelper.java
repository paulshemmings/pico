package razor.lib.models.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
	
	public static String MakeFriendly(String dateStr)
	{
		return DateHelper.formatDate(dateStr,"EEE dd MMM yyyy");
	}
 	
	public static Date buildDate(int year, int month, int day){
		String dateString = String.format("%d-%d-%d", 1900+year,1+month,day);
		return stringToDate(dateString);
	}
	
	public static String makeFriendlyTimeDate(Date date) {	
		String s = null;
		if(date!=null){
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy 'at' hh:mm aa");
			df.setTimeZone(java.util.TimeZone.getDefault());
			s = df.format(date);
		}
		return s;
	}
	
	/*
		Used primarily by the Message list screen to show: Today, Yesterday and <Day of the Week>
		Examples: 
			Today at 05:45 PM
			Yesterday at 10:30 AM
			Monday at 11:00 AM
			23 Aug 2012 at 12:30 PM
	*/
	public static String makeFriendlyShortTimeDate(Date sendDate) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(sendDate);
	    Calendar today = Calendar.getInstance();
	    Calendar yesterday = Calendar.getInstance();
	    yesterday.add(Calendar.DATE, -1);
	    DateFormat timeFormatter = new SimpleDateFormat("hh:mm aa");
	    timeFormatter.setTimeZone(java.util.TimeZone.getDefault());
	    DateFormat dayFormatter = new SimpleDateFormat("EEEEEEEE 'at' hh:mm aa");
	    dayFormatter.setTimeZone(java.util.TimeZone.getDefault());
	    
	    if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
	        return "Today at " + timeFormatter.format(sendDate);
	    } 
	    else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
	        return "Yesterday at " + timeFormatter.format(sendDate);
	    }
	    else if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.WEEK_OF_YEAR) == today.get(Calendar.WEEK_OF_YEAR)){
	    	return dayFormatter.format(sendDate);
	    }
	    else {
	        return DateHelper.makeFriendlyTimeDate(sendDate);
	    }
	}
	
	
	public static Calendar getCalendarFromMillis(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(millis));
		return calendar;
	}
	
	public static Date stringToDate(String dateStr)
	{
		try
		{
			SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd"); 
			java.util.Date dateObj = curFormater.parse(dateStr);
			return dateObj;
		}
		catch(Exception ex)
		{
			
		}
		try
		{
			SimpleDateFormat curFormater = new SimpleDateFormat("EEE dd MMM yyyy HH:mm"); 
			java.util.Date dateObj = curFormater.parse(dateStr);
			return dateObj;						
		}
		catch(Exception ex)
		{
			
		}
		return new Date();
	}
	
	public static String formatDate(String dateStr,String formatValue)
	{
		String formattedDate = dateStr;
		try
		{
			//SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd"); 
			//java.util.Date dateObj = curFormater.parse(dateStr); 
			
			SimpleDateFormat postFormater = new SimpleDateFormat(formatValue); 			 
			formattedDate = postFormater.format( stringToDate(dateStr) );
		}
		catch(Exception ex)
		{
			
		}
		return formattedDate;
	}
	
	public static int daysSince(String dateStr)
	{
		int days = 0;
		try 
		{
			//SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd"); 
			//java.util.Date firstDate = curFormater.parse(dateStr); 	
			
			java.util.Date todaysDate = new Date();		
			days = calculateDifference( stringToDate(dateStr),todaysDate);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return days;				
	}
	
	public static java.util.Date todaysDate(){
		return new Date();
	}
	
	public static String getMonthName(java.util.Date date){
		return String.format(Locale.US,"%tB",date);
	}
	
	public static java.util.Date firstDayOfMonthDate(){
		java.util.Date today = todaysDate();
		int thisMonth = today.getMonth();
		return firstDayOfMonthDate(thisMonth);
	}
	public static java.util.Date firstDayOfMonthDate(int month){
		java.util.Date today = todaysDate();
		String firstDayString = String.format("%d-%d-01",1900+today.getYear(), 1 + month);
		return stringToDate(firstDayString);		
	}
	
	public static Calendar firstDayOfMonth(){
		java.util.Date today = todaysDate();
		int thisMonth = today.getMonth();
		return firstDayOfMonth(thisMonth);
	}
	
	public static Calendar firstDayOfMonth(int month){
		java.util.Date today = todaysDate();
		Calendar calendar = Calendar.getInstance();
		calendar.set(today.getYear(), month, 1);
		return calendar;
	}
	
	public static int daysBetweenDates(String firstDate,String lastDate)
	{
		int days = 0;
		try 
		{
			//SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd"); 			
			//java.util.Date firstDateObj = curFormater.parse(firstDate);		
			//java.util.Date lastDateObj = curFormater.parse(lastDate); 
			
			//days = CalculateDifference(firstDateObj,lastDateObj);
			days = calculateDifference( stringToDate(firstDate),stringToDate(lastDate) );
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return days;
	}
	
	// http://stackoverflow.com/questions/3299972/difference-in-days-between-two-dates-in-java
	public static int calculateDifference(Date a, Date b)
	{
	    int tempDifference = 0;
	    int difference = 0;
	    Calendar earlier = Calendar.getInstance();
	    Calendar later = Calendar.getInstance();

	    if (a.compareTo(b) < 0)
	    {
	        earlier.setTime(a);
	        later.setTime(b);
	    }
	    else
	    {
	        earlier.setTime(b);
	        later.setTime(a);
	    }

	    while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR))
	    {
	        tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
	        difference += tempDifference;

	        earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	    }

	    if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR))
	    {
	        tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
	        difference += tempDifference;

	        earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	    }

	    return difference;
	}	
}
