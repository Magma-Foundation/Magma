package magmac.app.config;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;
import magmac.app.lang.JavaRules;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.java.JavaLang;
import magmac.app.stage.unit.MapUnitSet;
import magmac.app.stage.unit.SimpleUnit;
import magmac.app.stage.unit.UnitSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaImportTest {
    private JavaLang.Root parse(String input) {
        Rule rule = JavaRules.createRule();
        CompileResult<Node> result = rule.lex(input);
        Node node = result.toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });
        return JavaLang.Root.getChildren(node, JavaDeserializers::deserializeRootSegment)
                .toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });
    }

    @Test
    public void javaImportsAreAllowed() {
        String code = "package a;\nimport java.util.List;\nclass A {}\n";
        var root = parse(code);
        var loc = new Location(Lists.of("test"), "A");
        UnitSet<JavaLang.Root> set = new MapUnitSet<JavaLang.Root>().add(new SimpleUnit<>(loc, root));

        var parser = new JavaTypescriptParser();
        var result = parser.apply(set);
        boolean ok = result.toResult().match(v -> true, err -> false);
        assertTrue(ok);
    }
}
