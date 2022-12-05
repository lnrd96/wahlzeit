package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate{
    /**
     * Inheritance interface
     */
    public abstract CartesianCoordinate asCartesianCoordinate() throws IllegalStateException;
    public abstract SphericCoordinate asSphericCoordinate() throws IllegalStateException;
    public abstract double getCartesianDistance(Coordinate coordinate) throws IllegalStateException, ArithmeticException;
    public abstract double getCentralAngle(Coordinate coordinate) throws IllegalStateException, ArithmeticException;
    protected abstract void assertClassInvariants() throws IllegalStateException;
    
    /**
     * Common ressources
     */
    protected final String ERR_MSG_NOT_ON_EARTH = "Coordinate location not on earth surface! " +
                                                  "Only dog photos taken on planet earth " +
                                                  "are allowed for animal right reasons.";
    protected final String ERR_MSG_ATTR_NAN = "Invalid object state! At least one critical attribute is NaN.";
    protected final String ERR_MSG_ATTR_INVALID = "Invalid object state! At least one critical attribute " +
                                                  "is has an invalid numerical value.";
    
    /**
     * Common methods / general concepts
     */

    
     @Override
    public boolean isEqual(Coordinate coordinate) throws IllegalStateException {
        assert coordinate != null;  // pre condition       
        this.assertClassInvariants();
        boolean equal = this.getCartesianDistance(coordinate) < TOLERANCE;
        this.assertClassInvariants();
        return equal;
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
        // as long as they are very near and both extend the abstract super class.
        return this.isEqual((Coordinate) o);
    }

    @Override
    public int hashCode() throws IllegalStateException {
        this.assertClassInvariants();
        int _hashCode = this.asCartesianCoordinate().hashCode();
        this.assertClassInvariants();
        return _hashCode;
    }
}
