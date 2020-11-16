package vehicles;
import world.Object3D;
import guys.Guy;
import world.Simulatable;
import world.Directed;
import paths.Path;
import java.util.Optional;

public abstract class Vehicle extends Object3D implements Simulatable {
	public abstract boolean trySit(Guy guy);
	public abstract Optional<Guy> kickOutLast();
	public abstract boolean isFollowingPath();
	public abstract void followPath(Path path);

	protected Guy[] guys;
	protected int guysCount;
}

