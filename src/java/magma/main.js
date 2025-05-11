"use strict";
/* private */ class Some {
    /* @Override
        public  */ map(mapper) {
        return /* new Some<>(mapper */ .apply(this.value);
        ;
    }
    /* @Override
        public */ isPresent() {
        return true;
    }
    /* @Override
        public */ orElse(other) {
        return /* this.value */;
    }
    /* @Override
        public */ filter(predicate) {
        return predicate.test(this.value) ? this : new None();
    }
    /* @Override
        public */ orElseGet(supplier) {
        return /* this.value */;
    }
    /* @Override
        public */ or(other) {
        return this;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return mapper.apply(this.value);
    }
    /* @Override
        public */ isEmpty() {
        return false;
    }
}
/* private static */ class None {
    /* @Override
        public  */ map(mapper) {
        return /* new None<>() */;
    }
    /* @Override
        public */ isPresent() {
        return false;
    }
    /* @Override
        public */ orElse(other) {
        return other;
    }
    /* @Override
        public */ filter(predicate) {
        return /* new None<>() */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return supplier.get();
    }
    /* @Override
        public */ or(other) {
        return other.get();
    }
    /* @Override
        public  */ flatMap(mapper) {
        return /* new None<>() */;
    }
    /* @Override
        public */ isEmpty() {
        return true;
    }
}
/* private */ class HeadedIterator {
    /* @Override
        public  */ fold(initial, folder) {
        let current = initial; /* while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {
                    current = optional.orElse(null);
                }
                else {
                    return current;
                }
            } */
    }
    /* @Override
        public  */ map(mapper) {
        return /* new HeadedIterator<>(() -> this.head */ .next().map(mapper);
        ;
    }
    /* @Override
        public  */ collect(collector) {
        return this.fold(collector.createInitial(), collector, fold);
    }
}
/* private static final */ class JVMList {
    JVMList(elements) {
        let /* this.elements  */ = elements;
    }
    JVMList() {
        /* this(new ArrayList<>()) */ ;
    }
    /* @Override
            public */ add(element) {
        /* this.elements.add(element) */ ;
        return this;
    }
    /* @Override
            public */ iterate() {
        return /* new HeadedIterator<>(new RangeHead(this.elements */ .size();
        map(this.elements, get);
    }
    /* @Override
            public */ removeLast() {
        let slice = /* this.elements.subList(0, this.elements */ .size() - 1;
        let last = /* this.elements */ .getLast();
        return /* new Some<>(new Tuple<List<T>, T>(new JVMList<>(slice), last)) */;
    }
    /* @Override
            public */ get(index) {
        return /* this.elements */ .get(index);
    }
}
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return /* new JVMList<>() */;
    }
    /* public static  */ of(elements) {
        return /* new JVMList<>(new ArrayList<>(Arrays */ .asList(elements);
        ;
    }
}
/* private static */ class DivideState /*  */ {
    DivideState(input, index, segments, buffer, depth) {
        let /* this.segments  */ = segments;
        let /* this.buffer  */ = buffer;
        let /* this.depth  */ = depth;
        let /* this.input  */ = input;
        let /* this.index  */ = index;
    }
    DivideState(input) {
        /* this(input, 0, Lists.empty(), new StringBuilder(), 0) */ ;
    }
    /* private */ advance() {
        let /* this.segments  */ = /* this.segments.add(this.buffer */ .toString();
        ;
        let /* this.buffer  */ =  /*  new StringBuilder() */;
        return this;
    }
    /* private */ append(c) {
        /* this.buffer.append(c) */ ;
        return this;
    }
    /* public */ enter() {
        /* this.depth++ */ ;
        return this;
    }
    /* public */ isLevel() {
        return this.depth == 0;
    }
    /* public */ exit() {
        /* this.depth-- */ ;
        return this;
    }
    /* public */ isShallow() {
        return this.depth == 1;
    }
    /* public */ pop() {
        return /* new None<>() */;
    }
    /* public */ popAndAppendToTuple() {
        return /* this.pop().map(tuple -> new Tuple<>(tuple.left, tuple.right */ .append(tuple.left);
        ;
    }
    /* public */ popAndAppendToOption() {
        return this.popAndAppendToTuple().map(Tuple, right);
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return Lists.empty();
    }
    /* @Override
        public */ fold(current, element) {
        return current.add(element);
    }
}
/* private */ class Tuple {
}
/* public */ class Main /*  */ {
    /* private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        */ RangeHead(length) {
        let /* this.length  */ = length; /* }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            } */
        return /* new None<>() */; /* } */
    }
    /* private */ CompileState(structures) {
    } /*

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    } */
    /* private */ Definition(maybeBefore, type, name, typeParams) {
    }
    /* public static */ main() {
    }
    /* private static */ compile(input) {
        let tuple =  /*  compileStatements(new CompileState(), input, Main::compileRootSegment) */;
        let joined = /* tuple.left.structures */ .iterate().collect(new Joiner()).orElse("");
        return joined +  /*  tuple.right */;
    }
    /* private static */ compileStatements(state, input, mapper) {
        return /* compileAll(state, input, Main::foldStatementChar, mapper, Main::mergeStatements) */;
    }
    /* private static */ compileAll(state, input, folder, mapper, merger) {
        let parsed =  /*  parseAll(state, input, folder, mapper) */;
        let generated = /* generateAll(merger, parsed */ .right;
        return /* new Tuple<>(parsed */ .left, generated;
        ;
    }
    /* private static */ generateAll(merger, elements) {
        return elements.iterate().fold(new StringBuilder(), merger).toString();
    }
    /* private static */ parseAll(state, input, folder, mapper) {
        return /* divideAll(input, folder) */ .iterate().fold(new Tuple(state, Lists.empty()), (tuple, element) -  > {
            var: state1 = tuple.left,
            var: right = tuple.right,
            var: applied = mapper.apply(state1, element),
            return: new Tuple(applied.left, right.add(applied.right))
        });
    }
    /* private static */ mergeStatements(stringBuilder, str) {
        return stringBuilder.append(str);
    }
    /* private static */ divideAll(input, folder) {
        let current =  /*  new DivideState(input) */; /* while (true) {
            var maybePopped = current.pop().map(tuple -> {
                return foldSingleQuotes(tuple)
                        .or(() -> foldDoubleQuotes(tuple))
                        .orElseGet(() -> folder.apply(tuple.right, tuple.left));
            });

            if (maybePopped.isPresent()) {
                current = maybePopped.orElse(current);
            }
            else {
                break;
            }
        } */
        return /* current.advance().segments */;
    }
    /* private static */ foldDoubleQuotes(tuple) {
        return /* new None<>() */;
    }
    /* private static */ foldSingleQuotes(tuple) {
        return /* new None<>() */;
    }
    /* private static */ foldStatementChar(state, c) {
        let append = state.append(c); /* if (c == ';' && append.isLevel()) {
            return append.advance();
        } */ /* if (c == '}' && append.isShallow()) {
            return append.advance().exit();
        } */ /* if (c == '{' || c == '(') {
            return append.enter();
        } */ /* if (c == '}' || c == ')') {
            return append.exit();
        } */
        return append;
    }
    /* private static */ compileRootSegment(state, input) {
        let stripped = input.strip(); /* if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        } */
        return; /* compileClass(stripped, 0, state)
                 */
        orElseGet(() -  > new Tuple(state, generatePlaceholder(stripped)));
    }
    /* private static */ compileClass(stripped, depth, state) {
        return /* structure(stripped, "class ", "class ", state) */;
    }
    /* private static */ structure(stripped, sourceInfix, targetInfix, state) {
        return; /* first(stripped, sourceInfix, (beforeInfix, right) -> {
            return first(right, "{", (beforeContent, withEnd) -> {
                var strippedWithEnd = withEnd */
        strip();
        return suffix(strippedWithEnd, "}", content1 -  > {
            return: first(beforeContent, "<", new BiFunction(), {}, (), Option < Tuple < CompileState, String >> apply(String, name, String, withTypeParams), {
                return: first(withTypeParams, ">", new BiFunction(), {}, (), Option < Tuple < CompileState, String >> apply(String, typeParamsString, String, afterTypeParams), {
                    var: typeParams = parseValues(state, typeParamsString, (state1, s) -  > new Tuple(state1, s.strip())),
                    return: assemble(typeParams.left, targetInfix, beforeInfix, name, content1, typeParams.right, afterTypeParams)
                })
            })
        });
    }
    or() { }
}
() -  > {
    return: assemble(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "")
};
;
;
;
;
/* private static */ assemble(state, CompileState, targetInfix, string, beforeInfix, string, rawName, string, content, string, typeParams, (List), afterTypeParams, string);
Option < [CompileState, string] > {
    let, name: , var:  = rawName.strip(), /* if (!isSymbol(name)) {
        return new None<>();
    } */
    let, joinedTypeParams: , var:  = typeParams.iterate().collect(new Joiner(", ")).map(inner -  > "<" + inner + /* ">") */ .orElse("")),
    let, statements: , var:  =  /*  compileStatements(state, content, (state0, input) -> compileClassSegment(state0, input, 1)) */,
    let, generated: , var:  = /* generatePlaceholder(beforeInfix */ .strip()
} + targetInfix + name + joinedTypeParams + /*  generatePlaceholder(afterTypeParams)  */ +" {" + /*  statements.right  */ +"\n}\n";
return /* new Some<>(new Tuple<>(statements.left */ .addStructure(generated), "";
;
/* private static */ isSymbol(input, string);
boolean;
{ /* for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        if (Character.isLetter(c)) {
            continue;
        }
        return false;
    } */
    return true;
}
/* private static  */ suffix(input, string, suffix, string, mapper, (string) => Option);
Option < T > {
    let, slice: , var:  = input.substring(0, input.length() - suffix.length()),
    return: mapper.apply(slice)
};
/* private static */ compileClassSegment(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    return; /* compileWhitespace(input, state)
             */
    or(() -  > compileClass(input, depth, state)).or(() -  > structure(input, "interface ", "interface ", state)).or(() -  > structure(input, "record ", "class ", state)).or(() -  > method(state, input, depth)).or(() -  > compileDefinitionStatement(input, depth, state)).orElseGet(() -  > new Tuple(state, generatePlaceholder(input)));
}
/* private static */ compileWhitespace(input, string, state, CompileState);
Option < [CompileState, string] > {
    return:  /* new None<>() */
};
/* private static */ method(state, CompileState, input, string, depth, number);
Option < [CompileState, string] > {
    return /* first(input, "(", (definition, withParams) -> {
        return first(withParams, ")", (params, rawContent) -> {
            var definitionTuple = parseDefinition(state, definition)
                    .map(definition1 -> {
                        var paramsTuple = compileValues(state, params, Main::compileParameter);
                        var generated = definition1.right */: /* first(input, "(", (definition, withParams) -> {
        return first(withParams, ")", (params, rawContent) -> {
            var definitionTuple = parseDefinition(state, definition)
                    .map(definition1 -> {
                        var paramsTuple = compileValues(state, params, Main::compileParameter);
                        var generated = definition1.right */ 
        .generateWithParams("(" + /*  paramsTuple.right  */ +
        .left, generated)
};
orElseGet(() -  > new Tuple(state, generatePlaceholder(definition)));
var content = rawContent.strip();
var indent = createIndent(depth);
if (content.equals(";")) {
    var s = indent + /*  definitionTuple.right  */ +
        .left, s;
}
if (content.startsWith("{") && content.endsWith("}")) {
    var statementsTuple = compileStatements(definitionTuple.left, content.substring(1, content.length() - 1), (state1, input1) -  > compileFunctionalSegment(state1, input1, depth + /*  1));
    var generated = indent  */
        + +" {" + /*  statementsTuple.right  */ +indent + /* "}";
    return new Some<>(new Tuple<>(statementsTuple */
            .left, generated));
}
return new None();
;
;
/* private static */ compileFunctionalSegment(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let stripped = input.strip(); /* if (stripped.isEmpty()) {
        return new Tuple<>(state, "");
    } */
    return; /* suffix(stripped, ";", s -> {
        var tuple = statementValue(state, s);
        return new Some<>(new Tuple<>(tuple */
    left, createIndent(depth) + /*  tuple.right  */ +
        .orElseGet(() -  > {
        return: new Tuple(state, generatePlaceholder(stripped))
    });
}
/* private static */ statementValue(state, CompileState, input, string);
[CompileState, string];
{
    let stripped = input.strip(); /* if (stripped.startsWith("return ")) {
        var value = stripped.substring("return ".length());
        var tuple = value(state, value);
        return new Tuple<>(tuple.left, "return " + tuple.right);
    } */
    return; /* first(stripped, "=", (s, s2) -> {
        var definitionTuple = compileDefinition(state, s);
        var valueTuple = value(definitionTuple */
    left, s2;
    ;
    return new Some(new Tuple(valueTuple.left, "let " + /*  definitionTuple.right  */ +" = " + valueTuple.right));
}
orElseGet(() -  > {
    return: new Tuple(state, generatePlaceholder(stripped))
});
/* private static */ value(state, CompileState, input, string);
[CompileState, string];
{
    return; /* operation(state, input)
             */
    or(() -  > symbolValue(state, input)).or(() -  > stringValue(state, input)).or(() -  > dataAccess(state, input)).orElseGet(() -  > new Tuple(state, generatePlaceholder(input)));
}
/* private static */ dataAccess(state, CompileState, input, string);
Option < [CompileState, string] > {
    return /* last(input.strip(), ".", (parent, property) -> {
        var value = value(state, parent);
        if (isSymbol(property)) {
            return new None<>();
        }
        return new Some<>(new Tuple<>(value.left, value.right  */: /* last(input.strip(), ".", (parent, property) -> {
        var value = value(state, parent);
        if (isSymbol(property)) {
            return new None<>();
        }
        return new Some<>(new Tuple<>(value.left, value.right  */ +"." +
    /*  property));
}) */
};
/* private static */ stringValue(state, CompileState, input, string);
Option < [CompileState, string] > {
    let, stripped: , var:  = input.strip(), /* if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
        return new Some<>(new Tuple<>(state, stripped));
    } */
    return:  /* new None<>() */
};
/* private static */ symbolValue(state, CompileState, value, string);
Option < [CompileState, string] > {
    let, stripped: , var:  = value.strip(), /* if (isSymbol(stripped)) {
        return new Some<>(new Tuple<>(state, stripped));
    } */
    return:  /* new None<>() */
};
/* private static */ operation(state, CompileState, value, string);
Option < [CompileState, string] > {
    return /* first(value, " */: /* first(value, " */ + +" + " + rightTuple.right
};
;
/* private static */ compileValues(state, CompileState, params, string, mapper, (CompileState, string) => [CompileState, string]);
[CompileState, string];
{
    let parsed =  /*  parseValues(state, params, mapper) */;
    let generated = /* generateValues(parsed */ .right;
    return /* new Tuple<>(parsed */ .left, generated;
    ;
}
/* private static */ generateValues(elements, (List));
string;
{
    return /* generateAll(Main::mergeValues, elements) */;
}
/* private static */ parseValues(state, CompileState, input, string, mapper, (CompileState, string) => [CompileState, string]);
[CompileState, (List)];
{
    return /* parseAll(state, input, Main::foldValueChar, mapper) */;
}
/* private static */ compileParameter(state, CompileState, input, string);
[CompileState, string];
{ /* if (input.isBlank()) {
        return new Tuple<>(state, "");
    } */
    return /* compileDefinition(state, input) */;
}
/* private static */ compileDefinition(state, CompileState, input, string);
[CompileState, string];
{
    return; /* parseDefinition(state, input)
            .map((Tuple<CompileState, Definition> tuple) -> new Tuple<>(tuple.left, tuple.right */
    generate();
    orElseGet(() -  > new Tuple(state, generatePlaceholder(input)));
}
/* private static */ mergeValues(cache, StringBuilder, element, string);
StringBuilder;
{ /* if (cache.isEmpty()) {
        return cache.append(element);
    } */
    return cache.append(", ").append(element);
}
/* private static */ createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat(depth);
}
/* private static */ compileDefinitionStatement(input, string, depth, number, state, CompileState);
Option < [CompileState, string] > {
    return /* suffix(input */: /* suffix(input */ .strip(), ";": , withoutEnd
} -  > {
    return: parseDefinition(state, withoutEnd).map(result -  > {
        var: generated = createIndent(depth) + /* result.right */ .generate() + /* ";";
        return new Tuple<>(result */
                .left, generated
    })
};
;
;
/* private static */ parseDefinition(state, CompileState, input, string);
Option < [CompileState, Definition] > {
    return /* last(input */: /* last(input */ .strip(), " ": ,
}(beforeName, name) -  > {
    return: split(() -  > getStringStringTuple(beforeName), (beforeType, type) -  > {
        return: suffix(beforeType.strip(), ">", withoutTypeParamStart -  > {
            return: first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -  > {
                var: typeParams = parseValues(state, typeParamsString, (state1, s) -  > new Tuple(state1, s.strip())),
                return: assembleDefinition(typeParams.left, new Some(beforeTypeParams), name, typeParams.right, type)
            })
        }).or(() -  > {
            return: assembleDefinition(state, new Some(beforeType), name, Lists.empty(), type)
        })
    }).or(() -  > {
        return: assembleDefinition(state, new None(), name, Lists.empty(), beforeName)
    })
};
;
/* private static */ getStringStringTuple(beforeName, string);
Option < [string, string] > {
    let, divisions: , var:  =  /*  divideAll(beforeName, Main::foldTypeSeparator) */,
    return /* divisions.removeLast().map(removed -> {
        var left = removed.left */: /* divisions.removeLast().map(removed -> {
        var left = removed.left */ 
        .iterate().collect(new Joiner(" ")).orElse(""),
    var: right = removed.right,
    return: new Tuple(left, right)
};
;
/* private static */ foldTypeSeparator(state, DivideState, c, Character);
DivideState;
{ /* if (c == ' ' && state.isLevel()) {
        return state.advance();
    } */
    let appended = state.append(c); /* if (c == '<') {
        return appended.enter();
    } */ /* if (c == '>') {
        return appended.exit();
    } */
    return appended;
}
/* private static */ assembleDefinition(state, CompileState, beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
Option < [CompileState, Definition] > {
    let, type1: , var:  =  /*  type(state, type) */,
    let, node: , var:  = /* new Definition(beforeTypeParams, type1 */ .right, name, : .strip(), typeParams,
    return /* new Some<>(new Tuple<>(type1 */: /* new Some<>(new Tuple<>(type1 */ .left, node
};
/* private static */ foldValueChar(state, DivideState, c, char);
DivideState;
{ /* if (c == ',' && state.isLevel()) {
        return state.advance();
    } */
    let appended = state.append(c); /* if (c == '<') {
        return appended.enter();
    } */ /* if (c == '>') {
        return appended.exit();
    } */
    return appended;
}
/* private static */ type(state, CompileState, input, string);
[CompileState, string];
{
    let stripped = input.strip(); /* if (stripped.equals("int")) {
        return new Tuple<>(state, "number");
    } */ /* if (stripped.equals("String")) {
        return new Tuple<>(state, "string");
    } */ /* if (isSymbol(stripped)) {
        return new Tuple<>(state, stripped);
    } */
    return; /* template(state, input)
             */
    or(() -  > varArgs(state, input)).orElseGet(() -  > new Tuple(state, generatePlaceholder(stripped)));
}
/* private static */ varArgs(state, CompileState, input, string);
Option < [CompileState, string] > {
    return /* suffix(input, "...", s -> {
        var inner = type(state, s);
        return new Some<>(new Tuple<>(inner.left, inner.right  */: /* suffix(input, "...", s -> {
        var inner = type(state, s);
        return new Some<>(new Tuple<>(inner.left, inner.right  */ + /*  "[]"));
}) */
};
/* private static */ template(state, CompileState, input, string);
Option < [CompileState, string] > {
    return /* suffix(input */: /* suffix(input */ .strip(), ">": , withoutEnd
} -  > {
    return: first(withoutEnd, "<", (base, argumentsString) -  > {
        var: strippedBase = base.strip(),
        var: argumentsTuple = parseValues(state, argumentsString, Main, type),
        var: argumentsState = argumentsTuple.left,
        var: arguments = argumentsTuple.right,
        if(base) { }, : .equals("BiFunction")
    })
};
{
    return new Some(new Tuple(argumentsState, generate(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2))));
}
if (base.equals("Function")) {
    return new Some(new Tuple(argumentsState, generate(Lists.of(arguments.get(0)), arguments.get(1))));
}
if (base.equals("Predicate")) {
    return new Some(new Tuple(argumentsState, generate(Lists.of(arguments.get(0)), "boolean")));
}
if (base.equals("Supplier")) {
    return new Some(new Tuple(argumentsState, generate(Lists.empty(), arguments.get(0))));
}
if (base.equals("Tuple")) {
    return new Some(new Tuple(argumentsState, "[" + arguments.get(0) + ", " + arguments.get(1) + /*  "]"));
}

return new Some<>(new Tuple<>(argumentsState, strippedBase  */
        +"<" + /*  generateValues(arguments)  */ +)) /*  ">"));
});
}) */;
}
/* private static */ generate(arguments, (List), returns, string);
string;
{
    let joined = arguments.iterate().collect(new Joiner(", ")).orElse("");
    return "(" + joined + ") => " + returns;
}
/* private static  */ last(input, string, infix, string, mapper, (string, string) => Option);
Option < T > {
    return:  /* infix(input, infix, Main::findLast, mapper) */
};
/* private static */ findLast(input, string, infix, string);
Option < Integer > {
    let, index: , var:  = input.lastIndexOf(infix),
    return:  /* index == -1 ? new None<Integer>() : new Some<>(index) */
};
/* private static  */ first(input, string, infix, string, mapper, (string, string) => Option);
Option < T > {
    return:  /* infix(input, infix, Main::findFirst, mapper) */
};
/* private static  */ infix(input, string, infix, string, locator, (string, string) => Option, mapper, (string, string) => Option);
Option < T > {
    return /* split(() -> locator */: /* split(() -> locator */ .apply(input, infix).map(index -  > {
        var: left = input.substring(0, index),
        var: right = input.substring(index + infix.length()),
        return: new Tuple(left, right)
    }), mapper
};
/* private static  */ split(splitter, () => Option, mapper, (string, string) => Option);
Option < T > {
    return: splitter.get().flatMap(tuple -  > mapper.apply(tuple.left, tuple.right))
};
/* private static */ findFirst(input, string, infix, string);
Option < Integer > {
    let, index: , var:  = input.indexOf(infix),
    return:  /* index == -1 ? new None<Integer>() : new Some<>(index) */
};
/* private static */ generatePlaceholder(input, string);
string;
{
    let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
    return "/* " + replaced + " */";
}
/*  */ 
