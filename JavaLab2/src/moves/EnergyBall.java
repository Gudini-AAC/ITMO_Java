package moves;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Stat;

import java.lang.Math;

public class EnergyBall extends SpecialMove {
	public EnergyBall() {
		super(Type.GRASS, 90., 100.);
	}

	@Override
	protected void applyOppEffects(Pokemon p) {
		if (Math.random() < 0.1)
			p.addEffect(new Effect().turns(-1).stat(Stat.SPECIAL_DEFENSE, -1));
	}

	@Override
	public String describe() {
		return "draws power from nature and fires it at the target. This may also lower the target's Sp. Def";
	}

}
