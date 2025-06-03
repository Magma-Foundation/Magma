package magmac.app.compile.rule.divide;

import magmac.api.iter.Iter;
import magmac.api.iter.Iters;

import java.util.regex.Pattern;

public record DelimitedDivider(String delimiter) implements Divider {
    @Override
    public Iter<String> divide(String input) {
        return Iters.fromValues(input.split(Pattern.quote(this.delimiter)));
    }

    @Override
    public String createDelimiter() {
        return this.delimiter;
    }
}
