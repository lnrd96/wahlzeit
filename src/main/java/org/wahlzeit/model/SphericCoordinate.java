package org.wahlzeit.model;
import java.util.Objects;
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
        return this;
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        SphericCoordinate other = coordinate.asSphericCoordinate();
        double phi_1 = this.getPhi();
        double phi_2 = other.getPhi();
        double theta_1 = this.getTheta();  // longitude $\lambda \in [-180, 180]$ degree
        double theta_2 = other.getTheta();
        double delta_lambda = Math.abs(theta_1 - theta_2);
        double delta_phi = Math.abs(phi_1 - theta_2);
        return 2 * Math.asin(
            Math.sqrt(
                Math.pow(Math.sin((delta_phi / 2)), 2) + 
                (1 - Math.pow(Math.sin((delta_phi / 2)), 2) - 
                Math.pow(Math.sin((phi_1 + phi_2) / 2), 2)) *
                Math.pow(Math.sin(delta_lambda / 2), 2)
            )
        );
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return this.getCartesianDistance(coordinate) < TOLERANCE;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Coordinate)) {
            return false;
        }
        // Returns true even if they are different classes
        // as long as they are very near and both implementing the interface.
        return this.isEqual((Coordinate) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phi, theta, radius);
    }

}