package guys;
import world.Object3D;
import world.Named;
import guys.Guy;
import linalg.StatePair;
import linalg.Vec3;
import common.CardinalDirection;
import java.lang.Math;

public class Grunt extends Guy implements Named {
	@Override
	public void tick(float dt) {}

	@Override
	public String getName() {
		return "Grunt";
	}

	@Override
	public String describeSituation() { 
		return "It's a disaster!";
	}

	@Override
	public CardinalDirection resolveDirection() {
		Vec3 direction = orientation.getValue();
		double heading = Math.atan2(direction.getY(), direction.getX());
		heading *= 1. / Math.PI ;
		if (heading < .0) heading += 2.;
		heading += .125;
		heading *= 4.;
		
		int headingIndex = (int)heading;
		return CardinalDirection.fromIndex(headingIndex);
	}

	@Override
	public Vec3 resolveDirectionVector() {
		return orientation.getValue();
	}

	public final String grumble() { 
		return "*Grumbling sounds*";
	}

	public Grunt() {
		position    = new StatePair();
		orientation = new StatePair();
	}

}

