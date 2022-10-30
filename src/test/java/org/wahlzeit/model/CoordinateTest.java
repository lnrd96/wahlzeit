package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CoordinateTest {

    private Coordinate coordinate;

	@Before
	public void initCoordinate() {
		coordinate = new Coordinate(1.0, 1.0, 1.0);
	}
	
	/**
	 *
	 */
    @Test
	public void testConstructor() {
		assertNotNull(coordinate);
	}
	/**
	 *
	 */
    @Test
	public void testConstructorOverload() {
		Coordinate coordinate2 = new Coordinate(0.0, 0.1, 0.2);
        assertEquals(0.0, coordinate2.getX(), 0.0001);
        assertEquals(0.1, coordinate2.getY(), 0.0001);
        assertEquals(0.2, coordinate2.getZ(), 0.0001);
	/**
	 *
	 */
	}
    @Test
    public void testGetterAndSetter() {
        coordinate.setX(0.0);
        coordinate.setY(0.3);
        coordinate.setZ(0.6);
        assertEquals(0.0, coordinate.getX(), 0.0001);
        assertEquals(0.3, coordinate.getY(), 0.0001);
        assertEquals(0.6, coordinate.getZ(), 0.0001);
    }
	/**
	 *
	 */
    @Test
    public void testHasLocationAssociation(){
        coordinate.location = new Location(new Coordinate(1.0, 1.0, 1.0));
        assertNotNull(coordinate.location);
    }
	/**
	 *
	 */
    @Test
    public void testGetDistance(){
        coordinate.setX(1.3);
        coordinate.setY(2.3);
        coordinate.setZ(3.3);
		Coordinate other = new Coordinate(4.5, 7.7, 9.1);
        double distance = coordinate.getDistance(other);
		assertEquals(distance, 8.54634, 0.0001);
    }
	/**
	 *
	 */
    @Test
    public void testIsEqual(){
		Coordinate other = new Coordinate(1.0, 1.0, 1.0);
		assertTrue(coordinate.isEqual(other));
		
		other.setX(0.000001);
		assertFalse(coordinate.isEqual(other));
	
		assertTrue(coordinate.isEqual(coordinate));
    }
}
