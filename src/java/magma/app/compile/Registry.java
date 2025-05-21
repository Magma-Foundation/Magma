package magma.app.compile;

import magma.api.collect.Iter;

public interface Registry {
    Iter<Dependency> iterDependencies();

    boolean doesImportExistAlready(String requestedChild);

    Iter<Import> queryImports();

    Registry addDependency(Dependency dependency);

    Registry addImport(Import import_);

    Registry append(String element);

    boolean containsDependency(Dependency dependency);

    String output();

    Registry reset();
}
