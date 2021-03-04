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

public class Person implements Comparable<Person>, CSVSerializable, Interactive {
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
        ret += "," + CSV.decorate((hairColor == null ? "" : hairColor.toString()));
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
        if (val.length() == 0)
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
    
    @Override
    public void fromStream(InteractionStreams userIO) throws IOException, WrongStructureFormatException {
        creationDate = LocalDate.now();
        
        userIO.writeRequest("Person:\n");
        
        for (;;) {
            userIO.writeRequest("Name: ");
            
            String str = userIO.readLine();
            if (str == null) throw new WrongStructureFormatException();
            
            if (str.length() > 0) {
                name = str;
                break;
            } else {
                userIO.writeWarning("Illigal name string.\n");
            }
        }
        
        coordinates = new Coordinates();
        coordinates.fromStream(userIO);
        
        for (;;) {
            userIO.writeRequest("Height: ");
            
            String str = userIO.readLine();
            if (str == null) throw new WrongStructureFormatException();
            
            try {
                long height = Long.parseLong(str);
                if (height > 0) {
                    height = height;
                    break;
                } else {
                    userIO.writeWarning("Value is out of range [1, 9223372036854775807].\n");
                }
            } catch (NumberFormatException e) {
                userIO.writeWarning("Unable to parse the value.\n");
            }
        }
        
        for (;;) {
            userIO.writeRequest("Eye color:\n");
            for (Color value : Color.values())
                userIO.writeRequest(value.toString() + "\n");
            
            String str = userIO.readLine();
            if (str == null) throw new WrongStructureFormatException();
            
            try {
                eyeColor = Color.valueOf(str);
                break;
            } catch (IllegalArgumentException e) {
                userIO.writeWarning("Unable to parse the value.\n");
            }
        }
        
        for (;;) {
            userIO.writeRequest("Hair color:\n");
            for (Color value : Color.values())
                userIO.writeRequest(value.toString() + "\n");
            
            String str = userIO.readLine();
            if (str == null) throw new WrongStructureFormatException();
            
            if (str.length() == 0) {
                hairColor = null;            
                break;
            }
            
            try {
                hairColor = Color.valueOf(str);
                break;
            } catch (IllegalArgumentException e) {
                userIO.writeWarning("Unable to parse the value.\n");
            }
        }
        
        for (;;) {
            userIO.writeRequest("Nationality:\n");
            for (Country value : Country.values())
                userIO.writeRequest(value.toString() + "\n");
            
            String str = userIO.readLine();
            if (str == null) throw new WrongStructureFormatException();
            
            try {
                nationality = Country.valueOf(str);
                break;
            } catch (IllegalArgumentException e) {
                userIO.writeWarning("Unable to parse the value.\n");
            }
        }
        
        location = new Location();
        location.fromStream(userIO);
    }
}
