package org.wahlzeit.model;
import java.util.Objects;
import static java.lang.Math.*;

public class SphericCoordinate extends AbstractCoordinate {
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
        checkSphericalValueRange();
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
        checkSphericalValueRange();
    }

    public double getTheta() {
        return this.theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
        checkSphericalValueRange();
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        checkSphericalValueRange();
    }
    
    /**
     * Conversion methods
     */
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        double x = this.radius * Math.sin(this.phi) * Math.cos(this.theta);
        double y = this.radius * Math.sin(this.phi) * Math.sin(this.theta);
        double z = this.radius * Math.cos(this.phi);
        return new CartesianCoordinate(x, y, z);
    }
    
    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    /**
     * Distance calculation
     */
    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        CartesianCoordinate this_one = this.asCartesianCoordinate();
        CartesianCoordinate other = coordinate.asCartesianCoordinate();
        return this_one.getCartesianDistance(other);
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
    
    /**
     * Helper methods
     */
    private void checkSphericalValueRange() {
        if (Math.abs(this.radius - 6300) < TOLERANCE * 100)
            System.out.print("Only dog photos taken on planet earth are allowed for animal right reasons.");
        if (this.theta < 0.0 || this.theta > 180.0 || this.radius < 0.0 || this.phi < 0.0 || this.phi > 360.0)
            System.out.print("Invalid value range.");
            // TODO: Throw exception or do sth else?
            // Problematic because the throws declaration will pull up very far in wahlzeit.
    }
    

}