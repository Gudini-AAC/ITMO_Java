package moves;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Stat;

public class Confide extends StatusMove {
	public Confide() {
		super(Type.NORMAL, 0., 100.);
	}

	@Override
	protected void applyOppEffects(Pokemon p) {
		p.addEffect(new Effect().turns(-1).stat(Stat.SPECIAL_ATTACK, -1));
	}

	@Override
	public String describe() {
		return "tells the target a secret, and the target loses its ability to concentrate";
	}

}
