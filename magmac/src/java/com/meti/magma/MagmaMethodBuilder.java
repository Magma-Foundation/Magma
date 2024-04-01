package com.meti.magma;

import com.meti.java.RenderableBuilder;

import java.util.Optional;

public class MagmaMethodBuilder implements RenderableBuilder {
    private String prefix;
    private String name;
    private Optional<String> type;
    private String content;
    private String exceptionString;
    private String parametersString;

    public MagmaMethodBuilder() {
        this("", "", Optional.empty(), "", "", "");
    }

    public MagmaMethodBuilder(String prefix, String name, Optional<String> type, String content, String exceptionString, String parametersString) {
        this.prefix = prefix;
        this.name = name;
        this.type = type;
        this.content = content;
        this.exceptionString = exceptionString;
        this.parametersString = parametersString;
    }

    public MagmaMethodBuilder withPrefix(String prefix) {
        return new MagmaMethodBuilder(prefix, name, type, content, exceptionString, parametersString);
    }

    public MagmaMethodBuilder withName(String name) {
        return new MagmaMethodBuilder(prefix, name, type, content, exceptionString, parametersString);
    }

    public MagmaMethodBuilder withType(String type) {
        return new MagmaMethodBuilder(prefix, name, Optional.of(type), content, exceptionString, parametersString);
    }

    public MagmaMethodBuilder withContent(String content) {
        return new MagmaMethodBuilder(prefix, name, type, content, exceptionString, parametersString);
    }

    public MagmaMethodBuilder withExceptionString(String exceptionString) {
        return new MagmaMethodBuilder(prefix, name, type, content, exceptionString, parametersString);
    }

    public MagmaMethodNode build() {
        return new MagmaMethodNode(prefix, name, parametersString, type, content, exceptionString);
    }

    public MagmaMethodBuilder withParameters(String parametersString) {
        return new MagmaMethodBuilder(prefix, name, type, content, exceptionString, parametersString);
    }
}