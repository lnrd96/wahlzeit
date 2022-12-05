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
    public CartesianCoordinate(double x, double y, double z) throws IllegalArgumentException {
        this.x = x;
        this.y = y;
        this.z = z;
        try {
            this.assertClassInvariants();
        } catch (IllegalStateException e) {
            // convert error type for suitable user feedback
            throw new IllegalArgumentException(e.getMessage()); 
        }
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
    public CartesianCoordinate asCartesianCoordinate() throws IllegalStateException {
        this.assertClassInvariants();
        return this;
    }


    @Override
    public double getCartesianDistance(Coordinate coordinate) throws IllegalStateException, ArithmeticException {
       
        assert coordinate != null;  // pre condition       

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
    
    protected double doGetCartesianDistance(CartesianCoordinate other) throws ArithmeticException {
        double argument = Math.pow(this.getX() - other.getX(), 2)
                        + Math.pow(this.getY() - other.getY(), 2)
                        + Math.pow(this.getZ() - other.getZ(), 2);
        // pre condition Math.sqrt
        if (Double.isNaN(argument) || Double.isInfinite(argument)) {
            throw new ArithmeticException("Cartesian distance calculation failed.");
        }
        return  Math.sqrt(argument);
    }


    @Override
    public SphericCoordinate asSphericCoordinate() throws IllegalStateException {
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
    public double getCentralAngle(Coordinate coordinate) throws IllegalStateException, ArithmeticException {
        assert coordinate != null;  // pre condition       
        SphericCoordinate this_one = this.asSphericCoordinate();  // callee assures object state
        return this_one.getCentralAngle(coordinate); // delegation 
    }
    
    @Override
    public int hashCode() throws IllegalStateException {
       
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
    protected void assertClassInvariants() throws IllegalStateException {
        double radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        if (Math.abs(radius - WORLD_RADIUS_KM) > TOLERANCE * 100000) {
            throw new IllegalStateException(ERR_MSG_NOT_ON_EARTH);
            // throw dedicated exception from next homework on.
        }
        if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z)){
            throw new IllegalStateException(ERR_MSG_ATTR_NAN);
        }
    }

}