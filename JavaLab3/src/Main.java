import world.World;
import vehicles.Balloon;
import vehicles.Vehicle;
import guys.Grunt;
import guys.Dunno;
import guys.Doono;
import guys.RolyPoly;
import guys.GlassEye;
import guys.Scatterbrain;
import guys.Posey;
import guys.Guy;
import guys.Crowd;
import guys.TooBigOfACrowdException;
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
			path.addPoint(new Vec3(1, 1, 999));
			path.addPoint(new Vec3(1, 3, 1010));
			path.addPoint(new Vec3(1, 3, 6000));
			path.addPoint(new Vec3(10, 1, 9000));

			Vehicle balloon = new Balloon(new StatePair(new Vec3(), new Vec3(1, 1, 0)), new StatePair());
			balloon.followPath(path);
			boolean allAreSitting = true;
			allAreSitting |= balloon.trySit(new Dunno(new StatePair(), new StatePair()));
			allAreSitting |= balloon.trySit(new RolyPoly(new StatePair(), new StatePair()));
			System.out.print(allAreSitting ? "All of the guys are in the balloon\n" : "Guys are out of the balloon\n");
			world.addWorldObject(balloon);
		}

		{
			Path path = new BezierPath();
			path.addPoint(new Vec3(10, 1, 9000));
			path.addPoint(new Vec3(1, 3, 6000));
			path.addPoint(new Vec3(1, 3, 1001));
			path.addPoint(new Vec3(1, 1, 999));

			Vehicle balloon = new Balloon(new StatePair(new Vec3(), new Vec3(-1, 0, 1000)), new StatePair());
			balloon.followPath(path);
			boolean allAreSitting = true;
			allAreSitting |= balloon.trySit(new Guy("Tinkle"));
			allAreSitting |= balloon.trySit(new Guy("PeeWee"));
			allAreSitting |= balloon.trySit(new Guy("Swifty"));
			allAreSitting |= balloon.trySit(new Guy("Daisy"));
			allAreSitting |= balloon.trySit(new Guy("TreaclySweeter"));
			allAreSitting |= balloon.trySit(new Guy("Nebos'ka"));
			allAreSitting |= balloon.trySit(new Guy("Avos'ka"));
			System.out.print(allAreSitting ? "All of the guys are in the balloon\n" : "Guys are out of the balloon\n");
			world.addWorldObject(balloon);
		}

		{
			Crowd crowd = new Crowd();	

			try {
				crowd.addGuy(new Guy());
				crowd.addGuy(new Guy());
				crowd.addGuy(new Guy());
				crowd.addGuy(new GlassEye(new StatePair(), new StatePair(new Vec3(), new Vec3(0, 0, 1))));
			} catch (TooBigOfACrowdException e) {
				System.out.println(e.getMessage());
			}
			world.addWorldObject(crowd);
		}

		world.addWorldObject(new Grunt(new StatePair(), new StatePair(new Vec3(), new Vec3(0, 0, 1))));
		world.addWorldObject(new Dunno(new StatePair(), new StatePair(new Vec3(), new Vec3(0, 0, 1))));
		world.addWorldObject(new Doono(new StatePair(), new StatePair(new Vec3(), new Vec3(0, 0, 1))));
		world.addWorldObject(new RolyPoly(new StatePair(), new StatePair(new Vec3(), new Vec3(0, 0, 1))));
		world.addWorldObject(new Scatterbrain(new StatePair(), new StatePair(new Vec3(), new Vec3(0, 0, 1))));
		world.addWorldObject(new Posey(new StatePair(), new StatePair(new Vec3(), new Vec3(0, 0, 1))));
		world.addWorldObject(new Guy("Topick"));
		world.run(15.f, 0.2f);

	}

}