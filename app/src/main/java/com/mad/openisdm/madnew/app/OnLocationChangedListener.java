package com.mad.openisdm.madnew.app;

import org.osmdroid.util.GeoPoint;


/**
 * Activity that host MapFragment must implement this interface
 */

public interface OnLocationChangedListener {
    /**Callback method when user location is changed in MapFragment*/
    public void onLocationChanged(GeoPoint userLocation);
}
