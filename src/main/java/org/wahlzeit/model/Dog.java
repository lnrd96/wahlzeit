package org.wahlzeit.model;

import org.wahlzeit.utils.PatternInstance;


@PatternInstance (
	patternName = "Type Object",
	participants = {"Dog, DogType, DogManager"}
)
public class Dog {
    /**
     * Domain class providing instance specific functionality.
     */
    int id;
    protected DogType type = null;
    public String getId() {
        return null;
    }

}
