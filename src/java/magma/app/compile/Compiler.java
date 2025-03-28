package magma.app.compile;

import jvm.api.collect.Lists;
import magma.api.collect.List_;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.rule.EmptyRule;
import magma.app.compile.rule.Rule;

public class Compiler {
    public static final List_<String> FUNCTIONAL_NAMESPACE = Lists.of("java", "util", "function");

    public static Result<Tuple<String, String>, CompileError> compile(String input) {
        Rule rule = createRootRule();
        return rule.parse(input)
                .flatMapValue(rule::generate)
                .mapValue(output -> new Tuple<>(output, output));
    }

    private static Rule createRootRule() {
        return new EmptyRule();
    }
}