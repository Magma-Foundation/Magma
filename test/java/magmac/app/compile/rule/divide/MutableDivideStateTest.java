package magmac.app.compile.rule.divide;

import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MutableDivideStateTest {

    private java.util.List<String> toJavaList(List<String> segments) {
        java.util.List<String> result = new java.util.ArrayList<>();
        segments.iter().fold(result, (r, s) -> { r.add(s); return r; });
        return result;
    }

    @Test
    public void appendAdvanceAndIter() {
        MutableDivideState state = new MutableDivideState("");
        state.append('a').append('b').advance();
        state.append('c').advance();
        var segments = toJavaList(state.iter().collect(new ListCollector<>()));
        assertEquals(java.util.List.of("ab", "c"), segments);
    }

    @Test
    public void popAndPeek() {
        MutableDivideState state = new MutableDivideState("ab");
        var popped = state.pop().orElse(null);
        assertNotNull(popped);
        assertEquals('a', popped.right());
        assertEquals('b', state.peek().orElse(null));
        popped = state.pop().orElse(null);
        assertEquals('b', popped.right());
        assertTrue(state.pop().isEmpty());
    }

    @Test
    public void popAndAppendCreatesSegments() {
        MutableDivideState state = new MutableDivideState("ab");
        while(state.popAndAppendToOption().isPresent()) {}
        state.advance();
        var segments = toJavaList(state.iter().collect(new ListCollector<>()));
        assertEquals(java.util.List.of("ab"), segments);
    }

    @Test
    public void depthManagement() {
        MutableDivideState state = new MutableDivideState("");
        assertTrue(state.isLevel());
        state.enter();
        assertTrue(state.isShallow());
        assertFalse(state.isLevel());
        state.enter();
        assertFalse(state.isShallow());
        state.exit();
        assertTrue(state.isShallow());
        state.exit();
        assertTrue(state.isLevel());
    }

    @Test
    public void quoteCommentEscapeAndLast() {
        MutableDivideState state = new MutableDivideState("");
        state.startSingle();
        assertTrue(state.inSingle());
        state.endSingle();
        state.startDouble();
        assertTrue(state.inDouble());
        state.endDouble();
        state.startLineComment();
        assertTrue(state.inLineComment());
        state.endLineComment();
        state.startBlockComment();
        assertTrue(state.inBlockComment());
        state.endBlockComment();
        state.startEscape();
        assertTrue(state.escape());
        state.endEscape();
        assertFalse(state.escape());
        state.last('x');
        assertEquals('x', state.last());
    }
}
