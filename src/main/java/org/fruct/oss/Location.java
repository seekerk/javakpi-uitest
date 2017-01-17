package org.fruct.oss;

import java.util.ArrayList;
import java.util.Iterator;
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
        private ArrayList<Double> _lat_new = null;

        public ArrayList<Double> lat() {
            if (_lat_new != null) {
                return _lat_new;
            }
            // search in triple store
            return getInTriples(lat_URI);
        }

        public void lat(Double value) {
            lat(value, true);
        }

        public void lat(ArrayList<Double> value) {
            lat(value, true);
        }

        public void lat(Double value, boolean removeOldValues) {
             if (_lat_new == null) {
                _lat_new = lat();
            }
            if (removeOldValues) {
                _lat_new.clear();
            }
            _lat_new.add(value);
        }

        public void lat(ArrayList<Double> value, boolean removeOldValues) {
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
        private ArrayList<Double> _lon_new = null;

        public ArrayList<Double> lon() {
            if (_lon_new != null) {
                return _lon_new;
            }
            // search in triple store
            return getInTriples(lon_URI);
        }

        public void lon(Double value) {
            lon(value, true);
        }

        public void lon(ArrayList<Double> value) {
            lon(value, true);
        }

        public void lon(Double value, boolean removeOldValues) {
             if (_lon_new == null) {
                _lon_new = lon();
            }
            if (removeOldValues) {
                _lon_new.clear();
            }
            _lon_new.add(value);
        }

        public void lon(ArrayList<Double> value, boolean removeOldValues) {
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

                if (_lat_new != null) {
                    // получаем старые значения
                    ArrayList<Double> oldVals = getInTriples(lat_URI);
                    Iterator<Double> itrNew = _lat_new.iterator();
                    while (itrNew.hasNext()) {
                        Double curNew = itrNew.next();
                        // ищем старое значение
                        Iterator<Double> itrOld = oldVals.iterator();
                        while(itrOld.hasNext()) {
                            String curOld = itrOld.next();
                            if (curNew.equals(curOld)) {
                                itrNew.remove();
                                itrOld.remove();
                                break;
                            }
                        }
                    }
                    _lat_new.stream().forEach((String val) -> {
                        newTriples.add(createTriple(getID(), lat_URI, val, "uri", "literal"));
                    });
                    oldVals.stream().forEach((val) -> {
                        removeTriples.add(createTriple(getID(), lat_URI, val, "uri", "literal"));
                    });
        	    _lat_new = null;
                }
        //-----------------------
                if (_lon_new != null) {
                    // получаем старые значения
                    ArrayList<Double> oldVals = getInTriples(lon_URI);
                    Iterator<Double> itrNew = _lon_new.iterator();
                    while (itrNew.hasNext()) {
                        Double curNew = itrNew.next();
                        // ищем старое значение
                        Iterator<Double> itrOld = oldVals.iterator();
                        while(itrOld.hasNext()) {
                            String curOld = itrOld.next();
                            if (curNew.equals(curOld)) {
                                itrNew.remove();
                                itrOld.remove();
                                break;
                            }
                        }
                    }
                    _lon_new.stream().forEach((String val) -> {
                        newTriples.add(createTriple(getID(), lon_URI, val, "uri", "literal"));
                    });
                    oldVals.stream().forEach((val) -> {
                        removeTriples.add(createTriple(getID(), lon_URI, val, "uri", "literal"));
                    });
        	    _lon_new = null;
                }
        //-----------------------


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