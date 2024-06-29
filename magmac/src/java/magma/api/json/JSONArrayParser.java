package magma.api.json;

import magma.api.collect.List;
import magma.api.collect.stream.RequiredCollector;
import magma.api.option.None;
import magma.api.option.Option;
import magma.java.JavaList;

public record JSONArrayParser(JSONParser valueParser) implements JSONParser {
    @Override
    public Option<JSONValue> parse(String input) {
        if (!input.startsWith("[") || !input.endsWith("]")) return None.None();
        return JSON.split(input.substring(1, input.length() - 1))
                .stream()
                .map(String::strip)
                .map(valueParser::parse)
                .collect(new RequiredCollector<List<JSONValue>, JSONValue>(JavaList.collecting()))
                .map(JSONArray::new);
    }
}
