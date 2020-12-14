package guys;
import world.Object3D;
import guys.TooBigOfACrowdException;
import linalg.Vec3;
import world.Simulatable;
import world.Directed;
import linalg.StatePair;
import java.lang.Math;
import java.util.ArrayList;

/**
* @brief Represents a group of Homo Sapiens
*/
public class Crowd extends Object3D implements Simulatable {

	@Override
	public void tick(float dt) {
		position.integrate(dt);
		orientation.integrate(dt);

		for (Simulatable guy : guys)
			guy.tick(dt);
	}

	@Override
	public String toString() {
		return String.format("Crowd of %d guys", guys.size());
	}

	/**
	* @brief Adds a guy to the crowd
	* @param guy Guy to add
	* @throws TooBigOfACrowdException if crowd is too big
	*/
	public void addGuy(Guy guy) throws TooBigOfACrowdException {
		if (guys.size() == 10)
			throw new TooBigOfACrowdException();
		guys.add(guy);
	}

	public String shoutOutLoud() {
		int index = (int)(Math.random() * guys.size()) % guys.size();

		return guys.get(index).describeSituation();
	}

	/**
	* @brief Default-initialized zero-sized crowd
	*/
	public Crowd() {
		position    = StatePair.makeRandom(-3.14f, 3.14f);
		orientation = StatePair.makeRandom(-3.14f, 3.14f);

		guys = new ArrayList<Guy>();
	}

	private ArrayList<Guy> guys;
}

