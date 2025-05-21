package magma.app.compile;

import jvm.api.collect.list.Lists;
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

    public Registry addDependency(Dependency dependency) {
        return new Registry(imports(), dependencies().addLast(dependency), output());
    }

    public Registry addImport(Import import_) {
        return new Registry(imports().addLast(import_), dependencies(), output());
    }

    Registry append(String element) {
        return new Registry(imports(), dependencies(), output() + element);
    }

    Registry clearImports() {
        return new Registry(Lists.empty(), dependencies(), output());
    }

    public boolean containsDependency(Dependency dependency) {
        return dependencies().contains(dependency);
    }
}