package moves;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Stat;

import java.lang.Math;

public class ShadowBall extends SpecialMove {
	public ShadowBall() {
		super(Type.GHOST, 80., 90.);
	}

	@Override
	protected void applyOppEffects(Pokemon p) {
		if (Math.random() < 0.2)
			p.addEffect(new Effect().turns(-1).stat(Stat.SPECIAL_DEFENSE, -1));
	}

	@Override
	public String describe() {
		return "hurls a shadowy blob at the foe. It may also lower the foe's Sp. Def stat";
	}

}
