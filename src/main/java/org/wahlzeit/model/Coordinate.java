/*
* Coordinate
*/

package org.wahlzeit.model;

import org.wahlzeit.model.Location;

import java.lang.Math;

/* The Coordinate class stores 
*  a cartesian coordinate.
*/
public class Coordinate {
	/**
	 * 
	 */
	public Location location;
	/**
	 * 
	 */
    private double x, y, z;
	/**
	 * 
	 */
	public Coordinate(double x, double y, double z, Location location) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.location = location;
	}
	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * 
	 */
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public double getZ() { return this.z; }
	public void setX(double val) { this.x = val; }
	public void setY(double val) { this.y = val; }
	public void setZ(double val) { this.z = val; }

	/**
	 * Calculates direct cartesian distance from this coordinate
	 * to an other coordinate passed as parameter.
	 */
	protected double getDistance(Coordinate other) {
		return Math.sqrt(Math.pow(this.getX() - other.getX(), 2)
					   + Math.pow(this.getY() - other.getY(), 2)
					   + Math.pow(this.getZ() - other.getZ(), 2));
	}
	
	/**
	 * @methodtype boolean-query
	 */
	// TODO: https://www.baeldung.com/java-equals-hashcode-contracts
	//		 and take count of precision, f.e. use a delta.
	protected boolean isEqual(Coordinate other) {
		if(this.getX() != other.getX()){
			return false;
		}
		else if(this.getY() != other.getY()){
			return false;
		}
		else if(this.getZ() != other.getZ()){
			return false;
		}
		else {
			return true;
		}
	}

	/*
	* Is overriding the method `equals()` of the Object class.
	*/
	@Override
    public boolean equals(Object obj) {
  	    if (this == obj)
 		    return true;
 	    if (obj == null)
 		    return false;
 	    if (getClass() != obj.getClass())
 		    return false;
 	    Coordinate other = (Coordinate) obj;
 	    return this.isEqual(other);
    }
		
}
