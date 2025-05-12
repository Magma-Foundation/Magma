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
var OptionVariant;
(function (OptionVariant) {
    OptionVariant[OptionVariant["Some"] = 0] = "Some";
    OptionVariant[OptionVariant["None"] = 1] = "None";
})(OptionVariant || (OptionVariant = {}));
var ValueVariant;
(function (ValueVariant) {
    ValueVariant[ValueVariant["BooleanValue"] = 0] = "BooleanValue";
    ValueVariant[ValueVariant["Cast"] = 1] = "Cast";
    ValueVariant[ValueVariant["DataAccess"] = 2] = "DataAccess";
    ValueVariant[ValueVariant["IndexValue"] = 3] = "IndexValue";
    ValueVariant[ValueVariant["Invokable"] = 4] = "Invokable";
    ValueVariant[ValueVariant["Lambda"] = 5] = "Lambda";
    ValueVariant[ValueVariant["Not"] = 6] = "Not";
    ValueVariant[ValueVariant["Operation"] = 7] = "Operation";
    ValueVariant[ValueVariant["Placeholder"] = 8] = "Placeholder";
    ValueVariant[ValueVariant["StringValue"] = 9] = "StringValue";
    ValueVariant[ValueVariant["SymbolValue"] = 10] = "SymbolValue";
})(ValueVariant || (ValueVariant = {}));
var CallerVariant;
(function (CallerVariant) {
    CallerVariant[CallerVariant["ConstructionCaller"] = 0] = "ConstructionCaller";
    CallerVariant[CallerVariant["Value"] = 1] = "Value";
})(CallerVariant || (CallerVariant = {}));
var IncompleteClassSegmentVariant;
(function (IncompleteClassSegmentVariant) {
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["ClassDefinition"] = 0] = "ClassDefinition";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["IncompleteClassSegmentWrapper"] = 1] = "IncompleteClassSegmentWrapper";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["MethodPrototype"] = 2] = "MethodPrototype";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["Placeholder"] = 3] = "Placeholder";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["StructurePrototype"] = 4] = "StructurePrototype";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["Whitespace"] = 5] = "Whitespace";
})(IncompleteClassSegmentVariant || (IncompleteClassSegmentVariant = {}));
var ResultVariant;
(function (ResultVariant) {
    ResultVariant[ResultVariant["Ok"] = 0] = "Ok";
    ResultVariant[ResultVariant["Err"] = 1] = "Err";
})(ResultVariant || (ResultVariant = {}));
/* private static final */ class None {
    map(mapper) {
        return new None();
    }
    isPresent() {
        return false;
    }
    orElse(other) {
        return other;
    }
    filter(predicate) {
        return new None();
    }
    orElseGet(supplier) {
        return supplier();
    }
    or(other) {
        return other();
    }
    flatMap(mapper) {
        return new None();
    }
    isEmpty() {
        return true;
    }
    and(other) {
        return new None();
    }
    ifPresent(consumer) {
    }
}
/* private */ class Tuple2Impl {
    constructor(left, right) {
    }
}
/* private */ class Some {
    constructor(value) {
    }
    map(mapper) {
        return new Some(mapper(this.value));
    }
    isPresent() {
        return true;
    }
    orElse(other) {
        return this.value;
    }
    filter(predicate) {
        if (predicate(this.value)) {
            return this;
        }
        return new None();
    }
    orElseGet(supplier) {
        return this.value;
    }
    or(other) {
        return this;
    }
    flatMap(mapper) {
        return mapper(this.value);
    }
    isEmpty() {
        return false;
    }
    and(other) {
        return other().map((otherValue) => new Tuple2Impl(this.value, otherValue));
    }
    ifPresent(consumer) {
        /* consumer.accept(this.value) */ ;
    }
}
/* private static */ class SingleHead {
    constructor(value) {
        this.value = value;
        this.retrieved = false;
    }
    next() {
        if (this.retrieved) {
            return new None();
        }
        this.retrieved = true;
        return new Some(this.value);
    }
}
/* private static */ class EmptyHead {
    next() {
        return new None();
    }
}
/* private */ class HeadedIterator {
    constructor(head) {
    }
    fold(initial, folder) {
        let current = initial;
        while (true) {
            let finalCurrent = current;
            let option = this.head.next().map((inner) => folder(finalCurrent, inner));
            if (option._variant === OptionVariant.Some) {
                let some = option;
                current = some.value;
            }
            else {
                return current;
            }
        }
    }
    map(mapper) {
        return new HeadedIterator(() => this.head.next().map(mapper));
    }
    collect(collector) {
        return this.fold(collector.createInitial(), collector.fold);
    }
    filter(predicate) {
        return this.flatMap((element) => {
            if (predicate(element)) {
                return new HeadedIterator(new SingleHead(element));
            }
            return new HeadedIterator(new EmptyHead());
        });
    }
    next() {
        return this.head.next();
    }
    flatMap(f) {
        return new HeadedIterator(new FlatMapHead(this.head, f));
    }
    zip(other) {
        return new HeadedIterator(() => HeadedIterator.this.head.next().and(other.next));
    }
}
/* private static */ class RangeHead /*  */ {
    constructor(length) {
        this.length = length;
    }
    next() {
        if (this.counter < this.length) {
            let value = this.counter;
            /* this.counter++ */ ;
            return new Some(value);
        }
        return new None();
    }
}
/* private static */ class Lists /*  */ {
}
/* private */ class ImmutableDefinition /*  */ {
    constructor(annotations, maybeBefore, name, type, typeParams) {
    }
    createSimpleDefinition(name, type) {
        return new ImmutableDefinition(Lists.empty(), new None(), name, type, Lists.empty());
    }
    generate() {
        return this.generateWithParams("");
    }
    generateType() {
        if (this.type.equals(Primitive.Unknown)) {
            return "";
        }
        return " : " + this.type.generate();
    }
    joinBefore() {
        if (Main.isDebug) {
            return this.generateBefore();
        }
        return "";
    }
    generateBefore() {
        return this.maybeBefore.filter((value) => !value.isEmpty()).map(Main.generatePlaceholder).map((inner) => inner + " ").orElse("");
    }
    joinTypeParams() {
        return this.typeParams.iterate().collect(Joiner.empty()).map((inner) => "<" + inner + ">").orElse("");
    }
    mapType(mapper) {
        return new ImmutableDefinition(this.annotations, this.maybeBefore, this.name, mapper(this.type), this.typeParams);
    }
    generateWithParams(joinedParameters) {
        let joinedAnnotations = this.annotations.iterate().map((value) => "@" + value + " ").collect(Joiner.empty()).orElse("");
        let joined = this.joinTypeParams();
        let before = this.joinBefore();
        let typeString = this.generateType();
        return joinedAnnotations + before + this.name + joined + joinedParameters + typeString;
    }
    createDefinition(paramTypes) {
        let type1 = new FunctionType(paramTypes, this.type);
        return new ImmutableDefinition(this.annotations, this.maybeBefore, this.name, type1, this.typeParams);
    }
    containsAnnotation(annotation) {
        return this.annotations.contains(annotation);
    }
    removeAnnotations() {
        return new ImmutableDefinition(Lists.empty(), this.maybeBefore, this.name, this.type, this.typeParams);
    }
}
/* private */ class ObjectType /*  */ {
    constructor(name, typeParams, definitions) {
    }
    generate() {
        return this.name;
    }
    replace(mapping) {
        return new ObjectType(this.name, this.typeParams, this.definitions.iterate().map((definition) => definition.mapType((type) => type.replace(mapping))).collect(new ListCollector()));
    }
    find(name) {
        return this.definitions.iterate().filter((definition) => definition.name().equals(name)).map(Definition.type).next();
    }
    findName() {
        return new Some(this.name);
    }
}
/* private */ class TypeParam /*  */ {
    constructor(value) {
    }
    generate() {
        return this.value;
    }
    replace(mapping) {
        return mapping.find(this.value).orElse(this);
    }
    findName() {
        return new None();
    }
}
/* private */ class CompileState /*  */ {
    constructor(structures, definitions, objectTypes, structNames, typeParams, typeRegister, functionSegments) {
    }
    createInitial() {
        return new CompileState(Lists.empty(), Lists.of(Lists.empty()), Lists.empty(), Lists.empty(), Lists.empty(), new None(), Lists.empty());
    }
    resolveValue(name) {
        return this.definitions.iterateReversed().flatMap(List.iterate).filter((definition) => definition.name().equals(name)).next().map(Definition.type);
    }
    addStructure(structure) {
        return new CompileState(this.structures.addLast(structure), this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    defineAll(definitions) {
        let defined = this.definitions.mapLast((frame) => frame.addAllLast(definitions));
        return new CompileState(this.structures, defined, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    resolveType(name) {
        if (this.structNames.last().filter((inner) => inner.equals(name)).isPresent()) {
            return new Some(new ObjectType(name, this.typeParams, this.definitions.last().orElse(Lists.empty())));
        }
        let maybeTypeParam = this.typeParams.iterate().filter((param) => param.equals(name)).next();
        if (maybeTypeParam._variant === OptionVariant.Some) {
            let some = maybeTypeParam;
            return new Some(new TypeParam(some.value));
        }
        return this.objectTypes.iterate().filter((type) => type.name.equals(name)).next().map((type) => type);
    }
    define(definition) {
        return new CompileState(this.structures, this.definitions.mapLast((frame) => frame.addLast(definition)), this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    pushStructName(name) {
        return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.addLast(name), this.typeParams, this.typeRegister, this.functionSegments);
    }
    withTypeParams(typeParams) {
        return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams.addAllLast(typeParams), this.typeRegister, this.functionSegments);
    }
    withExpectedType(type) {
        return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, new Some(type), this.functionSegments);
    }
    popStructName() {
        return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.removeLast().map(Tuple2.left).orElse(this.structNames), this.typeParams, this.typeRegister, this.functionSegments);
    }
    enterDefinitions() {
        return new CompileState(this.structures, this.definitions.addLast(Lists.empty()), this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    exitDefinitions() {
        let removed = this.definitions.removeLast().map(Tuple2.left).orElse(this.definitions);
        return new CompileState(this.structures, removed, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    addType(thisType) {
        return new CompileState(this.structures, this.definitions, this.objectTypes.addLast(thisType), this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    addFunctionSegment(segment) {
        return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments.addLast(segment));
    }
    clearFunctionSegments() {
        return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, Lists.empty());
    }
}
/* private static */ class DivideState /*  */ {
    constructor(input, index, segments, buffer, depth) {
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
        this.input = input;
        this.index = index;
    }
    createInitial(input) {
        return new DivideState(input, 0, Lists.empty(), "", 0);
    }
    advance() {
        this.segments = this.segments.addLast(this.buffer);
        this.buffer = "";
        return this;
    }
    append(c) {
        this.buffer = this.buffer + c;
        return this;
    }
    enter() {
        /* this.depth++ */ ;
        return this;
    }
    isLevel() {
        return this.depth === 0;
    }
    exit() {
        /* this.depth-- */ ;
        return this;
    }
    isShallow() {
        return this.depth === 1;
    }
    pop() {
        if (this.index < this.input.length()) {
            let c = this.input.charAt(this.index);
            return new Some(new Tuple2Impl(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
        }
        return new None();
    }
    popAndAppendToTuple() {
        return this.pop().map((tuple) => {
            let c = tuple[0]();
            let right = tuple[1]();
            return new Tuple2Impl(c, right.append(c));
        });
    }
    popAndAppendToOption() {
        return this.popAndAppendToTuple().map(Tuple2.right);
    }
    peek() {
        return this.input.charAt(this.index);
    }
}
/* private */ class Joiner /*  */ {
    constructor(delimiter) {
    }
    empty() {
        return new Joiner("");
    }
    createInitial() {
        return new None();
    }
    fold(current, element) {
        return new Some(current.map((inner) => inner + this.delimiter + element).orElse(element));
    }
}
/* private static */ class ListCollector {
    createInitial() {
        return Lists.empty();
    }
    fold(current, element) {
        return current.addLast(element);
    }
}
/* private static */ class FlatMapHead {
    constructor(head, mapper) {
        this.mapper = mapper;
        this.current = new None();
        this.head = head;
    }
    next() {
        while (true) {
            if (this.current.isPresent()) {
                let inner = this.current.orElse( /* null */);
                let maybe = inner.next();
                if (maybe.isPresent()) {
                    return maybe;
                }
                else {
                    this.current = new None();
                }
            }
            let outer = this.head.next();
            if (outer.isPresent()) {
                this.current = outer.map(this.mapper);
            }
            else {
                return new None();
            }
        }
    }
}
/* private */ class ArrayType /*  */ {
    constructor(right) {
    }
    generate() {
        return this.right().generate() + "[]";
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return new None();
    }
}
/* private static final */ class Whitespace /*  */ {
    generate() {
        return "";
    }
    maybeCreateDefinition() {
        return new None();
    }
}
/* private static */ class Iterators /*  */ {
    fromOption(option) {
        let single = option.map(SingleHead.new);
        return new HeadedIterator(single.orElseGet(EmptyHead.new));
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
    }
    generate() {
        let joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left() + " : " + pair.right().generate()).collect(new Joiner(", ")).orElse("");
        return "(" + joined + ") => " + this.returns.generate();
    }
    replace(mapping) {
        return new FunctionType(this.arguments.iterate().map((type) => type.replace(mapping)).collect(new ListCollector()), this.returns);
    }
    findName() {
        return new None();
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
    }
    generate() {
        let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).orElse("");
        return "[" + joinedArguments + "]";
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return new None();
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
    }
    generate() {
        let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        return this.base.generate() + joinedArguments;
    }
    typeParams() {
        return this.base.typeParams();
    }
    find(name) {
        return this.base.find(name).map((found) => {
            let mapping = this.base.typeParams().iterate().zip(this.arguments.iterate()).collect(new MapCollector());
            return found.replace(mapping);
        });
    }
    name() {
        return this.base.name();
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return this.base.findName();
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    generate() {
        return generatePlaceholder(this.input);
    }
    type() {
        return Primitive.Unknown;
    }
    typeParams() {
        return Lists.empty();
    }
    find(name) {
        return new None();
    }
    name() {
        return this.input;
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return new None();
    }
    maybeCreateDefinition() {
        return new None();
    }
}
/* private */ class StringValue /*  */ {
    constructor(stripped) {
    }
    generate() {
        return this.stripped;
    }
    type() {
        return Primitive.Unknown;
    }
}
/* private */ class DataAccess /*  */ {
    constructor(parent, property, type) {
    }
    generate() {
        return this.parent.generate() + "." + this.property + createDebugString(this.type);
    }
    type() {
        return this.type;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(type) {
    }
    generate() {
        return "new " + this.type.generate();
    }
    toFunction() {
        return new FunctionType(Lists.empty(), this.type);
    }
}
/* private */ class Operator /*  */ {
    constructor(sourceRepresentation, targetRepresentation) {
    }
}
/* private */ class Operation /*  */ {
    constructor(left, operator, right) {
    }
    generate() {
        return this.left().generate() + " " + this.operator.targetRepresentation + " " + this.right().generate();
    }
    type() {
        return Primitive.Unknown;
    }
}
/* private */ class Not /*  */ {
    constructor(value) {
    }
    generate() {
        return "!" + this.value.generate();
    }
    type() {
        return Primitive.Unknown;
    }
}
/* private */ class BlockLambdaValue /*  */ {
    constructor(depth, statements) {
    }
    generate() {
        return "{" + this.joinStatements() + createIndent(this.depth) + "}";
    }
    joinStatements() {
        return this.statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameters, body) {
    }
    generate() {
        let joined = this.parameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
        return "(" + joined + ") => " + this.body.generate();
    }
    type() {
        return Primitive.Unknown;
    }
}
/* private */ class Invokable /*  */ {
    constructor(caller, arguments, type) {
    }
    generate() {
        let joined = this.arguments.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
        return this.caller.generate() + "(" + joined + ")" + createDebugString(this.type);
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
    }
    generate() {
        return this.parent.generate() + "[" + this.child.generate() + "]";
    }
    type() {
        return Primitive.Unknown;
    }
}
/* private */ class SymbolValue /*  */ {
    constructor(stripped, type) {
    }
    generate() {
        return this.stripped + createDebugString(this.type);
    }
}
/* private static */ class Maps /*  */ {
}
/* private */ class MapCollector {
    createInitial() {
        return Maps.empty();
    }
    fold(current, element) {
        return current.with(element[0](), element[1]());
    }
}
/* private static */ class ConstructorHeader /*  */ {
    createDefinition(paramTypes) {
        return ImmutableDefinition.createSimpleDefinition("new", Primitive.Unknown);
    }
    generateWithParams(joinedParameters) {
        return "constructor " + joinedParameters;
    }
}
/* private */ class Method /*  */ {
    constructor(depth, header, parameters, maybeStatements) {
    }
    joinStatements(statements) {
        return statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
    }
    generate() {
        let indent = createIndent(this.depth);
        let generatedHeader = this.header.generateWithParams(joinValues(this.parameters));
        let generatedStatements = this.maybeStatements.map(Method.joinStatements).map((inner) => " {" + inner + indent + "}").orElse(";");
        return indent + generatedHeader + generatedStatements;
    }
}
/* private */ class Block /*  */ {
    constructor(depth, header, statements) {
    }
    generate() {
        let indent = createIndent(this.depth);
        let collect = this.statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
        return indent + this.header.generate() + "{" + collect + indent + "}";
    }
}
/* private */ class Conditional /*  */ {
    constructor(prefix, value1) {
    }
    generate() {
        return this.prefix + " (" + this.value1.generate() + ")";
    }
}
/* private static */ class Else /*  */ {
    generate() {
        return "else ";
    }
}
/* private */ class Return /*  */ {
    constructor(value) {
    }
    generate() {
        return "return " + this.value.generate();
    }
}
/* private */ class Initialization /*  */ {
    constructor(definition, source) {
    }
    generate() {
        return "let " + this.definition.generate() + " = " + this.source.generate();
    }
}
/* private */ class Assignment /*  */ {
    constructor(destination, source) {
    }
    generate() {
        return this.destination.generate() + " = " + this.source.generate();
    }
}
/* private */ class Statement /*  */ {
    constructor(depth, value) {
    }
    generate() {
        return createIndent(this.depth) + this.value.generate() + ";";
    }
}
/* private */ class MethodPrototype /*  */ {
    constructor(depth, header, parameters, content) {
    }
    createDefinition() {
        return this.header.createDefinition(this.findParamTypes());
    }
    findParamTypes() {
        return this.parameters().iterate().map(Definition.type).collect(new ListCollector());
    }
    maybeCreateDefinition() {
        return new Some(this.header.createDefinition(this.findParamTypes()));
    }
}
/* private */ class IncompleteClassSegmentWrapper /*  */ {
    constructor(segment) {
    }
    maybeCreateDefinition() {
        return new None();
    }
}
/* private */ class ClassDefinition /*  */ {
    constructor(definition, depth) {
    }
    maybeCreateDefinition() {
        return new Some(this.definition);
    }
}
/* private */ class StructurePrototype /*  */ {
    constructor(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants) {
    }
    createObjectType() {
        let definitionFromSegments = this.segments.iterate().map(IncompleteClassSegment.maybeCreateDefinition).flatMap(Iterators.fromOption).collect(new ListCollector());
        return new ObjectType(this.name, this.typeParams, definitionFromSegments.addAllLast(this.parameters));
    }
    maybeCreateDefinition() {
        return new None();
    }
}
/* private */ class Cast /*  */ {
    constructor(value, type) {
    }
    generate() {
        return this.value.generate() + " as " + this.type.generate();
    }
}
/* private */ class Ok {
    constructor(value) {
    }
    mapValue(mapper) {
        return new Ok(mapper(this.value));
    }
    match(whenOk, whenErr) {
        return whenOk(this.value);
    }
}
/* private */ class Err {
    constructor(error) {
    }
    mapValue(mapper) {
        return new Err(this.error);
    }
    match(whenOk, whenErr) {
        return whenErr(this.error);
    }
}
/* private */ let Primitive = (() => {
    var _a;
    let __decorators;
    let __initializers = [];
    let __extraInitializers = [];
    let __decorators_1;
    let __initializers_1 = [];
    let __extraInitializers_1 = [];
    let __decorators_2;
    let __initializers_2 = [];
    let __extraInitializers_2 = [];
    let __decorators_3;
    let __initializers_3 = [];
    let __extraInitializers_3 = [];
    return _a = class Primitive /*  */ {
            constructor(value) {
                this. = __runInitializers(this, __initializers, void 0);
                this. = (__runInitializers(this, __extraInitializers), __runInitializers(this, __initializers_1, void 0));
                this. = (__runInitializers(this, __extraInitializers_1), __runInitializers(this, __initializers_2, void 0));
                this. = (__runInitializers(this, __extraInitializers_2), __runInitializers(this, __initializers_3, void 0));
                this.value = __runInitializers(this, __extraInitializers_3);
                this.value = value;
            }
            generate() {
                return this.value;
            }
            replace(mapping) {
                return this;
            }
            findName() {
                return new None();
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            __decorators = [nt("number")];
            __decorators_1 = [tring("string")];
            __decorators_2 = [oolean("boolean")];
            __decorators_3 = [nknown("unknown")];
            __esDecorate(null, null, __decorators, { kind: "field", name: "", static: false, private: false, access: { has: obj => "" in obj, get: obj => obj., set: (obj, value) => { obj. = value; } }, metadata: _metadata }, __initializers, __extraInitializers);
            __esDecorate(null, null, __decorators_1, { kind: "field", name: "", static: false, private: false, access: { has: obj => "" in obj, get: obj => obj., set: (obj, value) => { obj. = value; } }, metadata: _metadata }, __initializers_1, __extraInitializers_1);
            __esDecorate(null, null, __decorators_2, { kind: "field", name: "", static: false, private: false, access: { has: obj => "" in obj, get: obj => obj., set: (obj, value) => { obj. = value; } }, metadata: _metadata }, __initializers_2, __extraInitializers_2);
            __esDecorate(null, null, __decorators_3, { kind: "field", name: "", static: false, private: false, access: { has: obj => "" in obj, get: obj => obj., set: (obj, value) => { obj. = value; } }, metadata: _metadata }, __initializers_3, __extraInitializers_3);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ class BooleanValue /*  */ {
    constructor(value) {
        this.value = value;
    }
    generate() {
        return this.value;
    }
    type() {
        return Primitive.Boolean;
    }
}
/* public */ class Main /*  */ {
    generatePlaceholder(input) {
        let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
        return "/* " + replaced + " */";
    }
    joinValues(retainParameters) {
        let inner = retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
        return "(" + inner + ")";
    }
    createIndent(depth) {
        return "\n" + "\t".repeat(depth);
    }
    createDebugString(type) {
        if (!Main.isDebug) {
            return "";
        }
        return generatePlaceholder(": " + type.generate());
    }
    main() {
        let parent = this.findRoot();
        let source = parent.resolve("Main.java");
        let target = parent.resolve("main.ts");
        /* source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(Throwable::printStackTrace) */ ;
    }
    compile(input) {
        let state = CompileState.createInitial();
        let parsed = this.parseStatements(state, input, this.compileRootSegment);
        let joined = parsed[0]().structures.iterate().collect(Joiner.empty()).orElse("");
        return joined + this.generateStatements(parsed[1]());
    }
    generateStatements(statements) {
        return this.generateAll(this.mergeStatements, statements);
    }
    parseStatements(state, input, mapper) {
        return this.parseAllWithIndices(state, input, this.foldStatementChar, (state3, tuple) => new Some(mapper(state3, tuple.right()))).orElseGet(() => new Tuple2Impl(state, Lists.empty()));
    }
    generateAll(merger, elements) {
        return elements.iterate().fold("", merger);
    }
    parseAllWithIndices(state, input, folder, mapper) {
        let stringList = this.divideAll(input, folder);
        return this.mapUsingState(state, stringList, mapper);
    }
    mapUsingState(state, elements, mapper) {
        let initial = new Some(new Tuple2Impl(state, Lists.empty()));
        return elements.iterateWithIndices().fold(initial, (tuple, element) => {
            return tuple.flatMap((inner) => {
                let state1 = inner.left();
                let right = inner.right();
                return mapper(state1, element).map((applied) => {
                    return new Tuple2Impl(applied[0](), right.addLast(applied[1]()));
                });
            });
        });
    }
    mergeStatements(cache, statement) {
        return cache + statement;
    }
    divideAll(input, folder) {
        let current = DivideState.createInitial(input);
        while (true) {
            let maybePopped = current.pop().map((tuple) => {
                return this.foldSingleQuotes(tuple).or(() => this.foldDoubleQuotes(tuple)).orElseGet(() => folder(tuple[1](), tuple[0]()));
            });
            if (maybePopped.isPresent()) {
                current = maybePopped.orElse(current);
            }
            else {
                /* break */ ;
            }
        }
        return current.advance().segments;
    }
    foldDoubleQuotes(tuple) {
        if (tuple[0]() ===  /*  '\"' */) {
            let current = tuple[1]().append(tuple[0]());
            while (true) {
                let maybePopped = current.popAndAppendToTuple();
                if (maybePopped.isEmpty()) {
                    /* break */ ;
                }
                let popped = maybePopped.orElse( /* null */);
                current = popped.right();
                if (popped.left() ===  /*  '\\' */) {
                    current = current.popAndAppendToOption().orElse(current);
                }
                if (popped.left() ===  /*  '\"' */) {
                    /* break */ ;
                }
            }
            return new Some(current);
        }
        return new None();
    }
    foldSingleQuotes(tuple) {
        if ( /* tuple.left() != '\'' */) {
            return new None();
        }
        let appended = tuple[1]().append(tuple[0]());
        return appended.popAndAppendToTuple().map(this.foldEscaped).flatMap(DivideState.popAndAppendToOption);
    }
    foldEscaped(escaped) {
        if (escaped[0]() ===  /*  '\\' */) {
            return escaped[1]().popAndAppendToOption().orElse(escaped[1]());
        }
        return escaped[1]();
    }
    foldStatementChar(state, c) {
        let append = state.append(c);
        if (c ===  /*  ';'  */ && append.isLevel()) {
            return append.advance();
        }
        if (c ===  /*  '}'  */ && append.isShallow()) {
            return append.advance().exit();
        }
        if (c ===  /*  '{'  */ || c ===  /*  '(' */) {
            return append.enter();
        }
        if (c ===  /*  '}'  */ || c ===  /*  ')' */) {
            return append.exit();
        }
        return append;
    }
    compileRootSegment(state, input) {
        let stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple2Impl(state, "");
        }
        return this.parseClass(stripped, state).flatMap((tuple) => this.completeClassSegment(tuple[0](), tuple[1]())).map((tuple0) => new Tuple2Impl(tuple0.left(), tuple0.right().generate())).orElseGet(() => new Tuple2Impl(state, generatePlaceholder(stripped)));
    }
    parseClass(stripped, state) {
        return this.parseStructure(stripped, "class ", "class ", state);
    }
    parseStructure(stripped, sourceInfix, targetInfix, state) {
        return this.first(stripped, sourceInfix, (beforeInfix, right) => {
            return this.first(right, "{", (beforeContent, withEnd) => {
                return this.suffix(withEnd.strip(), "}", (content1) => {
                    return this.last(beforeInfix.strip(), "\n", (annotationsString, s2) => {
                        let annotations = this.parseAnnotations(annotationsString);
                        return this.parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, annotations);
                    }).or(() => {
                        return this.parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
                    });
                });
            });
        });
    }
    parseAnnotations(annotationsString) {
        return this.divideAll(annotationsString.strip(), this.foldByDelimiter).iterate().map(strip).filter((value) => !value.isEmpty()).map((value) => value.substring(1)).map(strip).filter((value) => !value.isEmpty()).collect(new ListCollector());
    }
    foldByDelimiter(state1, c) {
        if (c ===  /*  '\n' */) {
            return state1.advance();
        }
        return state1.append(c);
    }
    parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, annotations) {
        return this.last(beforeContent, " permits ", (s, s2) => {
            let variants = this.divideAll(s2, this.foldValueChar).iterate().map(strip).filter((value) => !value.isEmpty()).collect(new ListCollector());
            return this.parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, s, content1, variants, annotations);
        }).or(() => {
            return this.parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), annotations);
        });
    }
    parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations) {
        return this.first(beforeContent, " implements ", (s, s2) => {
            return this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, s, content1, variants, annotations);
        }).or(() => {
            return this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations);
        });
    }
    parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations) {
        return this.first(beforeContent, " extends ", (s, s2) => {
            return this.parseStructureWithMaybeParams(targetInfix, state, beforeInfix, s, content1, variants, annotations);
        }).or(() => {
            return this.parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations);
        });
    }
    parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations) {
        return this.suffix(beforeContent.strip(), ")", (s) => {
            return this.first(s, "(", (s1, s2) => {
                let parsed = this.parseParameters(state, s2);
                return this.parseStructureWithMaybeTypeParams(targetInfix, parsed[0](), beforeInfix, s1, content1, parsed[1](), variants, annotations);
            });
        }).or(() => {
            return this.parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), variants, annotations);
        });
    }
    parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, params, variants, annotations) {
        return this.first(beforeContent, "<", (name, withTypeParams) => {
            return this.first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
                let mapper = (state1, s) => new Tuple2Impl(state1, s.strip());
                let typeParams = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(mapper(state1, s)));
                return this.assembleStructure(typeParams[0](), targetInfix, annotations, beforeInfix, name, content1, typeParams[1](), afterTypeParams, params, variants);
            });
        }).or(() => {
            return this.assembleStructure(state, targetInfix, annotations, beforeInfix, beforeContent, content1, Lists.empty(), "", params, variants);
        });
    }
    assembleStructure(state, targetInfix, annotations, beforeInfix, rawName, content, typeParams, after, rawParameters, variants) {
        let name = rawName.strip();
        if (!this.isSymbol(name)) {
            return new None();
        }
        if (annotations.contains("Actual")) {
            return new Some(new Tuple2Impl(state, new Whitespace()));
        }
        let segmentsTuple = this.parseStatements(state.pushStructName(name).withTypeParams(typeParams), content, (state0, input) => this.parseClassSegment(state0, input, 1));
        let segmentsState = segmentsTuple[0]();
        let segments = segmentsTuple[1]();
        let parameters = this.retainDefinitions(rawParameters);
        let prototype = new StructurePrototype(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants);
        return new Some(new Tuple2Impl(segmentsState.addType(prototype.createObjectType()), prototype));
    }
    completeStructure(state, prototype) {
        let thisType = prototype.createObjectType();
        let state2 = state.enterDefinitions().define(ImmutableDefinition.createSimpleDefinition("this", thisType));
        return this.mapUsingState(state2, prototype.segments(), (state1, entry) => this.completeClassSegment(state1, entry.right())).map((completedTuple) => {
            let completedState = completedTuple[0]();
            let completed = completedTuple[1]();
            let exited = completedState.exitDefinitions();
            /* CompileState withEnum */ ;
            /* List<ClassSegment> completed1 */ ;
            if (prototype.variants.isEmpty()) {
                exited;
                completed;
            }
            else {
                let joined = prototype.variants.iterate().map((inner) => "\n\t" + inner).collect(new Joiner(",")).orElse("");
                let enumName = prototype.name + "Variant";
                exited.addStructure("enum " + enumName + " {" +
                    joined +
                    "\n}\n");
                let definition = ImmutableDefinition.createSimpleDefinition("_variant", new ObjectType(enumName, Lists.empty(), Lists.empty()));
                completed.addFirst(new Statement(1, definition));
            }
            let withMaybeConstructor = this.atttachConstructor(prototype);
            let parsed2 = withMaybeConstructor.iterate().map(ClassSegment.generate).collect(Joiner.empty()).orElse("");
            let joinedTypeParams = prototype.typeParams().iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
            let generated = generatePlaceholder(prototype.beforeInfix().strip()) + prototype.targetInfix() + prototype.name() + joinedTypeParams + generatePlaceholder(prototype.after()) + " {" + parsed2 + "\n}\n";
            let compileState = /* withEnum */ .popStructName();
            let definedState = compileState.addStructure(generated);
            return new Tuple2Impl(definedState, new Whitespace());
        });
    }
    atttachConstructor(prototype, segments) {
        /* List<ClassSegment> withMaybeConstructor */ ;
        if (prototype.parameters().isEmpty()) {
            segments;
        }
        else {
            segments.addFirst(new Method(1, new ConstructorHeader(), prototype.parameters(), new Some(Lists.empty())));
        }
        return /* withMaybeConstructor */;
    }
    completeClassSegment(state1, segment) {
        /* return switch (segment) */ {
            /* case IncompleteClassSegmentWrapper wrapper -> new Some<>(new Tuple2Impl<>(state1, wrapper.segment)) */ ;
            /* case MethodPrototype methodPrototype -> this.completeMethod(state1, methodPrototype) */ ;
            /* case Whitespace whitespace -> new Some<>(new Tuple2Impl<>(state1, whitespace)) */ ;
            /* case Placeholder placeholder -> new Some<>(new Tuple2Impl<>(state1, placeholder)) */ ;
            /* case ClassDefinition classDefinition -> this.completeDefinition(state1, classDefinition) */ ;
            /* case StructurePrototype structurePrototype -> this.completeStructure(state1, structurePrototype) */ ;
        }
        /*  */ ;
    }
    completeDefinition(state1, classDefinition) {
        let definition = classDefinition.definition;
        let statement = new Statement(classDefinition.depth, definition);
        return new Some(new Tuple2Impl(state1, statement));
    }
    retainDefinition(parameter) {
        if (parameter._variant === ParameterVariant.Definition) {
            let definition = parameter;
            return new Some(definition);
        }
        return new None();
    }
    isSymbol(input) {
        /* for (var i = 0; i < input.length(); i++) */ {
            let c = input.charAt( /* i */);
            if ( /* Character */.isLetter(c) || /*  */ ( /* i != 0  */ && /* Character */ .isDigit(c))) {
                /* continue */ ;
            }
            return false;
        }
        return true;
    }
    prefix(input, prefix, mapper) {
        if (!input.startsWith(prefix)) {
            return new None();
        }
        let slice = input.substring(prefix.length());
        return mapper(slice);
    }
    suffix(input, suffix, mapper) {
        if (!input.endsWith(suffix)) {
            return new None();
        }
        let slice = input.substring(0, input.length() - suffix.length());
        return mapper(slice);
    }
    parseClassSegment(state, input, depth) {
        return this. < /* Whitespace, IncompleteClassSegment>typed */ (() => this.parseWhitespace(input, state)).or(() => this.typed(() => this.parseClass(input, state))).or(() => this.typed(() => this.parseStructure(input, "interface ", "interface ", state))).or(() => this.typed(() => this.parseStructure(input, "record ", "class ", state))).or(() => this.typed(() => this.parseStructure(input, "enum ", "class ", state))).or(() => this.parseMethod(state, input, depth)).or(() => this.typed(() => this.parseDefinitionStatement(input, depth, state))).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
    }
    typed(action) {
        return action().map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]()));
    }
    parseWhitespace(input, state) {
        if (input.isBlank()) {
            return new Some(new Tuple2Impl(state, new Whitespace()));
        }
        return new None();
    }
    parseMethod(state, input, depth) {
        return this.first(input, "(", (definitionString, withParams) => {
            return this.first(withParams, ")", (parametersString, rawContent) => {
                return this.parseDefinition(state, definitionString). < Tuple2 < /* CompileState, Header>>map */ ((tuple) => new Tuple2Impl(tuple.left(), tuple.right())).or(() => this.parseConstructor(state, definitionString)).flatMap((definitionTuple) => this.assembleMethod(depth, parametersString, rawContent, definitionTuple));
            });
        });
    }
    assembleMethod(depth, parametersString, rawContent, definitionTuple) {
        let definitionState = definitionTuple[0]();
        let header = definitionTuple[1]();
        let parametersTuple = this.parseParameters(definitionState, parametersString);
        let rawParameters = parametersTuple[1]();
        let parameters = this.retainDefinitions(rawParameters);
        let prototype = new MethodPrototype(depth, header, parameters, rawContent.strip());
        return new Some(new Tuple2Impl(parametersTuple[0]().define(prototype.createDefinition()), prototype));
    }
    completeMethod(state, prototype) {
        let definition = prototype.createDefinition();
        let oldHeader = prototype.header();
        /* Header newHeader */ ;
        if (oldHeader._variant === Variant.Definition) {
            let maybeDefinition = oldHeader;
            maybeDefinition.removeAnnotations();
        }
        else {
            oldHeader;
        }
        if (prototype.content().equals(";") || definition.containsAnnotation("Actual")) {
            return new Some(new Tuple2Impl(state.define(definition), new Method(prototype.depth(), prototype.parameters(), new None())));
        }
        if (prototype.content().startsWith("{") && prototype.content().endsWith("}")) {
            let substring = prototype.content().substring(1, prototype.content().length() - 1);
            let withDefined = state.enterDefinitions().defineAll(prototype.parameters());
            let statementsTuple = this.parseStatements(withDefined, substring, (state1, input1) => this.parseFunctionSegment(state1, input1, prototype.depth() + 1));
            let statements = statementsTuple[1]();
            return new Some(new Tuple2Impl(statementsTuple[0]().exitDefinitions().define(definition), new Method(prototype.depth(), prototype.parameters(), new Some(statements))));
        }
        return new None();
    }
    parseConstructor(state, input) {
        let stripped = input.strip();
        if (stripped.equals(state.structNames.last().orElse(""))) {
            return new Some(new Tuple2Impl(state, new ConstructorHeader()));
        }
        return new None();
    }
    retainDefinitions(right) {
        return right.iterate().map(this.retainDefinition).flatMap(Iterators.fromOption).collect(new ListCollector());
    }
    parseParameters(state, params) {
        return this.parseValuesOrEmpty(state, params, (state1, s) => new Some(this.parseParameter(state1, s)));
    }
    parseFunctionSegments(state, input, depth) {
        return this.parseStatements(state, input, (state1, input1) => this.parseFunctionSegment(state1, input1, depth + 1));
    }
    parseFunctionSegment(state, input, depth) {
        let stripped = input.strip();
        if (stripped.isEmpty()) {
            return new Tuple2Impl(state, new Whitespace());
        }
        return this.parseFunctionStatement(state, depth, stripped).or(() => this.parseBlock(state, depth, stripped)).orElseGet(() => new Tuple2Impl(state, new Placeholder(stripped)));
    }
    parseFunctionStatement(state, depth, stripped) {
        return this.suffix(stripped, ";", (s) => {
            let tuple = this.parseStatementValue(state, s, depth);
            let left = tuple[0]();
            let right = tuple[1]();
            return new Some(new Tuple2Impl(left, new Statement(depth, right)));
        });
    }
    parseBlock(state, depth, stripped) {
        return this.suffix(stripped, "}", (withoutEnd) => {
            return this.split(() => this.toFirst(withoutEnd), (beforeContent, content) => {
                return this.suffix(beforeContent, "{", (headerString) => {
                    let headerTuple = this.parseBlockHeader(state, headerString, depth);
                    let headerState = headerTuple[0]();
                    let header = headerTuple[1]();
                    let statementsTuple = this.parseFunctionSegments(headerState, content, depth);
                    let statementsState = statementsTuple[0]();
                    let statements = statementsTuple[1]().addAllFirst(statementsState.functionSegments);
                    return new Some(new Tuple2Impl(statementsState.clearFunctionSegments(), new Block(depth, header, statements)));
                });
            });
        });
    }
    toFirst(input) {
        let divisions = this.divideAll(input, this.foldBlockStart);
        return divisions.removeFirst().map((removed) => {
            let right = removed[0]();
            let left = removed[1]().iterate().collect(new Joiner("")).orElse("");
            return new Tuple2Impl(right, left);
        });
    }
    parseBlockHeader(state, input, depth) {
        let stripped = input.strip();
        return this.parseConditional(state, stripped, "if", depth).or(() => this.parseConditional(state, stripped, "while", depth)).or(() => this.parseElse(state, input)).orElseGet(() => new Tuple2Impl(state, new Placeholder(stripped)));
    }
    parseElse(state, input) {
        let stripped = input.strip();
        if (stripped.equals("else")) {
            return new Some(new Tuple2Impl(state, new Else()));
        }
        return new None();
    }
    parseConditional(state, input, prefix, depth) {
        return this.prefix(input, prefix, (withoutPrefix) => {
            return this.prefix(withoutPrefix.strip(), "(", (withoutValueStart) => {
                return this.suffix(withoutValueStart, ")", (value) => {
                    let valueTuple = this.parseValue(state, value, depth);
                    let value1 = valueTuple[1]();
                    return new Some(new Tuple2Impl(valueTuple[0](), new Conditional(prefix, value1)));
                });
            });
        });
    }
    foldBlockStart(state, c) {
        let appended = state.append(c);
        if (c ===  /*  '{'  */ && state.isLevel()) {
            return appended.advance();
        }
        if (c ===  /*  '{' */) {
            return appended.enter();
        }
        if (c ===  /*  '}' */) {
            return appended.exit();
        }
        return appended;
    }
    parseStatementValue(state, input, depth) {
        let stripped = input.strip();
        if (stripped.startsWith("return ")) {
            let value = stripped.substring("return ".length());
            let tuple = this.parseValue(state, value, depth);
            let value1 = tuple[1]();
            return new Tuple2Impl(tuple[0](), new Return(value1));
        }
        return this.parseAssignment(state, depth, stripped).orElseGet(() => {
            return new Tuple2Impl(state, new Placeholder(stripped));
        });
    }
    parseAssignment(state, depth, stripped) {
        return this.first(stripped, "=", (beforeEquals, valueString) => {
            let sourceTuple = this.parseValue(state, valueString, depth);
            let sourceState = sourceTuple[0]();
            let source = sourceTuple[1]();
            return this.parseDefinition(sourceState, beforeEquals).flatMap((definitionTuple) => this.parseInitialization(definitionTuple[0](), definitionTuple[1](), source)).or(() => this.parseAssignment(depth, beforeEquals, sourceState, source));
        });
    }
    parseAssignment(depth, beforeEquals, sourceState, source) {
        let destinationTuple = this.parseValue(sourceState, beforeEquals, depth);
        let destinationState = destinationTuple[0]();
        let destination = destinationTuple[1]();
        return new Some(new Tuple2Impl(destinationState, new Assignment(destination, source)));
    }
    parseInitialization(state, rawDefinition, source) {
        let definition = rawDefinition.mapType((type) => {
            if (type.equals(Primitive.Unknown)) {
                return source.type();
            }
            else {
                return type;
            }
        });
        return new Some(new Tuple2Impl(state.define(definition), new Initialization(definition, source)));
    }
    parseValue(state, input, depth) {
        return this.parseBoolean(state, input).or(() => this.parseLambda(state, input, depth)).or(() => this.parseString(state, input)).or(() => this.parseDataAccess(state, input, depth)).or(() => this.parseSymbolValue(state, input)).or(() => this.parseInvokable(state, input, depth)).or(() => this.parseDigits(state, input)).or(() => this.parseInstanceOf(state, input, depth)).or(() => this.parseOperation(state, input, depth, Operator.ADD)).or(() => this.parseOperation(state, input, depth, Operator.EQUALS)).or(() => this.parseOperation(state, input, depth, Operator.SUBTRACT)).or(() => this.parseOperation(state, input, depth, Operator.AND)).or(() => this.parseOperation(state, input, depth, Operator.OR)).or(() => this.parseOperation(state, input, depth)).or(() => this.parseOperation(state, input, depth)).or(() => this.parseNot(state, input, depth)).or(() => this.parseMethodReference(state, input, depth)).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
    }
    parseBoolean(state, input) {
        let stripped = input.strip();
        if (stripped.equals("false")) {
            return new Some(new Tuple2Impl(state, BooleanValue.False));
        }
        if (stripped.equals("true")) {
            return new Some(new Tuple2Impl(state, BooleanValue.True));
        }
        return new None();
    }
    parseInstanceOf(state, input, depth) {
        return this.last(input, "instanceof", (s, s2) => {
            let childTuple = this.parseValue(state, s, depth);
            return this.parseDefinition(childTuple[0](), s2).map((definitionTuple) => {
                let value = childTuple[1]();
                let definition = definitionTuple[1]();
                let variant = new DataAccess(value, "_variant", Primitive.Unknown);
                let type = value.type();
                let generate = type.findName().orElse("");
                let temp = new SymbolValue(generate + "Variant." + definition.type().findName().orElse(""), Primitive.Unknown);
                let functionSegment = new Statement(depth + 1, new Initialization(definition, new Cast(value, definition.type())));
                return new Tuple2Impl(definitionTuple[0]().addFunctionSegment(functionSegment).define(definition), new Operation(variant, Operator.EQUALS, temp));
            });
        });
    }
    parseMethodReference(state, input, depth) {
        return this.last(input, "::", (s, s2) => {
            let tuple = this.parseValue(state, s, depth);
            return new Some(new Tuple2Impl(tuple[0](), new DataAccess(tuple[1](), s2, Primitive.Unknown)));
        });
    }
    parseNot(state, input, depth) {
        let stripped = input.strip();
        if (stripped.startsWith("!")) {
            let slice = stripped.substring(1);
            let tuple = this.parseValue(state, slice, depth);
            let value = tuple[1]();
            return new Some(new Tuple2Impl(tuple[0](), new Not(value)));
        }
        return new None();
    }
    parseLambda(state, input, depth) {
        return this.first(input, "->", (beforeArrow, valueString) => {
            let strippedBeforeArrow = beforeArrow.strip();
            if (this.isSymbol(strippedBeforeArrow)) {
                let type = Primitive.Unknown;
                if ( /* state.typeRegister instanceof Some */( /* var expectedType */)) {
                    if ( /* expectedType */._variant === Variant.FunctionType) {
                        let functionType = /* expectedType */ as, FunctionType;
                        type = functionType.arguments.get(0).orElse( /* null */);
                    }
                }
                return this.assembleLambda(state, Lists.of(ImmutableDefinition.createSimpleDefinition(strippedBeforeArrow, type)), valueString, depth);
            }
            if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")) {
                let parameterNames = this.divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), this.foldValueChar).iterate().map(strip).filter((value) => !value.isEmpty()).map((name) => ImmutableDefinition.createSimpleDefinition(name, Primitive.Unknown)).collect(new ListCollector());
                return this.assembleLambda(state, parameterNames, valueString, depth);
            }
            return new None();
        });
    }
    assembleLambda(state, definitions, valueString, depth) {
        let strippedValueString = valueString.strip();
        /* Tuple2Impl<CompileState, LambdaValue> value */ ;
        let state2 = state.defineAll(definitions);
        if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}")) {
            let value1 = this.parseStatements(state2, strippedValueString.substring(1, strippedValueString.length() - 1), (state1, input1) => this.parseFunctionSegment(state1, input1, depth + 1));
            let right = value1[1]();
            new Tuple2Impl(value1[0](), new BlockLambdaValue(depth, right));
        }
        else {
            let value1 = this.parseValue(state2, strippedValueString, depth);
            new Tuple2Impl(value1[0](), value1[1]());
        }
        let right = /* value */ .right();
        return new Some(new Tuple2Impl(left(), new Lambda(definitions, right)));
    }
    parseDigits(state, input) {
        let stripped = input.strip();
        if (this.isNumber(stripped)) {
            return new Some(new Tuple2Impl(state, new SymbolValue(stripped, Primitive.Int)));
        }
        return new None();
    }
    isNumber(input) {
        /* String maybeTruncated */ ;
        if (input.startsWith("-")) {
            input.substring(1);
        }
        else {
            input;
        }
        return this.areAllDigits( /* maybeTruncated */);
    }
    areAllDigits(input) {
        /* for (var i = 0; i < input.length(); i++) */ {
            let c = input.charAt( /* i */);
            if ( /* Character */.isDigit(c)) {
                /* continue */ ;
            }
            return false;
        }
        return true;
    }
    parseInvokable(state, input, depth) {
        return this.suffix(input.strip(), ")", (withoutEnd) => {
            return this.split(() => this.toLast(withoutEnd, "", this.foldInvocationStart), (callerWithEnd, argumentsString) => {
                return this.suffix(callerWithEnd, "(", (callerString) => {
                    return this.assembleInvokable(state, depth, argumentsString, callerString.strip());
                });
            });
        });
    }
    assembleInvokable(state, depth, argumentsString, callerString) {
        let callerTuple = this.invocationHeader(state, depth, callerString);
        let oldCallerState = callerTuple[0]();
        let oldCaller = callerTuple[1]();
        let newCaller = this.modifyCaller(oldCallerState, oldCaller);
        let callerType = this.findCallerType(newCaller);
        let argumentsTuple = this.parseValuesWithIndices(oldCallerState, argumentsString, (currentState, pair) => {
            let index = pair.left();
            let element = pair.right();
            let expectedType = callerType.arguments.get(index).orElse(Primitive.Unknown);
            let withExpected = currentState.withExpectedType(expectedType);
            let valueTuple = this.parseArgument(withExpected, element, depth);
            let valueState = valueTuple[0]();
            let value = valueTuple[1]();
            let actualType = valueTuple[0]().typeRegister.orElse(Primitive.Unknown);
            return new Some(new Tuple2Impl(valueState, new Tuple2Impl(value, actualType)));
        }).orElseGet(() => new Tuple2Impl(oldCallerState, Lists.empty()));
        let argumentsState = argumentsTuple.left();
        let argumentsWithActualTypes = argumentsTuple.right();
        let arguments = argumentsWithActualTypes.iterate().map(Tuple2.left).map(this.retainValue).flatMap(Iterators.fromOption).collect(new ListCollector());
        let invokable = new Invokable(newCaller, arguments, callerType.returns);
        return new Some(new Tuple2Impl(argumentsState, invokable));
    }
    retainValue(argument) {
        if (argument._variant === ArgumentVariant.Value) {
            let value = argument;
            return new Some(value);
        }
        return new None();
    }
    parseArgument(state, element, depth) {
        if (element.isEmpty()) {
            return new Tuple2Impl(state, new Whitespace());
        }
        let tuple = this.parseValue(state, element, depth);
        return new Tuple2Impl(tuple[0](), tuple[1]());
    }
    findCallerType(newCaller) {
        let callerType = new FunctionType(Lists.empty(), Primitive.Unknown);
        /* switch (newCaller) */ {
            /* case ConstructionCaller constructionCaller -> */ {
                callerType = /* constructionCaller */ .toFunction();
            }
            /* case Value value -> */ {
                let type = /* value */ .type();
                if (type._variant === Variant.FunctionType) {
                    let functionType = type;
                    callerType = functionType;
                }
            }
        }
        return callerType;
    }
    modifyCaller(state, oldCaller) {
        if (oldCaller._variant === CallerVariant.DataAccess) {
            let type = this.resolveType(access.parent, state);
            if ( /* type instanceof FunctionType */) {
                let access = oldCaller;
                return access.parent;
            }
        }
        return oldCaller;
    }
    resolveType(value, state) {
        return value.type();
    }
    invocationHeader(state, depth, callerString1) {
        if (callerString1.startsWith("new ")) {
            let input1 = callerString1.substring("new ".length());
            let map = this.parseType(state, input1).map((type) => {
                let right = type[1]();
                return new Tuple2Impl(type[0](), new ConstructionCaller(right));
            });
            if (map.isPresent()) {
                return map.orElse( /* null */);
            }
        }
        let tuple = this.parseValue(state, callerString1, depth);
        return new Tuple2Impl(tuple[0](), tuple[1]());
    }
    foldInvocationStart(state, c) {
        let appended = state.append(c);
        if (c ===  /*  '(' */) {
            let enter = appended.enter();
            if (enter.isShallow()) {
                return enter.advance();
            }
            return enter;
        }
        if (c ===  /*  ')' */) {
            return appended.exit();
        }
        return appended;
    }
    parseDataAccess(state, input, depth) {
        return this.last(input.strip(), ".", (parentString, rawProperty) => {
            let property = rawProperty.strip();
            if (!this.isSymbol(property)) {
                return new None();
            }
            let tuple = this.parseValue(state, parentString, depth);
            let parent = tuple[1]();
            let parentType = parent.type();
            if ( /* parentType instanceof TupleType */) {
                if (property.equals("left")) {
                    return new Some(new Tuple2Impl(state, new IndexValue(parent, new SymbolValue("0", Primitive.Int))));
                }
                if (property.equals("right")) {
                    return new Some(new Tuple2Impl(state, new IndexValue(parent, new SymbolValue("1", Primitive.Int))));
                }
            }
            let type = Primitive.Unknown;
            if (parentType._variant === Variant.FindableType) {
                if ( /* objectType.find(property) instanceof Some */( /* var memberType */)) {
                    let objectType = parentType;
                    type =  /* memberType */;
                }
            }
            return new Some(new Tuple2Impl(tuple[0](), new DataAccess(parent, property, type)));
        });
    }
    parseString(state, input) {
        let stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some(new Tuple2Impl(state, new StringValue(stripped)));
        }
        return new None();
    }
    parseSymbolValue(state, value) {
        let stripped = value.strip();
        if (this.isSymbol(stripped)) {
            if ( /* state.resolveValue(stripped) instanceof Some */( /* var type */)) {
                return new Some(new Tuple2Impl(state, new SymbolValue(stripped, type)));
            }
            if ( /* state.resolveType(stripped) instanceof Some */( /* var type */)) {
                return new Some(new Tuple2Impl(state, new SymbolValue(stripped, type)));
            }
            return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
        }
        return new None();
    }
    parseOperation(state, value, depth, operator) {
        return this.first(value, operator.sourceRepresentation, (leftString, rightString) => {
            let leftTuple = this.parseValue(state, leftString, depth);
            let rightTuple = this.parseValue(leftTuple[0](), rightString, depth);
            let left = leftTuple[1]();
            let right = rightTuple[1]();
            return new Some(new Tuple2Impl(rightTuple[0](), new Operation(left, operator, right)));
        });
    }
    parseValuesOrEmpty(state, input, mapper) {
        return this.parseValues(state, input, mapper).orElseGet(() => new Tuple2Impl(state, Lists.empty()));
    }
    parseValues(state, input, mapper) {
        return this.parseValuesWithIndices(state, input, (state1, tuple) => mapper(state1, tuple.right()));
    }
    parseValuesWithIndices(state, input, mapper) {
        return this.parseAllWithIndices(state, input, this.foldValueChar, mapper);
    }
    parseParameter(state, input) {
        if (input.isBlank()) {
            return new Tuple2Impl(state, new Whitespace());
        }
        return this.parseDefinition(state, input).map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]())).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
    }
    parseDefinitionStatement(input, depth, state) {
        return this.suffix(input.strip(), ";", (withoutEnd) => {
            return this.parseDefinition(state, withoutEnd).map((result) => {
                let definition = result[1]();
                return new Tuple2Impl(result[0](), new ClassDefinition(definition, depth));
            });
        });
    }
    parseDefinition(state, input) {
        return this.last(input.strip(), " ", (beforeName, name) => {
            return this.split(() => this.toLast(beforeName, " ", this.foldTypeSeparator), (beforeType, type) => {
                return this.last(beforeType, "\n", (s, s2) => {
                    let annotations = this.parseAnnotations(s);
                    return this.getOr(state, name, s2, type, annotations);
                }).or(() => {
                    return this.getOr(state, name, beforeType, type, Lists.empty());
                });
            }).or(() => this.assembleDefinition(state, Lists.empty(), new None(), name, Lists.empty(), beforeName));
        });
    }
    getOr(state, name, beforeType, type, annotations) {
        return this.suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
            return this.first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
                let typeParams = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(new Tuple2Impl(state1, s.strip())));
                return this.assembleDefinition(typeParams[0](), annotations, new Some(beforeTypeParams), name, typeParams[1](), type);
            });
        }).or(() => {
            return this.assembleDefinition(state, annotations, new Some(beforeType), name, Lists.empty(), type);
        });
    }
    toLast(input, separator, folder) {
        let divisions = this.divideAll(input, folder);
        return divisions.removeLast().map((removed) => {
            let left = removed[0]().iterate().collect(new Joiner(separator)).orElse("");
            let right = removed[1]();
            return new Tuple2Impl(left, right);
        });
    }
    foldTypeSeparator(state, c) {
        if (c ===  /*  ' '  */ && state.isLevel()) {
            return state.advance();
        }
        let appended = state.append(c);
        if (c === /*  ' */  /* ' */) {
            return appended.enter();
        }
        if (c ===  /*  '>' */) {
            return appended.exit();
        }
        return appended;
    }
    assembleDefinition(state, annotations, beforeTypeParams, name, typeParams, type) {
        return this.parseType(state.withTypeParams(typeParams), type).map((type1) => {
            let node = new ImmutableDefinition(annotations, beforeTypeParams, name.strip(), type1[1](), typeParams);
            return new Tuple2Impl(type1[0](), node);
        });
    }
    foldValueChar(state, c) {
        if (c ===  /*  ','  */ && state.isLevel()) {
            return state.advance();
        }
        let appended = state.append(c);
        if (c === /*  ' */ - /* ' */) {
            let peeked = appended.peek();
            if (peeked ===  /*  '>' */) {
                return appended.popAndAppendToOption().orElse(appended);
            }
            else {
                return appended;
            }
        }
        if (c === /*  ' */  /* '  */ || c ===  /*  '('  */ || c ===  /*  '{' */) {
            return appended.enter();
        }
        if (c ===  /*  '>'  */ || c ===  /*  ')'  */ || c ===  /*  '}' */) {
            return appended.exit();
        }
        return appended;
    }
    parseType(state, input) {
        let stripped = input.strip();
        if (stripped.equals("int") || stripped.equals("Integer")) {
            return new Some(new Tuple2Impl(state, Primitive.Int));
        }
        if (stripped.equals("String") || stripped.equals("char") || stripped.equals("Character")) {
            return new Some(new Tuple2Impl(state, Primitive.String));
        }
        if (stripped.equals("var")) {
            return new Some(new Tuple2Impl(state, Primitive.Unknown));
        }
        if (stripped.equals("boolean")) {
            return new Some(new Tuple2Impl(state, Primitive.Boolean));
        }
        if (stripped.equals("void")) {
            return new Some(new Tuple2Impl(state, Primitive.Void));
        }
        if (this.isSymbol(stripped)) {
            if ( /* state.resolveType(stripped) instanceof Some */( /* var resolved */)) {
                return new Some(new Tuple2Impl(state));
            }
            else {
                return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
            }
        }
        return this.parseTemplate(state, input).or(() => this.varArgs(state, input));
    }
    varArgs(state, input) {
        return this.suffix(input, "...", (s) => {
            return this.parseType(state, s).map((inner) => {
                let newState = inner[0]();
                let child = inner[1]();
                return new Tuple2Impl(newState, new ArrayType(child));
            });
        });
    }
    assembleTemplate(base, state, arguments) {
        let children = arguments.iterate().map(this.retainType).flatMap(Iterators.fromOption).collect(new ListCollector());
        if (base.equals("BiFunction")) {
            return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse( /* null */), children.get(1).orElse( /* null */)), children.get(2).orElse( /* null */)));
        }
        if (base.equals("Function")) {
            return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse( /* null */)), children.get(1).orElse( /* null */)));
        }
        if (base.equals("Predicate")) {
            return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse( /* null */)), Primitive.Boolean));
        }
        if (base.equals("Supplier")) {
            return new Tuple2Impl(state, new FunctionType(Lists.empty(), children.get(0).orElse( /* null */)));
        }
        if (base.equals("Consumer")) {
            return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse( /* null */)), Primitive.Void));
        }
        if (base.equals("Tuple2") && children.size() >= 2) {
            return new Tuple2Impl(state, new TupleType(children));
        }
        if (state.resolveType(base)._variant === OptionVariant.Some) {
            let baseType = some.value;
            if (baseType._variant === TypeVariant.FindableType) {
                let some = state.resolveType(base);
                let findableType = baseType;
                return new Tuple2Impl(state, new Template(findableType, children));
            }
        }
        return new Tuple2Impl(state, new Template(new Placeholder(base), children));
    }
    parseTemplate(state, input) {
        return this.suffix(input.strip(), ">", (withoutEnd) => {
            return this.first(withoutEnd, "<", (base, argumentsString) => {
                let strippedBase = base.strip();
                return this.parseValues(state, argumentsString, this.argument).map((argumentsTuple) => {
                    return this.assembleTemplate(strippedBase, argumentsTuple[0](), argumentsTuple[1]());
                });
            });
        });
    }
    retainType(argument) {
        if (argument._variant === ArgumentVariant.Type) {
            let type = argument;
            return new Some(type);
        }
        else {
            return new None();
        }
    }
    argument(state, input) {
        if (input.isBlank()) {
            return new Some(new Tuple2Impl(state, new Whitespace()));
        }
        return this.parseType(state, input).map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]()));
    }
    last(input, infix, mapper) {
        return this.infix(input, infix, this.findLast, mapper);
    }
    findLast(input, infix) {
        let index = input.lastIndexOf(infix);
        if (index === -1) {
            return new None();
        }
        return new Some(index);
    }
    first(input, infix, mapper) {
        return this.infix(input, infix, this.findFirst, mapper);
    }
    split(splitter, splitMapper) {
        return splitter().flatMap((splitTuple) => splitMapper(splitTuple[0](), splitTuple[1]()));
    }
    infix(input, infix, locator, mapper) {
        return this.split(() => locator(input, infix).map((index) => {
            let left = input.substring(0, index);
            let right = input.substring(index + infix.length());
            return new Tuple2Impl(left, right);
        }), mapper);
    }
    findFirst(input, infix) {
        let index = input.indexOf(infix);
        if (index === -1) {
            return new None();
        }
        return new Some(index);
    }
}
/*  */ 
