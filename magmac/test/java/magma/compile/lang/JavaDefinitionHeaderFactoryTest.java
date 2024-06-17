package magma.compile.lang;

import magma.compile.attribute.Attribute;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaDefinitionHeaderFactoryTest {
    @Test
    void test() {
        var rule = JavaDefinitionHeaderFactory.createDefinitionHeaderRule();
        var result = rule.toNode("var test");
        if (result.findError().isPresent())
            throw new RuntimeException(result.findError().get().findMessage().orElseThrow());

        assertEquals("test", result.create().orElseThrow()
                .attributes()
                .apply("name")
                .flatMap(Attribute::asString)
                .orElseThrow());
    }
}