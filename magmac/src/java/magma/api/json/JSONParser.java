package magma.api.json;

import magma.api.option.Option;

public interface JSONParser {
    Option<JSONValue> parse(String input);
}
