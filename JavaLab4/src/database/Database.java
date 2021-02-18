package database;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections; 
import java.util.function.Predicate;
import java.util.Comparator;
import java.time.LocalDate;
import java.io.*;
import structures.Person;

/**
* @brief This class holds datastructure and the metadata for the database.
*/
public class Database {
	/**
	* @brief Constructs database with associated database file.
	* @param filepath Path to a database file.
	*/
	public Database(String filepath) { this.filepath = filepath; stack = new Stack(); date = LocalDate.now(); }
	
	/**
	* @return Size of the underlying datastructure.
	*/
	public int size() { return stack.size(); }
	
	/**
	* @return Date when underlying datastructure was constructed.
	*/
	public LocalDate constructionDate() { return date; }
	
	/**
	* @brief Clear the underlying datastructure.
	*/
	public void clear() { stack.clear(); }
	
	/**
	* @brief Shuffle the elements in the underlying datastructure.
	*/
	public void shuffle() { Collections.shuffle(stack); }
	
	/**
	* @brief Add new element to the database.
	* @param val Element to add.
	*/
	public void add(Person val) { 
		long maxId = 0;
		for (Person person : stack)
			if (person.id > maxId) maxId = person.id;
		val.id = maxId + 1;
		stack.push(val);
	}
	
	/**
	* @brief Find the first element that passes the test.
	* @param test Test function.
	* @return Either index of the element or -1.
	*/
	public int findFirstOf(Predicate<Person> test) {
		for (int i = 0; i < stack.size(); i++)
			if (test.test(stack.get(i))) return i;
		return -1;
	}
	
	/**
	* @brief Replace element at index with a given.
	* @param index Index at wich new element should be placed.
	* @param person New element to insert.
	* @return Success bool.
	*/
	public boolean replace(int index, Person person) {
		if (index >= stack.size()) return false;
		stack.set(index, person);
		return true;
	}
	
	/**
	* @brief Retrieve all of the elements that are passing the test.
	* @param test Test function.
	* @return List of passed elements.
	*/
	public List<Person> retrieveIf(Predicate<Person> test) {
		List<Person> ret = new ArrayList();
		for (Person person : stack)
			if (test.test(person)) ret.add(person);
		return ret;
	}
	
	/**
	* @brief Remove all of the elements that pass the test.
	* @param test Test function.
	*/
	public void removeIf(Predicate<Person> test) {
		for (int i = 0; i < stack.size();) {
			if (test.test(stack.get(i))) {
				stack.removeElementAt(i);
			} else {
				i++;
			}
		}
	}
	
	/**
	* @brief Retrieve all of the elements in the sorted order.
	* @param compare Comparison function
	*/
	public List<Person> sortedBy(Comparator<Person> compare) {
		List<Person> ret = (Stack<Person>)stack.clone();
		Collections.sort(ret, compare);
		return ret;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for (Person person : stack)
			ret += person.toString();
		return ret;
	}
	
	/**
	* @brief Save database into the associated file
	*/
	public void save() throws IOException, FileNotFoundException {
		File file = new File(filepath);
		FileOutputStream fileOStream;
		
		fileOStream = new FileOutputStream(file);
		OutputStreamWriter fileWriter = new OutputStreamWriter(fileOStream);
		
		fileWriter.write(date.toString() + "\n");
		
		for (Person person : stack)
			fileWriter.write(person.toCSV() + "\n");

		fileWriter.flush();
		fileOStream.close();
	}
	
	/**
	* @brief Load database from the associated file
	*/
	public void load() throws IOException, FileNotFoundException {
		File file = new File(filepath);
		FileInputStream fileIStream;

		fileIStream = new FileInputStream(file);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileIStream));
		
		String str = fileReader.readLine();
		if (str != null) {
			date = LocalDate.parse(str);
		
			for (;;) {
				str = fileReader.readLine();
				if (str == null) break;
				Person person = new Person();
				person.fromCSV(str, 0);
				stack.push(person);
			}
		}
		
		fileIStream.close();
	}
	
	private Stack<Person> stack;
	private LocalDate date;
	private String filepath;
}
