package com.meti;

import java.util.ArrayList;
import java.util.List;

public record JavaDefinition(List<String> flags, String name, String type, String value) {
    public static String render(String name, String type, String value, String flagString) {
        return flagString + type + " " + name + Lang.renderDefinitionSuffix(value);
    }

    public String render() {
        return render(name(), type(), value(), flags().isEmpty() ? "" : (String.join(" ", flags()) + " "));
    }

    public JavaDefinition withFlag(String newFlag) {
        var copy = new ArrayList<>(flags);
        copy.add(newFlag);
        return new JavaDefinition(copy, name, type, value);
    }
}