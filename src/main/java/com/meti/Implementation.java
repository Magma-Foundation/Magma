package com.meti;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Implementation extends Function {
    private final Node body;

    public Implementation(String name, Node body, Flag... flags) {
        this(name, body, List.of(flags));
    }

    public Implementation(String name, Node body, List<Flag> flags) {
        super(name, flags);
        this.body = body;
    }

    @Override
    public String render() {
        return super.render() + " => " + body.render();
    }
}
