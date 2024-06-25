package magma.api.json;

import magma.api.option.Option;
import magma.api.option.Some;

public record JSONString(String value) implements JSONValue {
    @Override
    public Option<String> findValue() {
        return new Some<>(value);
    }
}
