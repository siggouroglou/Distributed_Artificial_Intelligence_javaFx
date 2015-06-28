package gr.unipi.ergasia.model;

/**
 *
 * @author siggouroglou
 */
public enum StadiumIncredience {

    AGENT("AGENT.png"),
    SPITI_AGENT("SPITI_AGENT.png"),
    PRASINO("PRASINO.png"),
    DROMOS_EUTHEIA_1("DROMOS_EUTHEIA_1.png"),
    DROMOS_EUTHEIA_2("DROMOS_EUTHEIA_2.png"),
    DROMOS_GONIA_1("DROMOS_GONIA_1.png"),
    DROMOS_GONIA_2("DROMOS_GONIA_2.png"),
    DROMOS_GONIA_3("DROMOS_GONIA_3.png"),
    DROMOS_GONIA_4("DROMOS_GONIA_4.png"),
    DROMOS_TAF_1("DROMOS_TAF_1.png"),
    DROMOS_TAF_2("DROMOS_TAF_2.png"),
    DROMOS_TAF_3("DROMOS_TAF_3.png"),
    DROMOS_TAF_4("DROMOS_TAF_4.png"),
    DROMOS_DIASTAUROSI("DROMOS_DIASTAUROSI.png"),
    ASTYNOMIA("ASTYNOMIA.png"),
    FOURNOS("FOURNOS.png"),
    DIMARXEIO("DIMARXEIO.png"),
    FARMAKEIO("FARMAKEIO.png"),
    FAST_FOOD("FAST_FOOD.png"),
    GUMNASTIRIO("GUMNASTIRIO.png"),
    KAFETERIA("KAFETERIA.png"),
    KREOPOLEIO("KREOPOLEIO.png"),
    MAGAZI_PAPOUTSIA("MAGAZI_PAPOUTSIA.png"),
    MAGAZI_ROUXA("MAGAZI_ROUXA.png"),
    MANABIKO("MANABIKO.png"),
    KENOS_XOROS("KENOS_XOROS.png");
    private String fileName;

    private StadiumIncredience(String value) {
        this.fileName = value;
    }

    public String getFileName() {
        return this.fileName;
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
