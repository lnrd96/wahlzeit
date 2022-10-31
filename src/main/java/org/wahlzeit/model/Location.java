/*
* Location
*/

package org.wahlzeit.model;
import org.wahlzeit.model.Coordinate;
import org.wahlzeit.model.Photo;

import java.sql.*;

import java.util.Map;
import java.util.HashMap;

/* The Location class provides
*  information about a photo's location.
*/
public class Location {
	/**
	 * 
	 */
    public Coordinate coordinate;
    public Photo photo;

	/**
	 * 
	 */
	public Location(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public Location(Coordinate coordinate, Photo photo) {
		this.coordinate = coordinate;
		this.photo = photo;
	}
	/**
	 * 
	 */
	public void setCoordinates(double x, double y, double z) {
		coordinate.setX(x);
		coordinate.setY(y);
		coordinate.setZ(z);
	}
	public Map<String, Double> getCoordinates() {
		Map<String, Double> coordinates = new HashMap<String, Double>();
		coordinates.put("x", coordinate.getX());
		coordinates.put("y", coordinate.getY());
		coordinates.put("z", coordinate.getZ());
		return coordinates;
	}
	/**
	 * 
	 */
	public void writeOn(ResultSet rset) throws SQLException {
		rset.updateDouble("location_coordinate_x", getCoordinates().get("x"));
		rset.updateDouble("location_coordinate_y", getCoordinates().get("y"));
		rset.updateDouble("location_coordinate_z", getCoordinates().get("z"));
	}
	public void readFrom(ResultSet rset) throws SQLException {
		double x = rset.getDouble("location_coordinate_x");
		double y = rset.getDouble("location_coordinate_y");
		double z = rset.getDouble("location_coordinate_z");
		setCoordinates(x, y, z);
	}

}
