package org.wahlzeit.model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.PatternInstance;


@PatternInstance (
	patternName = "Type Object",
	participants = {"Dog, DogType, DogManager"}
)
class DogManager extends ObjectManager {
    /**
     * Manager class responsible for managing different types
     * of dogs and their corresponding type object classes.
     * It acts as a central point of control for the creation, registration, and retrieval of the objects.
     */

    // references to Dog instances
    private HashMap<String, Dog> dogs = new HashMap<String, Dog>();
    // references to Dog types
    private HashMap<String, DogType> dogtypes = new HashMap<String, DogType>();

    @Override
    protected Persistent createObject(ResultSet rset) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public Dog createDog(String typeName) {
        assertIsValidDogTypeName(typeName);
        DogType type = getDogType(typeName);
        Dog instance = type.createInstance();
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
  
}