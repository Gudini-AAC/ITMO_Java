package structures;

import structures.WrongStructureFormatException;
import structures.CSV;
import structures.CSVSerializable;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Double;
import java.lang.NumberFormatException;
import java.io.*;

public class Location implements CSVSerializable {
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
            
        
        return namesMatch && x.equals(other.x) && y == other.y && z == other.z;
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
        ret += "," + CSV.decorate(name == null ? "null" : name);

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
        if (val.equals("null"))
            name = null;
        else
            name = val;
        
        return offset;   
    }
    
    /**
    * @brief Make location from stream in an interactive manner
    * 
    * @param reader Input stream from the user
    * @param writer Output stream to the user
    * @return Constructed location
    * @throws IOException if either of the streams falis
    * @throws WrongStructureFormatException if the user fails to input a location
    */
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
