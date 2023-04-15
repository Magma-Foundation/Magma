package com.meti;

import java.util.*;

public final class ImportCache {
    private final Map<String, List<String>> imports;

    public ImportCache() {
        this(Collections.emptyMap());
    }

    public ImportCache(Map<String, List<String>> imports) {
        this.imports = new HashMap<>(imports);
    }

    ImportCache addImport(List<String> anImport) {
        List<String> children;
        if (imports().containsKey(anImport.get(0))) {
            children = imports().get(anImport.get(0));
        } else {
            children = new ArrayList<>();
        }

        imports().put(anImport.get(0), children);
        return this;
    }

    public Map<String, List<String>> imports() {
        return imports;
    }
}