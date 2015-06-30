package gr.unipi.ergasia.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author siggouroglou
 */
public enum StadiumIncredience {

    AGENT("AGENT.png", "AGENT"),
    SPITI_AGENT("SPITI_AGENT.jpg", "SPITI"),
    PRASINO("PRASINO.jpg", "PRASI"),
    DROMOS_EUTHEIA_1("DROMOS_EUTHEIA_1.jpg", "D_E_1"),
    DROMOS_EUTHEIA_2("DROMOS_EUTHEIA_2.jpg", "D_E_2"),
    DROMOS_GONIA_1("DROMOS_GONIA_1.jpg", "D_G_1"),
    DROMOS_GONIA_2("DROMOS_GONIA_2.jpg", "D_G_2"),
    DROMOS_GONIA_3("DROMOS_GONIA_3.jpg", "D_G_3"),
    DROMOS_GONIA_4("DROMOS_GONIA_4.jpg", "D_G_4"),
    DROMOS_TAF_1("DROMOS_TAF_1.jpg", "D_T_1"),
    DROMOS_TAF_2("DROMOS_TAF_2.jpg", "D_T_2"),
    DROMOS_TAF_3("DROMOS_TAF_3.jpg", "D_T_3"),
    DROMOS_TAF_4("DROMOS_TAF_4.jpg", "D_T_4"),
    DROMOS_DIASTAUROSI("DROMOS_DIASTAUROSI.jpg", "D_D_1"),
    ASTYNOMIA("ASTYNOMIA.jpg", "ASTYN"),
    FOURNOS("FOURNOS.jpg", "FOURN"),
    DIMARXEIO("DIMARXEIO.jpg", "DIMAR"),
    FARMAKEIO("FARMAKEIO.jpg", "FARMA"),
    FAST_FOOD("FAST_FOOD.jpg", "FAS_F"),
    GUMNASTIRIO("GUMNASTIRIO.jpg", "GUMNA"),
    KAFETERIA("KAFETERIA.jpg", "KAFET"),
    KREOPOLEIO("KREOPOLEIO.jpg", "KREOP"),
    MAGAZI_PAPOUTSIA("MAGAZI_PAPOUTSIA.jpg", "M_PAP"),
    MAGAZI_ROUXA("MAGAZI_ROUXA.jpg", "M_ROU"),
    MANABIKO("MANABIKO.jpg", "MANAB"),
    KENOS_XOROS("KENOS_XOROS.jpg", "KENOS");

    public static StadiumIncredience initFromVocabulary(String vocabulary) {
        switch (vocabulary) {
            case "AGENT":
                return AGENT;
            case "SPITI":
                return SPITI_AGENT;
            case "PRASI":
                return PRASINO;
            case "D_E_1":
                return DROMOS_EUTHEIA_1;
            case "D_E_2":
                return DROMOS_EUTHEIA_2;
            case "D_G_1":
                return DROMOS_GONIA_1;
            case "D_G_2":
                return DROMOS_GONIA_2;
            case "D_G_3":
                return DROMOS_GONIA_3;
            case "D_G_4":
                return DROMOS_GONIA_4;
            case "D_T_1":
                return DROMOS_TAF_1;
            case "D_T_2":
                return DROMOS_TAF_2;
            case "D_T_3":
                return DROMOS_TAF_3;
            case "D_T_4":
                return DROMOS_TAF_4;
            case "D_D_1":
                return DROMOS_DIASTAUROSI;
            case "ASTYN":
                return ASTYNOMIA;
            case "FOURN":
                return FOURNOS;
            case "DIMAR":
                return DIMARXEIO;
            case "FARMA":
                return FARMAKEIO;
            case "FAS_F":
                return FAST_FOOD;
            case "GUMNA":
                return GUMNASTIRIO;
            case "KAFET":
                return KAFETERIA;
            case "KREOP":
                return KREOPOLEIO;
            case "M_PAP":
                return MAGAZI_PAPOUTSIA;
            case "M_ROU":
                return MAGAZI_ROUXA;
            case "MANAB":
                return MANABIKO;
            case "KENOS":
                return KENOS_XOROS;
            default:
                return null;
        }
    }

    private String fileName;
    private String vocabulary;

