package magma.api.json;

import magma.api.collect.List;
import magma.api.collect.stream.Stream;
import magma.api.option.Option;
import magma.api.option.Some;

public record JSONArray(List<JSONValue> items) implements JSONValue {
    @Override
    public Option<Stream<JSONValue>> stream() {
        return new Some<>(items.stream());
    }
}
