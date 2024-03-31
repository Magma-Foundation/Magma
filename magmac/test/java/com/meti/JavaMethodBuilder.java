package com.meti;

import com.meti.java.RenderableBuilder;

public class JavaMethodBuilder implements RenderableBuilder {
    private final String annotations;
    private final String flagsString;
    private final String name;
    private final String throwsString;
    private final String content;

    public JavaMethodBuilder() {
        this("", "", "", "", "");
    }

    public JavaMethodBuilder(String annotations, String flagsString, String name, String throwsString, String content) {
        this.annotations = annotations;
        this.flagsString = flagsString;
        this.name = name;
        this.throwsString = throwsString;
        this.content = content;
    }

    public JavaMethodBuilder withAnnotations(String annotations) {
        return new JavaMethodBuilder(annotations, flagsString, name, throwsString, content);
    }

    public JavaMethodBuilder withFlagsString(String flagsString) {
        return new JavaMethodBuilder(annotations, flagsString, name, throwsString, content);
    }

    public JavaMethodBuilder withName(String name) {
        return new JavaMethodBuilder(annotations, flagsString, name, throwsString, content);
    }

    public JavaMethodBuilder withThrows(String throwsString) {
        return new JavaMethodBuilder(annotations, flagsString, name, throwsString, content);
    }

    public JavaMethodBuilder withContent(String content) {
        return new JavaMethodBuilder(annotations, flagsString, name, throwsString, content);
    }

    public JavaMethod build() {
        return new JavaMethod(annotations, flagsString, name, throwsString, content);
    }
}