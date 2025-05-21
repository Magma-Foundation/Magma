package magma.app.compile;

import magma.api.collect.Query;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.app.compile.define.Definition;
import magma.app.io.Source;

public interface CompileState {
    String join(String otherOutput);

    Query<Import> queryImports();

    Query<Source> querySources();

    String createIndent();

    Option<String> findLastStructureName();

    boolean isLastWithin(String name);

    CompileState addResolvedImport(List<String> parent, String child);

    CompileState withNamespace(List<String> namespace);

    CompileState append(String element);

    CompileState pushStructureName(String name);

    CompileState enterDepth();

    CompileState exitDepth();

    CompileState defineAll(List<Definition> definitions);

    Option<Definition> resolve(String name);

    CompileState clearImports();

    CompileState clearOutput();

    CompileState addSource(Source source);

    Option<Source> findSource(String name);

    CompileState addResolvedImportFromCache(String base);

    CompileState popStructureName();
}
