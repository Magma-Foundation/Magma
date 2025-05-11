"use strict";
/* private */ class Some {
    constructor(value) {
    }
    /* @Override
        public  */ map(mapper) {
        return new Some(mapper /*  : (arg0 : T) => R */(value));
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
        return new None( /*  */);
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
        return mapper /*  : (arg0 : T) => Option<R> */(value);
    }
    /* @Override
        public */ isEmpty() {
        return /* false */;
    }
}
/* private static */ class None {
    /* @Override
        public  */ map(mapper) {
        return new None( /*  */);
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
        return new None( /*  */);
    }
    /* @Override
        public */ orElseGet(supplier) {
        return supplier /*  : () => T */( /*  */);
    }
    /* @Override
        public */ or(other) {
        return other /*  : () => Option<T> */( /*  */);
    }
    /* @Override
        public  */ flatMap(mapper) {
        return new None( /*  */);
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
            return new None( /*  */);
        }
        let /* this.retrieved  */ =  /* true */;
        return new Some(value);
    }
}
/* private static */ class EmptyHead {
    /* @Override
        public */ next() {
        return new None( /*  */);
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
            let optional = /* this */ .head.next( /*  */).map((inner) => folder /*  : (arg0 : R, arg1 : T) => R */( /* finalCurrent */));
            /* if (optional.isPresent())  */ {
                let /* current  */ = /* optional */ .orElse( /* null */);
            }
            /* else  */ {
                return /* current */;
            }
        }
    }
    /* @Override
        public  */ map(mapper) {
        return new HeadedIterator(() => /* this */ .head.next( /*  */).map(mapper /*  : (arg0 : T) => R */));
    }
    /* @Override
        public  */ collect(collector) {
        return /* this */ .fold(collector /*  : Collector<T, R> */.createInitial( /*  */), collector /*  : Collector<T, R> */.fold);
    }
    /* @Override
        public */ filter(predicate) {
        return /* this */ .flatMap((element) => {
            /* if (predicate.test(element))  */ {
                return new HeadedIterator(new SingleHead( /* element */));
            }
            return new HeadedIterator(new EmptyHead( /*  */));
        });
    }
    /* @Override
        public */ next() {
        return /* this */ .head.next( /*  */);
    }
    /* @Override
        public  */ flatMap(f) {
        return new HeadedIterator(new FlatMapHead(head, f /*  : (arg0 : T) => Iterator<R> */));
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
            return new Some( /* value */);
        }
        return new None( /*  */);
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
        return /* this */ .iterateWithIndices( /*  */).map(right);
    }
    /* @Override
            public */ removeLast() {
        /* if (this.elements.isEmpty())  */ {
            return new None( /*  */);
        }
        let slice = /* this */ .elements.subList(0 /*  : number */, elements.size( /*  */) - 1 /*  : number */);
        let last = /* this */ .elements.getLast( /*  */);
        return new Some(new [(List), T](new JVMList( /* slice */)));
    }
    /* @Override
            public */ get(index) {
        return /* this */ .elements.get(index /*  : number */);
    }
    /* @Override
            public */ size() {
        return /* this */ .elements.size( /*  */);
    }
    /* @Override
            public */ isEmpty() {
        return /* this */ .elements.isEmpty( /*  */);
    }
    /* @Override
            public */ addFirst(element) {
        /* this.elements.addFirst(element) */ ;
        return /* this */;
    }
    /* @Override
            public */ iterateWithIndices() {
        return new HeadedIterator(new RangeHead(elements.size( /*  */))).map((index) => new Tuple(elements.get( /* index */)));
    }
    /* @Override
            public */ removeFirst() {
        /* if (this.elements.isEmpty())  */ {
            return new None( /*  */);
        }
        let first = /* this */ .elements.getFirst( /*  */);
        let slice = /* this */ .elements.subList(1 /*  : number */, elements.size( /*  */));
        return new Some(new [T, (List)](new JVMList( /* slice */)));
    }
}
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return new JVMList( /*  */);
    }
    /* public static  */ of(elements) {
        return new JVMList(new ArrayList(asList(elements /*  : T[] */)));
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
        let /* this.segments  */ = /* this */ .segments.addLast(buffer.toString( /*  */));
        let /* this.buffer  */ = new StringBuilder( /*  */);
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
            let c = /* this */ .input.charAt(index);
            return new Some(new Tuple(new DivideState(input, index + 1 /*  : number */, segments, buffer, depth)));
        }
        return new None( /*  */);
    }
    /* public */ popAndAppendToTuple() {
        return /* this */ .pop( /*  */).map((tuple) => new Tuple(left, right.append(left)));
    }
    /* public */ popAndAppendToOption() {
        return /* this */ .popAndAppendToTuple( /*  */).map(right);
    }
    /* public */ peek() {
        return /* this */ .input.charAt(index);
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
        return new None( /*  */);
    }
    /* @Override
        public */ fold(current, element) {
        return new Some(current /*  : Option<string> */.map((inner) => /* inner */ +.delimiter + element /*  : string */).orElse(element /*  : string */));
    }
}
/* private */ class Definition /*  */ {
    constructor(maybeBefore, type, name, typeParams) {
    }
    /* private */ generate() {
        return /* this */ .generateWithParams("");
    }
    /* public */ generateWithParams(params) {
        let joined = /* this */ .joinTypeParams( /*  */);
        let before = /* this */ .joinBefore( /*  */);
        let typeString = /* this */ .generateType( /*  */);
        return /* before */ +.name + /* joined */ +params /*  : string */ +  /* typeString */;
    }
    /* private */ generateType() {
        /* if (this.type.equals(Primitive.Var))  */ {
            return "";
        }
        return " : " + /* this */ .type.generate( /*  */);
    }
    /* private */ joinBefore() {
        return /* this */ .maybeBefore.filter((value) => !.isEmpty( /*  */)).map(generatePlaceholder).map((inner) => /* inner */ +" ").orElse("");
    }
    /* private */ joinTypeParams() {
        return /* this */ .typeParams.iterate( /*  */).collect(new Joiner( /*  */)).map((inner) => "<" + inner + ">").orElse("");
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return /* Lists */ .empty( /*  */);
    }
    /* @Override
        public */ fold(current, element) {
        return current /*  : List<T> */.addLast(element /*  : T */);
    }
}
/* private */ class Tuple {
}
/* private static */ class FlatMapHead {
    FlatMapHead(head, mapper) {
        let /* this.mapper  */ = mapper /*  : (arg0 : T) => Iterator<R> */;
        let /* this.current  */ = new None( /*  */);
        let /* this.head  */ = head /*  : Head<T> */;
    }
    /* @Override
        public */ next() {
        /* while (true)  */ {
            /* if (this.current.isPresent())  */ {
                let inner = /* this */ .current.orElse( /* null */);
                let maybe = /* inner */ .next( /*  */);
                /* if (maybe.isPresent())  */ {
                    return /* maybe */;
                }
                /* else  */ {
                    let /* this.current  */ = new None( /*  */);
                }
            }
            let outer = /* this */ .head.next( /*  */);
            /* if (outer.isPresent())  */ {
                let /* this.current  */ = /* outer */ .map(mapper);
            }
            /* else  */ {
                return new None( /*  */);
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
        return /* this */ .right.generate( /*  */) + "[]";
    }
}
/* private static */ class Whitespace /*  */ {
}
/* private static */ class Iterators /*  */ {
    /* public static  */ fromOption(option) {
        let single = option /*  : Option<T> */.map(new );
        return new HeadedIterator(orElseGet(new ));
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments( /*  */).iterateWithIndices( /*  */).map((pair) => "arg" + /* pair */ .left + " : " + /* pair */ .right.generate( /*  */)).collect(new Joiner(", ")).orElse("");
        return "(" + /* joined */ +") => " + /* this */ .returns.generate( /*  */);
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments.iterate( /*  */).map(generate).collect(new Joiner(", ")).orElse("");
        return "[" + joinedArguments + "]";
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments.iterate( /*  */).map(generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        return /* this */ .base +  /* joinedArguments */;
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* generatePlaceholder */ ( /* this */.input);
    }
}
/* private */ class StringValue /*  */ {
    constructor(stripped) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped;
    }
}
/* private */ class DataAccess /*  */ {
    constructor(parent, property) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent.generate( /*  */) + "." + /* this */ .property;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(right) {
    }
    /* @Override
        public */ generate() {
        return "new " + /* this */ .right.generate( /*  */);
    }
}
/* private */ class Operation /*  */ {
    constructor(left, infix, right) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .left.generate( /*  */) + " " + /* this */ .infix + " " + /* this */ .right.generate( /*  */);
    }
}
/* private */ class Not /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return "!" + /* this */ .value.generate( /*  */);
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
        let joined = /* this */ .parameterNames.iterate( /*  */).map((inner) => /* inner */ +" : unknown").collect(new Joiner(", ")).orElse("");
        return "(" + /* joined */ +") => " + /* this */ .body.generate( /*  */);
    }
}
/* private */ class Invokable /*  */ {
    constructor(caller, arguments) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments.iterate( /*  */).map(generate).collect(new Joiner(", ")).orElse("");
        return /* this */ .caller.generate( /*  */) + "(" + joined + ")";
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent.generate( /*  */) + "[" + this.child.generate() + "]";
    }
}
/* private static final */ class SymbolValue /*  */ {
    SymbolValue(stripped, type) {
        let /* this.stripped  */ = stripped /*  : string */;
        let /* this.type  */ = type /*  : Type */;
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped + /* generatePlaceholder */ (" : " + /* this */ .type.generate( /*  */));
    }
}
/* public */ class Main /*  */ {
    /* private */ CompileState(structures, definitions) {
        /* public CompileState()  */ {
            /* this(Lists.empty(), Lists.empty()) */ ;
        }
        /* private Option<Type> resolve(String name)  */ {
            return /* this */ .definitions.iterate( /*  */).filter((definition) => /* definition */ .name.equals( /* name */)).next( /*  */).map(type);
        }
        /* public CompileState addStructure(String structure)  */ {
            return new CompileState(structures.addLast( /* structure */), definitions);
        }
        /* public CompileState withDefinitions(List<Definition> definitions)  */ {
            return new CompileState(structures, definitions /*  : List<Definition> */);
        }
    }
    /* public static */ main() {
        /* try  */ {
            let parent = /* Paths */ .get(".", "src", "java", "magma");
            let source = /* parent */ .resolve("Main.java");
            let target = /* parent */ .resolve("main.ts");
            let input = /* Files */ .readString( /* source */);
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
        let tuple = /* compileStatements */ (new CompileState( /*  */), input /*  : string */, /* Main */ .compileRootSegment);
        let joined = /* tuple */ .left.structures.iterate( /*  */).collect(new Joiner( /*  */)).orElse("");
        return /* joined */ +.right;
    }
    /* private static */ compileStatements(state, input, mapper) {
        let parsed = /* parseStatements */ (state /*  : CompileState */, input /*  : string */, mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */);
        return new Tuple(left, /* generateStatements */ ( /* parsed */.right));
    }
    /* private static */ generateStatements(statements) {
        return /* generateAll */ ( /* Main */.mergeStatements, statements /*  : List<string> */);
    }
    /* private static */ parseStatements(state, input, mapper) {
        return /* parseAll */ (state /*  : CompileState */, input /*  : string */, /* Main */ .foldStatementChar, mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */);
    }
    /* private static */ generateAll(merger, elements) {
        return elements /*  : List<string> */.iterate( /*  */).fold(new StringBuilder( /*  */), merger /*  : (arg0 : StringBuilder, arg1 : string) => StringBuilder */).toString( /*  */);
    }
    /* private static  */ parseAll(state, input, folder, mapper) {
        return /* getCompileStateListTuple */ (state /*  : CompileState */, input /*  : string */, folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */, (state1, s) => new Some(mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, T] */( /* state1 */))).orElseGet(() => new Tuple(state /*  : CompileState */, empty( /*  */)));
    }
    /* private static  */ getCompileStateListTuple(state, input, folder, mapper) {
        let initial = new Some(new Tuple(state /*  : CompileState */, empty( /*  */)));
        return /* divideAll */ (input /*  : string */, folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */).iterate( /*  */).fold((tuple, element) => {
            return /* tuple */ .flatMap((inner) => {
                let state1 = /* inner */ .left;
                let right = /* inner */ .right;
                return mapper /*  : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */( /* state1 */).map((applied) => {
                    return new Tuple(left, addLast(right));
                });
            });
        });
    }
    /* private static */ mergeStatements(stringBuilder, str) {
        return stringBuilder /*  : StringBuilder */.append(str /*  : string */);
    }
    /* private static */ divideAll(input, folder) {
        let current = new DivideState(input /*  : string */);
        /* while (true)  */ {
            let maybePopped = /* current */ .pop( /*  */).map((tuple) => {
                return /* foldSingleQuotes */ ( /* tuple */).or(() => /* foldDoubleQuotes */ ( /* tuple */)).orElseGet(() => folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */(right, left));
            });
            /* if (maybePopped.isPresent())  */ {
                let /* current  */ = /* maybePopped */ .orElse( /* current */);
            }
            /* else  */ {
                /* break */ ;
            }
        }
        return /* current */ .advance( /*  */).segments;
    }
    /* private static */ foldDoubleQuotes(tuple) {
        /* if (tuple.left == '\"')  */ {
            let current = tuple /*  : [Character, DivideState] */[1 /*  : number */].append(tuple /*  : [Character, DivideState] */[0 /*  : number */]);
            /* while (true)  */ {
                let maybePopped = /* current */ .popAndAppendToTuple( /*  */);
                /* if (maybePopped.isEmpty())  */ {
                    /* break */ ;
                }
                let popped = /* maybePopped */ .orElse( /* null */);
                let /* current  */ = /* popped */ .right;
                /* if (popped.left == '\\')  */ {
                    let /* current  */ = /* current */ .popAndAppendToOption( /*  */).orElse( /* current */);
                }
                /* if (popped.left == '\"')  */ {
                    /* break */ ;
                }
            }
            return new Some( /* current */);
        }
        return new None( /*  */);
    }
    /* private static */ foldSingleQuotes(tuple) {
        /* if (tuple.left != '\'')  */ {
            return new None( /*  */);
        }
        let appended = tuple /*  : [Character, DivideState] */[1 /*  : number */].append(tuple /*  : [Character, DivideState] */[0 /*  : number */]);
        return /* appended */ .popAndAppendToTuple( /*  */).map(foldEscaped).flatMap(popAndAppendToOption);
    }
    /* private static */ foldEscaped(escaped) {
        /* if (escaped.left == '\\')  */ {
            return escaped /*  : [Character, DivideState] */[1 /*  : number */].popAndAppendToOption( /*  */).orElse(escaped /*  : [Character, DivideState] */[1 /*  : number */]);
        }
        return escaped /*  : [Character, DivideState] */[1 /*  : number */];
    }
    /* private static */ foldStatementChar(state, c) {
        let append = state /*  : DivideState */.append(c /*  : char */);
        /* if (c == ';' && append.isLevel())  */ {
            return /* append */ .advance( /*  */);
        }
        /* if (c == '}' && append.isShallow())  */ {
            return /* append */ .advance( /*  */).exit( /*  */);
        }
        /* if (c == '{' || c == '(')  */ {
            return /* append */ .enter( /*  */);
        }
        /* if (c == '}' || c == ')')  */ {
            return /* append */ .exit( /*  */);
        }
        return /* append */;
    }
    /* private static */ compileRootSegment(state, input) {
        let stripped = input /*  : string */.strip( /*  */);
        /* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */ {
            return new Tuple(state /*  : CompileState */, "");
        }
        return /* compileClass */ ( /* stripped */, 0 /*  : number */, state /*  : CompileState */).orElseGet(() => new Tuple(state /*  : CompileState */, /* generatePlaceholder */ ( /* stripped */)));
    }
    /* private static */ compileClass(stripped, depth, state) {
        return /* structure */ (stripped /*  : string */, "class ", "class ", state /*  : CompileState */);
    }
    /* private static */ structure(stripped, sourceInfix, targetInfix, state) {
        return /* first */ (stripped /*  : string */, sourceInfix /*  : string */, (beforeInfix, right) => {
            return /* first */ ( /* right */, "{", (beforeContent, withEnd) => {
                let strippedWithEnd = /* withEnd */ .strip( /*  */);
                return /* suffix */ ( /* strippedWithEnd */, "}", (content1) => {
                    return /* first */ ( /* beforeContent */, " implements ", (s, s2) => {
                        return /* structureWithMaybeParams */ (targetInfix /*  : string */, state /*  : CompileState */,  /* beforeInfix */,  /* s */,  /* content1 */);
                    }).or(() => {
                        return /* structureWithMaybeParams */ (targetInfix /*  : string */, state /*  : CompileState */,  /* beforeInfix */,  /* beforeContent */,  /* content1 */);
                    });
                });
            });
        });
    }
    /* private static */ structureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1) {
        return /* suffix */ (beforeContent /*  : string */, ")", (s) => {
            return /* first */ ( /* s */, "(", (s1, s2) => {
                let parsed = /* parseParameters */ (state /*  : CompileState */,  /* s2 */);
                return /* getOred */ (targetInfix /*  : string */, /* parsed */ .left, beforeInfix /*  : string */,  /* s1 */, content1 /*  : string */, /* parsed */ .right);
            });
        }).or(() => {
            return /* getOred */ (targetInfix /*  : string */, state /*  : CompileState */, beforeInfix /*  : string */, beforeContent /*  : string */, content1 /*  : string */, /* Lists */ .empty( /*  */));
        });
    }
    /* private static */ getOred(targetInfix, state, beforeInfix, beforeContent, content1, params) {
        return /* first */ (beforeContent /*  : string */, "<", (name, withTypeParams) => {
            return /* first */ ( /* withTypeParams */, ">", (typeParamsString, afterTypeParams) => {
                let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple(strip( /*  */));
                let typeParams = /* parseValuesOrEmpty */ (state /*  : CompileState */,  /* typeParamsString */, (state1, s) => new Some(apply( /* state1 */)));
                return /* assemble */ ( /* typeParams */.left, targetInfix /*  : string */, beforeInfix /*  : string */,  /* name */, content1 /*  : string */, /* typeParams */ .right,  /* afterTypeParams */, params /*  : List<Parameter> */);
            });
        }).or(() => {
            return /* assemble */ (state /*  : CompileState */, targetInfix /*  : string */, beforeInfix /*  : string */, beforeContent /*  : string */, content1 /*  : string */, /* Lists */ .empty( /*  */), "", params /*  : List<Parameter> */);
        });
    }
    /* private static */ assemble(state, targetInfix, beforeInfix, rawName, content, typeParams, afterTypeParams, params) {
        let name = rawName /*  : string */.strip( /*  */);
        /* if (!isSymbol(name))  */ {
            return new None( /*  */);
        }
        let joinedTypeParams = typeParams /*  : List<string> */.iterate( /*  */).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        let parsed = /* parseStatements */ (state /*  : CompileState */, content /*  : string */, (state0, input) => /* compileClassSegment */ ( /* state0 */,  /* input */, 1 /*  : number */));
        /* List<String> parsed1 */ ;
        /* if (params.isEmpty())  */ {
            let /* parsed1  */ = /* parsed */ .right;
        }
        /* else  */ {
            let joined = /* joinValues */ ( /* retainDefinitions */(params /*  : List<Parameter> */));
            let constructorIndent = /* createIndent */ (1 /*  : number */);
            let /* parsed1  */ = /* parsed */ .right.addFirst(/* constructorIndent */ +"constructor (" + joined + ") {" + constructorIndent + "}\n");
        }
        let parsed2 = /* parsed1 */ .iterate( /*  */).collect(new Joiner( /*  */)).orElse("");
        let generated = /* generatePlaceholder */ (beforeInfix /*  : string */.strip( /*  */)) + targetInfix /*  : string */ + /* name */ + +(afterTypeParams /*  : string */) + " {" + parsed2 + "\n}\n";
        return new Some(new Tuple(left.addStructure( /* generated */), ""));
    }
    /* private static */ retainDefinition(parameter) {
        /* if (parameter instanceof Definition definition)  */ {
            return new Some( /* definition */);
        }
        return new None( /*  */);
    }
    /* private static */ isSymbol(input) {
        /* for (var i = 0; i < input.length(); i++)  */ {
            let c = input /*  : string */.charAt( /* i */);
            /* if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */ {
                /* continue */ ;
            }
            return /* false */;
        }
        return /* true */;
    }
    /* private static  */ suffix(input, suffix, mapper) {
        /* if (!input.endsWith(suffix))  */ {
            return new None( /*  */);
        }
        let slice = input /*  : string */.substring(0 /*  : number */, input /*  : string */.length( /*  */) - suffix /*  : string */.length( /*  */));
        return mapper /*  : (arg0 : string) => Option<T> */( /* slice */);
    }
    /* private static */ compileClassSegment(state, input, depth) {
        return /* compileWhitespace */ (input /*  : string */, state /*  : CompileState */).or(() => /* compileClass */ (input /*  : string */, depth /*  : number */, state /*  : CompileState */)).or(() => /* structure */ (input /*  : string */, "interface ", "interface ", state /*  : CompileState */)).or(() => /* structure */ (input /*  : string */, "record ", "class ", state /*  : CompileState */)).or(() => /* compileMethod */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */)).or(() => /* compileDefinitionStatement */ (input /*  : string */, depth /*  : number */, state /*  : CompileState */)).orElseGet(() => new Tuple(state /*  : CompileState */, /* generatePlaceholder */ (input /*  : string */)));
    }
    /* private static */ compileWhitespace(input, state) {
        /* if (input.isBlank())  */ {
            return new Some(new Tuple(state /*  : CompileState */, ""));
        }
        return new None( /*  */);
    }
    /* private static */ compileMethod(state, input, depth) {
        return /* first */ (input /*  : string */, "(", (definitionString, withParams) => {
            return /* first */ ( /* withParams */, ")", (parametersString, rawContent) => {
                return /* parseDefinition */ (state /*  : CompileState */,  /* definitionString */).flatMap((definitionTuple) => {
                    let definitionState = /* definitionTuple */ .left;
                    let definition = /* definitionTuple */ .right;
                    let parametersTuple = /* parseParameters */ ( /* definitionState */,  /* parametersString */);
                    let parameters = /* parametersTuple */ .right;
                    let definitions = /* retainDefinitions */ ( /* parameters */);
                    let joinedParameters = /* joinValues */ ( /* definitions */);
                    let content = /* rawContent */ .strip( /*  */);
                    let indent = /* createIndent */ (depth /*  : number */);
                    let generatedHeader = /* definition */ .generateWithParams("(" + joinedParameters + ")");
                    /* if (content.equals(";"))  */ {
                        return new Some(new Tuple(left, /* indent */ + +";"));
                    }
                    /* if (content.startsWith("{") && content.endsWith("}"))  */ {
                        let substring = /* content */ .substring(1 /*  : number */, length( /*  */) - 1 /*  : number */);
                        let statementsTuple = /* compileFunctionSegments */ ( /* parametersTuple */.left.withDefinitions( /* definitions */),  /* substring */, depth /*  : number */);
                        let generated = /* indent */ + +" {" + statementsTuple.right + indent + "}";
                        return new Some(new Tuple(left));
                    }
                    return new None( /*  */);
                });
            });
        });
    }
    /* private static */ joinValues(retainParameters) {
        return retainParameters /*  : List<Definition> */.iterate( /*  */).map(generate).collect(new Joiner(", ")).orElse("");
    }
    /* private static */ retainDefinitions(right) {
        return right /*  : List<Parameter> */.iterate( /*  */).map(retainDefinition).flatMap(fromOption).collect(new ListCollector( /*  */));
    }
    /* private static */ parseParameters(state, params) {
        return /* parseValuesOrEmpty */ (state /*  : CompileState */, params /*  : string */, (state1, s) => new Some(/* compileParameter */ ( /* state1 */,  /* s */)));
    }
    /* private static */ compileFunctionSegments(state, input, depth) {
        return /* compileStatements */ (state /*  : CompileState */, input /*  : string */, (state1, input1) => /* compileFunctionSegment */ ( /* state1 */,  /* input1 */, depth /*  : number */ + 1 /*  : number */));
    }
    /* private static */ compileFunctionSegment(state, input, depth) {
        let stripped = input /*  : string */.strip( /*  */);
        /* if (stripped.isEmpty())  */ {
            return new Tuple(state /*  : CompileState */, "");
        }
        return /* suffix */ ( /* stripped */, ";", (s) => {
            let tuple = /* statementValue */ (state /*  : CompileState */,  /* s */, depth /*  : number */);
            return new Some(new Tuple(left, /* createIndent */ (depth /*  : number */) + /* tuple */ .right + ";"));
        }).or(() => {
            return /* block */ (state /*  : CompileState */, depth /*  : number */,  /* stripped */);
        }).orElseGet(() => {
            return new Tuple(state /*  : CompileState */, /* generatePlaceholder */ ( /* stripped */));
        });
    }
    /* private static */ block(state, depth, stripped) {
        return /* suffix */ (stripped /*  : string */, "}", (withoutEnd) => {
            return /* split */ (() => {
                let divisions = /* divideAll */ ( /* withoutEnd */, /* Main */ .foldBlockStart);
                return /* divisions */ .removeFirst( /*  */).map((removed) => {
                    let right = /* removed */ .left;
                    let left = /* removed */ .right.iterate( /*  */).collect(new Joiner("")).orElse("");
                    return new Tuple( /* right */);
                });
            }, (beforeContent, content) => {
                return /* suffix */ ( /* beforeContent */, "{", (s) => {
                    let compiled = /* compileFunctionSegments */ (state /*  : CompileState */,  /* content */, depth /*  : number */);
                    let indent = /* createIndent */ (depth /*  : number */);
                    return new Some(new Tuple(left, /* indent */ +( /* s */) + "{" + compiled.right + indent + "}"));
                });
            });
        });
    }
    /* private static */ foldBlockStart(state, c) {
        let appended = state /*  : DivideState */.append(c /*  : Character */);
        /* if (c == '{' && state.isLevel())  */ {
            return /* appended */ .advance( /*  */);
        }
        /* if (c == '{')  */ {
            return /* appended */ .enter( /*  */);
        }
        /* if (c == '}')  */ {
            return /* appended */ .exit( /*  */);
        }
        return /* appended */;
    }
    /* private static */ statementValue(state, input, depth) {
        let stripped = input /*  : string */.strip( /*  */);
        /* if (stripped.startsWith("return "))  */ {
            let value = /* stripped */ .substring("return ".length( /*  */));
            let tuple = /* compileValue */ (state /*  : CompileState */,  /* value */, depth /*  : number */);
            return new Tuple(left, "return " + /* tuple */ .right);
        }
        return /* first */ ( /* stripped */, "=", (s, s2) => {
            let definitionTuple = /* compileDefinition */ (state /*  : CompileState */,  /* s */);
            let valueTuple = /* compileValue */ ( /* definitionTuple */.left,  /* s2 */, depth /*  : number */);
            return new Some(new Tuple(left, "let " + /* definitionTuple */ .right + " = " + /* valueTuple */ .right));
        }).orElseGet(() => {
            return new Tuple(state /*  : CompileState */, /* generatePlaceholder */ ( /* stripped */));
        });
    }
    /* private static */ compileValue(state, input, depth) {
        let tuple = /* parseValue */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */);
        return new Tuple(left, right.generate( /*  */));
    }
    /* private static */ parseValue(state, input, depth) {
        return /* parseLambda */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */).or(() => /* parseString */ (state /*  : CompileState */, input /*  : string */)).or(() => /* parseDataAccess */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */)).or(() => /* parseSymbolValue */ (state /*  : CompileState */, input /*  : string */)).or(() => /* parseInvocation */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */)).or(() => /* parseOperation */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */, "+")).or(() => /* parseOperation */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */, "-")).or(() => /* parseDigits */ (state /*  : CompileState */, input /*  : string */)).or(() => /* parseNot */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */)).or(() => /* parseMethodReference */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */)).orElseGet(() => new [CompileState, Value](state /*  : CompileState */, new Placeholder(input /*  : string */)));
    }
    /* private static */ parseMethodReference(state, input, depth) {
        return /* last */ (input /*  : string */, "::", (s, s2) => {
            let tuple = /* parseValue */ (state /*  : CompileState */,  /* s */, depth /*  : number */);
            return new Some(new Tuple(left, new DataAccess(right)));
        });
    }
    /* private static */ parseNot(state, input, depth) {
        let stripped = input /*  : string */.strip( /*  */);
        /* if (stripped.startsWith("!"))  */ {
            let slice = /* stripped */ .substring(1 /*  : number */);
            let tuple = /* parseValue */ (state /*  : CompileState */,  /* slice */, depth /*  : number */);
            let value = /* tuple */ .right;
            return new Some(new Tuple(left, new Not( /* value */)));
        }
        return new None( /*  */);
    }
    /* private static */ parseLambda(state, input, depth) {
        return /* first */ (input /*  : string */, "->", (beforeArrow, valueString) => {
            let strippedBeforeArrow = /* beforeArrow */ .strip( /*  */);
            /* if (isSymbol(strippedBeforeArrow))  */ {
                return /* assembleLambda */ (state /*  : CompileState */, /* Lists */ .of( /* strippedBeforeArrow */),  /* valueString */, depth /*  : number */);
            }
            /* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */ {
                let parameterNames = /* divideAll */ ( /* strippedBeforeArrow */.substring(1 /*  : number */, length( /*  */) - 1 /*  : number */), /* Main */ .foldValueChar).iterate( /*  */).map(strip).filter((value) => !.isEmpty( /*  */)).collect(new ListCollector( /*  */));
                return /* assembleLambda */ (state /*  : CompileState */,  /* parameterNames */,  /* valueString */, depth /*  : number */);
            }
            return new None( /*  */);
        });
    }
    /* private static */ assembleLambda(state, paramNames, valueString, depth) {
        let strippedValueString = valueString /*  : string */.strip( /*  */);
        /* Tuple<CompileState, LambdaValue> value */ ;
        /* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */ {
            let value1 = /* compileStatements */ (state /*  : CompileState */, /* strippedValueString */ .substring(1 /*  : number */, length( /*  */) - 1 /*  : number */), (state1, input1) => /* compileFunctionSegment */ ( /* state1 */,  /* input1 */, depth /*  : number */ + 1 /*  : number */));
            let right = /* value1 */ .right;
            let /* value  */ = new Tuple(left, new BlockLambdaValue(depth /*  : number */));
        }
        /* else  */ {
            let value1 = /* parseValue */ (state /*  : CompileState */,  /* strippedValueString */, depth /*  : number */);
            let /* value  */ = new Tuple(left, right);
        }
        let right = /* value */ .right;
        return new Some(new Tuple(left, new Lambda(paramNames /*  : List<string> */)));
    }
    /* private static */ parseDigits(state, input) {
        let stripped = input /*  : string */.strip( /*  */);
        /* if (isNumber(stripped))  */ {
            return new Some(new [CompileState, Value](state /*  : CompileState */, new SymbolValue(Int)));
        }
        return new None( /*  */);
    }
    /* private static */ isNumber(input) {
        /* for (var i = 0; i < input.length(); i++)  */ {
            let c = input /*  : string */.charAt( /* i */);
            /* if (Character.isDigit(c))  */ {
                /* continue */ ;
            }
            return /* false */;
        }
        return /* true */;
    }
    /* private static */ parseInvocation(state, input, depth) {
        return /* suffix */ (input /*  : string */.strip( /*  */), ")", (withoutEnd) => {
            return /* split */ (() => /* toLast */ ( /* withoutEnd */, "", /* Main */ .foldInvocationStart), (callerWithEnd, argumentsString) => {
                return /* suffix */ ( /* callerWithEnd */, "(", (callerString) => {
                    let callerString1 = /* callerString */ .strip( /*  */);
                    let callerTuple = /* invocationHeader */ (state /*  : CompileState */, depth /*  : number */,  /* callerString1 */);
                    let parsed = /* parseValues */ ( /* callerTuple */.left,  /* argumentsString */, (state3, s) => new Some(/* parseValue */ ( /* state3 */,  /* s */, depth /*  : number */))).orElseGet(() => new Tuple(left, empty( /*  */)));
                    let oldCaller = /* callerTuple */ .right;
                    let arguments = /* parsed */ .right;
                    let newCaller = /* modifyCaller */ ( /* parsed */.left,  /* oldCaller */);
                    let invokable = new Invokable( /* newCaller */);
                    return new Some(new Tuple(left));
                });
            });
        });
    }
    /* private static */ modifyCaller(state, oldCaller) {
        /* if (oldCaller instanceof DataAccess access)  */ {
            let type = /* resolveType */ ( /* access */.parent, state /*  : CompileState */);
            /* if (type instanceof FunctionType)  */ {
                return /* access */ .parent;
            }
        }
        return oldCaller /*  : Caller */;
    }
    /* private static */ resolveType(value, state) {
        /* return switch (value)  */ {
            /* case DataAccess dataAccess -> Primitive.Var */ ;
            /* case Invokable invokable -> Primitive.Var */ ;
            /* case Lambda lambda -> Primitive.Var */ ;
            /* case Not not -> Primitive.Var */ ;
            /* case Operation operation -> Primitive.Var */ ;
            /* case Placeholder placeholder -> Primitive.Var */ ;
            /* case StringValue stringValue -> Primitive.Var */ ;
            /* case SymbolValue symbolValue -> symbolValue.type */ ;
            /* case IndexValue indexValue -> Primitive.Var */ ;
        }
        /*  */ ;
    }
    /* private static */ invocationHeader(state, depth, callerString1) {
        /* if (callerString1.startsWith("new "))  */ {
            let input1 = callerString1 /*  : string */.substring("new ".length( /*  */));
            let map = /* parseType */ (state /*  : CompileState */,  /* input1 */).map((type) => {
                let right = /* type */ .right;
                return new [CompileState, Caller](left, new ConstructionCaller( /* right */));
            });
            /* if (map.isPresent())  */ {
                return /* map */ .orElse( /* null */);
            }
        }
        let tuple = /* parseValue */ (state /*  : CompileState */, callerString1 /*  : string */, depth /*  : number */);
        return new Tuple(left, right);
    }
    /* private static */ foldInvocationStart(state, c) {
        let appended = state /*  : DivideState */.append(c /*  : char */);
        /* if (c == '(')  */ {
            let enter = /* appended */ .enter( /*  */);
            /* if (enter.isShallow())  */ {
                return /* enter */ .advance( /*  */);
            }
            return /* enter */;
        }
        /* if (c == ')')  */ {
            return /* appended */ .exit( /*  */);
        }
        return /* appended */;
    }
    /* private static */ parseDataAccess(state, input, depth) {
        return /* last */ (input /*  : string */.strip( /*  */), ".", (parentString, rawProperty) => {
            let property = /* rawProperty */ .strip( /*  */);
            /* if (!isSymbol(property))  */ {
                return new None( /*  */);
            }
            let tuple = /* parseValue */ (state /*  : CompileState */,  /* parentString */, depth /*  : number */);
            let parent = /* tuple */ .right;
            let type = /* resolveType */ ( /* parent */, state /*  : CompileState */);
            /* if (type instanceof TupleType)  */ {
                /* if (property.equals("left"))  */ {
                    return new Some(new Tuple(state /*  : CompileState */, new IndexValue(new SymbolValue("0", Int))));
                }
                /* if (property.equals("right"))  */ {
                    return new Some(new Tuple(state /*  : CompileState */, new IndexValue(new SymbolValue("1", Int))));
                }
            }
            return new Some(new Tuple(left, new DataAccess( /* parent */)));
        });
    }
    /* private static */ parseString(state, input) {
        let stripped = input /*  : string */.strip( /*  */);
        /* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */ {
            return new Some(new Tuple(state /*  : CompileState */, new StringValue( /* stripped */)));
        }
        return new None( /*  */);
    }
    /* private static */ parseSymbolValue(state, value) {
        let stripped = value /*  : string */.strip( /*  */);
        /* if (isSymbol(stripped))  */ {
            /* if (state.resolve(stripped) instanceof Some(var type))  */ {
                return new Some(new Tuple(state /*  : CompileState */, new SymbolValue( /* stripped */)));
            }
            return new Some(new Tuple(state /*  : CompileState */, new Placeholder( /* stripped */)));
        }
        return new None( /*  */);
    }
    /* private static */ parseOperation(state, value, depth, infix) {
        return /* first */ (value /*  : string */, infix /*  : string */, (s, s2) => {
            let tuple = /* parseValue */ (state /*  : CompileState */,  /* s */, depth /*  : number */);
            let tuple1 = /* parseValue */ ( /* tuple */.left,  /* s2 */, depth /*  : number */);
            let left = /* tuple */ .right;
            let right = /* tuple1 */ .right;
            return new Some(new Tuple(left, new Operation(infix /*  : string */)));
        });
    }
    /* private static */ compileValues(state, params, mapper) {
        let parsed = /* parseValuesOrEmpty */ (state /*  : CompileState */, params /*  : string */, (state1, s) => new Some(mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */( /* state1 */)));
        let generated = /* generateValues */ ( /* parsed */.right);
        return new Tuple(left);
    }
    /* private static */ generateValues(elements) {
        return /* generateAll */ ( /* Main */.mergeValues, elements /*  : List<string> */);
    }
    /* private static  */ parseValuesOrEmpty(state, input, mapper) {
        return /* parseValues */ (state /*  : CompileState */, input /*  : string */, mapper /*  : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */).orElseGet(() => new Tuple(state /*  : CompileState */, empty( /*  */)));
    }
    /* private static  */ parseValues(state, input, mapper) {
        return /* getCompileStateListTuple */ (state /*  : CompileState */, input /*  : string */, /* Main */ .foldValueChar, mapper /*  : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */);
    }
    /* private static */ compileParameter(state, input) {
        /* if (input.isBlank())  */ {
            return new Tuple(state /*  : CompileState */, new Whitespace( /*  */));
        }
        return /* parseDefinition */ (state /*  : CompileState */, input /*  : string */).map((tuple) => new [CompileState, Parameter](left, right)).orElseGet(() => new Tuple(state /*  : CompileState */, new Placeholder(input /*  : string */)));
    }
    /* private static */ compileDefinition(state, input) {
        return /* parseDefinition */ (state /*  : CompileState */, input /*  : string */).map((tuple) => new Tuple(left, right.generate( /*  */))).orElseGet(() => new Tuple(state /*  : CompileState */, /* generatePlaceholder */ (input /*  : string */)));
    }
    /* private static */ mergeValues(cache, element) {
        /* if (cache.isEmpty())  */ {
            return cache /*  : StringBuilder */.append(element /*  : string */);
        }
        return cache /*  : StringBuilder */.append(", ").append(element /*  : string */);
    }
    /* private static */ createIndent(depth) {
        return "\n" + "\t".repeat(depth /*  : number */);
    }
    /* private static */ compileDefinitionStatement(input, depth, state) {
        return /* suffix */ (input /*  : string */.strip( /*  */), ";", (withoutEnd) => {
            return /* parseDefinition */ (state /*  : CompileState */,  /* withoutEnd */).map((result) => {
                let generated = /* createIndent */ (depth /*  : number */) + /* result */ .right.generate( /*  */) + ";";
                return new Tuple(left);
            });
        });
    }
    /* private static */ parseDefinition(state, input) {
        return /* last */ (input /*  : string */.strip( /*  */), " ", (beforeName, name) => {
            return /* split */ (() => /* toLast */ ( /* beforeName */, " ", /* Main */ .foldTypeSeparator), (beforeType, type) => {
                return /* suffix */ ( /* beforeType */.strip( /*  */), ">", (withoutTypeParamStart) => {
                    return /* first */ ( /* withoutTypeParamStart */, "<", (beforeTypeParams, typeParamsString) => {
                        let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple(strip( /*  */));
                        let typeParams = /* parseValuesOrEmpty */ (state /*  : CompileState */,  /* typeParamsString */, (state1, s) => new Some(apply( /* state1 */)));
                        return /* assembleDefinition */ ( /* typeParams */.left, new Some( /* beforeTypeParams */),  /* name */, /* typeParams */ .right,  /* type */);
                    });
                }).or(() => {
                    return /* assembleDefinition */ (state /*  : CompileState */, new Some( /* beforeType */),  /* name */, /* Lists */ .empty( /*  */),  /* type */);
                });
            }).or(() => {
                return /* assembleDefinition */ (state /*  : CompileState */, new None( /*  */),  /* name */, /* Lists */ .empty( /*  */),  /* beforeName */);
            });
        });
    }
    /* private static */ toLast(input, separator, folder) {
        let divisions = /* divideAll */ (input /*  : string */, folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */);
        return /* divisions */ .removeLast( /*  */).map((removed) => {
            let left = /* removed */ .left.iterate( /*  */).collect(new Joiner(separator /*  : string */)).orElse("");
            let right = /* removed */ .right;
            return new Tuple( /* left */);
        });
    }
    /* private static */ foldTypeSeparator(state, c) {
        /* if (c == ' ' && state.isLevel())  */ {
            return state /*  : DivideState */.advance( /*  */);
        }
        let appended = state /*  : DivideState */.append(c /*  : Character */);
        /* if (c == '<')  */ {
            return /* appended */ .enter( /*  */);
        }
        /* if (c == '>')  */ {
            return /* appended */ .exit( /*  */);
        }
        return /* appended */;
    }
    /* private static */ assembleDefinition(state, beforeTypeParams, name, typeParams, type) {
        return /* parseType */ (state /*  : CompileState */, type /*  : string */).map((type1) => {
            let node = new Definition(beforeTypeParams /*  : Option<string> */, right, name /*  : string */.strip( /*  */), typeParams /*  : List<string> */);
            return new Tuple(left);
        });
    }
    /* private static */ foldValueChar(state, c) {
        /* if (c == ',' && state.isLevel())  */ {
            return state /*  : DivideState */.advance( /*  */);
        }
        let appended = state /*  : DivideState */.append(c /*  : char */);
        /* if (c == '-')  */ {
            let peeked = /* appended */ .peek( /*  */);
            /* if (peeked == '>')  */ {
                return /* appended */ .popAndAppendToOption( /*  */).orElse( /* appended */);
            }
            /* else  */ {
                return /* appended */;
            }
        }
        /* if (c == '<' || c == '(' || c == '{')  */ {
            return /* appended */ .enter( /*  */);
        }
        /* if (c == '>' || c == ')' || c == '}')  */ {
            return /* appended */ .exit( /*  */);
        }
        return /* appended */;
    }
    /* private static */ compileType(state, input) {
        return /* parseType */ (state /*  : CompileState */, input /*  : string */).map((tuple) => new Tuple(left, right.generate( /*  */)));
    }
    /* private static */ parseType(state, input) {
        let stripped = input /*  : string */.strip( /*  */);
        /* if (stripped.equals("int") || stripped.equals("Integer"))  */ {
            return new Some(new Tuple(state /*  : CompileState */, Int));
        }
        /* if (stripped.equals("String"))  */ {
            return new Some(new Tuple(state /*  : CompileState */, String));
        }
        /* if (stripped.equals("var"))  */ {
            return new Some(new Tuple(state /*  : CompileState */, Var));
        }
        /* if (isSymbol(stripped))  */ {
            return new Some(new Tuple(state /*  : CompileState */, new SymbolType( /* stripped */)));
        }
        return /* template */ (state /*  : CompileState */, input /*  : string */).or(() => /* varArgs */ (state /*  : CompileState */, input /*  : string */));
    }
    /* private static */ varArgs(state, input) {
        return /* suffix */ (input /*  : string */, "...", (s) => {
            return /* parseType */ (state /*  : CompileState */,  /* s */).map((inner) => {
                let newState = /* inner */ .left;
                let child = /* inner */ .right;
                return new Tuple(new ArrayType( /* child */));
            });
        });
    }
    /* private static */ template(state, input) {
        return /* suffix */ (input /*  : string */.strip( /*  */), ">", (withoutEnd) => {
            return /* first */ ( /* withoutEnd */, "<", (base, argumentsString) => {
                let strippedBase = /* base */ .strip( /*  */);
                return /* parseValues */ (state /*  : CompileState */,  /* argumentsString */, /* Main */ .argument).map((argumentsTuple) => {
                    return /* assembleTemplate */ ( /* base */,  /* strippedBase */, /* argumentsTuple */ .left, /* argumentsTuple */ .right);
                });
            });
        });
    }
    /* private static */ assembleTemplate(base, strippedBase, state, arguments) {
        let children = arguments /*  : List<Argument> */.iterate( /*  */).map(retainType).flatMap(fromOption).collect(new ListCollector( /*  */));
        /* if (base.equals("BiFunction"))  */ {
            return new Tuple(state /*  : CompileState */, new FunctionType(of(get(0 /*  : number */), get(1 /*  : number */)), get(2 /*  : number */)));
        }
        /* if (base.equals("Function"))  */ {
            return new Tuple(state /*  : CompileState */, new FunctionType(of(get(0 /*  : number */)), get(1 /*  : number */)));
        }
        /* if (base.equals("Predicate"))  */ {
            return new Tuple(state /*  : CompileState */, new FunctionType(of(get(0 /*  : number */)), Boolean));
        }
        /* if (base.equals("Supplier"))  */ {
            return new Tuple(state /*  : CompileState */, new FunctionType(empty( /*  */), get(0 /*  : number */)));
        }
        /* if (base.equals("Tuple") && children.size() >= 2)  */ {
            return new Tuple(state /*  : CompileState */, new TupleType( /* children */));
        }
        return new Tuple(state /*  : CompileState */, new Template(strippedBase /*  : string */));
    }
    /* private static */ retainType(argument) {
        /* if (argument instanceof Type type)  */ {
            return new Some( /* type */);
        }
        /* else  */ {
            return new None( /*  */);
        }
    }
    /* private static */ argument(state, input) {
        /* if (input.isBlank())  */ {
            return new Some(new Tuple(state /*  : CompileState */, new Whitespace( /*  */)));
        }
        return /* parseType */ (state /*  : CompileState */, input /*  : string */).map((tuple) => new Tuple(left, right));
    }
    /* private static  */ last(input, infix, mapper) {
        return infix /*  : string */(input /*  : string */, infix /*  : string */, findLast, mapper /*  : (arg0 : string, arg1 : string) => Option<T> */);
    }
    /* private static */ findLast(input, infix) {
        let index = input /*  : string */.lastIndexOf(infix /*  : string */);
        /* if (index == -1)  */ {
            return new None( /*  */);
        }
        return new Some( /* index */);
    }
    /* private static  */ first(input, infix, mapper) {
        return infix /*  : string */(input /*  : string */, infix /*  : string */, findFirst, mapper /*  : (arg0 : string, arg1 : string) => Option<T> */);
    }
    /* private static  */ infix(input, infix, locator, mapper) {
        return /* split */ (() => locator /*  : (arg0 : string, arg1 : string) => Option<number> */(input /*  : string */, infix /*  : string */).map((index) => {
            let left = input /*  : string */.substring(0 /*  : number */);
            let right = input /*  : string */.substring(/* index */ +infix /*  : string */.length( /*  */));
            return new Tuple( /* left */);
        }), mapper /*  : (arg0 : string, arg1 : string) => Option<T> */);
    }
    /* private static  */ split(splitter, mapper) {
        return splitter /*  : () => Option<[string, string]> */( /*  */).flatMap((tuple) => mapper /*  : (arg0 : string, arg1 : string) => Option<T> */(left, right));
    }
    /* private static */ findFirst(input, infix) {
        let index = input /*  : string */.indexOf(infix /*  : string */);
        /* if (index == -1)  */ {
            return new None( /*  */);
        }
        return new Some( /* index */);
    }
    /* private static */ generatePlaceholder(input) {
        let replaced = input /*  : string */.replace("/*", "content-start").replace("*/", "content-end");
        return "/* " + replaced + " */";
    } /*

    private enum Primitive implements Type {
        Int("number"),
        String("string"),
        Boolean("boolean"),
        Var("var");

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
