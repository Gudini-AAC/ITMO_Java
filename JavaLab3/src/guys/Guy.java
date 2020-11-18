package guys;
import world.Object3D;
import linalg.Vec3;
import world.Simulatable;
import world.Directed;
import linalg.StatePair;
import common.CardinalDirection;
import java.lang.Math;

/**
* @brief Represents Homo Sapiens in this rude world
*/
public class Guy extends Object3D implements Directed, Simulatable {

	/**
	* @return String that expresses current ideas of the creature about the world
	*/
	public String describeSituation() { 
		if (Math.random() < .5)
			return "Amma just a usual guy";
		return "Life is not that bad";
	}

	/**
	* @param point Object in the world to look at
	* @return Eye vector
	*/
	public Vec3 lookAt(Object3D point) {
		return Vec3.sub(point.getPosition().getValue(), position.getValue());
	}

	@Override
	public void tick(float dt) {
		position.integrate(dt);
		orientation.integrate(dt);
	}

	@Override
	public CardinalDirection resolveDirection() {
		Vec3 direction = orientation.getValue();
		double heading = Math.atan2(direction.getY(), direction.getX());
		heading /= Math.PI;
		if (heading < .0) heading += 2.;
		heading += .125;
		heading *= 4.;
		
		int headingIndex = (int)heading;
		return CardinalDirection.fromIndex(headingIndex % 8);
	}

	@Override
	public Vec3 resolveDirectionVector() {
		return orientation.getValue();
	}

	@Override
	public String toString() {
		if (Math.random() < .5)
			return "Regular one";
		return "Nobody";
	}

	/**
	* @brief Default-initialized guy that looks at south and has random position and orientation
	*/
	public Guy() {
		eyeDirection = new Vec3(1, 0, 0);
		position     = StatePair.makeRandom(-3.14f, 3.14f);
		orientation  = StatePair.makeRandom(-3.14f, 3.14f);
	}

	protected Vec3 eyeDirection;
}

