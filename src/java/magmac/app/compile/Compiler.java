package magmac.app.compile;

import magmac.app.compile.error.CompileResult;
import magmac.app.lang.node.JavaRootSegment;
import magmac.app.lang.node.Root;
import magmac.app.stage.unit.UnitSet;

public interface Compiler {
    CompileResult<UnitSet<String>> parseAndGenerate(UnitSet<Root<JavaRootSegment>> units);
}
