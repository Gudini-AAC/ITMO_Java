import ru.ifmo.se.pokemon.*;
import pokemons.*;

public class Main {

	public static void main(String[] args) {

		Battle battle = new Battle();
		
		Pokemon bob = new Magearna("Bob", 1);
		Pokemon john = new Meditite("John", 1);
		Pokemon tom = new Medicham("Tom", 1);
		Pokemon regex = new Seedot("Regex", 1);
		Pokemon ross = new Nuzleaf("Ross", 1);
		Pokemon nut = new Nuzleaf("Nut", 1);

		battle.addAlly(bob);
		battle.addAlly(john);
		battle.addAlly(tom);
		battle.addFoe(regex);
		battle.addFoe(ross);
		battle.addFoe(nut);

		battle.go();

	} 

}