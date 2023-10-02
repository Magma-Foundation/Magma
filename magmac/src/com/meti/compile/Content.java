package com.meti.compile;

import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;

public class Content implements Node {
    public Content(JavaString value) {

    }

    public static Node from(JavaString value) {
        return new Content(value);
    }

    @Override
    public boolean is(String name) {
        return name.equals("content");
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

    public Option<JavaString> getName() {
        return None.apply();
    }

    public Option<JavaString> getParameters() {
        return None.apply();
    }

    public Option<JavaString> getBody() {
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

    private Node withBody(JavaString compiledBody) {
        return this;
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
