import world.World;
import vehicles.Balloon;
import vehicles.Vehicle;
import guys.Grunt;
import guys.Dunno;
import guys.GlassEye;
import guys.Guy;
import linalg.Vec3;
import linalg.StatePair;
import paths.Path;
import paths.BezierPath;
import paths.ManhattanPath;

public class Main {

	public static void main(String[] args) {

		World world = new World();

		{
			Path path = new ManhattanPath(2);
			path.addPoint(new Vec3(1, 1, 1));
			path.addPoint(new Vec3(1, 3, 1));
			path.addPoint(new Vec3(1, 3, 6));
			path.addPoint(new Vec3(10, 1, 9));

			Vehicle balloon = new Balloon(new StatePair(new Vec3(), new Vec3(1, 1, 0)), new StatePair());
			balloon.followPath(path);
			world.addWorldObject(balloon);
		}

		{
			Path path = new BezierPath();
			path.addPoint(new Vec3(10, 1, 9));
			path.addPoint(new Vec3(1, 3, 6));
			path.addPoint(new Vec3(1, 3, 1));
			path.addPoint(new Vec3(1, 1, 1));

			Vehicle balloon = new Balloon(new StatePair(new Vec3(), new Vec3(-1, 0, 0)), new StatePair());
			balloon.followPath(path);
			boolean allAreSitting = true;
			allAreSitting |= balloon.trySit(new Guy());
			allAreSitting |= balloon.trySit(new Guy());
			System.out.print(allAreSitting ? "All of the guys are in the balloon\n" : "Guys are out of the balloon\n");
			world.addWorldObject(balloon);
		}

		world.addWorldObject(new Grunt(new StatePair(), new StatePair(new Vec3(), new Vec3(0, 0, 1))));
		world.addWorldObject(new Dunno(new StatePair(), new StatePair(new Vec3(), new Vec3(0, 0, 1))));
		world.addWorldObject(new GlassEye(new StatePair(), new StatePair(new Vec3(), new Vec3(0, 0, 1))));
		world.addWorldObject(new Guy());
		world.addWorldObject(new Guy());
		world.addWorldObject(new Guy());
		world.run(15.f, 0.2f);

	}

}