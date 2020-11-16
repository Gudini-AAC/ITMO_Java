package linalg;
import java.lang.Math;

public class Vec3 {
	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}	
	
	public Vec3() {
		this.x = 0.f;
		this.y = 0.f;
		this.z = 0.f;
	}

	public Vec3(Vec3 other) {
		x = other.x;
		y = other.x;
		z = other.x;
	}
	
 	public final void add(Vec3 other) {
		x += other.x;
		y += other.y;
		z += other.z;
	}

	public final void sub(Vec3 other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
	}

	public final void mul(float factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}

	public final void div(float factor) {
		factor = 1.f / factor;
		mul(factor);
	}

	public static final Vec3 add(Vec3 lh, Vec3 rh) {
		Vec3 ret = new Vec3(lh);

		ret.x += rh.x;
		ret.y += rh.y;
		ret.z += rh.z;

		return ret;
	}

	public static final Vec3 sub(Vec3 lh, Vec3 rh) {
		Vec3 ret = new Vec3(lh);

		ret.x -= rh.x;
		ret.y -= rh.y;
		ret.z -= rh.z;

		return ret;
	}

	public static final Vec3 mul(Vec3 lh, float factor) {
		Vec3 ret = new Vec3(lh);

		ret.x *= factor;
		ret.y *= factor;
		ret.z *= factor;

		return ret;
	}

	public static final Vec3 div(Vec3 lh, float factor) {
		factor = 1.f / factor;

		return mul(lh, factor);
	}

	public static final float dot(Vec3 lh, Vec3 rh) {
		return lh.x * rh.x + lh.y * rh.y + lh.z * rh.z;
	}

	public static final Vec3 cross(Vec3 lh, Vec3 rh) {
		Vec3 ret = new Vec3();

		ret.x = lh.y * rh.z - rh.y * lh.z;
		ret.y = lh.z * rh.x - rh.z * lh.x;
		ret.z = lh.x * rh.y - rh.x * lh.y;

		return ret;
	}

	public final void normalize() {
		div((float)Math.sqrt(x * x + y * y + z * z));
	}

	public final float getX() { return x; }
	public final float getY() { return y; }
	public final float getZ() { return z; }
	
	public final void setX(float x) { this.x = x; }
	public final void setY(float y) { this.y = y; }
	public final void setZ(float z) { this.z = z; }

	private float x;
	private float y;
	private float z;
}
