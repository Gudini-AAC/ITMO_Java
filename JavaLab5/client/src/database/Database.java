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
import structures.Location;
import database.Server;

import protocol.*;

/**
* @brief This class holds datastructure and the metadata for the database.
*/
public class Database {
	/**
	* @brief Constructs database with associated database file.
	* @param filepath Path to a database file.
	*/
	public Database(Server server) {
		this.server = server;
	}
	
	/**
	* @return Size of the underlying datastructure.
	*/
	public int size() {
		Response ret = server.sendMessage(new RequestSize());
		if (ret != null && ret instanceof ResponseSize)
			return ((ResponseSize)ret).getSize();
		return 0;
	}
	
	/**
	* @return Date when underlying datastructure was constructed.
	*/
	public LocalDate constructionDate() { 
		Response ret = server.sendMessage(new RequestConstructionDate());
		if (ret != null && ret instanceof ResponseConstructionDate)
			return ((ResponseConstructionDate)ret).getConstructionDate();
		return null;
	}
	
	/**
	* @brief Clear the underlying datastructure.
	*/
	public void clear() {
		server.sendMessage(new RequestClear());
	}
	
	/**
	* @brief Shuffle the elements in the underlying datastructure.
	*/
	public void shuffle() {
		server.sendMessage(new RequestShuffle());
	}
	
	/**
	* @brief Add new element to the database.
	* @param val Element to add.
	*/
	public boolean add(Person val) { 
		return server.sendMessage(new RequestAdd(val)) != null;
	}
	
	/**
	* @brief Find an element with the same id.
	* @param id Id to test against.
	* @return Either index of the element or -1.
	*/
	public int findById(long id) {
		Response ret = server.sendMessage(new RequestFind(id));
		if (ret != null && ret instanceof ResponseFind)
			return ((ResponseFind)ret).getIndex();
		return -1;
	}
	
	/**
	* @brief Replace element at index with a given.
	* @param index Index at wich new element should be placed.
	* @param person New element to insert.
	* @return Success bool.
	*/
	public boolean replace(Person person) {
		Response ret = server.sendMessage(new RequestReplace(person));
		return ret != null && ret.isSuccessful();
	}
	
	/**
	* @brief Retrieve all of the elements that are passing the test.
	* @param test Test function.
	* @return List of passed elements.
	*/
	public List<Person> retrieveWithSameLocation(Location location) {
		Response ret = server.sendMessage(new RequestRetrieve(location));
		if (ret != null && ret instanceof ResponseRetrieve)
			return ((ResponseRetrieve)ret).getValues();
		return new ArrayList<Person>();
	}
	
	/**
	* @brief Remove all of the elements that pass the test.
	* @param test Test function.
	*/
	public boolean removeIf(RequestRemove.Key key, Long value) {
		Response ret = server.sendMessage(new RequestRemove(key, value));
		return ret != null && ret.isSuccessful();
	}
	
	/**
	* @brief Retrieve all of the elements in the sorted order.
	* @param compare Comparison function.
	* @return List of sorted elements.
	*/
	public List<Person> sortedBy(RequestSorted.Key key) {
		Response ret = server.sendMessage(new RequestSorted(key));
		if (ret != null && ret instanceof ResponseSorted)
			return ((ResponseSorted)ret).getValues();
		return new ArrayList<Person>();
	}
	
	public List<Person> retrieve() {
		Response ret = server.sendMessage(new RequestRetrieve(null));
		if (ret != null && ret instanceof ResponseRetrieve)
			return ((ResponseRetrieve)ret).getValues();
		return null;
	}
	
	public boolean isUpdateRequested() { return server.isUpdateRequested(); }
	
	public boolean isAccessible() {
		Response ret = server.sendMessage(new RequestAccess());
		return ret != null && ret.isSuccessful();
	}
	
	public boolean register() {
		Response ret = server.sendMessage(new RequestRegister());
		return ret != null && ret.isSuccessful();
	}
	
	@Override
	public String toString() {
		Response ret = server.sendMessage(new RequestRetrieve(null));
		if (ret != null && ret instanceof ResponseRetrieve) {
			List<Person> values = ((ResponseRetrieve)ret).getValues();
			
			String str = "";
			for (Person person : values)
				str += person.toString();
			
			return str;
		}
		return "\n";
	}
	
	Server server;
}
