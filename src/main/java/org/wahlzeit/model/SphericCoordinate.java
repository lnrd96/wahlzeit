package org.wahlzeit.model;
import java.util.Objects;

import org.wahlzeit.handlers.CoordinateHandler;

import static java.lang.Math.*;

public final class SphericCoordinate extends AbstractCoordinate {
    /**
     * Attributes
     */
    private final double phi;
    private final double theta;
    private final double radius;
    private final CoordinateHandler handler;
    
    /**
     * Constructors
     */
    public SphericCoordinate(double phi, double theta, double radius) throws IllegalArgumentException {
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
        try {
            assertClassInvariants();
        } catch (IllegalStateException e) {
            // convert error type for suitable user feedback
            throw new IllegalArgumentException(e.getMessage()); 
        }
        handler = CoordinateHandler.getInstance();
    }

    /**
     * Getters and Setters
     */
    public double getPhi() {
        return this.phi;
    }

    public SphericCoordinate setPhi(double phi) {
        return handler.getSphericCoordinate(phi, this.theta, this.radius);
    }

    public double getTheta() {
        return this.theta;
    }

    public SphericCoordinate setTheta(double theta) {
        return handler.getSphericCoordinate(this.phi, theta, this.radius);
    }

    public double getRadius() {
        return this.radius;
    }

    public SphericCoordinate setRadius(double radius) {
        return handler.getSphericCoordinate(this.phi, this.theta, radius);
    }
    
    /**
     * Conversion methods
     */
    @Override
    public CartesianCoordinate asCartesianCoordinate() throws IllegalStateException {
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
    public SphericCoordinate asSphericCoordinate() throws IllegalStateException {
        this.assertClassInvariants();
        return this;
    }

    /**
     * Distance calculation
     */
    @Override
    public double getCartesianDistance(Coordinate coordinate) throws IllegalStateException, ArithmeticException {
        assert coordinate != null;  // pre condition       
        CartesianCoordinate this_one = this.asCartesianCoordinate();  // callee assures object state
        return this_one.getCartesianDistance(coordinate);  // delegation
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) throws IllegalStateException, ArithmeticException {
        assert coordinate != null;  // pre condition       
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
    
    public double doGetCentralAngle(SphericCoordinate other) throws ArithmeticException {
        double phi_1 = this.getPhi();
        double phi_2 = other.getPhi();
        double theta_1 = this.getTheta();  // longitude $\lambda \in [-180, 180]$ degree
        double theta_2 = other.getTheta();
        double delta_lambda = Math.abs(theta_1 - theta_2);
        double delta_phi = Math.abs(phi_1 - theta_2);
        double argument = Math.sqrt(
                Math.pow(Math.sin((delta_phi / 2)), 2) + 
                (1 - Math.pow(Math.sin((delta_phi / 2)), 2) - 
                Math.pow(Math.sin((phi_1 + phi_2) / 2), 2)) *
                Math.pow(Math.sin(delta_lambda / 2), 2)
            );
        // pre condition for Math.asin
        if (Double.isNaN(argument)) {
            throw new ArithmeticException("Central angle computation failed.");
        }
        return 2 * Math.asin(argument);
    }
    

    /**
     * class invariant assertion functions
     */
    @Override
    protected void assertClassInvariants() throws IllegalStateException {
        if (Double.isNaN(theta) || Double.isNaN(radius) || Double.isNaN(phi)){
            throw new IllegalStateException(ERR_MSG_ATTR_NAN);
        }

        if (Math.abs(this.radius - WORLD_RADIUS_KM) > TOLERANCE * 100000)
            throw new IllegalStateException(ERR_MSG_NOT_ON_EARTH);

        if (this.theta < 0.0 || this.theta > 180.0 || this.radius < 0.0 || this.phi < 0.0 || this.phi > 360.0)
            throw new IllegalStateException(ERR_MSG_ATTR_INVALID);
    }
    

}