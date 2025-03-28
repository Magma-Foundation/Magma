package magma.app.compile.rule;

import jvm.api.collect.Maps;
import magma.api.collect.Map_;
import magma.api.result.Tuple;

public record MapOutput(Map_<String, String> map) implements Output {
    public MapOutput() {
        this(Maps.empty());
    }

    @Override
    public Tuple<String, String> toTuple() {
        return new Tuple<>(map.get("header").orElse(""), map.get("target").orElse(""));
    }

    @Override
    public Output with(String type, String content) {
        return new MapOutput(map.put(type, content));
    }
}
