package structures;

import structures.WrongStructureFormatException;
import structures.CSV;
import structures.CSVSerializable;
import java.lang.Integer;
import java.lang.Float;
import java.lang.NumberFormatException;
import java.io.*;

public class Coordinates implements CSVSerializable {
    /* private */ public int x;
    /* private */ public float y;
    
    @Override
    public String toString() {
    	String ret = "Coordinates {";

		ret += "\n   " + (new Integer(x)).toString();    	
		ret += "\n   " + (new Float(y)).toString();    	
    	
    	return ret + "\n}\n";
    }
    
    @Override
    public String toCSV() {
        String ret = "";

        ret += CSV.decorate((new Integer(x)).toString());
        ret += "," + CSV.decorate((new Float(y)).toString());
        
        return ret;
    }
    
    @Override
    public int fromCSV(String str, int offset) {
        String val;
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        x = Integer.parseInt(CSV.undecorate(val));
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        y = Float.parseFloat(CSV.undecorate(val));

        return offset;   
    }
    
    /**
    * @brief Make coordinates from stream in an interactive manner.
    * 
    * @param reader Input stream from the user.
    * @param writer Output stream to the user.
    * @return Constructed coordinates.
    * @throws IOException if either of the streams falis.
    * @throws WrongStructureFormatException if the user fails to input the coordinates.
    */
    public static Coordinates fromStream(BufferedReader reader, BufferedWriter writer) throws IOException,
        WrongStructureFormatException {
    	Coordinates ret = new Coordinates();
    	
    	writer.write("Coordinates:\n");
        writer.flush();
    	 
    	for (;;) {
    		writer.write("X: ");
    		writer.flush();
            
    		String str = reader.readLine();
            if (str == null) throw new WrongStructureFormatException();
    		
    		try {
    			ret.x = Integer.parseInt(str);
    			break;
    		} catch (NumberFormatException e) {
    			writer.write("Unable to parse the value.\n");
    			writer.flush();
    		}
    		
    	}
    	
    	for (;;) {
    		writer.write("Y: ");
    		writer.flush();
            
    		String str = reader.readLine();
            if (str == null) throw new WrongStructureFormatException();
    		
    		try {
    			float y = Float.parseFloat(str);
    			if (y <= 971) {
    				ret.y = y;
    				break;
    			} else {
    				writer.write("Value is out of range [-inf, 971].\n");
    				writer.flush();
    			}
    		} catch (NumberFormatException e) {
    			writer.write("Unable to parse the value.\n");
    			writer.flush();
    		}
    		
    	}
    	
    	return ret;
    }
    
}
