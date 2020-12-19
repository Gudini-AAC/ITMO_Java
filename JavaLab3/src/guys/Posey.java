package guys;
import world.Named;
import world.Work;
import world.Object3D;
import guys.Guy;
import linalg.Vec3;
import linalg.StatePair;
import java.lang.Math;

/**
* @brief Guy named after a flower
*/
public class Posey extends Guy implements Named {
	@Override
	public String getName() {
		return "Posey";
	}

	@Override
	public String describeSituation() { 
		if (Math.random() < .5)
			return "I've made a new cool poem!";
		return "Listen! I've made a new poem";
	}

	private static class Quatrain {
		Quatrain(String name) {
			body = "\n";
			for (int i = 0; i < 4; i++) {
				String formatString = dictionary[(int)(Math.random() * dictionary.length) %dictionary.length];
				body += "\t" + String.format(formatString, name);
				if (i < 3) body += '\n';
			}
		}

		String getBody() {
			return body;
		}

		private String body;
	}

	/**
	* @brief Makes poetry about an object
	* @param Object to write poem about
	* @return Couple of quatrains
	*/
	public final String makePoetryAbout(Object3D object) { 
		String name;
		if (object instanceof Named) {
			name = ((Named)object).getName();
		} else {
			name = object.toString();
		}

		String ret = "";
		int poemSize = (int)(Math.random() * 3);
		for (int i = 0; i < poemSize; i++) {
			Quatrain quatrain = new Quatrain(name);
			ret += quatrain.getBody();
			if (i < poemSize - 1) ret += '\n';
		}

		return ret;
	}

	@Override
	public String toString() {
		return "Guy likes flowers";
	}

	/**
	* @brief Creates Posey with a certan position and orientation
	* @param position
	* @param orientation
	*/
	public Posey(StatePair position, StatePair orientation) {
		this.position    = new StatePair(position);
		this.orientation = new StatePair(orientation);
	}

	static final String[] dictionary = {
		"%s is a cool guy",
		"%s is a best thing",
		"%s is a thing of beauty",
		"And a joy forever"
	};
}

