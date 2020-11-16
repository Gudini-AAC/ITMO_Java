package common;

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

	@Override
	public String toString() {
		return color;
	}

}