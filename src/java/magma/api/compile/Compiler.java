package magma.api.compile;

import jvm.api.collect.Lists;
import magma.api.collect.Joiner;
import magma.api.collect.List_;
import magma.api.compile.divide.DivideRule;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.ParseState;

public class Compiler {

    public static final List_<String> FUNCTIONAL_NAMESPACE = Lists.of("java", "util", "function");
    public static final String NAME = "name";
    public static final String HEADER = "header";
    public static final String TARGET = "target";

    public static Result<Tuple<String, String>, CompileError> compile(ParseState parseState, String input) {
        return createRootRule().apply(parseState, input).mapValue(tuple -> {
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
        return new DivideRule((parseState1, input1) -> compileRootSegment(input1, parseState1));
    }

    static Result<Tuple<String, String>, CompileError> compileRootSegment(String input, ParseState state) {
        Result<Tuple<String, String>, CompileError> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isOk()) return maybeWhitespace;

        Result<Tuple<String, String>, CompileError> maybePackage = compilePackage(input);
        if (maybePackage.isOk()) return maybePackage;

        Result<Tuple<String, String>, CompileError> maybeImport = compileImport(state, input);
        if (maybeImport.isOk()) return maybeImport;

        Result<Tuple<String, String>, CompileError> maybeClass = compileClass(state, input);
        if (maybeClass.isOk()) return maybeClass;

        Result<Tuple<String, String>, CompileError> maybeRecord = compileRecord(input, state);
        if (maybeRecord.isOk()) return maybeRecord;

        Result<Tuple<String, String>, CompileError> maybeInterface = compileInterface(input);
        if (maybeInterface.isOk()) return maybeInterface;

        return new Err<>(new CompileError("Invalid root segment", input));
    }

    private static Result<Tuple<String, String>, CompileError> compileWhitespace(String input) {
        String stripped = input.strip();
        if (stripped.isEmpty()) return generateEmpty();
        return new Err<>(new CompileError("Input not empty", stripped));
    }

    private static Result<Tuple<String, String>, CompileError> compilePackage(String input) {
        if (input.startsWith("package ")) return generateEmpty();
        return createPrefixErr(input, "package ");
    }

    private static Err<Tuple<String, String>, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
    }

    private static Result<Tuple<String, String>, CompileError> compileClass(ParseState state, String input) {
        int classIndex = input.indexOf("class ");
        if (classIndex < 0) return createInfixErr(input, "class ");

        String right = input.substring(classIndex + "class ".length());
        int contentStart = right.indexOf("{");
        if (contentStart < 0) return createInfixErr(input, "{");

        String name = right.substring(0, contentStart).strip();
        String withEnd = right.substring(contentStart + "{".length()).strip();

        int implementsIndex = name.indexOf(" implements ");
        String name1 = implementsIndex >= 0
                ? name.substring(0, implementsIndex).strip()
                : name;

        if (name1.endsWith(">")) {
            return generateEmpty();
        }

        if (!withEnd.endsWith("}")) return createSuffixErr(input, "}");

        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        return new DivideRule((parseState2, s) -> compileClassMember(s)).apply(state, inputContent)
                .flatMapValue(tuple -> generateStruct(new MapNode()
                        .withString(NAME, name1)
                        .withString(HEADER, tuple.left())
                        .withString(TARGET, tuple.right())));

    }

    private static Err<Tuple<String, String>, CompileError> createSuffixErr(String input, String suffix) {
        return new Err<>(new CompileError("Suffix '" + suffix + "' not present", input));
    }

    private static Err<Tuple<String, String>, CompileError> createInfixErr(String input, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", input));
    }

    private static Result<Tuple<String, String>, CompileError> compileRecord(String input, ParseState state) {
        int recordIndex = input.indexOf("record ");
        if (recordIndex < 0) return createInfixErr(input, "record ");

        String right = input.substring(recordIndex + "record ".length());
        int contentStart = right.indexOf("{");
        if (contentStart < 0) return createInfixErr(right, "{");

        String beforeContent = right.substring(0, contentStart).strip();
        String withEnd = right.substring(contentStart + "{".length()).strip();

        int paramStart = beforeContent.indexOf("(");
        if (paramStart < 0) return createInfixErr(input, "(");

        String maybeWithTypeParams = beforeContent.substring(0, paramStart).strip();
        int implementsIndex = maybeWithTypeParams.indexOf(" implements ");
        String name = implementsIndex >= 0
                ? maybeWithTypeParams.substring(0, implementsIndex).strip()
                : maybeWithTypeParams;

        if (name.endsWith(">")) return generateEmpty();

        if (!withEnd.endsWith("}")) return createSuffixErr(input, "}");
        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        return new DivideRule((parseState1, s) -> compileClassMember(s)).apply(state, inputContent).flatMapValue(content -> {
            return generateStruct(new MapNode()
                    .withString(NAME, name)
                    .merge(new MapNode()
                            .withString(HEADER, content.left())
                            .withString(TARGET, content.right())));
        });
    }

