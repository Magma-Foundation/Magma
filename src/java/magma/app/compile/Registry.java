package magma.app.compile;

import magma.api.collect.Iter;
import magma.api.collect.list.List;

public record Registry(List<Import> imports, List<Dependency> dependencies, String output) {
    public Iter<Dependency> iterDependencies() {
        return dependencies().iter();
    }

    public boolean doesImportExistAlready(String requestedChild) {
        return imports()
                .iter()
                .filter((Import node) -> node.hasSameChild(requestedChild))
                .next()
                .isPresent();
    }

    public Iter<Import> queryImports() {
        return imports().iter();
    }
}