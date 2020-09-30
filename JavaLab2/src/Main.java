import ru.ifmo.se.pokemon.*;
import pokemons.*;

public class Main {

	public static void main(String[] args) {

		Battle b = new Battle();
		Pokemon p1 = new Magearna("Alien", 1);
		Pokemon p2 = new Pokemon("Predator", 1);
		b.addAlly(p1);
		b.addFoe(p2);
		b.go();

	} 

}