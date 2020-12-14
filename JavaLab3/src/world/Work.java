package world;
import java.lang.Math;
import java.util.ArrayList;

/**
* @brief A work to do
*/
public class Work {
	private class Task {
		private float complexity;

		public float doTask() {
			return complexity;
		}

		public Task() {
			complexity = (float)Math.random();
		}
	}

	/**
	* @brief Gives a description of the work
	* @return Description of the work
	*/
	public String getDescription() {
		return description;
	}

	/**
	* @brief Does the work
	* @return How complex work was
	*/
	public String doWork() {
		float overallComplexity = 0;

		for (Task task : tasks)
			overallComplexity += task.doTask();

		if (overallComplexity < 3)
			return "Slightly complex";
		else if (overallComplexity < 10)
			return "Moderatly complex";
		return "Highly complex";
	}

	/**
	* @brief Creates work with a random description
	*/
	public Work() {
		description = descriptions[(int)(Math.random() * descriptions.length) % descriptions.length];

		tasks = new ArrayList<Task>();

		long numberOfTasks = (long)(Math.random() * 10);
        for (long i = 0; i < numberOfTasks; i++)
            tasks.add(new Task());
	}

	private String description;

	private static final String[] descriptions = {
		"Hole carving",
		"Bag dropping",
		"Direction determination",
		"Programming",
		"Taking away things"
	};

	private ArrayList<Task> tasks;
}

