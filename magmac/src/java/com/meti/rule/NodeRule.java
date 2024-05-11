package com.meti.rule;

import com.meti.Tuple;
import com.meti.api.Result;
import com.meti.api.ThrowableOption;
import com.meti.node.MapNode;
import com.meti.api.Options;

import java.util.Optional;

import static com.meti.node.NodeAttributes.NodeAttributesBuilder;

public record NodeRule(String name, Rule content) implements Rule {
    public static Rule Node(String name, TypeRule name1) {
        return new NodeRule(name, name1);
    }

    private Optional<String> toString1(MapNode node) {
        var apply = Options.toNative(node.apply(name));
        if (apply.isEmpty()) return Optional.empty();

        var childNode = Options.toNative(apply.get().asNode());
        if (childNode.isEmpty()) return Optional.empty();

        return Options.toNative(content.toString(childNode.get()).value());
    }

    @Override
    public RuleResult fromString(String value) {
        var parentResult = content.fromString(value);
        var attributes = parentResult.getAttributes();
        if (attributes.isEmpty()) {
            return new ParentRuleResult("Content result was invalid.", value, parentResult);
        }

        var name = parentResult.getType();
        if (name.isEmpty()) {
            return new ParentRuleResult("Usage of '" + getClass().getName() + "' requires a name to be present in the content, which was not provided.", value, parentResult);
        }

        var wrappedAttributes = NodeAttributesBuilder()
                .withNode(this.name, name.get(), attributes.get())
                .complete();

        return new NodeRuleResult(new Tuple<>(wrappedAttributes, Optional.empty()));
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}
