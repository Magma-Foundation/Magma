package magmac.app.lang;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommentRuleTest {
    private Node lex(Rule rule, String input) {
        CompileResult<Node> result = rule.lex(input);
        return result.toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });
    }

    @Test
    public void testLineComment() {
        Rule rule = JavaRules.createCommentRule();
        Node node = lex(rule, "//hello\r\n");
        assertTrue(node.is("comment"));
        assertEquals("hello", node.findString("value").orElse(null));
    }

    @Test
    public void testLineCommentWithWhitespace() {
        Rule rule = JavaRules.createCommentRule();
        Node node = lex(rule, "//  hello  \r\n");
        assertTrue(node.is("comment"));
        assertEquals("hello", node.findString("value").orElse(null));
    }

    @Test
    public void testBlockComment() {
        Rule rule = JavaRules.createCommentRule();
        Node node = lex(rule, "/*hi*/");
        assertTrue(node.is("comment"));
        assertEquals("hi", node.findString("value").orElse(null));
    }

    @Test
    public void testBlockCommentWithWhitespace() {
        Rule rule = JavaRules.createCommentRule();
        Node node = lex(rule, "/*  hi  */");
        assertTrue(node.is("comment"));
        assertEquals("hi", node.findString("value").orElse(null));
    }
}
