package structures;

import structures.WrongStructureFormatException;
import structures.CSV;
import structures.CSVSerializable;
import structures.Interactive;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Double;
import java.lang.NumberFormatException;
import java.io.*;

public class Location implements CSVSerializable, Interactive {
    /* private */ public Long x;
    /* private */ public double y;
    /* private */ public int z;
    /* private */ public String name;
    
    public boolean equals(Location other) {
        boolean namesMatch;
        
        if (name == null && other.name == null)
            namesMatch = true;
        else if (name == null || other.name == null)
            namesMatch = false;
        else 
            namesMatch = name.equals(other.name);
            
        
        return namesMatch;// && x.equals(other.x) && y == other.y && z == other.z;
    }
    
    @Override
    public String toString() {
    	String ret = "Location {";

		ret += "\n   " + x.toString();    	
		ret += "\n   " + (new Double(y)).toString();    	
		ret += "\n   " + (new Integer(z)).toString();    	
		ret += "\n   " + (name == null ? "null" : name);
    	
    	return ret + "\n}\n";
    }
    
    @Override
    public String toCSV() {
        String ret = "";

        ret += CSV.decorate(x.toString());
        ret += "," + CSV.decorate((new Double(y)).toString());
        ret += "," + CSV.decorate((new Integer(z)).toString());
        ret += "," + CSV.decorate(name == null ? "" : name);

        return ret;
    }
    
    @Override
    public int fromCSV(String str, int offset) {
        String val;
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        x = Long.parseLong(CSV.undecorate(val));
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        y = Double.parseDouble(CSV.undecorate(val));
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        z = Integer.parseInt(CSV.undecorate(val));
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        val = CSV.undecorate(val);
        if (val.length() == 0)
            name = null;
        else
            name = val;
        
        return offset;   
    }
    
    @Override
    public void fromStream(InteractionStreams userIO) throws IOException, WrongStructureFormatException {

        userIO.writeRequest("Location:\n"); 

    	for (;;) {
            userIO.writeRequest("X: "); 
            
    		String str = userIO.readLine();
            if (str == null) throw new WrongStructureFormatException();
    		
    		try {
    			x = new Long(Long.parseLong(str));
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
    			y = Double.parseDouble(str);
   				break;
    		} catch (NumberFormatException e) {
                userIO.writeWarning("Unable to parse the value.\n");
    		}
    		
    	}
    	
		for (;;) {
            userIO.writeRequest("Z: "); 
            
    		String str = userIO.readLine();
            if (str == null) throw new WrongStructureFormatException();
    		
    		try {
    			z = Integer.parseInt(str);
   				break;
    		} catch (NumberFormatException e) {
                userIO.writeWarning("Unable to parse the value.\n");
    		}
    		
    	}
		
		{
            userIO.writeRequest("Name: "); 
            
    		String str = userIO.readLine();
    		if (str.length() == 0)
    			name = null;
    		else
    			name = str;
    	}
    	
    }
    
}
