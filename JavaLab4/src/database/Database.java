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

public class Database {
	public Database(String filepath) { this.filepath = filepath; stack = new Stack(); date = LocalDate.now(); }
	public int size() { return stack.size(); }
	public LocalDate constructionDate() { return date; }
	public void clear() { stack.clear(); }
	public void shuffle() { Collections.shuffle(stack); }
	
	public void add(Person val) { 
		long maxId = 0;
		for (Person person : stack)
			if (person.id > maxId) maxId = person.id;
		val.id = maxId + 1;
		stack.push(val);
	}
	
	public int findFirstOf(Predicate<Person> test) {
		for (int i = 0; i < stack.size(); i++)
			if (test.test(stack.get(i))) return i;
		return -1;
	}
	
	public boolean replace(int index, Person person) {
		if (index >= stack.size()) return false;
		stack.set(index, person);
		return true;
	}
	
	public List<Person> retrieveIf(Predicate<Person> test) {
		List<Person> ret = new ArrayList();
		for (Person person : stack)
			if (test.test(person)) ret.add(person);
		return ret;
	}
	
	public void removeIf(Predicate<Person> test) {
		for (int i = 0; i < stack.size();) {
			if (test.test(stack.get(i))) {
				stack.removeElementAt(i);
			} else {
				i++;
			}
		}
	}
	
	public List<Person> sortedBy(Comparator<Person> test) {
		List<Person> ret = (Stack<Person>)stack.clone();
		Collections.sort(ret, test);
		return ret;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for (Person person : stack)
			ret += person.toString();
		return ret;
	}
	
	public boolean save(BufferedWriter writer) throws IOException {
		File file = new File(filepath);
		FileOutputStream fileOStream;
		
		try {
			fileOStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			writer.write("File not found\n");
			writer.flush();
			return false;
		}

		OutputStreamWriter fileWriter = new OutputStreamWriter(fileOStream);
		
		for (Person person : stack)
			fileWriter.write(person.toCSV() + "\n");

		fileWriter.flush();
		fileOStream.close();
		
		return true;
	}
	
	public boolean load(BufferedWriter writer) throws IOException {
		File file = new File(filepath);
		FileInputStream fileIStream;

		try {
			fileIStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			writer.write("File not found\n");
			writer.flush();
			return false;
		}
		
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileIStream));
		
		try {
			String str = fileReader.readLine();
			if (str == null) return false;
			date = LocalDate.parse(str);
		} catch (IOException e) {
			writer.write(e.toString());
			return false;
		}
		
		for (;;) {
			try {
				String str = fileReader.readLine();
				if (str == null) break;
				Person person = new Person();
				person.fromCSV(str, 0);
				stack.push(person);
			} catch (IOException e) {
				writer.write(e.toString());
				break;
			} 
		}
		
		writer.flush();
		fileIStream.close();
		
		return true;
	}
	
	private Stack<Person> stack;
	private LocalDate date;
	private String filepath;
}
