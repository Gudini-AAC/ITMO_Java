package vehicles;
import vehicles.Vehicle;
import linalg.Vec3;

public abstract class FlyingVehicle extends Vehicle {
	public abstract void setWind(Vec3 direction);
}

