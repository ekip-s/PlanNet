package ru.darkt.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Model {
    GPT_40_MINI("gpt-4o-mini"),
    GPT_5_mini("gpt-5-mini"),
    GPT_5("gpt-5");

    private final String value;

    Model(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Model fromValue(String input) {
        if (input == null) {
            return null;
        }
        for (Model m : Model.values()) {
            if (m.value.equalsIgnoreCase(input)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Unknown model value: " + input);
    }

    @Override
    public String toString() {
        return value;
    }
}