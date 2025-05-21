package magma.app.compile.split;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.locate.Locator;

public record LocatingSplitter(String infix, Locator locator) implements Splitter {
    @Override
    public Option<Tuple2<String, String>> apply(String input) {
        var index = this.locator.apply(input, this.infix);
        if (0 > index) {
            return new None<Tuple2<String, String>>();
        }

        var left = Strings.sliceBetween(input, 0, index);

        var length = Strings.length(this.infix);
        var right = Strings.sliceFrom(input, index + length);
        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(left, right));
    }
}
