package com.meti;

import java.util.Set;

public abstract class Definition implements Node {
    protected final String name;
    protected final Set<Flag> flags;

    public Definition(String name, Set<Flag> flags) {
        this.name = name;
        this.flags = flags;
    }

    public boolean isPublic() {
        return flags.contains(Flag.Public);
    }

    public enum Flag {
        Public,
        Static
    }
}
