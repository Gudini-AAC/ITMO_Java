package common;
import java.lang.IndexOutOfBoundsException;

public enum CardinalDirection {
	EAST ("East"),
	WEST ("West"),
	NORTH("North"),
	SOUTH("South"),

	SOUTH_WEST("South west"),
	SOUTH_EAST("South east"),
	NORTH_WEST("North west"),
	NORTH_EAST("North east");

	private String direction;

	CardinalDirection(String direction) {
		this.direction = direction;
	}

	public static CardinalDirection fromIndex(int index) {
		switch(index) {
			case 0: return CardinalDirection.EAST;
			case 1: return CardinalDirection.NORTH_EAST;
			case 2: return CardinalDirection.NORTH;
			case 3: return CardinalDirection.NORTH_WEST;
			case 4: return CardinalDirection.WEST;
			case 5: return CardinalDirection.SOUTH_WEST;
			case 6: return CardinalDirection.SOUTH;
			case 7: return CardinalDirection.SOUTH_EAST;
			default: throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public String toString() {
		return direction;
	}
}

