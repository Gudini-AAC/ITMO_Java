package paths;
import linalg.Vec3;

/**
* @brief Interface of path with a usual functionalities
*/
public interface Path {

	/**
	* @biref Finds point on a path that corresponds to a percentage paremeter t
	* @param t Percentage of the path
	* @return Point on the path
	*/
	public Vec3 interpolate(float t);

	/**
	* @brief Add point to the path
	* @param point Point to add
	*/
	public void addPoint(Vec3 point);

	/**
	* @brief Claculate length of the path
	* @return Length of the path
	*/
	public float length();
}
