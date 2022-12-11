/*
* Location
*/

package org.wahlzeit.model;
import org.wahlzeit.model.Coordinate;
import org.wahlzeit.model.Photo;
import org.wahlzeit.services.DataObject;
import org.wahlzeit.handlers.CoordinateHandler;

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
	public CoordinateHandler coordinateHandler;

	/**
	 * 
	 */
	public Location(Coordinate coordinate) {
		this.coordinate = coordinate;
		this.coordinateHandler = CoordinateHandler.getInstance();
	}
	public Location(Coordinate coordinate, Photo photo) {
		this.coordinate = coordinate;
		this.photo = photo;
		this.coordinateHandler = CoordinateHandler.getInstance();
	}
	/**
	 * 
	 */
	public void setCartesianCoordinate(double x, double y, double z) {
		this.coordinate = coordinateHandler.getCartesianCoordinate(x, y, z);
	}
	public void setSphericCoordinate(double phi, double theta, double radius) {
		this.coordinate = coordinateHandler.getSphericCoordinate(phi, theta, radius);
	}
	public Map<String, Double> getCartesianCoordinates() {
		Map<String, Double> coordinates = new HashMap<String, Double>();
		coordinates.put("x", coordinate.asCartesianCoordinate().getX());
		coordinates.put("y", coordinate.asCartesianCoordinate().getY());
		coordinates.put("z", coordinate.asCartesianCoordinate().getZ());
		return coordinates;
	}
	public Map<String, Double> getSphericCoordinates() {
		Map<String, Double> coordinates = new HashMap<String, Double>();
		coordinates.put("phi",    coordinate.asSphericCoordinate().getPhi());
		coordinates.put("theta",  coordinate.asSphericCoordinate().getTheta());
		coordinates.put("radius", coordinate.asSphericCoordinate().getRadius());
		return coordinates;
	}
	/**
	 * 
	 */
	public void writeOn(ResultSet rset) throws SQLException {
		rset.updateDouble("location_coordinate_x", getCartesianCoordinates().get("x"));
		rset.updateDouble("location_coordinate_y", getCartesianCoordinates().get("y"));
		rset.updateDouble("location_coordinate_z", getCartesianCoordinates().get("z"));
		rset.updateDouble("location_coordinate_phi",    getSphericCoordinates().get("phi"));
		rset.updateDouble("location_coordinate_theta",  getSphericCoordinates().get("theta"));
		rset.updateDouble("location_coordinate_radius", getSphericCoordinates().get("radius"));
	}
	public void readFrom(ResultSet rset) throws SQLException {
		double x = rset.getDouble("location_coordinate_x");
		double y = rset.getDouble("location_coordinate_y");
		double z = rset.getDouble("location_coordinate_z");
		setCartesianCoordinate(x, y, z);
		double phi = rset.getDouble("location_coordinate_phi");
		double theta = rset.getDouble("location_coordinate_theta");
		double radius = rset.getDouble("location_coordinate_radius");
		setSphericCoordinate(phi, theta, radius);
	}

}
