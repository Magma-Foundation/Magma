package magmac.app.config;

import magmac.api.result.Err;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.context.StringContext;
import magmac.app.error.ImmutableCompileError;
import magmac.app.lang.java.node.JavaRoot;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.UnitSet;

class JavaTypescriptParser implements Parser<JavaRoot, TypescriptRoot> {
    @Override
    public CompileResult<UnitSet<TypescriptRoot>> apply(UnitSet<JavaRoot> initial) {
        return CompileResults.fromResult(new Err<>(new ImmutableCompileError("Not implemented yet", new StringContext(""))));
    }
}
