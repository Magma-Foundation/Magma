package com.meti;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Abstraction extends Function {

    public Abstraction(String name, Flag... flags) {
        this(name, List.of(flags));
    }

    public Abstraction(String name, List<Flag> flags) {
        super(name, flags);
    }

}