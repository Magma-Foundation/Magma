package magma.compile.lang;

import magma.compile.Error_;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.attribute.StringAttribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.ImmutableNode;
import magma.compile.rule.Node;
import magma.compile.rule.result.RuleResult;
import magma.java.JavaOptionals;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class JavaDefinitionHeaderFactoryTest {
    private static void assertParse(String input, String propertyKey, Attribute test) {
        var optional = parseImpl(input);
        assertEquals(test, optional.orElseThrow()
                .attributes()
                .apply(propertyKey)
                .orElseThrow());
    }

    private static Optional<Node> parseImpl(String input) {
        var rule = JavaDefinitionHeaderFactory.createDefinitionHeaderRule();
        var result = rule.toNode(input);
        if (JavaOptionals.toNative(result.findError()).isPresent()) {
            var error = JavaOptionals.toNative(result.findError()).orElseThrow();
            fail(toException(error));
        }

        return JavaOptionals.toNative(result.tryCreate());
    }

    private static RuntimeException toException(Error_ error) {
        var message = error.findMessage().orElse("");
        var context = error.findContext().orElse("");

        var causes = error.findCauses().orElse(Collections.emptyList());
        if (causes.isEmpty()) {
            return new RuntimeException(message + context);
        } else {
            return new RuntimeException(message, toException(causes.get(0)));
        }
    }

    private static void assertParseModifiers(String input, String key, List<String> list) {
        assertParse(input, key, new StringListAttribute(list));
    }

    @Test
    void spaceInType() {
        RuleResult ruleResult = Lang.createTypeRule()
                .toNode("First<L, R>");
        assertParse("public <L, R> First<L, R> test", "type", new NodeAttribute(JavaOptionals.toNative(ruleResult.tryCreate())
                .orElseThrow()));
    }

    @Test
    void modifiersAndGenerics() {
        assertTrue(parseImpl("public <T> var test").isPresent());
    }

    @Test
    void oneTypeParam() {
        assertParse("<T> var test", "type-params", new StringListAttribute(List.of("T")));
    }

    @Test
    void twoTypeParams() {
        assertParse("<L, R> var test", "type-params", new StringListAttribute(List.of("L", "R")));
    }

    @Test
    void oneAnnotation() {
        assertParse("@Test\nvar test;", "annotations", new NodeListAttribute(List.of(new ImmutableNode("annotation")
                .withString("value", "Test"))));
    }

    @Test
    void twoAnnotations() {
        assertParse("@First\n@Second\nvar test;", "annotations", new NodeListAttribute(List.of(
                new ImmutableNode("annotation").withString("value", "First"),
                new ImmutableNode("annotation").withString("value", "Second")
        )));
    }

    @Test
    void all() {
        assertTrue(parseImpl("@First\n@Second\npublic static <L, R> var test").isPresent());
    }

    @Test
    void oneModifier() {
        assertParseModifiers("public var test", "modifiers", List.of("public"));
    }

    @Test
    void twoModifiers() {
        assertParseModifiers("public static var test", "modifiers", List.of("public", "static"));
    }

    @Test
    void name() {
        assertParse("var test", "name", new StringAttribute("test"));
    }

    @Test
    void type() {
        RuleResult ruleResult = Lang.createTypeRule().toNode("var");
        assertParse("var test", "type", new NodeAttribute(JavaOptionals.toNative(ruleResult.tryCreate()).orElseThrow()));
    }
}