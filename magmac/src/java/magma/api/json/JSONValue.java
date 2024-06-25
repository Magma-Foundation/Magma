package magma.api.json;

import magma.api.Tuple;
import magma.api.collect.stream.Stream;
import magma.api.option.None;
import magma.api.option.Option;

public interface JSONValue {
    default Option<JSONValue> find(String key) {
        return new None<>();
    }

    default Option<Stream<Tuple<String, JSONValue>>> entryStream() {
        return new None<>();
    }

    default Option<String> findValue() {
        return new None<>();
    }

    default Option<Stream<JSONValue>> stream() {
        return new None<>();
    }
}
