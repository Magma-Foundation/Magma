package com.meti;

public class FieldRenderer {
    String render(Field field) {
        return field.type() + " " + field.name();
    }
}