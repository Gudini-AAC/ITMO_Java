package moves;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Pound extends PhysicalMove {
	public Pound() {
		super(Type.NORMAL, 40., 100.);
	}

	@Override
	public String describe() {
		return "pounds the foe with forelegs or tail";
	}

}
