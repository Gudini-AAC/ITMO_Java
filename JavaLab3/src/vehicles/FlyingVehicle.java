package vehicles;
import vehicles.Vehicle;
import linalg.Vec3;

/**
* @brief Flying specialization of the vehicle 
*/
public abstract class FlyingVehicle extends Vehicle {

	/**
	* @brief Sets a wind acting on the vehicle
	* @param direction Direction of the wind
	*/
	public abstract void setWind(Vec3 direction);
}

