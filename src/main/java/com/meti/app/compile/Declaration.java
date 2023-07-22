package com.meti.app.compile;

import com.meti.java.String_;

public record Declaration(String_ name1, String_ type) implements Node {
    @Override
    public String_ render() {
        return name1.append(" : ").appendOwned(type);
    }
}
