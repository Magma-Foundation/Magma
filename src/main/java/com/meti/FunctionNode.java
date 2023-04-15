package com.meti;

import java.util.Optional;
import java.util.Set;

public class FunctionNode implements Node {
    enum Flag {
        Class
    }

    private final Set<Flag> flags;
    private final String name;

    public FunctionNode(String name, Set<Flag> flags) {
        this.name = name;
        this.flags = flags;
    }

    @Override
    public boolean is(Object key) {
        return Key.Id == key;
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        if(Key.Name == key) return Optional.of(new TextAttribute(name));
        if(Key.Flags == key) return Optional.of(new FlagsAttribute(flags));
        return Optional.empty();
    }

    public enum Key {
        Id,
        Name,
        Flags
    }
}
