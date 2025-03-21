package magma;

import java.util.Optional;

public final class IndexSplitter implements Splitter {
    private final String slice;
    private final Locator locator;

    public IndexSplitter(String slice, Locator locator) {
        this.slice = slice;
        this.locator = locator;
    }

    @Override
    public Optional<Tuple<String, String>> split(String input) {
        return locator.locate(slice, input).map(index -> {
            final var left = input.substring(0, index);
            final var right = input.substring(index + slice.length());
            return new Tuple<>(left, right);
        });
    }
}