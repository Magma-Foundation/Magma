package magmac.app.config;

import magmac.api.None;
import magmac.api.collect.list.Lists;
import magmac.app.io.Location;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.java.JavaMethod;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.web.TypescriptLang;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VariadicParameterTest {
    @Test
    public void variadicParameterBecomesArray() {
        var param = new JavaLang.Definition(new None<>(), Lists.empty(), "a", new None<>(),
                new JavaLang.JavaVariadicType(new JavaLang.Symbol("int")));
        var header = new JavaLang.Definition(new None<>(), Lists.empty(), "f", new None<>(),
                new JavaLang.Symbol("void"));
        var method = new JavaMethod(header, Lists.of(param), new None<>());

        var loc = new Location(Lists.of("test"), "A");
        var state = new CompileState(Lists.empty(), loc);
        JavaTypescriptParser.parseMethod(method, state)
                .toResult()
                .consume(m -> {
                    assertTrue(m instanceof TypescriptLang.TypescriptMethod);
                    var tsMethod = (TypescriptLang.TypescriptMethod) m;
                    var first = tsMethod.header().parameters().get(0);
                    assertTrue(first instanceof TypescriptLang.Definition);
                    var def = (TypescriptLang.Definition) first;
                    assertEquals("...a", def.name());
                    assertTrue(def.type() instanceof TypescriptLang.ArrayType);
                }, err -> Assertions.fail(err.display()));
    }
}
