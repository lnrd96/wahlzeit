package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate{
    /**
     * Inheritance interface
     */
    public abstract CartesianCoordinate asCartesianCoordinate();
    public abstract SphericCoordinate asSphericCoordinate();
    public abstract double getCartesianDistance(Coordinate coordinate);
    public abstract double getCentralAngle(Coordinate coordinate);
    protected abstract void assertClassInvariants();
    
    /**
     * Common methods / general concepts
     */

    
     @Override
    public boolean isEqual(Coordinate coordinate) {
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
    public int hashCode() {
        this.assertClassInvariants();
        int _hashCode = this.asCartesianCoordinate().hashCode();
        this.assertClassInvariants();
        return _hashCode;
    }
}
