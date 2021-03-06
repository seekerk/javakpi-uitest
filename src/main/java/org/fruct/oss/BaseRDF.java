package org.fruct.oss;

import java.util.ArrayList;
import java.util.Random;
import sofia_kp.KPICore;
import sofia_kp.SIBResponse;

public abstract class BaseRDF {

    protected KPICore _kp;
    private final String _id;
    // датчик случайных чисел для генератора
    private static Random rand = null;

    // загруженные триплеты
    private ArrayList<ArrayList<String>> triples = null;

    // разные гадости
    //TODO: заменить на rdg4j
    public static final String RDF_TYPE_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    public static final String SIB_ANY = "http://www.nokia.com/NRC/M3/sib#any";

    public BaseRDF(KPICore kp, String objectID) {
        _kp = kp;
        _id = objectID;
    }

    public String getID() {
        return _id;
    }

    /**
     * Получение URI класса для идентификации
     * @return URI
     */
    public abstract String getURI();

    public void load() {
        SIBResponse resp;
        resp = _kp.queryRDF(_id, SIB_ANY, SIB_ANY, "uri", "uri");
        if (!resp.isConfirmed()) {
            //TODO: change to exception
            System.err.println("Failed connection");
            return;
        }
        this.triples = resp.query_results;
        //TODO: DEBUG
        for (ArrayList<String> t : this.triples) {
            System.out.println(t);
        }
    }

    public ArrayList<String> getStringInTriples(String searchURI) {
        ArrayList<String> ret = new ArrayList<>();
        if (this.triples == null) {
            load();
        }
        for (ArrayList<String> t : this.triples) {
            if (t.contains(searchURI)) {
                ret.add(t.get(2));
            }
        }
        return ret;
    }

    public ArrayList<Double> getDoubleInTriples(String searchURI) {
        ArrayList<Double> ret = new ArrayList<>();
        if (this.triples == null) {
            load();
        }
        for (ArrayList<String> t : this.triples) {
            if (t.contains(searchURI)) {
                ret.add(Double.parseDouble(t.get(2)));
            }
        }
        return ret;
    }

    /**
     *  функция для удаления свойства объекта из SIB
     * @param URI адрес свойства
     */
    public abstract void removeProperty(String URI);

    public static String generateID(String prefix) {
        if (rand == null) {
            rand = new Random();
        }
        return prefix + Long.toUnsignedString(rand.nextLong());
    }

    /**
     * Создание триплета с объектом и субъектом типа "uri"
     * @param object объект (uri)
     * @param predicate связь (uri)
     * @param subject субъект (uri)
     * @return триплет в виде массива
     */
    public static ArrayList<String> createTriple(String object, String predicate, String subject) {
        return createTriple(object, predicate, subject, "uri", "uri");
    }

    /**
     * Создание триплета (объект->предикат->субъект)
     * @param object объект (uri)
     * @param predicate связь (uri)
     * @param subject субъект (uri)
     * @param objectType тип объекта ("uri" or "literal")
     * @param subjectType тип субъекта ("uri" or "literal")
     * @return триплет в виде массива
     */
    public static ArrayList<String> createTriple(String object, String predicate, String subject, String objectType, String subjectType) {
            ArrayList<String> ret = new ArrayList(5);
            ret.add(object);
            ret.add(predicate);
            ret.add(subject);
            ret.add(objectType);
            ret.add(subjectType);
            return ret;
    }

    /**
     * преобразование ArrayList в ArrayList и добавление в СИБ
     * @param list
     * @return 
     */
    protected SIBResponse _insert(ArrayList<ArrayList<String>> list) {
        return _kp.insert(list);
    }

    protected SIBResponse _remove(ArrayList<ArrayList<String>> list) {
        return _kp.remove(list);
    }
}
