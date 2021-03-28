package database;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections; 
import java.util.function.Predicate;
import java.util.Comparator;
import java.time.LocalDate;
import java.io.*;
import protocol.*;
import structures.Person;
import structures.Location;

/**
* @brief This class holds datastructure and the metadata for the database.
*/
public class Database {
	/**
	* @brief Constructs database with associated database file.
	* @param filepath Path to a database file.
	*/
	public Database(String filepath) { this.filepath = filepath; stack = new Stack<Person>(); date = LocalDate.now(); }
	
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
		List<Person> ret = new ArrayList<Person>();
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
	* @param compare Comparison function.
	* @return List of sorted elements.
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
	* @throws FileNotFoundException If file is not present.
    * @throws IOException If one of the user io streams fails.
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
	
	public Response respond(Request request) {
		Response ret;
	
		switch (request.getMessageType()) {
			case REQUEST_SIZE: {
            	ret = new ResponseSize(size());
				break;
			} case REQUEST_CONSTRUCTUION_DATE: {
	            ret = new ResponseConstructionDate(date);
				break;
			} case REQUEST_CLEAR: {
				clear();
            	ret = new ResponseClear(true);
				break;
			} case REQUEST_SHUFFLE: {
	            shuffle();
	            ret = new ResponseShuffle(true);
				break;
			} case REQUEST_ADD: {
				add(((RequestAdd)request).getPerson());
				ret = new ResponseAdd(true);
				break;
			} case REQUEST_FIND: {
				long id = ((RequestFind)request).getId();
				ret = new ResponseFind(findFirstOf(x -> id == x.id));
				break;
			} case REQUEST_REPLACE: {
				Person person = ((RequestReplace)request).getPerson();
				int index = ((RequestReplace)request).getIndex();
				
				ret = new ResponseReplace(replace(index, person));
				break;
			} case REQUEST_RETRIEVE: {
				Location location = ((RequestRetrieve)request).getLocation();
				if (location == null)
					ret = new ResponseRetrieve(stack);					
				else
					ret = new ResponseRetrieve(retrieveIf(x -> x.location.equals(location)));
				break;
			} case REQUEST_REMOVE: {
				RequestRemove.Key key = ((RequestRemove)request).getKey();
				Long value = ((RequestRemove)request).getValue();

				if (key == RequestRemove.Key.ID) {
					removeIf(x -> x.id == ((Long)value).longValue());
					ret = new ResponseRemove(true);
				} else if (key == RequestRemove.Key.LOCATION_Z_GREATER) {
					removeIf(x -> x.location.z > ((Long)value).intValue());
					ret = new ResponseRemove(true);
				} else if (key == RequestRemove.Key.LOCATION_Z_LESS) {
					removeIf(x -> x.location.z < ((Long)value).intValue());
					ret = new ResponseRemove(true);
				} else {
					ret = null;
				} 
				
				break;
			} case REQUEST_SORTED: {
				RequestSorted.Key key = ((RequestSorted)request).getKey();
				
				if (key == RequestSorted.Key.HEIGHT) {
					List<Person> persons = sortedBy(
						(x, y) -> (x.height > y.height ? 1 : x.height < y.height ? -1 : 0)
					);
					ret = new ResponseSorted(persons);
				} else if (key == RequestSorted.Key.LOCATION_Z) {
					List<Person> persons = 
					sortedBy(
						(x, y) -> (x.location.z > y.location.z ? 1 : x.location.z < y.location.z ? -1 : 0)
					);
					ret = new ResponseSorted(persons);
				} else {
					ret = null;
				}

				break;
			} default: {
				ret = null;
			}
		}

		return ret;		
	}
	
	/**
	* @brief Load database from the associated file
	* @throws FileNotFoundException If file is not present.
    * @throws IOException If one of the user io streams fails.
	*/
	public void load() throws IOException, FileNotFoundException {
		File file = new File(filepath);
		FileInputStream fileIStream;

		fileIStream = new FileInputStream(file);
		
		try {
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
		} finally {
			fileIStream.close();
		}
	}
	
	private Stack<Person> stack;
	private LocalDate date;
	private String filepath;
}
