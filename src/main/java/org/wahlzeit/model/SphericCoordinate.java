package org.wahlzeit.model;
import static java.lang.Math.*;

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
        double x = this.radius * Math.sin(this.phi) * Math.cos(this.theta);
        double y = this.radius * Math.sin(this.phi) * Math.sin(this.theta);
        double z = this.radius * Math.cos(this.phi);
        return new CartesianCoordinate(x, y, z);
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        CartesianCoordinate this_one = this.asCartesianCoordinate();
        CartesianCoordinate other = coordinate.asCartesianCoordinate();
        double distance = Math.sqrt(Math.pow(this_one.getX() - other.getX(), 2)
                                  + Math.pow(this_one.getY() - other.getY(), 2)
                                  + Math.pow(this_one.getZ() - other.getZ(), 2));
        return distance;
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