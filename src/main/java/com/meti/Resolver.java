package com.meti;

import com.meti.node.PrimitiveType;

public class Resolver {
    private final String type;

    public Resolver(String type) {
        this.type = type;
    }

    PrimitiveType resolve() throws ApplicationException {
        return switch (type) {
            case "I16" -> PrimitiveType.I16;
            case "U16" -> PrimitiveType.U16;
            default -> throw new ApplicationException("Invalid type: " + type);
        };
    }
}
