package structures;

import structures.WrongStructureFormatException;
import structures.Coordinates;
import structures.Location;
import structures.Color;
import structures.CSV;
import structures.CSVSerializable;
import java.time.LocalDate;
import java.lang.Long;
import java.lang.Comparable;
import java.io.*;

public class Person implements Comparable<Person>, CSVSerializable {
	// I'm not ready to spend my time here and in the other structures
    // on the bs getters and setters for absolutly no reason.

    /* privete */ public long id;
    /* privete */ public String name;
    /* privete */ public Coordinates coordinates;
    /* privete */ public LocalDate creationDate;
    /* privete */ public long height;
    /* privete */ public Color eyeColor;
    /* privete */ public Color hairColor;
    /* privete */ public Country nationality;
    /* privete */ public Location location;
    
    @Override
    public int compareTo(Person y) {
        return location.z > y.location.z ? 1 : location.z < y.location.z ? -1 : 0;
    }
    
    @Override
    public String toString() {
        String ret = "Person {";
        
        ret += "\n   " + (new Long(id)).toString();
        ret += "\n   " + name.toString();
        
        String[] lines = coordinates.toString().split("\n");
        for (String line : lines)
            ret += "\n   " + line;
        
        ret += "\n   " + creationDate.toString();
        ret += "\n   " + (new Long(height)).toString();
        ret += "\n   " + eyeColor.toString();
        ret += "\n   " + (hairColor == null ? "null" : hairColor.toString());
        ret += "\n   " + nationality.toString();
        
        lines = location.toString().split("\n");
        for (String line : lines)
            ret += "\n   " + line;
        
        return ret + "\n}\n";
    }
    
    @Override
    public String toCSV() {
        String ret = "";

        ret += CSV.decorate((new Long(id)).toString());
        ret += "," + CSV.decorate(name.toString());
        ret += "," + coordinates.toCSV();
        ret += "," + CSV.decorate(creationDate.toString());
        ret += "," + CSV.decorate((new Long(height)).toString());
        ret += "," + CSV.decorate(eyeColor.toString());
        ret += "," + CSV.decorate((hairColor == null ? "null" : hairColor.toString()));
        ret += "," + CSV.decorate(nationality.toString());
        ret += "," + location.toCSV();
       
        return ret;
    }
    
    @Override
    public int fromCSV(String str, int offset) {
        String val;
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        id = Long.parseLong(CSV.undecorate(val));
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        name = CSV.undecorate(val);

        coordinates = new Coordinates();
        offset = coordinates.fromCSV(str, offset);
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        creationDate = LocalDate.parse(CSV.undecorate(val));

        val = CSV.nextCell(str, offset);
        offset += val.length();
        height = Long.parseLong(CSV.undecorate(val));

        val = CSV.nextCell(str, offset);
        offset += val.length();
        eyeColor = Color.valueOf(CSV.undecorate(val));
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        val = CSV.undecorate(val);
        if (val.equals("null"))
            hairColor = null;
        else    
            hairColor = Color.valueOf(val);
        
        val = CSV.nextCell(str, offset);
        offset += val.length();
        nationality = Country.valueOf(CSV.undecorate(val));
        
        location = new Location();
        offset = location.fromCSV(str, offset);
        
        return offset;
    }
    
    /**
    * @brief Make person from stream in an interactive manner.
    * 
    * @param reader Input stream from the user.
    * @param writer Output stream to the user.
    * @return Constructed person.
    * @throws IOException if either of the streams falis.
    * @throws WrongStructureFormatException if the user fails to input a person.
    */
    public static Person fromStream(BufferedReader reader, BufferedWriter writer) throws IOException,
        WrongStructureFormatException {
        Person ret = new Person();
        ret.creationDate = LocalDate.now();
        
        writer.write("Person:\n");
        writer.flush();
        
        for (;;) {
            writer.write("Name: ");
            writer.flush();
            
            String str = reader.readLine();
            if (str == null) throw new WrongStructureFormatException();
            
            if (str.length() > 0) {
                ret.name = str;
                break;
            } else {
                writer.write("Illigal name string.\n");
                writer.flush();
            }
        }
        
        ret.coordinates = Coordinates.fromStream(reader, writer);
        
        for (;;) {
            writer.write("Height: ");
            writer.flush();
            
            String str = reader.readLine();
            if (str == null) throw new WrongStructureFormatException();
            
            try {
                long height = Long.parseLong(str);
                if (height > 0) {
                    ret.height = height;
                    break;
                } else {
                    writer.write("Value is out of range [1, 9223372036854775807].\n");
                    writer.flush();
                }
            } catch (NumberFormatException e) {
                writer.write("Unable to parse the value.\n");
                writer.flush();
            }
        }
        
        for (;;) {
            writer.write("Eye color:\n");
            for (Color value : Color.values()) {
                writer.write(value.toString());
                writer.write("\n");
            }
            writer.flush();
            
            String str = reader.readLine();
            if (str == null) throw new WrongStructureFormatException();
            
            try {
                ret.eyeColor = Color.valueOf(str);
                break;
            } catch (IllegalArgumentException e) {
                writer.write("Unable to parse the value.\n");
                writer.flush();
            }
        }
        
        for (;;) {
            writer.write("Hair color:\n");
            for (Color value : Color.values()) {
                writer.write(value.toString());
                writer.write("\n");
            }
            writer.flush();
            
            String str = reader.readLine();
            if (str == null) throw new WrongStructureFormatException();
            
            if (str.length() == 0) {
                ret.hairColor = null;            
                break;
            }
            
            try {
                ret.hairColor = Color.valueOf(str);
                break;
            } catch (IllegalArgumentException e) {
                writer.write("Unable to parse the value.\n");
                writer.flush();
            }
        }
        
        for (;;) {
            writer.write("Nationality:\n");
            for (Country value : Country.values()) {
                writer.write(value.toString());
                writer.write("\n");
            }
            writer.flush();
            
            String str = reader.readLine();
            if (str == null) throw new WrongStructureFormatException();
            
            try {
                ret.nationality = Country.valueOf(str);
                break;
            } catch (IllegalArgumentException e) {
                writer.write("Unable to parse the value.\n");
                writer.flush();
            }
        }
        
        ret.location = Location.fromStream(reader, writer);
        
        return ret;
    }
}
