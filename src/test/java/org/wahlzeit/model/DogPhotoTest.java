package org.wahlzeit.model;

import org.wahlzeit.model.DogPhoto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class DogPhotoTest {
    private DogPhoto photo;

	@Before
	public void initPhoto() {
		photo = new DogPhoto();
	}

	/**
	 *
	 */
	@Test
	public void testConstructor() {
		assertNotNull(photo);
	}
}
