package magma.app.compile.value;

import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.app.compile.define.Definition;
import magma.app.compile.define.FunctionHeader;
import magma.app.io.Platform;

public class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
    private String generateWithAfterName(Platform platform, final String afterName) {
        return "constructor " + afterName;
    }

    @Override
    public boolean hasAnnotation(final String annotation) {
        return false;
    }

    @Override
    public ConstructorHeader removeModifier(final String modifier) {
        return this;
    }

    @Override
    public ConstructorHeader addModifierLast(final String modifier) {
        return this;
    }

    private String generateWithDefinitions0(Platform platform, String definitions) {
        return generateWithAfterName(platform, "(" + definitions + ")");
    }

    @Override
    public String generateWithDefinitions(final Platform platform, final List<Definition> definitions) {
        final var joinedDefinitions = definitions.query()
                .map((Definition definition) -> definition.generate(platform))
                .collect(new Joiner(", "))
                .orElse("");

        return this.generateWithDefinitions0(platform, joinedDefinitions);
    }
}
