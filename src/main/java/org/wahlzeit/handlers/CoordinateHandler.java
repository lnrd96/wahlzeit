package org.wahlzeit.handlers;
import org.wahlzeit.model.*;
import org.wahlzeit.services.*;
import org.wahlzeit.services.ObjectManager.*;

import java.sql.*;
import java.util.HashMap;

/* 
 * CoordinateHandler assures the sharing of coordinate value objects
 * among all Location instances.
*/

public class CoordinateHandler {

    private static final CoordinateHandler INSTANCE = new CoordinateHandler();
    public static CoordinateHandler getInstance() {
        return INSTANCE;
    }

    private HashMap<Integer, Coordinate> coordinateCache = new HashMap<Integer, Coordinate>();

    public CartesianCoordinate getCartesianCoordinate(double x, double y, double z) {
        // create
        Coordinate coordinate = new CartesianCoordinate(x, y, z);
        // see if exists
        Coordinate cached_coordinate = coordinateCache.get(coordinate.hashCode());
        if (cached_coordinate != null) {
            // destroy new one and return existing coordinate
            return cached_coordinate.asCartesianCoordinate();
        } else {
            // return new one
            coordinateCache.put(coordinate.hashCode(), coordinate);
            return (CartesianCoordinate) coordinate;
        }
    }
    
    public SphericCoordinate getSphericCoordinate(double phi, double theta, double radius) {
        // create
        Coordinate coordinate = new SphericCoordinate(phi, theta, radius);
        // see if exists
        Coordinate cached_coordinate = coordinateCache.get(coordinate.hashCode());
        if (cached_coordinate != null) {
            // destroy new one and return existing coordinate
            return cached_coordinate.asSphericCoordinate();
        } else {
            // return new one and put it in cache
            coordinateCache.put(coordinate.hashCode(), coordinate);
            return (SphericCoordinate) coordinate;
        }
    }
}
