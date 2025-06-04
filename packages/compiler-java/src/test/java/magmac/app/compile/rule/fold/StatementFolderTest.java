package magmac.app.compile.rule.fold;

import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.divide.MutableDivideState;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatementFolderTest {
    private java.util.List<String> divide(String input) {
        var divider = new FoldingDivider(new StatementFolder());
        List<String> segments = divider.divide(input).collect(new ListCollector<>());
        java.util.List<String> result = new java.util.ArrayList<>();
        segments.iter().fold(result, (r, s) -> { r.add(s); return r; });
        return result;
    }

    @Test
    public void semicolonAdvancesAtLevel() {
        var segments = divide("a;b");
        assertEquals(java.util.List.of("a;", "b"), segments);
    }

    @Test
    public void closingBraceAdvancesAndExits() {
        var segments = divide("{a}{b}");
        assertEquals(java.util.List.of("{a}", "{b}", ""), segments);
    }

    @Test
    public void openingBraceEntersDepth() {
        var folder = new StatementFolder();
        MutableDivideState state = new MutableDivideState("");
        state = (MutableDivideState) folder.fold(state, '{');
        assertTrue(state.isShallow());
        assertTrue(state.iter().collect(new ListCollector<>()).isEmpty());
    }

    @Test
    public void closingParenExitsDepth() {
        var folder = new StatementFolder();
        MutableDivideState state = new MutableDivideState("");
        state.enter();
        state.enter();
        state = (MutableDivideState) folder.fold(state, ')');
        assertTrue(state.isShallow());
        assertTrue(state.iter().collect(new ListCollector<>()).isEmpty());
    }

    @Test
    public void defaultAppendsCharacter() {
        var folder = new StatementFolder();
        MutableDivideState state = new MutableDivideState("");
        state = (MutableDivideState) folder.fold(state, 'x');
        state.advance();
        java.util.List<String> segments = new java.util.ArrayList<>();
        state.iter().collect(new ListCollector<>()).iter().fold(segments, (r, s) -> { r.add(s); return r; });
        assertEquals(java.util.List.of("x"), segments);
        assertTrue(state.isLevel());
    }
}
