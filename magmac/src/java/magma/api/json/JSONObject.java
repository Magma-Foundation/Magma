package magma.api.json;

import magma.api.collect.Map;
import magma.api.option.Option;

public record JSONObject(Map<String, JSONValue> entries) implements JSONValue {
    @Override
    public Option<JSONValue> find(String key) {
        return entries.get(key);
    }

}
