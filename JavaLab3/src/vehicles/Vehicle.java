package vehicles;
import world.Object3D;
import guys.Guy;
import java.util.Optional;

public abstract class Vehicle extends Object3D {
	public abstract boolean trySit(Guy guy);
	public abstract Optional<Guy> kickOutLast();

	protected Guy[] guys;
	protected int guysCount;
}

