package org.wahlzeit.model;

public class SphericCoordinate implements Coordinate {
    /**
     * Attributes
     */
    private double phi;
    private double theta;
    private double radius;
    
    /**
     * Constructors
     */
    public SphericCoordinate(double phi, double theta, double radius) {
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }

    public SphericCoordinate() { }

    /**
     * Getters and Setters
     */
    public double getPhi() {
        return this.phi;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public double getTheta() {
        return this.theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
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