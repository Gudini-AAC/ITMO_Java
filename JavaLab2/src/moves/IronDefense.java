package moves;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Stat;

public class IronDefense extends StatusMove {
	public IronDefense() {
		super(Type.STEEL, 0., 100.);
	}

	@Override
	protected void applySelfEffects(Pokemon p) {
		p.addEffect(new Effect().turns(-1).stat(Stat.DEFENSE, 2));
	}

	@Override
	public String describe() {
		return "hardens its body's surface like iron, sharply raising its Defense stat";
	}

}
