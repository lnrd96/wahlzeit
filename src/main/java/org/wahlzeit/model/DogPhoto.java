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
        if (myId == null){
            // fail early
            throw new NullPointerException("PhotoId is null.");
        }
    }
	/**
     * @methodtype constructor
	 */
    public DogPhoto(ResultSet rset) throws SQLException, NullPointerException, IllegalStateException {
        super(rset);
        if (rset == null){
            // fail early
            throw new NullPointerException("ResultSet is null.");
        }
        this.readFrom(rset);
        this.assertClassInvariants();
    }
	/**
     * @methodtype constructor
	 */
    public DogPhoto(String dogName, double cutenessFactor) throws NullPointerException, IllegalStateException {
        super();
        if (dogName == null){
            throw new NullPointerException("String `dogName` is null.");
        }
        this.dogName = dogName;
        this.cutenessFactor = cutenessFactor;
        this.assertClassInvariants();
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
    public void setCutenessFactor(double cutenessFactor) throws IllegalStateException {
        this.cutenessFactor = cutenessFactor;
        this.assertClassInvariants();
    }
	/**
     * @methodtype set
	 */
    public void setName(String name) throws IllegalStateException {
        if (name == null){
            throw new NullPointerException("String `name` for doggo is null.");
        }
        this.dogName = name;
        this.assertClassInvariants();
    }
    public void writeOn(ResultSet rset) throws SQLException, IllegalStateException, NullPointerException {
        this.assertClassInvariants();
        if (rset == null){
            // fail early
            throw new NullPointerException("ResultSet is null.");
        }
        super.writeOn(rset);
		rset.updateString("dog_name", dogName);
		rset.updateDouble("dog_cuteness_factor", cutenessFactor);
        this.assertClassInvariants();
    }
	public void readFrom(ResultSet rset) throws SQLException, IllegalStateException, NullPointerException {
        this.assertClassInvariants();
        if (rset == null){
            // fail early
            throw new NullPointerException("ResultSet is null.");
        }
        super.readFrom(rset);
		dogName = rset.getString("dog_name");
		cutenessFactor = rset.getDouble("cuteness_factor");
        this.assertClassInvariants();
    }
    
    protected void assertClassInvariants() throws IllegalStateException {
        if (this.dogName == null || this.dogName.length() < 3){
            throw new IllegalStateException("Invalid dog name: Missing or too short. " +
                                            "Give your dog a proper name, at least sth. like \"Bob\".");
        }
        if (Double.isNaN(this.cutenessFactor) || Double.isInfinite(this.cutenessFactor) ||
                    this.cutenessFactor < 0.0 || this.cutenessFactor > 10.0){
                        throw new IllegalStateException("Invalid cutenessFactor value. Must be in [0,10].");
            }
    }

}
