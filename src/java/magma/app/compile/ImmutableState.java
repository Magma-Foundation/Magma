package magma.app.compile;

import magma.api.collect.List_;

public record ImmutableState(List_<String> namespace, String name) implements State {
}