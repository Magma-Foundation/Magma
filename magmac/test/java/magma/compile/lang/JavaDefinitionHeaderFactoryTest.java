package magma.compile.lang;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.StringAttribute;
import magma.compile.attribute.StringListAttribute;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaDefinitionHeaderFactoryTest {
    private static void assertParse(String input, String propertyKey, Attribute test) {
        var rule = JavaDefinitionHeaderFactory.createDefinitionHeaderRule();
        var result = rule.toNode(input);
        if (result.findError().isPresent())
            throw new RuntimeException(result.findError().get().findMessage().orElseThrow());

        assertEquals(test, result.create().orElseThrow()
                .attributes()
                .apply(propertyKey)
                .orElseThrow());
    }



    private static void assertParseModifiers(String input, List<String> list) {
        assertParse(input, "modifiers", new StringListAttribute(list));
    }

    @Test
    void oneModifier() {
        assertParseModifiers("public var test", List.of("public"));
    }

    @Test
    void twoModifiers() {
        assertParseModifiers("public static var test", List.of("public", "static"));
    }

    @Test
    void name() {
        assertParse("var test", "name", new StringAttribute("test"));
    }

    @Test
    void type() {
        assertParse("var test", "type", new NodeAttribute(Lang.createTypeRule().toNode("var").create().orElseThrow()));
    }
}