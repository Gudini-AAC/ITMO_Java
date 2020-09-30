package pokemons;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import moves.Meditate;
import moves.Confusion;
import moves.ShadowBall;

public class Meditite extends Pokemon {
	public Meditite() {
		this("Unnamed", 1);
	}

	public Meditite(String name, int level) {
		super(name, level);
		setStats(30., 40., 55., 40., 55., 60.);
		setType(Type.FIGHTING, Type.PSYCHIC);
		addMove(new Meditate());
		addMove(new Confusion());
		addMove(new ShadowBall());
	}

}