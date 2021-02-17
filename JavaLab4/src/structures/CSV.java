package structures;

import java.io.*;

public class CSV {
	
	public static String decorate(String val) {
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
	
	public static String undecorate(String val) {
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
	
	public static String nextCell(String val, int offset) {
		int firstChar  = val.charAt(offset) == ',' ? 1 : 0;
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
