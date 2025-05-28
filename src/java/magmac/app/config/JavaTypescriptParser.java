package magmac.app.config;

import magmac.app.compile.error.CompileResult;
import magmac.app.lang.java.JavaRoot;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.UnitSet;

class JavaTypescriptParser implements Parser<JavaRoot, TypescriptRoot> {
    @Override
    public CompileResult<UnitSet<TypescriptRoot>> apply(UnitSet<JavaRoot> initial) {
        throw new UnsupportedOperationException();
    }
}
