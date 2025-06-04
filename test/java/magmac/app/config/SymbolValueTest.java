package magmac.app.config;

import magmac.api.None;
import magmac.api.Some;
import magmac.api.collect.list.Lists;
import magmac.app.io.Location;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.java.JavaMethod;
import magmac.app.lang.java.JavaParameter;
import magmac.app.lang.web.TypescriptLang;
import magmac.app.lang.web.TypescriptLang.TypescriptMethod;
import magmac.app.compile.error.CompileResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SymbolValueTest {
    @Test
    public void unknownValueFails() {
        var header = new JavaLang.Definition(new None<>(), Lists.empty(), "f", new None<>(), new JavaLang.Symbol("void"));
        var returnSeg = new JavaLang.Return(new JavaLang.Symbol("a"));
        var method = new JavaMethod(header, Lists.empty(), new Some<>(Lists.of(returnSeg)));

        var loc = new Location(Lists.of("test"), "A");
        var state = new CompileState(Lists.empty(), loc);
        CompileResult<TypescriptLang.TypescriptStructureMember> result = JavaTypescriptParser.parseMethod(method, state);
        boolean failed = result.toResult().match(v -> false, err -> true);
        assertTrue(failed);
    }

    @Test
    public void typeAsValueFails() {
        var header = new JavaLang.Definition(new None<>(), Lists.empty(), "f", new None<>(), new JavaLang.Symbol("void"));
        var returnSeg = new JavaLang.Return(new JavaLang.Symbol("A"));
        var method = new JavaMethod(header, Lists.empty(), new Some<>(Lists.of(returnSeg)));

        var loc = new Location(Lists.of("test"), "B");
        var types = Lists.of(new magmac.api.Tuple2<>(Lists.<String>empty(), "A"));
        var state = new CompileState(types, loc);
        CompileResult<TypescriptLang.TypescriptStructureMember> result = JavaTypescriptParser.parseMethod(method, state);
        boolean failed = result.toResult().match(v -> false, err -> true);
        assertTrue(failed);
    }

    @Test
    public void parameterValueSucceeds() {
        JavaParameter param = new JavaLang.Definition(new None<>(), Lists.empty(), "a", new None<>(), new JavaLang.Symbol("int"));
        var header = new JavaLang.Definition(new None<>(), Lists.empty(), "f", new None<>(), new JavaLang.Symbol("void"));
        var returnSeg = new JavaLang.Return(new JavaLang.Symbol("a"));
        var method = new JavaMethod(header, Lists.of(param), new Some<>(Lists.of(returnSeg)));

        var loc = new Location(Lists.of("test"), "A");
        var state = new CompileState(Lists.empty(), loc);
        CompileResult<TypescriptLang.TypescriptStructureMember> result = JavaTypescriptParser.parseMethod(method, state);
        result.toResult().consume(m -> assertInstanceOf(TypescriptMethod.class, m), err -> Assertions.fail(err.display()));
    }
}
