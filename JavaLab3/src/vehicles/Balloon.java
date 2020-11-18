package vehicles;
import vehicles.FlyingVehicle;
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
			}
			else { //Path is finished
				newPosition = currentPath.interpolate(1.f);
				currentPath = null;
			}

			position.setValue(newPosition);
		}
		else { //Just integrate the position
			position.setValue(Vec3.mul(Vec3.sub(position.getDerivative(), windSpeed), dt));
		}

		orientation.integrate(dt);
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

		return true;
	}

	@Override
	public Optional<Guy> kickOutLast() {
		if (guysCount == 0)
			return Optional.empty();
 
		return Optional.of(guys[guysCount--]);
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
	* @brief Creates Balloon with a certan position and orientation
	* @param position
	* @param orientation
	*/
	public Balloon(StatePair position, StatePair orientation) {
		this.position    = new StatePair(position);
		this.orientation = new StatePair(orientation);
						 
		this.windSpeed   = new Vec3();
		this.guys        = new Guy[MAX_GUYS];
		this.guysCount   = 0;
		this.flameColor  = Color.ORANGE;
		this.currentPath = null;
	}

	private Path currentPath;
	private float currentPathTime;
	private float totalPathTime;

	private Vec3 windSpeed;
	private Color flameColor;
	private static final int MAX_GUYS = 12;
}

