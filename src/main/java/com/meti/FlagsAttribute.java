package com.meti;

import java.util.Set;

public class FlagsAttribute implements Attribute {
    private final Set<?> flags;

    @Override
    public boolean contains(Object o) {
        return flags.contains(o);
    }

    public FlagsAttribute(Set<?> flags) {
        this.flags = flags;
    }
}
