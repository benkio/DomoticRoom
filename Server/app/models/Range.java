package models;

import models.enums.*;

public class Range {
    public double minBound;
    public double maxBound;
    public RangeType type;

    public Range(){}

    public Range(RangeType rangeType, double minBound, double maxBound){
	this.minBound = minBound;
	this.maxBound = maxBound;
	type = rangeType;
    }
    public String validate() {
        if(minBound > maxBound) {
            return "Invalid Boundaries: check the max and min";
        }
        return null;
    }
}
