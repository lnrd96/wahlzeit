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
        this.assertClassInvariants();
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
        this.assertClassInvariants();
        return this;
    }


    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        
        CartesianCoordinate other = coordinate.asCartesianCoordinate();
        
        // class invariant assertion
        this.assertClassInvariants();
        other.assertClassInvariants();
        
        // no precondition
        double distance = doGetCartesianDistance(other);
        assert !Double.isNaN(distance) && distance >= 0.0 && distance <= WORLD_PERIMETER_KM;  // post condition

        // class invariant assertion
        this.assertClassInvariants();
        other.assertClassInvariants();
        
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
        this.assertClassInvariants();
        
        // no pre conditions
        SphericCoordinate converted = doAsSphericCoordinate();  
        // no post conditions

        // class invariant assertion
        this.assertClassInvariants();

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
        return new SphericCoordinate(phi, theta, radius);  // object state is guaranteed by other class
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        SphericCoordinate this_one = this.asSphericCoordinate();  // callee assures object state
        return this_one.getCentralAngle(coordinate); // delegation 
    }
    
    @Override
    public int hashCode() {
       
        // class invariant assertion
        this.assertClassInvariants();
        
        String pattern = "0.0000";  // somehow derive it from TOLERANCE?
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.CEILING);
        String rounded_x = df.format(x);
        String rounded_y = df.format(y);
        String rounded_z = df.format(z);
        int hashValue = Objects.hash(rounded_x, rounded_y, rounded_z);
        
        // class invariant assertion
        this.assertClassInvariants();

        return hashValue;
    }

    /**
     * class invariant assertion functions
     */
    @Override
    protected void assertClassInvariants() {
        if (Math.abs(Math.sqrt(Math.sqrt(x) + Math.sqrt(y) + Math.sqrt(z)) - WORLD_RADIUS_KM) < TOLERANCE * 100) {
            System.out.print("Only dog photos taken on planet earth are allowed for animal right reasons.");
            // throw dedicated exception from next homework on.
        }
        if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z)){
            System.out.print("Invalid object state!");
        }
    }

}