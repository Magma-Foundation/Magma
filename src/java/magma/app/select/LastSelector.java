package magma.app.select;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.Selector;

public record LastSelector(String delimiter) implements Selector {
    @Override
    public Option<Tuple2<String, String>> apply(List<String> divisions) {
        var beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
        var last = divisions.findLast().orElse("");

        var joined = beforeLast.iter()
                .collect(new Joiner(this.delimiter))
                .orElse("");

        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(joined, last));
    }
}
