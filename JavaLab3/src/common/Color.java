package common;
import java.lang.Math;

public enum Color {
	RED       ("Red"),
	GREEN     ("Green"),
	BLUE      ("Blue"),
	MAGENTA   ("Magenta"),
	CYAN      ("Cyan"),
	LIGHT_BLUE("Light blue"),
	PINK      ("Pink"),
	YELLOW    ("Yellow"),
	ORANGE    ("Orange"),
	BLACK     ("Black"),
	LIME      ("Lime"),
	WHITE     ("White"),
	GRAY      ("Grey");

	private String color;

	Color(String color) {
		this.color = color;
	}

	public static Color makeRandom() {
		int id = (int)(Math.random() * 13.);

		switch (id) {
			case 0:  return RED; 
			case 1:  return GREEN;     
			case 2:  return BLUE;      
			case 3:  return MAGENTA;  
			case 4:  return CYAN;  
			case 5:  return LIGHT_BLUE;
			case 6:  return PINK;      
			case 7:  return YELLOW;    
			case 8:  return ORANGE;    
			case 9:  return BLACK;     
			case 10: return LIME;      
			case 11: return WHITE;     
			default: return GRAY;      
		}

	}

	@Override
	public String toString() {
		return color;
	}

}