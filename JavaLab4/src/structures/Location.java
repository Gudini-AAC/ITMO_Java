package structures;

import structures.WrongStructureFormatException;
import structures.CSV;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Double;
import java.lang.NumberFormatException;
import java.io.*;

public class Location {
    /* private */ public Long x;
    /* private */ public double y;
    /* private */ public int z;
    /* private */ public String name;
    
    public boolean equals(Location other) {
        return x.equals(other.x) && y == other.y && z == other.z && name.equals(other.name);
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
    
    public String toCSV() {
        String ret = "";

        ret += CSV.decorate(x.toString());
        ret += "," + CSV.decorate((new Double(y)).toString());
        ret += "," + CSV.decorate((new Integer(z)).toString());
        ret += "," + CSV.decorate(name == null ? "null" : name);

        return ret;
    }
    
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
        if (val.equals("null"))
            name = null;
        else
            name = val;
        
        return offset;   
    }
    
    public static Location fromStream(BufferedReader reader, BufferedWriter writer) throws IOException,
        WrongStructureFormatException {
    	Location ret = new Location();

    	writer.write("Location:\n");
        writer.flush();

    	for (;;) {
    		writer.write("X: ");
    		writer.flush();
            
    		String str = reader.readLine();
            if (str == null) throw new WrongStructureFormatException();
    		
    		try {
    			ret.x = new Long(Long.parseLong(str));
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
    			ret.y = Double.parseDouble(str);
   				break;
    		} catch (NumberFormatException e) {
    			writer.write("Unable to parse the value.\n");
    			writer.flush();
    		}
    		
    	}
    	
		for (;;) {
    		writer.write("Z: ");
    		writer.flush();
            
    		String str = reader.readLine();
            if (str == null) throw new WrongStructureFormatException();
    		
    		try {
    			ret.z = Integer.parseInt(str);
   				break;
    		} catch (NumberFormatException e) {
    			writer.write("Unable to parse the value.\n");
    			writer.flush();
    		}
    		
    	}
		
		{
    		writer.write("Name: ");
    		writer.flush();
    		String name = reader.readLine();
    		if (name.length() > 0)
    			ret.name = name;
    		else
    			ret.name = null;
    	}
    	
    	return ret;
    }
    
}
