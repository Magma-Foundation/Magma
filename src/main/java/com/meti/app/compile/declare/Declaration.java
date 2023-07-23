package com.meti.app.compile.declare;

import com.meti.app.compile.Node;
import com.meti.java.String_;

public record Declaration(String_ name1, String_ type) implements Node {
    public String_ render() {
        return name1.append(" : ").appendOwned(type);
    }
}
