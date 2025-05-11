"use strict";
/* private */ class Some {
    constructor(value) {
    }
    /* @Override
        public  */ map(mapper) {
        return new Some(mapper /*  : (arg0 : T) => R */(value) /* : R */) /* : Some */;
    }
    /* @Override
        public */ isPresent() {
        return /* true */;
    }
    /* @Override
        public */ orElse(other) {
        return /* this */ .value;
    }
    /* @Override
        public */ filter(predicate) {
        /* if (predicate.test(this.value))  */ {
            return /* this */;
        }
        return new None( /*  */) /* : None */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return /* this */ .value;
    }
    /* @Override
        public */ or(other) {
        return /* this */;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return mapper /*  : (arg0 : T) => Option<R> */(value) /* : Option<R> */;
    }
    /* @Override
        public */ isEmpty() {
        return /* false */;
    }
}
/* private static */ class None {
    /* @Override
        public  */ map(mapper) {
        return new None( /*  */) /* : None */;
    }
    /* @Override
        public */ isPresent() {
        return /* false */;
    }
    /* @Override
        public */ orElse(other) {
        return other /*  : T */;
    }
    /* @Override
        public */ filter(predicate) {
        return new None( /*  */) /* : None */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return supplier /*  : () => T */( /*  */) /* : T */;
    }
    /* @Override
        public */ or(other) {
        return other /*  : () => Option<T> */( /*  */) /* : Option<T> */;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return new None( /*  */) /* : None */;
    }
    /* @Override
        public */ isEmpty() {
        return /* true */;
    }
}
/* private static */ class SingleHead {
    SingleHead(value) {
        let /* this.value  */ = value /*  : T */;
        let /* this.retrieved  */ =  /* false */;
    }
    /* @Override
        public */ next() {
        /* if (this.retrieved)  */ {
            return new None( /*  */) /* : None */;
        }
        let /* this.retrieved  */ =  /* true */;
        return new Some(value) /* : Some */;
    }
}
/* private static */ class EmptyHead {
    /* @Override
        public */ next() {
        return new None( /*  */) /* : None */;
    }
}
/* private */ class HeadedIterator {
    constructor(head) {
    }
    /* @Override
        public  */ fold(initial, folder) {
        let current = initial /*  : R */;
        /* while (true)  */ {
            let finalCurrent =  /* current */;
            let optional = /* this */ .head.next( /*  */) /* : unknown */.map((inner) => folder /*  : (arg0 : R, arg1 : T) => R */( /* finalCurrent */) /* : R */) /* : unknown */;
            /* if (optional.isPresent())  */ {
                let /* current  */ = /* optional */ .orElse( /* null */) /* : unknown */;
            }
            /* else  */ {
                return /* current */;
            }
        }
    }
    /* @Override
        public  */ map(mapper) {
        return new HeadedIterator(() => /* this */ .head.next( /*  */) /* : unknown */.map(mapper /*  : (arg0 : T) => R */) /* : unknown */) /* : HeadedIterator */;
    }
    /* @Override
        public  */ collect(collector) {
        return /* this */ .fold(collector /*  : Collector<T, R> */.createInitial( /*  */) /* : unknown */, collector /*  : Collector<T, R> */.fold) /* : unknown */;
    }
    /* @Override
        public */ filter(predicate) {
        return /* this */ .flatMap((element) => {
            /* if (predicate.test(element))  */ {
                return new HeadedIterator(new SingleHead( /* element */) /* : SingleHead */) /* : HeadedIterator */;
            }
            return new HeadedIterator(new EmptyHead( /*  */) /* : EmptyHead */) /* : HeadedIterator */;
        }) /* : unknown */;
    }
    /* @Override
        public */ next() {
        return /* this */ .head.next( /*  */) /* : unknown */;
    }
    /* @Override
        public  */ flatMap(f) {
        return new HeadedIterator(new FlatMapHead(head, f /*  : (arg0 : T) => Iterator<R> */) /* : FlatMapHead */) /* : HeadedIterator */;
    }
}
/* private static */ class RangeHead /*  */ {
    RangeHead(length) {
        let /* this.length  */ = length /*  : number */;
    }
    /* @Override
        public */ next() {
        /* if (this.counter < this.length)  */ {
            let value = /* this */ .counter;
            /* this.counter++ */ ;
            return new Some( /* value */) /* : Some */;
        }
        return new None( /*  */) /* : None */;
    }
}
/* private static final */ class JVMList {
    JVMList(elements) {
        let /* this.elements  */ = elements /*  : java.util.List<T> */;
    }
    JVMList() {
        /* this(new ArrayList<>()) */ ;
    }
    /* @Override
            public */ addLast(element) {
        /* this.elements.add(element) */ ;
        return /* this */;
    }
    /* @Override
            public */ iterate() {
        return /* this */ .iterateWithIndices( /*  */) /* : unknown */.map(right) /* : unknown */;
    }
    /* @Override
            public */ removeLast() {
        /* if (this.elements.isEmpty())  */ {
            return new None( /*  */) /* : None */;
        }
        let slice = /* this */ .elements.subList(0 /*  : number */, elements.size( /*  */) /* : unknown */ - 1 /*  : number */) /* : unknown */;
        let last = /* this */ .elements.getLast( /*  */) /* : unknown */;
        return new Some(new [(List), T](new JVMList( /* slice */) /* : JVMList */) /* : [List<T>, T] */) /* : Some */;
    }
    /* @Override
            public */ get(index) {
        return /* this */ .elements.get(index /*  : number */) /* : unknown */;
    }
    /* @Override
            public */ size() {
        return /* this */ .elements.size( /*  */) /* : unknown */;
    }
    /* @Override
            public */ isEmpty() {
        return /* this */ .elements.isEmpty( /*  */) /* : unknown */;
    }
    /* @Override
            public */ addFirst(element) {
        /* this.elements.addFirst(element) */ ;
        return /* this */;
    }
    /* @Override
            public */ iterateWithIndices() {
        return new HeadedIterator(new RangeHead(elements.size( /*  */) /* : unknown */) /* : RangeHead */) /* : HeadedIterator */.map((index) => new Tuple(elements.get( /* index */) /* : unknown */) /* : Tuple */) /* : unknown */;
    }
    /* @Override
            public */ removeFirst() {
        /* if (this.elements.isEmpty())  */ {
            return new None( /*  */) /* : None */;
        }
        let first = /* this */ .elements.getFirst( /*  */) /* : unknown */;
        let slice = /* this */ .elements.subList(1 /*  : number */, elements.size( /*  */) /* : unknown */) /* : unknown */;
        return new Some(new [T, (List)](new JVMList( /* slice */) /* : JVMList */) /* : [T, List<T>] */) /* : Some */;
    }
}
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return new JVMList( /*  */) /* : JVMList */;
    }
    /* public static  */ of(elements) {
        return new JVMList(new ArrayList(asList(elements /*  : T[] */) /* : unknown */) /* : ArrayList */) /* : JVMList */;
    }
}
/* private static */ class DivideState /*  */ {
    DivideState(input, index, segments, buffer, depth) {
        let /* this.segments  */ = segments /*  : List<string> */;
        let /* this.buffer  */ = buffer /*  : StringBuilder */;
        let /* this.depth  */ = depth /*  : number */;
        let /* this.input  */ = input /*  : string */;
        let /* this.index  */ = index /*  : number */;
    }
    DivideState(input) {
        /* this(input, 0, Lists.empty(), new StringBuilder(), 0) */ ;
    }
    /* private */ advance() {
        let /* this.segments  */ = /* this */ .segments.addLast(buffer.toString( /*  */) /* : unknown */) /* : unknown */;
        let /* this.buffer  */ = new StringBuilder( /*  */) /* : StringBuilder */;
        return /* this */;
    }
    /* private */ append(c) {
        /* this.buffer.append(c) */ ;
        return /* this */;
    }
    /* public */ enter() {
        /* this.depth++ */ ;
        return /* this */;
    }
    /* public */ isLevel() {
        return /* this.depth == 0 */;
    }
    /* public */ exit() {
        /* this.depth-- */ ;
        return /* this */;
    }
    /* public */ isShallow() {
        return /* this.depth == 1 */;
    }
    /* public */ pop() {
        /* if (this.index < this.input.length())  */ {
            let c = /* this */ .input.charAt(index) /* : unknown */;
            return new Some(new Tuple(new DivideState(input, index + 1 /*  : number */, segments, buffer, depth) /* : DivideState */) /* : Tuple */) /* : Some */;
        }
        return new None( /*  */) /* : None */;
    }
    /* public */ popAndAppendToTuple() {
        return /* this */ .pop( /*  */) /* : unknown */.map((tuple) => new Tuple(left, right.append(left) /* : unknown */) /* : Tuple */) /* : unknown */;
    }
    /* public */ popAndAppendToOption() {
        return /* this */ .popAndAppendToTuple( /*  */) /* : unknown */.map(right) /* : unknown */;
    }
    /* public */ peek() {
        return /* this */ .input.charAt(index) /* : unknown */;
    }
}
/* private */ class Joiner /*  */ {
    constructor(delimiter) {
    }
    Joiner() {
        /* this("") */ ;
    }
    /* @Override
        public */ createInitial() {
        return new None( /*  */) /* : None */;
    }
    /* @Override
        public */ fold(current, element) {
        return new Some(current /*  : Option<string> */.map((inner) => /* inner */ +.delimiter + element /*  : string */) /* : unknown */.orElse(element /*  : string */) /* : unknown */) /* : Some */;
    }
}
/* private */ class Definition /*  */ {
    constructor(maybeBefore, type, name, typeParams) {
    }
    /* private */ generate() {
        return /* this */ .generateWithParams("") /* : unknown */;
    }
    /* public */ generateWithParams(params) {
        let joined = /* this */ .joinTypeParams( /*  */) /* : unknown */;
        let before = /* this */ .joinBefore( /*  */) /* : unknown */;
        let typeString = /* this */ .generateType( /*  */) /* : unknown */;
        return /* before */ +.name + /* joined */ +params /*  : string */ +  /* typeString */;
    }
    /* private */ generateType() {
        /* if (this.type.equals(Primitive.Unknown))  */ {
            return "";
        }
        return " : " + /* this */ .type.generate( /*  */) /* : unknown */;
    }
    /* private */ joinBefore() {
        return /* this */ .maybeBefore.filter((value) => !.isEmpty( /*  */) /* : unknown */) /* : unknown */.map(generatePlaceholder) /* : unknown */.map((inner) => /* inner */ +" ") /* : unknown */.orElse("") /* : unknown */;
    }
    /* private */ joinTypeParams() {
        return /* this */ .typeParams.iterate( /*  */) /* : unknown */.collect(new Joiner( /*  */) /* : Joiner */) /* : unknown */.map((inner) => "<" + inner + ">") /* : unknown */.orElse("") /* : unknown */;
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return /* Lists */ .empty( /*  */) /* : unknown */;
    }
    /* @Override
        public */ fold(current, element) {
        return current /*  : List<T> */.addLast(element /*  : T */) /* : unknown */;
    }
}
/* private */ class Tuple {
}
/* private static */ class FlatMapHead {
    FlatMapHead(head, mapper) {
        let /* this.mapper  */ = mapper /*  : (arg0 : T) => Iterator<R> */;
        let /* this.current  */ = new None( /*  */) /* : None */;
        let /* this.head  */ = head /*  : Head<T> */;
    }
    /* @Override
        public */ next() {
        /* while (true)  */ {
            /* if (this.current.isPresent())  */ {
                let inner = /* this */ .current.orElse( /* null */) /* : unknown */;
                let maybe = /* inner */ .next( /*  */) /* : unknown */;
                /* if (maybe.isPresent())  */ {
                    return /* maybe */;
                }
                /* else  */ {
                    let /* this.current  */ = new None( /*  */) /* : None */;
                }
            }
            let outer = /* this */ .head.next( /*  */) /* : unknown */;
            /* if (outer.isPresent())  */ {
                let /* this.current  */ = /* outer */ .map(mapper) /* : unknown */;
            }
            /* else  */ {
                return new None( /*  */) /* : None */;
            }
        }
    }
}
/* private */ class SymbolType /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .input;
    }
}
/* private static */ class ArrayType /*  */ {
    ArrayType(right) {
        let /* this.right  */ = right /*  : Type */;
    }
    /* @Override
        public */ generate() {
        return /* this */ .right.generate( /*  */) /* : unknown */ + "[]";
    }
}
/* private static */ class Whitespace /*  */ {
}
/* private static */ class Iterators /*  */ {
    /* public static  */ fromOption(option) {
        let single = option /*  : Option<T> */.map(new ) /* : unknown */;
        return new HeadedIterator(orElseGet(new ) /* : unknown */) /* : HeadedIterator */;
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments( /*  */) /* : unknown */.iterateWithIndices( /*  */) /* : unknown */.map((pair) => "arg" + /* pair */ .left + " : " + /* pair */ .right.generate( /*  */) /* : unknown */) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        return "(" + /* joined */ +") => " + /* this */ .returns.generate( /*  */) /* : unknown */;
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments.iterate( /*  */) /* : unknown */.map(generate) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        return "[" + joinedArguments + "]";
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments.iterate( /*  */) /* : unknown */.map(generate) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.map((inner) => "<" + inner + ">") /* : unknown */.orElse("") /* : unknown */;
        return /* this */ .base +  /* joinedArguments */;
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* generatePlaceholder */ ( /* this */.input) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class StringValue /*  */ {
    constructor(stripped) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class DataAccess /*  */ {
    constructor(parent, property) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent.generate( /*  */) /* : unknown */ + "." + /* this */ .property;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(type) {
    }
    /* @Override
        public */ generate() {
        return "new " + /* this */ .type.generate( /*  */) /* : unknown */;
    }
}
/* private */ class Operation /*  */ {
    constructor(left, infix, right) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .left.generate( /*  */) /* : unknown */ + " " + /* this */ .infix + " " + /* this */ .right.generate( /*  */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class Not /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return "!" + /* this */ .value.generate( /*  */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class BlockLambdaValue /*  */ {
    constructor(right, depth) {
    }
    /* @Override
        public */ generate() {
        return "{" + this.right + createIndent(this.depth) + "}";
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameterNames, body) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .parameterNames.iterate( /*  */) /* : unknown */.map((inner) => /* inner */ +" : unknown") /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        return "(" + /* joined */ +") => " + /* this */ .body.generate( /*  */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class Invokable /*  */ {
    constructor(caller, arguments, returnsType) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments.iterate( /*  */) /* : unknown */.map(generate) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        return /* this */ .caller.generate( /*  */) /* : unknown */ + "(" + /* joined */ +")" + /* generatePlaceholder */ (": " + /* this */ .returnsType.generate( /*  */) /* : unknown */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent.generate( /*  */) /* : unknown */ + "[" + this.child.generate() + "]";
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class SymbolValue /*  */ {
    constructor(stripped, type) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped + /* generatePlaceholder */ (" : " + /* this */ .type.generate( /*  */) /* : unknown */) /* : unknown */;
    }
}
/* public */ class Main /*  */ {
    /* private */ CompileState(structures, definitions) {
        /* public CompileState()  */ {
            /* this(Lists.empty(), Lists.empty()) */ ;
        }
        /* private Option<Type> resolve(String name)  */ {
            return /* this */ .definitions.iterate( /*  */) /* : unknown */.filter((definition) => /* definition */ .name.equals( /* name */) /* : unknown */) /* : unknown */.next( /*  */) /* : unknown */.map(type) /* : unknown */;
        }
        /* public CompileState addStructure(String structure)  */ {
            return new CompileState(structures.addLast( /* structure */) /* : unknown */, definitions) /* : CompileState */;
        }
        /* public CompileState withDefinitions(List<Definition> definitions)  */ {
            return new CompileState(structures, definitions /*  : List<Definition> */) /* : CompileState */;
        }
    }
    /* public static */ main() {
        /* try  */ {
            let parent = /* Paths */ .get(".", "src", "java", "magma") /* : unknown */;
            let source = /* parent */ .resolve("Main.java") /* : unknown */;
            let target = /* parent */ .resolve("main.ts") /* : unknown */;
            let input = /* Files */ .readString( /* source */) /* : unknown */;
            /* Files.writeString(target, compile(input)) */ ;
            /* new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor() */ ;
        }
        /* catch (IOException | InterruptedException e)  */ {
            /* throw new RuntimeException(e) */ ;
        }
    }
    /* private static */ compile(input) {
        let tuple = /* compileStatements */ (new CompileState( /*  */) /* : CompileState */, input /*  : string */, /* Main */ .compileRootSegment) /* : unknown */;
        let joined = /* tuple */ .left.structures.iterate( /*  */) /* : unknown */.collect(new Joiner( /*  */) /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        return /* joined */ +.right;
    }
    /* private static */ compileStatements(state, input, mapper) {
        let parsed = /* parseStatements */ (state /*  : CompileState */, input /*  : string */, mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */) /* : unknown */;
        return new Tuple(left, /* generateStatements */ ( /* parsed */.right) /* : unknown */) /* : Tuple */;
    }
    /* private static */ generateStatements(statements) {
        return /* generateAll */ ( /* Main */.mergeStatements, statements /*  : List<string> */) /* : unknown */;
    }
    /* private static */ parseStatements(state, input, mapper) {
        return /* parseAll */ (state /*  : CompileState */, input /*  : string */, /* Main */ .foldStatementChar, mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */) /* : unknown */;
    }
    /* private static */ generateAll(merger, elements) {
        return elements /*  : List<string> */.iterate( /*  */) /* : unknown */.fold(new StringBuilder( /*  */) /* : StringBuilder */, merger /*  : (arg0 : StringBuilder, arg1 : string) => StringBuilder */) /* : unknown */.toString( /*  */) /* : unknown */;
    }
    /* private static  */ parseAll(state, input, folder, mapper) {
        return /* getCompileStateListTuple */ (state /*  : CompileState */, input /*  : string */, folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */, (state1, s) => new Some(mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, T] */( /* state1 */) /* : [CompileState, T] */) /* : Some */) /* : unknown */.orElseGet(() => new Tuple(state /*  : CompileState */, empty( /*  */) /* : unknown */) /* : Tuple */) /* : unknown */;
    }
    /* private static  */ getCompileStateListTuple(state, input, folder, mapper) {
        let initial = new Some(new Tuple(state /*  : CompileState */, empty( /*  */) /* : unknown */) /* : Tuple */) /* : Some */;
        return /* divideAll */ (input /*  : string */, folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */) /* : unknown */.iterate( /*  */) /* : unknown */.fold((tuple, element) => {
            return /* tuple */ .flatMap((inner) => {
                let state1 = /* inner */ .left;
                let right = /* inner */ .right;
                return mapper /*  : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */( /* state1 */) /* : Option<[CompileState, T]> */.map((applied) => {
                    return new Tuple(left, addLast(right) /* : unknown */) /* : Tuple */;
                }) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ mergeStatements(stringBuilder, str) {
        return stringBuilder /*  : StringBuilder */.append(str /*  : string */) /* : unknown */;
    }
    /* private static */ divideAll(input, folder) {
        let current = new DivideState(input /*  : string */) /* : DivideState */;
        /* while (true)  */ {
            let maybePopped = /* current */ .pop( /*  */) /* : unknown */.map((tuple) => {
                return /* foldSingleQuotes */ ( /* tuple */) /* : unknown */.or(() => /* foldDoubleQuotes */ ( /* tuple */) /* : unknown */) /* : unknown */.orElseGet(() => folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */(right, left) /* : DivideState */) /* : unknown */;
            }) /* : unknown */;
            /* if (maybePopped.isPresent())  */ {
                let /* current  */ = /* maybePopped */ .orElse( /* current */) /* : unknown */;
            }
            /* else  */ {
                /* break */ ;
            }
        }
        return /* current */ .advance( /*  */) /* : unknown */.segments;
    }
    /* private static */ foldDoubleQuotes(tuple) {
        /* if (tuple.left == '\"')  */ {
            let current = tuple /*  : [Character, DivideState] */.right.append(tuple /*  : [Character, DivideState] */[0 /*  : number */]) /* : unknown */;
            /* while (true)  */ {
                let maybePopped = /* current */ .popAndAppendToTuple( /*  */) /* : unknown */;
                /* if (maybePopped.isEmpty())  */ {
                    /* break */ ;
                }
                let popped = /* maybePopped */ .orElse( /* null */) /* : unknown */;
                let /* current  */ = /* popped */ .right;
                /* if (popped.left == '\\')  */ {
                    let /* current  */ = /* current */ .popAndAppendToOption( /*  */) /* : unknown */.orElse( /* current */) /* : unknown */;
                }
                /* if (popped.left == '\"')  */ {
                    /* break */ ;
                }
            }
            return new Some( /* current */) /* : Some */;
        }
        return new None( /*  */) /* : None */;
    }
    /* private static */ foldSingleQuotes(tuple) {
        /* if (tuple.left != '\'')  */ {
            return new None( /*  */) /* : None */;
        }
        let appended = tuple /*  : [Character, DivideState] */.right.append(tuple /*  : [Character, DivideState] */[0 /*  : number */]) /* : unknown */;
        return /* appended */ .popAndAppendToTuple( /*  */) /* : unknown */.map(foldEscaped) /* : unknown */.flatMap(popAndAppendToOption) /* : unknown */;
    }
    /* private static */ foldEscaped(escaped) {
        /* if (escaped.left == '\\')  */ {
            return escaped /*  : [Character, DivideState] */.right.popAndAppendToOption( /*  */) /* : unknown */.orElse(escaped /*  : [Character, DivideState] */.right) /* : unknown */;
        }
        return escaped /*  : [Character, DivideState] */.right;
    }
    /* private static */ foldStatementChar(state, c) {
        let append = state /*  : DivideState */.append(c /*  : char */) /* : unknown */;
        /* if (c == ';' && append.isLevel())  */ {
            return /* append */ .advance( /*  */) /* : unknown */;
        }
        /* if (c == '}' && append.isShallow())  */ {
            return /* append */ .advance( /*  */) /* : unknown */.exit( /*  */) /* : unknown */;
        }
        /* if (c == '{' || c == '(')  */ {
            return /* append */ .enter( /*  */) /* : unknown */;
        }
        /* if (c == '}' || c == ')')  */ {
            return /* append */ .exit( /*  */) /* : unknown */;
        }
        return /* append */;
    }
    /* private static */ compileRootSegment(state, input) {
        let stripped = input /*  : string */.strip( /*  */) /* : unknown */;
        /* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */ {
            return new Tuple(state /*  : CompileState */, "") /* : Tuple */;
        }
        return /* compileClass */ ( /* stripped */, 0 /*  : number */, state /*  : CompileState */) /* : unknown */.orElseGet(() => new Tuple(state /*  : CompileState */, /* generatePlaceholder */ ( /* stripped */) /* : unknown */) /* : Tuple */) /* : unknown */;
    }
    /* private static */ compileClass(stripped, depth, state) {
        return /* structure */ (stripped /*  : string */, "class ", "class ", state /*  : CompileState */) /* : unknown */;
    }
    /* private static */ structure(stripped, sourceInfix, targetInfix, state) {
        return /* first */ (stripped /*  : string */, sourceInfix /*  : string */, (beforeInfix, right) => {
            return /* first */ ( /* right */, "{", (beforeContent, withEnd) => {
                let strippedWithEnd = /* withEnd */ .strip( /*  */) /* : unknown */;
                return /* suffix */ ( /* strippedWithEnd */, "}", (content1) => {
                    return /* first */ ( /* beforeContent */, " implements ", (s, s2) => {
                        return /* structureWithMaybeParams */ (targetInfix /*  : string */, state /*  : CompileState */,  /* beforeInfix */,  /* s */,  /* content1 */) /* : unknown */;
                    }) /* : unknown */.or(() => {
                        return /* structureWithMaybeParams */ (targetInfix /*  : string */, state /*  : CompileState */,  /* beforeInfix */,  /* beforeContent */,  /* content1 */) /* : unknown */;
                    }) /* : unknown */;
                }) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ structureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1) {
        return /* suffix */ (beforeContent /*  : string */, ")", (s) => {
            return /* first */ ( /* s */, "(", (s1, s2) => {
                let parsed = /* parseParameters */ (state /*  : CompileState */,  /* s2 */) /* : unknown */;
                return /* getOred */ (targetInfix /*  : string */, /* parsed */ .left, beforeInfix /*  : string */,  /* s1 */, content1 /*  : string */, /* parsed */ .right) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */.or(() => {
            return /* getOred */ (targetInfix /*  : string */, state /*  : CompileState */, beforeInfix /*  : string */, beforeContent /*  : string */, content1 /*  : string */, /* Lists */ .empty( /*  */) /* : unknown */) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ getOred(targetInfix, state, beforeInfix, beforeContent, content1, params) {
        return /* first */ (beforeContent /*  : string */, "<", (name, withTypeParams) => {
            return /* first */ ( /* withTypeParams */, ">", (typeParamsString, afterTypeParams) => {
                let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple(strip( /*  */) /* : unknown */) /* : Tuple */;
                let typeParams = /* parseValuesOrEmpty */ (state /*  : CompileState */,  /* typeParamsString */, (state1, s) => new Some(apply( /* state1 */) /* : unknown */) /* : Some */) /* : unknown */;
                return /* assemble */ ( /* typeParams */.left, targetInfix /*  : string */, beforeInfix /*  : string */,  /* name */, content1 /*  : string */, /* typeParams */ .right,  /* afterTypeParams */, params /*  : List<Parameter> */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */.or(() => {
            return /* assemble */ (state /*  : CompileState */, targetInfix /*  : string */, beforeInfix /*  : string */, beforeContent /*  : string */, content1 /*  : string */, /* Lists */ .empty( /*  */) /* : unknown */, "", params /*  : List<Parameter> */) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ assemble(state, targetInfix, beforeInfix, rawName, content, typeParams, afterTypeParams, params) {
        let name = rawName /*  : string */.strip( /*  */) /* : unknown */;
        /* if (!isSymbol(name))  */ {
            return new None( /*  */) /* : None */;
        }
        let joinedTypeParams = typeParams /*  : List<string> */.iterate( /*  */) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.map((inner) => "<" + inner + ">") /* : unknown */.orElse("") /* : unknown */;
        let parsed = /* parseStatements */ (state /*  : CompileState */, content /*  : string */, (state0, input) => /* compileClassSegment */ ( /* state0 */,  /* input */, 1 /*  : number */) /* : unknown */) /* : unknown */;
        /* List<String> parsed1 */ ;
        /* if (params.isEmpty())  */ {
            let /* parsed1  */ = /* parsed */ .right;
        }
        /* else  */ {
            let joined = /* joinValues */ ( /* retainDefinitions */(params /*  : List<Parameter> */) /* : unknown */) /* : unknown */;
            let constructorIndent = /* createIndent */ (1 /*  : number */) /* : unknown */;
            let /* parsed1  */ = /* parsed */ .right.addFirst(/* constructorIndent */ +"constructor (" + joined + ") {" + constructorIndent + "}\n") /* : unknown */;
        }
        let parsed2 = /* parsed1 */ .iterate( /*  */) /* : unknown */.collect(new Joiner( /*  */) /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        let generated = /* generatePlaceholder */ (beforeInfix /*  : string */.strip( /*  */) /* : unknown */) /* : unknown */ + targetInfix /*  : string */ + /* name */ + +(afterTypeParams /*  : string */) /* : unknown */ + " {" + parsed2 + "\n}\n";
        return new Some(new Tuple(left.addStructure( /* generated */) /* : unknown */, "") /* : Tuple */) /* : Some */;
    }
    /* private static */ retainDefinition(parameter) {
        /* if (parameter instanceof Definition definition)  */ {
            return new Some( /* definition */) /* : Some */;
        }
        return new None( /*  */) /* : None */;
    }
    /* private static */ isSymbol(input) {
        /* for (var i = 0; i < input.length(); i++)  */ {
            let c = input /*  : string */.charAt( /* i */) /* : unknown */;
            /* if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */ {
                /* continue */ ;
            }
            return /* false */;
        }
        return /* true */;
    }
    /* private static  */ suffix(input, suffix, mapper) {
        /* if (!input.endsWith(suffix))  */ {
            return new None( /*  */) /* : None */;
        }
        let slice = input /*  : string */.substring(0 /*  : number */, input /*  : string */.length( /*  */) /* : unknown */ - suffix /*  : string */.length( /*  */) /* : unknown */) /* : unknown */;
        return mapper /*  : (arg0 : string) => Option<T> */( /* slice */) /* : Option<T> */;
    }
    /* private static */ compileClassSegment(state, input, depth) {
        return /* compileWhitespace */ (input /*  : string */, state /*  : CompileState */) /* : unknown */.or(() => /* compileClass */ (input /*  : string */, depth /*  : number */, state /*  : CompileState */) /* : unknown */) /* : unknown */.or(() => /* structure */ (input /*  : string */, "interface ", "interface ", state /*  : CompileState */) /* : unknown */) /* : unknown */.or(() => /* structure */ (input /*  : string */, "record ", "class ", state /*  : CompileState */) /* : unknown */) /* : unknown */.or(() => /* compileMethod */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */) /* : unknown */.or(() => /* compileDefinitionStatement */ (input /*  : string */, depth /*  : number */, state /*  : CompileState */) /* : unknown */) /* : unknown */.orElseGet(() => new Tuple(state /*  : CompileState */, /* generatePlaceholder */ (input /*  : string */) /* : unknown */) /* : Tuple */) /* : unknown */;
    }
    /* private static */ compileWhitespace(input, state) {
        /* if (input.isBlank())  */ {
            return new Some(new Tuple(state /*  : CompileState */, "") /* : Tuple */) /* : Some */;
        }
        return new None( /*  */) /* : None */;
    }
    /* private static */ compileMethod(state, input, depth) {
        return /* first */ (input /*  : string */, "(", (definitionString, withParams) => {
            return /* first */ ( /* withParams */, ")", (parametersString, rawContent) => {
                return /* parseDefinition */ (state /*  : CompileState */,  /* definitionString */) /* : unknown */.flatMap((definitionTuple) => {
                    let definitionState = /* definitionTuple */ .left;
                    let definition = /* definitionTuple */ .right;
                    let parametersTuple = /* parseParameters */ ( /* definitionState */,  /* parametersString */) /* : unknown */;
                    let parameters = /* parametersTuple */ .right;
                    let definitions = /* retainDefinitions */ ( /* parameters */) /* : unknown */;
                    let joinedParameters = /* joinValues */ ( /* definitions */) /* : unknown */;
                    let content = /* rawContent */ .strip( /*  */) /* : unknown */;
                    let indent = /* createIndent */ (depth /*  : number */) /* : unknown */;
                    let generatedHeader = /* definition */ .generateWithParams("(" + joinedParameters + ")") /* : unknown */;
                    /* if (content.equals(";"))  */ {
                        return new Some(new Tuple(left, /* indent */ + +";") /* : Tuple */) /* : Some */;
                    }
                    /* if (content.startsWith("{") && content.endsWith("}"))  */ {
                        let substring = /* content */ .substring(1 /*  : number */, length( /*  */) /* : unknown */ - 1 /*  : number */) /* : unknown */;
                        let statementsTuple = /* compileFunctionSegments */ ( /* parametersTuple */.left.withDefinitions( /* definitions */) /* : unknown */,  /* substring */, depth /*  : number */) /* : unknown */;
                        let generated = /* indent */ + +" {" + statementsTuple.right + indent + "}";
                        return new Some(new Tuple(left) /* : Tuple */) /* : Some */;
                    }
                    return new None( /*  */) /* : None */;
                }) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ joinValues(retainParameters) {
        return retainParameters /*  : List<Definition> */.iterate( /*  */) /* : unknown */.map(generate) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
    }
    /* private static */ retainDefinitions(right) {
        return right /*  : List<Parameter> */.iterate( /*  */) /* : unknown */.map(retainDefinition) /* : unknown */.flatMap(fromOption) /* : unknown */.collect(new ListCollector( /*  */) /* : ListCollector */) /* : unknown */;
    }
    /* private static */ parseParameters(state, params) {
        return /* parseValuesOrEmpty */ (state /*  : CompileState */, params /*  : string */, (state1, s) => new Some(/* compileParameter */ ( /* state1 */,  /* s */) /* : unknown */) /* : Some */) /* : unknown */;
    }
    /* private static */ compileFunctionSegments(state, input, depth) {
        return /* compileStatements */ (state /*  : CompileState */, input /*  : string */, (state1, input1) => /* compileFunctionSegment */ ( /* state1 */,  /* input1 */, depth /*  : number */ + 1 /*  : number */) /* : unknown */) /* : unknown */;
    }
    /* private static */ compileFunctionSegment(state, input, depth) {
        let stripped = input /*  : string */.strip( /*  */) /* : unknown */;
        /* if (stripped.isEmpty())  */ {
            return new Tuple(state /*  : CompileState */, "") /* : Tuple */;
        }
        return /* suffix */ ( /* stripped */, ";", (s) => {
            let tuple = /* statementValue */ (state /*  : CompileState */,  /* s */, depth /*  : number */) /* : unknown */;
            return new Some(new Tuple(left, /* createIndent */ (depth /*  : number */) /* : unknown */ + /* tuple */ .right + ";") /* : Tuple */) /* : Some */;
        }) /* : unknown */.or(() => {
            return /* block */ (state /*  : CompileState */, depth /*  : number */,  /* stripped */) /* : unknown */;
        }) /* : unknown */.orElseGet(() => {
            return new Tuple(state /*  : CompileState */, /* generatePlaceholder */ ( /* stripped */) /* : unknown */) /* : Tuple */;
        }) /* : unknown */;
    }
    /* private static */ block(state, depth, stripped) {
        return /* suffix */ (stripped /*  : string */, "}", (withoutEnd) => {
            return /* split */ (() => {
                let divisions = /* divideAll */ ( /* withoutEnd */, /* Main */ .foldBlockStart) /* : unknown */;
                return /* divisions */ .removeFirst( /*  */) /* : unknown */.map((removed) => {
                    let right = /* removed */ .left;
                    let left = /* removed */ .right.iterate( /*  */) /* : unknown */.collect(new Joiner("") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
                    return new Tuple( /* right */) /* : Tuple */;
                }) /* : unknown */;
            }, (beforeContent, content) => {
                return /* suffix */ ( /* beforeContent */, "{", (s) => {
                    let compiled = /* compileFunctionSegments */ (state /*  : CompileState */,  /* content */, depth /*  : number */) /* : unknown */;
                    let indent = /* createIndent */ (depth /*  : number */) /* : unknown */;
                    return new Some(new Tuple(left, /* indent */ +( /* s */) /* : unknown */ + "{" + compiled.right + indent + "}") /* : Tuple */) /* : Some */;
                }) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ foldBlockStart(state, c) {
        let appended = state /*  : DivideState */.append(c /*  : Character */) /* : unknown */;
        /* if (c == '{' && state.isLevel())  */ {
            return /* appended */ .advance( /*  */) /* : unknown */;
        }
        /* if (c == '{')  */ {
            return /* appended */ .enter( /*  */) /* : unknown */;
        }
        /* if (c == '}')  */ {
            return /* appended */ .exit( /*  */) /* : unknown */;
        }
        return /* appended */;
    }
    /* private static */ statementValue(state, input, depth) {
        let stripped = input /*  : string */.strip( /*  */) /* : unknown */;
        /* if (stripped.startsWith("return "))  */ {
            let value = /* stripped */ .substring("return ".length( /*  */) /* : unknown */) /* : unknown */;
            let tuple = /* compileValue */ (state /*  : CompileState */,  /* value */, depth /*  : number */) /* : unknown */;
            return new Tuple(left, "return " + /* tuple */ .right) /* : Tuple */;
        }
        return /* first */ ( /* stripped */, "=", (s, s2) => {
            let definitionTuple = /* compileDefinition */ (state /*  : CompileState */,  /* s */) /* : unknown */;
            let valueTuple = /* compileValue */ ( /* definitionTuple */.left,  /* s2 */, depth /*  : number */) /* : unknown */;
            return new Some(new Tuple(left, "let " + /* definitionTuple */ .right + " = " + /* valueTuple */ .right) /* : Tuple */) /* : Some */;
        }) /* : unknown */.orElseGet(() => {
            return new Tuple(state /*  : CompileState */, /* generatePlaceholder */ ( /* stripped */) /* : unknown */) /* : Tuple */;
        }) /* : unknown */;
    }
    /* private static */ compileValue(state, input, depth) {
        let tuple = /* parseValue */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */;
        return new Tuple(left, right.generate( /*  */) /* : unknown */) /* : Tuple */;
    }
    /* private static */ parseValue(state, input, depth) {
        return /* parseLambda */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */.or(() => /* parseString */ (state /*  : CompileState */, input /*  : string */) /* : unknown */) /* : unknown */.or(() => /* parseDataAccess */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */) /* : unknown */.or(() => /* parseSymbolValue */ (state /*  : CompileState */, input /*  : string */) /* : unknown */) /* : unknown */.or(() => /* parseInvocation */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */) /* : unknown */.or(() => /* parseOperation */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */, "+") /* : unknown */) /* : unknown */.or(() => /* parseOperation */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */, "-") /* : unknown */) /* : unknown */.or(() => /* parseDigits */ (state /*  : CompileState */, input /*  : string */) /* : unknown */) /* : unknown */.or(() => /* parseNot */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */) /* : unknown */.or(() => /* parseMethodReference */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */) /* : unknown */.orElseGet(() => new [CompileState, Value](state /*  : CompileState */, new Placeholder(input /*  : string */) /* : Placeholder */) /* : [CompileState, Value] */) /* : unknown */;
    }
    /* private static */ parseMethodReference(state, input, depth) {
        return /* last */ (input /*  : string */, "::", (s, s2) => {
            let tuple = /* parseValue */ (state /*  : CompileState */,  /* s */, depth /*  : number */) /* : unknown */;
            return new Some(new Tuple(left, new DataAccess(right) /* : DataAccess */) /* : Tuple */) /* : Some */;
        }) /* : unknown */;
    }
    /* private static */ parseNot(state, input, depth) {
        let stripped = input /*  : string */.strip( /*  */) /* : unknown */;
        /* if (stripped.startsWith("!"))  */ {
            let slice = /* stripped */ .substring(1 /*  : number */) /* : unknown */;
            let tuple = /* parseValue */ (state /*  : CompileState */,  /* slice */, depth /*  : number */) /* : unknown */;
            let value = /* tuple */ .right;
            return new Some(new Tuple(left, new Not( /* value */) /* : Not */) /* : Tuple */) /* : Some */;
        }
        return new None( /*  */) /* : None */;
    }
    /* private static */ parseLambda(state, input, depth) {
        return /* first */ (input /*  : string */, "->", (beforeArrow, valueString) => {
            let strippedBeforeArrow = /* beforeArrow */ .strip( /*  */) /* : unknown */;
            /* if (isSymbol(strippedBeforeArrow))  */ {
                return /* assembleLambda */ (state /*  : CompileState */, /* Lists */ .of( /* strippedBeforeArrow */) /* : unknown */,  /* valueString */, depth /*  : number */) /* : unknown */;
            }
            /* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */ {
                let parameterNames = /* divideAll */ ( /* strippedBeforeArrow */.substring(1 /*  : number */, length( /*  */) /* : unknown */ - 1 /*  : number */) /* : unknown */, /* Main */ .foldValueChar) /* : unknown */.iterate( /*  */) /* : unknown */.map(strip) /* : unknown */.filter((value) => !.isEmpty( /*  */) /* : unknown */) /* : unknown */.collect(new ListCollector( /*  */) /* : ListCollector */) /* : unknown */;
                return /* assembleLambda */ (state /*  : CompileState */,  /* parameterNames */,  /* valueString */, depth /*  : number */) /* : unknown */;
            }
            return new None( /*  */) /* : None */;
        }) /* : unknown */;
    }
    /* private static */ assembleLambda(state, paramNames, valueString, depth) {
        let strippedValueString = valueString /*  : string */.strip( /*  */) /* : unknown */;
        /* Tuple<CompileState, LambdaValue> value */ ;
        /* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */ {
            let value1 = /* compileStatements */ (state /*  : CompileState */, /* strippedValueString */ .substring(1 /*  : number */, length( /*  */) /* : unknown */ - 1 /*  : number */) /* : unknown */, (state1, input1) => /* compileFunctionSegment */ ( /* state1 */,  /* input1 */, depth /*  : number */ + 1 /*  : number */) /* : unknown */) /* : unknown */;
            let right = /* value1 */ .right;
            let /* value  */ = new Tuple(left, new BlockLambdaValue(depth /*  : number */) /* : BlockLambdaValue */) /* : Tuple */;
        }
        /* else  */ {
            let value1 = /* parseValue */ (state /*  : CompileState */,  /* strippedValueString */, depth /*  : number */) /* : unknown */;
            let /* value  */ = new Tuple(left, right) /* : Tuple */;
        }
        let right = /* value */ .right;
        return new Some(new Tuple(left, new Lambda(paramNames /*  : List<string> */) /* : Lambda */) /* : Tuple */) /* : Some */;
    }
    /* private static */ parseDigits(state, input) {
        let stripped = input /*  : string */.strip( /*  */) /* : unknown */;
        /* if (isNumber(stripped))  */ {
            return new Some(new [CompileState, Value](state /*  : CompileState */, new SymbolValue(Int) /* : SymbolValue */) /* : [CompileState, Value] */) /* : Some */;
        }
        return new None( /*  */) /* : None */;
    }
    /* private static */ isNumber(input) {
        /* for (var i = 0; i < input.length(); i++)  */ {
            let c = input /*  : string */.charAt( /* i */) /* : unknown */;
            /* if (Character.isDigit(c))  */ {
                /* continue */ ;
            }
            return /* false */;
        }
        return /* true */;
    }
    /* private static */ parseInvocation(state, input, depth) {
        return /* suffix */ (input /*  : string */.strip( /*  */) /* : unknown */, ")", (withoutEnd) => {
            return /* split */ (() => /* toLast */ ( /* withoutEnd */, "", /* Main */ .foldInvocationStart) /* : unknown */, (callerWithEnd, argumentsString) => {
                return /* suffix */ ( /* callerWithEnd */, "(", (callerString) => {
                    let callerString1 = /* callerString */ .strip( /*  */) /* : unknown */;
                    let callerTuple = /* invocationHeader */ (state /*  : CompileState */, depth /*  : number */,  /* callerString1 */) /* : unknown */;
                    let parsed = /* parseValues */ ( /* callerTuple */.left,  /* argumentsString */, (state3, s) => new Some(/* parseValue */ ( /* state3 */,  /* s */, depth /*  : number */) /* : unknown */) /* : Some */) /* : unknown */.orElseGet(() => new Tuple(left, empty( /*  */) /* : unknown */) /* : Tuple */) /* : unknown */;
                    let oldCaller = /* callerTuple */ .right;
                    let arguments = /* parsed */ .right;
                    let newCaller = /* modifyCaller */ ( /* parsed */.left,  /* oldCaller */) /* : unknown */;
                    let;
                    var Type = /* Primitive */ .Unknown;
                    /* switch (newCaller)  */ {
                        /* case ConstructionCaller constructionCaller ->  */ {
                            let /* var  */ = /* constructionCaller */ .type;
                        }
                        /* case Value value ->  */ {
                            let type = /* value */ .type( /*  */) /* : unknown */;
                            /* if (type instanceof FunctionType functionType)  */ {
                                let /* var  */ = /*  */ ( /* (FunctionType) type */) /* : unknown */.returns;
                            }
                        }
                    }
                    let invokable = new Invokable( /* newCaller */) /* : Invokable */;
                    return new Some(new Tuple(left) /* : Tuple */) /* : Some */;
                }) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ modifyCaller(state, oldCaller) {
        /* if (oldCaller instanceof DataAccess access)  */ {
            let type = /* resolveType */ ( /* access */.parent, state /*  : CompileState */) /* : unknown */;
            /* if (type instanceof FunctionType)  */ {
                return /* access */ .parent;
            }
        }
        return oldCaller /*  : Caller */;
    }
    /* private static */ resolveType(value, state) {
        /* return switch (value)  */ {
            /* case DataAccess dataAccess -> Primitive.Unknown */ ;
            /* case Invokable invokable -> Primitive.Unknown */ ;
            /* case Lambda lambda -> Primitive.Unknown */ ;
            /* case Not not -> Primitive.Unknown */ ;
            /* case Operation operation -> Primitive.Unknown */ ;
            /* case Placeholder placeholder -> Primitive.Unknown */ ;
            /* case StringValue stringValue -> Primitive.Unknown */ ;
            /* case SymbolValue symbolValue -> symbolValue.type */ ;
            /* case IndexValue indexValue -> Primitive.Unknown */ ;
        }
        /*  */ ;
    }
    /* private static */ invocationHeader(state, depth, callerString1) {
        /* if (callerString1.startsWith("new "))  */ {
            let input1 = callerString1 /*  : string */.substring("new ".length( /*  */) /* : unknown */) /* : unknown */;
            let map = /* parseType */ (state /*  : CompileState */,  /* input1 */) /* : unknown */.map((type) => {
                let right = /* type */ .right;
                return new [CompileState, Caller](left, new ConstructionCaller( /* right */) /* : ConstructionCaller */) /* : [CompileState, Caller] */;
            }) /* : unknown */;
            /* if (map.isPresent())  */ {
                return /* map */ .orElse( /* null */) /* : unknown */;
            }
        }
        let tuple = /* parseValue */ (state /*  : CompileState */, callerString1 /*  : string */, depth /*  : number */) /* : unknown */;
        return new Tuple(left, right) /* : Tuple */;
    }
    /* private static */ foldInvocationStart(state, c) {
        let appended = state /*  : DivideState */.append(c /*  : char */) /* : unknown */;
        /* if (c == '(')  */ {
            let enter = /* appended */ .enter( /*  */) /* : unknown */;
            /* if (enter.isShallow())  */ {
                return /* enter */ .advance( /*  */) /* : unknown */;
            }
            return /* enter */;
        }
        /* if (c == ')')  */ {
            return /* appended */ .exit( /*  */) /* : unknown */;
        }
        return /* appended */;
    }
    /* private static */ parseDataAccess(state, input, depth) {
        return /* last */ (input /*  : string */.strip( /*  */) /* : unknown */, ".", (parentString, rawProperty) => {
            let property = /* rawProperty */ .strip( /*  */) /* : unknown */;
            /* if (!isSymbol(property))  */ {
                return new None( /*  */) /* : None */;
            }
            let tuple = /* parseValue */ (state /*  : CompileState */,  /* parentString */, depth /*  : number */) /* : unknown */;
            let parent = /* tuple */ .right;
            let type = /* resolveType */ ( /* parent */, state /*  : CompileState */) /* : unknown */;
            /* if (type instanceof TupleType)  */ {
                /* if (property.equals("left"))  */ {
                    return new Some(new Tuple(state /*  : CompileState */, new IndexValue(new SymbolValue("0", Int) /* : SymbolValue */) /* : IndexValue */) /* : Tuple */) /* : Some */;
                }
                /* if (property.equals("type"))  */ {
                    return new Some(new Tuple(state /*  : CompileState */, new IndexValue(new SymbolValue("1", Int) /* : SymbolValue */) /* : IndexValue */) /* : Tuple */) /* : Some */;
                }
            }
            return new Some(new Tuple(left, new DataAccess( /* parent */) /* : DataAccess */) /* : Tuple */) /* : Some */;
        }) /* : unknown */;
    }
    /* private static */ parseString(state, input) {
        let stripped = input /*  : string */.strip( /*  */) /* : unknown */;
        /* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */ {
            return new Some(new Tuple(state /*  : CompileState */, new StringValue( /* stripped */) /* : StringValue */) /* : Tuple */) /* : Some */;
        }
        return new None( /*  */) /* : None */;
    }
    /* private static */ parseSymbolValue(state, value) {
        let stripped = value /*  : string */.strip( /*  */) /* : unknown */;
        /* if (isSymbol(stripped))  */ {
            /* if (state.resolve(stripped) instanceof Some(var type))  */ {
                return new Some(new Tuple(state /*  : CompileState */, new SymbolValue( /* stripped */) /* : SymbolValue */) /* : Tuple */) /* : Some */;
            }
            return new Some(new Tuple(state /*  : CompileState */, new Placeholder( /* stripped */) /* : Placeholder */) /* : Tuple */) /* : Some */;
        }
        return new None( /*  */) /* : None */;
    }
    /* private static */ parseOperation(state, value, depth, infix) {
        return /* first */ (value /*  : string */, infix /*  : string */, (s, s2) => {
            let tuple = /* parseValue */ (state /*  : CompileState */,  /* s */, depth /*  : number */) /* : unknown */;
            let tuple1 = /* parseValue */ ( /* tuple */.left,  /* s2 */, depth /*  : number */) /* : unknown */;
            let left = /* tuple */ .right;
            let right = /* tuple1 */ .right;
            return new Some(new Tuple(left, new Operation(infix /*  : string */) /* : Operation */) /* : Tuple */) /* : Some */;
        }) /* : unknown */;
    }
    /* private static */ compileValues(state, params, mapper) {
        let parsed = /* parseValuesOrEmpty */ (state /*  : CompileState */, params /*  : string */, (state1, s) => new Some(mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */( /* state1 */) /* : [CompileState, string] */) /* : Some */) /* : unknown */;
        let generated = /* generateValues */ ( /* parsed */.right) /* : unknown */;
        return new Tuple(left) /* : Tuple */;
    }
    /* private static */ generateValues(elements) {
        return /* generateAll */ ( /* Main */.mergeValues, elements /*  : List<string> */) /* : unknown */;
    }
    /* private static  */ parseValuesOrEmpty(state, input, mapper) {
        return /* parseValues */ (state /*  : CompileState */, input /*  : string */, mapper /*  : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */) /* : unknown */.orElseGet(() => new Tuple(state /*  : CompileState */, empty( /*  */) /* : unknown */) /* : Tuple */) /* : unknown */;
    }
    /* private static  */ parseValues(state, input, mapper) {
        return /* getCompileStateListTuple */ (state /*  : CompileState */, input /*  : string */, /* Main */ .foldValueChar, mapper /*  : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */) /* : unknown */;
    }
    /* private static */ compileParameter(state, input) {
        /* if (input.isBlank())  */ {
            return new Tuple(state /*  : CompileState */, new Whitespace( /*  */) /* : Whitespace */) /* : Tuple */;
        }
        return /* parseDefinition */ (state /*  : CompileState */, input /*  : string */) /* : unknown */.map((tuple) => new [CompileState, Parameter](left, right) /* : [CompileState, Parameter] */) /* : unknown */.orElseGet(() => new Tuple(state /*  : CompileState */, new Placeholder(input /*  : string */) /* : Placeholder */) /* : Tuple */) /* : unknown */;
    }
    /* private static */ compileDefinition(state, input) {
        return /* parseDefinition */ (state /*  : CompileState */, input /*  : string */) /* : unknown */.map((tuple) => new Tuple(left, right.generate( /*  */) /* : unknown */) /* : Tuple */) /* : unknown */.orElseGet(() => new Tuple(state /*  : CompileState */, /* generatePlaceholder */ (input /*  : string */) /* : unknown */) /* : Tuple */) /* : unknown */;
    }
    /* private static */ mergeValues(cache, element) {
        /* if (cache.isEmpty())  */ {
            return cache /*  : StringBuilder */.append(element /*  : string */) /* : unknown */;
        }
        return cache /*  : StringBuilder */.append(", ") /* : unknown */.append(element /*  : string */) /* : unknown */;
    }
    /* private static */ createIndent(depth) {
        return "\n" + "\t".repeat(depth /*  : number */) /* : unknown */;
    }
    /* private static */ compileDefinitionStatement(input, depth, state) {
        return /* suffix */ (input /*  : string */.strip( /*  */) /* : unknown */, ";", (withoutEnd) => {
            return /* parseDefinition */ (state /*  : CompileState */,  /* withoutEnd */) /* : unknown */.map((result) => {
                let generated = /* createIndent */ (depth /*  : number */) /* : unknown */ + /* result */ .right.generate( /*  */) /* : unknown */ + ";";
                return new Tuple(left) /* : Tuple */;
            }) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ parseDefinition(state, input) {
        return /* last */ (input /*  : string */.strip( /*  */) /* : unknown */, " ", (beforeName, name) => {
            return /* split */ (() => /* toLast */ ( /* beforeName */, " ", /* Main */ .foldTypeSeparator) /* : unknown */, (beforeType, type) => {
                return /* suffix */ ( /* beforeType */.strip( /*  */) /* : unknown */, ">", (withoutTypeParamStart) => {
                    return /* first */ ( /* withoutTypeParamStart */, "<", (beforeTypeParams, typeParamsString) => {
                        let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple(strip( /*  */) /* : unknown */) /* : Tuple */;
                        let typeParams = /* parseValuesOrEmpty */ (state /*  : CompileState */,  /* typeParamsString */, (state1, s) => new Some(apply( /* state1 */) /* : unknown */) /* : Some */) /* : unknown */;
                        return /* assembleDefinition */ ( /* typeParams */.left, new Some( /* beforeTypeParams */) /* : Some<string> */,  /* name */, /* typeParams */ .right,  /* type */) /* : unknown */;
                    }) /* : unknown */;
                }) /* : unknown */.or(() => {
                    return /* assembleDefinition */ (state /*  : CompileState */, new Some( /* beforeType */) /* : Some<string> */,  /* name */, /* Lists */ .empty( /*  */) /* : unknown */,  /* type */) /* : unknown */;
                }) /* : unknown */;
            }) /* : unknown */.or(() => {
                return /* assembleDefinition */ (state /*  : CompileState */, new None( /*  */) /* : None<string> */,  /* name */, /* Lists */ .empty( /*  */) /* : unknown */,  /* beforeName */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ toLast(input, separator, folder) {
        let divisions = /* divideAll */ (input /*  : string */, folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */) /* : unknown */;
        return /* divisions */ .removeLast( /*  */) /* : unknown */.map((removed) => {
            let left = /* removed */ .left.iterate( /*  */) /* : unknown */.collect(new Joiner(separator /*  : string */) /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
            let right = /* removed */ .right;
            return new Tuple( /* left */) /* : Tuple */;
        }) /* : unknown */;
    }
    /* private static */ foldTypeSeparator(state, c) {
        /* if (c == ' ' && state.isLevel())  */ {
            return state /*  : DivideState */.advance( /*  */) /* : unknown */;
        }
        let appended = state /*  : DivideState */.append(c /*  : Character */) /* : unknown */;
        /* if (c == '<')  */ {
            return /* appended */ .enter( /*  */) /* : unknown */;
        }
        /* if (c == '>')  */ {
            return /* appended */ .exit( /*  */) /* : unknown */;
        }
        return /* appended */;
    }
    /* private static */ assembleDefinition(state, beforeTypeParams, name, typeParams, type) {
        return /* parseType */ (state /*  : CompileState */, type /*  : string */) /* : unknown */.map((type1) => {
            let node = new Definition(beforeTypeParams /*  : Option<string> */, right, name /*  : string */.strip( /*  */) /* : unknown */, typeParams /*  : List<string> */) /* : Definition */;
            return new Tuple(left) /* : Tuple */;
        }) /* : unknown */;
    }
    /* private static */ foldValueChar(state, c) {
        /* if (c == ',' && state.isLevel())  */ {
            return state /*  : DivideState */.advance( /*  */) /* : unknown */;
        }
        let appended = state /*  : DivideState */.append(c /*  : char */) /* : unknown */;
        /* if (c == '-')  */ {
            let peeked = /* appended */ .peek( /*  */) /* : unknown */;
            /* if (peeked == '>')  */ {
                return /* appended */ .popAndAppendToOption( /*  */) /* : unknown */.orElse( /* appended */) /* : unknown */;
            }
            /* else  */ {
                return /* appended */;
            }
        }
        /* if (c == '<' || c == '(' || c == '{')  */ {
            return /* appended */ .enter( /*  */) /* : unknown */;
        }
        /* if (c == '>' || c == ')' || c == '}')  */ {
            return /* appended */ .exit( /*  */) /* : unknown */;
        }
        return /* appended */;
    }
    /* private static */ compileType(state, input) {
        return /* parseType */ (state /*  : CompileState */, input /*  : string */) /* : unknown */.map((tuple) => new Tuple(left, right.generate( /*  */) /* : unknown */) /* : Tuple */) /* : unknown */;
    }
    /* private static */ parseType(state, input) {
        let stripped = input /*  : string */.strip( /*  */) /* : unknown */;
        /* if (stripped.equals("int") || stripped.equals("Integer"))  */ {
            return new Some(new Tuple(state /*  : CompileState */, Int) /* : Tuple */) /* : Some */;
        }
        /* if (stripped.equals("String"))  */ {
            return new Some(new Tuple(state /*  : CompileState */, String) /* : Tuple */) /* : Some */;
        }
        /* if (stripped.equals("type"))  */ {
            return new Some(new Tuple(state /*  : CompileState */, Unknown) /* : Tuple */) /* : Some */;
        }
        /* if (isSymbol(stripped))  */ {
            return new Some(new Tuple(state /*  : CompileState */, new SymbolType( /* stripped */) /* : SymbolType */) /* : Tuple */) /* : Some */;
        }
        return /* template */ (state /*  : CompileState */, input /*  : string */) /* : unknown */.or(() => /* varArgs */ (state /*  : CompileState */, input /*  : string */) /* : unknown */) /* : unknown */;
    }
    /* private static */ varArgs(state, input) {
        return /* suffix */ (input /*  : string */, "...", (s) => {
            return /* parseType */ (state /*  : CompileState */,  /* s */) /* : unknown */.map((inner) => {
                let newState = /* inner */ .left;
                let child = /* inner */ .right;
                return new Tuple(new ArrayType( /* child */) /* : ArrayType */) /* : Tuple */;
            }) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ template(state, input) {
        return /* suffix */ (input /*  : string */.strip( /*  */) /* : unknown */, ">", (withoutEnd) => {
            return /* first */ ( /* withoutEnd */, "<", (base, argumentsString) => {
                let strippedBase = /* base */ .strip( /*  */) /* : unknown */;
                return /* parseValues */ (state /*  : CompileState */,  /* argumentsString */, /* Main */ .argument) /* : unknown */.map((argumentsTuple) => {
                    return /* assembleTemplate */ ( /* base */,  /* strippedBase */, /* argumentsTuple */ .left, /* argumentsTuple */ .right) /* : unknown */;
                }) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }
    /* private static */ assembleTemplate(base, strippedBase, state, arguments) {
        let children = arguments /*  : List<Argument> */.iterate( /*  */) /* : unknown */.map(retainType) /* : unknown */.flatMap(fromOption) /* : unknown */.collect(new ListCollector( /*  */) /* : ListCollector */) /* : unknown */;
        /* if (base.equals("BiFunction"))  */ {
            return new Tuple(state /*  : CompileState */, new FunctionType(of(get(0 /*  : number */) /* : unknown */, get(1 /*  : number */) /* : unknown */) /* : unknown */, get(2 /*  : number */) /* : unknown */) /* : FunctionType */) /* : Tuple */;
        }
        /* if (base.equals("Function"))  */ {
            return new Tuple(state /*  : CompileState */, new FunctionType(of(get(0 /*  : number */) /* : unknown */) /* : unknown */, get(1 /*  : number */) /* : unknown */) /* : FunctionType */) /* : Tuple */;
        }
        /* if (base.equals("Predicate"))  */ {
            return new Tuple(state /*  : CompileState */, new FunctionType(of(get(0 /*  : number */) /* : unknown */) /* : unknown */, Boolean) /* : FunctionType */) /* : Tuple */;
        }
        /* if (base.equals("Supplier"))  */ {
            return new Tuple(state /*  : CompileState */, new FunctionType(empty( /*  */) /* : unknown */, get(0 /*  : number */) /* : unknown */) /* : FunctionType */) /* : Tuple */;
        }
        /* if (base.equals("Tuple") && children.size() >= 2)  */ {
            return new Tuple(state /*  : CompileState */, new TupleType( /* children */) /* : TupleType */) /* : Tuple */;
        }
        return new Tuple(state /*  : CompileState */, new Template(strippedBase /*  : string */) /* : Template */) /* : Tuple */;
    }
    /* private static */ retainType(argument) {
        /* if (argument instanceof Type type)  */ {
            return new Some( /* type */) /* : Some */;
        }
        /* else  */ {
            return new None( /*  */) /* : None<Type> */;
        }
    }
    /* private static */ argument(state, input) {
        /* if (input.isBlank())  */ {
            return new Some(new Tuple(state /*  : CompileState */, new Whitespace( /*  */) /* : Whitespace */) /* : Tuple */) /* : Some */;
        }
        return /* parseType */ (state /*  : CompileState */, input /*  : string */) /* : unknown */.map((tuple) => new Tuple(left, right) /* : Tuple */) /* : unknown */;
    }
    /* private static  */ last(input, infix, mapper) {
        return infix /*  : string */(input /*  : string */, infix /*  : string */, findLast, mapper /*  : (arg0 : string, arg1 : string) => Option<T> */) /* : unknown */;
    }
    /* private static */ findLast(input, infix) {
        let index = input /*  : string */.lastIndexOf(infix /*  : string */) /* : unknown */;
        /* if (index == -1)  */ {
            return new None( /*  */) /* : None<number> */;
        }
        return new Some( /* index */) /* : Some */;
    }
    /* private static  */ first(input, infix, mapper) {
        return infix /*  : string */(input /*  : string */, infix /*  : string */, findFirst, mapper /*  : (arg0 : string, arg1 : string) => Option<T> */) /* : unknown */;
    }
    /* private static  */ infix(input, infix, locator, mapper) {
        return /* split */ (() => locator /*  : (arg0 : string, arg1 : string) => Option<number> */(input /*  : string */, infix /*  : string */) /* : Option<number> */.map((index) => {
            let left = input /*  : string */.substring(0 /*  : number */) /* : unknown */;
            let right = input /*  : string */.substring(/* index */ +infix /*  : string */.length( /*  */) /* : unknown */) /* : unknown */;
            return new Tuple( /* left */) /* : Tuple */;
        }) /* : unknown */, mapper /*  : (arg0 : string, arg1 : string) => Option<T> */) /* : unknown */;
    }
    /* private static  */ split(splitter, mapper) {
        return splitter /*  : () => Option<[string, string]> */( /*  */) /* : Option<[string, string]> */.flatMap((tuple) => mapper /*  : (arg0 : string, arg1 : string) => Option<T> */(left, right) /* : Option<T> */) /* : unknown */;
    }
    /* private static */ findFirst(input, infix) {
        let index = input /*  : string */.indexOf(infix /*  : string */) /* : unknown */;
        /* if (index == -1)  */ {
            return new None( /*  */) /* : None<number> */;
        }
        return new Some( /* index */) /* : Some */;
    }
    /* private static */ generatePlaceholder(input) {
        let replaced = input /*  : string */.replace("/*", "content-start") /* : unknown */.replace("*/", "content-end") /* : unknown */;
        return "/* " + replaced + " */";
    } /*

    private enum Primitive implements Type {
        Int("number"),
        String("string"),
        Boolean("boolean"),
        Unknown("unknown");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }
    } */
}
/*  */ 
