package magma.compile.rule.text.extract;

import magma.compile.Error_;
import magma.compile.rule.Rule;

import java.util.Optional;

public final class QualifiedExtractStringListRule extends ExtractStringListRule {
    private final Rule qualifier;

    public QualifiedExtractStringListRule(String key, String delimiter, Rule qualifier) {
        super(key, delimiter);
        this.qualifier = qualifier;
    }

    @Override
    protected Optional<Error_> qualify(String child) {
        return qualifier.toNode(child).findError();
    }
}