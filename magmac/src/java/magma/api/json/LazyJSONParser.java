package magma.api.json;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

public class LazyJSONParser implements JSONParser {
    private Option<JSONParser> value = new None<>();

    public void setValue(JSONParser value) {
        this.value = new Some<>(value);
    }

    @Override
    public Option<JSONValue> parse(String input) {
        return value.flatMap(inner -> inner.parse(input));
    }
}
