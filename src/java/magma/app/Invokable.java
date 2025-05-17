package magma.app;

import magma.api.Type;
import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.Caller;
import magma.app.compile.CompileState;

record Invokable(Caller caller, List<Value> args) implements Value {
    @Override
    public String generate() {
        final var joinedArguments = this.joinArgs();
        return this.caller.generate() + "(" + joinedArguments + ")";
    }

    private String joinArgs() {
        return this.args.query()
                .map((Value value) -> value.generate())
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
        return Primitive.Unknown;
    }

    @Override
    public Option<String> generateAsEnumValue(final String structureName) {
        return new Some<String>("\n\tstatic " + this.caller.generate() + ": " + structureName + " = new " + structureName + "(" + this.joinArgs() + ");");
    }
}
