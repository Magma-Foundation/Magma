package magma.compile.rule.text.extract;

import magma.compile.Error_;
import magma.compile.rule.Rule;
import magma.compile.rule.result.RuleResult;
import magma.java.JavaOptionals;

import java.util.Optional;

public final class QualifiedExtractStringListRule extends ExtractStringListRule {
    private final Rule qualifier;

    public QualifiedExtractStringListRule(String key, String delimiter, Rule qualifier) {
        super(key, delimiter);
        this.qualifier = qualifier;
    }

    @Override
    protected Optional<Error_> qualify(String child) {
        RuleResult ruleResult = qualifier.toNode(child);
        return JavaOptionals.toNative(ruleResult.findError());
    }
}