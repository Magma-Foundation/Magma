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

    @Test
    public void testCommentMarkersInsideQuotes() {
        var segments = this.divide("a,'//b',c");
        assertEquals(java.util.List.of("a", "'//b'", "c"), segments);
        segments = this.divide("a,\"/*b*/\",c");
        assertEquals(java.util.List.of("a", "\"/*b*/\"", "c"), segments);
    }

    @Test
    public void testQuotesInsideBlockComment() {
        var segments = this.divide("a,/*'b,c'*/d,e");
        assertEquals(java.util.List.of("a", "/*'b,c'*/", "d", "e"), segments);
    }

    @Test
    public void testBlockCommentWithNewline() {
        var segments = this.divide("a,/*b\nc*/d,e");
        assertEquals(java.util.List.of("a", "/*b\nc*/", "d", "e"), segments);
    }

    @Test
    public void testLineCommentNoNewline() {
        var segments = this.divide("a,//b,c");
        assertEquals(java.util.List.of("a", "//b,c"), segments);
    }

    @Test
    public void testMultilineDoubleQuote() {
        var segments = this.divide("a,\"b\nc\",d");
        assertEquals(java.util.List.of("a", "\"b\nc\"", "d"), segments);
    }

    @Test
    public void testSingleQuoteWithNewline() {
        var segments = this.divide("a,'b\nc',d");
        assertEquals(java.util.List.of("a", "'b\nc'", "d"), segments);
    }

    @Test
    public void testUnterminatedSingleQuote() {
        var segments = this.divide("'a,b");
        assertEquals(java.util.List.of("'a,b"), segments);
    }

    @Test
    public void testUnterminatedDoubleQuote() {
        var segments = this.divide("a,\"b,c");
        assertEquals(java.util.List.of("a", "\"b,c"), segments);
    }

    @Test
    public void testUnterminatedBlockComment() {
        var segments = this.divide("a,/*b,c");
        assertEquals(java.util.List.of("a", "/*b,c"), segments);
    }

    @Test
    public void testBlockCommentWithCommentMarker() {
        var segments = this.divide("a,/*b//c*/d,e");
        assertEquals(java.util.List.of("a", "/*b//c*/", "d", "e"), segments);
    }

    @Test
    public void testDelimiterCreation() {
        Folder folder = new DecoratedFolder(new DelimitedFolder(','));
        assertEquals(",", folder.createDelimiter());
    }
}
