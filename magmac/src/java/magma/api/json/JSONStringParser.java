package magma.api.json;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

public class JSONStringParser implements JSONParser {
    @Override
    public Option<JSONValue> parse(String input) {
        return input.startsWith("\"") && input.endsWith("\"")
                ? new Some<>(new JSONString(input.substring(1, input.length() - 1)))
                : new None<>();
    }
}
