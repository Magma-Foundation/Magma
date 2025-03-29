package magma.compile;

import magma.collect.list.List_;

import java.io.IOException;

public interface Source {
    List_<String> computeNamespace();

    String computeName();

    String readString() throws IOException;
}
