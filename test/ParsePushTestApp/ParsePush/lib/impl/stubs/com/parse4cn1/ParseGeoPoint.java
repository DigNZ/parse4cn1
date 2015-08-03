package com.parse4cn1;


/**
 *  ParseGeoPoint represents a latitude / longitude point that may be associated 
 *  with a key in a ParseObject or used as a reference point for geo queries. 
 *  This allows proximity based queries on the key.
 */
public class ParseGeoPoint {

	/**
	 *  Creates a new point with the specified latitude and longitude.
	 *  
	 *  @param latitude The point's latitude.
	 *  @param longitude The point's longitude.
	 */
	public ParseGeoPoint(double latitude, double longitude) {
	}

	/**
	 *  Set latitude. Valid range is (-90.0, 90.0). Extremes should not be used.
	 *  
	 *  @param latitude The point's latitude
	 */
	public final void setLatitude(double latitude) {
	}

	/**
	 *  Set longitude. Valid range is (-180.0, 180.0). Extremes should not be used.
	 *  
	 *  @param longitude The point's longitude.
	 */
	public final void setLongitude(double longitude) {
	}

	public double getLatitude() {
	}

	public double getLongitude() {
	}

	/**
	 *  Get distance in radians between this point and another ParseGeoPoint. 
	 *  This is the smallest angular distance between the two points.
	 *  
	 *  @param point ParseGeoPoint describing the other point being measured against.
	 *  @return The distance between this point and {@code point} in radians.
	 */
	public double distanceInRadiansTo(ParseGeoPoint point) {
	}

	/**
	 *  Get distance between this point and another ParseGeoPoint in kilometers.
	 *  
	 *  @param point ParseGeoPoint describing the other point being measured against.
	 *  @return The distance between this point and {@code point} in kilometers.
	 *  @see #distanceInRadiansTo(com.parse4cn1.ParseGeoPoint)
	 */
	public double distanceInKilometersTo(ParseGeoPoint point) {
	}

	/**
	 *  Get distance between this point and another ParseGeoPoint in miles.
	 *  
	 *  @param point ParseGeoPoint describing the other point being measured against.
	 *  @return The distance between this point and {@code point} in miles.
	 *  @see #distanceInRadiansTo(com.parse4cn1.ParseGeoPoint)
	 */
	public double distanceInMilesTo(ParseGeoPoint point) {
	}
}
