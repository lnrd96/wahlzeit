package org.wahlzeit.model;

import org.wahlzeit.model.Photo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class PhotoTest {
	
    private Photo photo;

	@Before
	public void initPhoto() {
		photo = new Photo();
	}

	/**
	 *
	 */
	@Test
	public void testConstructor() {
		assertNotNull(photo);

		// Check properties after creation
		assertEquals(10, photo.praiseSum);
		assertEquals(1, photo.noVotes);
	}

	/**
	 *
	 */
	@Test
	public void testStaticProperties() {
		assertEquals("image", Photo.IMAGE);
		assertEquals("thumb", Photo.THUMB);
		assertEquals("link", Photo.LINK);
		assertEquals("praise", Photo.PRAISE);
		assertEquals("noVotes", Photo.NO_VOTES);
		assertEquals("caption", Photo.CAPTION);
		assertEquals("description", Photo.DESCRIPTION);
		assertEquals("keywords", Photo.KEYWORDS);
	}
	/**
	 *
	 */
	@Test
    public void testSetWidthAndHeight() {
        photo.setWidthAndHeight(12, 13);
        assertEquals(photo.getWidth(), 12);
        assertEquals(photo.getHeight(), 13);
    }
	
	/**
	 *
	 */
	// @Test
    // public void testPersistance() {
	// 	// make photo
	// 	Photo photo_created = new Photo();
	// 	photo_created.setOwnerId(666);
	// 	photo_created.setOwnerName("Jack o' Lantern");
	// 	photo_created.setLocationCoordinates(31.0, 10.0, 666.666);
	// 	PhotoId idCreated = photo_created.getId();
		
	// 	// save photo to db
	// 	PhotoManager photoManager = PhotoManager.getInstance();
	// 	photoManager.savePhoto(photo_created);

	// 	// retrieve photo from db by id
	// 	Photo photo_retrieved = photoManager.getPhotoFromId(idCreated);
		
	// 	// assert
	// 	Coordinate coordinate_created = photo_created.location.coordinate;
	// 	Coordinate coordinate_retrieved = photo_retrieved.location.coordinate;
	// 	assertTrue(coordinate_created.isEqual(coordinate_retrieved));

	// 	assertEquals(photo_created.getOwnerName(), photo_retrieved.getOwnerName());
	// 	assertEquals(photo_created.getOwnerId(), photo_retrieved.getOwnerId());
    // }
}
