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
        sphericCoordinate = new SphericCoordinate(1.0, 1.0, Coordinate.WORLD_RADIUS_KM);
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
        sphericCoordinate.setRadius(Coordinate.WORLD_RADIUS_KM);
        assertEquals(0.0, sphericCoordinate.getPhi(), 0.0001);
        assertEquals(0.3, sphericCoordinate.getTheta(), 0.0001);
        assertEquals(Coordinate.WORLD_RADIUS_KM, sphericCoordinate.getRadius(), 0.0001);
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
        sphericCoordinate.setRadius(Coordinate.WORLD_RADIUS_KM);
        sphericCoordinate.setPhi(2.3);
        sphericCoordinate.setTheta(3.3);
		Coordinate other = new SphericCoordinate(4.5, 7.7, 9.1);
        double distance = sphericCoordinate.getCentralAngle(other);
		assertEquals(distance, 1.926746, 0.00001);
    }
    @ Test 
    public void testSphericalDistanceInverse(){
        cartesianCoordinate.setX(1.3);
        cartesianCoordinate.setY(2.3);
        cartesianCoordinate.setZ(3.3);
		Coordinate other = new CartesianCoordinate(4.5, 7.7, 9.1);
        double distance = cartesianCoordinate.getCentralAngle(other);
        assertNotNull(distance);
    }
    @ Test 
    public void testGetCartesianDistanceInverse(){
        sphericCoordinate.setRadius(Coordinate.WORLD_RADIUS_KM);
        sphericCoordinate.setPhi(2.3);
        sphericCoordinate.setTheta(3.3);
		Coordinate other = new SphericCoordinate(4.5, 7.7, 9.1);
        double distance = sphericCoordinate.getCartesianDistance(other);
        assertNotNull(distance);
    }
    /*
    * 
    */
    @Test
    public void testConversion(){
        CartesianCoordinate cc = new CartesianCoordinate(3, 4, 5);
        SphericCoordinate cs = cc.asSphericCoordinate();
        assertEquals(cs.getRadius(), 7.0710678118655, 0.000001);
        System.out.print(cs.getTheta());
        assertEquals(cs.getTheta(), 0.92729521800161, 0.000001);
        assertEquals(cs.getPhi(), 0.78539816339745, 0.000001);
        
        SphericCoordinate cs_ = new SphericCoordinate(30, 60, 5);
        CartesianCoordinate cc_ = cs_.asCartesianCoordinate();
        assertEquals(cc_.getX(), 4.705070719, 0.000001);
        assertEquals(cc_.getY(), 1.505812665, 0.000001);
        assertEquals(cc_.getZ(), 0.7712572494, 0.000001);
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
        
        cartesianCoordinate.setX(2896.566953);
        cartesianCoordinate.setY(4511.135748);
        cartesianCoordinate.setZ(3442.265991);
        assertTrue(cartesianCoordinate.isEqual(sphericCoordinate));
        
        cartesianCoordinate.setX(2896.566950);
        cartesianCoordinate.setY(4511.135740);
        cartesianCoordinate.setZ(3442.265990);
        assertTrue(cartesianCoordinate.isEqual(sphericCoordinate));
    }
}
