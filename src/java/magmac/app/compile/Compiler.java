package magmac.app.compile;

import magmac.app.compile.error.CompileResult;
import magmac.app.lang.java.JavaLang;
import magmac.app.stage.unit.UnitSet;

public interface Compiler {
    CompileResult<UnitSet<String>> parseAndGenerate(UnitSet<JavaLang.Root> units);
}
