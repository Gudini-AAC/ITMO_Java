import ru.ifmo.se.pokemon.*;
import pokemons.*;

public class Main {

	public static void main(String[] args) {

		Battle battle = new Battle();
		
		battle.addAlly(new Shiftry("Bob", 1));
		battle.addAlly(new Meditite("John", 1));
		battle.addAlly(new Medicham("Tom", 1));
		battle.addFoe(new Seedot("Regex", 1));
		battle.addFoe(new Nuzleaf("Ross", 1));
		battle.addFoe(new Magearna("Nut", 1));

		battle.go();

	} 

}