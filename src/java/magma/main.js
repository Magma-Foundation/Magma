"use strict";
var __esDecorate = (this && this.__esDecorate) || function (ctor, descriptorIn, decorators, contextIn, initializers, extraInitializers) {
    function accept(f) { if (f !== void 0 && typeof f !== "function") throw new TypeError("Function expected"); return f; }
    var kind = contextIn.kind, key = kind === "getter" ? "get" : kind === "setter" ? "set" : "value";
    var target = !descriptorIn && ctor ? contextIn["static"] ? ctor : ctor.prototype : null;
    var descriptor = descriptorIn || (target ? Object.getOwnPropertyDescriptor(target, contextIn.name) : {});
    var _, done = false;
    for (var i = decorators.length - 1; i >= 0; i--) {
        var context = {};
        for (var p in contextIn) context[p] = p === "access" ? {} : contextIn[p];
        for (var p in contextIn.access) context.access[p] = contextIn.access[p];
        context.addInitializer = function (f) { if (done) throw new TypeError("Cannot add initializers after decoration has completed"); extraInitializers.push(accept(f || null)); };
        var result = (0, decorators[i])(kind === "accessor" ? { get: descriptor.get, set: descriptor.set } : descriptor[key], context);
        if (kind === "accessor") {
            if (result === void 0) continue;
            if (result === null || typeof result !== "object") throw new TypeError("Object expected");
            if (_ = accept(result.get)) descriptor.get = _;
            if (_ = accept(result.set)) descriptor.set = _;
            if (_ = accept(result.init)) initializers.unshift(_);
        }
        else if (_ = accept(result)) {
            if (kind === "field") initializers.unshift(_);
            else descriptor[key] = _;
        }
    }
    if (target) Object.defineProperty(target, contextIn.name, descriptor);
    done = true;
};
var __runInitializers = (this && this.__runInitializers) || function (thisArg, initializers, value) {
    var useValue = arguments.length > 2;
    for (var i = 0; i < initializers.length; i++) {
        value = useValue ? initializers[i].call(thisArg, value) : initializers[i].call(thisArg);
    }
    return useValue ? value : void 0;
};
Symbol[input = R];
Option;
isPresent();
boolean;
orElse(other, T);
T;
filter(predicate, (arg0 = T) => );
Boolean;
Option;
orElseGet(supplier, () => Symbol[input = T]);
T;
or(other, () => Template[base = Option, arguments = magma.Main$Lists$JVMList], );
Option;
flatMap(mapper, (arg0 = T) => );
Template[base = Option, arguments = magma.Main$Lists$JVMList];
f8f9dd6;
Option;
isEmpty();
boolean;
Symbol[input = R];
R;
map(mapper, (arg0 = T) => );
Symbol[input = R];
Iterator;
collect(collector, (Collector));
R;
filter(predicate, (arg0 = T) => );
Boolean;
Iterator;
next();
Option;
flatMap(f, (arg0 = T) => );
Template[base = Iterator, arguments = magma.Main$Lists$JVMList];
a1cd57;
Iterator;
/* private */ class Some {
    constructor(value) {
    }
    Option() {
        return new Some(mapper.apply(this.value));
    }
    /* @Override
        public */ isPresent() {
        return true;
    }
    /* @Override
        public */ orElse(other) {
        return this.value;
    }
    Option() {
        /* if (predicate.test(this.value))  */ {
            return this;
        }
        return new None();
    }
}
input = R;
{
    return this.value;
}
/* @Override
    public */ or(other, () => Template[base = Option, arguments = magma.Main$Lists$JVMList], );
Option < T > {
    return: this
};
/* @Override
    public  */ flatMap(mapper, (arg0 = T) => );
Template[base = Option, arguments = magma.Main$Lists$JVMList];
Option < R > {
    return: mapper.apply(this.value)
};
/* @Override
    public */ isEmpty();
boolean;
{
    return false;
}
/* private static */ class None {
    Option() {
        return new None();
    }
    /* @Override
        public */ isPresent() {
        return false;
    }
    /* @Override
        public */ orElse(other) {
        return other;
    }
    Option() {
        return new None();
    }
}
input = R;
{
    return supplier.get();
}
/* @Override
    public */ or(other, () => Template[base = Option, arguments = magma.Main$Lists$JVMList], , fb4f649);
Option < T > {
    return: other.get()
};
/* @Override
    public  */ flatMap(mapper, (arg0 = T) => );
Template[base = Option, arguments = magma.Main$Lists$JVMList];
Option < R > {
    return: new None()
};
/* @Override
    public */ isEmpty();
boolean;
{
    return true;
}
/* private static */ class SingleHead {
    SingleHead(value) {
        let /* this.value  */ = value;
        let /* this.retrieved  */ = false;
    }
    /* @Override
        public */ next() {
        /* if (this.retrieved)  */ {
            return new None();
        }
        let /* this.retrieved  */ = true;
        return new Some(this.value);
    }
}
/* private static */ class EmptyHead {
    /* @Override
        public */ next() {
        return new None();
    }
}
/* private */ class HeadedIterator {
    constructor(head) {
        this.arg1 = T;
    }
}
input = R;
{
    let current = initial;
    /* while (true) {{
            R finalCurrent = current;
            var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
            if (optional.isPresent()) {{
                current = optional.orElse(null);
            }
            else  */ {
        return current; /* } */
    }
}
/* @Override
    public  */ map(mapper, (arg0 = T) => );
