package org.wahlzeit.model;
import java.util.Objects;

/**
 * Some Notes:
 * The names of the two classes are used only to create the objects.
 * Coordinate c = CartesianCoordinate();
 */


public class CartesianCoordinate implements Coordinate {
    /**
     * Attributes
     */
    private double x;
    private double y;
    private double z;
    
    /**
     * Constructors
     */
    public CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CartesianCoordinate() { }

    /**
     * Getters and Setters
     */
    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        CartesianCoordinate other = coordinate.asCartesianCoordinate();
        double distance = Math.sqrt(Math.pow(this.getX() - other.getX(), 2)
                                  + Math.pow(this.getY() - other.getY(), 2)
                                  + Math.pow(this.getZ() - other.getZ(), 2));
        return distance;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        double radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        double theta;
        if (x != 0.0) {
            theta = Math.atan(y / x);
        } else {
            theta = Math.atan(y / 0.000000001);
        }
        double phi = Math.atan(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) / z);
        return new SphericCoordinate(phi, theta, radius);
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        SphericCoordinate other = coordinate.asSphericCoordinate();
        return other.getCentralAngle(this);

    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return false;
    }

}