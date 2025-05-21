package magma.app.compile;

import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.text.Strings;

public record Import(List<String> namespace, String child) {
    public String generate() {
        var joinedNamespace = this.namespace.query()
                .collect(new Joiner("/"))
                .orElse("");

        return "import { " + this.child + " } from \"" + joinedNamespace + "\";\n";
    }

    boolean hasSameChild(String child) {
        return Strings.equalsTo(this.child, child);
    }
}
