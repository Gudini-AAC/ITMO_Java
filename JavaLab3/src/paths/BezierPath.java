package paths;
import java.util.ArrayList;
import linalg.Vec3;
import paths.Path;

/**
* @brief Spline-like path
*/
public class BezierPath implements Path {
	
	/**
	* @brief Default-initialized emplty path
	*/
	public BezierPath() {
		this.points = new ArrayList<Vec3>();
	}

	@Override
	public Vec3 interpolate(float t) {
		if (t == 0.f) // Start of the path
			return points.get(0);

		float aim = length() * t;

		int i = 0; // Index of the next point
		float len = 0.f; // Length of the path up to next point
		while(len < aim && i < points.size()) { // Trivial calculations of the length
			i++;
			len += Vec3.sub(points.get(i), points.get(i - 1)).length();
		}

		// Interpolate current segment
		Vec3 currentSegment = Vec3.sub(points.get(i), points.get(i - 1));
		float currentSegmentLen = currentSegment.length();
		currentSegment.mul((aim + currentSegmentLen - len) / currentSegmentLen);

		// Return interpolated point
		return Vec3.add(points.get(i - 1), currentSegment);
	}

	@Override
	public float length() {
		float ret = 0.f;
		for (int i = 1; i < points.size(); i++)
			ret += Vec3.sub(points.get(i), points.get(i - 1)).length();
		return ret;
	}

	@Override
	public void addPoint(Vec3 point) {
		points.add(point);
	}

	private ArrayList<Vec3> points;

}
