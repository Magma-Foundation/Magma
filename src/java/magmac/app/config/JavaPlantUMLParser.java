package magmac.app.config;

import magmac.app.compile.error.CompileResult;
import magmac.app.io.Location;
import magmac.app.lang.java.node.JavaRoot;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.UnitSet;

import java.util.function.BiFunction;

class JavaPlantUMLParser implements Parser<JavaRoot, PlantUMLRoot> {
    @Override
    public CompileResult<UnitSet<PlantUMLRoot>> apply(UnitSet<JavaRoot> initial) {
        initial.iter().map(unit -> {
            unit.destruct(new BiFunction<Location, JavaRoot, PlantUMLRoot>() {
                @Override
                public PlantUMLRoot apply(Location location, JavaRoot root) {
                    root.children()
                            .iter()
                            .map(child -> {
                                switch (child) {

                                }
                            });
                }
            });
        });
    }
}
