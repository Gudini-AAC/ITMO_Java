package vehicles;
import vehicles.FlyingVehicle;
import guys.Guy;
import linalg.Vec3;
import linalg.StatePair;
import java.util.Optional;
import common.Color;

public class Balloon extends FlyingVehicle {

	@Override
	public void tick(float dt) {
		Vec3 deltaPosition = Vec3.mul(position.getDerivative(), dt);
		Vec3 newPosition = Vec3.add(position.getValue(), deltaPosition);
		position.setValue(newPosition);
		System.out.print("Hel dat shit works!\n");
	}

	@Override
	public boolean trySit(Guy guy) {
		if (guysCount == MAX_GUYS)
			return false;

		guys[guysCount++] = guy;

		return true;
	}

	@Override
	public Optional<Guy> kickOutLast() {
		if (guysCount == 0)
			return Optional.empty();
 
		return Optional.of(guys[guysCount--]);
	}

	@Override
	public void setWind(Vec3 windSpeed) {
		this.windSpeed = new Vec3(windSpeed);
	}

	public Balloon(StatePair position, StatePair orientation) {
		this.position    = new StatePair(position);
		this.orientation = new StatePair(orientation);
						 
		this.windSpeed  = new Vec3();
		this.guys       = new Guy[MAX_GUYS];
		this.guysCount  = 0;
		this.flameColor = Color.ORANGE;
	}

	private Vec3 windSpeed;
	private Color flameColor;
	private static final int MAX_GUYS = 12;
}

