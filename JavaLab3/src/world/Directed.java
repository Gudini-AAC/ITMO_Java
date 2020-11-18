package world;
import common.CardinalDirection;
import linalg.Vec3;

/**
* @brief Gives access to an entity with a distinct direction in the world frame 
*/
public interface Directed {
	
	/**
	* @return Cardinal direction of the entity
	*/
	public CardinalDirection resolveDirection();

	/**
	* @return Direction vector
	*/
	public Vec3 resolveDirectionVector();

}