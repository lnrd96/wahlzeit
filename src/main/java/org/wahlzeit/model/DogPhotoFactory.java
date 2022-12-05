package org.wahlzeit.model;

import java.sql.*;

/**
 * An Abstract Factory for creating dogPhotos and related objects.
 */
public class DogPhotoFactory extends PhotoFactory {
    
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	private static DogPhotoFactory instance = null;
	
	/**
	 * Public singleton access method.
	 */
    public static synchronized DogPhotoFactory getInstance() {
        if (instance == null) {
            setInstance(new DogPhotoFactory());
        }
        return instance;
    }
	
	/**
	 * Method to set the singleton instance of DogPhotoFactory.
	 */
	protected static synchronized void setInstance(DogPhotoFactory dogDogPhotoFactory) {
		if (instance != null) {
			throw new IllegalStateException("attempt to initialize DogPhotoFactory twice");
		}
		
		instance = dogDogPhotoFactory;
	}
	
	/**
	 * 
	 */
	protected DogPhotoFactory() {
		// do nothing
	}

	/**
	 * @methodtype factory
     * @override
	 */
	public DogPhoto createPhoto() {
		return new DogPhoto();
	}
    
	/**
	 * @override
	 */
	public DogPhoto createPhoto(PhotoId id) {
		if (id == null) {
			throw new NullPointerException("Dog Photo can not be created, since PhotoID is null.");
		}
		return new DogPhoto(id);
	}
	
	/**
	 * @override
	 */
	public DogPhoto createPhoto(ResultSet rs) throws SQLException , IllegalStateException{
		if (rs == null) {
			throw new NullPointerException("Dog Photo can not be created, since ResultSet is null.");
		}
		return new DogPhoto(rs);
	}

}
