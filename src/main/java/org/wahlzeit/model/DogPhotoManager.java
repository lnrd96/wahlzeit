package org.wahlzeit.model;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.wahlzeit.model.DogPhoto;
import org.wahlzeit.main.*;
import org.wahlzeit.services.*;

public class DogPhotoManager extends PhotoManager {
    
	/**
	 * 
	 */
	protected static final DogPhotoManager instance = new DogPhotoManager();

	/**
	 * In-memory cache for photos
     * @override
	 */
	protected Map<PhotoId, DogPhoto> photoCache = new HashMap<PhotoId, DogPhoto>();

    public DogPhotoManager() {
        photoTagCollector = DogPhotoFactory.getInstance().createPhotoTagCollector();
    }
	
    /**
	 * @methodtype get
	 * @methodproperties primitive
     * @override
	 */
	protected DogPhoto doGetPhotoFromId(PhotoId id) {
		return photoCache.get(id);
	}
	
    /**
	 * @override
	 */
	public DogPhoto getPhotoFromId(PhotoId id) {
		if (id.isNullId()) {
			return null;
		}
        
        // cache hit
		DogPhoto result = doGetPhotoFromId(id);
		
        // cache miss -> query db
		if (result == null) {
			try {
				PreparedStatement stmt = getReadingStatement("SELECT * FROM dog_photos WHERE id = ?");
				result = (DogPhoto) readObject(stmt, id.asInt());
			} catch (SQLException sex) {
				SysLog.logThrowable(sex);
			}
			if (result != null) {
				doAddPhoto(result);
			}
		}
		
		return result;
	}
	
    /**
	 * @override
	 */
	protected DogPhoto createObject(ResultSet rset) throws SQLException {
		return DogPhotoFactory.getInstance().createPhoto(rset);
	}
	
    /**
	 * @methodtype command
	 * @methodproperties primitive
     * @override
	 */
	protected void doAddPhoto(DogPhoto myPhoto) {
		photoCache.put(myPhoto.getId(), myPhoto);
	}
	
    /**
	 * @methodtype command
	 * @override
	 * Load all persisted photos. Executed when Wahlzeit is restarted.
	 */
	public void addPhoto(DogPhoto photo) {
		PhotoId id = photo.getId();
		assertIsNewPhoto(id);
		doAddPhoto(photo);

		try {
			PreparedStatement stmt = getReadingStatement("INSERT INTO dog_photos(id) VALUES(?)");
			createObject(photo, stmt, id.asInt());  // super-super method
			ServiceMain.getInstance().saveGlobals();
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	
    /**
	 * @methodtype command
	 */
	public void loadDogPhotos(Collection<DogPhoto> result) {
		try {
			PreparedStatement stmt = getReadingStatement("SELECT * FROM dog_photos");
			readObjects(result, stmt);
			for (Iterator<DogPhoto> i = result.iterator(); i.hasNext(); ) {
				DogPhoto photo = i.next();
				if (!doHasPhoto(photo.getId())) {
					doAddPhoto(photo);
				} else {
					SysLog.logSysInfo("dog_photo", photo.getId().asString(), "dog photo had already been loaded");
				}
			}
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
		
		SysLog.logSysInfo("loaded all dog photos");
	}
	
	/**
	 * 
	 */
	public void savePhoto(DogPhoto photo) {
		try {
			PreparedStatement stmt = getUpdatingStatement("SELECT * FROM dog_photos WHERE id = ?");
			updateObject(photo, stmt);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	
    /**
	 *
	 */
	public void savePhotos() {
		try {
			PreparedStatement stmt = getUpdatingStatement("SELECT * FROM dog_photos WHERE id = ?");
			updateObjects(photoCache.values(), stmt);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	
    /**
	 * @methodtype command
	 *
	 * Persists all available sizes of the Photo. If one size exceeds the limit of the persistence layer, e.g. > 1MB for
	 * the Datastore, it is simply not persisted.
	 */
	public Set<DogPhoto> findDogPhotosByOwner(String ownerName) {
		Set<DogPhoto> result = new HashSet<DogPhoto>();
		try {
			PreparedStatement stmt = getReadingStatement("SELECT * FROM photos WHERE owner_name = ? AND dog_photo_id IS NOT NULL");
			readObjects(result, stmt, ownerName);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
		
		for (Iterator<DogPhoto> i = result.iterator(); i.hasNext(); ) {
			doAddPhoto(i.next());
		}

		return result;
	}
	
    /**
	 * 
	 */
	public DogPhoto getVisiblePhoto(PhotoFilter filter) {
		DogPhoto result = getPhotoFromFilter(filter);
		
		if(result == null) {
			java.util.List<PhotoId> list = getFilteredPhotoIds(filter);
			filter.setDisplayablePhotoIds(list);
			result = getPhotoFromFilter(filter);
		}

		return result;
	}
	
    /**
	 * 
	 */
	protected DogPhoto getPhotoFromFilter(PhotoFilter filter) {
		PhotoId id = filter.getRandomDisplayablePhotoId();
		DogPhoto result = getPhotoFromId(id);
		while((result != null) && !result.isVisible()) {
			id = filter.getRandomDisplayablePhotoId();
			result = getPhotoFromId(id);
			if ((result != null) && !result.isVisible()) {
				filter.addProcessedPhoto(result);
			}
		}
		
		return result;
	}
	
		
	/**
	 * 
	 */
	public DogPhoto createPhoto(File file) throws Exception {
		PhotoId id = PhotoId.getNextId();
		DogPhoto result = (DogPhoto) PhotoUtil.createPhoto(file, id);
		addPhoto(result);
		return result;
	}

}
