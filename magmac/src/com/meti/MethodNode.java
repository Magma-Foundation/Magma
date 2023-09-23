package com.meti;

public record MethodNode(String name, String parameters, String body) implements Node {
    @Override
    public Node withBody(String compiledBody) {
        return new MethodNode(getName().unwrapOrElse(""), parameters, compiledBody);
    }

    @Override
    public Option<String> getName() {
        return Some.apply(name);
    }

    @Override
    public Option<String> getParameters() {
        return Some.apply(parameters);
    }

    @Override
    public Option<String> getBody() {
        return Some.apply(body);
    }
}