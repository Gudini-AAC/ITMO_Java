package pokemons;
import pokemons.Nuzleaf;
import moves.DoubleTeam;

public class Shiftry extends Nuzleaf {
	public Shiftry() {
		this("Unnamed", 1);
	}

	public Shiftry(String name, int level) {
		super(name, level);
		setStats(90., 100., 60., 90., 60., 80.);
		addMove(new DoubleTeam());
	}

}