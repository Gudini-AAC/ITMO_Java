package guys;
import world.Named;
import world.Work;
import guys.Guy;
import linalg.Vec3;
import linalg.StatePair;
import java.lang.Math;

/**
* @brief Named guy that knows stuff
*/
public class Doono extends Guy implements Named {
    @Override
    public String getName() {
        return "Doono";
    }

    @Override
    public String describeSituation() { 
        if (Math.random() < .5)
            return "Boy, you're doing it wrong!";
        return "Do you know what you're doing with your life?";
    }

    /**
    * @brief Unique ability
    * @return Exactly what you're expecting
    */
    public final String doWork(Work work) { 
        return work.doWork() + " " + work.getDescription() + " is done exceptionally well";
    }

    public String giveASpeech() {
        final class Speech {
            
            public void AddPhrases(long num) {
                for (long i = 0; i < num; i++)
                    AddPhrase();
            }

            public void AddPhrase() {
                if (text != null) 
                    text += "\n\t";
                else
                    text = new String();
                
                final String[] phrases = {
                    "If you think you are real.",
                    "First of all, don't do it.",
                    "Secondly, everybody is wrong!",
                    "Secondly, you are wrong!",
                    "We can do it together.",
                    "I'm already doing bulk of the work.",
                    "Better don't stand on my way!"
                };

                text += phrases[(int)(Math.random() * phrases.length) % phrases.length];
            }

            public String getText() {
                return text;
            }

            private String text;
        }

        Speech speech = new Speech();
        speech.AddPhrases(1 + (long)(Math.random() * 5.));
        return speech.getText();
    }

    @Override
    public String toString() {
        return "Guy that know a lot of stuff";
    }

    /**
    * @brief Creates Doono with a certan position and orientation
    * @param position
    * @param orientation
    */
    public Doono(StatePair position, StatePair orientation) {
        this.position    = new StatePair(position);
        this.orientation = new StatePair(orientation);
    }

}

