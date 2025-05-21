package magma.app.compile.select;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.option.Some;

public record FirstSelector(String delimiter) implements Selector {
    @Override
    public Option<Tuple2<String, String>> select(List<String> divisions) {
        var first = divisions.findFirst().orElse("");
        var afterFirst = divisions.subList(1, divisions.size()).orElse(divisions)
                .iter()
                .collect(new Joiner(this.delimiter()))
                .orElse("");

        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(first, afterFirst));
    }
}