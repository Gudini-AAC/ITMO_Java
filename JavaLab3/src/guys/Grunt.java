package guys;
import world.Object3D;
import guys.Guy;
import linalg.StatePair;

public class Grunt extends Guy {
	@Override
	public void tick(float dt) {}

	@Override
	public String describeSituation() { 
		return "It's a disaster!";
	}

	public final String grumble() { 
		return "*Grumbling sounds*";
	}

	public Grunt() {
		position    = new StatePair();
		orientation = new StatePair();
	}

}

