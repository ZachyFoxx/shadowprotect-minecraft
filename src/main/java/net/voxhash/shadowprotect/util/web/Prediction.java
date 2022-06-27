package net.voxhash.shadowprotect.util.web;

public class Prediction {

    private String input;
    private Type type;
    private double probability;
    private String lang;
    private double lang_probability;
 
    public Prediction(String input, Type type, double probability, String lang, double lang_probability) {
        this.input = input;
        this.type = type;
        this.probability = probability;
        this.lang = lang;
        this.lang_probability = lang_probability;
    }

    public String getInput() {
        return input;
    }

    public Type getType() {
        return type;
    }

    public double getProbability() {
        return probability;
    }

    public String getLang() {
        return lang;
    }

    public double getLang_probability() {
        return lang_probability;
    }
}
