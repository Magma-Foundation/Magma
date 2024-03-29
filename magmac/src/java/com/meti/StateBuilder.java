package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StateBuilder {
    private String value = "";
    private Optional<String> exports = Optional.empty();
    private List<String> functions = new ArrayList<>();
    private Optional<String> importString = Optional.empty();
    private Optional<String> structs = Optional.empty();

    public StateBuilder setValue(String value) {
        this.value = value;
        return this;
    }

    public StateBuilder setExports(Optional<String> exports) {
        this.exports = exports;
        return this;
    }

    public StateBuilder setFunctions(List<String> functions) {
        this.functions = functions;
        return this;
    }

    public StateBuilder setImportString(Optional<String> importString) {
        this.importString = importString;
        return this;
    }

    public StateBuilder setStructs(Optional<String> structs) {
        this.structs = structs;
        return this;
    }

    public ApplicationState createState() {
        return new ApplicationState(importString, value, exports, structs, functions);
    }
}