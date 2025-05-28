package magmac.app.config;

import magmac.api.result.Err;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.context.StringContext;
import magmac.app.error.ImmutableCompileError;
import magmac.app.lang.java.JavaRoot;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.UnitSet;

class JavaPlantUMLParser implements Parser<JavaRoot, PlantUMLRoot> {
    @Override
    public CompileResult<UnitSet<PlantUMLRoot>> apply(UnitSet<JavaRoot> initial) {
        return CompileResults.fromResult(new Err<>(new ImmutableCompileError("Not implemented yet", new StringContext(""))));
    }
}
