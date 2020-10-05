package moves;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;

import java.lang.Math;

public class Confusion extends SpecialMove {
	public Confusion() {
		super(Type.PSYCHIC, 50., 100.);
	}

	@Override
	protected void applyOppEffects(Pokemon p) {
		if (Math.random() < 0.1)
			Effect.confuse(p);
	}

	@Override
	public String describe() {
		return "makes psychic attack that may cause confusion";
	}

}
