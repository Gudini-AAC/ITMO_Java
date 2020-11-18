package linalg;
import linalg.Vec3;

/**
* @brief Represents value and its derivative in a neat little package
*/
public class StatePair {

	/**
	* @brief Default, zero-initialized pair of vectors
	*/
	public StatePair() {
		value      = new Vec3();
		derivative = new Vec3();
	}

	/**
	* @brief Copy constructor
	* @param other State pair to copy
	*/
	public StatePair(StatePair other) {
		value      = new Vec3(other.value);
		derivative = new Vec3(other.derivative);
	}

	/**
	* @brief Construct pair from two vectors
	* @param value
	* @param derivative
	*/
	public StatePair(Vec3 value, Vec3 derivative) {
		this.value      = new Vec3(value);
		this.derivative = new Vec3(derivative);
	}

	/**
	* @param dt Delta of time
	* @brief Integrates derivative by dt
	*/
	public void integrate(float dt) {
		value.add(Vec3.mul(derivative, dt));
	}

	/**
	* @brief Creates StatePair with random components
	* @param min Maximal value of the component
	* @param max Minimal value of the component
	* @return StatePair with a random components
	*/
	public static StatePair makeRandom(float min, float max) {
		StatePair ret = new StatePair();
		ret.value      = Vec3.makeRandom(min, max);
		ret.derivative = Vec3.makeRandom(min, max);
		return ret;
	}

	public Vec3 getValue() { return value; }
	public void setValue(Vec3 value) { this.value = new Vec3(value); }

	public Vec3 getDerivative() { return derivative; }
	public void setDerivative(Vec3 derivative) { this.derivative = new Vec3(derivative); }

	private Vec3 value;
	private Vec3 derivative;

}
