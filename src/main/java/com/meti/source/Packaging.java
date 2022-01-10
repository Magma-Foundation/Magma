package com.meti.source;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Packaging {
    private final List<String> parent;
    private final String name;

    public Packaging(String name, String... parent) {
        this(name, List.of(parent));
    }

    public Packaging(String name, List<String> parent) {
        this.parent = parent;
        this.name = name;
    }

    public String computeName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Packaging packaging = (Packaging) o;
        return Objects.equals(parent, packaging.parent) && Objects.equals(name, packaging.name);
    }

    @Override
    public String toString() {
        return "Packaging{" +
               "parent=" + parent +
               ", name='" + name + '\'' +
               '}';
    }

    public Packaging relativize(Packaging that) {
        if (parent.isEmpty()) return that;
        if (that.parent.isEmpty()) {
            var relativeParent = new ArrayList<String>();
            for (int i = 0; i < parent.size(); i++) {
                relativeParent.add("..");
            }
            return new Packaging(that.name, relativeParent);
        }

        var offset = 0;
        for (int i = 0; i < parent.size() && i < that.parent.size(); i++) {
            if (!parent.get(i).equals(that.parent.get(i))) {
                offset = i;
                break;
            }
        }

        var offsetThis = parent.subList(offset, parent.size());
        var offsetThat = that.parent.subList(offset, that.parent.size());

        var newParent = new ArrayList<String>();
        for (int i = 0; i < offsetThis.size(); i++) {
            newParent.add("..");
        }

        newParent.addAll(offsetThat);
        return new Packaging(that.name, newParent);
    }

    public Stream<String> streamParent() {
        return parent.stream();
    }
}
