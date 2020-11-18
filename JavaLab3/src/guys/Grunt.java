package guys;
import world.Object3D;
import world.Named;
import guys.Guy;
import linalg.Vec3;
import linalg.StatePair;
import java.lang.Math;

/**
* @brief Named guy that has quite an interesting temperament
*/
public class Grunt extends Guy implements Named {
	@Override
	public String getName() {
		return "Grunt";
	}

	@Override
	public String describeSituation() { 
		if (Math.random() < .5)
			return "It's a disaster!";
		return "Life will never be the same as it used to be";
	}

	/**
	* @brief This is the main point of existance of this guy
	* @return Exactly what you are expecting
	*/
	public final String grumble() { 
		return "*Grumbling sounds*";
	}

	@Override
	public String toString() {
		return "Cool guy with panama by the name of Grunt";
	}

	/**
	* @brief Creates Grunt with a certan position and orientation
	* @param position
	* @param orientation
	*/
	public Grunt(StatePair position, StatePair orientation) {
		this.position    = new StatePair(position);
		this.orientation = new StatePair(orientation);
	}

}

