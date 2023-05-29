package com.meti;

import java.util.ArrayList;
import java.util.List;

public record ListPackage(List<String> namespace, String name) implements Package {
    @Override
    public String computeName() {
        return name;
    }

    @Override
    public List<String> computeNamespace() {
        return new ArrayList<>(namespace);
    }
}
