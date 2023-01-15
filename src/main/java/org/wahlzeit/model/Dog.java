package org.wahlzeit.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.utils.PatternInstance;


@PatternInstance (
	patternName = "Type Object",
	participants = {"Dog, DogType, DogManager"}
)
public class Dog extends DataObject {
    /**
     * Domain class providing instance specific functionality.
     */
    
    protected DogType type = null;
    private String id;
    
    private String name;
    private int age;

    public Dog(String id, String name, int age, DogType type) {
        setId(id);
        setName(name);
        setAge(age);
        setType(type);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DogType getType() {
        return type;
    }

    public void setType(DogType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null){
            throw new NullPointerException("String `name` for doggo is null :-( !");
        }
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getIdAsString() {
        return getId();
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
		name = rset.getString("dog_name");
        
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        if (rset == null){
            // fail early
            throw new NullPointerException("ResultSet is null.");
        }
		rset.updateString("dog_name", name);
        
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        // Not necessary, because Wahlzeit is just an example application
        // to practice different aspects of design and advanced programming and
        // persitence is not a subject of the current exercise.
    }

}
