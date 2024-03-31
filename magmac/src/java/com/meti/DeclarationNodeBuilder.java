package com.meti;

public record DeclarationNodeBuilder(String flagString, String name, String type, String value) {
    public DeclarationNodeBuilder() {
        this("", "", "", "");
    }

    public DeclarationNodeBuilder(String flagString, String name, String type, String value) {
        this.flagString = flagString;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public DeclarationNodeBuilder withFlagString(String flagString) {
        return new DeclarationNodeBuilder(flagString, name, type, value);
    }

    public DeclarationNodeBuilder setName(String name) {
        return new DeclarationNodeBuilder(flagString, name, type, value);
    }

    public DeclarationNodeBuilder setType(String type) {
        return new DeclarationNodeBuilder(flagString, name, type, value);
    }

    public DeclarationNodeBuilder withValue(String value) {
        return new DeclarationNodeBuilder(flagString, name, type, value);
    }

    public DeclarationNode build() {
        return new DeclarationNode(flagString, name, type, value);
    }
}