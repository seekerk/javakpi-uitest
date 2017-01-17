package org.fruct.oss;

import java.util.ArrayList;
import java.util.Iterator;
import sofia_kp.KPICore;
import sofia_kp.SIBResponse;

public class User extends BaseRDF {

    private static final String CLASS_URI = "http://oss.fruct.org/etourism#User";

    public User(KPICore kp, String objectID) {
        super(kp, objectID);
    }

    public User(KPICore kp) {
        super(kp, generateID("User"));
    }

    //------------ name --------------
        private static final String name_URI = "http://oss.fruct.org/etourism#name";
        private ArrayList<String> _name_new = null;

        public ArrayList<String> name() {
            if (_name_new != null) {
                return _name_new;
            }
            // search in triple store
            return getInTriples(name_URI);
        }

        public void name(String value) {
            name(value, true);
        }

        public void name(ArrayList<String> value) {
            name(value, true);
        }

        public void name(String value, boolean removeOldValues) {
             if (_name_new == null) {
                _name_new = name();
            }
            if (removeOldValues) {
                _name_new.clear();
            }
            _name_new.add(value);
        }

        public void name(ArrayList<String> value, boolean removeOldValues) {
            if (_name_new == null) {
                _name_new = name();
            }
            if (removeOldValues) {
                _name_new.clear();
            }
            _name_new.addAll(value);
        }
    //============== name =============
    //------------ hasLocation --------------
        private static final String hasLocation_URI = "http://oss.fruct.org/etourism#hasLocation";
        private ArrayList<Location> _hasLocation_new = null;


        public ArrayList<Location> hasLocation() {
            if (_hasLocation_new != null)
                return _hasLocation_new;

            ArrayList<Location> ret = new ArrayList();
            //search IDs in triples
            ArrayList<String> hasLocationIDs = getInTriples(hasLocation_URI);
            for (String locID: hasLocationIDs) {
                Location value = new Location(_kp, locID);
                ret.add(value);
            }

            return ret;
        }

        public void hasLocation(Location loc) {
            hasLocation(loc, true);
        }

        public void hasLocation(Location value, boolean removeOldValues) {
            if (_hasLocation_new == null) {
                _hasLocation_new = hasLocation();
            }
            if (removeOldValues) {
                _hasLocation_new.clear();
            }
            _hasLocation_new.add(value);
        }
    //============== hasLocation =============
    //------------ surname --------------
        private static final String surname_URI = "http://oss.fruct.org/etourism#surname";
        private ArrayList<String> _surname_new = null;

        public ArrayList<String> surname() {
            if (_surname_new != null) {
                return _surname_new;
            }
            // search in triple store
            return getInTriples(surname_URI);
        }

        public void surname(String value) {
            surname(value, true);
        }

        public void surname(ArrayList<String> value) {
            surname(value, true);
        }

        public void surname(String value, boolean removeOldValues) {
             if (_surname_new == null) {
                _surname_new = surname();
            }
            if (removeOldValues) {
                _surname_new.clear();
            }
            _surname_new.add(value);
        }

        public void surname(ArrayList<String> value, boolean removeOldValues) {
            if (_surname_new == null) {
                _surname_new = surname();
            }
            if (removeOldValues) {
                _surname_new.clear();
            }
            _surname_new.addAll(value);
        }
    //============== surname =============
    //------------ preferences --------------
        private static final String preferences_URI = "http://oss.fruct.org/etourism#preferences";
        private ArrayList<String> _preferences_new = null;

        public ArrayList<String> preferences() {
            if (_preferences_new != null) {
                return _preferences_new;
            }
            // search in triple store
            return getInTriples(preferences_URI);
        }

        public void preferences(String value) {
            preferences(value, true);
        }

        public void preferences(ArrayList<String> value) {
            preferences(value, true);
        }

        public void preferences(String value, boolean removeOldValues) {
             if (_preferences_new == null) {
                _preferences_new = preferences();
            }
            if (removeOldValues) {
                _preferences_new.clear();
            }
            _preferences_new.add(value);
        }

