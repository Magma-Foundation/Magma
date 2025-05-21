package magma.app.compile;

import jvm.api.collect.list.Lists;
import magma.api.collect.Iter;
import magma.api.collect.list.List;

public record ImmutableRegistry(List<Import> imports, List<Dependency> dependencies, String output) implements Registry {
    static Registry createEmpty() {
        return new ImmutableRegistry(Lists.empty(), Lists.empty(), "");
    }

    @Override
    public Iter<Dependency> iterDependencies() {
        return this.dependencies().iter();
    }

    @Override
    public boolean doesImportExistAlready(String requestedChild) {
        return this.imports()
                .iter()
                .filter((Import node) -> node.hasSameChild(requestedChild))
                .next()
                .isPresent();
    }

    @Override
    public Iter<Import> queryImports() {
        return this.imports().iter();
    }

    @Override
    public Registry addDependency(Dependency dependency) {
        return new ImmutableRegistry(this.imports(), this.dependencies().addLast(dependency), this.output());
    }

    @Override
    public Registry addImport(Import import_) {
        return new ImmutableRegistry(this.imports().addLast(import_), this.dependencies(), this.output());
    }

    @Override
    public Registry append(String element) {
        return new ImmutableRegistry(this.imports(), this.dependencies(), this.output() + element);
    }

    @Override
    public Registry clearImports() {
        return new ImmutableRegistry(Lists.empty(), this.dependencies(), this.output());
    }

    @Override
    public boolean containsDependency(Dependency dependency) {
        return this.dependencies().contains(dependency);
    }

    @Override
    public Registry clearOutput() {
        return new ImmutableRegistry(this.imports, this.dependencies, "");
    }
}