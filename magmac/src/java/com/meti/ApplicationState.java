package com.meti;

import java.util.List;
import java.util.Optional;

final class ApplicationState {
    public final Optional<String> import_;
    public final String value;
    public final Optional<String> exports;
    public final Optional<String> structs;
    public final List<String> functions;

    ApplicationState(Optional<String> import_, String value, Optional<String> exports, Optional<String> structs, List<String> functions) {
        this.import_ = import_;
        this.value = value;
        this.exports = exports;
        this.structs = structs;
        this.functions = functions;
    }

    public String value() {
        return value;
    }

    public Optional<String> exports() {
        return exports;
    }
}