        public void preferences(ArrayList<String> value, boolean removeOldValues) {
            if (_preferences_new == null) {
                _preferences_new = preferences();
            }
            if (removeOldValues) {
                _preferences_new.clear();
            }
            _preferences_new.addAll(value);
        }
    //============== preferences =============


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

                if (_name_new != null) {
                    // получаем старые значения
                    ArrayList<String> oldVals = getInTriples(name_URI);
                    Iterator<String> itrNew = _name_new.iterator();
                    while (itrNew.hasNext()) {
                        String curNew = itrNew.next();
                        // ищем старое значение
                        Iterator<String> itrOld = oldVals.iterator();
                        while(itrOld.hasNext()) {
                            String curOld = itrOld.next();
                            if (curNew.equals(curOld)) {
                                itrNew.remove();
                                itrOld.remove();
                                break;
                            }
                        }
                    }
                    _name_new.stream().forEach((String val) -> {
                        newTriples.add(createTriple(getID(), name_URI, val, "uri", "literal"));
                    });
                    oldVals.stream().forEach((val) -> {
                        removeTriples.add(createTriple(getID(), name_URI, val, "uri", "literal"));
                    });
        	    _name_new = null;
                }
        //-----------------------
                if (_hasLocation_new != null) {
                    // получаем старые значения
                    ArrayList<String> oldValsIDs = getInTriples(hasLocation_URI);
                    Iterator<Location> itrNew = _hasLocation_new.iterator();
                    while (itrNew.hasNext()) {
                        Location curNew = itrNew.next();
                        // ищем старое значение
                        Iterator<String> itrOldID = oldValsIDs.iterator();
                        while(itrOldID.hasNext()) {
                            String curOldID = itrOldID.next();
                            if (curNew.getID().equals(curOldID)) {
                                itrNew.remove();
                                itrOldID.remove();
                                break;
                            }
                        }
                    }
                    _hasLocation_new.stream().forEach((Location val) -> {
                        newTriples.add(createTriple(getID(), hasLocation_URI, val.getID(), "uri", "literal"));
                    });
                    oldValsIDs.stream().forEach((val) -> {
                        removeTriples.add(createTriple(getID(), hasLocation_URI, val, "uri", "literal"));
                    });
        	    _hasLocation_new = null;
                }
        //--------------------
                if (_surname_new != null) {
                    // получаем старые значения
                    ArrayList<String> oldVals = getInTriples(surname_URI);
                    Iterator<String> itrNew = _surname_new.iterator();
                    while (itrNew.hasNext()) {
                        String curNew = itrNew.next();
                        // ищем старое значение
                        Iterator<String> itrOld = oldVals.iterator();
                        while(itrOld.hasNext()) {
                            String curOld = itrOld.next();
                            if (curNew.equals(curOld)) {
                                itrNew.remove();
                                itrOld.remove();
                                break;
                            }
                        }
                    }
                    _surname_new.stream().forEach((String val) -> {
                        newTriples.add(createTriple(getID(), surname_URI, val, "uri", "literal"));
                    });
                    oldVals.stream().forEach((val) -> {
                        removeTriples.add(createTriple(getID(), surname_URI, val, "uri", "literal"));
                    });
        	    _surname_new = null;
                }
        //-----------------------
                if (_preferences_new != null) {
                    // получаем старые значения
                    ArrayList<String> oldVals = getInTriples(preferences_URI);
                    Iterator<String> itrNew = _preferences_new.iterator();
                    while (itrNew.hasNext()) {
                        String curNew = itrNew.next();
                        // ищем старое значение
                        Iterator<String> itrOld = oldVals.iterator();
                        while(itrOld.hasNext()) {
                            String curOld = itrOld.next();
                            if (curNew.equals(curOld)) {
                                itrNew.remove();
                                itrOld.remove();
                                break;
                            }
                        }
                    }
                    _preferences_new.stream().forEach((String val) -> {
                        newTriples.add(createTriple(getID(), preferences_URI, val, "uri", "literal"));
                    });
                    oldVals.stream().forEach((val) -> {
                        removeTriples.add(createTriple(getID(), preferences_URI, val, "uri", "literal"));
                    });
        	    _preferences_new = null;
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