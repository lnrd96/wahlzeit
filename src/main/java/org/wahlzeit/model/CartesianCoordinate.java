package org.wahlzeit.model;
import java.util.Objects;

public class CartesianCoordinate implements Coordinate {
    /**
     * Attributes
     */
    private double x;
    private double y;
    private double radius;
    
    /**
     * Constructors
     */
    public CartesianCoordinate(double phi, double theta, double radius) {
        this.x = phi;
        this.y = theta;
        this.radius = radius;
    }

    public CartesianCoordinate() { }

    /**
     * Getters and Setters
     */
    public double getX() {
        return this.x;
    }

    public void setX(double phi) {
        this.x = phi;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double theta) {
        this.y = theta;
    }

    public double getZ() {
        return this.radius;
    }

    public void setZ(double radius) {
        this.radius = radius;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return null;
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return 0;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return null;
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        return 0;
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return false;
    }

}