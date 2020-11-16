import world.World;
import vehicles.Balloon;
import vehicles.Vehicle;
import guys.Grunt;
import linalg.Vec3;
import linalg.StatePair;
import paths.Path;
import paths.BezierPath;

public class Main {

	public static void main(String[] args) {

		World world = new World();

		Path path = new BezierPath();
		path.addPoint(new Vec3(1, 1, 1));
		path.addPoint(new Vec3(1, 3, 1));
		path.addPoint(new Vec3(1, 3, 6));
		path.addPoint(new Vec3(12, 1, 9));

		Vehicle balloon = new Balloon(new StatePair(new Vec3(), new Vec3(1, 1, 0)), new StatePair());
		balloon.followPath(path);

		world.addWorldObject(balloon);
		world.addWorldObject(new Grunt());
		world.run(15.f, 0.2f);

	}

}