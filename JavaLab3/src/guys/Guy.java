package guys;
import world.Object3D;
import linalg.Vec3;
import world.Simulatable;
import world.Directed;

public abstract class Guy extends Object3D implements Directed, Simulatable {
	public String describeSituation() { return "Amma just a guy!"; }

	public Vec3 lookAt(Object3D point) {
		return Vec3.sub(point.getPosition().getValue(), position.getValue());
	}

	protected Vec3 eyeDirection;
}

