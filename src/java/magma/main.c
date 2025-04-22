package /*magma*/;

import /*java.io.IOException*/;
import /*java.nio.file.Files*/;
import /*java.nio.file.Path*/;
import /*java.nio.file.Paths*/;
import /*java.util.ArrayList*/;
import /*java.util.List*/;
import /*java.util.Optional*/;
import /*java.util.stream.Stream*/;/*

public */class Main {/*
    private */interface DivideState {
        DivideState advance();

        DivideState append(char c);

        Stream<String> stream();

        boolean isLevel();

        DivideState enter();

        DivideState exit();

        boolean isShallow();
    }

    interface Rule {
        Optional<String> compile(String input);
    }/*

    private */interface Locator {
        Optional<Integer> locate(String input String infix);
    }/*

    private */interface Folder {
        DivideState fold(DivideState state char c);
    }/*

    private static */class MutableDivideState implements DivideState {/*
        private final List<String> segments;*//*
        private StringBuilder buffer;*//*
        private int depth;*/

        private MutableDivideState(List<String> segments StringBuilder buffer int depth)/* {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth*/;/*
        }*/

        public MutableDivideState() {
            this(new ArrayList<>() new StringBuilder() 0);/*
        }*/

        @Override
        public DivideState advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder()/*;
            return this*/;/*
        }*/

        @Override
        public DivideState append(char c) {
            this.buffer.append(c)/*;
            return this*/;/*
        }*/

        @Override
        public Stream<String> stream() {
            return this.segments.stream();/*
        }*/

        @Override
        public boolean isLevel()/* {
            return this.depth == 0*/;/*
        }*/

        @Override
        public DivideState enter()/* {
            this.depth++;
            return this*/;/*
        }*/

        @Override
        public DivideState exit()/* {
            this.depth--;
            return this*/;/*
        }*/

        @Override
        public boolean isShallow()/* {
            return this.depth == 1*/;/*
        }*/
    }/*

    private */record InfixRule(
            Rule leftRule,
            String infix,
            Locator locator,
            Rule rightRule
    ) implements Rule {
        private InfixRule(Rule leftRule String infix Rule rightRule) {
            this(leftRule infix new FirstLocator() rightRule);/*
        }*/

        @Override
        public Optional<String> compile(
                String input) {
            return this.locator().locate(input this.infix()).flatMap(index -> {
                String left = input.substring(0 index);
                String right = input.substring(index + this.infix().length());

                return this.leftRule().compile(left).flatMap(compiledLeft -> {
                    return this.rightRule().compile(right).map(compiledRight -> {
                        return compiledLeft + this.infix() + compiledRight;
                    });
                });
            });/*
        }*/
    }/*

    public static */class FirstLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input String infix) {
            int index = input.indexOf(infix);
            return index == -1
                    ? Optional.empty()
                    : Optional.of(index);/*
        }*/
    }/*

    private */record PrefixRule(String prefix, Rule childRule) implements Rule {
        @Override
        public Optional<String> compile(String input) {
            return new InfixRule(new ContentRule() this.prefix() this.childRule()).compile(input);/*
        }*/
    }/*

    private */record SuffixRule(Rule childRule, String suffix) implements Rule {
        @Override
        public Optional<String> compile(String input) {
            return new InfixRule(this.childRule() this.suffix() new LastLocator() new ContentRule()).compile(input);/*
        }*/
    }/*

    public static */class ContentRule implements Rule {
        @Override
        public Optional<String> compile(String content) {
            String generated = content.isBlank() ? content : "/*" + content + "*/";
            return Optional.of(generated);/*
        }*/
    }/*

    public static */class StringRule implements Rule {
        @Override
        public Optional<String> compile(String name) {
            return Optional.of(name);/*
        }*/
    }/*

    private */record DivideRule(Folder folder, Rule rule) implements Rule {
        private static Stream<String> divide(DivideState state String input Folder folder) {
            DivideState current = state;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                current = folder.fold(current c);
            }

            return current.advance().stream();/*
        }*/

        @Override
        public Optional<String> compile(String input) {
            return divide(new MutableDivideState() input this.folder)
                    .reduce(Optional.of("")
                            (maybeCurrent element) -> maybeCurrent.flatMap(current -> this.rule.compile(element).map(compiled -> current + compiled))
                            (_ next) -> next);/*
        }*/
    }/*

    private */record OrRule(List<Rule> rules) implements Rule {
        @Override
        public Optional<String> compile(String input) {
            for (Rule rule : this.rules()) {
                Optional<String> compiled = rule.compile(input);
                if (compiled.isPresent()) {
                    return compiled;
                }
            }

            return Optional.empty();/*
        }*/
    }/*

    private static */class LazyRule implements Rule {
        private Optional<Rule> childRule = Optional.empty();

        public void set(Rule childRule) {
            this.childRule = Optional.of(childRule);/*
        }*/

        @Override
        public Optional<String> compile(String input) {
            return this.childRule.flatMap(internal -> internal.compile(input));/*
        }*/
    }/*

    private static */class LastLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input String infix) {
            int index = input.lastIndexOf(infix);
            return index == -1
                    ? Optional.empty()
                    : Optional.of(index);/*
        }*/
    }/*

    private static */class StatementFolder implements Folder {
        @Override
        public DivideState fold(DivideState state char c) {
            DivideState appended = state.append(c);
            if (c == ';' && appended.isLevel()) {
                return appended.advance();/*
            }
            if (c == '}*/' && appended.isShallow()) {
                return appended.advance().exit();/*
            }*/
            if (c == '{') {
                return appended.enter();/*
            }
            if (c == '}*/') {
                return appended.exit();/*
            }*//*
            return appended;*/
        }
    }/*

    private static */class ValueFolder implements Folder {
        @Override
        public DivideState fold(DivideState state char c) {
            if (c == '') {
                return state.advance();
            }

            return state.append(c);/*
        }*/
    }/*

    private static */class BlankRule implements Rule {
        @Override
        public Optional<String> compile(String input) {
            if (input.isBlank()) {
                return Optional.of("");
            }
            else {
                return Optional.empty();/*
            }
        }*/
    }/*

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }*//*

    private static String compile(String input) {
        return Statements(createRootSegmentRule()).compile(input).orElse("");
    }*//*

    private static OrRule createRootSegmentRule() {
        return new OrRule(List.of(
                createNamespaceRule("*/package /*"),
                createNamespaceRule("import "),
                createClassRule(createClassSegmentRule()),
                new ContentRule()
        ))*/;/*
    }*//*

    private static Rule createNamespaceRule(String infix) {
        return new PrefixRule(infix, new SuffixRule(new ContentRule(), ";"));
    }*//*

    private static Rule createClassRule(Rule classSegment) {
        return createStructuredRule("class ", classSegment);
    }*//*

    private static Rule createStructuredRule(String infix, Rule classSegment) {
        return new PrefixRule(infix, new InfixRule(new StringRule(), "{",
                new SuffixRule(Statements(classSegment), "}")));
    }*//*

    private static DivideRule Statements(Rule classSegment) {
        return new DivideRule(new StatementFolder(), classSegment);
    }*//*

    private static Rule createClassSegmentRule() {
        LazyRule classSegment = new LazyRule();
        classSegment.set(new OrRule(List.of(
                createClassRule(classSegment),
                createStructuredRule("interface ", classSegment),
                createStructuredRule("record ", classSegment),
                createMethodRule(),
                new ContentRule()
        )));
        return classSegment;
    }*//*

    private static SuffixRule createMethodRule() {
        InfixRule definition = createDefinitionRule();
        Rule params = new DivideRule(new ValueFolder(), new OrRule(List.of(
                new BlankRule(),
                definition
        )));

        return new SuffixRule(new InfixRule(definition, "(", new SuffixRule(params, ")")), ";");
    }*//*

    private static InfixRule createDefinitionRule() {
        return new InfixRule(new StringRule(), " ", new LastLocator(), new StringRule());
    }*//*
}*/