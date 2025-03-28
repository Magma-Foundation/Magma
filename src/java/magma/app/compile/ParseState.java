package magma.app.compile;

import magma.api.collect.List_;

public interface ParseState {
    List_<String> namespace();

    String name();
}
