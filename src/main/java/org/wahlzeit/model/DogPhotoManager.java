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
	 * 
	 */
	public static final DogPhotoManager getInstance() {
		return instance;
	}
	
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
		if (id == null){  // pre condition for HashMap, fail early with meaningfull error message.
			throw new NullPointerException("Can not get dog photo as PhotoId is null.");
		}
		return photoCache.get(id);
	}
	
    /**
     * @override
	 */
	public DogPhoto getPhotoFromId(PhotoId id) {
		if (id.isNullId()) {
			throw new IllegalArgumentException("Can not get dog photo as passed PhotoId is set to 0-id.");
		}
        
        // cache hit
		DogPhoto result = null;
		try {
			result = doGetPhotoFromId(id);  // checked by callee
		} catch (IllegalArgumentException e) {
			// query db instead
			result = null;
		}
		
        // cache miss -> query db
		if (result == null) {
			for (int i = 0; i < 3; i++) {  // retry three times
				try {
					PreparedStatement stmt = getReadingStatement("SELECT * FROM photos WHERE id = ?");
					result = (DogPhoto) readObject(stmt, id.asInt());
					break;
				} catch (SQLException sex) {
					SysLog.logThrowable(sex);
				}
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
		if (rset == null){
			throw new NullPointerException("DogPhoto can not be created from ResultSet as it is null.");
		}
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
		if (photo == null){
			throw new NullPointerException("Can not add photo as it is null.");
		}
		PhotoId id = photo.getId();
			assertIsNewPhoto(id);
		doAddPhoto(photo);

		try {
			PreparedStatement stmt = getReadingStatement("INSERT INTO photos(id) VALUES(?)");
			createObject(photo, stmt, id.asInt());  // super-super method
			ServiceMain.getInstance().saveGlobals();
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		} catch (NullPointerException nex){
			SysLog.logThrowable(nex);
		}
	}
	
    /**
	 * @methodtype command
	 */
	public void loadDogPhotos(Collection<DogPhoto> result) {
		if (result == null) {
			throw new NullPointerException("Can not load dog photos as Collection is null.");
		}
		try {
			PreparedStatement stmt = getReadingStatement("SELECT * FROM photos");
			readObjects(result, stmt);
			for (Iterator<DogPhoto> i = result.iterator(); i.hasNext(); ) {
				DogPhoto photo = i.next();
				if (!doHasPhoto(photo.getId())) {
					doAddPhoto(photo);
				} else {
					SysLog.logSysInfo("photo", photo.getId().asString(), "dog photo had already been loaded");
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
		if (photo == null) {
			throw new NullPointerException("Can not save dog photo as it is null.");
		}
		try {
			PreparedStatement stmt = getUpdatingStatement("SELECT * FROM photos WHERE id = ?");
			updateObject(photo, stmt);
			super.savePhoto((Photo)photo);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	
    /**
	 *
	 */
	public void savePhotos() {
		try {
			PreparedStatement stmt = getUpdatingStatement("SELECT * FROM photos WHERE id = ?");
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
		if (ownerName == null) {
			throw new NullPointerException("Can not look for photos as ownerName is null.");
		}
		Set<DogPhoto> result = new HashSet<DogPhoto>();
		try {
			PreparedStatement stmt = getReadingStatement("SELECT * FROM photos WHERE owner_name = ? AND photo_id IS NOT NULL");
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
		if (filter == null) {
			throw new NullPointerException("Can not look for photos as filter is null.");
		}
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
		if (filter == null) {
			throw new NullPointerException("Can not look for photos filter is null.");
		}
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
	public DogPhoto createPhoto(File file) throws Exception {  // ok, throw them ¯\_(ツ)_/¯
		PhotoId id = PhotoId.getNextId();
		DogPhoto result = (DogPhoto) PhotoUtil.createPhoto(file, id);
		addPhoto(result);
		return result;
	}

}
