package magmac.app.compile.rule;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.divide.DivideState;
import magmac.app.compile.rule.divide.MutableDivideState;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record DivideRule(String key, Rule childRule) implements Rule {
    private static List<String> divide(String input) {
        DivideState current = new MutableDivideState();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            current = DivideRule.fold(current, c);
        }

        return current.advance().stream().toList();
    }

    private static DivideState fold(DivideState state, char c) {
        DivideState appended = state.append(c);
        if (';' == c) {
            return appended.advance();
        }
        else {
            return appended;
        }
    }

    @Override
    public RuleResult<Node> lex(String input) {
        List<String> segments = DivideRule.divide(input);
        RuleResult<List<Node>> result = InlineRuleResult.from(new ArrayList<>());
        for (String segment : segments) {
            String stripped = segment.strip();
            result = result.and(() -> this.childRule.lex(stripped)).map(tuple -> {
                tuple.left().add(tuple.right());
                return tuple.left();
            });
        }

        return result.map(children -> new MapNode().withNodeList(this.key(), children));
    }

    @Override
    public RuleResult<String> generate(Node node) {
        return new InlineRuleResult<>(node.findNodeList(this.key).map(list -> list.stream()
                .map(this.childRule::generate)
                .map(RuleResult::toOptional)
                .flatMap(Optional::stream)
                .collect(Collectors.joining())));
    }
}