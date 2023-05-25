package com.meti;

import java.util.Set;
import java.util.stream.Collectors;

public class Method extends Definition {
    public Method(String name, Set<Flag> flags) {
        super(name, flags);
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
