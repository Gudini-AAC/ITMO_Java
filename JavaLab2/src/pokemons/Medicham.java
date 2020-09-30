package pokemons;
import pokemons.Meditite;
import moves.EnergyBall;

public class Medicham extends Meditite {
	public Medicham() {
		this("Unnamed", 1);
	}

	public Medicham(String name, int level) {
		super(name, level);
		setStats(60., 60., 75., 60., 75., 80.);
		addMove(new EnergyBall());
	}

}