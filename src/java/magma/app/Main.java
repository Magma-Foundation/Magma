package magma.app;

import jvm.api.collect.list.Lists;
import jvm.api.io.Console;
import jvm.api.io.Files;
import jvm.api.text.Characters;
import jvm.api.text.Strings;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Collector;
import magma.api.collect.Queries;
import magma.api.collect.head.HeadedQuery;
import magma.api.collect.head.RangeHead;
import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.io.IOError;
import magma.api.io.Path;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class Main {
    private interface MethodHeader {
        String generateWithAfterName(String afterName);

        boolean hasAnnotation(String annotation);

        MethodHeader removeModifier(String modifier);
    }

    private interface Parameter {
        String generate();

        Option<Definition> asDefinition();
    }

    private interface Value extends Argument, Caller {
        Type resolve(CompileState state);

        Option<String> generateAsEnumValue(String structureName);
    }

    private interface Argument {
        Option<Value> toValue();
    }

    private interface Caller {
        String generate();

        Option<Value> findChild();
    }

    private interface Type {
        String generate();

        boolean isFunctional();

        boolean isVar();

        String generateBeforeName();
    }

    private record DivideState(List<String> segments, String buffer, int depth, String input, int index) {
        static DivideState createInitial(String input) {
            return new DivideState(Lists.empty(), "", 0, input, 0);
        }

        private DivideState advance() {
            return new DivideState(this.segments.addLast(this.buffer), "", this.depth, this.input, this.index);
        }

        private DivideState append(char c) {
            return new DivideState(this.segments, this.buffer + c, this.depth, this.input, this.index);
        }

        boolean isLevel() {
            return 0 == this.depth;
        }

        DivideState enter() {
            return new DivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index);
        }

        DivideState exit() {
            return new DivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index);
        }

        boolean isShallow() {
            return 1 == this.depth;
        }

        Option<Tuple2<DivideState, Character>> pop() {
            if (this.index >= Strings.length(this.input)) {
                return new None<Tuple2<DivideState, Character>>();
            }

            var c = Strings.charAt(this.input, this.index);
            var nextState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
            return new Some<Tuple2<DivideState, Character>>(new Tuple2Impl<DivideState, Character>(nextState, c));
        }

        Option<Tuple2<DivideState, Character>> popAndAppendToTuple() {
            return this.pop().map((Tuple2<DivideState, Character> inner) -> new Tuple2Impl<DivideState, Character>(inner.left().append(inner.right()), inner.right()));
        }

        Option<DivideState> popAndAppendToOption() {
            return this.popAndAppendToTuple().map((Tuple2<DivideState, Character> tuple) -> tuple.left());
        }

        char peek() {
            return Strings.charAt(this.input, this.index);
        }

        boolean startsWith(String slice) {
            return Strings.sliceFrom(this.input, this.index).startsWith(slice);
        }
    }

    private record CompileState(
            List<Import> imports,
            String output,
            List<String> structureNames,
            int depth,
            List<Definition> definitions,
            Option<Location> maybeLocation,
            List<Source> sources,
            Platform platform
    ) {
        private static CompileState createInitial() {
            return new CompileState(Lists.empty(), "", Lists.empty(), 0, Lists.empty(), new None<Location>(), Lists.empty(), Platform.Magma);
        }

        private boolean isLastWithin(String name) {
            return this.structureNames.findLast()
                    .filter((String anObject) -> Strings.equalsTo(name, anObject))
                    .isPresent();
        }

        private CompileState addResolvedImport(List<String> parent, String child) {
            var namespace = this.maybeLocation
                    .map((Location location) -> location.namespace)
                    .orElse(Lists.empty());

            var parent1 = parent;
            if (namespace.isEmpty()) {
                parent1 = parent1.addFirst(".");
            }

            var i = 0;
            var size = namespace.size();
            while (i < size) {
                parent1 = parent1.addFirst("..");
                i++;
            }

            var stringList = parent1.addLast(child);
            if (this.imports
                    .query()
                    .filter((Import node) -> Strings.equalsTo(node.child, child))
                    .next()
                    .isPresent()) {
                return this;
            }

            var importString = new Import(stringList, child);
            return new CompileState(this.imports.addLast(importString), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
        }

        private CompileState withLocation(Location namespace) {
            return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, new Some<Location>(namespace), this.sources, this.platform);
        }

        CompileState append(String element) {
            return new CompileState(this.imports, this.output + element, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
        }

        CompileState pushStructureName(String name) {
            return new CompileState(this.imports, this.output, this.structureNames.addLast(name), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
        }

        CompileState enterDepth() {
            return new CompileState(this.imports, this.output, this.structureNames, this.depth + 1, this.definitions, this.maybeLocation, this.sources, this.platform);
        }

        CompileState exitDepth() {
            return new CompileState(this.imports, this.output, this.structureNames, this.depth - 1, this.definitions, this.maybeLocation, this.sources, this.platform);
        }

        CompileState defineAll(List<Definition> definitions) {
            return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions.addAll(definitions), this.maybeLocation, this.sources, this.platform);
        }

        Option<Type> resolve(String name) {
            return this.definitions.queryReversed()
                    .filter((Definition definition) -> Strings.equalsTo(definition.name, name))
                    .map((Definition definition1) -> definition1.type)
                    .next();
        }

        CompileState clearImports() {
            return new CompileState(Lists.empty(), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
        }

        CompileState clearOutput() {
            return new CompileState(this.imports, "", this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
        }

        CompileState addSource(Source source) {
            return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources.addLast(source), this.platform);
        }

        Option<Source> findSource(String name) {
            return this.sources.query()
                    .filter((Source source) -> Strings.equalsTo(source.computeName(), name))
                    .next();
        }

        CompileState addResolvedImportFromCache(String base) {
            if (this.structureNames.query().anyMatch((String inner) -> Strings.equalsTo(inner, base))) {
                return this;
            }

            return this.findSource(base)
                    .map((Source source) -> this.addResolvedImport(source.computeNamespace(), source.computeName()))
                    .orElse(this);
        }

        CompileState popStructureName() {
            return new CompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
        }

        public CompileState mapLocation(Function<Location, Location> mapper) {
            return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation.map(mapper), this.sources, this.platform);
        }

        public CompileState withPlatform(Platform platform) {
            return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, platform);
        }
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        private static Joiner empty() {
            return new Joiner("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<String>();
        }

        @Override
        public Option<String> fold(Option<String> maybe, String element) {
            return new Some<String>(maybe.map((String inner) -> inner + this.delimiter + element).orElse(element));
        }
    }

    private record Definition(
            List<String> annotations,
            List<String> modifiers,
            List<String> typeParams,
            Type type,
            String name
    ) implements MethodHeader, Parameter {
        @Override
        public String generate() {
            return this.generateWithAfterName("");
        }

        @Override
        public Option<Definition> asDefinition() {
            return new Some<Definition>(this);
        }

        @Override
        public String generateWithAfterName(String afterName) {
            var joinedTypeParams = this.joinTypeParams();
            var joinedModifiers = this.modifiers.query()
                    .map((String value) -> value + " ")
                    .collect(new Joiner(""))
                    .orElse("");

            return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType();
        }

        private String generateType() {
            if (this.type.isVar()) {
                return "";
            }

            return ": " + this.type.generate();
        }

        private String joinTypeParams() {
            return Main.joinTypeParams(this.typeParams);
        }

        @Override
        public boolean hasAnnotation(String annotation) {
            return this.annotations.contains(annotation, Strings::equalsTo);
        }

        @Override
        public MethodHeader removeModifier(String modifier) {
            return new Definition(this.annotations, this.modifiers.removeValue(modifier, Strings::equalsTo), this.typeParams, this.type, this.name);
        }
    }

    private static class ConstructorHeader implements MethodHeader {
        @Override
        public String generateWithAfterName(String afterName) {
            return "constructor " + afterName;
        }

        @Override
        public boolean hasAnnotation(String annotation) {
            return false;
        }

        @Override
        public MethodHeader removeModifier(String modifier) {
            return this;
        }
    }

    private record Placeholder(String input) implements Parameter, Value, Type {
        @Override
        public String generate() {
            return Main.generatePlaceholder(this.input);
        }

        @Override
        public boolean isFunctional() {
            return false;
        }

        @Override
        public Option<Value> findChild() {
            return new None<Value>();
        }

        @Override
        public Option<Definition> asDefinition() {
            return new None<Definition>();
        }

        @Override
        public Option<Value> toValue() {
            return new None<Value>();
        }

        @Override
        public Type resolve(CompileState state) {
            return Primitive.Unknown;
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
        public Option<String> generateAsEnumValue(String structureName) {
            return new None<String>();
        }
    }

    private record Whitespace() implements Parameter {
        @Override
        public String generate() {
            return "";
        }

        @Override
        public Option<Definition> asDefinition() {
            return new None<Definition>();
        }
    }

    private record Access(Value child, String property) implements Value {
        @Override
        public String generate() {
            return this.child.generate() + "." + this.property;
        }

        @Override
        public Option<Value> toValue() {
            return new Some<Value>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new Some<Value>(this.child);
        }

        @Override
        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }

        @Override
        public Option<String> generateAsEnumValue(String structureName) {
            return new None<String>();
        }
    }

    private record SymbolNode(String value) implements Value, Type {
        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public Type resolve(CompileState state) {
            return state.resolve(this.value).orElse(Primitive.Unknown);
        }

        @Override
        public Option<Value> toValue() {
            return new Some<Value>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<Value>();
        }

        @Override
        public boolean isFunctional() {
            return false;
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
        public Option<String> generateAsEnumValue(String structureName) {
            return new None<String>();
        }
    }

    private record StringValue(String value) implements Value {
        @Override
        public String generate() {
            return "\"" + this.value + "\"";
        }

        @Override
        public Option<Value> toValue() {
            return new Some<Value>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<Value>();
        }

        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }

        @Override
        public Option<String> generateAsEnumValue(String structureName) {
            return new None<String>();
        }
    }

    private record Not(String child) implements Value {
        @Override
        public String generate() {
            return this.child;
        }

        @Override
        public Option<Value> toValue() {
            return new Some<Value>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<Value>();
        }

        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }

        @Override
        public Option<String> generateAsEnumValue(String structureName) {
            return new None<String>();
        }
    }

    private record Lambda(List<Definition> paramNames, String content) implements Value {
        @Override
        public String generate() {
            var joinedParamNames = this.paramNames.query()
                    .map((Definition definition) -> definition.generate())
                    .collect(new Joiner(", "))
                    .orElse("");

            return "(" + joinedParamNames + ")" + " => " + this.content;
        }

        @Override
        public Option<Value> toValue() {
            return new Some<Value>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<Value>();
        }

        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }

        @Override
        public Option<String> generateAsEnumValue(String structureName) {
            return new None<String>();
        }
    }

    private record Invokable(Caller caller, List<Value> args) implements Value {
        @Override
        public String generate() {
            var joinedArguments = this.joinArgs();
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

        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }

        @Override
        public Option<String> generateAsEnumValue(String structureName) {
            return new Some<String>("\n\tstatic " + this.caller.generate() + ": " + structureName + " = new " + structureName + "(" + this.joinArgs() + ");");
        }
    }

    private record Operation(Value left, String targetInfix, Value right) implements Value {
        @Override
        public String generate() {
            return this.left.generate() + " " + this.targetInfix + " " + this.right.generate();
        }

        @Override
        public Option<Value> toValue() {
            return new Some<Value>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<Value>();
        }

        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }

        @Override
        public Option<String> generateAsEnumValue(String structureName) {
            return new None<String>();
        }
    }

    private record ConstructionCaller(String right) implements Caller {
        @Override
        public String generate() {
            return "new " + this.right;
        }

        @Override
        public Option<Value> findChild() {
            return new None<Value>();
        }
    }

    private record FunctionType(List<String> args, String returns) implements Type {
        @Override
        public String generate() {
            var joinedArguments = this.args
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

    private record Generic(String base, List<String> args) implements Type {
        @Override
        public String generate() {
            return this.base + "<" + Main.generateValueStrings(this.args) + ">";
        }

        @Override
        public boolean isFunctional() {
            return false;
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

    private record VarArgs(Type type) implements Type {
        @Override
        public String generate() {
            return this.type.generate() + "[]";
        }

        @Override
        public boolean isFunctional() {
            return false;
        }

        @Override
        public boolean isVar() {
            return false;
        }

        @Override
        public String generateBeforeName() {
            return "...";
        }
    }

    private record Import(List<String> namespace, String child) {
        private String generate() {
            var joinedNamespace = this.namespace.query()
                    .collect(new Joiner("/"))
                    .orElse("");

            return "import { " + this.child + " } from \"" + joinedNamespace + "\";\n";
        }
    }

    private record Source(Path sourceDirectory, Path source) {
        private Result<String, IOError> read() {
            return this.source.readString();
        }

        private String computeName() {
            var fileName = this.source.findFileName();
            var separator = fileName.lastIndexOf('.');
            return fileName.substring(0, separator);
        }

        private List<String> computeNamespace() {
            return this.sourceDirectory.relativize(this.source)
                    .getParent()
                    .query()
                    .collect(new ListCollector<String>());
        }

        Location computeLocation() {
            return new Location(this.computeNamespace(), this.computeName());
        }
    }

    private record Location(List<String> namespace, String name) {
    }

    private record ArrayType(Type child) implements Type {
        @Override
        public String generate() {
            return this.child.generate() + "[]";
        }

        @Override
        public boolean isFunctional() {
            return false;
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

    public static void main() {
        var sourceDirectory = Files.get(".", "src", "java");
        sourceDirectory.walk()
                .match(
                        (List<Path> children) -> Main.runWithChildren(children, sourceDirectory),
                        (IOError value) -> new Some<IOError>(value))
                .map((IOError error) -> error.display())
                .ifPresent((String displayed) -> Console.printErrLn(displayed));
    }

    private static Option<IOError> runWithChildren(List<Path> children, Path sourceDirectory) {
        var sources = children.query()
                .filter((Path source) -> source.endsWith(".java"))
                .map((Path child) -> new Source(sourceDirectory, child))
                .collect(new ListCollector<Source>());

        var initial = sources.query().foldWithInitial(CompileState.createInitial(), (CompileState state, Source source) -> state.addSource(source));

        return sources.query()
                .foldWithInitial(Main.createInitialState(initial), (Tuple2<CompileState, Option<IOError>> current, Source source1) -> Main.foldChild(current.left(), current.right(), source1))
                .right();
    }

    private static Tuple2<CompileState, Option<IOError>> createInitialState(CompileState state) {
        return new Tuple2Impl<CompileState, Option<IOError>>(state, new None<IOError>());
    }

    private static Tuple2<CompileState, Option<IOError>> foldChild(CompileState state, Option<IOError> maybeError, Source source) {
        if (maybeError.isPresent()) {
            return new Tuple2Impl<CompileState, Option<IOError>>(state, maybeError);
        }

        return Main.runWithSource(state, source);
    }

    private static Tuple2<CompileState, Option<IOError>> runWithSource(CompileState state, Source source) {
        return source.read().match(
                (String input) -> Main.getCompileStateOptionTuple2(state, source, input),
                (IOError value) -> new Tuple2Impl<CompileState, Option<IOError>>(state, new Some<IOError>(value)));
    }

    private static Tuple2Impl<CompileState, Option<IOError>> getCompileStateOptionTuple2(CompileState state, Source source, String input) {
        var result = Main.compileAndWrite(state, source, input, Platform.TypeScript);
        var compileStateOptionTuple2 = Main.compileAndWrite(result.left(), source, input, Platform.Magma);
        return new Tuple2Impl<>(compileStateOptionTuple2.left(), result.right().or(() -> compileStateOptionTuple2.right()));
    }

    private static Tuple2<CompileState, Option<IOError>> compileAndWrite(CompileState state, Source source, String input, Platform platform) {
        var state1 = state.withLocation(source.computeLocation()).withPlatform(platform);
        var output = Main.compileRoot(state1, source, input);

        var location = output.left().maybeLocation.orElse(new Location(Lists.empty(), ""));
        var targetDirectory = Files.get(".", "src", platform.root);
        var targetParent = targetDirectory.resolveChildSegments(location.namespace);
        if (!targetParent.exists()) {
            var maybeError = targetParent.createDirectories();
            if (maybeError.isPresent()) {
                return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), maybeError);
            }
        }

        var target = targetParent.resolveChild(location.name + "." + platform.extension);
        return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), target.writeString(output.right()));
    }

    private static Tuple2Impl<CompileState, String> compileRoot(CompileState state, Source source, String input) {
        var statementsTuple = Main.compileStatements(state, input, Main::compileRootSegment);
        var statementsState = statementsTuple.left();
        var imports = statementsState.imports.query()
                .map((Import anImport) -> anImport.generate())
                .collect(new Joiner(""))
                .orElse("");

        var compileState = statementsState.clearImports().clearOutput();
        var segment = compileState.sources
                .query()
                .map((Source source1) -> Main.formatSource(source1))
                .collect(new Joiner(", "))
                .orElse("");

        var withMain = Main.createMain(source);
        var output = "/*[" + segment + "\n]*/\n" + imports + statementsState.output + statementsTuple.right() + withMain;
        return new Tuple2Impl<CompileState, String>(compileState, output);
    }

    private static String createMain(Source source) {
        if (Strings.equalsTo(source.computeName(), "Main")) {
            return "Main.main();";
        }
        return "";
    }

    private static String formatSource(Source source) {
        var joinedNamespace = source.computeNamespace().query().collect(new Joiner(".")).orElse("");
        return "\n\t" + source.computeName() + ": " + joinedNamespace;
    }

    private static Tuple2<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper) {
        return Main.compileAll(state, input, Main::foldStatements, mapper, Main::mergeStatements);
    }

    private static Tuple2<CompileState, String> compileAll(CompileState state, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper, BiFunction<String, String, String> merger) {
        var folded = Main.parseAll(state, input, folder, (CompileState state1, String s) -> new Some<Tuple2<CompileState, String>>(mapper.apply(state1, s))).orElse(new Tuple2Impl<CompileState, List<String>>(state, Lists.empty()));
        return new Tuple2Impl<CompileState, String>(folded.left(), Main.generateAll(folded.right(), merger));
    }

    private static String generateAll(List<String> elements, BiFunction<String, String, String> merger) {
        return elements.query()
                .foldWithInitial("", merger);
    }

    private static <T> Option<Tuple2<CompileState, List<T>>> parseAll(CompileState state, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> biFunction) {
        return Main.divide(input, folder).query().foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (Option<Tuple2<CompileState, List<T>>> maybeCurrent, String segment) -> {
            return maybeCurrent.flatMap((Tuple2<CompileState, List<T>> current) -> {
                var currentState = current.left();
                var currentElement = current.right();

                return biFunction.apply(currentState, segment).map((Tuple2<CompileState, T> mappedTuple) -> {
                    var mappedState = mappedTuple.left();
                    var mappedElement = mappedTuple.right();
                    return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
                });
            });
        });
    }

    private static String mergeStatements(String cache, String element) {
        return cache + element;
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = DivideState.createInitial(input);

        while (true) {
            var poppedTuple0 = current.pop().toTuple(new Tuple2Impl<DivideState, Character>(current, '\0'));
            if (!poppedTuple0.left()) {
                break;
            }

            var poppedTuple = poppedTuple0.right();
            var poppedState = poppedTuple.left();
            var popped = poppedTuple.right();

            current = Main.foldSingleQuotes(poppedState, popped)
                    .or(() -> Main.foldDoubleQuotes(poppedState, popped))
                    .orElseGet(() -> folder.apply(poppedState, popped));
        }

        return current.advance().segments;
    }

    private static Option<DivideState> foldDoubleQuotes(DivideState state, char c) {
        if ('\"' != c) {
            return new None<DivideState>();
        }

        var appended = state.append(c);
        while (true) {
            var maybeTuple = appended.popAndAppendToTuple()
                    .toTuple(new Tuple2Impl<DivideState, Character>(appended, '\0'));

            if (!maybeTuple.left()) {
                break;
            }

            var tuple = maybeTuple.right();
            appended = tuple.left();

            if ('\\' == tuple.right()) {
                appended = appended.popAndAppendToOption().orElse(appended);
            }
            if ('\"' == tuple.right()) {
                break;
            }
        }
        return new Some<DivideState>(appended);
    }

    private static Option<DivideState> foldSingleQuotes(DivideState state, char c) {
        if ('\'' != c) {
            return new None<DivideState>();
        }

        return state.append(c)
                .popAndAppendToTuple()
                .flatMap(Main::foldEscaped)
                .flatMap((DivideState state1) -> state1.popAndAppendToOption());
    }

    private static Option<DivideState> foldEscaped(Tuple2<DivideState, Character> tuple) {
        var state = tuple.left();
        var c = tuple.right();

        if ('\\' == c) {
            return state.popAndAppendToOption();
        }

        return new Some<DivideState>(state);
    }

    private static DivideState foldStatements(DivideState state, char c) {
        var appended = state.append(c);
        if (';' == c && appended.isLevel()) {
            return appended.advance();
        }

        if ('}' == c && appended.isShallow()) {
            return appended.advance().exit();
        }

        if ('{' == c || '(' == c) {
            return appended.enter();
        }

        if ('}' == c || ')' == c) {
            return appended.exit();
        }

        return appended;
    }

    private static Tuple2<CompileState, String> compileRootSegment(CompileState state, String input) {
        return Main.compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileNamespaced,
                Main.createStructureRule("class ", "class "),
                Main.createStructureRule("interface ", "interface "),
                Main.createStructureRule("record ", "class "),
                Main.createStructureRule("enum ", "class ")
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureRule(String sourceInfix, String targetInfix) {
        return (CompileState state, String input1) -> {
            return Main.compileFirst(input1, sourceInfix, (String beforeInfix, String afterInfix) -> {
                return Main.compileFirst(afterInfix, "{", (String beforeContent, String withEnd) -> {
                    return Main.compileSuffix(Strings.strip(withEnd), "}", (String inputContent) -> {
                        return Main.compileLast(beforeInfix, "\n", (String s, String s2) -> {
                            var annotations = Main.parseAnnotations(s);
                            if (annotations.contains("Actual", Strings::equalsTo)) {
                                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ""));
                            }

                            return Main.compileStructureWithImplementing(state, annotations, Main.parseModifiers(s2), targetInfix, beforeContent, inputContent);
                        }).or(() -> {
                            var modifiers = Main.parseModifiers(beforeContent);
                            return Main.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent);
                        });
                    });
                });
            });
        };
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithImplementing(CompileState state, List<String> annotations, List<String> modifiers, String targetInfix, String beforeContent, String content) {
        return Main.compileLast(beforeContent, " implements ", (String s, String s2) -> {
            return Main.parseType(state, s2).flatMap((Tuple2<CompileState, Type> implementingTuple) -> {
                return Main.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content);
            });
        }).or(() -> {
            return Main.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content);
        });
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithExtends(CompileState state, List<String> annotations, List<String> modifiers, String targetInfix, String beforeContent, Option<Type> maybeImplementing, String inputContent) {
        return Main.compileFirst(beforeContent, " extends ", (String beforeExtends, String afterExtends) -> Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeExtends, new Some<String>(afterExtends), maybeImplementing, inputContent)).or(() -> {
            return Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, new None<String>(), maybeImplementing, inputContent);
        });
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithParameters(CompileState state, List<String> annotations, List<String> modifiers, String targetInfix, String beforeContent, Option<String> maybeSuperType, Option<Type> maybeImplementing, String inputContent) {
        return Main.compileFirst(beforeContent, "(", (String rawName, String withParameters) -> {
            return Main.compileFirst(withParameters, ")", (String parametersString, String _) -> {
                var name = Strings.strip(rawName);

                var parametersTuple = Main.parseParameters(state, parametersString);
                var parameters = Main.retainDefinitionsFromParameters(parametersTuple.right());

                return Main.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType);
            });
        }).or(() -> {
            return Main.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType);
        });
    }

    private static List<Definition> retainDefinitionsFromParameters(List<Parameter> parameters) {
        return parameters.query()
                .map((Parameter parameter) -> parameter.asDefinition())
                .flatMap(Queries::fromOption)
                .collect(new ListCollector<Definition>());
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithTypeParams(CompileState state, String infix, String content, String beforeParams, List<Definition> parameters, Option<Type> maybeImplementing, List<String> annotations, List<String> modifiers, Option<String> maybeSuperType) {
        return Main.compileSuffix(Strings.strip(beforeParams), ">", (String withoutTypeParamEnd) -> {
            return Main.compileFirst(withoutTypeParamEnd, "<", (String name, String typeParamsString) -> {
                var typeParams = Main.divideValues(typeParamsString);
                return Main.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType);
            });
        }).or(() -> {
            return Main.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType);
        });
    }

    private static Option<Tuple2<CompileState, String>> assembleStructure(
            CompileState state,
            List<String> annotations,
            List<String> oldModifiers,
            String infix,
            String rawName,
            List<String> typeParams,
            List<Definition> parameters,
            Option<Type> maybeImplementing,
            String content,
            Option<String> maybeSuperType
    ) {
        var name = Strings.strip(rawName);
        if (!Main.isSymbol(name)) {
            return new None<Tuple2<CompileState, String>>();
        }

        var outputContentTuple = Main.compileStatements(state.pushStructureName(name), content, Main::compileClassSegment);
        var outputContentState = outputContentTuple.left().popStructureName();
        var outputContent = outputContentTuple.right();

        var constructorString = Main.generateConstructorFromRecordParameters(parameters);
        var joinedTypeParams = Main.joinTypeParams(typeParams);
        var implementingString = Main.generateImplementing(maybeImplementing);
        var newModifiers = Main.modifyModifiers0(oldModifiers);

        var joinedModifiers = newModifiers.query()
                .map((String value) -> value + " ")
                .collect(Joiner.empty())
                .orElse("");

        if (annotations.contains("Namespace", Strings::equalsTo)) {
            String actualInfix = "interface ";
            String newName = name + "Instance";

            var generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
            var withNewLocation = outputContentState.append(generated).mapLocation((Location location) -> new Location(location.namespace, location.name + "Instance"));

            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(withNewLocation, ""));
        }
        else {
            var extendsString = maybeSuperType.map((String inner) -> " extends " + inner).orElse("");
            var generated = joinedModifiers + infix + name + joinedTypeParams + extendsString + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(outputContentState.append(generated), ""));
        }
    }

    private static List<String> modifyModifiers0(List<String> oldModifiers) {
        if (oldModifiers.contains("public", Strings::equalsTo)) {
            return Lists.of("export");
        }
        return Lists.empty();
    }

    private static String generateImplementing(Option<Type> maybeImplementing) {
        return maybeImplementing.map((Type type) -> type.generate())
                .map((String inner) -> " implements " + inner)
                .orElse("");
    }

    private static String joinTypeParams(List<String> typeParams) {
        return typeParams.query()
                .collect(new Joiner(", "))
                .map((String inner) -> "<" + inner + ">")
                .orElse("");
    }

    private static String generateConstructorFromRecordParameters(List<Definition> parameters) {
        return parameters.query()
                .map((Definition definition) -> definition.generate())
                .collect(new Joiner(", "))
                .map((String generatedParameters) -> Main.generateConstructorWithParameterString(parameters, generatedParameters))
                .orElse("");
    }

    private static String generateConstructorWithParameterString(List<Definition> parameters, String parametersString) {
        var constructorAssignments = Main.generateConstructorAssignments(parameters);

        return "\n\tconstructor (" + parametersString + ") {" +
                constructorAssignments +
                "\n\t}";
    }

    private static String generateConstructorAssignments(List<Definition> parameters) {
        return parameters.query()
                .map((Definition definition) -> "\n\t\tthis." + definition.name + " = " + definition.name + ";")
                .collect(Joiner.empty())
                .orElse("");
    }

    private static String joinParameters(List<Definition> parameters) {
        return parameters.query()
                .map((Definition definition) -> definition.generate())
                .map((String generated) -> "\n\t" + generated + ";")
                .collect(Joiner.empty())
                .orElse("");
    }

    private static Option<Tuple2<CompileState, String>> compileNamespaced(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (stripped.startsWith("package ")) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ""));
        }

        return Main.compileImport(state, stripped);
    }

    private static Option<Tuple2<CompileState, String>> compileImport(CompileState state, String stripped) {
        return Main.compilePrefix(stripped, "import ", (String s) -> {
            return Main.compileSuffix(s, ";", (String s1) -> {
                var divisions = Main.divide(s1, (DivideState divideState, Character c) -> Main.foldDelimited(divideState, c, '.'));
                var child = Strings.strip(divisions.findLast().orElse(""));
                var parent = divisions.subList(0, divisions.size() - 1)
                        .orElse(Lists.empty());

                if (parent.equalsTo(Lists.of("java", "util", "function"), Strings::equalsTo)) {
                    return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ""));
                }

                var compileState = state.addResolvedImport(parent, child);
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ""));
            });
        });
    }

    private static Tuple2<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>>> rules
    ) {
        return Main.or(state, input, rules).orElseGet(() -> new Tuple2Impl<CompileState, String>(state, Main.generatePlaceholder(input)));
    }

    private static <T> Option<Tuple2<CompileState, T>> or(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>>> rules
    ) {
        return rules.query()
                .map((BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) -> Main.getApply(state, input, rule))
                .flatMap(Queries::fromOption)
                .next();
    }

    private static <T> Option<Tuple2<CompileState, T>> getApply(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) {
        return rule.apply(state, input);
    }

    private static Tuple2<CompileState, String> compileClassSegment(CompileState state1, String input1) {
        return Main.compileOrPlaceholder(state1, input1, Lists.of(
                Main::compileWhitespace,
                Main.createStructureRule("class ", "class "),
                Main.createStructureRule("interface ", "interface "),
                Main.createStructureRule("record ", "class "),
                Main.createStructureRule("enum ", "class "),
                Main::compileMethod,
                Main::compileFieldDefinition
        ));
    }

    private static Option<Tuple2<CompileState, String>> compileMethod(CompileState state, String input) {
        return Main.compileFirst(input, "(", (String beforeParams, String withParams) -> {
            var strippedBeforeParams = Strings.strip(beforeParams);
            return Main.compileLast(strippedBeforeParams, " ", (String _, String name) -> {
                if (state.isLastWithin(name)) {
                    return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return new None<Tuple2<CompileState, String>>();
            }).or(() -> {
                if (state.structureNames.findLast().filter((String anObject) -> Strings.equalsTo(strippedBeforeParams, anObject)).isPresent()) {
                    return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return new None<Tuple2<CompileState, String>>();
            }).or(() -> {
                return Main.parseDefinition(state, beforeParams)
                        .flatMap((Tuple2<CompileState, Definition> tuple) -> Main.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams));
            });
        });
    }

    private static Option<Tuple2<CompileState, String>> compileMethodWithBeforeParams(CompileState state, MethodHeader header, String withParams) {
        return Main.compileFirst(withParams, ")", (String params, String afterParams) -> {
            var parametersTuple = Main.parseParameters(state, params);

            var parametersState = parametersTuple.left();
            var parameters = parametersTuple.right();
            var definitions = Main.retainDefinitionsFromParameters(parameters);

            var joinedDefinitions = definitions.query()
                    .map((Definition definition) -> definition.generate())
                    .collect(new Joiner(", "))
                    .orElse("");

            if (header.hasAnnotation("Actual")) {
                var headerGenerated = header
                        .removeModifier("static")
                        .generateWithAfterName("(" + joinedDefinitions + ")");

                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(parametersState, "\n\t" + headerGenerated + ";\n"));
            }

            var headerGenerated = header.generateWithAfterName("(" + joinedDefinitions + ")");
            return Main.compilePrefix(Strings.strip(afterParams), "{", (String withoutContentStart) -> {
                return Main.compileSuffix(Strings.strip(withoutContentStart), "}", (String withoutContentEnd) -> {
                    var statementsTuple = Main.compileFunctionStatements(parametersState.enterDepth().enterDepth().defineAll(definitions), withoutContentEnd);

                    return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(statementsTuple.left().exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right() + "\n\t}"));
                });
            }).or(() -> {
                if (Strings.equalsTo(";", Strings.strip(afterParams))) {
                    return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(parametersState, "\n\t" + headerGenerated + ";"));
                }

                return new None<Tuple2<CompileState, String>>();
            });
        });
    }

    private static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, String params) {
        return Main.parseValuesOrEmpty(state, params, (CompileState state1, String s) -> new Some<Tuple2<CompileState, Parameter>>(Main.parseParameterOrPlaceholder(state1, s)));
    }

    private static Tuple2<CompileState, String> compileFunctionStatements(CompileState state, String input) {
        return Main.compileStatements(state, input, Main::compileFunctionSegment);
    }

    private static Tuple2<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        return Main.compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileEmptySegment,
                Main::compileBlock,
                Main::compileFunctionStatement,
                Main::compileReturnWithoutSuffix
        ));
    }

    private static Option<Tuple2<CompileState, String>> compileEmptySegment(CompileState state, String input) {
        if (Strings.equalsTo(";", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ";"));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    private static Option<Tuple2<CompileState, String>> compileReturnWithoutSuffix(CompileState state1, String input1) {
        return Main.compileReturn(input1, (String withoutPrefix) -> Main.compileValue(state1, withoutPrefix))
                .map((Tuple2<CompileState, String> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), Main.generateIndent(state1.depth) + tuple.right()));
    }

    private static Option<Tuple2<CompileState, String>> compileBlock(CompileState state, String input) {
        return Main.compileSuffix(Strings.strip(input), "}", (String withoutEnd) -> {
            return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main::foldBlockStarts), (String beforeContentWithEnd, String content) -> {
                return Main.compileSuffix(beforeContentWithEnd, "{", (String beforeContent) -> {
                    return Main.compileBlockHeader(state, beforeContent).flatMap((Tuple2<CompileState, String> headerTuple) -> {
                        var contentTuple = Main.compileFunctionStatements(headerTuple.left().enterDepth(), content);

                        var indent = Main.generateIndent(state.depth);
                        return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
                    });
                });
            });
        });
    }

    private static DivideState foldBlockStarts(DivideState state, char c) {
        var appended = state.append(c);
        if ('{' == c) {
            var entered = appended.enter();
            if (entered.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }

        if ('}' == c) {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple2<CompileState, String>> compileBlockHeader(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                Main.createConditionalRule("if"),
                Main.createConditionalRule("while"),
                Main::compileElse
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createConditionalRule(String prefix) {
        return (CompileState state1, String input1) -> Main.compilePrefix(Strings.strip(input1), prefix, (String withoutPrefix) -> {
            var strippedCondition = Strings.strip(withoutPrefix);
            return Main.compilePrefix(strippedCondition, "(", (String withoutConditionStart) -> {
                return Main.compileSuffix(withoutConditionStart, ")", (String withoutConditionEnd) -> {
                    var tuple = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
                    return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), prefix + " (" + tuple.right() + ")"));
                });
            });
        });
    }

    private static Option<Tuple2<CompileState, String>> compileElse(CompileState state, String input) {
        if (Strings.equalsTo("else", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, "else "));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    private static Option<Tuple2<CompileState, String>> compileFunctionStatement(CompileState state, String input) {
        return Main.compileSuffix(Strings.strip(input), ";", (String withoutEnd) -> {
            var valueTuple = Main.compileFunctionStatementValue(state, withoutEnd);
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(valueTuple.left(), Main.generateIndent(state.depth) + valueTuple.right() + ";"));
        });
    }

    private static String generateIndent(int indent) {
        return "\n" + "\t".repeat(indent);
    }

    private static Tuple2<CompileState, String> compileFunctionStatementValue(CompileState state, String withoutEnd) {
        return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(
                Main::compileReturnWithValue,
                Main::compileAssignment,
                (CompileState state1, String input) -> Main.parseInvokable(state1, input).map((Tuple2<CompileState, Value> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate())),
                Main.createPostRule("++"),
                Main.createPostRule("--"),
                Main::compileBreak
        ));
    }

    private static Option<Tuple2<CompileState, String>> compileBreak(CompileState state, String input) {
        if (Strings.equalsTo("break", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, "break"));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createPostRule(String suffix) {
        return (CompileState state1, String input) -> Main.compileSuffix(Strings.strip(input), suffix, (String child) -> {
            var tuple = Main.compileValueOrPlaceholder(state1, child);
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right() + suffix));
        });
    }

    private static Option<Tuple2<CompileState, String>> compileReturnWithValue(CompileState state, String input) {
        return Main.compileReturn(input, (String value1) -> Main.compileValue(state, value1));
    }

    private static Option<Tuple2<CompileState, String>> compileReturn(String input, Function<String, Option<Tuple2<CompileState, String>>> mapper) {
        return Main.compilePrefix(Strings.strip(input), "return ", (String value) -> {
            return mapper.apply(value).flatMap((Tuple2<CompileState, String> tuple) -> {
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), "return " + tuple.right()));
            });
        });
    }

    private static Option<Tuple2<CompileState, Value>> parseInvokable(CompileState state, String input) {
        return Main.compileSuffix(Strings.strip(input), ")", (String withoutEnd) -> {
            return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main::foldInvocationStarts), (String callerWithArgStart, String args) -> {
                return Main.compileSuffix(callerWithArgStart, "(", (String callerString) -> {
                    return Main.compilePrefix(Strings.strip(callerString), "new ", (String type) -> {
                        return Main.compileType(state, type).flatMap((Tuple2<CompileState, String> callerTuple) -> {
                            var callerState = callerTuple.right();
                            var caller = callerTuple.left();
                            return Main.assembleInvokable(caller, new ConstructionCaller(callerState), args);
                        });
                    }).or(() -> {
                        return Main.parseValue(state, callerString).flatMap((Tuple2<CompileState, Value> callerTuple) -> {
                            return Main.assembleInvokable(callerTuple.left(), callerTuple.right(), args);
                        });
                    });
                });
            });
        });
    }

    private static Option<Tuple2<String, String>> splitFoldedLast(String input, String delimiter, BiFunction<DivideState, Character, DivideState> folder) {
        return Main.splitFolded(input, folder, (List<String> divisions1) -> Main.selectLast(divisions1, delimiter));
    }

    private static Option<Tuple2<String, String>> splitFolded(
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            Function<List<String>, Option<Tuple2<String, String>>> selector
    ) {
        var divisions = Main.divide(input, folder);
        if (2 > divisions.size()) {
            return new None<Tuple2<String, String>>();
        }

        return selector.apply(divisions);
    }

    private static Option<Tuple2<String, String>> selectLast(List<String> divisions, String delimiter) {
        var beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
        var last = divisions.findLast().orElse("");

        var joined = beforeLast.query()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(joined, last));
    }

    private static DivideState foldInvocationStarts(DivideState state, char c) {
        var appended = state.append(c);
        if ('(' == c) {
            var entered = appended.enter();
            if (entered.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }

        if (')' == c) {
            return appended.exit();
        }

        return appended;
    }

    private static Option<Tuple2<CompileState, Value>> assembleInvokable(CompileState state, Caller oldCaller, String argsString) {
        return Main.parseValues(state, argsString, (CompileState state1, String s) -> Main.parseArgument(state1, s)).flatMap((Tuple2<CompileState, List<Argument>> argsTuple) -> {
            var argsState = argsTuple.left();
            var args = Main.retain(argsTuple.right(), (Argument argument) -> argument.toValue());

            var newCaller = Main.transformCaller(argsState, oldCaller);
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new Invokable(newCaller, args)));
        });
    }

    private static Caller transformCaller(CompileState state, Caller oldCaller) {
        return oldCaller.findChild().flatMap((Value parent) -> {
            var parentType = parent.resolve(state);
            if (parentType.isFunctional()) {
                return new Some<Caller>(parent);
            }

            return new None<Caller>();
        }).orElse(oldCaller);
    }

    private static <T, R> List<R> retain(List<T> args, Function<T, Option<R>> mapper) {
        return args.query()
                .map(mapper)
                .flatMap(Queries::fromOption)
                .collect(new ListCollector<R>());
    }

    private static Tuple2<CompileState, Argument> parseArgumentOrPlaceholder(CompileState state1, String input) {
        return Main.parseArgument(state1, input).orElseGet(() -> new Tuple2Impl<CompileState, Argument>(state1, new Placeholder(input)));
    }

    private static Option<Tuple2<CompileState, Argument>> parseArgument(CompileState state1, String input) {
        return Main.parseValue(state1, input)
                .map((Tuple2<CompileState, Value> tuple) -> new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right()));
    }

    private static Option<Tuple2<CompileState, String>> compileAssignment(CompileState state, String input) {
        return Main.compileFirst(input, "=", (String destination, String source) -> {
            var sourceTuple = Main.compileValueOrPlaceholder(state, source);

            var destinationTuple = Main.compileValue(sourceTuple.left(), destination)
                    .or(() -> Main.parseDefinition(sourceTuple.left(), destination).map((Tuple2<CompileState, Definition> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), "let " + tuple.right().generate())))
                    .orElseGet(() -> new Tuple2Impl<CompileState, String>(sourceTuple.left(), Main.generatePlaceholder(destination)));

            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
        });
    }

    private static Tuple2<CompileState, String> compileValueOrPlaceholder(CompileState state, String input) {
        return Main.compileValue(state, input).orElseGet(() -> new Tuple2Impl<CompileState, String>(state, Main.generatePlaceholder(input)));
    }

    private static Option<Tuple2<CompileState, String>> compileValue(CompileState state, String input) {
        return Main.parseValue(state, input).map((Tuple2<CompileState, Value> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate()));
    }

    private static Option<Tuple2<CompileState, Value>> parseValue(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                Main::parseLambda,
                Main.createOperatorRule("+"),
                Main.createOperatorRule("-"),
                Main.createOperatorRule("<="),
                Main.createOperatorRule("<"),
                Main.createOperatorRule("&&"),
                Main.createOperatorRule("||"),
                Main.createOperatorRule(">"),
                Main.createOperatorRule(">="),
                Main::parseInvokable,
                Main.createAccessRule("."),
                Main.createAccessRule("::"),
                Main::parseSymbol,
                Main::parseNot,
                Main::parseNumber,
                Main.createOperatorRuleWithDifferentInfix("==", "==="),
                Main.createOperatorRuleWithDifferentInfix("!=", "!=="),
                Main.createTextRule("\""),
                Main.createTextRule("'")
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createTextRule(String slice) {
        return (CompileState state1, String input1) -> {
            var stripped = Strings.strip(input1);
            return Main.compilePrefix(stripped, slice, (String s) -> {
                return Main.compileSuffix(s, slice, (String s1) -> new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringValue(s1))));
            });
        };
    }

    private static Option<Tuple2<CompileState, Value>> parseNot(CompileState state, String input) {
        return Main.compilePrefix(Strings.strip(input), "!", (String withoutPrefix) -> {
            var childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix);
            var childState = childTuple.left();
            var child = "!" + childTuple.right();
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Not(child)));
        });
    }

    private static Option<Tuple2<CompileState, Value>> parseLambda(CompileState state, String input) {
        return Main.compileFirst(input, "->", (String beforeArrow, String afterArrow) -> {
            var strippedBeforeArrow = Strings.strip(beforeArrow);
            return Main.compilePrefix(strippedBeforeArrow, "(", (String withoutStart) -> {
                return Main.compileSuffix(withoutStart, ")", (String withoutEnd) -> {
                    return Main.parseValues(state, withoutEnd, (CompileState state1, String s) -> Main.parseParameter(state1, s)).flatMap((Tuple2<CompileState, List<Parameter>> paramNames) -> {
                        return Main.compileLambdaWithParameterNames(paramNames.left(), Main.retainDefinitionsFromParameters(paramNames.right()), afterArrow);
                    });
                });
            });
        });
    }

    private static Option<Tuple2<CompileState, Value>> compileLambdaWithParameterNames(CompileState state, List<Definition> paramNames, String afterArrow) {
        var strippedAfterArrow = Strings.strip(afterArrow);
        return Main.compilePrefix(strippedAfterArrow, "{", (String withoutContentStart) -> {
            return Main.compileSuffix(withoutContentStart, "}", (String withoutContentEnd) -> {
                var statementsTuple = Main.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd);
                var statementsState = statementsTuple.left();
                var statements = statementsTuple.right();

                var exited = statementsState.exitDepth();
                return Main.assembleLambda(exited, paramNames, "{" + statements + Main.generateIndent(exited.depth) + "}");
            });
        }).or(() -> {
            return Main.compileValue(state, strippedAfterArrow).flatMap((Tuple2<CompileState, String> tuple) -> {
                return Main.assembleLambda(tuple.left(), paramNames, tuple.right());
            });
        });
    }

    private static Option<Tuple2<CompileState, Value>> assembleLambda(CompileState exited, List<Definition> paramNames, String content) {
        return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(exited, new Lambda(paramNames, content)));
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createOperatorRule(String infix) {
        return Main.createOperatorRuleWithDifferentInfix(infix, infix);
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createAccessRule(String infix) {
        return (CompileState state, String input) -> Main.compileLast(input, infix, (String childString, String rawProperty) -> {
            var property = Strings.strip(rawProperty);
            if (!Main.isSymbol(property)) {
                return new None<Tuple2<CompileState, Value>>();
            }

            return Main.parseValue(state, childString).flatMap((Tuple2<CompileState, Value> childTuple) -> {
                var childState = childTuple.left();
                var child = childTuple.right();
                return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Access(child, property)));
            });
        });
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createOperatorRuleWithDifferentInfix(String sourceInfix, String targetInfix) {
        return (CompileState state1, String input1) -> {
            return Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), (List<String> divisions) -> Main.selectFirst(divisions, sourceInfix)), (String leftString, String rightString) -> {
                return Main.parseValue(state1, leftString).flatMap((Tuple2<CompileState, Value> leftTuple) -> {
                    return Main.parseValue(leftTuple.left(), rightString).flatMap((Tuple2<CompileState, Value> rightTuple) -> {
                        var left = leftTuple.right();
                        var right = rightTuple.right();
                        return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new Operation(left, targetInfix, right)));
                    });
                });
            });
        };
    }

    private static Option<Tuple2<String, String>> selectFirst(List<String> divisions, String delimiter) {
        var first = divisions.findFirst().orElse("");
        var afterFirst = divisions.subList(1, divisions.size()).orElse(divisions)
                .query()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(first, afterFirst));
    }

    private static BiFunction<DivideState, Character, DivideState> foldOperator(String infix) {
        return (DivideState state, Character c) -> {
            if (c == Strings.charAt(infix, 0) && state.startsWith(Strings.sliceFrom(infix, 1))) {
                var length = Strings.length(infix) - 1;
                var counter = 0;
                var current = state;
                while (counter < length) {
                    counter++;

                    current = current.pop().map((Tuple2<DivideState, Character> tuple) -> tuple.left()).orElse(current);
                }
                return current.advance();
            }

            return state.append(c);
        };
    }

    private static Option<Tuple2<CompileState, Value>> parseNumber(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (Main.isNumber(stripped)) {
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new SymbolNode(stripped)));
        }
        else {
            return new None<Tuple2<CompileState, Value>>();
        }
    }

    private static boolean isNumber(String input) {
        var query = new HeadedQuery<Integer>(new RangeHead(Strings.length(input)));
        return query.map((Integer index) -> input.charAt(index)).allMatch((Character c) -> Characters.isDigit(c));
    }

    private static Option<Tuple2<CompileState, Value>> parseSymbol(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (Main.isSymbol(stripped)) {
            var withImport = state.addResolvedImportFromCache(stripped);
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new SymbolNode(stripped)));
        }
        else {
            return new None<Tuple2<CompileState, Value>>();
        }
    }

    private static boolean isSymbol(String input) {
        var query = new HeadedQuery<Integer>(new RangeHead(Strings.length(input)));
        return query.allMatch((Integer index) -> Main.isSymbolChar(index, Strings.charAt(input, index)));
    }

    private static boolean isSymbolChar(int index, char c) {
        return '_' == c
                || Characters.isLetter(c)
                || (0 != index && Characters.isDigit(c));
    }

    private static <T> Option<Tuple2<CompileState, T>> compilePrefix(
            String input,
            String infix,
            Function<String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        if (!input.startsWith(infix)) {
            return new None<Tuple2<CompileState, T>>();
        }

        var slice = Strings.sliceFrom(input, Strings.length(infix));
        return mapper.apply(slice);
    }

    private static Option<Tuple2<CompileState, String>> compileWhitespace(CompileState state, String input) {
        return Main.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate()));
    }

    private static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(CompileState state, String input) {
        if (Strings.isBlank(input)) {
            return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
        }
        return new None<Tuple2<CompileState, Whitespace>>();
    }

    private static Option<Tuple2<CompileState, String>> compileFieldDefinition(CompileState state, String input) {
        return Main.compileSuffix(Strings.strip(input), ";", (String withoutEnd) -> {
            return Main.getTupleOption(state, withoutEnd).or(() -> Main.compileEnumValues(state, withoutEnd));
        });
    }

    private static Option<Tuple2<CompileState, String>> getTupleOption(CompileState state, String withoutEnd) {
        return Main.parseParameter(state, withoutEnd).flatMap((Tuple2<CompileState, Parameter> definitionTuple) -> {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(definitionTuple.left(), "\n\t" + definitionTuple.right().generate() + ";"));
        });
    }

    private static Option<Tuple2<CompileState, String>> compileEnumValues(CompileState state, String withoutEnd) {
        return Main.parseValues(state, withoutEnd, (CompileState state1, String s) -> {
            return Main.parseInvokable(state1, s).flatMap((Tuple2<CompileState, Value> tuple) -> {
                var structureName = state.structureNames.findLast().orElse("");
                return tuple.right().generateAsEnumValue(structureName).map((String stringOption) -> {
                    return new Tuple2Impl<CompileState, String>(tuple.left(), stringOption);
                });
            });
        }).map((Tuple2<CompileState, List<String>> tuple) -> {
            return new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().query().collect(new Joiner("")).orElse(""));
        });
    }

    private static Tuple2<CompileState, Parameter> parseParameterOrPlaceholder(CompileState state, String input) {
        return Main.parseParameter(state, input).orElseGet(() -> new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input)));
    }

    private static Option<Tuple2<CompileState, Parameter>> parseParameter(CompileState state, String input) {
        return Main.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) -> Main.getCompileStateParameterTuple2(tuple))
                .or(() -> Main.parseDefinition(state, input).map((Tuple2<CompileState, Definition> tuple) -> new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())));
    }

    private static Tuple2<CompileState, Parameter> getCompileStateParameterTuple2(Tuple2<CompileState, Whitespace> tuple) {
        return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return Main.compileLast(Strings.strip(input), " ", (String beforeName, String name) -> {
            return Main.compileSplit(Main.splitFoldedLast(Strings.strip(beforeName), " ", Main::foldTypeSeparators), (String beforeType, String type) -> {
                return Main.compileLast(Strings.strip(beforeType), "\n", (String annotationsString, String afterAnnotations) -> {
                    var annotations = Main.parseAnnotations(annotationsString);
                    return Main.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
                }).or(() -> {
                    return Main.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name);
                });
            }).or(() -> {
                return Main.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name);
            });
        });
    }

    private static List<String> parseAnnotations(String s) {
        return Main.divide(s, (DivideState state1, Character c) -> Main.foldDelimited(state1, c, '\n')).query()
                .map((String s2) -> Strings.strip(s2))
                .filter((String value) -> !Strings.isEmpty(value))
                .filter((String value) -> 1 <= Strings.length(value))
                .map((String value) -> Strings.sliceFrom(value, 1))
                .map((String s1) -> Strings.strip(s1))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithAnnotations(CompileState state, List<String> annotations, String beforeType, String type, String name) {
        return Main.compileSuffix(Strings.strip(beforeType), ">", (String withoutTypeParamEnd) -> {
            return Main.compileFirst(withoutTypeParamEnd, "<", (String beforeTypeParams, String typeParamsString) -> {
                var typeParams = Main.divideValues(typeParamsString);
                return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name);
            });
        }).or(() -> {
            var divided = Main.parseModifiers(beforeType);
            return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
        });
    }

    private static List<String> parseModifiers(String beforeType) {
        return Main.divide(Strings.strip(beforeType), (DivideState state1, Character c) -> Main.foldDelimited(state1, c, ' '))
                .query()
                .map((String s) -> Strings.strip(s))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    private static DivideState foldDelimited(DivideState state1, char c, char delimiter) {
        if (delimiter == c) {
            return state1.advance();
        }
        return state1.append(c);
    }

    private static List<String> divideValues(String input) {
        return Main.divide(input, Main::foldValues)
                .query()
                .map((String input1) -> Strings.strip(input1))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    private static DivideState foldTypeSeparators(DivideState state, char c) {
        if (' ' == c && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if ('<' == c) {
            return appended.enter();
        }
        if ('>' == c) {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeParameters(
            CompileState state,
            List<String> annotations,
            List<String> typeParams,
            List<String> oldModifiers,
            String type,
            String name
    ) {
        return Main.parseType(state, type).flatMap((Tuple2<CompileState, Type> typeTuple) -> {
            var newModifiers = Main.modifyModifiers(oldModifiers);
            var generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name);
            return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated));
        });
    }

    private static List<String> modifyModifiers(List<String> oldModifiers) {
        if (oldModifiers.contains("static", Strings::equalsTo)) {
            return Lists.of("static");
        }
        return Lists.empty();
    }

    private static Tuple2<CompileState, Type> parseTypeOrPlaceholder(CompileState state, String type) {
        return Main.parseType(state, type)
                .map((Tuple2<CompileState, Type> tuple) -> new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right()))
                .orElseGet(() -> new Tuple2Impl<CompileState, Type>(state, new Placeholder(type)));
    }

    private static Option<Tuple2<CompileState, String>> compileType(CompileState state, String type) {
        return Main.parseType(state, type).map((Tuple2<CompileState, Type> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate()));
    }

    private static Option<Tuple2<CompileState, Type>> parseType(CompileState state, String type) {
        return Main.or(state, type, Lists.of(
                Main::parseArrayType,
                Main::parseVarArgs,
                Main::parseGeneric,
                Main::parsePrimitive,
                Main::parseSymbolType
        ));
    }

    private static Option<Tuple2<CompileState, Type>> parseArrayType(CompileState state, String input) {
        var stripped = Strings.strip(input);
        return Main.compileSuffix(stripped, "[]", (String s) -> {
            var child = Main.parseTypeOrPlaceholder(state, s);
            return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new ArrayType(child.right())));
        });
    }

    private static Option<Tuple2<CompileState, Type>> parseVarArgs(CompileState state, String input) {
        var stripped = Strings.strip(input);
        return Main.compileSuffix(stripped, "...", (String s) -> {
            var child = Main.parseTypeOrPlaceholder(state, s);
            return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VarArgs(child.right())));
        });
    }

    private static Option<Tuple2<CompileState, Type>> parseSymbolType(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (Main.isSymbol(stripped)) {
            return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state.addResolvedImportFromCache(stripped), new SymbolNode(stripped)));
        }
        return new None<Tuple2<CompileState, Type>>();
    }

    private static Option<Tuple2<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        return Main.findPrimitiveValue(Strings.strip(input)).map((Type result) -> new Tuple2Impl<CompileState, Type>(state, result));
    }

    private static Option<Type> findPrimitiveValue(String input) {
        var stripped = Strings.strip(input);
        if (Strings.equalsTo("char", stripped) || Strings.equalsTo("Character", stripped) || Strings.equalsTo("String", stripped)) {
            return new Some<Type>(Primitive.String);
        }

        if (Strings.equalsTo("int", stripped) || Strings.equalsTo("Integer", stripped)) {
            return new Some<Type>(Primitive.Number);
        }

        if (Strings.equalsTo("boolean", stripped) || Strings.equalsTo("Boolean", stripped)) {
            return new Some<Type>(Primitive.Boolean);
        }

        if (Strings.equalsTo("var", stripped)) {
            return new Some<Type>(Primitive.Var);
        }

        if (Strings.equalsTo("void", stripped)) {
            return new Some<Type>(Primitive.Void);
        }

        return new None<Type>();
    }

    private static Option<Tuple2<CompileState, Type>> parseGeneric(CompileState state, String input) {
        return Main.compileSuffix(Strings.strip(input), ">", (String withoutEnd) -> {
            return Main.compileFirst(withoutEnd, "<", (String baseString, String argsString) -> {
                var argsTuple = Main.parseValuesOrEmpty(state, argsString, (CompileState state1, String s) -> Main.compileTypeArgument(state1, s));
                var argsState = argsTuple.left();
                var args = argsTuple.right();

                var base = Strings.strip(baseString);
                return Main.assembleFunctionType(argsState, base, args).or(() -> {
                    var compileState = argsState.addResolvedImportFromCache(base);
                    return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new Generic(base, args)));
                });
            });
        });
    }

    private static Option<Tuple2<CompileState, Type>> assembleFunctionType(CompileState state, String base, List<String> args) {
        return Main.mapFunctionType(base, args).map((Type generated) -> new Tuple2Impl<CompileState, Type>(state, generated));
    }

    private static Option<Type> mapFunctionType(String base, List<String> args) {
        if (Strings.equalsTo("Function", base)) {
            return args.findFirst().and(() -> args.find(1))
                    .map((Tuple2<String, String> tuple) -> new FunctionType(Lists.of(tuple.left()), tuple.right()));
        }

        if (Strings.equalsTo("BiFunction", base)) {
            return args.find(0)
                    .and(() -> args.find(1))
                    .and(() -> args.find(2))
                    .map((Tuple2<Tuple2<String, String>, String> tuple) -> new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right()));
        }

        if (Strings.equalsTo("Supplier", base)) {
            return args.findFirst().map((String first) -> {
                return new FunctionType(Lists.empty(), first);
            });
        }

        if (Strings.equalsTo("Consumer", base)) {
            return args.findFirst().map((String first) -> {
                return new FunctionType(Lists.of(first), "void");
            });
        }

        if (Strings.equalsTo("Predicate", base)) {
            return args.findFirst().map((String first) -> {
                return new FunctionType(Lists.of(first), "boolean");
            });
        }

        return new None<Type>();
    }

    private static Option<Tuple2<CompileState, String>> compileTypeArgument(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                (CompileState state2, String input1) -> Main.compileWhitespace(state2, input1),
                (CompileState state1, String type) -> Main.compileType(state1, type)
        ));
    }

    private static String generateValueStrings(List<String> values) {
        return Main.generateAll(values, Main::mergeValues);
    }

    private static <T> Tuple2<CompileState, List<T>> parseValuesOrEmpty(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        return Main.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
    }

    private static <T> Option<Tuple2<CompileState, List<T>>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper) {
        return Main.parseAll(state, input, Main::foldValues, mapper);
    }

    private static String mergeValues(String cache, String element) {
        if (Strings.isEmpty(cache)) {
            return cache + element;
        }
        return cache + ", " + element;
    }

    private static DivideState foldValues(DivideState state, char c) {
        if (',' == c && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if ('-' == c) {
            var peeked = appended.peek();
            if ('>' == peeked) {
                return appended.popAndAppendToOption().orElse(appended);
            }
            else {
                return appended;
            }
        }

        if ('<' == c || '(' == c) {
            return appended.enter();
        }

        if ('>' == c || ')' == c) {
            return appended.exit();
        }

        return appended;
    }

    private static <T> Option<T> compileLast(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return Main.compileInfix(input, infix, Main::findLast, mapper);
    }

    private static int findLast(String input, String infix) {
        return input.lastIndexOf(infix);
    }

    private static <T> Option<T> compileSuffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<T>();
        }

        var length = Strings.length(input);
        var length1 = Strings.length(suffix);
        var content = Strings.sliceBetween(input, 0, length - length1);
        return mapper.apply(content);
    }

    private static <T> Option<T> compileFirst(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return Main.compileInfix(input, infix, Main::findFirst, mapper);
    }

    private static <T> Option<T> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Option<T>> mapper) {
        return Main.compileSplit(Main.split(input, infix, locator), mapper);
    }

    private static <T> Option<T> compileSplit(Option<Tuple2<String, String>> splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.flatMap((Tuple2<String, String> tuple) -> mapper.apply(tuple.left(), tuple.right()));
    }

    private static Option<Tuple2<String, String>> split(String input, String infix, BiFunction<String, String, Integer> locator) {
        var index = locator.apply(input, infix);
        if (0 > index) {
            return new None<Tuple2<String, String>>();
        }

        var left = Strings.sliceBetween(input, 0, index);

        var length = Strings.length(infix);
        var right = Strings.sliceFrom(input, index + length);
        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(left, right));
    }

    private static int findFirst(String input, String infix) {
        return input.indexOf(infix);
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }

    private enum Platform {
        TypeScript("node", "ts"),
        Magma("magma", "mgs");
        String root;
        String extension;

        Platform(String root, String extension) {
            this.root = root;
            this.extension = extension;
        }
    }

    private enum Primitive implements Type {
        String("string"),
        Number("number"),
        Boolean("boolean"),
        Var("var"),
        Void("void"),
        Unknown("unknown");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public boolean isFunctional() {
            return false;
        }

        @Override
        public boolean isVar() {
            return Primitive.Var == this;
        }

        @Override
        public String generateBeforeName() {
            return "";
        }
    }
}