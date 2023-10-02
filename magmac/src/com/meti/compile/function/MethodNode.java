package com.meti.compile.function;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Node;

public record MethodNode(JavaString name, JavaString parameters, JavaString body) implements Node {
    @Override
    public Node withBody(JavaString compiledBody) {
        return new MethodNode(getName().unwrapOrElse(JavaString.Empty), parameters, compiledBody);
    }

    @Override
    public Option<JavaString> getName() {
        return Some.apply(name);
    }

    @Override
    public Option<JavaString> getParameters() {
        return Some.apply(parameters);
    }

    @Override
    public Option<JavaString> getBody() {
        return Some.apply(body);
    }

    @Override
    public boolean is(String name) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}