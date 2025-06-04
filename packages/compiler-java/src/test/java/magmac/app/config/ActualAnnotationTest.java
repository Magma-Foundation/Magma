package magmac.app.config;

import magmac.api.None;
import magmac.api.Some;
import magmac.api.collect.list.Lists;
import magmac.app.io.Location;
import magmac.app.lang.common.Annotation;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.java.JavaMethod;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.web.TypescriptLang.TypescriptMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActualAnnotationTest {
    @Test
    void staticMethodAnnotatedActualLosesBody() {
        var annotations = Lists.of(new Annotation("Actual"));
        var modifiers = Lists.of(new Modifier("public"), new Modifier("static"));
        var header = new JavaLang.Definition(new Some<>(annotations), modifiers, "f", new None<>(), new JavaLang.Symbol("void"));
        var method = new JavaMethod(header, Lists.empty(), new Some<>(Lists.of(new JavaLang.Whitespace())));

        var loc = new Location(Lists.of("test"), "A");
        var typeMap = new CompileState(Lists.empty(), loc);
        JavaTypescriptParser.parseMethod(method, typeMap)
                .toResult()
                .consume(member -> {
                    assertInstanceOf(TypescriptMethod.class, member);
                    var tm = (TypescriptMethod) member;
                    assertTrue(tm.maybeChildren().isEmpty());
                }, err -> {
                    Assertions.fail(err.display());
                });

    }
}
