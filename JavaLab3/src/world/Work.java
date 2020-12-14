package world;
import java.lang.Math;

/**
* @brief A work to do
*/
public class Work {
	/**
	* @brief Gives a description of the work
	* @return Description of the work
	*/
	public String getDescription() {
		return description;
	}

	/**
	* @brief Creates work with a random description
	*/
	public Work() {
		description = descriptions[(int)(Math.random() * descriptions.length) % descriptions.length];
	}

	private String description;

	private static final String[] descriptions = {
		"Hole carving",
		"Bag dropping",
		"Direction determination",
		"Programming",
		"Taking away things"
	};

}

