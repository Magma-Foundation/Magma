package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StripRuleTest {
    @Test
    public void trimsWhitespaceOnLex() {
        Rule rule = new StripRule(new StringRule("value"));
        CompileResult<Node> result = rule.lex("  hi  ");
        Node node = result.toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });
        assertEquals("hi", node.findString("value").orElse(null));
    }

    @Test
    public void usesBeforeAfterOnGenerate() {
        Rule rule = new StripRule(new StringRule("value"));
        Node node = new MapNode()
                .withString("value", "x")
                .withString("before", "<")
                .withString("after", ">");
        String generated = rule.generate(node).toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });
        assertEquals("<x>", generated);
    }
}
