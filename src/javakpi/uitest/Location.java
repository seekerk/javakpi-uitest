/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javakpi.uitest;

import java.util.ArrayList;
import sofia_kp.KPICore;

/**
 *
 * @author kulakov
 */
class Location extends SSBase {
    private static final String CLASS_URI = "http://oss.fruct.org/etourism#User";
    
    public static final String CLASS_NAME = "User";

    public Location(KPICore kp, String objectID) {
        super(kp, objectID);
    }

    Location(KPICore kp) {
        super(kp, generateID(CLASS_NAME));
    }
    
    // latitude /////////////////////////////////

    private static final String LATITUDE_URI = "http://oss.fruct.org/etourism#latitude";
    private ArrayList<String> _latitude_new = null;
    
    ArrayList<String> latitude() {
        if (_latitude_new != null) {
            return _latitude_new;
        }
        // search in triples
        _latitude_new = getInTriples(LATITUDE_URI);
        return _latitude_new;
    }

    void latitude(String value) {
        latitude(value, true);
    }
    
    private void latitude(String value, boolean removeOldValues) {
        if (_latitude_new == null) {
            latitude();
        }
        if (removeOldValues) {
            _latitude_new.clear();
        }
        _latitude_new.add(value);
    }
    
    // longitude ////////////////////////////////

    private static final String LONGITUDE_URI = "http://oss.fruct.org/etourism#longitude";
    private ArrayList<String> _longitude_new = null;
    
    ArrayList<String> longitude() {
        if (_longitude_new != null) {
            return _longitude_new;
        }
        // search in triples
        _longitude_new = getInTriples(LONGITUDE_URI);
        return _longitude_new;
    }

    void longitude(String value) {
        longitude(value, true);
    }
    
    private void longitude(String value, boolean removeOldValues) {
        if (_longitude_new == null) {
            longitude();
        }
        if (removeOldValues) {
            _longitude_new.clear();
        }
        _longitude_new.add(value);
    }

    void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURI() {
        return CLASS_URI;
    }

    @Override
    public void removeProperty(String URI) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
