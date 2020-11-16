package paths;
import java.util.ArrayList;
import linalg.Vec3;
import paths.Path;

public class BezierPath implements Path {
	public BezierPath() {
		this.points = new ArrayList<Vec3>();
	}

	@Override
	public Vec3 interpolate(float t) {
		if (t == 0.f) return points.get(0);

		float aim = calculateLength() * t;

		int i = 0;
		float len = 0.f;
		while(len < aim && i < points.size()) {
			i++;
			len += Vec3.sub(points.get(i), points.get(i - 1)).length();
		}

		Vec3 currentSegment = Vec3.sub(points.get(i), points.get(i - 1));
		float currentSegmentLen = currentSegment.length();
		currentSegment.mul((aim + currentSegmentLen - len) / currentSegmentLen);

		return Vec3.add(points.get(i - 1), currentSegment);
	}

	@Override
	public float length() {
		return calculateLength();
	}

	@Override
	public void addPoint(Vec3 point) {
		points.add(point);
	}

	private float calculateLength() {
		float ret = 0.f;
		for (int i = 1; i < points.size(); i++)
			ret += Vec3.sub(points.get(i), points.get(i - 1)).length();
		return ret;
	}

	private ArrayList<Vec3> points;

}
