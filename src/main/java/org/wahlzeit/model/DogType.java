package org.wahlzeit.model;

import org.wahlzeit.utils.PatternInstance;


@PatternInstance (
	patternName = "Type Object",
	participants = {"Dog, DogType, DogManager"}
)
public class DogType {
    /*
     * Type class providing type specific functionality.
     */

    public DogType(String typeName) {
    }

    public Dog createInstance() {
        return null;
    }

}
