package magma.compile.attribute;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record StringListAttribute(List<String> values) implements Attribute {
    public static final Factory<List<String>> Factory = new Factory<List<String>>() {
        @Override
        public Optional<List<String>> fromAttribute(Attribute attribute) {
            return attribute.asStringList();
        }

        @Override
        public Attribute toAttribute(List<String> value) {
            return new StringListAttribute(value);
        }
    };

    @Override
    public Optional<List<String>> asStringList() {
        return Optional.of(values);
    }

    @Override
    public String format(int depth) {
        return values.stream()
                .map(value -> "\"" + value + "\"")
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
