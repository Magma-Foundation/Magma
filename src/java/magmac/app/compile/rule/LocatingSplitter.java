package magmac.app.compile.rule;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.app.compile.rule.locate.Locator;

public record LocatingSplitter(String infix, Locator locator) implements Splitter {
    @Override
    public Option<Tuple2<String, String>> split(String input) {
        return this.locator.locate(input, this.infix)
                .map(separator -> this.splitAt(input, separator));
    }

    private Tuple2<String, String> splitAt(String input, int separator) {
        var left = input.substring(0, separator);
        var right = input.substring(separator + this.infix.length());
        return new Tuple2<>(left, right);
    }

    @Override
    public String createMessage() {
        return "Infix '" + this.infix + "' not present";
    }

    @Override
    public String merge(String leftString, String rightString) {
        return leftString + this.infix + rightString;
    }
}