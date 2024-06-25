package magma.api.json;

import magma.api.Tuple;
import magma.api.collect.Map;
import magma.api.collect.stream.Stream;
import magma.api.option.Option;
import magma.api.option.Some;

public record JSONObject(Map<String, JSONValue> entries) implements JSONValue {
    @Override
    public Option<JSONValue> find(String key) {
        return entries.get(key);
    }

    @Override
    public Option<Stream<Tuple<String, JSONValue>>> entryStream() {
        return new Some<>(entries.streamEntries());
    }
}
