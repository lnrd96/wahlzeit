package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LocationTest {

    private Location location;

    @Before
    public void initLocation() {
        location = new Location(new CartesianCoordinate(1.0, 1.0, 1.0));
    }

    @Test
    public void testConstructor() {
        assertNotNull(location);
    }
	/**
	 * 
	 */
    @Test
    public void testConstructor2() {
        Location l2 = new Location(new CartesianCoordinate(1.0, 1.0, 1.0), new Photo());
        assertNotNull(l2);
    }
	/**
	 * 
	 */
    @Test
    public void testHasCoordinateAssociation() {
        assertNotNull(location.coordinate);
    }
	/**
	 * 
	 */
    @Test
    public void testHasPhotoAssociation() {
        location.photo = new Photo();
        assertNotNull(location.photo);
    }
    
}

