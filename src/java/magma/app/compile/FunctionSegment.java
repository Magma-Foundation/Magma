package magma.app.compile;

import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.app.compile.define.Definition;
import magma.app.compile.define.FunctionHeader;
import magma.app.io.Platform;

public record FunctionSegment<S extends FunctionHeader<S>>(
        FunctionHeader<S> header,
        List<Definition> definitions,
        Option<String> maybeContent
) {
    public String generate(final Platform platform, final String indent) {
        final String content = this.maybeContent()
                .map((String inner) -> " {" + inner + indent + "}")
                .orElse(";");

        return indent + this.header.generateWithDefinitions(platform, this.definitions()) + content;
    }
}