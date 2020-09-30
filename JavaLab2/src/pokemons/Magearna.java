package pokemons;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import moves.SwordsDance;
import moves.ThunderWave;
import moves.IronDefense;
import moves.Confide;

public class Magearna extends Pokemon {
	public Magearna() {
		this("Unnamed", 1);
	}

	public Magearna(String name, int level) {
		super(name, level);
		setStats(80., 95., 115., 130., 115., 65.);
		setType(Type.STEEL, Type.FAIRY);
		addMove(new SwordsDance());
		addMove(new ThunderWave());
		addMove(new IronDefense());
		addMove(new Confide());
	}

}