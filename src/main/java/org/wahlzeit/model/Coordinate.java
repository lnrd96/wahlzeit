package org.wahlzeit.model;

public interface Coordinate {
    public abstract CartesianCoordinate asCartesianCoordinate();
    public abstract double getCartesianDistance(Coordinate coordinate);
    public abstract SphericCoordinate asSphericCoordinate();
    public abstract double getCentralAngle(Coordinate coordinate);
    public abstract boolean isEqual(Coordinate coordinate);
}
