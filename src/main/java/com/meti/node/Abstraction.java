package com.meti.node;

import java.util.List;

public class Abstraction extends Function {

    public Abstraction(String name, Flag... flags) {
        this(name, List.of(flags));
    }

    public Abstraction(String name, List<Flag> flags) {
        super(name, flags);
    }

}