package magma.app.compile;

import magma.api.collect.Joiner;
import magma.api.collect.list.List;

public record Import(List<String> namespace, String child) {
    public String generate() {
        var joinedNamespace = this.namespace.query()
                .collect(new Joiner("/"))
                .orElse("");

        return "import { " + this.child + " } from \"" + joinedNamespace + "\";\n";
    }
}