Symbol[input = R];
Iterator < R > {
    return: new HeadedIterator(() => this.head.next().map(mapper))
};
/* @Override
    public  */ collect(collector, (Collector));
R;
{
    return this.fold(collector.createInitial(), collector.fold);
}
/* @Override
    public */ filter(predicate, (arg0 = T) => );
Boolean;
Iterator < T > {
    return: this.flatMap((element) => {
        /* if (predicate.test(element))  */ {
            return new HeadedIterator(new SingleHead(element));
        }
        return new HeadedIterator(new EmptyHead());
    })
};
/* @Override
    public */ next();
Option < T > {
    return: this.head.next()
};
/* @Override
    public  */ flatMap(f, (arg0 = T) => );
Template[base = Iterator, arguments = magma.Main$Lists$JVMList];
d591d15;
Iterator < R > {
    return: new HeadedIterator(new FlatMapHead(this.head, f))
};
/* private static */ class RangeHead /*  */ {
    RangeHead(length) {
        let /* this.length  */ = length;
    }
    /* @Override
        public */ next() {
        /* if (this.counter < this.length)  */ {
            let value = this.counter;
            /* this.counter++ */ ;
            return new Some(value);
        }
        return new None();
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
            public */ addLast(element) {
        /* this.elements.add(element) */ ;
        return this;
    }
    /* @Override
            public */ iterate() {
        return this.iterateWithIndices().map(Tuple.right);
    }
    /* @Override
            public */ removeLast() {
        /* if (this.elements.isEmpty())  */ {
            return new None();
        }
        let slice = this.elements.subList(0, this.elements.size() - 1);
        let last = this.elements.getLast();
        return new Some(new [(List), T](new JVMList(slice), last));
    }
    /* @Override
            public */ get(index) {
        return this.elements.get(index);
    }
    /* @Override
            public */ size() {
        return this.elements.size();
    }
    /* @Override
            public */ addAll(other) {
        let initial = this;
        return other.iterate().fold(initial, List.addLast);
    }
    /* @Override
            public */ isEmpty() {
        return this.elements.isEmpty();
    }
    /* @Override
            public */ addFirst(element) {
        /* this.elements.addFirst(element) */ ;
        return this;
    }
    /* @Override
            public */ iterateWithIndices() {
        return new HeadedIterator(new RangeHead(this.elements.size())).map((index) => new Tuple(index, this.elements.get(index)));
    }
}
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return new JVMList();
    }
    /* public static  */ of(elements) {
        return new JVMList(new ArrayList(Arrays.asList(elements)));
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
        let /* this.segments  */ = this.segments.addLast(this.buffer.toString());
        let /* this.buffer  */ = new StringBuilder();
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
        return /* this.depth == 0 */;
    }
    /* public */ exit() {
        /* this.depth-- */ ;
        return this;
    }
    /* public */ isShallow() {
        return /* this.depth == 1 */;
    }
    /* public */ pop() {
        /* if (this.index < this.input.length())  */ {
            let c = this.input.charAt(this.index);
            return new Some(new Tuple(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
        }
        return new None();
    }
    /* public */ popAndAppendToTuple() {
        return this.pop().map((tuple) => new Tuple(tuple.left, tuple.right.append(tuple.left)));
    }
    /* public */ popAndAppendToOption() {
        return this.popAndAppendToTuple().map(Tuple.right);
    }
    /* public */ peek() {
        return this.input.charAt(this.index);
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
        return new None();
    }
    /* @Override
        public */ fold(current, element) {
        return new Some(current.map((inner) => inner + this.delimiter + element).orElse(element));
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return Lists.empty();
    }
    /* @Override
        public */ fold(current, element) {
        return current.addLast(element);
    }
}
/* private */ class Tuple {
}
/* private static */ let FlatMapHead = (() => {
    var _a;
    let _ccd43c2_decorators;
    let _ccd43c2_initializers = [];
    let _ccd43c2_extraInitializers = [];
    let _aa8f0b4_decorators;
    let _aa8f0b4_initializers = [];
    let _aa8f0b4_extraInitializers = [];
    return _a = class FlatMapHead {
            constructor() {
                this.ccd43c2 = __runInitializers(this, _ccd43c2_initializers, void 0);
                /* private final */ this.head = __runInitializers(this, _ccd43c2_extraInitializers);
                this.aa8f0b4 = __runInitializers(this, _aa8f0b4_initializers, void 0);
                this. = __runInitializers(this, _aa8f0b4_extraInitializers);
            }
            ;
        },
        base = Iterator,
        arguments = magma.Main$Lists$JVMList,
        _ccd43c2_decorators = [5],
        base = Iterator,
        arguments = magma.Main$Lists$JVMList,
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _aa8f0b4_decorators = [4];
            __esDecorate(null, null, _ccd43c2_decorators, { kind: "field", name: "ccd43c2", static: false, private: false, access: { has: obj => "ccd43c2" in obj, get: obj => obj.ccd43c2, set: (obj, value) => { obj.ccd43c2 = value; } }, metadata: _metadata }, _ccd43c2_initializers, _ccd43c2_extraInitializers);
            __esDecorate(null, null, _aa8f0b4_decorators, { kind: "field", name: "aa8f0b4", static: false, private: false, access: { has: obj => "aa8f0b4" in obj, get: obj => obj.aa8f0b4, set: (obj, value) => { obj.aa8f0b4 = value; } }, metadata: _metadata }, _aa8f0b4_initializers, _aa8f0b4_extraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
{
    let /* this.mapper  */ = mapper;
    let /* this.current  */ = new None();
    let /* this.head  */ = head;
}
/* @Override
    public */ next();
Option < R > {
/* while (true) {{
        if (this.current.isPresent()) {{
            Iterator<R> inner = this.current.orElse(null);
            Option<R> maybe = inner.next();
            if (maybe.isPresent()) {{
                return maybe;
            }
            else {{
                this.current = new None<>();
            }
        }

        Option<T> outer = this.head.next();
        if (outer.isPresent()) {{
            this.current = outer.map(this.mapper);
        }
        else  */ };
/* while (true) {{
        if (this.current.isPresent()) {{
            Iterator<R> inner = this.current.orElse(null);
            Option<R> maybe = inner.next();
            if (maybe.isPresent()) {{
                return maybe;
            }
            else {{
                this.current = new None<>();
            }
        }

        Option<T> outer = this.head.next();
        if (outer.isPresent()) {{
            this.current = outer.map(this.mapper);
        }
        else  */ {
    return new None(); /* } */
}
/* private */ class Symbol /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return this.input;
    }
}
/* private static */ class ArrayType /*  */ {
    ArrayType(right) {
        let /* this.right  */ = right;
    }
    /* @Override
        public */ generate() {
        return this.right.generate() + "[]";
    }
}
/* private static */ class Whitespace /*  */ {
}
/* private static */ class Iterators /*  */ {
    /* public static  */ fromOption(option) {
        return new HeadedIterator(/* option.<Head<T>>map */ (SingleHead.new).orElseGet(EmptyHead.new));
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
    }
    /* @Override
        public */ generate() {
        let joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left + " : " + pair.right).collect(new Joiner(", ")).orElse("");
        return "(" + joined + ") => " + this.returns();
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).orElse("");
        return "[" + joinedArguments + "]";
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        return this.base + joinedArguments;
    }
}
/* public */ let Main = (() => {
    var _a;
    let _b_decorators;
    let _b_initializers = [];
    let _b_extraInitializers = [];
    return _a = class Main /*  */ {
            /* private */ CompileState(structures) {
                /* public CompileState()  */ {
                    /* this(Lists.empty()) */ ;
                }
                /* public CompileState addStructure(String structure)  */ {
                    return new CompileState(this.structures.addLast(structure));
                }
            }
            /* private */ Definition(maybeBefore, type, name, typeParams) {
                /* private String generate()  */ {
                    return this.generateWithParams("");
                }
                /* public String generateWithParams(String params) {{
                    var joined = this.typeParams.iterate()
                            .collect(new Joiner())
                            .map(inner -> "<" + inner + ">")
                            .orElse("");
        
                    var before = this.maybeBefore
                            .filter(value -> !value.isEmpty())
                            .map(Main::generatePlaceholder)
                            .map(inner -> inner + " ")
                            .orElse("");
        
                    var s = before + this.name + joined + params;
                    if (this.type.equals("var"))  */ {
                    return s;
                    /* }
                    return s + " : " + this.type */ ;
                }
            }
            /* public static */ main() {
                /* try  */ {
                    let parent = Paths.get(".", "src", "java", "magma");
                    let source = parent.resolve("Main.java");
                    let target = parent.resolve("main.ts");
                    let input = Files.readString(source);
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
                let tuple = compileStatements(new CompileState(), input, _a.compileRootSegment);
                let joined = tuple.left.structures.iterate().collect(new Joiner()).orElse("");
                return joined + tuple.right;
            }
            constructor() {
                this.b = __runInitializers(this, _b_initializers, void 0);
                __runInitializers(this, _b_extraInitializers);
            }
        },
        arguments = magma.Main$Lists$JVMList,
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _b_decorators = [7960847];
            __esDecorate(null, null, _b_decorators, { kind: "field", name: "b", static: false, private: false, access: { has: obj => "b" in obj, get: obj => obj.b, set: (obj, value) => { obj.b = value; } }, metadata: _metadata }, _b_initializers, _b_extraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
{
    let parsed = parseStatements(state, input, mapper);
    return new Tuple(parsed.left, generateStatements(parsed.right));
}
/* private static */ generateStatements(statements, (List));
string;
{
    return generateAll(Main.mergeStatements, statements);
}
/* private static */ parseStatements(state, CompileState, input, string, mapper, (arg0 = CompileState) => , arg1, String);
TupleType[arguments = magma.Main$Lists$JVMList];
a6824be;
[CompileState, (List)];
{
    return parseAll(state, input, Main.foldStatementChar, mapper);
}
/* private static */ generateAll(merger, (arg0 = StringBuilder) => , arg1, String);
Symbol[input = StringBuilder], elements;
List;
string;
{
    return elements.iterate().fold(new StringBuilder(), merger).toString();
}
/* private static  */ parseAll(state, CompileState, input, string, folder, (arg0 = DivideState) => , arg1, Symbol[input = Character]);
Symbol[input = DivideState], mapper;
(arg0 = CompileState) => ;
arg1: String;
TupleType[arguments = magma.Main$Lists$JVMList];
c8da962;
[CompileState, (List)];
{
    return getCompileStateListTuple(state, input, folder, (state1, s) => new Some(mapper.apply(state1, s))).orElseGet(() => new Tuple(state, Lists.empty()));
}
/* private static  */ getCompileStateListTuple(state, CompileState, input, string, folder, (arg0 = DivideState) => , arg1, Symbol[input = Character]);
Symbol[input = DivideState], mapper;
(arg0 = CompileState) => ;
arg1: String;
Template[base = Option, arguments = magma.Main$Lists$JVMList];
ddf17;
Option < [CompileState, (List)] > {
    let, initial: (Option) = new Some(new Tuple(state, Lists.empty())),
    return: divideAll(input, folder).iterate().fold(initial, (tuple, element) => {
        return tuple.flatMap((inner) => {
            let state1 = inner.left;
            let right = inner.right;
            return mapper.apply(state1, element).map((applied) => {
                return new Tuple(applied.left, right.addLast(applied.right));
            });
        });
    })
};
/* private static */ mergeStatements(stringBuilder, StringBuilder, str, string);
StringBuilder;
{
    return stringBuilder.append(str);
}
/* private static */ divideAll(input, string, folder, (arg0 = DivideState) => , arg1, Symbol[input = Character]);
Symbol[input = DivideState];
List < string > {
    let, current = new DivideState(input)
};
/* while (true) {{
    var maybePopped = current.pop().map(tuple -> {{
        return foldSingleQuotes(tuple)
                .or(() -> foldDoubleQuotes(tuple))
                .orElseGet(() -> folder.apply(tuple.right, tuple.left));
    });

    if (maybePopped.isPresent()) {{
        current = maybePopped.orElse(current);
    }
    else  */ {
    /* break */ ; /* } */
}
return current.advance().segments;
/* private static */ foldDoubleQuotes(tuple, [Character, DivideState]);
Option < DivideState > {
/* if (tuple.left == '\"') {{
    var current = tuple.right.append(tuple.left);
    while (true) {{
        var maybePopped = current.popAndAppendToTuple();
        if (maybePopped.isEmpty()) {{
            break;
        }

        var popped = maybePopped.orElse(null);
        current = popped.right;

        if (popped.left == '\\') {{
            current = current.popAndAppendToOption().orElse(current);
        }
        if (popped.left == '\"')  */ };
/* if (tuple.left == '\"') {{
    var current = tuple.right.append(tuple.left);
    while (true) {{
        var maybePopped = current.popAndAppendToTuple();
        if (maybePopped.isEmpty()) {{
            break;
        }

        var popped = maybePopped.orElse(null);
        current = popped.right;

        if (popped.left == '\\') {{
            current = current.popAndAppendToOption().orElse(current);
        }
        if (popped.left == '\"')  */ {
    /* break */ ;
    /* }
    }

    return new Some<>(current) */ ;
}
return new None();
/* private static */ foldSingleQuotes(tuple, [Character, DivideState]);
Option < DivideState > {
/* if (tuple.left != '\'')  */ };
/* if (tuple.left != '\'')  */ {
    return new None();
}
let appended = tuple.right.append(tuple.left);
return appended.popAndAppendToTuple().map(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption);
/* private static */ foldEscaped(escaped, [Character, DivideState]);
DivideState;
{
    /* if (escaped.left == '\\')  */ {
        return escaped.right.popAndAppendToOption().orElse(escaped.right);
    }
    return escaped.right;
}
/* private static */ foldStatementChar(state, DivideState, c, char);
DivideState;
{
    let append = state.append(c);
    /* if (c == ';' && append.isLevel())  */ {
        return append.advance();
    }
    /* if (c == '}' && append.isShallow())  */ {
        return append.advance().exit();
    }
    /* if (c == '{' || c == '(')  */ {
        return append.enter();
    }
    /* if (c == '}' || c == ')')  */ {
        return append.exit();
    }
    return append;
}
/* private static */ compileRootSegment(state, CompileState, input, string);
[CompileState, string];
{
    let stripped = input.strip();
    /* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */ {
        return new Tuple(state, "");
    }
    return compileClass(stripped, 0, state).orElseGet(() => new Tuple(state, generatePlaceholder(stripped)));
}
/* private static */ compileClass(stripped, string, depth, number, state, CompileState);
Option < [CompileState, string] > {
    return: structure(stripped, "class ", "class ", state)
};
/* private static */ structure(stripped, string, sourceInfix, string, targetInfix, string, state, CompileState);
Option < [CompileState, string] > {
    return: first(stripped, sourceInfix, (beforeInfix, right) => {
        return first(right, "{", (beforeContent, withEnd) => {
            let strippedWithEnd = withEnd.strip();
            return suffix(strippedWithEnd, "}", (content1) => {
                return first(beforeContent, " implements ", (s, s2) => {
                    return structureWithMaybeParams(targetInfix, state, beforeInfix, s, content1);
                }).or(() => {
                    return structureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1);
                });
            });
        });
    })
};
/* private static */ structureWithMaybeParams(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, string] > {
    return: suffix(beforeContent, ")", (s) => {
        return first(s, "(", (s1, s2) => {
            let parsed = parseParameters(state, s2);
            return getOred(targetInfix, parsed.left, beforeInfix, s1, content1, parsed.right);
        });
    }).or(() => {
        return getOred(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
    })
};
/* private static */ getOred(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, params, (List));
Option < [CompileState, string] > {
    return: first(beforeContent, "<", (name, withTypeParams) => {
        return first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
            let /* final */ compileStateStringTupleBiFunction, arg1;
        });
        TupleType[arguments = magma.Main$Lists$JVMList];
        c13da15;
        (state1, s) => new Tuple(state1, s.strip());
        let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(compileStateStringTupleBiFunction.apply(state1, s)));
        return assemble(typeParams.left, targetInfix, beforeInfix, name, content1, typeParams.right, afterTypeParams, params);
    })
};
or(() => {
    return assemble(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "", params);
});
/* private static */ assemble(state, CompileState, targetInfix, string, beforeInfix, string, rawName, string, content, string, typeParams, (List), afterTypeParams, string, params, (List));
Option < [CompileState, string] > {
    let, name = rawName.strip()
};
/* if (!isSymbol(name))  */ {
    return new None();
}
let joinedTypeParams = typeParams.iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
let parsed = parseStatements(state, content, (state0, input) => compileClassSegment(state0, input, 1));
/* List<String> parsed1 */ ;
/* if (params.isEmpty())  */ {
    let /* parsed1  */ = parsed.right;
}
/* else  */ {
    let joined = params.iterate().collect(new Joiner(", ")).orElse("");
    let constructorIndent = createIndent(1);
    let /* parsed1  */ = parsed.right.addFirst(constructorIndent + "constructor (" + joined + ") {" + constructorIndent + "}\n");
}
let parsed2 = parsed1.iterate().collect(new Joiner()).orElse("");
let generated = generatePlaceholder(beforeInfix.strip()) + targetInfix + name + joinedTypeParams + generatePlaceholder(afterTypeParams) + " {" + parsed2 + "\n}\n";
return new Some(new Tuple(parsed.left.addStructure(generated), ""));
/* private static */ isSymbol(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) {{
        var c = input.charAt(i);
        if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */ {
        /* continue */ ;
        /* }
        return false */ ;
    }
    return true;
}
/* private static  */ suffix(input, string, suffix, string, mapper, (arg0) => Template[base = Option, arguments = magma.Main$Lists$JVMList], (), fd);
Option < T > {
/* if (!input.endsWith(suffix))  */ };
/* if (!input.endsWith(suffix))  */ {
    return new None();
}
let slice = input.substring(0, input.length() - suffix.length());
return mapper.apply(slice);
/* private static */ compileClassSegment(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    return compileWhitespace(input, state).or(() => compileClass(input, depth, state)).or(() => structure(input, "interface ", "interface ", state)).or(() => structure(input, "record ", "class ", state)).or(() => method(state, input, depth)).or(() => compileDefinitionStatement(input, depth, state)).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
}
/* private static */ compileWhitespace(input, string, state, CompileState);
Option < [CompileState, string] > {
/* if (input.isBlank())  */ };
/* if (input.isBlank())  */ {
    return new Some(new Tuple(state, ""));
}
return new None();
/* private static */ method(state, CompileState, input, string, depth, number);
Option < [CompileState, string] > {
    return: first(input, "(", (definition, withParams) => {
        return first(withParams, ")", (params, rawContent) => {
            let definitionTuple = parseDefinition(state, definition).map((definition1) => {
                let paramsTuple = compileParameters(state, params);
                let generated = definition1.right.generateWithParams("(" + paramsTuple.right + ")");
                return new Tuple(paramsTuple.left, generated);
            }).orElseGet(() => new Tuple(state, generatePlaceholder(definition)));
            let content = rawContent.strip();
            let indent = createIndent(depth);
            /* if (content.equals(";"))  */ {
                let s = indent + definitionTuple.right + ";";
                return new Some(new Tuple(definitionTuple.left, s));
            }
            /* if (content.startsWith("{") && content.endsWith("}"))  */ {
                let substring = content.substring(1, content.length() - 1);
                let statementsTuple = compileFunctionSegments(definitionTuple.left, substring, depth);
                let generated = indent + definitionTuple.right + " {" + statementsTuple.right + indent + "}";
                return new Some(new Tuple(statementsTuple.left, generated));
            }
            return new None();
        });
    })
};
/* private static */ compileParameters(state, CompileState, params, string);
[CompileState, string];
{
    let parsed = parseParameters(state, params);
    let generated = generateValues(parsed.right);
    return new Tuple(parsed.left, generated);
}
/* private static */ parseParameters(state, CompileState, params, string);
[CompileState, (List)];
{
    return parseValuesOrEmpty(state, params, (state1, s) => new Some(compileParameter(state1, s)));
}
/* private static */ compileFunctionSegments(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    return compileStatements(state, input, (state1, input1) => compileFunctionSegment(state1, input1, depth + 1));
}
/* private static */ compileFunctionSegment(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let stripped = input.strip();
    /* if (stripped.isEmpty())  */ {
        return new Tuple(state, "");
    }
    return suffix(stripped, ";", (s) => {
        let tuple = statementValue(state, s, depth);
        return new Some(new Tuple(tuple.left, createIndent(depth) + tuple.right + ";"));
    }).or(() => {
        return block(state, depth, stripped);
    }).orElseGet(() => {
        return new Tuple(state, generatePlaceholder(stripped));
    });
}
/* private static */ block(state, CompileState, depth, number, stripped, string);
Option < [CompileState, string] > {
    return: suffix(stripped, "}", (withoutEnd) => {
        return split(() => {
            return toLast(withoutEnd, "{", Main.foldBlockStart);
        }, (beforeContent, content) => {
            return suffix(beforeContent, "{", (s) => {
                let compiled = compileFunctionSegments(state, content, depth);
                let indent = createIndent(depth);
                return new Some(new Tuple(compiled.left, indent + generatePlaceholder(s) + "{" + compiled.right + indent + "}"));
            });
        });
    })
};
/* private static */ foldBlockStart(state, DivideState, c, Character);
DivideState;
{
    let appended = state.append(c);
    /* if (c == '{')  */ {
        return appended.advance();
    }
    return appended;
}
/* private static */ statementValue(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let stripped = input.strip();
    /* if (stripped.startsWith("return "))  */ {
        let value = stripped.substring("return ".length());
        let tuple = value(state, value, depth);
        return new Tuple(tuple.left, "return " + tuple.right);
    }
    return first(stripped, "=", (s, s2) => {
        let definitionTuple = compileDefinition(state, s);
        let valueTuple = value(definitionTuple.left, s2, depth);
        return new Some(new Tuple(valueTuple.left, "let " + definitionTuple.right + " = " + valueTuple.right));
    }).orElseGet(() => {
        return new Tuple(state, generatePlaceholder(stripped));
    });
}
/* private static */ value(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    return lambda(state, input, depth).or(() => stringValue(state, input)).or(() => dataAccess(state, input, depth)).or(() => symbolValue(state, input)).or(() => invocation(state, input, depth)).or(() => operation(state, input, depth, "+")).or(() => operation(state, input, depth, "-")).or(() => digits(state, input)).or(() => not(state, input, depth)).or(() => methodReference(state, input, depth)).orElseGet(() => new [CompileState, string](state, generatePlaceholder(input)));
}
/* private static */ methodReference(state, CompileState, input, string, depth, number);
Option < [CompileState, string] > {
    return: last(input, "::", (s, s2) => {
        let value = value(state, s, depth);
        return new Some(new Tuple(value.left, value.right + "." + s2));
    })
};
/* private static */ not(state, CompileState, input, string, depth, number);
Option < [CompileState, string] > {
    let, stripped = input.strip()
};
/* if (stripped.startsWith("!"))  */ {
    let slice = stripped.substring(1);
    let value = value(state, slice, depth);
    return new Some(new Tuple(value.left, "!" + value.right));
}
return new None();
/* private static */ lambda(state, CompileState, input, string, depth, number);
Option < [CompileState, string] > {
    return: first(input, "->", (beforeArrow, valueString) => {
        let strippedBeforeArrow = beforeArrow.strip();
        /* if (isSymbol(strippedBeforeArrow))  */ {
            return assembleLambda(state, Lists.of(strippedBeforeArrow), valueString, depth);
        }
        /* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */ {
            let parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main.foldValueChar);
            return assembleLambda(state, parameterNames, valueString, depth);
        }
        return new None();
    })
};
/* private static */ assembleLambda(state, CompileState, paramNames, (List), valueString, string, depth, number);
Some < [CompileState, string] > {
    let, strippedValueString = valueString.strip()
};
/* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */ {
    let /* value  */ = compileFunctionSegments(state, strippedValueString.substring(1, strippedValueString.length() - 1), depth);
    let /* s  */ = "{" + value.right + createIndent(depth) + "}";
}
/* else  */ {
    let /* value  */ = value(state, strippedValueString, depth);
    let /* s  */ = value.right;
}
let joined = paramNames.iterate().collect(new Joiner(", ")).orElse("");
return new Some(new Tuple(value.left, "(" + joined + ") => " + s));
/* private static */ digits(state, CompileState, input, string);
Option < [CompileState, string] > {
    let, stripped = input.strip()
};
/* if (isNumber(stripped))  */ {
    return new Some(new Tuple(state, stripped));
}
return new None();
/* private static */ isNumber(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) {{
        var c = input.charAt(i);
        if (Character.isDigit(c))  */ {
        /* continue */ ;
        /* }
        return false */ ;
    }
    return true;
}
/* private static */ invocation(state, CompileState, input, string, depth, number);
Option < [CompileState, string] > {
    return: suffix(input.strip(), ")", (withoutEnd) => {
        return split(() => toLast(withoutEnd, "", Main.foldInvocationStart), (callerWithEnd, argumentsString) => {
            return suffix(callerWithEnd, "(", (callerString) => {
                let callerString1 = callerString.strip();
                let callerTuple = invocationHeader(state, depth, callerString1);
                let argumentsTuple = compileValues(callerTuple.left, argumentsString, (state1, input1) => value(state1, input1, depth));
                return new Some(new Tuple(argumentsTuple.left, callerTuple.right + "(" + argumentsTuple.right + ")"));
            });
        });
    })
};
/* private static */ invocationHeader(state, CompileState, depth, number, callerString1, string);
[CompileState, string];
{
    /* if (callerString1.startsWith("new ")) {{
        String input1 = callerString1.substring("new ".length());
        var map = compileType(state, input1).map(type -> {{
            return new Tuple<>(type.left, "new " + type.right);
        });

        if (map.isPresent())  */ {
        return map.orElse(null); /* } */
    }
    return value(state, callerString1, depth);
}
/* private static */ foldInvocationStart(state, DivideState, c, char);
DivideState;
{
    let appended = state.append(c);
    /* if (c == '(') {{
        var enter = appended.enter();
        if (enter.isShallow())  */ {
        return enter.advance();
        /* }
        return enter */ ;
    }
    /* if (c == ')')  */ {
        return appended.exit();
    }
    return appended;
}
/* private static */ dataAccess(state, CompileState, input, string, depth, number);
Option < [CompileState, string] > {
    return: last(input.strip(), ".", (parent, property) => {
        /* if (!isSymbol(property))  */ {
            return new None();
        }
        let value = value(state, parent, depth);
        return new Some(new Tuple(value.left, value.right + "." + property));
    })
};
/* private static */ stringValue(state, CompileState, input, string);
Option < [CompileState, string] > {
    let, stripped = input.strip()
};
/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */ {
    return new Some(new Tuple(state, stripped));
}
return new None();
/* private static */ symbolValue(state, CompileState, value, string);
Option < [CompileState, string] > {
    let, stripped = value.strip()
};
/* if (isSymbol(stripped))  */ {
    return new Some(new Tuple(state, stripped));
}
return new None();
/* private static */ operation(state, CompileState, value, string, depth, number, infix, string);
Option < [CompileState, string] > {
    return: first(value, infix, (s, s2) => {
        let leftTuple = value(state, s, depth);
        let rightTuple = value(leftTuple.left, s2, depth);
        return new Some(new Tuple(rightTuple.left, leftTuple.right + " " + infix + " " + rightTuple.right));
    })
};
/* private static */ compileValues(state, CompileState, params, string, mapper, (arg0 = CompileState) => , arg1, String);
TupleType[arguments = magma.Main$Lists$JVMList];
c8;
[CompileState, string];
{
    let parsed = parseValuesOrEmpty(state, params, (state1, s) => new Some(mapper.apply(state1, s)));
    let generated = generateValues(parsed.right);
    return new Tuple(parsed.left, generated);
}
/* private static */ generateValues(elements, (List));
string;
{
    return generateAll(Main.mergeValues, elements);
}
/* private static  */ parseValuesOrEmpty(state, CompileState, input, string, mapper, (arg0 = CompileState) => , arg1, String);
Template[base = Option, arguments = magma.Main$Lists$JVMList];
d68;
[CompileState, (List)];
{
    return parseValues(state, input, mapper).orElseGet(() => new Tuple(state, Lists.empty()));
}
/* private static  */ parseValues(state, CompileState, input, string, mapper, (arg0 = CompileState) => , arg1, String);
Template[base = Option, arguments = magma.Main$Lists$JVMList];
b192d32;
Option < [CompileState, (List)] > {
    return: getCompileStateListTuple(state, input, Main.foldValueChar, mapper)
};
/* private static */ compileParameter(state, CompileState, input, string);
[CompileState, string];
{
    /* if (input.isBlank())  */ {
        return new Tuple(state, "");
    }
    return compileDefinition(state, input);
}
/* private static */ compileDefinition(state, CompileState, input, string);
[CompileState, string];
{
    return parseDefinition(state, input).map((tuple) => new Tuple(tuple.left, tuple.right.generate())).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
}
/* private static */ mergeValues(cache, StringBuilder, element, string);
StringBuilder;
{
    /* if (cache.isEmpty())  */ {
        return cache.append(element);
    }
    return cache.append(", ").append(element);
}
/* private static */ createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat(depth);
}
/* private static */ compileDefinitionStatement(input, string, depth, number, state, CompileState);
Option < [CompileState, string] > {
    return: suffix(input.strip(), ";", (withoutEnd) => {
        return parseDefinition(state, withoutEnd).map((result) => {
            let generated = createIndent(depth) + result.right.generate() + ";";
            return new Tuple(result.left, generated);
        });
    })
};
/* private static */ parseDefinition(state, CompileState, input, string);
Option < [CompileState, Definition] > {
    return: last(input.strip(), " ", (beforeName, name) => {
        return split(() => toLast(beforeName, " ", Main.foldTypeSeparator), (beforeType, type) => {
            return suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
                return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
                    let /* final */ compileStateStringTupleBiFunction, arg1;
                });
                TupleType[arguments = magma.Main$Lists$JVMList];
                f65612;
                (state1, s) => new Tuple(state1, s.strip());
                let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(compileStateStringTupleBiFunction.apply(state1, s)));
                return assembleDefinition(typeParams.left, new Some(beforeTypeParams), name, typeParams.right, type);
            });
        }).or(() => {
            return assembleDefinition(state, new Some(beforeType), name, Lists.empty(), type);
        });
    }).or(() => {
        return assembleDefinition(state, new None(), name, Lists.empty(), beforeName);
    })
};
;
/* private static */ toLast(input, string, separator, string, folder, (arg0 = DivideState) => , arg1, Symbol[input = Character]);
Symbol[input = DivideState];
Option < [string, string] > {
    let, divisions = divideAll(input, folder),
    return: divisions.removeLast().map((removed) => {
        let left = removed.left.iterate().collect(new Joiner(separator)).orElse("");
        let right = removed.right;
        return new Tuple(left, right);
    })
};
/* private static */ foldTypeSeparator(state, DivideState, c, Character);
DivideState;
{
    /* if (c == ' ' && state.isLevel())  */ {
        return state.advance();
    }
    let appended = state.append(c);
    /* if (c == '<')  */ {
        return appended.enter();
    }
    /* if (c == '>')  */ {
        return appended.exit();
    }
    return appended;
}
/* private static */ assembleDefinition(state, CompileState, beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
Option < [CompileState, Definition] > {
    let, type1 = typeOrPlaceholder(state, type),
    let, node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams),
    return: new Some(new Tuple(type1.left, node))
};
/* private static */ foldValueChar(state, DivideState, c, char);
DivideState;
{
    /* if (c == ',' && state.isLevel())  */ {
        return state.advance();
    }
    let appended = state.append(c);
    /* if (c == '-') {{
        var peeked = appended.peek();
        if (peeked == '>') {{
            return appended.popAndAppendToOption().orElse(appended);
        }
        else  */ {
        return appended; /* } */
    }
    /* if (c == '<' || c == '(' || c == '{')  */ {
        return appended.enter();
    }
    /* if (c == '>' || c == ')' || c == '}')  */ {
        return appended.exit();
    }
    return appended;
}
/* private static */ typeOrPlaceholder(state, CompileState, input, string);
[CompileState, string];
{
    return compileType(state, input).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
}
/* private static */ compileType(state, CompileState, input, string);
Option < [CompileState, string] > {
    return: type(state, input).map((tuple) => new Tuple(tuple.left, tuple.right.generate()))
};
/* private static */ type(state, CompileState, input, string);
Option < [CompileState, Type] > {
    let, stripped = input.strip()
};
/* if (stripped.equals("int") || stripped.equals("Integer"))  */ {
    return new Some(new Tuple(state, Primitive.Int));
}
/* if (stripped.equals("String"))  */ {
    return new Some(new Tuple(state, Primitive.String));
}
/* if (isSymbol(stripped))  */ {
    return new Some(new Tuple(state, new Symbol(stripped)));
}
return template(state, input).or(() => varArgs(state, input));
/* private static */ varArgs(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix(input, "...", (s) => {
        return type(state, s).map((inner) => {
            let newState = inner.left;
            let child = inner.right;
            return new Tuple(newState, new ArrayType(child));
        });
    })
};
/* private static */ template(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix(input.strip(), ">", (withoutEnd) => {
        return first(withoutEnd, "<", (base, argumentsString) => {
            let strippedBase = base.strip();
            return parseValues(state, argumentsString, Main.argument).map((argumentsTuple) => {
                return getCompileStateTuple(base, argumentsTuple, strippedBase);
            });
        });
    })
};
/* private static */ getCompileStateTuple(base, string, argumentsTuple, [CompileState, (List)], strippedBase, string);
[CompileState, Type];
{
    let argumentsState = argumentsTuple.left;
    let arguments = argumentsTuple.right.iterate().map(Main.retainType).flatMap(Iterators.fromOption).collect(new ListCollector());
    /* if (base.equals("BiFunction"))  */ {
        return new Tuple(argumentsState, new FunctionType(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2)));
    }
    /* if (base.equals("Function"))  */ {
        return new Tuple(argumentsState, new FunctionType(Lists.of(arguments.get(0)), arguments.get(1)));
    }
    /* if (base.equals("Predicate"))  */ {
        return new Tuple(argumentsState, new FunctionType(Lists.of(arguments.get(0)), Primitive.Boolean));
    }
    /* if (base.equals("Supplier"))  */ {
        return new Tuple(argumentsState, new FunctionType(Lists.empty(), arguments.get(0)));
    }
    /* if (base.equals("Tuple") && arguments.size() >= 2)  */ {
        return new Tuple(argumentsState, new TupleType(arguments));
    }
    return new Tuple(argumentsState, new Template(strippedBase, arguments));
}
/* private static */ retainType(argument, Argument);
Option < Type > {
/* if (argument instanceof Type type)  */ };
/* if (argument instanceof Type type)  */ {
    return new Some(type);
}
/* else  */ {
    return new None();
}
/* private static */ argument(state, CompileState, input, string);
Option < [CompileState, Argument] > {
    return: type(state, input).map((tuple) => new Tuple(tuple.left, tuple.right))
};
/* private static  */ last(input, string, infix, string, mapper, (arg0, arg1) => Template[base = Option, arguments = magma.Main$Lists$JVMList], (), d617d);
Option < T > {
    return: infix(input, infix, Main.findLast, mapper)
};
/* private static */ findLast(input, string, infix, string);
Option < number > {
    let, index = input.lastIndexOf(infix)
};
/* if (index == -1)  */ {
    return new None();
}
return new Some(index);
/* private static  */ first(input, string, infix, string, mapper, (arg0, arg1) => Template[base = Option, arguments = magma.Main$Lists$JVMList], (), c53a9eb);
Option < T > {
    return: infix(input, infix, Main.findFirst, mapper)
};
/* private static  */ infix(input, string, infix, string, locator, (arg0, arg1) => Template[base = Option, arguments = magma.Main$Lists$JVMList], (), mapper, (arg0, arg1) => Template[base = Option, arguments = magma.Main$Lists$JVMList], (), a33fae0);
Option < T > {
    return: split(() => locator.apply(input, infix).map((index) => {
        let left = input.substring(0, index);
        let right = input.substring(index + infix.length());
        return new Tuple(left, right);
    }), mapper)
};
/* private static  */ split(splitter, () => Template[base = Option, arguments = magma.Main$Lists$JVMList], (), f7052, mapper, (arg0, arg1) => Template[base = Option, arguments = magma.Main$Lists$JVMList], ());
Option < T > {
    return: splitter.get().flatMap((tuple) => mapper.apply(tuple.left, tuple.right))
};
/* private static */ findFirst(input, string, infix, string);
Option < number > {
    let, index = input.indexOf(infix)
};
/* if (index == -1)  */ {
    return new None();
}
return new Some(index);
/* private static */ generatePlaceholder(input, string);
string;
{
    let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
    return "/* " + replaced + " */";
} /*

private enum Primitive implements Type {
    Int("number"),
    String("string"),
    Boolean("boolean");

    private final String value;

    Primitive(String value) {
        this.value = value;
    }

    @Override
    public String generate() {
        return this.value;
    }
} */
/*  */ 
