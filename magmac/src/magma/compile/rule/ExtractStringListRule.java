package magma.compile.rule;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.StringListAttribute;

import java.util.Arrays;
import java.util.Optional;

public final class ExtractStringListRule extends ExtractRule {
    private final String delimiter;

    public ExtractStringListRule(String key, String delimiter) {
        super(key);
        this.delimiter = delimiter;
    }

    @Override
    protected Optional<String> fromAttribute(Attribute attribute) {
        return attribute.asStringList().map(list -> String.join(delimiter, list));
    }

    @Override
    protected Attribute toAttribute(String content) {
        return new StringListAttribute(Arrays.asList(content.split(delimiter)));
    }
}