package magma.app.compile;

import magma.api.collect.List_;

public record ImmutableParseState(List_<String> namespace, String name) implements ParseState {
}