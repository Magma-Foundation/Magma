package magmac.app.compile;

import magmac.app.compile.error.CompileResult;
import magmac.app.lang.java.JavaLang;
import magmac.app.stage.unit.UnitSet;

/**
 * Front end of the compiler performing parsing and code generation.
 */
public interface Compiler {
    /**
     * Parses and generates outputs for the provided units.
     *
     * @param units program sources to compile
     * @return a compilation result containing generated outputs or errors
     */
    CompileResult<UnitSet<String>> parseAndGenerate(UnitSet<JavaLang.Root> units);
}
