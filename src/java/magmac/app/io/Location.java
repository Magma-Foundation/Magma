package magmac.app.io;

import magmac.api.collect.list.List;
import magmac.api.iter.collect.Joiner;

public record Location(List<String> namespace, String name) {
    @Override
    public String toString() {
        String joined = this.namespace.iter().collect(new Joiner(".")).orElse("");
        return joined + "." + this.name;
    }
}
