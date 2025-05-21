package magma.app.compile;

import magma.api.collect.list.Iterable;
import magma.app.Location;
import magma.app.Platform;
import magma.app.compile.define.Definition;
import magma.app.io.Source;

public interface CompileState {
    String createIndent();

    CompileState addResolvedImport(Location location);

    CompileState withLocation(Location location);

    CompileState append(String element);

    CompileState pushStructureName(String name);

    CompileState enterDepth();

    CompileState exitDepth();

    CompileState defineAll(Iterable<Definition> definitions);

    CompileState clearImports();

    CompileState clearOutput();

    CompileState addSource(Source source);

    CompileState addResolvedImportFromCache(String base);

    CompileState popStructureName();

    CompileState withPlatform(Platform platform);

    Context context();

    Registry registry();

    Stack stack();
}