    private StadiumIncredience(String fileName, String vocabulary) {
        this.fileName = fileName;
        this.vocabulary = vocabulary;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getVocabulary() {
        return this.vocabulary;
    }

    public static List<StadiumIncredience> getAllBuildings() {
        LinkedList<StadiumIncredience> linkedList = new LinkedList<>();
        linkedList.add(ASTYNOMIA);
        linkedList.add(FOURNOS);
        linkedList.add(FARMAKEIO);
        linkedList.add(FAST_FOOD);
        linkedList.add(GUMNASTIRIO);
        linkedList.add(KAFETERIA);
        linkedList.add(KREOPOLEIO);
        linkedList.add(MAGAZI_PAPOUTSIA);
        linkedList.add(MAGAZI_ROUXA);
        linkedList.add(MANABIKO);

        return linkedList;
    }

    public static List<StadiumIncredience> getAllRoads() {
        LinkedList<StadiumIncredience> linkedList = new LinkedList<>();
        linkedList.add(DROMOS_EUTHEIA_1);
        linkedList.add(DROMOS_EUTHEIA_2);
        linkedList.add(DROMOS_GONIA_1);
        linkedList.add(DROMOS_GONIA_2);
        linkedList.add(DROMOS_GONIA_3);
        linkedList.add(DROMOS_GONIA_4);
        linkedList.add(DROMOS_TAF_1);
        linkedList.add(DROMOS_TAF_2);
        linkedList.add(DROMOS_TAF_3);
        linkedList.add(DROMOS_TAF_4);
        linkedList.add(DROMOS_DIASTAUROSI);

        return linkedList;
    }

    public static List<StadiumIncredience> getAllNodes() {
        List<StadiumIncredience> allBuildings = getAllBuildings();
        List<StadiumIncredience> allRoads = getAllRoads();
        List<StadiumIncredience> buildingsWithRoadsList = Stream.concat(allBuildings.stream(), allRoads.stream()).collect(Collectors.toList());
        
        LinkedList<StadiumIncredience> linkedList = new LinkedList<>(buildingsWithRoadsList);
        linkedList.add(SPITI_AGENT);
        linkedList.add(PRASINO);
        linkedList.add(KENOS_XOROS);
        
        return linkedList;
    }

    @Override
    public String toString() {
        switch (this) {
            case AGENT:
                return "Πράκτορας";
            case SPITI_AGENT:
                return "Σπίτι Πράκτορα";
            case PRASINO:
                return "Πράσινο";
            case DROMOS_EUTHEIA_1:
                return "Δρόμος Ευθεία 1";
            case DROMOS_EUTHEIA_2:
                return "Δρόμος Ευθεία 2";
            case DROMOS_GONIA_1:
                return "Δρόμος Γωνία 1";
            case DROMOS_GONIA_2:
                return "Δρόμος Γωνία 2";
            case DROMOS_GONIA_3:
                return "Δρόμος Γωνία 3";
            case DROMOS_GONIA_4:
                return "Δρόμος Γωνία 4";
            case DROMOS_TAF_1:
                return "Δρόμος Τ 1";
            case DROMOS_TAF_2:
                return "Δρόμος Τ 2";
            case DROMOS_TAF_3:
                return "Δρόμος Τ 3";
            case DROMOS_TAF_4:
                return "Δρόμος Τ 4";
            case DROMOS_DIASTAUROSI:
                return "Δρόμος Διασταύρωση";
            case ASTYNOMIA:
                return "Αστυνομία";
            case FOURNOS:
                return "Φούρνος";
            case DIMARXEIO:
                return "Δημαρχείο";
            case FARMAKEIO:
                return "Φαρμακείο";
            case FAST_FOOD:
                return "Fast Food";
            case GUMNASTIRIO:
                return "Γυμναστήριο";
            case KAFETERIA:
                return "Καφετέρια";
            case KREOPOLEIO:
                return "Κρεοπωλείο";
            case MAGAZI_PAPOUTSIA:
                return "Παπούτσια";
            case MAGAZI_ROUXA:
                return "Ρούχα";
            case MANABIKO:
                return "Μανάβικο";
            case KENOS_XOROS:
                return "Κενός Χώρος";
            default:
                return "";
        }
    }

}
