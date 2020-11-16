package world;
import common.CardinalDirection;
import linalg.Vec3;

public interface Directed {

	public CardinalDirection resolveDirection();
	public Vec3 resolveDirectionVector();

}