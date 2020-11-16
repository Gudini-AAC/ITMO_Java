package world;
import java.util.ArrayList;
import java.lang.Math;
import world.Object3D;
import guys.Guy;
import world.Simulatable;
import linalg.Vec3;
import vehicles.Vehicle;

public class World {

	public World() {
		simulatableObjects = new ArrayList<Simulatable>();
		staticObjects = new ArrayList<Object3D>();
	}

	public void addWorldObject(Object3D object) {
		if (object instanceof Simulatable) 
			simulatableObjects.add((Simulatable)object);
		else 
			staticObjects.add(object);
			
	}

	public void run(float seconds, float dt) {

		for (float time = 0.f; time < seconds; time += dt) {

			for (Simulatable object : simulatableObjects) {
				object.tick(dt);

				String name;
				if (object instanceof Named)
					name = ((Named)object).getName();
				else
					name = object.toString();

				if (object instanceof Directed && Math.random() < .05) {
					String message = name + " is pointing at " + ((Directed)object).resolveDirection().toString();
					System.out.println(message);
				}

				if (object instanceof Guy && Math.random() < .05) {
					String message = name + " is here, and he thinks: \"" + ((Guy)object).describeSituation().toString() + "\".";
					System.out.println(message);
				}

				if (object instanceof Vehicle && ((Vehicle)object).isFollowingPath() && Math.random() < .05) {
					Vec3 position = ((Vehicle)object).getPosition().getValue();
					System.out.print(name + " is following path and now at position ");
					System.out.printf("X: %.3f Y: %.3f Z: %.3f\n", position.getX(), position.getY(), position.getZ());
				}

				if (object instanceof Vehicle && Math.random() < .05) {
					Vec3 position = ((Vehicle)object).getPosition().getValue();
					System.out.print(name + " is now at position ");
					System.out.printf("X: %.3f Y: %.3f Z: %.3f\n", position.getX(), position.getY(), position.getZ());
				}

			}

		}

	}

	private ArrayList<Simulatable> simulatableObjects;
	private ArrayList<Object3D> staticObjects;
}