package magmac.app.io.sources;

import magmac.api.collect.list.List;
import magmac.api.iter.Iter;
import magmac.api.iter.collect.ListCollector;
import magmac.api.iter.collect.ResultCollector;
import magmac.api.result.Result;
import magmac.app.io.IOResult;
import magmac.app.io.InlineIOResult;
import magmac.app.io.SafeFiles;
import magmac.app.annotation.Actual;
import magmac.app.stage.unit.SimpleUnit;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Actual
public record PathSources(Path root) implements Sources {
    private static IOResult<Unit<String>> getTuple2IOResult(PathSource source) {
        return source.read().mapValue((String input) -> new SimpleUnit<>(source.computeLocation(), input));
    }

    @Override
    public IOResult<UnitSet<String>> readAll() {
        return SafeFiles.walk(this.root)
                .flatMapValue(this::apply);
    }

    private IOResult<UnitSet<String>> apply(Iter<Path> sources) {
        return new InlineIOResult<>(this.getCollect(sources));
    }

    private Result<UnitSet<String>, IOException> getCollect(Iter<Path> sources) {
        return this.getCollected(sources)
                .iter()
                .map(PathSources::getTuple2IOResult)
                .map(IOResult::result)
                .collect(new ResultCollector<>(new UnitSetCollector<>()));
    }

    private List<PathSource> getCollected(Iter<Path> sources) {
        return sources.filter(Files::isRegularFile)
                .filter((Path file) -> file.toString().endsWith(".java"))
                .map((Path path) -> new PathSource(this.root, path))
                .collect(new ListCollector<>());
    }
}