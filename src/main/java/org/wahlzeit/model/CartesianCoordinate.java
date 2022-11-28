package org.wahlzeit.model;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;



public class CartesianCoordinate extends AbstractCoordinate {
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
        this.assertClassInvariance();
    }

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
        this.assertClassInvariance();
        return this;
    }


    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        
        CartesianCoordinate other = coordinate.asCartesianCoordinate();
        
        // class invariant assertion
        this.assertClassInvariance();
        other.assertClassInvariance();
        
        // no precondition
        double distance = doGetCartesianDistance(other);
        assert distance >= 0.0;  // post condition

        // class invariant assertion
        this.assertClassInvariance();
        other.assertClassInvariance();
        
        return distance;
    }
    
    protected double doGetCartesianDistance(CartesianCoordinate other) {
        return  Math.sqrt(Math.pow(this.getX() - other.getX(), 2)
                        + Math.pow(this.getY() - other.getY(), 2)
                        + Math.pow(this.getZ() - other.getZ(), 2));
    }


    @Override
    public SphericCoordinate asSphericCoordinate() {
        // class invariant assertion
        this.assertClassInvariance();
        
        SphericCoordinate converted = doAsSphericCoordinate();
        
        // class invariant assertion
        this.assertClassInvariance();

        return converted;
    }
    
    protected SphericCoordinate doAsSphericCoordinate() {
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
        // class invariant assertion
        this.assertClassInvariance();
        SphericCoordinate other = coordinate.asSphericCoordinate();

        // no pre conditions
        double centralAngle = other.getCentralAngle(this);  // delegate to other class
        assert centralAngle >= 0.0;  // post condition

        // class invariant assertion
        this.assertClassInvariance();
        
        return centralAngle;
    }
    
    @Override
    public int hashCode() {
       
        // class invariant assertion
        this.assertClassInvariance();
        
        String pattern = "0.0000";  // somehow derive it from TOLERANCE?
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.CEILING);
        String rounded_x = df.format(x);
        String rounded_y = df.format(y);
        String rounded_z = df.format(z);
        int hashValue = Objects.hash(rounded_x, rounded_y, rounded_z);
        
        // class invariant assertion
        this.assertClassInvariance();

        return hashValue;
    }

    /**
     * class invariant assertion functions
     */
    @Override
    protected void assertClassInvariance() {
        if (Math.abs(Math.sqrt(Math.sqrt(x) + Math.sqrt(y) + Math.sqrt(z)) - WORLD_RADIUS_KM) < TOLERANCE * 100) {
            System.out.print("Only dog photos taken on planet earth are allowed for animal right reasons.");
            // throw dedicated exception from next homework on.
        }
    }

}