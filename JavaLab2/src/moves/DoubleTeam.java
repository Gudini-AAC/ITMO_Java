package moves;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Stat;

public class DoubleTeam extends StatusMove {
	public DoubleTeam() {
		super(Type.NORMAL, 0., 100.);
	}

	@Override
	protected void applySelfEffects(Pokemon p) {
		p.addEffect(new Effect().turns(-1).stat(Stat.EVASION, 1));
	}

	@Override
	public String describe() {
		return "creates illusory copies to raise evasiveness";
	}

}
