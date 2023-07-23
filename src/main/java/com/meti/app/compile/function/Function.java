package com.meti.app.compile.function;

import com.meti.app.Attribute;
import com.meti.app.NodeListAttribute;
import com.meti.app.compile.*;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public abstract class Function implements Node {
    protected final Set<String_> keywords1;
    protected final String_ name1;
    protected final Set<? extends Node> parameters1;

    public Function(Set<String_> keywords1, String_ name1, Set<? extends Node> parameters1) {
        this.keywords1 = keywords1;
        this.name1 = name1;
        this.parameters1 = parameters1;
    }

    private Option<Set<String_>> keywords() {
        return Some.apply(keywords1);
    }

    private Option<String_> name() {
        return Some.apply(name1);
    }

    private Option<Set<? extends Node>> parameters() {
        return Some.apply(parameters1);
    }

    private Option<List<? extends Node>> lines() {
        return None.apply();
    }

    private Option<Node> type() {
        return None.apply();
    }

    private Option<String_> value() {
        return None.apply();
    }

    private Option<String_> parent() {
        return None.apply();
    }

    private Option<String_> child() {
        return None.apply();
    }

    @Override
    public Option<Node> returns() {
        return None.apply();
    }

    @Override
    public Option<Attribute> apply(String_ key) {
        if (key.equalsTo(fromSlice("lines"))) {
            return lines().map(NodeListAttribute::new);
        } else if (key.equalsTo(fromSlice("type"))) {
            return type().map(NodeAttribute::new);
        } else if (key.equalsTo(fromSlice("value"))) {
            return value().map(StringAttribute::new);
        } else if (key.equalsTo(fromSlice("body"))) {
            return body().map(NodeAttribute::new);
        } else if (key.equalsTo(fromSlice("name"))) {
            return name().map(StringAttribute::new);
        } else if (key.equalsTo(fromSlice("parameters"))) {
            return parameters().map(NodeSetAttribute::new);
        } else if (key.equalsTo(fromSlice("keywords"))) {
            return keywords().map(StringSetAttribute::new);
        } else if (key.equalsTo(fromSlice("parent"))) {
            return parent().map(StringAttribute::new);
        } else if (key.equalsTo(fromSlice("child"))) {
            return child().map(StringAttribute::new);
        } else if (key.equalsTo(fromSlice("returns"))) {
            return returns().map(NodeAttribute::new);
        } else {
            return None.apply();
        }
    }

    protected Option<Node> body() {
        return None.apply();
    }
}
