package magma.app.compile;

import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.app.io.Platform;

public record Import(List<String> namespace, String child) {
    public String generate(final Platform platform) {
        if (Platform.Magma == platform) {
            final String joinedNamespace = this.namespace.query()
                    .collect(new Joiner("."))
                    .orElse("");

            return "import " + joinedNamespace + "." + this.child + ";\n";
        }

        final String joinedNamespace = this.namespace
                .addLast(this.child)
                .query()
                .collect(new Joiner("/"))
                .orElse("");

        return "import { " + this.child + " } from \"" + joinedNamespace + "\";\n";
    }
}
