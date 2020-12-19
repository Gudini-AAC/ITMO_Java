package guys;
import world.Object3D;
import world.Named;
import guys.Guy;
import linalg.Vec3;
import linalg.StatePair;
import java.lang.Math;

/**
* @brief Named guy with a telescope
*/
public class GlassEye extends Guy implements Named {
	private class Telescope {
		public float magnification;
		public float mirrorDiamiter;

		/**
		* @brief Make a telescope with random specs
		*/
		public Telescope() {
			magnification  = (float)Math.random() * 100.f;
			mirrorDiamiter = (float)Math.random() * 10.f;
		}
	}


	@Override
	public String getName() {
		return "GlassEye";
	}

	@Override
	public String describeSituation() { 
		if (Math.random() < .5)
			return "Last night stars were beautiful";
		return "Look at the stars!";
	}

	/**
	* @brief  Looks look through his telescope on to the object
	* @param Object to look at
	* @return What he sees
	*/
	public final String lookThroughTelescope(Object3D object) {
		float len = this.lookAt(object).length();
		if (len < 1e4 * telescope.mirrorDiamiter / telescope.magnification)
			return "Looks blurry, can't really say what it is";
		else
			return object.toString() + " looks sharp";
	}

	@Override
	public String toString() {
		return "Guy that looks at the sky";
	}

	/**
	* @brief Creates GlassEye with a certan position and orientation
	* @param position
	* @param orientation
	*/
	public GlassEye(StatePair position, StatePair orientation) {
		this.position    = new StatePair(position);
		this.orientation = new StatePair(orientation);
		this.telescope   = new Telescope();
	}

	private Telescope telescope;
}

