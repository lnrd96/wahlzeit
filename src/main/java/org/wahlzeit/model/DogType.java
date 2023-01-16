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
public class DogType extends DataObject {
    /*
     * Type class providing type specific functionality.
     */
    protected String typeName;

    public DogType(String typeName) {
        this.typeName = typeName;
    }
        
    /*
     * Create and return a new instance of the Dog class
     */
    public Dog createInstance() {
        return new Dog(this);
    }
    
    public Dog createInstance(String name, int age) {
        return new Dog(name, age, this);
    }

    public int getAverageLifespan() {
        // this is just an example for type-specific functionality
        return 11;
    } 

    public double getAverageWeight() {
        // this is just an example for type-specific functionality
        return 38.0;
    }

    @Override
    public String getIdAsString() { return null; }

    @Override
    public void readFrom(ResultSet rset) throws SQLException { }

    @Override
    public void writeOn(ResultSet rset) throws SQLException { }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException { }

}
