package vehicles;
import vehicles.FlyingVehicle;
import vehicles.NoBagsToDropException;
import paths.Path;
import world.Named;
import guys.Guy;
import linalg.Vec3;
import linalg.StatePair;
import java.util.Optional;
import common.Color;

/**
* @brief A large bag filled with hot air
*/
public class Balloon extends FlyingVehicle implements Named {
	@Override
	public void tick(float dt) {

		if (currentPath != null) { //Check if we are following path
			Vec3 newPosition;
			currentPathTime += dt;

			if (currentPathTime < totalPathTime) { //Path is not finished yet
				newPosition = currentPath.interpolate(currentPathTime / totalPathTime);
			} else { //Path is finished
				newPosition = currentPath.interpolate(1.f);
				currentPath = null;
			}

			position.setValue(newPosition);
		} else { //Just integrate the position
			Vec3 speedVector = Vec3.sub(position.getDerivative(), windSpeed);
			speedVector.setZ(speedVector.getZ() - mass);
			position.setValue(Vec3.mul(speedVector, dt));
		}

		orientation.integrate(dt);

		for (int i = 0; i < guysCount; i++) {
			guys[i].setPosition(position);
			guys[i].setOrientation(orientation);
		}
	}

	@Override
	public String getName() {
		return "Balloon";
	}

	@Override
	public boolean trySit(Guy guy) {
		if (guysCount == MAX_GUYS)
			return false;

		guys[guysCount++] = guy;
		mass += 1.f;

		return true;
	}

	@Override
	public Optional<Guy> kickOutLast() {
		if (guysCount == 0)
			return Optional.empty();
		
		mass -= 1.f;
		return Optional.of(guys[--guysCount]);
	}

	@Override
	public void followPath(Path path) {
		currentPath = path;
		currentPathTime = 0.f;
		totalPathTime = path.length() / position.getDerivative().length();
	}

	@Override
	public boolean isFollowingPath() {
		return currentPath != null;
	}

	@Override
	public void setWind(Vec3 windSpeed) {
		this.windSpeed = windSpeed;
	}

	@Override
	public String toString() {
		return "Fancy flying machine";
	}

	@Override
	public String vehicleStats() {
		float height = position.getValue().getZ();

		if (height > 1e5)
			return "Desintegrated";
		else if (height > 1.5e4)
			return "Everything is coated with ice";
		else if (height > 1e3)
			return "Water starts condencing on the surfaces";

		return "Everything is ok";
	}

	@Override
	public String passengerOpinions() {
		String ret = "";

		for (int i = 0; i < guysCount; i++) {
			Guy guy = guys[i];

			String name;
			if (guy instanceof Named) {
				name = ((Named)guy).getName();
			} else {
				name = guy.toString();
			}	

			if (Math.random() < .7) {
				if (!ret.isEmpty()) ret += "\n\t";
				ret += name + " thinks that \"" + guy.lifeStats() + "\"";
			} else if (Math.random() < .7) {
				if (!ret.isEmpty()) ret += "\n\t";
				ret += name + " thinks that \"" + guy.describeSituation() + "\"";
			}
		}
		
		if (ret.isEmpty())
			return "Nothing really is happening";
		return ret;
	}

	/**
	* @brief Sets the color of the flame
	* @param color Color to set
	*/
	public void setFlameColor(Color color) {
		flameColor = color;
	}

	/**
	* @brief Retrieves the color of the flame
	* @return Color of the flame
	*/
	public Color getFlameColor() {
		return flameColor;
	}

	/**
	* @brief Drops one of a bags to reduce mass of the aircraft
	* @return Remaining mass
	* @throws NoBagsToDropException if there is no more bags to drop :-/
	*/
	public float dropBag() {
		if (mass > 1.f)
			return mass -= 1.f;

		throw new NoBagsToDropException();
	}

	/**
	* @brief Creates Balloon with a certan position and orientation
	* @param position
	* @param orientation
	*/
	public Balloon(StatePair position, StatePair orientation) {
		this.position    = new StatePair(position);
		this.orientation = new StatePair(orientation);
						 
		this.windSpeed   = new Vec3();
		this.mass        = 100.f;
		this.guys        = new Guy[MAX_GUYS];
		this.guysCount   = 0;
		this.flameColor  = Color.ORANGE;
		this.currentPath = null;
	}

	private Path currentPath;
	private float currentPathTime;
	private float totalPathTime;

	private Vec3 windSpeed;
	private float mass;
	private Color flameColor;
	private static final int MAX_GUYS = 12;
}

