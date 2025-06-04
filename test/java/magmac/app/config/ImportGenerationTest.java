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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImportGenerationTest {
    private JavaLang.Root parse(String input) {
        Rule rule = JavaRules.createRule();
        CompileResult<Node> result = rule.lex(input);
        Node node = result.toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });
        return JavaLang.Root.getChildren(node, JavaDeserializers::deserializeRootSegment)
                .toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });
    }

    @Test
    public void importsGeneratedForReferencedType() {
        var rootB = parse("class B {}\n");
        var rootA = parse("class A { B f(B b); }\n");

        var locB = new Location(Lists.of("foo"), "B");
        var locA = new Location(Lists.of("bar"), "A");

        UnitSet<JavaLang.Root> set = new MapUnitSet<JavaLang.Root>()
                .add(new SimpleUnit<>(locA, rootA))
                .add(new SimpleUnit<>(locB, rootB));

        var parser = new JavaTypescriptParser();
        var result = parser.apply(set);
        var tsSet = result.toResult().match(v -> v, e -> { throw new RuntimeException(e.display()); });

        var tsRootA = tsSet.iter()
                .filter(unit -> unit.destruct((l, r) -> l.equals(locA)))
                .next()
                .map(unit -> unit.destruct((l, r) -> r))
                .orElseThrow();

        List<magmac.app.lang.web.TypescriptLang.TypeScriptRootSegment> children = tsRootA.children();
        boolean hasImport = children.iter().filter(c -> c instanceof magmac.app.lang.web.TypescriptLang.TypeScriptImport)
                .next().isPresent();
        assertTrue(hasImport);
    }
}
