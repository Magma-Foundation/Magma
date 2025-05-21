package magma.app.compile;

import magma.api.collect.Joiner;
import magma.api.collect.list.Iterable;
import magma.api.text.Strings;

public record Import(Iterable<String> namespace, String child) {
    public String generate() {
        var joinedNamespace = this.namespace.iter()
                .collect(new Joiner("/"))
                .orElse("");

        return "import { " + this.child + " } from \"" + joinedNamespace + "\";\n";
    }

    boolean hasSameChild(String child) {
        return Strings.equalsTo(this.child, child);
    }
}
