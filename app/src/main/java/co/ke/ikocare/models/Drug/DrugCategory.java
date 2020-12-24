package co.ke.ikocare.models.Drug;

import java.util.List;

public class DrugCategory {

    private String name;
    private List<Drug> drugs;

    public DrugCategory(String name, List<Drug> drugs) {
        this.name = name;
        this.drugs = drugs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }
}
