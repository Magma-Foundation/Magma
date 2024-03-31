package com.meti;

import java.util.Objects;

public final class DeclarationNode {
    private final String flagString;
    private final String name;
    private final String type;
    private final String value;

    public DeclarationNode(String flagString, String name, String type, String value) {
        this.flagString = flagString;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    String render() {
        return flagString() + type() + " " + name() + " = " + value() + ";";
    }

    public String flagString() {
        return flagString;
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
        var that = (DeclarationNode) obj;
        return Objects.equals(this.flagString, that.flagString) &&
               Objects.equals(this.name, that.name) &&
               Objects.equals(this.type, that.type) &&
               Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flagString, name, type, value);
    }

    @Override
    public String toString() {
        return "DeclarationNode[" +
               "flagString=" + flagString + ", " +
               "name=" + name + ", " +
               "type=" + type + ", " +
               "value=" + value + ']';
    }

}