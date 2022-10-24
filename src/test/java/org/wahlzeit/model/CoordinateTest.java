package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CoordinateTest {

    private Coordinate coordinate;

	@Before
	public void initCoordinate() {
		coordinate = new Coordinate();
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
        coordinate.location = new Location(new Coordinate());
        assertNotNull(coordinate.location);
    }
	/**
	 *
	 */
}
