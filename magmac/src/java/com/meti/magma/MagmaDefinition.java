package com.meti.magma;

import com.meti.node.Renderable;

public record MagmaDefinition(String flagString, String mutabilityString, String name, String type,
                              String value) implements Renderable {
    @Override
    public String render() {
        return new MagmaDefinitionHeader(flagString(), mutabilityString(), name(), type()).render() + " = " + value() + ";";
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