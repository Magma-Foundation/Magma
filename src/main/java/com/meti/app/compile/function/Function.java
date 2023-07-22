package com.meti.app.compile.function;

import com.meti.app.compile.Node;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

public record Function(Set<String_> keywords, String_ name,
                       List<Node> parameters, Node body) implements Node {
    @Override
    public String_ render() {
        return new FunctionRenderer(this).render().unwrap();
    }
}