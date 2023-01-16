package org.wahlzeit.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.utils.PatternInstance;


@PatternInstance (
	patternName = "Type Object",
	participants = {"Dog", "DogType", "DogManager"}
)
public class DogType extends DataObject {
    /*
     * Type class providing type specific functionality.
     */
    protected String typeName;
    private DogType supertype = null;
    private Set<DogType> subtypes = new HashSet<DogType>();

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

    /**
     * Functions to handle and inspect the type hierarchie
     */
    // Getter for supertype
    public DogType getSupertype() {
        return supertype;
    }

    // Setter for supertype
    public void setSupertype(DogType supertype) {
        this.supertype = supertype;
    }

    // Getter for subtypes
    public Set<DogType> getSubtypes() {
        return subtypes;
    }

    // Setter for subtypes
    public void setSubtypes(Set<DogType> subtypes) {
        this.subtypes = subtypes;
    }

    // Add a subtype
    public void addSubtype(DogType subtype) {
        subtypes.add(subtype);
    }

    // Remove a subtype
    public void removeSubtype(DogType subtype) {
        subtypes.remove(subtype);
    }

    // Check if the type has a subtype
    public boolean hasSubtype(DogType subtype) {
        return subtypes.contains(subtype);
    }

    /**
     * Type specific methods
     */
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
