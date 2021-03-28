package structures;

import structures.WrongStructureFormatException;
import structures.CSV;
import structures.CSVSerializable;
import java.lang.Integer;
import java.lang.Float;
import java.lang.NumberFormatException;
import java.io.Serializable;
import java.io.*;

public class Coordinates implements CSVSerializable, Interactive, Serializable {
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
    
    @Override
    public void fromStream(InteractionStreams userIO) throws IOException, WrongStructureFormatException {
  
        userIO.writeRequest("Coordinates:\n");
            	 
    	for (;;) {
            userIO.writeRequest("X: ");      
            
    		String str = userIO.readLine();
            if (str == null) throw new WrongStructureFormatException();
    		
    		try {
    			x = Integer.parseInt(str);
    			break;
    		} catch (NumberFormatException e) {
                userIO.writeWarning("Unable to parse the value.\n");
    		}
    	}
    	
    	for (;;) {
            userIO.writeRequest("Y: "); 
            
    		String str = userIO.readLine();
            if (str == null) throw new WrongStructureFormatException();
    		
    		try {
    			float y = Float.parseFloat(str);
    			if (y <= 971) {
    				this.y = y;
    				break;
    			} else {
                    userIO.writeWarning("Value is out of range [-inf, 971].\n");
    			}
    		} catch (NumberFormatException e) {
                userIO.writeWarning("Unable to parse the value.\n");
    		}
    	}
    	
    }
    
}
