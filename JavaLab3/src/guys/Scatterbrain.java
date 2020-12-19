package guys;
import world.Named;
import world.Work;
import guys.Guy;
import guys.NothingToEatException;
import linalg.Vec3;
import linalg.StatePair;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Comparator;
import java.util.function.Consumer;
import java.lang.Math;

/**
* @brief Named guy who can loose things that he didn't ever have
*/
public class Scatterbrain extends Guy implements Named {
    
    /**
    * @brief I guess this name is self-explanatory...
    */
    private class Belongings {

        /**
        * @brief Constructs random thing
        */
        public Belongings() {
            final String[] names = {
                "Hat",
                "Rat",
                "Cat",
                "Gat",
                "Fat",
                "Bat",
                "Pet",
                "Oat"
            };

            name = names[(int)(Math.random() * names.length) % names.length];
            chanceToDrop = (float)Math.random();
        }

        /**
        * @brief Retrieve chane of dropping
        * @return Chance of dropping
        */
        public float getChanceToDrop() {
            return chanceToDrop;
        }

        /**
        * @brief Retrieve name
        * @return Name
        */
        public String getName() {
            return name;
        }

        private String name;
        private float chanceToDrop;
    }

    @Override
    public String getName() {
        return "Scatterbrain";
    }

    @Override
    public String describeSituation() { 
        if (Math.random() < .5)
            return "Looks like I've lost something";
        return "T thought I have that thing";
    }

    @Override
    public String toString() {
        return "Guy that had things";
    }

    /**
    * @brief Check if everething is on it's places
    * @return Check message
    */
    public String checkBelongings() {
        if (belongings.isEmpty())
            return "Nothing is lost, because there is nothing to loose";

        int lastIndex = belongings.size() - 1;
        float looseChance = belongings.get(lastIndex).getChanceToDrop();
        if (Math.random() < looseChance) {
            String ret = String.format("Looks like %s is lost, chance was %f", 
                belongings.get(lastIndex).getName(), looseChance);

            belongings.remove(lastIndex);
            return ret;
        }

        return "Nothing is lost, Imma lucky";
    }

    /**
    * @brief Creates Scatterbrain with a certan position and orientation
    * @param position
    * @param orientation
    */
    public Scatterbrain(StatePair position, StatePair orientation) {
        this.position    = new StatePair(position);
        this.orientation = new StatePair(orientation);

        this.belongings = new ArrayList<Belongings>();

        long numberOfBelongings = (long)(Math.random() * 10);
        for (long i = 0; i < numberOfBelongings; i++)
            belongings.add(new Belongings());
    }

    private ArrayList<Belongings> belongings;
}

