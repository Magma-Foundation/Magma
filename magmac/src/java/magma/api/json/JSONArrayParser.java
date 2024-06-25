package magma.api.json;

import magma.api.collect.stream.Collectors;
import magma.api.option.None;
import magma.api.option.Option;
import magma.java.JavaList;

public record JSONArrayParser(JSONParser valueParser) implements JSONParser {
    @Override
    public Option<JSONValue> parse(String input) {
        if (!input.startsWith("[") || !input.endsWith("]")) return new None<>();
        return JSON.split(input.substring(1, input.length() - 1))
                .stream()
                .map(String::strip)
                .map(valueParser::parse)
                .collect(Collectors.required(JavaList.collecting()))
                .map(JSONArray::new);
    }
}
