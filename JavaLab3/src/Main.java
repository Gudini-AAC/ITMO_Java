import world.World;
import vehicles.Balloon;
import guys.Grunt;
import linalg.Vec3;
import linalg.StatePair;

public class Main {

	public static void main(String[] args) {

		World world = new World();
		world.addWorldObject(new Balloon(new StatePair(), new StatePair()));
		world.addWorldObject(new Grunt());
		world.run(5.f, 0.2f);

	}

}