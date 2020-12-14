package guys;
import world.Named;
import world.Work;
import guys.Guy;
import linalg.Vec3;
import linalg.StatePair;
import java.lang.Math;

/**
* @brief Named guy without any knowledge
*/
public class Dunno extends Guy implements Named {
	@Override
	public String getName() {
		return "Dunno";
	}

	@Override
	public String describeSituation() { 
		if (Math.random() < .5)
			return "I know everything! (Not really)";
		return "I'm the smartest guy in the simulation! (Not really)";
	}

	/**
	* @brief The only thing that he's able to do
	* @return Exactly what you're expecting - messed up work
	*/
	public final String messUp(Work work) { 
		return work.doWork() + " " + work.getDescription() + " successfully messed up";
	}

	@Override
	public String toString() {
		return "Guy that doesn't know anything";
	}

	/**
	* @brief Creates Dunno with a certan position and orientation
	* @param position
	* @param orientation
	*/
	public Dunno(StatePair position, StatePair orientation) {
		this.position    = new StatePair(position);
		this.orientation = new StatePair(orientation);
	}

}

