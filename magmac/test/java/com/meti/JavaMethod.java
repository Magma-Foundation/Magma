package com.meti;

import com.meti.node.Renderable;

import java.util.Objects;

public final class JavaMethod implements Renderable {
    private final String annotations;
    private final String flagsString;
    private final String name;
    private final String throwsString;
    private final String content;

    public JavaMethod(String annotations, String flagsString, String name, String throwsString, String content) {
        this.annotations = annotations;
        this.flagsString = flagsString;
        this.name = name;
        this.throwsString = throwsString;
        this.content = content;
    }

    @Override
    public String render() {
        return annotations() + "\n" + flagsString() + "void " + name() + "()" + throwsString() + content();
    }

    public String annotations() {
        return annotations;
    }

    public String flagsString() {
        return flagsString;
    }

    public String name() {
        return name;
    }

    public String throwsString() {
        return throwsString;
    }

    public String content() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (JavaMethod) obj;
        return Objects.equals(this.annotations, that.annotations) &&
               Objects.equals(this.flagsString, that.flagsString) &&
               Objects.equals(this.name, that.name) &&
               Objects.equals(this.throwsString, that.throwsString) &&
               Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotations, flagsString, name, throwsString, content);
    }

    @Override
    public String toString() {
        return "JavaMethod[" +
               "annotations=" + annotations + ", " +
               "flagsString=" + flagsString + ", " +
               "name=" + name + ", " +
               "throwsString=" + throwsString + ", " +
               "content=" + content + ']';
    }

}