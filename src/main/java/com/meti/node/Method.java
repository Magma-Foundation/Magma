package com.meti.node;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Method extends Definition {
    public Method(String name, List<Flag> flags) {
        super(name, flags);
    }

    public Method(String name) {
        super(name, Collections.emptyList());
    }

    @Override
    public String render() {
        var flagString = flags.isEmpty() ? "" : concatFlags() + " ";
        return flagString + "void " + name + "(){}";
    }

    private String concatFlags() {
        return flags.stream().map(Flag::name)
                .map(String::toLowerCase)
                .collect(Collectors.joining(" "));
    }
}
