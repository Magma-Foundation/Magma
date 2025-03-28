package magma.app.compile;

import magma.api.collect.List_;

public interface State {
    List_<String> namespace();

    String name();
}
