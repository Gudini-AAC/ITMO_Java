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
	* @brief Position related life stats
	* @return Life stats
	*/
	public String lifeStats() {
		float height = position.getValue().getZ();

		if (height > 1e5)
			return "It's like vaccume here, imma dead :(";
		else if (height > 1.5e4)
			return "It's dead cold out there, my pants are full of ice";
		else if (height > 1e3)
			return "It's kinda cold out there";
		else if (height > 5e2)
			return "Cant's see anything because of clouds";

		return "Everything looks beautiful!";
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
		if (name != null)
			return name;

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

	/**
	* @brief Mostly not interesting, but named guy that looks at south and has random position and orientation
	*/
	public Guy(String name) {
		this();
		this.name = name;
	}

	private String name = null;
	protected Vec3 eyeDirection;
}

