package paths;
import linalg.Vec3;

public interface Path {
	public Vec3 interpolate(float t);
	public void addPoint(Vec3 point);
	public float length();
}
