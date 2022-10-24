package org.wahlzeit.model;

import org.wahlzeit.model.Photo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
}
