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
        assertClassInvariants();
    }

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
    
    /**
     * Conversion methods
     */
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        // class invariant assertion
        this.assertClassInvariants();
        
        // no pre conditions, bc no arguments to function call
        CartesianCoordinate cartesianCoordinate = doAsCartesianCoordinate(); 
        
        // class invariant assertion
        this.assertClassInvariants();

        return cartesianCoordinate;
    }
    
    protected CartesianCoordinate doAsCartesianCoordinate() {
        double x = this.radius * Math.sin(this.phi) * Math.cos(this.theta);
        double y = this.radius * Math.sin(this.phi) * Math.sin(this.theta);
        double z = this.radius * Math.cos(this.phi);
        return new CartesianCoordinate(x, y, z);  // object state guaranteed by contract
    }
    
    @Override
    public SphericCoordinate asSphericCoordinate() {
        this.assertClassInvariants();
        return this;
    }

    /**
     * Distance calculation
     */
    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        this.assertClassInvariants();
        CartesianCoordinate this_one = this.asCartesianCoordinate();
        CartesianCoordinate other = coordinate.asCartesianCoordinate();
        this.assertClassInvariants();
        return this_one.getCartesianDistance(other);
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        SphericCoordinate other = coordinate.asSphericCoordinate();
        
        // class invariant assertion
        other.assertClassInvariants();
        this.assertClassInvariants();

        
        // no pre conditions
        double angle = doGetCentralAngle(other);
        assert !Double.isNaN(angle) && angle >= 0.0 && angle <= Math.PI;  // post condition
        
        // class invariant assertion
        other.assertClassInvariants();
        this.assertClassInvariants();

        return angle;
    }
    
    public double doGetCentralAngle(SphericCoordinate other) {
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
     * class invariant assertion functions
     */
    @Override
    protected void assertClassInvariants() {
        if (Double.isNaN(theta) || Double.isNaN(radius) || Double.isNaN(phi)){
            System.out.print("Invalid object state!");
        }
        if (Math.abs(this.radius - WORLD_RADIUS_KM) > TOLERANCE * 100)
            System.out.print("Only dog photos taken on planet earth are allowed for animal right reasons.");
        if (this.theta < 0.0 || this.theta > 180.0 || this.radius < 0.0 || this.phi < 0.0 || this.phi > 360.0)
            System.out.print("Invalid value range.");
            // TODO: Throw exception or do sth else? -> in next homeowrk.
            // Problematic because the throws declaration will pull up very far in wahlzeit.
    }
    

}