package com.meti.node;

import java.util.List;
import java.util.stream.Collectors;

public class Function extends Definition {

    public Function(String name, List<Flag> flags) {
        super(name, flags);
    }

    @Override
    public String render() {
        var collect = (flags.isEmpty()) ? "" : (joinFlags() + " ");
        return collect + "def " + name + "()";
    }

    private String joinFlags() {
        return flags.stream()
                .map(Flag::name)
                .map(String::toLowerCase)
                .collect(Collectors.joining(" "));
    }
}
