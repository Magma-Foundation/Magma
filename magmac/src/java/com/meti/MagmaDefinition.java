package com.meti;

import java.util.Objects;

public final class MagmaDefinition {
    private final String flagString;
    private final String mutabilityString;
    private final String name;
    private final String type;
    private final String value;

    public MagmaDefinition(String flagString, String mutabilityString, String name, String type, String value) {
        this.flagString = flagString;
        this.mutabilityString = mutabilityString;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    String render() {
        return new MagmaDefinitionHeader(flagString(), mutabilityString(), name(), type()).render() + " = " + value() + ";";
    }

    public String flagString() {
        return flagString;
    }

    public String mutabilityString() {
        return mutabilityString;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MagmaDefinition) obj;
        return Objects.equals(this.flagString, that.flagString) &&
               Objects.equals(this.mutabilityString, that.mutabilityString) &&
               Objects.equals(this.name, that.name) &&
               Objects.equals(this.type, that.type) &&
               Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flagString, mutabilityString, name, type, value);
    }

    @Override
    public String toString() {
        return "MagmaDefinition[" +
               "flagString=" + flagString + ", " +
               "mutabilityString=" + mutabilityString + ", " +
               "name=" + name + ", " +
               "type=" + type + ", " +
               "value=" + value + ']';
    }

}