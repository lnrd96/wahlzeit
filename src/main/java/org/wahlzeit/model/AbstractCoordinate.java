package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate{
    /**
     * Inheritance interface
     */
    public abstract CartesianCoordinate asCartesianCoordinate();
    public abstract SphericCoordinate asSphericCoordinate();
    public abstract double getCartesianDistance(Coordinate coordinate);
    public abstract double getCentralAngle(Coordinate coordinate);
    public static final double TOLERANCE = 0.00001;  // for isEqual()
    public static final double WORLD_RADIUS_KM = 6371;  // for sanity check
    
    /**
     * Common methods / general concepts
     */

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
        // as long as they are very near and both extend the abstract super class.
        return this.isEqual((Coordinate) o);
    }

    @Override
    public int hashCode() {
        return this.asCartesianCoordinate().hashCode();
    }
}
