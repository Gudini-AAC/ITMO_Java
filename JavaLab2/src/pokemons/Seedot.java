package pokemons;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import moves.SwordsDance;
import moves.DoubleTeam;

public class Seedot extends Pokemon {
	public Seedot() {
		this("Unnamed", 1);
	}

	public Seedot(String name, int level) {
		super(name, level);
		setStats(40., 40., 50., 30., 30., 30.);
		setType(Type.GRASS);
		addMove(new SwordsDance());
		addMove(new DoubleTeam());
	}

}