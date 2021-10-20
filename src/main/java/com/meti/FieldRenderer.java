package com.meti;

public class FieldRenderer {
    String render(Field field) {
        return field.apply(Attribute.Group.Type).asString() + " " + field.apply(Attribute.Group.Name).asString();
    }
}