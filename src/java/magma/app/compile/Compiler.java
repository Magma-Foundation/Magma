package magma.app.compile;

import jvm.api.collect.Lists;
import magma.api.collect.Joiner;
import magma.api.collect.List_;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.divide.DivideRule;
import magma.app.compile.rule.EmptyRule;
import magma.app.compile.rule.OrRule;
import magma.app.compile.rule.Output;
import magma.app.compile.rule.PrefixRule;
import magma.app.compile.rule.Rule;
import magma.app.compile.rule.StripRule;
import magma.app.compile.rule.SuffixRule;

public class Compiler {
    public static final List_<String> FUNCTIONAL_NAMESPACE = Lists.of("java", "util", "function");

    public static Result<Tuple<String, String>, CompileError> compile(ParseState parseState, String input) {
        Rule rule = createRootRule();
        return rule.parse(parseState, input)
                .flatMapValue(rule::generate)
                .mapValue(Output::toTuple).mapValue(tuple -> {
                    return new Tuple<String, String>(wrapHeader(tuple, parseState), wrapTarget(parseState, tuple));
                });
    }

    private static String wrapHeader(Tuple<String, String> tuple, ParseState parseState) {
        String path = parseState.namespace().add(parseState.name())
                .stream()
                .collect(new Joiner("_"))
                .orElse("");

        return generateDirectiveWithValue("ifndef", path) +
                generateDirectiveWithValue("define", path) +
                tuple.left() +
                generateDirective("endif");
    }

    private static String generateDirective(String name) {
        return generateDirectiveWithSuffix(name, "");
    }

    private static String generateDirectiveWithValue(String name, String value) {
        return generateDirectiveWithSuffix(name, " " + value);
    }

    private static String generateDirectiveWithSuffix(String name, String suffix) {
        return "#" + name + suffix + "\n";
    }

    static String generateImport(String content) {
        return "#include \"" + content + ".h" + "\"\n";
    }

    private static DivideRule createRootRule() {
        return new DivideRule(createRootSegmentRule());
    }

    private static Rule createRootSegmentRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                createPackageRule(),
                createImportRule()
        ));
    }

    public static StripRule createWhitespaceRule() {
        return new StripRule(new EmptyRule());
    }

    private static StripRule createPackageRule() {
        return createNamespaceRule("package ", new MyRule());
    }

    public static Err<Tuple<String, String>, CompileError> createInfixErr(String input, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", new StringContext(input)));
    }

    private static StripRule createImportRule() {
        return createNamespaceRule("import ", new MyRule0());
    }

    private static StripRule createNamespaceRule(String prefix, Rule rule) {
        return new StripRule(new PrefixRule(prefix, new SuffixRule(rule, ";")));
    }

    public static Ok<Tuple<String, String>, CompileError> generateEmpty() {
        return new Ok<>(new Tuple<>("", ""));
    }

    static Result<Tuple<String, String>, CompileError> generateStruct(Node node) {
        String name = node.findString("name").orElse("");
        String left = node.findString("header").orElse("");
        String right = node.findString("target").orElse("");

        return new Ok<>(new Tuple<>("struct " + name + " {\n};\n" + left, right));
    }

    public static String wrapTarget(ParseState parseState, Tuple<String, String> tuple) {
        String name = parseState.name();
        String source = generateImport(name) + tuple.right();
        if (Lists.equalsTo(parseState.namespace(), Lists.of("magma"), String::equals) && name.equals("Main")) {
            return source + "int main(){\n\treturn 0;\n}\n";
        } else {
            return source;
        }
    }

    public static Rule createClassMemberRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule()
        ));
    }
}