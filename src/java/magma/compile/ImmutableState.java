package magma.compile;

import magma.list.List_;

public record ImmutableState(List_<String> namespace, String name) implements State {
}