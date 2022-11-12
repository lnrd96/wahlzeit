package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CoordinateImplTest {

    private CartesianCoordinate cartesianCoordinate;
    private SphericCoordinate sphericCoordinate;

	@Before
	public void initCoordinate() {
		cartesianCoordinate = new CartesianCoordinate(1.0, 1.0, 1.0);
	}
	
	/**
	 *
	 */
    @Test
	public void testConstructor() {
		assertNotNull(cartesianCoordinate);
	}
	/**
	 *
	 */
    @Test
	public void testConstructorOverload() {
		CartesianCoordinate coordinate2 = new CartesianCoordinate(0.0, 0.1, 0.2);
        assertEquals(0.0, coordinate2.getX(), 0.0001);
        assertEquals(0.1, coordinate2.getY(), 0.0001);
        assertEquals(0.2, coordinate2.getZ(), 0.0001);
		SphericCoordinate coordinate3 = new SphericCoordinate(0.0, 0.1, 0.2);
        assertEquals(0.0, coordinate3.getPhi(), 0.0001);
        assertEquals(0.1, coordinate3.getTheta(), 0.0001);
        assertEquals(0.2, coordinate3.getRadius(), 0.0001);
	/**
	 *
	 */
	}
    @Test
    public void testGetterAndSetter() {
        cartesianCoordinate.setX(0.0);
        cartesianCoordinate.setY(0.3);
        cartesianCoordinate.setZ(0.6);
        assertEquals(0.0, cartesianCoordinate.getX(), 0.0001);
        assertEquals(0.3, cartesianCoordinate.getY(), 0.0001);
        assertEquals(0.6, cartesianCoordinate.getZ(), 0.0001);
        sphericCoordinate.setPhi(0.0);
        sphericCoordinate.setTheta(0.3);
        sphericCoordinate.setRadius(0.6);
        assertEquals(0.0, sphericCoordinate.getPhi(), 0.0001);
        assertEquals(0.3, sphericCoordinate.getTheta(), 0.0001);
        assertEquals(0.6, sphericCoordinate.getRadius(), 0.0001);
    }
	/**
	 *
	 */
    @Test
    public void testGetCartesianDistance(){
        cartesianCoordinate.setX(1.3);
        cartesianCoordinate.setY(2.3);
        cartesianCoordinate.setZ(3.3);
		Coordinate other = new CartesianCoordinate(4.5, 7.7, 9.1);
        double distance = cartesianCoordinate.getCartesianDistance(other);
		assertEquals(distance, 8.54634, 0.0001);
    }
    @Test
    public void testSphericalDistance(){
        // TODO
    }
	/**
	 *
	 */
    @Test
    public void testIsEqual(){
		CartesianCoordinate other = new CartesianCoordinate(1.0, 1.0, 1.0);
		assertTrue(cartesianCoordinate.isEqual(other));
		
		assertTrue(cartesianCoordinate.isEqual(cartesianCoordinate));
		
		other.setX(0.000001);
		assertFalse(cartesianCoordinate.isEqual(other));
        
        // TODO: verify near or converted Cartesian and Spheric are equal!
    }
}
