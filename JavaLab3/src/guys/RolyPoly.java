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
* @brief Named guy who is THE expret in the meals
*/
public class RolyPoly extends Guy implements Named {
    
    /**
    * @brief Tasty meal for RolyPoly
    */
    private class Meal {

        /**
        * @brief Constructs random meal
        */
        public Meal() {
            final String[] names = {
                "Strawberry",
                "Niceberry",
                "Lollipop",
                "Icecream",
                "Cupcake",
                "Candy",
                "Pie"
            };

            name   = names[(int)(Math.random() * names.length) % names.length];
            weight = (float)Math.random();
        }

        public float getWeight() {
            return weight;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof Meal && weight == ((Meal)other).weight && name.equals(((Meal)other).name);
        }

        private String name;
        private float weight;
    }

    @Override
    public String getName() {
        return "RolyPoly";
    }

    @Override
    public String describeSituation() { 
        if (Math.random() < .5)
            return "That stuff is super tasty";
        return "Do you want some tasty meals?";
    }

    @Override
    public String toString() {
        return "Guy that eats a lot of stuff";
    }

    /**
    * @brief Eat meal from an internal storage
    * @throws NothingToEatException if internal storage is empty
    */
    public String eatMealFromStorage() {
        class MealEater {
            public String eat() {
                Optional<Meal> max = mealsToEat.stream().max(new Comparator<Meal>() {
                    public int compare(Meal lh, Meal rh) {
                        if (lh.getWeight() < rh.getWeight()) return -1;
                        if (lh.getWeight() > rh.getWeight()) return  1;
                        return 0;
                    }
                });
                
                if (!max.isPresent())
                    throw new NothingToEatException();

                mealsToEat.remove(max.get());
                return max.get().getName();
            }

            private ArrayList<Meal> mealsToEat = meals;
        }

        MealEater eater = new MealEater();
        return eater.eat();
    }

    /**
    * @brief Counts how many meals is still present
    * @return Meal count
    */
    public long countMealsToEat() {
        return meals.size();
    }

    /**
    * @brief Creates RolyPoly with a certan position and orientation
    * @param position
    * @param orientation
    */
    public RolyPoly(StatePair position, StatePair orientation) {
        this.position    = new StatePair(position);
        this.orientation = new StatePair(orientation);

        this.meals = new ArrayList<Meal>();

        long numberOfMeals = (long)(Math.random() * 10);
        for (long i = 0; i < numberOfMeals; i++)
            meals.add(new Meal());
    }

    private ArrayList<Meal> meals;
}

