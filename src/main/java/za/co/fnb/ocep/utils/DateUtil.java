package za.co.fnb.ocep.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
	public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat basicDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat medDateFormatter = new SimpleDateFormat("dd MMM yy");
	public static final SimpleDateFormat longDateFormatter = new SimpleDateFormat("dd MMM yyyy");
	public static final SimpleDateFormat monthFormatter = new SimpleDateFormat("yyyyMM");
	public static final SimpleDateFormat BJM_HISTORY_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	public static final SimpleDateFormat BJM_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static final SimpleDateFormat BJM_FORMATTER_NO_SECODS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	public static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyyMMdd HH:mm");
	public static final SimpleDateFormat SHARES_QUOTE_FORMATTER = new SimpleDateFormat("dd MMM yyyy HH:mm z");
	public static final SimpleDateFormat SHARES_DISPLAY_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat SHARES_SENS_DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat("dd-MM-yyyy|HH:mm");
	public static final SimpleDateFormat SHARES_SENS_DISPLAY_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat IM_BANKER_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	public static final SimpleDateFormat FNB_LIFE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	public static final SimpleDateFormat FNB_LIFE_ENTRY_FORMATTER = new SimpleDateFormat("yyyy-MM-ddXXX");
	public static final SimpleDateFormat GENERIC_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat GENERIC_DATE_FORMATTER_YEAR_FIRST = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat GENEREIC_LONG_DATE_FORMATTER_DAY_FIRST = new SimpleDateFormat("dd MMMMMM yyyy");
	public static final SimpleDateFormat GENERIC_DATE_FORMATTER2 = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat HOUR_MINUTE_FORMATTER = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat MONTH_DATE_FORMATTER = new SimpleDateFormat("dd MMM");
	public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static boolean isCurrentMonth(String vodsDate) {
		String currentMonth = monthFormatter.format(new Date(System.currentTimeMillis()));
		return vodsDate != null ? vodsDate.startsWith(currentMonth) : false;
	}

	public static boolean isLastMonth(String vodsDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		String lastMonth = monthFormatter.format(c.getTime());
		return vodsDate != null ? vodsDate.startsWith(lastMonth) : false;
	}

	public static boolean inTheFuture(Date date) {
		// check if this is after now and is not today
		if (date.after(new Date()) && !isToday(date))
			return true;
		return false;
	}

	public static boolean isToday(Date date) {
		String d = dateFormatter.format(date);
		String n = dateFormatter.format(new Date());
		return d.equals(n);
	}

	public static boolean isYesterday(Date date) {
		String d = dateFormatter.format(date);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		String n = dateFormatter.format(c.getTime());
		return d.equals(n);
	}

	public static boolean isPreviousXDays(Date date, int xdays) {
		String d = dateFormatter.format(date);
		Calendar c = Calendar.getInstance();
		// RJ: Removed the below code, as it does not make sense???
		// c.add( Calendar.DAY_OF_YEAR, -2 );
		for (int x = 0; x < xdays; x++) {
			String n = dateFormatter.format(c.getTime());
			if (d.equals(n))
				return true;
			c.add(Calendar.DAY_OF_MONTH, -1);
		}
		return false;
	}

	public static boolean isPrevious7Days(Date date) {
		String d = dateFormatter.format(date);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -2);
		for (int x = 0; x < 7; x++) {
			String n = dateFormatter.format(c.getTime());
			if (d.equals(n))
				return true;
			c.add(Calendar.DAY_OF_MONTH, -1);
		}
		return false;
	}

	public static boolean isMoreThan7DaysInFuture(Date date) {
		Calendar original = Calendar.getInstance();
		original.setTime(date);
		Calendar now = Calendar.getInstance();
		original.add(Calendar.DAY_OF_YEAR, -7);

		return now.before(original);

	}

	public static long getTimeStamp(int offset) {
		try {
			String date = dateFormatter.format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormatter.parse(date));
			cal.add(Calendar.DAY_OF_YEAR, offset);
			return cal.getTimeInMillis();
		}
		catch (ParseException e) {
			return 0l;
		}
	}

	public static long getTimeStampAtMidnight(long timestamp) {
		try {
			String date = dateFormatter.format(new Date(timestamp));
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormatter.parse(date));
			return cal.getTimeInMillis();
		}
		catch (ParseException e) {
			return 0l;
		}
	}

	/**
	 * This method returns the date as a formatted String like yyyy-MM-dd
	 *
	 * @param date
	 *            - The date that needs formatting Ex. 1305 --> 2013-05-31 OR 20140825 --> 2013-08-25
	 * @param format
	 *            - The format of the input date Ex. yyMM OR yyyyMMdd
	 * @return
	 * @throws ParseException
	 */
	public static String dateFormatter(String date, String format) throws ParseException {
		try {
			if (Integer.parseInt(date) <= 0)
				return "N/A";
		}
		catch (Exception e) {
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat actualDateFormatter = new SimpleDateFormat(format);
		Date actualDate = actualDateFormatter.parse(date);
		cal.setTime(actualDate);

		if (format.equalsIgnoreCase("yyMM")) {
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		return basicDateFormatter.format(cal.getTime());
	}

	/**
	 * This method returns the date as a formatted String like dd MMM yyyy
	 *
	 * @param date
	 *            - The date that needs formatting Ex. 1305 OR 20140825
	 * @param format
	 *            - The format of the input date Ex. yyMM OR yyyyMMdd
	 * @return
	 * @throws ParseException
	 */
	public static String dateLongFormatter(String date, String format) throws ParseException {
		try {
			if (Integer.parseInt(date) <= 0)
				return "N/A";
		}
		catch (NumberFormatException e) {
			/*
			 * Don't do anything in the catch. We will solve this issue in the next phase of the method
			 */}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat actualDateFormatter = new SimpleDateFormat(format);
		Date actualDate = actualDateFormatter.parse(date);
		cal.setTime(actualDate);

		if (format.equalsIgnoreCase("yyMM")) {
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		return longDateFormatter.format(cal.getTime());
	}

	public static String timeFormatter(String date, String format) throws ParseException {
		try {
			if (Integer.parseInt(date) <= 0)
				return "N/A";
		}
		catch (NumberFormatException e) {
			/*
			 * Don't do anything in the catch. We will solve this issue in the next phase of the method
			 */}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat actualDateFormatter = new SimpleDateFormat(format);
		Date actualDate = actualDateFormatter.parse(date);
		cal.setTime(actualDate);

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		return formatter.format(cal.getTime());
	}

	public static String timeFormatterNoSecods(String date, String format) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat actualDateFormatter = new SimpleDateFormat(format);
		Date actualDate = actualDateFormatter.parse(date);
		cal.setTime(actualDate);

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		return formatter.format(cal.getTime());
	}

	public static String timeFormatterVODS(String time) throws ParseException {
		if (time.contains(":"))
			return time;

		String time1 = time.substring(0, 2);
		String time2 = time.substring(2, 4);
		time = time1 + ":" + time2;

		return time;
	}

	public static String formatDate(String date) throws ParseException {
		// format date from VODS to UI date required
		SimpleDateFormat actualDateFormatter = new SimpleDateFormat("yyyyMMdd");
		Date actualDate = actualDateFormatter.parse(date);
		SimpleDateFormat outgoingDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String outgoingDate = outgoingDateFormatter.format(actualDate);
		return outgoingDate;
	}

	public static String formatVoucherDate(String date) throws ParseException {
		// format date from VODS to UI date required
		SimpleDateFormat actualDateFormatter = new SimpleDateFormat("yyyyMMdd");
		Date actualDate = actualDateFormatter.parse(date);
		SimpleDateFormat outgoingDateFormatter = new SimpleDateFormat("dd-MM-yy");
		String outgoingDate = outgoingDateFormatter.format(actualDate);
		return outgoingDate;
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static String formatIntoHHMMSS(int seconds) {
		int hours = seconds / 3600,
				remainder = seconds % 3600,
				minutes = remainder / 60,
				secs = remainder % 60;

		return (hours < 10 ? "0" : "") + hours
				+ ":" + (minutes < 10 ? "0" : "") + minutes
				+ ":" + (secs < 10 ? "0" : "") + secs;
	}

	public static String formatYYYYMMDD(long when) {
		SimpleDateFormat outgoingDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return outgoingDateFormatter.format(new Date(when));
	}

	public static String formatHHMMSS(long when) {
		SimpleDateFormat outgoingDateFormatter = new SimpleDateFormat("HH:mm:ss");
		return outgoingDateFormatter.format(new Date(when));
	}

	public static String formatDDMMMYY(long when) {
		return medDateFormatter.format(new Date(when));
	}

	public static String formatDDMMMHHMM(long when) {
		return MONTH_DATE_FORMATTER.format(new Date(when)) + " " + HOUR_MINUTE_FORMATTER.format(new Date(when));
	}

	public static String formatTime(String time) {
		// Only when time is 00:00:00, we get back from VODS 000000 and since this value on our side
		// will be saved as an integer, which ends up being 0, we cannot add : in some index of the
		// string
		if (time.length() == 1 && time.equals("0"))
			return "00:00:00";
		else {
			StringBuffer timeBuff = new StringBuffer(time);
			timeBuff.insert(4, ":");
			timeBuff.insert(2, ":");
			return timeBuff.toString();
		}
	}

	public static String formatLastWeek(long when) {
		// Get day values ...
		long dayWhen = when / (24 * 60 * 60000);
		long dayNow = System.currentTimeMillis() / (24 * 60 * 60000);

		if (dayNow == dayWhen)
			return "Today";
		if (dayNow - dayWhen == 1)
			return "Yesterday";
		if (dayNow - dayWhen > 1 && dayNow - dayWhen < 7) {
			SimpleDateFormat outgoingDateFormatter = new SimpleDateFormat("EEEE");
			return outgoingDateFormatter.format(new Date(when));
		}

		SimpleDateFormat outgoingDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return outgoingDateFormatter.format(new Date(when));
	}

	public static String formatDateWithDay(int vodsDate) throws ParseException {
		SimpleDateFormat actualDateFormatter = new SimpleDateFormat("yyyyMMdd");
		Date actualDate = actualDateFormatter.parse("" + vodsDate);
		SimpleDateFormat outgoingDateFormatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
		return outgoingDateFormatter.format(actualDate);
	}

	public static String deriveDateOfBirth(String idNumber) throws ParseException {
		String datePart = idNumber.substring(0, 6);
		int yearPart = Integer.parseInt(datePart.substring(0, 2));
		Calendar c = Calendar.getInstance();
		String currentYear = "" + c.get(Calendar.YEAR);
		int currentYearPart = Integer.parseInt(currentYear.substring(2));
		int currentCentury = Integer.parseInt(currentYear.substring(0, 2));

		String dob = "";

		if (yearPart <= currentYearPart) {
			dob = currentCentury + datePart;
		}
		else {
			dob = currentCentury - 1 + datePart;
		}

		Date date = dateFormatter.parse(dob);

		if (inTheFuture(date)) {
			dob = currentCentury - 1 + datePart;
		}

		return dob;
	}

	public static String convertDateToBJMDate(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return BJM_FORMATTER.format(cal.getTime());
	}

	public static String convertDateToBJMHistDate(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String result = BJM_HISTORY_FORMATTER.format(cal.getTime());
		return result.substring(0, result.length() - 2) + ":" + result.substring(result.length() - 2);
	}

	public static String convertBJMShareDateToString(String dateString) throws ParseException {
		Date date = convertBJMShareDateToDate(dateString);
		return SHARES_QUOTE_FORMATTER.format(date);
	}

	public static String convertBJMShareDateToSharesDisplayDate(String dateString) throws ParseException {
		Date date = convertBJMShareDateToDate(dateString);
		return SHARES_DISPLAY_DATE_FORMATTER.format(date);
	}

	public static String convertBJMSensDateToDisplayDate(String dateString) throws ParseException {
		Date date = convertBJMShareDateToDate(dateString);
		return SHARES_SENS_DISPLAY_DATE_FORMATTER.format(date);
	}

	public static String convertBJMSensDateToDisplayDateTime(String dateString) throws ParseException {
		Date date = convertBJMShareDateToDate(dateString);
		return SHARES_SENS_DISPLAY_DATE_TIME_FORMATTER.format(date);
	}

	public static Date convertBJMShareDateToDate(String dateString) throws ParseException {
		Date result = null;
		if (dateString.length() == 29 && dateString.charAt(dateString.length() - 3) == ':') {
			String parsable = dateString.substring(0, dateString.length() - 3) + dateString.substring(dateString.length() - 2);
			result = BJM_HISTORY_FORMATTER.parse(parsable);
		}
		else {
			result = BJM_FORMATTER.parse(dateString);
		}
		return result;
	}

	public static Date convertSharesDisplayDatetoDate(String dateString) throws ParseException {
		return SHARES_DISPLAY_DATE_FORMATTER.parse(dateString);
	}

	public static String convertIPHShareDateToString(String dateString, String timeString) throws ParseException {
		if (dateString.trim().equalsIgnoreCase("") && timeString.trim().equalsIgnoreCase(""))
			return "";
		Date date = DATE_TIME_FORMATTER.parse(dateString + " " + timeString);
		return SHARES_QUOTE_FORMATTER.format(date);
	}

	public static String formatDateToBasicFormat(Date date) {
		return basicDateFormatter.format(date);
	}

	public static Date convertBJMShareDateNoTimeZoneToDate(String dateString) throws ParseException {
		return BJM_FORMATTER.parse(dateString);
	}

	public static Date convertBJMShareDateNoTimeZoneNoSecodsToDate(String dateString) throws ParseException {
		return BJM_FORMATTER_NO_SECODS.parse(dateString);
	}

	public static String convertBJMNoSecodsToString(Date date) {
		return BJM_FORMATTER_NO_SECODS.format(date);
	}

	public static String removeBJMSeconds(String date) throws ParseException {
		return convertBJMNoSecodsToString(BJM_FORMATTER_NO_SECODS.parse(date));
	}

	public static String getSDVPaymentDueDate(int date) {
		String dueDate = "Not Set. We Messed UP!!";

		Calendar cal = Calendar.getInstance();
		if (cal.get(Calendar.DAY_OF_MONTH) == date) {
			dueDate = DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd");
		}
		else {
			while (cal.get(Calendar.DAY_OF_MONTH) != date) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			dueDate = DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd");
		}

		return dueDate;
	}

	/**
	 * This method returns the current system date with a offset in MINUTES in the format yyyyMMdd
	 *
	 * @param offsetMIN
	 *            - Number of minutes to add (Could also be a negative number)
	 * @return String Date
	 */
	public static String getCurrentDate(int offsetMIN) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		cal.add(Calendar.MINUTE, offsetMIN);
		return sdf.format(cal.getTime());
	}

	public static String getSalaryDate(int dayOfMonth) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return sdf.format(cal.getTime());
	}

	/**
	 * This method returns the current system time with a offset in MINUTES in the format HHmmss
	 *
	 * @param offsetMIN
	 *            - Number of minutes to add (Could also be a negative number)
	 * @return String Time
	 */
	public static String getTime(int offsetMIN) {
		return getTime(offsetMIN, "HHmmss");
	}

	/**
	 * This method returns the current system time with a offset in MINUTES in the format HHmmss
	 *
	 * @param offsetMIN
	 *            - Number of minutes to add (Could also be a negative number)
	 * @return String Time
	 */
	public static String getTime(int offsetMIN, String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		cal.add(Calendar.MINUTE, offsetMIN);
		return sdf.format(cal.getTime());
	}

	public static long formatCCYYMMDD(long when) {
		try {
			return Long.parseLong(dateFormatter.format(new Date(when)));
		}
		catch (NumberFormatException exception) {
			return 0;
		}
	}

	public static String formatToCCYYMMDD(long when) {
		return dateFormatter.format(new Date(when));
	}

	public static long formatCCYYMMDD(Date date) {
		try {
			return Long.parseLong(dateFormatter.format(date));
		}
		catch (NumberFormatException exception) {
			return 0;
		}
	}

	// JO: Returns Calendar object from vods date string (20150617)
	public static Calendar convertDateStringToCalendar(String dateString) {

		int year = Integer.parseInt(dateString.substring(0, 4));
		int month = Integer.parseInt(dateString.substring(4, 6));
		int day = Integer.parseInt(dateString.substring(6, 8));

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);

		return cal;
	}

	public static String convertDateFormat(String curdateFormat, String date, String changeToDateFormat) throws ParseException {
		Date dt = new SimpleDateFormat(curdateFormat).parse(date);
		return formatDate(dt, changeToDateFormat);
	}

	// SHEF
	/**
	 * Get a diff between two dates returned in the TIMEUNIT required (DAYS, MINUTES, etc.)
	 *
	 * @param date1
	 *            the oldest date
	 * @param date2
	 *            the newest date
	 * @param timeUnit
	 *            the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiffInTimeUnit(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	// TB
	/**
	 * converts date in supplied format to timestamp
	 *
	 * @param date
	 *            the date to convert
	 * @param formatter
	 *            the format which the date is in
	 * @return the timestamp
	 */
	public static long convertDateToTimestamp(String date, SimpleDateFormat formatter) {

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(formatter.parse(date));

			return cal.getTime().getTime();
		}
		catch (ParseException e) {
			return 0L;
		}

	}

	// TB
	/**
	 * converts from timestamp to Specified format
	 *
	 * @param timestamp
	 *            the date to convert
	 * @param formatter
	 *            the format which the date is in
	 * @return the date String
	 */
	public static String convertDateFromTimestamp(long timestamp, SimpleDateFormat formatter) {
		return formatter.format(timestamp);
	}

	public static String convertFromCalendarToString(Calendar calendar, SimpleDateFormat formatter) {

		return formatter.format(calendar.getTime());
	}

	/**
	 * converts Date formats to DD month year
	 *
	 * @param date
	 *            the date to convert as string
	 * @return the date String
	 */
	public static String convertDateToLongDate(String date, SimpleDateFormat formatter) {

		Calendar calendar = DateUtil.convertDateStringToCalendar(date);

		return formatter.format(calendar.getTime());
	}

	public static Date convertDateStringToDate(String date) throws ParseException {
		return dateFormatter.parse(date);
	}

	/**
	 * Receives the date in a YYYYMMdd OR yyyMMdd format and returns it as dd MMM
	 *
	 * @param date
	 * @return
	 */
	public static String getShortDateForIncontact(String date) {
		// Add this amount to date because it might be in the format 1170313
		date = Long.parseLong(date) + 10000000 + "";

		Calendar cal = Calendar.getInstance();
		// Need to -1 off the integer as MONTH is a enum that returns a int as an index that starts Jan at index 0
		cal.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6, 8)));

		return MONTH_DATE_FORMATTER.format(cal.getTime());
	}

	/**
	 * Receives the date in a YYYYMMdd format and returns it as dd MMM
	 *
	 * @param date
	 * @return
	 */
	public static String getTimeForIncontact(String time) {
		if (time.isEmpty())
			return "";

		if (time.length() == 7) {
			time = "0" + time;
		}

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
		cal.set(Calendar.MINUTE, Integer.parseInt(time.substring(2, 4)));

		return HOUR_MINUTE_FORMATTER.format(cal.getTime());
	}

	public static Date getDateWithTime(String time) {
		Date date = new Date();
		String timeArray[] = formatTime(time).split(":");
		date.setHours(Integer.parseInt(timeArray[0]));
		date.setMinutes(Integer.parseInt(timeArray[1]));
		date.setSeconds(Integer.parseInt(timeArray[2]));
		return date;
	}
}
