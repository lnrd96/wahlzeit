package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate{
    public abstract CartesianCoordinate asCartesianCoordinate();
    public abstract double getCartesianDistance(Coordinate coordinate);
    public abstract SphericCoordinate asSphericCoordinate();
    public abstract double getCentralAngle(Coordinate coordinate);
    public abstract boolean isEqual(Coordinate coordinate);
    public static final double TOLERANCE = 0.00001;  // for isEqual()
    public static final double WORLD_RADIUS_KM = 6371;  // for sanity check
}
