package com.meti;

import com.meti.node.Block;
import com.meti.node.Definition;
import com.meti.node.Node;

import java.util.List;
import java.util.stream.Collectors;

public final class JavaClass extends Definition {
    public static final String ClassKeyword = "class ";
    private final Node body;

    public JavaClass(String name, Block body, Flag... flags) {
        this(name, body, List.of(flags));
    }

    public JavaClass(String name, Node body, List<Flag> flags) {
        super(name, flags);
        this.body = body;
    }

    @Override
    public String render() {
        var collect = flags.isEmpty() ? "" : (renderFlags() + " ");
        return collect + ClassKeyword + name + " " + body.render();
    }

    private String renderFlags() {
        return flags.stream()
                .map(Flag::name)
                .map(String::toLowerCase)
                .collect(Collectors.joining(" "));
    }

}