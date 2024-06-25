package magma.api.json;

import magma.api.collect.List;
import magma.api.collect.stream.Streams;
import magma.api.option.Option;

public record CompoundJSONParser(List<JSONParser> parsers) implements JSONParser {
    @Override
    public Option<JSONValue> parse(String input) {
        return parsers.stream()
                .map(parser -> parser.parse(input))
                .flatMap(Streams::fromOption)
                .head();
    }
}
