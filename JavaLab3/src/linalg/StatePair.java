package linalg;
import linalg.Vec3;


public class StatePair {
	public StatePair() {
		value = new Vec3();
		derivative = new Vec3();
	}

	public StatePair(StatePair other) {
		value = new Vec3(other.value);
		derivative = new Vec3(other.derivative);
	}

	public StatePair(Vec3 value, Vec3 derivative) {
		this.value = new Vec3(value);
		this.derivative = new Vec3(derivative);
	}

	public Vec3 getValue() { return value; }
	public void setValue(Vec3 value) { this.value = new Vec3(value); }

	public Vec3 getDerivative() { return derivative; }
	public void setDerivative(Vec3 derivative) { this.derivative = new Vec3(derivative); }

	public void integrate(float dt) {
		value.add(Vec3.mul(derivative, dt));
	}

	private Vec3 value;
	private Vec3 derivative;

}
