package magma.app.compile.value;

import magma.api.Type;
import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.CompileState;
import magma.app.compile.type.PrimitiveType;
import magma.app.io.Platform;

public record InvokableNode(Caller caller, List<Value> args) implements Value {
    @Override
    public String generate(final Platform platform) {
        final String joinedArguments = this.joinArgs(platform);
        return this.caller.generate(platform) + "(" + joinedArguments + ")";
    }

    private String joinArgs(final Platform platform) {
        return this.args.query()
                .map((Value value) -> value.generate(platform))
                .collect(new Joiner(", "))
                .orElse("");
    }

    @Override
    public Option<Value> toValue() {
        return new Some<Value>(this);
    }

    @Override
    public Option<Value> findChild() {
        return new None<Value>();
    }

    public Type resolve(final CompileState state) {
        return PrimitiveType.Unknown;
    }

    @Override
    public Option<String> generateAsEnumValue(final String structureName, Platform platform) {
        return new Some<String>("\n\tstatic " + this.caller.generate(platform) + ": " + structureName + " = new " + structureName + "(" + this.joinArgs(platform) + ");");
    }
}
