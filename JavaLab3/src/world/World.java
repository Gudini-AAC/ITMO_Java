package world;
import java.util.ArrayList;
import java.util.Optional;
import java.lang.Math;
import java.lang.RuntimeException;
import vehicles.NoBagsToDropException;
import world.Simulatable;
import world.Object3D;
import common.Color;
import linalg.Vec3;
import guys.Guy;
import guys.Crowd;
import guys.Grunt;
import guys.RolyPoly;
import guys.Posey;
import guys.Scatterbrain;
import guys.Dunno;
import guys.Doono;
import guys.GlassEye;
import vehicles.Vehicle;
import vehicles.FlyingVehicle;
import vehicles.Balloon;

/**
* @brief world that can hold objects and simulate their behavior in space-time domain
*/
public class World {
	
	/**
	* @brief Default-initialized empty world
	*/
	public World() {
		simulatableObjects = new ArrayList<Simulatable>();
		staticObjects      = new ArrayList<Object3D>();
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
				} else {
					name = object.toString();
				}

				// Just use the functionality provided by the object...
				if (object instanceof Directed && Math.random() < threshold) {
					if (Math.random() < .5) {
						System.out.printf("%s is pointing at %s\n", name, ((Directed)object).resolveDirection().toString());
					} else {
						Vec3 direction = ((Directed)object).resolveDirectionVector();

						System.out.printf("%s has a direction X: %.3f Y: %.3f Z: %.3f\n",
						name, direction.getX(), direction.getY(), direction.getZ());
					}
				}

				if (object instanceof Guy && Math.random() < threshold) {
					if (Math.random() < .5) {
						System.out.printf("%s is here, and he thinks: \"%s\"\n", name,
							((Guy)object).describeSituation());
					} else {
						System.out.printf("%s thinks about his life: \"%s\"\n", name,
							((Guy)object).lifeStats());
					}
				}

				if (object instanceof Guy && Math.random() < threshold) {
					int index = (int)(Math.random() * simulatableObjects.size()) % simulatableObjects.size();

					if (simulatableObjects.get(index) instanceof Object3D) {
						Object3D target = (Object3D)simulatableObjects.get(index);

						System.out.printf("%s in now looking at %s\n", name, target.toString());
						((Guy)object).lookAt(target);
					}
				}

				if (object instanceof Vehicle && ((Vehicle)object).isFollowingPath() && Math.random() < threshold) {
					Vec3 position = ((Vehicle)object).getPosition().getValue();

					System.out.printf("%s is following path and now at position X: %.3f Y: %.3f Z: %.3f\n",
						name, position.getX(), position.getY(), position.getZ());
				}

				if (object instanceof Vehicle && Math.random() < threshold) {
					Vec3 position = ((Vehicle)object).getPosition().getValue();

					System.out.printf("%s is now at position X: %.3f Y: %.3f Z: %.3f\n",
						name, position.getX(), position.getY(), position.getZ());
				}

				if (object instanceof Vehicle && Math.random() < threshold) {
					System.out.printf("Passangers in the %s think: \n\t\"%s\"\n",
						name, ((Vehicle)object).passengerOpinions());
				}

				if (object instanceof Vehicle && Math.random() < threshold) {
					Optional<Guy> guy = ((Vehicle)object).kickOutLast();
					if (guy.isPresent())
						System.out.printf("%s been kicked out of %s\n", guy.get().toString(), name);
					else
						System.out.printf("Nobody been kicked out of %s\n", name);
				}

				if (object instanceof FlyingVehicle && Math.random() < threshold) {
					Vec3 windSpeed = Vec3.makeRandom(-2.f, 2.f);

					System.out.printf("%s is now affected by a %.3f m/s wind\n", name, windSpeed.length());
					((FlyingVehicle)object).setWind(windSpeed);
				}

				if (object instanceof Vehicle && Math.random() < threshold) {
					System.out.printf("%s stats: %s\n", name, ((Vehicle)object).vehicleStats());
				}

				if (object instanceof Balloon && Math.random() < threshold) {
					((Balloon)object).setFlameColor(Color.makeRandom());
					Color color = ((Balloon)object).getFlameColor();

					System.out.printf("%s now has %s flame color\n", name, color.toString());
				}
				
				if (object instanceof Grunt && Math.random() < threshold) {
					System.out.printf("%s makes %s\n", name, ((Grunt)object).grumble());
				}

				if (object instanceof GlassEye && Math.random() < threshold) {
					int index = (int)(Math.random() * simulatableObjects.size()) % simulatableObjects.size();

					if (simulatableObjects.get(index) instanceof Object3D) {
						Object3D target = (Object3D)simulatableObjects.get(index);

						System.out.printf("%s looks through his telescope at %s: \"%s\"\n", name,
							target.toString(), ((GlassEye)object).lookThroughTelescope(target));
					}
				}

				if (object instanceof Doono && Math.random() < threshold) {
					if (Math.random() < .5) {
						System.out.printf("%s gives a speech: \n\t\"%s\"\n", name, ((Doono)object).giveASpeech());
					} else {
						System.out.printf("%s by %s\n", ((Doono)object).doWork(new Work()), name);
					}
				}

				if (object instanceof Dunno && Math.random() < threshold) {
					System.out.printf("%s by %s\n", ((Dunno)object).messUp(new Work()), name);
				}

				if (object instanceof Crowd && Math.random() < threshold) {
					System.out.printf("%s shouted: \"%s\"\n", name, ((Crowd)object).shoutOutLoud());
				}

				if (object instanceof RolyPoly && Math.random() < threshold) {
					try {
						String mealName = ((RolyPoly)object).eatMealFromStorage();
						System.out.printf("%s have eated his %s. %d to eat\n", name, mealName, ((RolyPoly)object).countMealsToEat());
					} catch (RuntimeException e) {
						System.out.printf("%s coludn't eat his meal, because %s\n", name, e.getMessage());
					}
				}

				if (object instanceof Scatterbrain && Math.random() < threshold) {
					System.out.printf("%s checked his things: \"%s\"\n", name, ((Scatterbrain)object).checkBelongings());
				}

				if (object instanceof Posey && Math.random() < threshold) {
					int index = (int)(Math.random() * simulatableObjects.size()) % simulatableObjects.size();

					if (simulatableObjects.get(index) instanceof Object3D) {
						Object3D target = (Object3D)simulatableObjects.get(index);

						System.out.printf("%s made a poem about %s: \"%s\"\n", name,
							target.toString(), ((Posey)object).makePoetryAbout(target));
					}
				}

				if (object instanceof Balloon && Math.random() < threshold) {
					float residualMass;
					try {
						residualMass = ((Balloon)object).dropBag();
						System.out.printf("Bag has been dropped from %s, residual mass is %.3f\n", name, residualMass);
					} catch (NoBagsToDropException e) {
						System.out.printf("No bags are dropped from %s: %s\n", name, e.getMessage());
					}
				}


			}

		}

	}

	private ArrayList<Simulatable> simulatableObjects;
	private ArrayList<Object3D> staticObjects;
	private static final double threshold = .02;
}