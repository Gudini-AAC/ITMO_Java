package pokemons;
import ru.ifmo.se.pokemon.Type;
import pokemons.Seedot;
import moves.Pound;

public class Nuzleaf extends Seedot {
	public Nuzleaf() {
		this("Unnamed", 1);
	}

	public Nuzleaf(String name, int level) {
		super(name, level);
		setStats(70., 70., 40., 60., 40., 60.);
		setType(Type.GRASS, Type.DARK);
		addMove(new Pound());
	}

}