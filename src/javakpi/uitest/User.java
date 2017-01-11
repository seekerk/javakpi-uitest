/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javakpi.uitest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.function.Consumer;
import sofia_kp.KPICore;
import sofia_kp.SIBResponse;

/**
 *
 * @author kulakov
 */
class User extends SSBase {
    
    private static final String CLASS_URI = "http://oss.fruct.org/etourism#User";

    public User(KPICore kp, String objectID) {
        super(kp, objectID);
    }
    
    // name ////////////////////////////////////////
    
    private static final String NAME_URI = "http://oss.fruct.org/etourism#name";
    private ArrayList<String> _name_new = null;

    ArrayList<String> name() {
        if (_name_new != null)
            return _name_new;
        
        return getInTriples(NAME_URI);
    }
    
    void name(String text) {
        name(text, true);
    }

    void name(String text, boolean removeOldValues) {
        if (_name_new == null) {
            _name_new = name();
        }
        if (removeOldValues) {
            _name_new.clear();
        }
        _name_new.add(text);
    }

    // surname ////////////////////////////////////
    
    private static final String SURNAME_URI = "http://oss.fruct.org/etourism#surname";
    private ArrayList<String> _surname_new = null;
    
    ArrayList<String> surname() {
        if (_surname_new != null)
            return _surname_new;
        
        return getInTriples(SURNAME_URI);
    }

    void surname(String text) {
        surname(text, true);
    }
    
    private void surname(String text, boolean removeOldValues) {
        if (_surname_new == null) {
            _surname_new = surname();
        }
        if (removeOldValues) {
            _surname_new.clear();
        }
        _surname_new.add(text);
    }
    
    // hasLocation (Location) /////////////////////////////////

    private static final String HASLOCATION_URI = "http://oss.fruct.org/etourism#hasLocation";
    private ArrayList<Location> _location_new = null;
    
    
    public ArrayList<Location> hasLocation() {
        if (_location_new != null)
            return _location_new;
        
        ArrayList<Location> ret = new ArrayList();
        //search IDs in triples
        ArrayList<String> locationIDs = getInTriples(HASLOCATION_URI);
        for (String locID: locationIDs) {
            Location loc = new Location(_kp, locID);
            ret.add(loc);
        }
        
        return ret;
    }
    
    void hasLocation(Location loc) {
        hasLocation(loc, true);
    }
    
    private void hasLocation(Location loc, boolean removeOldValues) {
        if (_location_new == null) {
            _location_new = hasLocation();
        }
        if (removeOldValues) {
            _location_new.clear();
        }
        _location_new.add(loc);
    }

    // preferences //////////////////////////////////////

    private static final String PREFERENCES_URI = "http://oss.fruct.org/etourism#preferences";
    private ArrayList<String> _preferences_new = null;
    

    ArrayList<String> preferences() {
        if (_preferences_new != null) {
            return _preferences_new;
        }
        // search in triples
        return getInTriples(PREFERENCES_URI);
    }

    void preferences(String text) {
        preferences(text, true);
    }
    
    //TODO: вставить обработку массивов для других полей
    void preferences(ArrayList<String> value) {
        preferences(value, true);
    }

    private void preferences(String text, boolean removeOldValues) {
         if (_preferences_new == null) {
            _preferences_new = preferences();
        }
        if (removeOldValues) {
            _preferences_new.clear();
        }
        _preferences_new.add(text);
    }

    private void preferences(ArrayList<String> value, boolean removeOldValues) {
        if (_preferences_new == null) {
            _preferences_new = preferences();
        }
        if (removeOldValues) {
            _preferences_new.clear();
        }
        _preferences_new.addAll(value);
}

    /**
     * Внесение изменений в СИБ
     */
    void update() {
        // загрузка триплетов из СИБа
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
        // 2. name
        if (_name_new != null) {
            // получаем старые значения
            ArrayList<String> oldVals = getInTriples(NAME_URI);
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
                newTriples.add(createTriple(getID(), NAME_URI, val, "uri", "literal"));
            });
            oldVals.stream().forEach((val) -> {
                removeTriples.add(createTriple(getID(), NAME_URI, val, "uri", "literal"));
            });
        }
        
        // 3. surname
        if (_surname_new != null) {
            // получаем старые значения
            ArrayList<String> oldVals = getInTriples(SURNAME_URI);
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
                //!!! literal потому что текстовое свойство
                newTriples.add(createTriple(getID(), SURNAME_URI, val, "uri", "literal"));
            });
            oldVals.stream().forEach((val) -> {
                removeTriples.add(createTriple(getID(), SURNAME_URI, val, "uri", "literal"));
            });
        }
        
        // 4. preferences
        if (_preferences_new != null) {
            // получаем старые значения
            ArrayList<String> oldVals = getInTriples(PREFERENCES_URI);
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
                //!!! literal потому что текстовое свойство
                newTriples.add(createTriple(getID(), PREFERENCES_URI, val, "uri", "literal"));
            });
            oldVals.stream().forEach((val) -> {
                removeTriples.add(createTriple(getID(), PREFERENCES_URI, val, "uri", "literal"));
            });
        }
        
        //TODO: location
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
