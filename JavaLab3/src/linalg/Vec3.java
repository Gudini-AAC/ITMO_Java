package linalg;
import java.lang.Math;

/**
* @brief General class of 3 dimentional point or vector
*/
public class Vec3 {

	/**
	* @param x First component of the vector
	* @param y Second component of the vector
	* @param z Third component of the vector
	*/
	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}	
	
	/**
	* @brief Default, zero-initialized vector
	*/
	public Vec3() {
		this.x = 0.f;
		this.y = 0.f;
		this.z = 0.f;
	}

	/**
	* @brief Copy constructor
	* @param other Vector to copy
	*/
	public Vec3(Vec3 other) {
		x = other.x;
		y = other.y;
		z = other.z;
	}
	
	/**
	* @brief Adds up two vectors and stores result in the first one
	* @param other Vector to add
	*/
	public final void add(Vec3 other) {
		x += other.x;
		y += other.y;
		z += other.z;
	}

	/**
	* @brief Subtracts two vectors and stores result in the first one
	* @param other Vector to add
	*/
	public final void sub(Vec3 other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
	}

	/**
	* @brief Scales vector by a certan factor
	* @param other Factor by witch vector is scaled
	*/
	public final void mul(float factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}

	/**
	* @brief Divides vector by a certan factor
	* @param other Factor by witch vector is divided
	*/
	public final void div(float factor) {
		factor = 1.f / factor;
		mul(factor);
	}

	/**
	* @brief Adds up two vectors and returns result as new vector
	* @param lh Left hand vector to add
	* @param rh Right hand vector to add
	* @return Sum of the two vectors
	*/
	public static final Vec3 add(Vec3 lh, Vec3 rh) {
		Vec3 ret = new Vec3(lh);

		ret.x += rh.x;
		ret.y += rh.y;
		ret.z += rh.z;

		return ret;
	}

	/**
	* @brief Subtracts two vectors and returns result as new vector
	* @param lh Left hand vector to subtract
	* @param rh Right hand vector to subtract
	* @return Difference of the two vectors
	*/
	public static final Vec3 sub(Vec3 lh, Vec3 rh) {
		Vec3 ret = new Vec3(lh);

		ret.x -= rh.x;
		ret.y -= rh.y;
		ret.z -= rh.z;

		return ret;
	}

	/**
	* @brief Scales vector by a certan factor
	* @param lh Left hand vector to multiply
	* @param factor Factor by witch vector is scaled
	* @return Scaled vector
	*/
	public static final Vec3 mul(Vec3 lh, float factor) {
		Vec3 ret = new Vec3(lh);

		ret.x *= factor;
		ret.y *= factor;
		ret.z *= factor;

		return ret;
	}

	/**
	* @brief Divides vector by a certan factor
	* @param lh Left hand vector to divide
	* @param factor Factor by witch vector is divided
	* @return Divided vector
	*/
	public static final Vec3 div(Vec3 lh, float factor) {
		factor = 1.f / factor;

		return mul(lh, factor);
	}

	/**
	* @brief Calculates dot product of two vectors
	* @param lh Left hand vector
	* @param rh Right hand vector
	* @return Dot product of two vectors
	*/
	public static final float dot(Vec3 lh, Vec3 rh) {
		return lh.x * rh.x + lh.y * rh.y + lh.z * rh.z;
	}

	/**
	* @brief Calculates cross product of two vectors
	* @param lh Left hand vector
	* @param rh Right hand vector
	* @return Cross product of two vectors
	*/
	public static final Vec3 cross(Vec3 lh, Vec3 rh) {
		Vec3 ret = new Vec3();

		ret.x = lh.y * rh.z - rh.y * lh.z;
		ret.y = lh.z * rh.x - rh.z * lh.x;
		ret.z = lh.x * rh.y - rh.x * lh.y;

		return ret;
	}

	/**
	* @brief Normalizes vector
	*/
	public final void normalize() {
		div((float)Math.sqrt(x * x + y * y + z * z));
	}

	/**
	* @return Length of the vector
	*/
	public final float length() {
		return (float)Math.sqrt(x * x + y * y + z * z);
	}

	/**
	* @brief Creates vector with random components
	* @param min Maximal value of the component
	* @param max Minimal value of the component
	* @return Vector with a random components
	*/
	public static final Vec3 makeRandom(float min, float max) {
		Vec3 ret = new Vec3();
		float amplitude = max - min;
		ret.x = (float)Math.random() * amplitude + min;
		ret.y = (float)Math.random() * amplitude + min;
		ret.z = (float)Math.random() * amplitude + min;
		return ret;
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
