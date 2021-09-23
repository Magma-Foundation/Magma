package com.meti;

public class Resolver {
    private final String typeString;

    public Resolver(String typeString) {
        this.typeString = typeString;
    }

    PrimitiveType resolve() throws ApplicationException {
        return switch (getTypeString()) {
            case "I16" -> PrimitiveType.I16;
            case "U16" -> PrimitiveType.U16;
            default -> throw new ApplicationException("Invalid type: " + getTypeString());
        };
    }

    public String getTypeString() {
        return typeString;
    }
}
