package com.meti;

import java.util.List;

public record NodeListAttribute(List<? extends Node> value) implements Attribute {
    @Override
    public List<? extends Node> asNodeList() throws AttributeException {
        return value;
    }
}
