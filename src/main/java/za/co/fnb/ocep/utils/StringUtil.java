package za.co.fnb.ocep.utils;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	public static String applyCamelCaseRules(String sentence){
    	return( applyCamelCaseRules( sentence, true ) );
    }
    
    public static String applyCamelCaseRules(String sentence, boolean haveSpacesAroundReservedWords){
    	if(sentence == null) return sentence;
		// remove spaces
		sentence = sentence.trim().replaceAll(" +", " ");
		// convert first letters to upper
		sentence = StringUtil.convertAllFirstLettersToUpper( sentence );
		
		// exclude exceptions
		StringTokenizer st = new StringTokenizer(sentence); 
		while(st.hasMoreTokens()) { 
			String key = st.nextToken();
			// vods exception...
			if(key.toLowerCase().indexOf("vods") > -1 || key.toLowerCase().indexOf("vds") > -1){
				sentence = sentence.replaceAll(key,key.toUpperCase());
			}
			String space = haveSpacesAroundReservedWords ? " " : "";
			for( String EXCEPTION : EXCEPTIONS ) {
				if(key.equalsIgnoreCase(EXCEPTION)){
					sentence = sentence.replaceAll( "\\s("+key+")\\s", " "+EXCEPTION+" " );
					sentence = sentence.replaceAll( "("+key+")\\s", EXCEPTION+" " );
					sentence = sentence.replaceAll( "("+key+")", space+EXCEPTION+space );
				}
	    	}
		}
		
		// convert <word+number> to uppercase
		Pattern pattern = Pattern.compile("\\w[a-z|A-Z]*\\d+[a-z|A-Z]*");
	     Matcher matcher = pattern.matcher(sentence);
	     while (matcher.find()) { 
	    	 sentence = sentence.trim().replaceAll(matcher.group(), matcher.group().toUpperCase());
	     }
	     
	     // convert words that start with a number
	     Pattern pattern2 = Pattern.compile("\\d+[a-z|A-Z]*");
	     Matcher matcher2 = pattern2.matcher(sentence);
	     while (matcher2.find()) { 
	    	 sentence = sentence.trim().replaceAll(matcher2.group(), matcher2.group().toUpperCase());
	     }
		return sentence;
	}
	
    public static String[] EXCEPTIONS = {"INT", "FNB", "PC", "PW", "RMB", "RMBPB", "PB", "KFC", "FNBACP", 
		 "BP", "PnP", "VoiP", "eBucks", "co", "ADSL" ,
		 "za", "8.ta", "BlackBerry", "MTN", "KFC", "MyNotice", "MyMeg",
		 "SMS", "SMSs", "USD", "iPhone", "iPad", "iPod", "PEP" ,"OTP", "eWallet", "GEO", "HTC", "1G", "2G", "3G", "3GS", "4G", "4S", "5G", "iTunes", "eB", "CNY" };
    
    public static final String convertAllFirstLettersToUpper(String sentence) {
        if( sentence == null ) {
              return( sentence );
        }
        sentence = sentence.toLowerCase();
        StringBuilder humanized = new StringBuilder();
        boolean nextToUpper = true;
        for( int x = 0; x < sentence.length(); x++ ) {
              char c = sentence.charAt( x );                  
              if( nextToUpper ) {
                    c = Character.toUpperCase( c );
                    nextToUpper = false;
              }
              humanized.append( c );              
              if( sentence.charAt( x ) == '/' ||
              	sentence.charAt( x ) == '\\' ||
              	sentence.charAt( x ) == '*' ||
              	sentence.charAt( x ) == ' ' || 
                  sentence.charAt( x ) == '#' ||
              	sentence.charAt( x ) == ',' ||
              	sentence.charAt( x ) == '-' ) {
                    nextToUpper = true;
              }
        }
        return( humanized.toString() );
   }
    
    public static String maskAccountNum(String accnum){
		accnum = stripLeadingZeros(accnum);
		if (accnum.length() < 12){
			return accnum;
		}
		String replace = accnum.substring(6, 12);
		return accnum.replaceFirst(replace, "******");
	}
    
    public static String stripLeadingZeros(String input) {
		while( true ) {
			if( input.startsWith( "0" ) ) {
				input = input.substring( 1 );
			}
			else {
				return( input );
			}
		}
	}
    
    /**
	 * Replaces a variable of the format ${variable} in a specified string
	 * 
	 * @param str - String containing a variable to replace
	 * @param variable - variable name to replace
	 * @param value - value to replace variable with
	 * @return - The original String with the variable replaced.
	 */	
	public static String replaceVar(String str, String variable, String value) {
		if( value == null ) {
			value = "";
		}
		String fullVar = "\\$\\{" + variable + "\\}";
		if (value != null
				&& (value.indexOf('\\') >= 0 || value.indexOf('$') >= 0)) {
			fullVar = "${" + variable + "}";

			if (str == null) {
				
				return null;
			}

			int i = str.indexOf(fullVar);
			if (i < 0) {
				return str;
			}

			int len = fullVar.length();
			return str.substring(0, i) + value + str.substring(i + len);
		} else {
			return str != null ? str.replaceAll(fullVar, value) : null;
		}
	}
	
	/**
	 * 
	 * @param html String with the $if variables to replace
	 * @param key The name of the boolean to replace
	 * @param value Either true or false
	 * @return Formatted HTML with the values replaced
	 */
	public static String replaceIfVar(String html, String key, String value) {
		if(html == null)
			return html;
		
		if(!html.contains("$if{"+key) || key == null || value == null || html.indexOf("$if{") == -1)
			return html;
				
		String tempKey = "";
		int x = 0,y = 0, z = 0;
		boolean replace = Boolean.parseBoolean(value);
		
		while(!tempKey.equalsIgnoreCase(key)){
			x = html.indexOf("$if{", z);
			y = html.indexOf("?", z);
			z = html.indexOf("}fi$", z);
						
			while(y <= x){
				String temp = html.substring(y+1);
				int q = temp.indexOf("?");
				y += q+1;
			}
			
			while(z <= y){
				String temp = html.substring(z+1);
				int q = temp.indexOf("}fi$");
				z += q+1;
			}		
			
			tempKey = html.substring(x+4, y);
		}
		
		String fullReplace = html.substring(x,z+4);
		String[] values = html.substring(y+1, z).split("::");		
		
		if(replace){
			html = html.replace(fullReplace, values[0]);
		}
		else{
			html = html.replace(fullReplace, values[1]);
		}			
		
		return html;
	}
	
	/**
	 * Replaces any $if variables left in the HTML with the default value (It's false value)
	 * @param html String with the $if variables to replace
	 * @return formatted HTML with the default values
	 */
	public static String replaceIfVarWithDefault(String html) {
		if(html == null)
			return null;
		
		int x = html.indexOf("$if{");
		int y = html.indexOf("?");
		int z = html.indexOf("}fi$");
		
		if(x == -1){
			return html;
		}
		
		while(y <= x){
			String temp = html.substring(y+1);
			int q = temp.indexOf("?");
			y += q+1;
		}
		
		while(z <= y){
			String temp = html.substring(z+1);
			int q = temp.indexOf("}fi$");
			z += q+1;
		}
		
		String fullReplace = html.substring(x,z+4);
		String[] values = html.substring(y+1, z).split("::");
		html = html.replace(fullReplace, values[1]);			
		
		if(html.contains("$if{"))
			html = replaceIfVarWithDefault(html);
		
		return html;
	}
	
	public static String formatTelcoNumber(String number) {
		String numeric = "";
		
		// first things first, check if the number starts with a +
		// if, so add this to the final number
		if( number.startsWith( "+" ) ) {
			numeric += "+";
		}
		
		// now we strip everything except for NUMERIC values out of the 
		// number, and that is it
		for( int x = 0; x < number.length(); x++ ) {
			if( number.charAt( x ) >= 48 && number.charAt( x ) <= 57 ) {
				numeric += number.substring( x, x + 1 );
			}
		}
		
		return( numeric );		
	}
}
