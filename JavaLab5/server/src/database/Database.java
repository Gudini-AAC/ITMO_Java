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
import structures.*;

import database.SimplePasswordGenerator;
import java.sql.*;

/**
* @brief This class holds datastructure and the metadata for the database.
*/
public class Database {
/*
create table persons (
    id int not null,
    name varchar(255) not null,
    coordinatesX int not null,
    coordinatesY real not null,
    creationDate date not null,
    height bigint not null,
    eyeColor varchar(16) not null,
    hairColor varchar(16),
    nationality varchar(16) not null,
    locationX bigint not null,
    locationY double precision not null,
    locationZ int not null,
    locationName varchar(16)
);
*/
    
    /**
    * @brief Constructs database with associated database file.
    */
    public Database(String login, String password) {
        stack = new Stack<Person>();
        date = LocalDate.now();
        
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", login, password);
        } catch (Exception e) {
            System.out.println("Can't connect to the database.");
            System.exit(1);
        }
    }

    public long generateId() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select nextval('generate_id')");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getLong("nextval");
    }
    
    /**
    * @brief Add new element to the database.
    * @param person Element to add.
    */
    public void add(Person person) { 
        try {
            person.id = generateId();
            person.creationDate = LocalDate.now();
            
            PreparedStatement set = connection.prepareStatement("insert into persons values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            set.setLong  (1,  person.id);
            set.setString(2,  person.name);
            set.setInt   (3,  person.coordinates.x);
            set.setFloat (4,  person.coordinates.y);
            set.setDate  (5,  Date.valueOf(person.creationDate));
            set.setLong  (6,  person.height);
            set.setString(7,  person.eyeColor.toString());
            set.setString(8,  person.hairColor == null ? null : person.hairColor.toString());
            set.setString(9,  person.nationality.toString());
            set.setLong  (10, person.location.x);
            set.setDouble(11, person.location.y);
            set.setInt   (12, person.location.z);
            set.setString(13, person.location.name);
            
            set.execute();
            stack.push(person);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public void addUser(String login, String password) {
        String salt = new SimplePasswordGenerator(true, true, true, true).generate(10, 10);
        String hash = PassEncoder.getHash(password + salt);
        
        try {
            PreparedStatement statement = connection.prepareStatement("insert into users values (?, ?, ?)");
            statement.setString(1, login);
            statement.setString(2, hash);
            statement.setString(3, salt);
        
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean containsUser(String login, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from users where name = ?");
            statement.setString(1, login);
            
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return false;
            
            String salt = resultSet.getString("salt");
            String hash = PassEncoder.getHash(password + salt);
            
            statement = connection.prepareStatement("select * from users where name = ? and password = ? and salt = ?");
            statement.setString(1, login);
            statement.setString(2, hash);
            statement.setString(3, salt);
            
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
    * @brief Replace element at index with a given.
    * @param index Index at wich new element should be placed.
    * @param person New element to insert.
    * @return Success bool.
    */
    public boolean replace(Person person) {
        try {
            person.creationDate = LocalDate.now();
            PreparedStatement set = connection.prepareStatement("update persons set name = ?, coordinatesX = ?, coordinatesY = ?, creationDate = ?, height = ?, eyeColor = ?, hairColor = ?, nationality = ?, locationX = ?, locationY = ?, locationZ = ?, locationName = ? where id = ?");
    
            set.setString(1,  person.name);
            set.setInt   (2,  person.coordinates.x);
            set.setFloat (3,  person.coordinates.y);
            set.setDate  (4,  Date.valueOf(person.creationDate));
            set.setLong  (5,  person.height);
            set.setString(6,  person.eyeColor.toString());
            set.setString(7,  person.hairColor == null ? null : person.hairColor.toString());
            set.setString(8,  person.nationality.toString());
            set.setLong  (9,  person.location.x);
            set.setDouble(10, person.location.y);
            set.setInt   (11, person.location.z);
            set.setString(12, person.location.name);
            set.setLong  (13, person.id);
                
            set.execute();
            
            int index = IntStream.range(0, stack.size()).filter(x -> stack.get(x).id == person.id).findFirst().getAsInt();
            stack.set(index, person);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
    * @brief Mutates state of the database in responce to a request
    * @param request Request to the database
    * @return Responce from the database
    */
    public Response respond(Request request) {
        boolean hasAccess = containsUser(request.login, request.password);
        
        switch (request.getMessageType()) {
            case REQUEST_ACCESS: {
                return new ResponseAccess(hasAccess);
            } case REQUEST_REGISTER: {
                addUser(request.login, request.password);
                return new ResponseRegister(true);
            } default: {
                if (!hasAccess) return null;
            }
        }
        
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
                ret = new ResponseReplace(replace(person));
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
    * @brief Load database from the associated file
    * @throws FileNotFoundException If file is not present.
    * @throws IOException If one of the user io streams fails.
    */
    public void load() {
        try {
            PreparedStatement select = connection.prepareStatement("select * from persons");
            ResultSet set = select.executeQuery();
            while (set.next()) {
                Person person = new Person();
                person.coordinates = new Coordinates();
                person.location = new Location();
                
                person.id            = set.getLong("id");
                person.name          = set.getString("name");
                person.coordinates.x = set.getInt("coordinatesX");
                person.coordinates.y = set.getFloat("coordinatesY");
                person.creationDate  = set.getDate("creationDate").toLocalDate();
                person.height        = set.getLong("height");
                person.eyeColor      = Color.valueOf(set.getString("eyeColor"));
                String hairColorString = set.getString("hairColor");
                person.hairColor     = hairColorString == null ? null : Color.valueOf(hairColorString);
                person.nationality   = Country.valueOf(set.getString("nationality"));
                person.location.x    = set.getLong("locationX");
                person.location.y    = set.getDouble("locationY");
                person.location.z    = set.getInt("locationZ");
                person.location.name = set.getString("locationName");
                
                stack.push(person);                
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    private Stack<Person> stack;
    private LocalDate date;
    private Connection connection;
}
