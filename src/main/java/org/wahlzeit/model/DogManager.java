package org.wahlzeit.model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.PatternInstance;


@PatternInstance (
	patternName = "Type Object",
	participants = {"Dog", "DogType", "DogManager"}
)
class DogManager extends ObjectManager {
    /**
     * Manager class responsible for managing different types
     * of dogs and their corresponding type object classes.
     * It acts as a central point of control for the creation, registration, and retrieval of the objects.
     */

    @PatternInstance (
        patternName = "Singleton",
        participants = {"DogManager"}
    )
    private static final DogManager instance = new DogManager();

    private DogManager() {
        // private constructor to prevent instantiation
    }

    public static DogManager getInstance() {
        return instance;
    }

    // references to Dog instances
    private HashMap<String, Dog> dogs = new HashMap<String, Dog>();
    // references to Dog types
    private HashMap<String, DogType> dogtypes = new HashMap<String, DogType>();

    @Override
    protected Dog createObject(ResultSet rset) throws SQLException {
        // delegate to Dog class
        return new Dog(rset);
    }

    public Dog createDog(String typeName, String name, int age) {
        assertIsValidDogTypeName(typeName);
        DogType type = getDogType(typeName);
        // delegate to DogType class
        Dog instance = type.createInstance(name, age);
        dogs.put(instance.getId(), instance);
        return instance;
    }

    private DogType getDogType(String typeName) {
        DogType type = null;
        type = dogtypes.get(typeName);
        if (type == null) {
            type = new DogType(typeName);
        }
        return type;
    }

    public void assertIsValidDogTypeName(String typeName) {
        // no special characters or numbers
        assert !typeName.matches(".*[^a-zA-Z\\s].*");
    }

    /*
     * Methods working on shared ressources.
     */
    public synchronized Dog getDog(String id) {
        return dogs.get(id);
    }

    public synchronized void updateDog(Dog dog) {
        dogs.put(dog.getId(), dog);
    }

    public synchronized void deleteDog(String id) {
        dogs.remove(id);
    }

     /**
     * Get the number of dogs of a certain type.
     * @param typeName name of the dog type
     * @return number of dogs of the specified type
     */
    public synchronized int getNumberOfDogs(String typeName) {
        DogType type = dogtypes.get(typeName);
        int count = 0;
        for (Dog dog : dogs.values()) {
            if (dog.getType() == type) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get a set of all dogs of a certain type.
     * @param typeName name of the dog type
     * @return set of dogs of the specified type
     */
    public synchronized Set<Dog> getDogsOfType(String typeName) {
        DogType type = dogtypes.get(typeName);
        Set<Dog> result = new HashSet<Dog>();
        for (Dog dog : dogs.values()) {
            if (dog.getType() == type) {
                result.add(dog);
            }
        }
        return result;
    }

  
}
