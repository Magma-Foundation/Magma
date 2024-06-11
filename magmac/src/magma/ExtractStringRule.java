package magma;

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
    protected Attribute toAttribute(String content) {
        return new StringAttribute(content);
    }
}