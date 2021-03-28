package structures;

import java.io.*;

/**
* @brief Just some functions used to help with CSV parsing
*/
public class CSV {
	
	/**
	* @brief Formats the string so it can be used as a CSV cell.
	*        String is quoted if it has quotes or commas.
	*        All of the inside the quotes are duplicated.
	*
	* @warning This function doesn't add commas, so do that yourself.
	* 
	* @param val String that you want to store as CSV
	* @return String with all of the formating needed
	*/
	public static String decorate(String val) {
		if (val.length() == 0) return "";
		boolean hasQuotes = val.indexOf("\"") != -1;
		boolean hasCommas = val.indexOf(",")  != -1;
		
		if (hasQuotes) {
			String str = "";
			
			for (int i = 0; i < val.length(); i++) {
				char c = val.charAt(i);
				if (c == '\"') str += "\"\"";
				else str += c;
			}
			
			val = str;
		}
		
		if (hasQuotes || hasCommas)
			val = "\"" + val + "\"";
		
		return val;
	}
	
	/**
	* @brief Strip all of the CSV specific stuff. See the decorate
	*        function to learn more.
	* 
	* @warning This function assumes that the comma either leading or not present.
	* 
	* @param val String that represents CSV cell with or without leading comma.
	* @return String that is stored in the CSV cell.
	*/
	public static String undecorate(String val) {
		if (val.length() == 0 || val.equals(",")) return "";
		
		int firstChar  = val.charAt(0) == ',' ? 1 : 0;
		boolean quoted = val.charAt(firstChar) == '\"';
		if (!quoted) return val.substring(firstChar, val.length());
		
		String str = "";
		
		for (int i = firstChar + (quoted ? 1 : 0); i + 1 < val.length();) {
			char c = val.charAt(i);
			if (c == '\"') { str += "\""; i += 2; }
			else { str += c; i += 1; }
		}
		
		return str;		
	}
	
	/**
	* @brief Find the string that represents a single cell in a
	*        multi-cell input string with a known offset from the start.
	*
	* @param val String to parse.
	* @param offset Offset into the val.
	* @return String that represents a single cell.
	* 
	* @usage Add length of the returned string to the offset to parse the next cell.
	*/
	public static String nextCell(String val, int offset) {
		int firstChar = offset != 0 && val.charAt(offset) == ',' ? 1 : 0;
		
		if (firstChar + offset >= val.length() || val.charAt(firstChar + offset) == ',')
			return ",";
		
		boolean quoted = val.charAt(firstChar + offset) == '\"';

		int end = offset + (quoted ? 1 : 0) + firstChar;		
		for (int i = end; i < val.length();) {
			boolean thisIsQuote = val.charAt(i) == '\"';	
			boolean nextIsEnd   = i + 1 == val.length();
			
			// "Random quoted string",
			//          We are here ^
			if (quoted && thisIsQuote && (nextIsEnd || val.charAt(i + 1) == ',')) {
				end = i + 1;
				break;
			}
			
			// Random non-quoted string,
			//            We are here ^
			if (!quoted && (nextIsEnd || val.charAt(i + 1) == ',')) {
				end = i + 1;
				break;
			}
			
			// "Random quoted"", string",
			//   We are here ^
			if (quoted && thisIsQuote && val.charAt(i + 1) == '\"')
				i += 2;
			else
				i += 1;
		}
		
		return val.substring(offset, end);
	}
	
}
