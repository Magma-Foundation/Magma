package magma.api.json;

import magma.api.contain.stream.Stream;
import magma.api.option.None;
import magma.api.option.Option;

public interface JSONValue {
    default Option<JSONValue> find(String key) {
        return None.None();
    }

    default Option<String> findValue() {
        return None.None();
    }

    default Option<Stream<JSONValue>> stream() {
        return None.None();
    }
}
