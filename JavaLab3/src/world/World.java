package world;
import java.util.ArrayList;
import java.lang.Math;
import world.Object3D;
import guys.Guy;
import world.Simulatable;
import linalg.Vec3;
import vehicles.Vehicle;
import vehicles.FlyingVehicle;
import vehicles.Balloon;
import common.Color;

/**
* @brief world that can hold objects and simulate their behavior in space-time domain
*/
public class World {
	
	/**
	* @brief Default-initialized empty world
	*/
	public World() {
		simulatableObjects = new ArrayList<Simulatable>();
		staticObjects = new ArrayList<Object3D>();
	}

	/**
	* @brief Add object to the world
	* @param object Either static or simulateble object
	*/
	public void addWorldObject(Object3D object) {
		if (object instanceof Simulatable) 
			simulatableObjects.add((Simulatable)object);
		else 
			staticObjects.add(object);
			
	}

	/**
	* @brief Run simulation of the world
	* @param seconds Simulation time
	* @param dt Delta of time
	*/
	public void run(float seconds, float dt) {

		for (float time = 0.f; time < seconds; time += dt) {
			
			// Simulate all of the simulateble objects
			for (Simulatable object : simulatableObjects) {
				object.tick(dt);

				// Find the name of the object. Either its name provided by Named interface or toString method
				String name;
				if (object instanceof Named) {
					name = ((Named)object).getName();
				}
				else {
					name = object.toString();
				}

				// Just use the functionality provided by the object...
				if (object instanceof Directed && Math.random() < .05)
					System.out.printf("%s is pointing at %s", name, ((Directed)object).resolveDirection().toString());

				if (object instanceof Guy && Math.random() < .05)
					System.out.printf("%s is here, and he thinks: \"%s\"\n", name, ((Guy)object).describeSituation());

				if (object instanceof Guy && Math.random() < .05) {
					int index = (int)(Math.random() * simulatableObjects.size()) % simulatableObjects.size();
					if (simulatableObjects.get(index) instanceof Object3D) {
						Object3D target = (Object3D)simulatableObjects.get(index);
						System.out.printf("%s in now looking at %s\n", name, target.toString());
						((Guy)object).lookAt(target);
					}
				}

				if (object instanceof Vehicle && ((Vehicle)object).isFollowingPath() && Math.random() < .05) {
					Vec3 position = ((Vehicle)object).getPosition().getValue();
					System.out.printf("%s is following path and now at position X: %.3f Y: %.3f Z: %.3f\n",
						name, position.getX(), position.getY(), position.getZ());
				}

				if (object instanceof Vehicle && Math.random() < .05) {
					Vec3 position = ((Vehicle)object).getPosition().getValue();
					System.out.printf("%s is now at position X: %.3f Y: %.3f Z: %.3f\n",
						name, position.getX(), position.getY(), position.getZ());
				}

				if (object instanceof FlyingVehicle && Math.random() < .05) {
					Vec3 windSpeed = Vec3.makeRandom(-2.f, 2.f);
					System.out.printf("%s is now affected by a %.3f m/s wind\n", name, windSpeed.length());
					((FlyingVehicle)object).setWind(windSpeed);
				}

				if (object instanceof Balloon && Math.random() < .05) {
					Color color = Color.makeRandom();
					System.out.printf("%s now has %s flame color\n", name, color.toString());
					((Balloon)object).setFlameColor(color);
				}
				
			}

		}

	}

	private ArrayList<Simulatable> simulatableObjects;
	private ArrayList<Object3D> staticObjects;
}