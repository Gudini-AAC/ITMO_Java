package paths;
import java.util.ArrayList;
import linalg.Vec3;
import paths.Path;

public class ManhattanPath implements Path {
	public ManhattanPath(float gridSize) {
		this.gridSize = gridSize;
		this.points = new ArrayList<Vec3>();
	}

	@Override
	public Vec3 interpolate(float t) {
		float aim = length() * t;

		int i = 0;
		float len = 0.f;
		while (len < aim && i < points.size()) {
			i++;
			len += manhattanLength(Vec3.sub(points.get(i - 1), points.get(i)));	
		}
		
		return points.get(i - 1);
	}

	@Override
	public float length() {
		float ret = 0.f;
		for (int i = 1; i < points.size(); i++)
			ret += manhattanLength(Vec3.sub(points.get(i - 1), points.get(i)));
		return ret;
	}

	public void addPoint(Vec3 point) {
		Vec3 pointOnGrid = new Vec3();

		pointOnGrid.setX(point.getX() - point.getX() % gridSize);
		pointOnGrid.setY(point.getY() - point.getY() % gridSize);
		pointOnGrid.setZ(point.getZ() - point.getZ() % gridSize);

		points.add(pointOnGrid);
	}

	private float manhattanLength(Vec3 vec) {
		float ret = 0.f;
		ret += Math.abs(vec.getX());
		ret += Math.abs(vec.getY());
		ret += Math.abs(vec.getZ());
		return ret;
	}

	private float gridSize;
	private ArrayList<Vec3> points;
}
