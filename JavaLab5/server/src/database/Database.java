package database;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections; 
import java.util.function.Predicate;
import java.util.Comparator;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.*;
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
	* @brief Add new element to the database.
	* @param val Element to add.
	*/
	public void add(Person val) { 
		OptionalLong maxId = stack.stream().mapToLong(x -> x.id).max();
		
		val.id = maxId.isPresent() ? maxId.getAsLong() + 1 : 0;
		val.creationDate = LocalDate.now();
		
		stack.push(val);
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
	* @brief Mutates state of the database in responce to a request
	* @param request Request to the database
	* @return Responce from the database
	*/
	public Response respond(Request request) {
		Response ret;
	
		switch (request.getMessageType()) {
			case REQUEST_SIZE: {
            	ret = new ResponseSize(stack.size());
				break;
			} case REQUEST_CONSTRUCTUION_DATE: {
	            ret = new ResponseConstructionDate(date);
				break;
			} case REQUEST_CLEAR: {
				stack.clear();
            	ret = new ResponseClear(true);
				break;
			} case REQUEST_SHUFFLE: {
				Collections.shuffle(stack);
	            ret = new ResponseShuffle(true);
				break;
			} case REQUEST_ADD: {
				add(((RequestAdd)request).getPerson());
				ret = new ResponseAdd(true);
				break;
			} case REQUEST_FIND: {
				long id = ((RequestFind)request).getId();
				
				OptionalInt index = IntStream.range(0, stack.size()).filter(x -> stack.get(x).id == id).findFirst();
				
				ret = new ResponseFind(index.isPresent() ? index.getAsInt() : -1);
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
					ret = new ResponseRetrieve(stack.stream().filter(x -> x.location.equals(location)).collect(Collectors.toList()));
				break;
			} case REQUEST_REMOVE: {
				RequestRemove.Key key = ((RequestRemove)request).getKey();
				Long value = ((RequestRemove)request).getValue();

				Predicate<Person> predicate = null;
				if (key == RequestRemove.Key.ID) {
					predicate = x -> !(x.id == ((Long)value).longValue());
				} else if (key == RequestRemove.Key.LOCATION_Z_GREATER) {
					predicate = x -> !(x.location.z > ((Long)value).intValue());
				} else if (key == RequestRemove.Key.LOCATION_Z_LESS) {
					predicate = x -> !(x.location.z < ((Long)value).intValue());
				} else {
					ret = null;
					break;
				} 

				List<Person> persons = stack.stream().filter(predicate).collect(Collectors.toList());
				boolean removeHappend = persons.size() != stack.size();
				
				stack.clear();
				stack.addAll(persons);
								
				ret = new ResponseRemove(removeHappend);
				break;
			} case REQUEST_SORTED: {
				RequestSorted.Key key = ((RequestSorted)request).getKey();
				
				Comparator<Person> comparator;
				if (key == RequestSorted.Key.HEIGHT) {
					comparator = (x, y) -> (x.height > y.height ? 1 : x.height < y.height ? -1 : 0);
				} else if (key == RequestSorted.Key.LOCATION_Z) {
					comparator = (x, y) -> (x.location.z > y.location.z ? 1 : x.location.z < y.location.z ? -1 : 0);
				} else  {
					ret = null;
					break;
				}
				
				ret = new ResponseSorted(stack.stream().sorted(comparator).collect(Collectors.toList()));
				break;
			} default: {
				ret = null;
			}
		}

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
