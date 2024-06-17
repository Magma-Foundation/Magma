package magma.compile.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvocationStartRuleTest {

    @Test
    void computeIndex() {
        var present = new InvocationStartRule(null, null)
                .computeIndex("TypeRule(\"string\", new LeftRule(\"\\\"\", new RightRule(new ExtractStringRule(\"value\"), \"\\\"\"))")
                .isPresent();

        Assertions.assertTrue(present);
    }
}