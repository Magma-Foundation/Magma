package magmac.app.config;

import magmac.api.collect.list.List;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RootParseTest {
    private JavaLang.Root parse(String input) {
        Rule rule = JavaRules.createRule();
        CompileResult<Node> result = rule.lex(input);
        Node node = result.toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });
        return JavaLang.Root.getChildren(node, JavaDeserializers::deserializeRootSegment)
                .toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });
    }

    @Test
    public void multipleStructuresParsed() {
        var root = parse("class A {}\nclass B {}\n");
        var loc = new Location(Lists.of("test"), "File");
        UnitSet<JavaLang.Root> set = new MapUnitSet<JavaLang.Root>().add(new SimpleUnit<>(loc, root));

        var parser = new JavaTypescriptParser();
        var result = parser.apply(set);
        var tsSet = result.toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });
        var tsRoot = tsSet.iter().next().map(u -> u.destruct((l, r) -> r)).orElseGet(() -> { throw new RuntimeException(); });

        List<magmac.app.lang.web.TypescriptLang.TypeScriptRootSegment> children = tsRoot.children();
        int count = children.iter().fold(0, (c, seg) -> seg instanceof magmac.app.lang.web.TypescriptLang.StructureNode ? c + 1 : c);
        assertEquals(2, count);
    }
}
