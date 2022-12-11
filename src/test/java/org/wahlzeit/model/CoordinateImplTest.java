package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.handlers.CoordinateHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
public class CoordinateImplTest {

    private CartesianCoordinate cartesianCoordinate_this;
    private CartesianCoordinate cartesianCoordinate_other;
    private SphericCoordinate sphericCoordinate;
    private CoordinateHandler handler;

	@Before
	public void initCoordinate() {
        double x = 5929;
        double y = 1939;
        double z = 1298;
		cartesianCoordinate_this = new CartesianCoordinate(x, y, z);
		cartesianCoordinate_other = new CartesianCoordinate(x, z, y);
        sphericCoordinate = new SphericCoordinate(1.0, 1.0, Coordinate.WORLD_RADIUS_KM);
        handler = CoordinateHandler.getInstance();
	}
	
	/**
	 *
	 */
    @Test
	public void testConstructor() {
		assertNotNull(cartesianCoordinate_this);
	}
	/**
	 *
	 */
    @Test
	public void testConstructorOverload() {
		CartesianCoordinate coordinate2 = new CartesianCoordinate(0.0, 0.1, 6371);
        assertEquals(0.0, coordinate2.getX(), 0.0001);
        assertEquals(0.1, coordinate2.getY(), 0.0001);
        assertEquals(6371, coordinate2.getZ(), 0.0001);
		SphericCoordinate coordinate3 = new SphericCoordinate(0.0, 0.1, Coordinate.WORLD_RADIUS_KM); 
        assertEquals(0.0, coordinate3.getPhi(), 0.0001);
        assertEquals(0.1, coordinate3.getTheta(), 0.0001);
        assertEquals(Coordinate.WORLD_RADIUS_KM, coordinate3.getRadius(), 0.0001);
	}
	/**
	 *
	 */
    @Test
    public void testGetterAndSetter() {
        CartesianCoordinate c1 = handler.getCartesianCoordinate(0.0, 0.1, Coordinate.WORLD_RADIUS_KM);
        Coordinate c3 = handler.getCartesianCoordinate(0.0, 0.2, Coordinate.WORLD_RADIUS_KM);
        Coordinate c2 = c1.setY(0.2);
        assert c2 == c3;
        SphericCoordinate s1 = new SphericCoordinate(0.0, 0.3, Coordinate.WORLD_RADIUS_KM);
        assertEquals(0.0, s1.getPhi(), 0.0001);
        assertEquals(0.3, s1.getTheta(), 0.0001);
        assertEquals(Coordinate.WORLD_RADIUS_KM, sphericCoordinate.getRadius(), 0.0001);
    }
	/**
	 *
	 */
    @Test
    public void testObjectSharing() {
        Coordinate c1 = handler.getCartesianCoordinate(0.0, 0.1, Coordinate.WORLD_RADIUS_KM);
        Coordinate c2 = handler.getCartesianCoordinate(0.0, 0.1, Coordinate.WORLD_RADIUS_KM);
        assert c1 == c2;
        c1 = handler.getCartesianCoordinate(0.0, 0.2, Coordinate.WORLD_RADIUS_KM);
        c2 = handler.getCartesianCoordinate(0.0, 0.1, Coordinate.WORLD_RADIUS_KM);
        assert c1 != c2;
    }
	/**
	 *
	 */
    @Test
    public void testGetCartesianDistance(){
        double distance = cartesianCoordinate_this.getCartesianDistance(cartesianCoordinate_other);
		assertEquals(distance, 906.510893, 0.0001);
    }
    @Test
    public void testSphericalDistance(){
        sphericCoordinate.setRadius(Coordinate.WORLD_RADIUS_KM);
        sphericCoordinate.setPhi(2.3);
        sphericCoordinate.setTheta(3.3);
		Coordinate other = new SphericCoordinate(4.5, 7.7, Coordinate.WORLD_RADIUS_KM);
        double distance = sphericCoordinate.getCentralAngle(other);
		assertEquals(0.564412881256, distance, 0.00001);
    }
    @ Test 
    public void testSphericalDistanceInverse(){
        double distance = cartesianCoordinate_this.getCentralAngle(cartesianCoordinate_this);
        assertNotNull(distance);
    }
    @ Test 
    public void testGetCartesianDistanceInverse(){
        sphericCoordinate.setRadius(Coordinate.WORLD_RADIUS_KM);
        sphericCoordinate.setPhi(2.3);
        sphericCoordinate.setTheta(3.3);
		Coordinate other = new SphericCoordinate(4.5, 7.7, Coordinate.WORLD_RADIUS_KM);
        double distance = sphericCoordinate.getCartesianDistance(other);
        assertNotNull(distance);
    }
    /*
    * 
    */
    @Test
    public void testConversion(){
        SphericCoordinate cs = cartesianCoordinate_this.asSphericCoordinate();
        assertEquals(cs.getTheta(), 0.316073, 0.000001);
        assertEquals(cs.getPhi(), 1.365645, 0.000001);
        
        SphericCoordinate cs_ = new SphericCoordinate(30, 60, Coordinate.WORLD_RADIUS_KM);
        CartesianCoordinate cc_ = cs_.asCartesianCoordinate();
        assertEquals(cc_.getX(), 5995.201110, 0.000001);
        assertEquals(cc_.getY(), 1918.706498, 0.000001);
        assertEquals(cc_.getZ(), 982.735987, 0.000001);
    }
    
	/**
	 *
	 */
    @Test
    public void testIsEqual(){
		CartesianCoordinate other = new CartesianCoordinate(cartesianCoordinate_this.getX(),
                                                            cartesianCoordinate_this.getY(), 
                                                            cartesianCoordinate_this.getZ() + 0.00001);
		assertTrue(cartesianCoordinate_this.isEqual(other));
		
		assertTrue(cartesianCoordinate_this.isEqual(cartesianCoordinate_this));
        
        double y = other.getY();
        double x = other.getX();
        double z = cartesianCoordinate_this.getZ();
        other = handler.getCartesianCoordinate(y, x, z);
		assertFalse(cartesianCoordinate_this.isEqual(other));
        // assert hashcode is not equal if coordinates are equal
		assertFalse(cartesianCoordinate_this.hashCode() == other.hashCode());

        
        cartesianCoordinate_this = handler.getCartesianCoordinate(2896.566953, 4511.135748, 3442.265991);
        int hash_1 = cartesianCoordinate_this.hashCode();
        assertTrue(cartesianCoordinate_this.isEqual(sphericCoordinate));
        
        cartesianCoordinate_this.setX(2896.566950);
        cartesianCoordinate_this.setY(4511.135740);
        cartesianCoordinate_this.setZ(3442.265990);
        int hash_2 = cartesianCoordinate_this.hashCode();
        assertTrue(cartesianCoordinate_this.isEqual(sphericCoordinate));
        
        // assert hashcode is equal if coordinates are equal
        assertTrue(hash_1 == hash_2);


    }
}
