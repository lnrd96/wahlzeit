/*
* Coordinate
*/

package org.wahlzeit.model;
import org.wahlzeit.model.Location;

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
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public double getZ() { return this.z; }
	public void setX(double val) { this.x = val; }
	public void setY(double val) { this.y = val; }
	public void setZ(double val) { this.z = val; }
}
