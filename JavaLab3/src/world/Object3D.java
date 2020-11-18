package world;
import linalg.StatePair;

/**
* @brief 3 dimensional object in space with position and orientation
*/
public abstract class Object3D {
	public StatePair getPosition() { return position; }
	public void setPosition(StatePair position) { this.position = new StatePair(position); }

	public StatePair getOrientation()  { return orientation; }
	public void setOrientation(StatePair orientation) { this.orientation = new StatePair(orientation); }

	protected StatePair position;
	protected StatePair orientation;
}

