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
    protected Dog dog;  // dog specific properties are in the Dog class!
    protected double cutenessFactor;  // cutenessFactor is photo specific!

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
    public DogPhoto(double cutenessFactor) throws NullPointerException, IllegalStateException {
        super();
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
     * @methodtype set
	 */
    public void setCutenessFactor(double cutenessFactor) throws IllegalStateException {
        this.cutenessFactor = cutenessFactor;
        this.assertClassInvariants();
    }
	/**
     * @methodtype get
	 */
    public Dog getDog() {
        return dog;
    }
	/**
     * @methodtype set
	 */
    public void setDog(Dog dog){
        if (dog == null) {
            throw new NullPointerException("Dog is null.");
        }
        this.dog = dog;
        this.assertClassInvariants();
    }
    public void writeOn(ResultSet rset) throws SQLException, IllegalStateException, NullPointerException {
        this.assertClassInvariants();
        if (rset == null){
            // fail early
            throw new NullPointerException("ResultSet is null.");
        }
        super.writeOn(rset);
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
		cutenessFactor = rset.getDouble("cuteness_factor");
        this.assertClassInvariants();
    }
    
    protected void assertClassInvariants() throws IllegalStateException {
        if (Double.isNaN(this.cutenessFactor) || Double.isInfinite(this.cutenessFactor) ||
                    this.cutenessFactor < 0.0 || this.cutenessFactor > 10.0){
                        throw new IllegalStateException("Invalid cutenessFactor value. Must be in [0,10].");
        }
    }

}
