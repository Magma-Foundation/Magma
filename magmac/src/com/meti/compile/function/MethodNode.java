package com.meti.compile.function;

import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.*;

public record MethodNode(JavaString name, JavaString parameters, JavaString body) implements Node {
    private Node withBody(JavaString compiledBody) {
        return new MethodNode(apply(JavaString.apply("name"))
                .flatMap(Attribute::asString).unwrapOrElse(JavaString.Empty), parameters, compiledBody);
    }

    public Option<JavaString> getName() {
        return Some.apply(name);
    }

    public Option<JavaString> getParameters() {
        return Some.apply(parameters);
    }

    public Option<JavaString> getBody() {
        return Some.apply(body);
    }

    @Override
    public boolean is(String name) {
        return name.equals("method");
    }

    public Option<JavaString> getChild() {
        return None.apply();
    }

    public Option<JavaString> getParent() {
        return None.apply();
    }

    public Option<List<JavaString>> getLines() {
        return None.apply();
    }

    @Override
    public Option<Attribute> apply(JavaString name) {
        return None.apply();
        if (name.equalsToSlice("child")) {
            return getChild()
                    .map(Content::from)
                    .map(NodeAttribute::new);
        } else if (name.equalsToSlice("parent")) {
            return getParent()
                    .map(Content::from)
                    .map(NodeAttribute::new);
        } else if (name.equalsToSlice("lines")) {
            return getLines().map(lines -> lines.iter()
                            .map(Content::from)
                            .collect(ImmutableLists.into()))
                    .map(NodeListAttribute::from);
        } else if (name.equalsToSlice("name")) {
            return getName().map(StringAttribute::new);
        } else if (name.equalsToSlice("parameters")) {
            return getLines().map(lines -> lines.iter()
                            .map(Content::from)
                            .collect(ImmutableLists.into()))
                    .map(NodeListAttribute::from);
        } else if (name.equalsToSlice("body")) {
            return getBody()
                    .map(Content::from)
                    .map(NodeAttribute::new);
        } else {
            return None.apply();
        }
    }

    @Override
    public Option<Node> with(JavaString name, Attribute attribute) {
        if(name.equalsToSlice("body")) {
            return attribute.asNode()
                    .flatMap(node -> node.apply(JavaString.apply("value")))
                    .flatMap(Attribute::asString)
                    .map(this::withBody);
        } else {
            return None.apply();
        }
    }
}