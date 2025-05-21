package magma.app.compile;

import jvm.api.collect.list.Lists;
import magma.api.collect.Iter;
import magma.api.collect.list.List;

public record Registry(List<Import> imports, List<Dependency> dependencies, String output) {
    public Iter<Dependency> iterDependencies() {
        return this.dependencies().iter();
    }

    public boolean doesImportExistAlready(String requestedChild) {
        return this.imports()
                .iter()
                .filter((Import node) -> node.hasSameChild(requestedChild))
                .next()
                .isPresent();
    }

    public Iter<Import> queryImports() {
        return this.imports().iter();
    }

    public Registry addDependency(Dependency dependency) {
        return new Registry(this.imports(), this.dependencies().addLast(dependency), this.output());
    }

    public Registry addImport(Import import_) {
        return new Registry(this.imports().addLast(import_), this.dependencies(), this.output());
    }

    public Registry append(String element) {
        return new Registry(this.imports(), this.dependencies(), this.output() + element);
    }

    public   Registry clearImports() {
        return new Registry(Lists.empty(), this.dependencies(), this.output());
    }

    public boolean containsDependency(Dependency dependency) {
        return this.dependencies().contains(dependency);
    }

    public Registry clearOutput() {
        return new Registry(this.imports, this.dependencies, "");
    }
}