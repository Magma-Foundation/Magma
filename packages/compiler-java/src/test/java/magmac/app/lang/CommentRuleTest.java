package magmac.app.lang;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommentRuleTest {
    private Node lex(Rule rule, String input) {
        var result = rule.lex(input);
        return result.toResult().match(v -> v, e -> {
            throw new RuntimeException(e.display());
        });
    }

    @Test
    void testLineComment() {
        var rule = JavaRules.createCommentRule();
        var node = this.lex(rule, "//hello\r\n");
        assertTrue(node.is("comment"));
        assertEquals("hello\r", node.findString("value").orElse(null));
    }

    @Test
    void testLineCommentWithInternalWhitespace() {
        var rule = JavaRules.createCommentRule();
        var node = this.lex(rule, "//  hello  \r\n");
        assertTrue(node.is("comment"));
        assertEquals("  hello  \r", node.findString("value").orElse(null));
    }

    @Test
    void testLineCommentSurroundedWhitespace() {
        var rule = JavaRules.createCommentRule();
        var node = this.lex(rule, "  //hello\r\n  ");
        assertTrue(node.is("comment"));
        assertEquals("hello\r", node.findString("value").orElse(null));
    }

    @Test
    void testBlockComment() {
        var rule = JavaRules.createCommentRule();
        var node = this.lex(rule, "/*hi*/");
        assertTrue(node.is("comment"));
        assertEquals("hi", node.findString("value").orElse(null));
    }

    @Test
    void testBlockCommentWithInternalWhitespace() {
        var rule = JavaRules.createCommentRule();
        var node = this.lex(rule, "/*  hi  */");
        assertTrue(node.is("comment"));
        assertEquals("  hi  ", node.findString("value").orElse(null));
    }

    @Test
    void testBlockCommentSurroundedWhitespace() {
        var rule = JavaRules.createCommentRule();
        var node = this.lex(rule, "  /*hi*/  ");
        assertTrue(node.is("comment"));
        assertEquals("hi", node.findString("value").orElse(null));
    }
}
