package magma.compile.annotate;

import magma.api.option.Option;
import magma.java.JavaSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    @Test
    void defined() {
        var map = new State(new JavaSet<>())
                .enter()
                .define("test")
                .findValue()
                .map(inner -> inner.isDefined("test"))
                .orElse(false);
        assertTrue(map);
    }
}