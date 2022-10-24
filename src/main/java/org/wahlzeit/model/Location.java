/*
* Location
*/

package org.wahlzeit.model;
import org.wahlzeit.model.Coordinate;
import org.wahlzeit.model.Photo;

/* The Location class provides
*  information about a photo's location.
*/
public class Location {
	/**
	 * 
	 */
    public Coordinate coordinate;
    public Photo photo;

	/**
	 * 
	 */
	public Location(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public Location(Coordinate coordinate, Photo photo) {
		this.coordinate = coordinate;
		this.photo = photo;
	}

}
