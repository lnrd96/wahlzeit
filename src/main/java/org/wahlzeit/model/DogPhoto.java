package org.wahlzeit.model;

import java.sql.*;
import java.net.*;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

/**
 * A photo represents a user-provided (uploaded) photo of a dog.
 */
public class DogPhoto extends Photo {
    
	/**
	 * 
	 */
    protected int age;
    protected String dogName;
    protected double cutenessFactor;

	/**
     * @methodtype constructor
	 */
    public DogPhoto(){
        super();
    }
	/**
     * @methodtype constructor
	 */
    public DogPhoto(PhotoId myId){
        super(myId);
    }
	/**
     * @methodtype constructor
	 */
    public DogPhoto(ResultSet rset) throws SQLException {
        super(rset);
        this.readFrom(rset);
    }
	/**
     * @methodtype constructor
	 */
    public DogPhoto(String dogName, int age, double cutenessFactor){
        super();
        this.age = age;
        this.dogName = dogName;
        this.cutenessFactor = cutenessFactor;
    }
	/**
     * @methodtype get
	 */
    public int getAge() {
        return age;
    }
	/**
     * @methodtype get
	 */
    public double getCutenessFactor() {
        return cutenessFactor;
    }
	/**
     * @methodtype get
	 */
    public String getName() {
        return dogName;
    }
	/**
     * @methodtype set
	 */
    public void setAge(int age) {
        this.age =  age;
    }
	/**
     * @methodtype set
	 */
    public void setCutenessFactor(double cutenessFactor) {
        this.cutenessFactor = cutenessFactor;
    }
	/**
     * @methodtype set
	 */
    public void setName(String name) {
        this.dogName = name;
    }
	/**
	 *
	 */
	public void writeOn(ResultSet rset) throws SQLException {
        super.readFrom(rset);
		rset.updateInt("dog_age", age);
		rset.updateString("dog_name", dogName);
		rset.updateDouble("cuteness_factor", cutenessFactor);
	}
    public void readFrom(ResultSet rset) throws SQLException {
        super.writeOn(rset);
        this.age = rset.getInt("dog_age");
        this.dogName = rset.getString("dog_name");
        this.cutenessFactor = rset.getDouble("cuteness_factor");
    }
}
