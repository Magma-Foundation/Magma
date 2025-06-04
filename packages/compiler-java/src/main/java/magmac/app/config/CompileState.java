package magmac.app.config;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import magmac.app.io.Location;

import java.util.HashSet;
import java.util.Set;

/**
 * Keeps track of known type locations and the imports required for the
 * currently parsed file.
 */
public final class CompileState {
    private final List<Tuple2<List<String>, String>> types;
    private final Location location;
    private final Set<Location> imports = new HashSet<>();
    private final Set<String> values = new HashSet<>();

    public CompileState(List<Tuple2<List<String>, String>> types, Location location) {
        this.types = types;
        this.location = location;
    }

    public List<Tuple2<List<String>, String>> types() {
        return this.types;
    }

    public Location location() {
        return this.location;
    }

    /**
     * Finds where the given type is defined.
     */
    public Option<Location> find(String name) {
        return this.types
                .iter()
                .filter(tuple -> tuple.right().equals(name))
                .next()
                .map(tuple -> new Location(tuple.left(), tuple.right()));
    }

    /**
     * Registers that an import is required for the provided location.
     */
    public void registerImport(Location loc) {
        if (!loc.equals(this.location)) {
            this.imports.add(loc);
        }
    }

    /**
     * Registers that a value with the given name exists in the current scope.
     */
    public void registerValue(String name) {
        this.values.add(name);
    }

    /**
     * Returns whether a value with the given name has been registered.
     */
    public boolean hasValue(String name) {
        return this.values.contains(name);
    }

    /**
     * Returns all imports collected so far.
     */
    public Set<Location> imports() {
        return this.imports;
    }

    /**
     * Returns the collected imports as a persistent list.
     */
    public List<Location> importList() {
        return magmac.api.iter.Iters.fromValues(this.imports.toArray(new Location[0]))
                .collect(new ListCollector<>());
    }
}
