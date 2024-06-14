package magma.compile.rule.text.extract;

import magma.api.Err;
import magma.api.Ok;
import magma.api.Result;
import magma.compile.Error_;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.StringListAttribute;

import java.util.Arrays;
import java.util.Optional;

public abstract class ExtractStringListRule extends ExtractRule {
    protected final String delimiter;

    public ExtractStringListRule(String key, String delimiter) {
        super(key);
        this.delimiter = delimiter;
    }

    @Override
    protected Optional<String> fromAttribute(Attribute attribute) {
        return attribute.asStringList().map(list -> String.join(delimiter, list));
    }

    @Override
    protected Result<Attribute, Error_> toAttribute(String content) {
        var list = Arrays.asList(content.split(delimiter));
        for (String format : list) {
            var qualified = qualify(format);
            if (qualified.isPresent()) {
                return new Err<>(qualified.get());
            }
        }

        return new Ok<>(new StringListAttribute(list));
    }

    protected abstract Optional<Error_> qualify(String child);
}
