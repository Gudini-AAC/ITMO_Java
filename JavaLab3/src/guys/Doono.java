package guys;
import world.Named;
import world.Work;
import guys.Guy;
import linalg.Vec3;
import linalg.StatePair;
import java.lang.Math;

/**
* @brief Named guy that knows stuff
*/
public class Doono extends Guy implements Named {
	@Override
	public String getName() {
		return "Doono";
	}

	@Override
	public String describeSituation() { 
		if (Math.random() < .5)
			return "Boy, you're doing it wrong!";
		return "Do you know what you're doing with your life?";
	}

	/**
	* @brief Unique ability
	* @return Exactly what you're expecting
	*/
	public final String messUp(Work work) { 
		return work.getDescription() + " is done exceptionally well";
	}

	@Override
	public String toString() {
		return "Guy that know a lot of stuff";
	}

	/**
	* @brief Creates Doono with a certan position and orientation
	* @param position
	* @param orientation
	*/
	public Doono(StatePair position, StatePair orientation) {
		this.position    = new StatePair(position);
		this.orientation = new StatePair(orientation);
	}

}

