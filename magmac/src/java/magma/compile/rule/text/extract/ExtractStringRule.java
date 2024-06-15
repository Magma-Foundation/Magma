package magma.compile.rule.text.extract;

import magma.api.Ok;
import magma.api.Result;
import magma.compile.Error_;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.StringAttribute;

import java.util.Optional;

public final class ExtractStringRule extends ExtractRule {
    public ExtractStringRule(String key) {
        super(key);
    }

    @Override
    protected Optional<String> fromAttribute(Attribute attribute) {
        return attribute.asString();
    }

    @Override
    protected Result<Attribute, Error_> toAttribute(String content) {
        return new Ok<>(new StringAttribute(content));
    }
}