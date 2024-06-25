package magma.api.json;

import magma.api.option.None;
import magma.api.option.Option;

public class JSONStringParser implements JSONParser {
    @Override
    public Option<JSONValue> parse(String input) {
        return new None<>();
    }
}