    private static Result<Tuple<String, String>, CompileError> compileInterface(String input) {
        int interfaceIndex = input.indexOf("interface ");
        if (interfaceIndex < 0) return createInfixErr(input, "interface ");

        String right = input.substring(interfaceIndex + "interface ".length());
        int contentStart = right.indexOf("{");
        if (contentStart < 0) return createInfixErr(right, "{");

        String beforeContent = right.substring(0, contentStart).strip();
        if (beforeContent.endsWith(">")) {
            return generateEmpty();
        }

        String name;
        int extendsKeyword = beforeContent.indexOf(" extends ");
        if (extendsKeyword >= 0) {
            name = beforeContent.substring(0, extendsKeyword).strip();
        } else {
            name = beforeContent;
        }

        final Tuple<String, String> content = new Tuple<>("", "");
        MapNode other = new MapNode()
                .withString(HEADER, content.left())
                .withString(TARGET, content.right());

        return generateStruct(new MapNode()
                .withString(NAME, name)
                .merge(other));
    }

    private static Result<Tuple<String, String>, CompileError> compileImport(ParseState parseState, String input) {
        String stripped = input.strip();

        if (!stripped.startsWith("import ")) return createPrefixErr(input, "import ");

        String right = stripped.substring("import ".length());
        if (!right.endsWith(";")) return createSuffixErr(input, "import ");

        String left = right.substring(0, right.length() - ";".length());
        List_<String> namespace = Lists.empty();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < left.length(); i++) {
            char c = left.charAt(i);
            if (c == '.') {
                namespace = namespace.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                buffer.append(c);
            }
        }
        namespace = namespace.add(buffer.toString());

        if (isFunctionalImport(namespace)) {
            return generateEmpty();
        }

        List_<String> copy = Lists.empty();
        List_<String> thisNamespace = parseState.namespace();

        for (int i = 0; i < thisNamespace.size(); i++) {
            copy = copy.add("..");
        }

        if (namespace.isEmpty()) copy.add(".");

        List_<String> finalCopy = copy;
        List_<String> withNamespace = finalCopy.addAll(namespace);

        List_<String> mapped = namespace.popFirst()
                .map(tuple -> mapPlatformDependentNamespace(tuple.left(), tuple.right(), finalCopy).orElse(withNamespace))
                .orElse(withNamespace);

        String stringList = mapped
                .stream()
                .collect(new Joiner("/"))
                .orElse("");

        return new Ok<>(new Tuple<>(generateImport(stringList), ""));
    }

    private static Result<Tuple<String, String>, CompileError> compileClassMember(String input) {
        if (input.isBlank()) return generateEmpty();
        if (input.endsWith(";")) return new Ok<>(new Tuple<>("\tint value;\n", ""));

        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String definition = input.substring(0, paramStart).strip();
            int nameSeparator = definition.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                String oldName = definition.substring(nameSeparator + " ".length()).strip();
                String newName = oldName.equals("main") ? "__main__" : oldName;
                return new Ok<>(new Tuple<>("", "void " + newName + "(){\n}\n"));
            }
        }

        return new Err<>(new CompileError("Invalid class segment", input));
    }

    private static boolean isFunctionalImport(List_<String> namespace) {
        List_<String> slice = namespace.subList(0, 3).orElse(Lists.empty());
        return Lists.equalsTo(slice, FUNCTIONAL_NAMESPACE, String::equals);
    }

    private static Option<List_<String>> mapPlatformDependentNamespace(
            String first,
            List_<String> slice,
            List_<String> copy
    ) {
        return first.equals("jvm")
                ? new Some<>(copy.add("windows").addAll(slice))
                : new None<>();
    }

    static Ok<Tuple<String, String>, CompileError> generateEmpty() {
        return new Ok<>(new Tuple<>("", ""));
    }

    static Result<Tuple<String, String>, CompileError> generateStruct(MapNode node) {
        String name = node.find(NAME).orElse("");
        String left = node.find(HEADER).orElse("");
        String right = node.find(TARGET).orElse("");

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
}