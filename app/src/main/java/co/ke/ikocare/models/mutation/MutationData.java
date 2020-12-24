package co.ke.ikocare.models.mutation;

import java.util.List;

public class MutationData {

    int number;
    String initLetter;
    List<CharSequence> characterOptions;

    public MutationData(int number, String initLetter, List<CharSequence> characterOptions) {
        this.number = number;
        this.initLetter = initLetter;
        this.characterOptions = characterOptions;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getInitLetter() {
        return initLetter;
    }

    public void setInitLetter(String initLetter) {
        this.initLetter = initLetter;
    }

    public List<CharSequence> getCharacterOptions() {
        return characterOptions;
    }

    public void setCharacterOptions(List<CharSequence> characterOptions) {
        this.characterOptions = characterOptions;
    }
}
