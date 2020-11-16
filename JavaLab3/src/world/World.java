package world;
import java.util.ArrayList;
import world.Object3D;
import linalg.Vec3;


public class World {

 	public World() {
 		objects = new ArrayList<Object3D>();
 	}

 	public void addWorldObject(Object3D object) {
 		objects.add(object);
 	}

 	public void run(float seconds, float dt) {

 		for (float time = 0.f; time < seconds; time += dt) {

 			for (Object3D object : objects) {
 				object.tick(dt);
 			}

 		}

 	}

 	private ArrayList<Object3D> objects;
}