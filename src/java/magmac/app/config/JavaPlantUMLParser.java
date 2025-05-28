package magmac.app.config;

import magmac.app.compile.error.CompileResult;
import magmac.app.lang.java.JavaRoot;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.UnitSet;

class JavaPlantUMLParser implements Parser<JavaRoot, PlantUMLRoot> {
    @Override
    public CompileResult<UnitSet<PlantUMLRoot>> apply(UnitSet<JavaRoot> initial) {
        throw new UnsupportedOperationException();
    }
}
