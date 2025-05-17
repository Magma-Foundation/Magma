package magma.app.compile.type;

import magma.api.Tuple2;
import magma.api.Type;
import magma.api.collect.Joiner;
import magma.api.collect.list.List;

public record FunctionType(List<String> args, String returns) implements Type {
    @Override
    public String generate() {
        final var joinedArguments = this.args
                .queryWithIndices()
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
}
