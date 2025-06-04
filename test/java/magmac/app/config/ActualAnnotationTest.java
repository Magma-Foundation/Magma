package magmac.app.config;

import magmac.api.Some;
import magmac.api.None;
import magmac.api.collect.list.Lists;
import magmac.app.lang.common.Annotation;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.java.JavaMethod;
import magmac.app.lang.node.Modifier;
import magmac.app.config.TypeMap;
import magmac.app.io.Location;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ActualAnnotationTest {
    @Test
    public void staticMethodAnnotatedActualLosesBody() throws Exception {
        var annotations = Lists.of(new Annotation("Actual"));
        var modifiers = Lists.of(new Modifier("public"), new Modifier("static"));
        JavaLang.Definition header = new JavaLang.Definition(new Some<>(annotations), modifiers, "f", new None<>(), new JavaLang.Symbol("void"));
        JavaMethod method = new JavaMethod(header, Lists.empty(), new Some<>(Lists.of(new JavaLang.Whitespace())));

        Location loc = new Location(Lists.of("test"), "A");
        TypeMap typeMap = new TypeMap(Lists.empty(), loc);
        var member = JavaTypescriptParser.parseMethod(method, typeMap)
                .toResult()
                .match(v -> v, e -> { throw new RuntimeException(e.display()); });
        assertTrue(member instanceof magmac.app.lang.web.TypescriptLang.TypescriptMethod);
        var tm = (magmac.app.lang.web.TypescriptLang.TypescriptMethod) member;
        assertTrue(tm.maybeChildren().isEmpty());
    }
}
