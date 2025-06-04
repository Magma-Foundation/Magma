package magmac.app.compile.rule.fold;

import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecoratedFolderTest {
    private java.util.List<String> divide(String input) {
        var divider = new magmac.app.compile.rule.divide.FoldingDivider(
                new DecoratedFolder(new DelimitedFolder(',')));
        List<String> segments = divider.divide(input).collect(new ListCollector<>());
        java.util.List<String> result = new java.util.ArrayList<>();
        segments.iter().fold(result, (r, s) -> {
            r.add(s);
            return r;
        });
        return result;
    }

    @Test
    public void testSingleQuotes() {
        var segments = this.divide("a,'b,c',d");
        assertEquals(java.util.List.of("a", "'b,c'", "d"), segments);
    }

    @Test
    public void testDoubleQuotes() {
        var segments = this.divide("a,\"b,c\",d");
        assertEquals(java.util.List.of("a", "\"b,c\"", "d"), segments);
    }

    @Test
    public void testLineComment() {
        var segments = this.divide("a,//b,c\nd,e");
        assertEquals(java.util.List.of("a", "//b,c\n", "d", "e"), segments);
    }

    @Test
    public void testBlockComment() {
        var segments = this.divide("a,/*b,c*/d,e");
        assertEquals(java.util.List.of("a", "/*b,c*/", "d", "e"), segments);
    }

    @Test
    public void testEscapedSingleQuote() {
        var segments = this.divide("'a\\'b,c',d");
        assertEquals(java.util.List.of("'a\\'b,c'", "d"), segments);
    }

    @Test
    public void testEscapedDoubleQuote() {
        var segments = this.divide("\"a\\\"b,c\",d");
        assertEquals(java.util.List.of("\"a\\\"b,c\"", "d"), segments);
    }
}
