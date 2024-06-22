package magma.compile.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConditionEndSearcherTest {
    private static void assertSearch(String input, int index) {
        assertEquals(index, new ConditionEndSearcher()
                .search(input)
                .orElseThrow());
    }

    @Test
    void ignoreWithinCharsAfterEscape() {
        assertSearch("('\\\\' ')')", 9);
    }

    @Test
    void ignoreWithinChars() {
        assertSearch("(')')", 4);
    }

    @Test
    void empty() {
        assertTrue(new ConditionEndSearcher()
                .search("")
                .isEmpty());
    }

    @Test
    void simple() {
        assertSearch("()", 1);
    }

    @Test
    void nested() {
        assertSearch("(())", 3);
    }
}