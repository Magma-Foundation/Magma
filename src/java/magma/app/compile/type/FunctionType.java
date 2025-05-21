package magma.app.compile.type;

import magma.api.Tuple2;
import magma.api.collect.Joiner;
import magma.api.collect.list.Iterable;

public record FunctionType(Iterable<String> args, String returns) implements Type {
    @Override
    public String generate() {
        var joinedArguments = this.args
                .iterWithIndices()
                .map((Tuple2<Integer, String> tuple) -> "arg" + tuple.left() + " : " + tuple.right())
                .collect(new Joiner(", "))
                .orElse("");

        return "(" + joinedArguments + ") => " + this.returns;
    }

    @Override
    public boolean isFunctional() {
        return true;
    }

    @Override
    public boolean isVar() {
        return false;
    }

    @Override
    public String generateBeforeName() {
        return "";
    }

    @Override
    public String generateSimple() {
        return this.generate();
    }
}
