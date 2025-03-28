package magma.compile;

import magma.list.List_;

public interface State {
    List_<String> namespace();

    String name();
}
