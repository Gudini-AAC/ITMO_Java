package vehicles;
import world.Object3D;
import guys.Guy;
import world.Simulatable;
import world.Directed;
import paths.Path;
import java.util.Optional;

/**
* @brief 3 dimentional simulateble object that represents a vehicle
*/
public abstract class Vehicle extends Object3D implements Simulatable {

	/**
	* @brief Trys to put a guy in the vehicle
	* @param guy Guy to puth in the vehicle
	* @return Success boolean
	*/
	public abstract boolean trySit(Guy guy);

	/**
	* @brief Trys to kick the last guy out of the vehicle
	* @return Optional guy that's been kicked out
	*/
	public abstract Optional<Guy> kickOutLast();

	/**
	* @brief Check if the vehicle is following path
	* @return State boolean
	*/
	public abstract boolean isFollowingPath();

	/**
	* @brief Checks everybodys opinion and gives a common opinion
	* @return Common opinion
	*/
	public abstract String passengerOpinions();

	/**
	* @brief Checks vehicle stats
	* @return Vehicle stats
	*/
	public abstract String vehicleStats();

	/**
	* @brief Sets a path to follow
	* @param path Patn to follow
	*/
	public abstract void followPath(Path path);

	protected Guy[] guys;
	protected int guysCount;
}

