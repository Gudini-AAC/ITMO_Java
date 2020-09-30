package moves;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Stat;

public class SwordsDance extends StatusMove {
	public SwordsDance() {
		super(Type.NORMAL, 80., 100.);
	}

	@Override
	protected void applySelfEffects(Pokemon p) {
		p.addEffect(new Effect().turns(-1).stat(Stat.ATTACK, 2));
	}

	@Override
	public String describe() {
		return "dances a frenetic dance to uplift the fighting spirit";
	}

}
