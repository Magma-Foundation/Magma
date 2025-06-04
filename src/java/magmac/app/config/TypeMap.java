package magmac.app.config;

import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.app.io.Location;

public record TypeMap(List<Tuple2<List<String>, String>> types) {
    public Option<Location> find(String name) {
        return this.types
                .iter()
                .filter(tuple -> tuple.right().equals(name))
                .next()
                .map(tuple -> new Location(tuple.left(), tuple.right()));
    }
}
