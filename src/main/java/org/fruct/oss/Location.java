package org.fruct.oss;

import java.util.ArrayList;
import sofia_kp.KPICore;
import sofia_kp.SIBResponse;

public class Location extends BaseRDF {

    private static final String CLASS_URI = "http://oss.fruct.org/etourism#Location";

    public Location(KPICore kp, String objectID) {
        super(kp, objectID);
    }

    public Location(KPICore kp) {
        super(kp, generateID("Location"));
    }

    //------------ lat --------------
        private static final String lat_URI = "http://oss.fruct.org/etourism#lat";
        private ArrayList<String> _lat_new = null;

        public ArrayList<String> lat() {
            if (_lat_new != null) {
                return _lat_new;
            }
            // search in triple store
            return getInTriples(lat_URI);
        }

        public void lat(String value) {
            lat(value, true);
        }

        public void lat(ArrayList<String> value) {
            lat(value, true);
        }

        public void lat(String value, boolean removeOldValues) {
             if (_lat_new == null) {
                _lat_new = lat();
            }
            if (removeOldValues) {
                _lat_new.clear();
            }
            _lat_new.add(value);
        }

        public void lat(ArrayList<String> value, boolean removeOldValues) {
            if (_lat_new == null) {
                _lat_new = lat();
            }
            if (removeOldValues) {
                _lat_new.clear();
            }
            _lat_new.addAll(value);
        }
    //============== lat =============
    //------------ lon --------------
        private static final String lon_URI = "http://oss.fruct.org/etourism#lon";
        private ArrayList<String> _lon_new = null;

        public ArrayList<String> lon() {
            if (_lon_new != null) {
                return _lon_new;
            }
            // search in triple store
            return getInTriples(lon_URI);
        }

        public void lon(String value) {
            lon(value, true);
        }

        public void lon(ArrayList<String> value) {
            lon(value, true);
        }

        public void lon(String value, boolean removeOldValues) {
             if (_lon_new == null) {
                _lon_new = lon();
            }
            if (removeOldValues) {
                _lon_new.clear();
            }
            _lon_new.add(value);
        }

        public void lon(ArrayList<String> value, boolean removeOldValues) {
            if (_lon_new == null) {
                _lon_new = lon();
            }
            if (removeOldValues) {
                _lon_new.clear();
            }
            _lon_new.addAll(value);
        }
    //============== lon =============


    public void update() {
        // update triple store
        load();

        // триплеты для добавления
        ArrayList<ArrayList<String>> newTriples = new ArrayList();

        // триплеты для удаления
        ArrayList<ArrayList<String>> removeTriples = new ArrayList();

        // 1. проверяем, новый ли индивид. Если новый, то у него нет триплетов с сиба
        if (getInTriples(RDF_TYPE_URI).isEmpty()) {
            // Добавляем триплет для класса индивида
            newTriples.add(createTriple(getID(), RDF_TYPE_URI, getURI()));
        }


        SIBResponse ret;
        ret = _insert(newTriples);
        if (!ret.isConfirmed()) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        ret = _remove(removeTriples);
        if (!ret.isConfirmed()) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
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