package moves;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Stat;

public class ThunderWave extends StatusMove {
	public ThunderWave() {
		super(Type.ELECTRIC, 0., 90.);
	}

	@Override
	protected void applyOppEffects(Pokemon p) {
		Effect.paralyze(p);
	}

	@Override
	public String describe() {
		return "launches a weak jolt of electricity that paralyzes the target";
	}

}
