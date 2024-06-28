package magma.compile.rule.text.extract;

import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.StringAttribute;
import magma.compile.rule.Node;

import java.util.Optional;

public final class ExtractStringRule extends ExtractRule {
    public ExtractStringRule(String key) {
        super(key);
    }

    @Override
    protected Optional<String> fromAttribute(Node attribute) {
        return attribute.asString();
    }

    @Override
    protected Result<Attribute, Error_> toAttribute(String content) {
        return new Ok<>(new StringAttribute(content));
    }
}