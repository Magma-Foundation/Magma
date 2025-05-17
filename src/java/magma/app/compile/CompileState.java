package magma.app.compile;

import magma.api.Type;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.app.compile.define.Definition;
import magma.app.io.Location;
import magma.app.io.Platform;
import magma.app.io.Source;

import java.util.function.Function;

public interface CompileState {
    Option<String> findLastStructureName();

    String createIndent();

    boolean isPlatform(Platform platform);

    boolean hasLastStructureNameOf(String name);

    CompileState addResolvedImportFromCache(String base);

    CompileState addResolvedImportWithNamespace(List<String> namespace, String child);

    CompileState withLocation(Location namespace);

    CompileState append(String element);

    CompileState pushStructureName(String name);

    CompileState enterDepth();

    CompileState exitDepth();

    CompileState defineAll(List<Definition> definitions);

    Option<Type> resolve(String name);

    CompileState clearImports();

    CompileState clearOutput();

    CompileState addSource(Source source);

    Option<Source> findSource(String name);

    CompileState popStructureName();

    CompileState mapLocation(Function<Location, Location> mapper);

    CompileState withPlatform(Platform platform);

    List<Import> imports();

    String output();

    Option<Location> findCurrentLocation();

    Platform platform();
}
