"use strict";
var OptionVariant;
(function (OptionVariant) {
    OptionVariant[OptionVariant["Some"] = 0] = "Some";
    OptionVariant[OptionVariant["None"] = 1] = "None";
})(OptionVariant || (OptionVariant = {}));
var ArgumentVariant;
(function (ArgumentVariant) {
    ArgumentVariant[ArgumentVariant["Type"] = 0] = "Type";
    ArgumentVariant[ArgumentVariant["Value"] = 1] = "Value";
    ArgumentVariant[ArgumentVariant["Whitespace"] = 2] = "Whitespace";
})(ArgumentVariant || (ArgumentVariant = {}));
var ParameterVariant;
(function (ParameterVariant) {
    ParameterVariant[ParameterVariant["Definition"] = 0] = "Definition";
    ParameterVariant[ParameterVariant["Placeholder"] = 1] = "Placeholder";
    ParameterVariant[ParameterVariant["Whitespace"] = 2] = "Whitespace";
})(ParameterVariant || (ParameterVariant = {}));
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
    ValueVariant[ValueVariant["TupleNode"] = 11] = "TupleNode";
})(ValueVariant || (ValueVariant = {}));
var CallerVariant;
(function (CallerVariant) {
    CallerVariant[CallerVariant["ConstructionCaller"] = 0] = "ConstructionCaller";
    CallerVariant[CallerVariant["Value"] = 1] = "Value";
})(CallerVariant || (CallerVariant = {}));
var IncompleteClassSegmentVariant;
(function (IncompleteClassSegmentVariant) {
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["ClassDefinition"] = 0] = "ClassDefinition";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["ClassInitialization"] = 1] = "ClassInitialization";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["EnumValues"] = 2] = "EnumValues";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["IncompleteClassSegmentWrapper"] = 3] = "IncompleteClassSegmentWrapper";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["MethodPrototype"] = 4] = "MethodPrototype";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["Placeholder"] = 5] = "Placeholder";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["StructurePrototype"] = 6] = "StructurePrototype";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["Whitespace"] = 7] = "Whitespace";
})(IncompleteClassSegmentVariant || (IncompleteClassSegmentVariant = {}));
var ResultVariant;
(function (ResultVariant) {
    ResultVariant[ResultVariant["Ok"] = 0] = "Ok";
    ResultVariant[ResultVariant["Err"] = 1] = "Err";
})(ResultVariant || (ResultVariant = {}));
/* private static final */ class None {
    map(mapper) {
        return new None() /* : None */;
    }
    isPresent() {
        return false;
    }
    orElse(other) {
        return other;
    }
    filter(predicate) {
        return new None() /* : None */;
    }
    orElseGet(supplier) {
        return supplier() /* : T */;
    }
    or(other) {
        return other() /* : Option<T> */;
    }
    flatMap(mapper) {
        return new None() /* : None */;
    }
    isEmpty() {
        return true;
    }
    and(other) {
        return new None() /* : None */;
    }
    ifPresent(consumer) {
    }
}
/* private */ class Some {
    constructor(value) {
        this.value /* : unknown */ = value;
    }
    map(mapper) {
        return new Some(mapper(this.value /* : unknown */) /* : R */) /* : Some */;
    }
    isPresent() {
        return true;
    }
    orElse(other) {
        return this.value /* : unknown */;
    }
    filter(predicate) {
        if (predicate(this.value /* : unknown */) /* : boolean */) {
            return this;
        }
        return new None() /* : None */;
    }
    orElseGet(supplier) {
        return this.value /* : unknown */;
    }
    or(other) {
        return this;
    }
    flatMap(mapper) {
        return mapper(this.value /* : unknown */) /* : Option<R> */;
    }
    isEmpty() {
        return false;
    }
    and(other) {
        return other() /* : Option<R> */.map /* : unknown */((otherValue) => [this.value /* : unknown */, otherValue]) /* : unknown */;
    }
    ifPresent(consumer) {
        /* consumer.accept(this.value) */ ;
    }
}
/* private static */ class SingleHead {
    constructor(retrievableValue) {
        this.retrievableValue /* : T */ = retrievableValue;
        this.retrieved /* : boolean */ = false;
    }
    next() {
        if (this.retrieved /* : boolean */) {
            return new None() /* : None */;
        }
        this.retrieved /* : boolean */ = true;
        return new Some(this.retrievableValue /* : T */) /* : Some */;
    }
}
/* private static */ class EmptyHead {
    next() {
        return new None() /* : None */;
    }
}
/* private */ class HeadedQuery {
    constructor(head) {
        this.head /* : unknown */ = head;
    }
    fold(initial, folder) {
        let current = initial;
        while (true) {
            let finalCurrent = current;
            let option = this.head /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */((inner) => folder(finalCurrent, inner) /* : R */) /* : unknown */;
            if (option._UnknownVariant /* : unknown */ === UnknownVariant.Some) {
                let some = option;
                current = some.value /* : unknown */;
            }
            else {
                return current;
            }
        }
    }
    map(mapper) {
        return new HeadedQuery(new MapHead(this.head /* : unknown */, mapper) /* : MapHead */) /* : HeadedQuery */;
    }
    collect(collector) {
        return this.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */(collector.createInitial /* : unknown */() /* : unknown */, collector.fold /* : unknown */) /* : R */;
    }
    filter(predicate) {
        return this.flatMap /* : (arg0 : (arg0 : T) => Query<R>) => Query<R> */((element) => {
            if (predicate(element) /* : boolean */) {
                return new HeadedQuery(new SingleHead(element) /* : SingleHead */) /* : HeadedQuery */;
            }
            return new HeadedQuery(new EmptyHead() /* : EmptyHead */) /* : HeadedQuery */;
        }) /* : Query<R> */;
    }
    next() {
        return this.head /* : unknown */.next /* : unknown */() /* : unknown */;
    }
    flatMap(f) {
        return new HeadedQuery(new FlatMapHead(this.head /* : unknown */, f) /* : FlatMapHead */) /* : HeadedQuery */;
    }
    zip(other) {
        return new HeadedQuery(new ZipHead(this.head /* : unknown */, other) /* : ZipHead */) /* : HeadedQuery */;
    }
}
/* private static */ class RangeHead /*  */ {
    constructor(length) {
        this.length /* : number */ = length;
        this.counter /* : number */ = 0;
    }
    next() {
        if (this.counter /* : number */ < this.length /* : unknown */) {
            let value = this.counter /* : number */;
            /* this.counter++ */ ;
            return new Some(value) /* : Some */;
        }
        return new None() /* : None */;
    }
}
/* private static */ class Lists /*  */ {
}
/* private */ class Definition /*  */ {
    constructor(annotations, modifiers, name, type, typeParams) {
        this.annotations /* : unknown */ = annotations;
        this.modifiers /* : unknown */ = modifiers;
        this.name /* : unknown */ = name;
        this.type /* : unknown */ = type;
        this.typeParams /* : unknown */ = typeParams;
    }
    static createSimpleDefinition(name, type) {
        return new Definition(Lists.empty /* : unknown */() /* : unknown */, Lists.empty /* : unknown */() /* : unknown */, name, type, Lists.empty /* : unknown */() /* : unknown */) /* : Definition */;
    }
    findName() {
        return this.name /* : unknown */;
    }
    findType() {
        return this.type /* : unknown */;
    }
    generate() {
        return this.generateWithParams /* : (arg0 : string) => string */("") /* : string */;
    }
    generateType() {
        if (this.type /* : unknown */ === Primitive.Unknown /* : unknown */) {
            return "";
        }
        return " : " + this.type /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    joinModifiers() {
        return this.modifiers /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((value) => value + " ") /* : unknown */.collect /* : unknown */(new Joiner("") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    joinTypeParams() {
        return this.typeParams /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    mapType(mapper) {
        return new Definition(this.annotations /* : unknown */, this.modifiers /* : unknown */, this.name /* : unknown */, mapper(this.type /* : unknown */) /* : Type */, this.typeParams /* : unknown */) /* : Definition */;
    }
    generateWithParams(joinedParameters) {
        let joinedAnnotations = this.annotations /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((value) => "@" + value + " ") /* : unknown */.collect /* : unknown */(Joiner.empty /* : unknown */() /* : unknown */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        let joined = this.joinTypeParams /* : () => string */() /* : string */;
        let before = this.joinModifiers /* : () => string */() /* : string */;
        let typeString = this.generateType /* : () => string */() /* : string */;
        return joinedAnnotations + before + this.name /* : unknown */ + joined + joinedParameters + typeString;
    }
    createDefinition(paramTypes) {
        let type1 = new FunctionType(paramTypes, this.type /* : unknown */) /* : FunctionType */;
        return new Definition(this.annotations /* : unknown */, this.modifiers /* : unknown */, this.name /* : unknown */, type1, this.typeParams /* : unknown */) /* : Definition */;
    }
    containsAnnotation(annotation) {
        return this.annotations /* : unknown */.contains /* : unknown */(annotation) /* : unknown */;
    }
    removeAnnotations() {
        return new Definition(Lists.empty /* : unknown */() /* : unknown */, this.modifiers /* : unknown */, this.name /* : unknown */, this.type /* : unknown */, this.typeParams /* : unknown */) /* : Definition */;
    }
    toString() {
        return "ImmutableDefinition[" + "annotations=" + this.annotations /* : unknown */ + ", " + "maybeBefore=" + this.modifiers /* : unknown */ + ", " + "findName=" + this.name /* : unknown */ + ", " + "findType=" + this.type /* : unknown */ + ", " + "typeParams=" + this.typeParams /* : unknown */ + "]";
    }
}
/* private */ class ObjectRefType /*  */ {
    constructor(name) {
        this.name /* : unknown */ = name;
    }
    generate() {
        return this.name /* : unknown */;
    }
    replace(mapping) {
        return new ObjectRefType(this.name /* : unknown */) /* : ObjectRefType */;
    }
    findName() {
        return this.name /* : unknown */;
    }
}
/* private */ class TypeParam /*  */ {
    constructor(value) {
        this.value /* : unknown */ = value;
    }
    generate() {
        return this.value /* : unknown */;
    }
    replace(mapping) {
        return mapping.find /* : unknown */(this.value /* : unknown */) /* : unknown */.orElse /* : unknown */(this) /* : unknown */;
    }
    findName() {
        return "";
    }
}
/* private */ class CompileState /*  */ {
    constructor(structures, definitions, structureTypes, structNames, typeParams, typeRegister, functionSegments) {
        this.structures /* : unknown */ = structures;
        this.definitions /* : unknown */ = definitions;
        this.structureTypes /* : unknown */ = structureTypes;
        this.structNames /* : unknown */ = structNames;
        this.typeParams /* : unknown */ = typeParams;
        this.typeRegister /* : unknown */ = typeRegister;
        this.functionSegments /* : unknown */ = functionSegments;
    }
    static createInitial() {
        return new CompileState(Lists.empty /* : unknown */() /* : unknown */, Lists.of /* : unknown */(Lists.empty /* : unknown */() /* : unknown */) /* : unknown */, Lists.empty /* : unknown */() /* : unknown */, Lists.empty /* : unknown */() /* : unknown */, Lists.empty /* : unknown */() /* : unknown */, new None() /* : None */, Lists.empty /* : unknown */() /* : unknown */) /* : CompileState */;
    }
    resolveValue(name) {
        return this.definitions /* : unknown */.iterateReversed /* : unknown */() /* : unknown */.flatMap /* : unknown */(List.iterate /* : unknown */) /* : unknown */.filter /* : unknown */((definition) => definition.findName /* : unknown */() /* : unknown */ === name) /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */(Definition.findType /* : unknown */) /* : unknown */;
    }
    addStructure(structure) {
        return new CompileState(this.structures /* : unknown */.addLast /* : unknown */(structure) /* : unknown */, this.definitions /* : unknown */, this.structureTypes /* : unknown */, this.structNames /* : unknown */, this.typeParams /* : unknown */, this.typeRegister /* : unknown */, this.functionSegments /* : unknown */) /* : CompileState */;
    }
    defineAll(definitions) {
        let defined = this.definitions /* : unknown */.mapLast /* : unknown */((frame) => frame.addAllLast /* : unknown */(definitions) /* : unknown */) /* : unknown */;
        return new CompileState(this.structures /* : unknown */, defined, this.structureTypes /* : unknown */, this.structNames /* : unknown */, this.typeParams /* : unknown */, this.typeRegister /* : unknown */, this.functionSegments /* : unknown */) /* : CompileState */;
    }
    resolveType(name) {
        let maybe = this.structNames /* : unknown */.last /* : unknown */() /* : unknown */.filter /* : unknown */((inner) => inner.left /* : unknown */() /* : unknown */ === name) /* : unknown */;
        if (maybe._OptionVariant /* : unknown */ === OptionVariant.Some) {
            let some = maybe;
            let found = some.value /* : unknown */;
            return new Some(new ObjectRefType(found.left /* : unknown */() /* : unknown */) /* : ObjectRefType */) /* : Some */;
        }
        let maybeTypeParam = this.typeParams /* : unknown */.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((param) => param === name) /* : unknown */.next /* : unknown */() /* : unknown */;
        if (maybeTypeParam._UnknownVariant /* : unknown */ === UnknownVariant.Some) {
            let some = maybeTypeParam;
            return new Some(new TypeParam(some.value /* : unknown */) /* : TypeParam */) /* : Some */;
        }
        return this.findStructure /* : (arg0 : string) => Option<StructureType> */(name) /* : Option<StructureType> */.map /* : unknown */((type) => type) /* : unknown */;
    }
    define(definition) {
        return new CompileState(this.structures /* : unknown */, this.definitions /* : unknown */.mapLast /* : unknown */((frame) => frame.addLast /* : unknown */(definition) /* : unknown */) /* : unknown */, this.structureTypes /* : unknown */, this.structNames /* : unknown */, this.typeParams /* : unknown */, this.typeRegister /* : unknown */, this.functionSegments /* : unknown */) /* : CompileState */;
    }
    pushStructName(definition) {
        return new CompileState(this.structures /* : unknown */, this.definitions /* : unknown */, this.structureTypes /* : unknown */, this.structNames /* : unknown */.addLast /* : unknown */(definition) /* : unknown */, this.typeParams /* : unknown */, this.typeRegister /* : unknown */, this.functionSegments /* : unknown */) /* : CompileState */;
    }
    withTypeParams(typeParams) {
        return new CompileState(this.structures /* : unknown */, this.definitions /* : unknown */, this.structureTypes /* : unknown */, this.structNames /* : unknown */, this.typeParams /* : unknown */.addAllLast /* : unknown */(typeParams) /* : unknown */, this.typeRegister /* : unknown */, this.functionSegments /* : unknown */) /* : CompileState */;
    }
    withExpectedType(type) {
        return new CompileState(this.structures /* : unknown */, this.definitions /* : unknown */, this.structureTypes /* : unknown */, this.structNames /* : unknown */, this.typeParams /* : unknown */, new Some(type) /* : Some */, this.functionSegments /* : unknown */) /* : CompileState */;
    }
    popStructName() {
        return new CompileState(this.structures /* : unknown */, this.definitions /* : unknown */, this.structureTypes /* : unknown */, this.structNames /* : unknown */.removeLast /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2.left /* : unknown */) /* : unknown */.orElse /* : unknown */(this.structNames /* : unknown */) /* : unknown */, this.typeParams /* : unknown */, this.typeRegister /* : unknown */, this.functionSegments /* : unknown */) /* : CompileState */;
    }
    enterDefinitions() {
        return new CompileState(this.structures /* : unknown */, this.definitions /* : unknown */.addLast /* : unknown */(Lists.empty /* : unknown */() /* : unknown */) /* : unknown */, this.structureTypes /* : unknown */, this.structNames /* : unknown */, this.typeParams /* : unknown */, this.typeRegister /* : unknown */, this.functionSegments /* : unknown */) /* : CompileState */;
    }
    exitDefinitions() {
        let removed = this.definitions /* : unknown */.removeLast /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2.left /* : unknown */) /* : unknown */.orElse /* : unknown */(this.definitions /* : unknown */) /* : unknown */;
        return new CompileState(this.structures /* : unknown */, removed, this.structureTypes /* : unknown */, this.structNames /* : unknown */, this.typeParams /* : unknown */, this.typeRegister /* : unknown */, this.functionSegments /* : unknown */) /* : CompileState */;
    }
    addType(thisType) {
        return new CompileState(this.structures /* : unknown */, this.definitions /* : unknown */, this.structureTypes /* : unknown */.addLast /* : unknown */(thisType) /* : unknown */, this.structNames /* : unknown */, this.typeParams /* : unknown */, this.typeRegister /* : unknown */, this.functionSegments /* : unknown */) /* : CompileState */;
    }
    addFunctionSegment(segment) {
        return new CompileState(this.structures /* : unknown */, this.definitions /* : unknown */, this.structureTypes /* : unknown */, this.structNames /* : unknown */, this.typeParams /* : unknown */, this.typeRegister /* : unknown */, this.functionSegments /* : unknown */.addLast /* : unknown */(segment) /* : unknown */) /* : CompileState */;
    }
    clearFunctionSegments() {
        return new CompileState(this.structures /* : unknown */, this.definitions /* : unknown */, this.structureTypes /* : unknown */, this.structNames /* : unknown */, this.typeParams /* : unknown */, this.typeRegister /* : unknown */, Lists.empty /* : unknown */() /* : unknown */) /* : CompileState */;
    }
    isCurrentStructName(stripped) {
        return stripped === this.structNames /* : unknown */.last /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2.left /* : unknown */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    findStructure(name) {
        return this.structureTypes /* : unknown */.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((structureType) => structureType.name /* : unknown */ === name) /* : unknown */.next /* : unknown */() /* : unknown */;
    }
}
/* private */ class StructureType /*  */ {
    constructor(name, variants, definitions) {
        this.name /* : unknown */ = name;
        this.variants /* : unknown */ = variants;
        this.definitions /* : unknown */ = definitions;
    }
    hasVariant(name) {
        return this.variants /* : unknown */.contains /* : unknown */(name) /* : unknown */;
    }
    generate() {
        return this.name /* : unknown */;
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return this.name /* : unknown */;
    }
    find(property) {
        return this.definitions /* : unknown */.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((definition) => definition.name /* : unknown */ === property) /* : unknown */.map /* : unknown */(Definition.type /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */;
    }
}
/* private static */ class DivideState /*  */ {
    constructor(input, index, segments, buffer, depth) {
        this.segments /* : List<string> */ = segments;
        this.buffer /* : string */ = buffer;
        this.depth /* : number */ = depth;
        this.input /* : string */ = input;
        this.index /* : number */ = index;
    }
    static createInitial(input) {
        return new DivideState(input, 0, Lists.empty /* : unknown */() /* : unknown */, "", 0) /* : DivideState */;
    }
    advance() {
        this.segments /* : List<string> */ = this.segments /* : List<string> */.addLast /* : unknown */(this.buffer /* : string */) /* : unknown */;
        this.buffer /* : string */ = "";
        return this;
    }
    append(c) {
        this.buffer /* : string */ = this.buffer /* : string */ + c;
        return this;
    }
    enter() {
        /* this.depth++ */ ;
        return this;
    }
    isLevel() {
        return this.depth /* : number */ === 0;
    }
    exit() {
        /* this.depth-- */ ;
        return this;
    }
    isShallow() {
        return this.depth /* : number */ === 1;
    }
    pop() {
        if (this.index /* : number */ < Strings.length /* : unknown */(this.input /* : string */) /* : unknown */) {
            let c = this.input /* : string */.charAt /* : unknown */(this.index /* : number */) /* : unknown */;
            return new Some([c, new DivideState(this.input /* : string */, this.index /* : number */ + 1, this.segments /* : List<string> */, this.buffer /* : string */, this.depth /* : number */) /* : DivideState */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    popAndAppendToTuple() {
        return this.pop /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */.map /* : unknown */((tuple) => {
            let c = tuple.left /* : unknown */() /* : unknown */;
            let right = tuple.right /* : unknown */() /* : unknown */;
            return [c, right.append /* : unknown */(c) /* : unknown */];
        }) /* : unknown */;
    }
    popAndAppendToOption() {
        return this.popAndAppendToTuple /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */.map /* : unknown */(Tuple2.right /* : unknown */) /* : unknown */;
    }
    peek() {
        return this.input /* : string */.charAt /* : unknown */(this.index /* : number */) /* : unknown */;
    }
}
/* private */ class Joiner /*  */ {
    constructor(delimiter) {
        this.delimiter /* : unknown */ = delimiter;
    }
    static empty() {
        return new Joiner("") /* : Joiner */;
    }
    createInitial() {
        return new None() /* : None */;
    }
    fold(current, element) {
        return new Some(current.map /* : unknown */((inner) => inner + this.delimiter /* : unknown */ + element) /* : unknown */.orElse /* : unknown */(element) /* : unknown */) /* : Some */;
    }
}
/* private static */ class ListCollector {
    createInitial() {
        return Lists.empty /* : unknown */() /* : unknown */;
    }
    fold(current, element) {
        return current.addLast /* : unknown */(element) /* : unknown */;
    }
}
/* private static */ class FlatMapHead {
    constructor(head, mapper) {
        this.mapper /* : (arg0 : T) => Query<R> */ = mapper;
        this.current /* : Option<Query<R>> */ = new None() /* : None */;
        this.head /* : Head<T> */ = head;
    }
    next() {
        while (true) {
            if (this.current /* : Option<Query<R>> */.isPresent /* : unknown */() /* : unknown */) {
                let inner = this.current /* : Option<Query<R>> */.orElse /* : unknown */(null) /* : unknown */;
                let maybe = inner.next /* : unknown */() /* : unknown */;
                if (maybe.isPresent /* : unknown */() /* : unknown */) {
                    return maybe;
                }
                else {
                    this.current /* : Option<Query<R>> */ = new None() /* : None */;
                }
            }
            let outer = this.head /* : Head<T> */.next /* : unknown */() /* : unknown */;
            if (outer.isPresent /* : unknown */() /* : unknown */) {
                this.current /* : Option<Query<R>> */ = outer.map /* : unknown */(this.mapper /* : (arg0 : T) => Query<R> */) /* : unknown */;
            }
            else {
                return new None() /* : None */;
            }
        }
    }
}
/* private */ class ArrayType /*  */ {
    constructor(right) {
        this.right /* : unknown */ = right;
    }
    generate() {
        return this.right /* : unknown */.generate /* : unknown */() /* : unknown */ + "[]";
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return "";
    }
}
/* private static final */ class Whitespace /*  */ {
    generate() {
        return "";
    }
    maybeCreateDefinition() {
        return new None() /* : None */;
    }
}
/* private static */ class Queries /*  */ {
    static fromOption(option) {
        let single = option.map /* : unknown */(SingleHead.new /* : unknown */) /* : unknown */;
        return new HeadedQuery(single.orElseGet /* : unknown */(EmptyHead.new /* : unknown */) /* : unknown */) /* : HeadedQuery */;
    }
    static from(elements) {
        return new HeadedQuery(new RangeHead(elements.length /* : unknown */) /* : RangeHead */) /* : HeadedQuery */.map /* : unknown */((index) =>  /* elements[index] */) /* : unknown */;
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
        this.arguments /* : unknown */ = arguments;
        this.returns /* : unknown */ = returns;
    }
    generate() {
        let joined = this.arguments /* : unknown */() /* : unknown */.iterateWithIndices /* : unknown */() /* : unknown */.map /* : unknown */((pair) => "arg" + pair.left /* : unknown */() /* : unknown */ + " : " + pair.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + joined + ") => " + this.returns /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    replace(mapping) {
        return new FunctionType(this.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((type) => type.replace /* : unknown */(mapping) /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */, this.returns /* : unknown */.replace /* : unknown */(mapping) /* : unknown */) /* : FunctionType */;
    }
    findName() {
        return "";
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
        this.arguments /* : unknown */ = arguments;
    }
    generate() {
        let joinedArguments = this.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Type.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "[" + joinedArguments + "]";
    }
    replace(mapping) {
        return new TupleType(this.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((child) => child.replace /* : unknown */(mapping) /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */) /* : TupleType */;
    }
    findName() {
        return "";
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
        this.base /* : unknown */ = base;
        this.arguments /* : unknown */ = arguments;
    }
    generate() {
        let joinedArguments = this.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Type.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return this.base /* : unknown */.generate /* : unknown */() /* : unknown */ + joinedArguments;
    }
    replace(mapping) {
        let collect = this.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((argument) => argument.replace /* : unknown */(mapping) /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        return new Template(this.base /* : unknown */, collect) /* : Template */;
    }
    findName() {
        return this.base /* : unknown */.findName /* : unknown */() /* : unknown */;
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
        this.input /* : unknown */ = input;
    }
    generate() {
        return generatePlaceholder(this.input /* : unknown */) /* : string */;
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return "";
    }
    maybeCreateDefinition() {
        return new None() /* : None */;
    }
}
/* private */ class StringValue /*  */ {
    constructor(value) {
        this.value /* : unknown */ = value;
    }
    generate() {
        return "\"" + this.value + "\"";
    }
}
/* private */ class DataAccess /*  */ {
    constructor(parent, property, type) {
        this.parent /* : unknown */ = parent;
        this.property /* : unknown */ = property;
        this.type /* : unknown */ = type;
    }
    generate() {
        return this.parent /* : unknown */.generate /* : unknown */() /* : unknown */ + "." + this.property /* : unknown */ + createDebugString(this.type /* : unknown */) /* : unknown */;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(type) {
        this.type /* : unknown */ = type;
    }
    generate() {
        return "new " + this.type /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    toFunction() {
        return new FunctionType(Lists.empty /* : unknown */() /* : unknown */, this.type /* : unknown */) /* : FunctionType */;
    }
}
/* private */ class Operator /*  */ {
    constructor(sourceRepresentation, targetRepresentation) {
        this.GREATER_THAN_OR_EQUALS = Operator();
        this.LESS_THAN = Operator();
        this.sourceRepresentation /* : unknown */ = sourceRepresentation;
        this.targetRepresentation /* : unknown */ = targetRepresentation;
    }
}
Operator.ADD = new Operator("+", "+") /* : Operator */;
Operator.AND = new Operator("&&", "&&") /* : Operator */;
Operator.EQUALS = new Operator("==", "===") /* : Operator */;
Operator.OR = new Operator("||", "||") /* : Operator */;
Operator.SUBTRACT = new Operator("-", "-") /* : Operator */;
/* private */ class Operation /*  */ {
    constructor(left, operator, right) {
        this.left /* : unknown */ = left;
        this.operator /* : unknown */ = operator;
        this.right /* : unknown */ = right;
    }
    generate() {
        return this.left /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */ + " " + this.operator /* : unknown */.targetRepresentation /* : unknown */ + " " + this.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Not /*  */ {
    constructor(value) {
        this.value /* : unknown */ = value;
    }
    generate() {
        return "!" + this.value /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class BlockLambdaValue /*  */ {
    constructor(depth, statements) {
        this.depth /* : unknown */ = depth;
        this.statements /* : unknown */ = statements;
    }
    generate() {
        return "{" + this.joinStatements() + createIndent(this.depth) + "}";
    }
    joinStatements() {
        return this.statements /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(FunctionSegment.generate /* : unknown */) /* : unknown */.collect /* : unknown */(Joiner.empty /* : unknown */() /* : unknown */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameters, body) {
        this.parameters /* : unknown */ = parameters;
        this.body /* : unknown */ = body;
    }
    generate() {
        let joined = this.parameters /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Definition.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + joined + ") => " + this.body /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Invokable /*  */ {
    constructor(caller, arguments, type) {
        this.caller /* : unknown */ = caller;
        this.arguments /* : unknown */ = arguments;
        this.type /* : unknown */ = type;
    }
    generate() {
        let joined = this.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Value.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return this.caller /* : unknown */.generate /* : unknown */() /* : unknown */ + "(" + joined + ")" + createDebugString(this.type /* : unknown */) /* : unknown */;
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
        this.parent /* : unknown */ = parent;
        this.child /* : unknown */ = child;
    }
    generate() {
        return this.parent /* : unknown */.generate /* : unknown */() /* : unknown */ + "[" + this.child.generate() + "]";
    }
}
/* private */ class SymbolValue /*  */ {
    constructor(value) {
        this.value /* : unknown */ = value;
    }
    generate() {
        return this.value /* : unknown */;
    }
}
/* private static */ class Maps /*  */ {
}
/* private static */ class ConstructorHeader /*  */ {
    createDefinition(paramTypes) {
        return Definition.createSimpleDefinition /* : unknown */("new", Primitive.Unknown /* : unknown */) /* : unknown */;
    }
    generateWithParams(joinedParameters) {
        return "constructor " + joinedParameters;
    }
}
/* private */ class FunctionNode /*  */ {
    constructor(depth, header, parameters, maybeStatements) {
        this.depth /* : unknown */ = depth;
        this.header /* : unknown */ = header;
        this.parameters /* : unknown */ = parameters;
        this.maybeStatements /* : unknown */ = maybeStatements;
    }
    static joinStatements(statements) {
        return statements.iterate /* : unknown */() /* : unknown */.map /* : unknown */(FunctionSegment.generate /* : unknown */) /* : unknown */.collect /* : unknown */(Joiner.empty /* : unknown */() /* : unknown */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    generate() {
        let indent = createIndent(this.depth /* : unknown */) /* : string */;
        let generatedHeader = this.header /* : unknown */.generateWithParams /* : unknown */(joinValues(this.parameters /* : unknown */) /* : string */) /* : unknown */;
        let generatedStatements = this.maybeStatements /* : unknown */.map /* : unknown */(FunctionNode.joinStatements /* : unknown */) /* : unknown */.map /* : unknown */((inner) => " {" + inner + indent + "}") /* : unknown */.orElse /* : unknown */(";") /* : unknown */;
        return indent + generatedHeader + generatedStatements;
    }
}
/* private */ class Block /*  */ {
    constructor(depth, header, statements) {
        this.depth /* : unknown */ = depth;
        this.header /* : unknown */ = header;
        this.statements /* : unknown */ = statements;
    }
    generate() {
        let indent = createIndent(this.depth /* : unknown */) /* : string */;
        let collect = this.statements /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(FunctionSegment.generate /* : unknown */) /* : unknown */.collect /* : unknown */(Joiner.empty /* : unknown */() /* : unknown */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return indent + this.header /* : unknown */.generate /* : unknown */() /* : unknown */ + "{" + collect + indent + "}";
    }
}
/* private */ class Conditional /*  */ {
    constructor(prefix, value1) {
        this.prefix /* : unknown */ = prefix;
        this.value1 /* : unknown */ = value1;
    }
    generate() {
        return this.prefix /* : unknown */ + " (" + this.value1.generate() + ")";
    }
}
/* private static */ class Else /*  */ {
    generate() {
        return "else ";
    }
}
/* private */ class Return /*  */ {
    constructor(value) {
        this.value /* : unknown */ = value;
    }
    generate() {
        return "return " + this.value /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Initialization /*  */ {
    constructor(definition, source) {
        this.definition /* : unknown */ = definition;
        this.source /* : unknown */ = source;
    }
    generate() {
        return "let " + this.definition /* : unknown */.generate /* : unknown */() /* : unknown */ + " = " + this.source /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class FieldInitialization /*  */ {
    constructor(definition, source) {
        this.definition /* : unknown */ = definition;
        this.source /* : unknown */ = source;
    }
    generate() {
        return this.definition /* : unknown */.generate /* : unknown */() /* : unknown */ + " = " + this.source /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Assignment /*  */ {
    constructor(destination, source) {
        this.destination /* : unknown */ = destination;
        this.source /* : unknown */ = source;
    }
    generate() {
        return this.destination /* : unknown */.generate /* : unknown */() /* : unknown */ + " = " + this.source /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Statement /*  */ {
    constructor(depth, value) {
        this.depth /* : unknown */ = depth;
        this.value /* : unknown */ = value;
    }
    generate() {
        return createIndent(this.depth /* : unknown */) /* : string */ + this.value /* : unknown */.generate /* : unknown */() /* : unknown */ + ";";
    }
}
/* private */ class MethodPrototype /*  */ {
    constructor(depth, header, parameters, content) {
        this.depth /* : unknown */ = depth;
        this.header /* : unknown */ = header;
        this.parameters /* : unknown */ = parameters;
        this.content /* : unknown */ = content;
    }
    createDefinition() {
        return this.header /* : unknown */.createDefinition /* : unknown */(this.findParamTypes /* : () => List<Type> */() /* : List<Type> */) /* : unknown */;
    }
    findParamTypes() {
        return this.parameters /* : unknown */() /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Definition.findType /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    maybeCreateDefinition() {
        return new Some(this.header /* : unknown */.createDefinition /* : unknown */(this.findParamTypes /* : () => List<Type> */() /* : List<Type> */) /* : unknown */) /* : Some */;
    }
}
/* private */ class IncompleteClassSegmentWrapper /*  */ {
    constructor(segment) {
        this.segment /* : unknown */ = segment;
    }
    maybeCreateDefinition() {
        return new None() /* : None */;
    }
}
/* private */ class ClassDefinition /*  */ {
    constructor(depth, definition) {
        this.depth /* : unknown */ = depth;
        this.definition /* : unknown */ = definition;
    }
    maybeCreateDefinition() {
        return new Some(this.definition /* : unknown */) /* : Some */;
    }
}
/* private */ class ClassInitialization /*  */ {
    constructor(depth, definition, value) {
        this.depth /* : unknown */ = depth;
        this.definition /* : unknown */ = definition;
        this.value /* : unknown */ = value;
    }
    maybeCreateDefinition() {
        return new Some(this.definition /* : unknown */) /* : Some */;
    }
}
/* private */ class TypeRef /*  */ {
    constructor(value) {
        this.value /* : unknown */ = value;
    }
}
/* private */ class StructurePrototype /*  */ {
    constructor(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants, interfaces, superTypes) {
        this.targetInfix /* : unknown */ = targetInfix;
        this.beforeInfix /* : unknown */ = beforeInfix;
        this.name /* : unknown */ = name;
        this.typeParams /* : unknown */ = typeParams;
        this.parameters /* : unknown */ = parameters;
        this.after /* : unknown */ = after;
        this.segments /* : unknown */ = segments;
        this.variants /* : unknown */ = variants;
        this.interfaces /* : unknown */ = interfaces;
        this.superTypes /* : unknown */ = superTypes;
    }
    static generateEnumEntries(variants) {
        return variants.iterate /* : unknown */() /* : unknown */.map /* : unknown */((inner) => "\n\t" + inner) /* : unknown */.collect /* : unknown */(new Joiner(",") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    createObjectType() {
        let definitionFromSegments = this.segments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(IncompleteClassSegment.maybeCreateDefinition /* : unknown */) /* : unknown */.flatMap /* : unknown */(Queries.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        return new StructureType(this.name /* : unknown */, this.variants /* : unknown */, definitionFromSegments) /* : StructureType */;
    }
    maybeCreateDefinition() {
        return new None() /* : None */;
    }
    joinTypeParams() {
        return this.typeParams /* : unknown */() /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    generateToEnum() {
        let variants = this.variants /* : unknown */;
        let joined = generateEnumEntries(variants) /* : string */;
        return "enum " + this.name + "Variant" + " {" + joined + "\n}\n";
    }
}
/* private */ class Cast /*  */ {
    constructor(value, type) {
        this.value /* : unknown */ = value;
        this.type /* : unknown */ = type;
    }
    generate() {
        return this.value /* : unknown */.generate /* : unknown */() /* : unknown */ + " as " + this.type /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Ok {
    constructor(value) {
        this.value /* : unknown */ = value;
    }
    mapValue(mapper) {
        return new Ok(mapper(this.value /* : unknown */) /* : R */) /* : Ok */;
    }
    match(whenOk, whenErr) {
        return whenOk(this.value /* : unknown */) /* : R */;
    }
}
/* private */ class Err {
    constructor(error) {
        this.error /* : unknown */ = error;
    }
    mapValue(mapper) {
        return new Err(this.error /* : unknown */) /* : Err */;
    }
    match(whenOk, whenErr) {
        return whenErr(this.error /* : unknown */) /* : R */;
    }
}
/* private */ class JVMIOError /*  */ {
    constructor(error) {
        this.error /* : unknown */ = error;
    }
    display() {
        let writer = new StringWriter() /* : StringWriter */;
        /* this.error.printStackTrace(new PrintWriter(writer)) */ ;
        return writer.toString /* : unknown */() /* : unknown */;
    }
}
/* private */ class TupleNode /*  */ {
    constructor(values) {
        this.values /* : unknown */ = values;
    }
    generate() {
        let joined = this.values /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Value.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "[" + joined + "]";
    }
}
/* private */ class MapHead {
    constructor(head, mapper) {
        this.head /* : unknown */ = head;
        this.mapper /* : unknown */ = mapper;
    }
    next() {
        return this.head /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */(this.mapper /* : unknown */) /* : unknown */;
    }
}
/* private */ class ZipHead {
    constructor(head, other) {
        this.head /* : unknown */ = head;
        this.other /* : unknown */ = other;
    }
    next() {
        return this.head /* : unknown */.next /* : unknown */() /* : unknown */.and /* : unknown */(this.other /* : unknown */.next /* : unknown */) /* : unknown */;
    }
}
/* private */ class EnumValue /*  */ {
    constructor(value, values) {
        this.value /* : unknown */ = value;
        this.values /* : unknown */ = values;
    }
    generate() {
        let s = this.values /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Value.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return this.value /* : unknown */ + "(" + s + ")";
    }
}
/* private */ class EnumValues /*  */ {
    constructor(values) {
        this.values /* : unknown */ = values;
    }
    generate() {
        return this.values /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(EnumValue.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    maybeCreateDefinition() {
        return new None() /* : None */;
    }
}
/* public static */ class Strings /*  */ {
    static isBlank(input) {
        return input.isBlank /* : unknown */() /* : unknown */;
    }
}
/* private */ class SymbolType /*  */ {
    constructor(value) {
        this.value /* : unknown */ = value;
    }
    generate() {
        return this.value /* : unknown */;
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return "";
    }
}
/* private */ class Primitive /*  */ {
    constructor(value) {
        this.value /* : string */ = value;
    }
    generate() {
        return this.value /* : string */;
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return this.name /* : unknown */() /* : unknown */;
    }
}
Primitive.Int = new Primitive("number") /* : Primitive */;
Primitive.String = new Primitive("string") /* : Primitive */;
Primitive.Boolean = new Primitive("boolean") /* : Primitive */;
Primitive.Unknown = new Primitive("unknown") /* : Primitive */;
Primitive.Void = new Primitive("void") /* : Primitive */;
/* private */ class BooleanValue /*  */ {
    constructor(value) {
        this.value /* : string */ = value;
    }
    generate() {
        return this.value /* : string */;
    }
}
BooleanValue.True = new BooleanValue("true") /* : BooleanValue */;
BooleanValue.False = new BooleanValue("false") /* : BooleanValue */;
/* public */ class Main /*  */ {
    static generatePlaceholder(input) {
        let replaced = input.replace /* : unknown */("/*", "content-start") /* : unknown */.replace /* : unknown */("*/", "content-end") /* : unknown */;
        return "/* " + replaced + " */";
    }
    static joinValues(retainParameters) {
        let inner = retainParameters.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Definition.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + inner + ")";
    }
    static createIndent(depth) {
        return "\n" + "\t".repeat /* : unknown */(depth) /* : unknown */;
    }
    static createDebugString(type) {
        if (!Main.isDebugEnabled /* : unknown */) {
            return "";
        }
        return generatePlaceholder(": " + type.generate /* : unknown */() /* : unknown */) /* : string */;
    }
    static isSymbol(input) {
        /* for (var i = 0; i < Strings.length(input); i++) */ {
            let c = input.charAt /* : unknown */(i) /* : unknown */;
            if (Character.isLetter /* : unknown */(c) /* : unknown */ || ( /* i != 0  */ && Character.isDigit /* : unknown */(c) /* : unknown */) /* : unknown */) {
                /* continue */ ;
            }
            return false;
        }
        return true;
    }
    static parseWhitespace(input, state) {
        if (Strings.isBlank /* : unknown */(input) /* : unknown */) {
            return new Some([state, new Whitespace() /* : Whitespace */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    static retainObjectRefType(type) {
        if (type._Variant /* : unknown */ === Variant.) {
            let objectRefType = type;
            return new Some(objectRefType) /* : Some */;
        }
        else {
            return new None() /* : None */;
        }
    }
    main() {
        let parent = this.findRoot /* : () => Path */() /* : Path */;
        let source = parent.resolve /* : unknown */("Main.java") /* : unknown */;
        let target = parent.resolve /* : unknown */("main.ts") /* : unknown */;
        /* source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(error -> System.err.println(error.display())) */ ;
    }
    compile(input) {
        let state = CompileState.createInitial /* : unknown */() /* : unknown */;
        let parsed = this.parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state, input, this.compileRootSegment /* : unknown */) /* : [CompileState, List<T>] */;
        let joined = parsed[0].structures /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(Joiner.empty /* : unknown */() /* : unknown */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return joined + this.generateStatements /* : unknown */(parsed[1]) /* : unknown */;
    }
    generateStatements(statements) {
        return this.generateAll /* : (arg0 : (arg0 : string, arg1 : string) => string, arg1 : List<string>) => string */(this.mergeStatements /* : unknown */, statements) /* : string */;
    }
    parseStatements(state, input, mapper) {
        return this.parseAllWithIndices /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state, input, this.foldStatementChar /* : unknown */, (state3, tuple) => new Some(mapper(state3, tuple.right /* : unknown */() /* : unknown */) /* : [CompileState, T] */) /* : Some */) /* : Option<[CompileState, List<T>]> */.orElseGet /* : unknown */(() => [state, Lists.empty /* : unknown */() /* : unknown */]) /* : unknown */;
    }
    generateAll(merger, elements) {
        return elements.iterate /* : unknown */() /* : unknown */.fold /* : unknown */("", merger) /* : unknown */;
    }
    parseAllWithIndices(state, input, folder, mapper) {
        let stringList = this.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input, folder) /* : List<string> */;
        return this.mapUsingState /* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(state, stringList, mapper) /* : Option<[CompileState, List<R>]> */;
    }
    mapUsingState(state, elements, mapper) {
        return elements.iterateWithIndices /* : unknown */() /* : unknown */.fold /* : unknown */(new Some([state, Lists.empty /* : unknown */() /* : unknown */]) /* : Some */, this.getOptionTuple2OptionBiFunction /* : (arg0 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => (arg0 : Option<[CompileState, List<R>]>, arg1 : [number, T]) => Option<[CompileState, List<R>]> */(mapper) /* : (arg0 : Option<[CompileState, List<R>]>, arg1 : [number, T]) => Option<[CompileState, List<R>]> */) /* : unknown */;
    }
    getOptionTuple2OptionBiFunction(mapper) {
        return (maybeCurrent, entry) => maybeCurrent.flatMap /* : unknown */((current) => {
            let currentState = current.left /* : unknown */() /* : unknown */;
            let currentList = current.right /* : unknown */() /* : unknown */;
            return mapper(currentState, entry) /* : Option<[CompileState, R]> */.map /* : unknown */((applied) => {
                return [applied.left /* : unknown */() /* : unknown */, currentList.addLast /* : unknown */(applied.right /* : unknown */() /* : unknown */) /* : unknown */];
            }) /* : unknown */;
        }) /* : unknown */;
    }
    mergeStatements(cache, statement) {
        return cache + statement;
    }
    divideAll(input, folder) {
        let current = DivideState.createInitial /* : unknown */(input) /* : unknown */;
        while (true) {
            let maybePopped = current.pop /* : unknown */() /* : unknown */.map /* : unknown */((tuple) => this.foldDecorated /* : (arg0 : (arg0 : DivideState, arg1 : string) => DivideState, arg1 : [string, DivideState]) => DivideState */(folder, tuple) /* : DivideState */) /* : unknown */;
            if (maybePopped.isPresent /* : unknown */() /* : unknown */) {
                current = maybePopped.orElse /* : unknown */(current) /* : unknown */;
            }
            else {
                /* break */ ;
            }
        }
        return current.advance /* : unknown */() /* : unknown */.segments /* : unknown */;
    }
    foldDecorated(folder, tuple) {
        return this.foldSingleQuotes /* : (arg0 : [string, DivideState]) => Option<DivideState> */(tuple) /* : Option<DivideState> */.or /* : unknown */(() => this.foldDoubleQuotes /* : (arg0 : [string, DivideState]) => Option<DivideState> */(tuple) /* : Option<DivideState> */) /* : unknown */.orElseGet /* : unknown */(() => folder(tuple[1], tuple[0]) /* : DivideState */) /* : unknown */;
    }
    foldDoubleQuotes(tuple) {
        if (tuple[0] === "\"") {
            let current = tuple[1].append /* : unknown */(tuple[0]) /* : unknown */;
            while (true) {
                let maybePopped = current.popAndAppendToTuple /* : unknown */() /* : unknown */;
                if (maybePopped.isEmpty /* : unknown */() /* : unknown */) {
                    /* break */ ;
                }
                let popped = maybePopped.orElse /* : unknown */(null) /* : unknown */;
                current = popped.right /* : unknown */() /* : unknown */;
                if (popped.left /* : unknown */() /* : unknown */ === "\\") {
                    current = current.popAndAppendToOption /* : unknown */() /* : unknown */.orElse /* : unknown */(current) /* : unknown */;
                }
                if (popped.left /* : unknown */() /* : unknown */ === "\"") {
                    /* break */ ;
                }
            }
            return new Some(current) /* : Some */;
        }
        return new None() /* : None */;
    }
    foldSingleQuotes(tuple) {
        if ( /* tuple.left() != '\'' */) {
            return new None() /* : None */;
        }
        let appended = tuple[1].append /* : unknown */(tuple[0]) /* : unknown */;
        return appended.popAndAppendToTuple /* : unknown */() /* : unknown */.map /* : unknown */(this.foldEscaped /* : unknown */) /* : unknown */.flatMap /* : unknown */(DivideState.popAndAppendToOption /* : unknown */) /* : unknown */;
    }
    foldEscaped(escaped) {
        if (escaped[0] === "\\") {
            return escaped[1].popAndAppendToOption /* : unknown */() /* : unknown */.orElse /* : unknown */(escaped[1]) /* : unknown */;
        }
        return escaped[1];
    }
    foldStatementChar(state, c) {
        let append = state.append /* : unknown */(c) /* : unknown */;
        if (c === ";" && append.isLevel /* : unknown */() /* : unknown */) {
            return append.advance /* : unknown */() /* : unknown */;
        }
        if (c === "}" && append.isShallow /* : unknown */() /* : unknown */) {
            return append.advance /* : unknown */() /* : unknown */.exit /* : unknown */() /* : unknown */;
        }
        if (c === "{" || c === "(") {
            return append.enter /* : unknown */() /* : unknown */;
        }
        if (c === "}" || c === ")") {
            return append.exit /* : unknown */() /* : unknown */;
        }
        return append;
    }
    compileRootSegment(state, input) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (stripped.startsWith /* : unknown */("package ") /* : unknown */ || stripped.startsWith /* : unknown */("import ") /* : unknown */) {
            return [state, ""];
        }
        return this.parseClass /* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(stripped, state) /* : Option<[CompileState, IncompleteClassSegment]> */.flatMap /* : unknown */((tuple) => this.completeClassSegment /* : (arg0 : CompileState, arg1 : IncompleteClassSegment) => Option<[CompileState, ClassSegment]> */(tuple.left /* : unknown */() /* : unknown */, tuple.right /* : unknown */() /* : unknown */) /* : Option<[CompileState, ClassSegment]> */) /* : unknown */.map /* : unknown */((tuple0) => [tuple0.left /* : unknown */() /* : unknown */, tuple0.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */]) /* : unknown */.orElseGet /* : unknown */(() => [state, generatePlaceholder(stripped) /* : string */]) /* : unknown */;
    }
    parseClass(stripped, state) {
        return this.parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(stripped, "class ", "class ", state) /* : Option<[CompileState, IncompleteClassSegment]> */;
    }
    parseStructure(stripped, sourceInfix, targetInfix, state) {
        return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(stripped, sourceInfix, (beforeInfix, right) => {
            return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(right, "{", (beforeContent, withEnd) => {
                return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withEnd.strip /* : unknown */() /* : unknown */, "}", (content1) => {
                    return this.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeInfix.strip /* : unknown */() /* : unknown */, "\n", (annotationsString, s2) => {
                        let annotations = this.parseAnnotations /* : (arg0 : string) => List<string> */(annotationsString) /* : List<string> */;
                        return this.parseStructureWithMaybePermits /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix, state, s2, beforeContent, content1, annotations) /* : Option<[CompileState, IncompleteClassSegment]> */;
                    }) /* : Option<T> */.or /* : unknown */(() => {
                        return this.parseStructureWithMaybePermits /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty /* : unknown */() /* : unknown */) /* : Option<[CompileState, IncompleteClassSegment]> */;
                    }) /* : unknown */;
                }) /* : Option<T> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    parseAnnotations(annotationsString) {
        return this.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(annotationsString.strip /* : unknown */() /* : unknown */, (state1, c) => this.foldByDelimiter /* : (arg0 : DivideState, arg1 : string, arg2 : string) => DivideState */(state1, c, "\n") /* : DivideState */) /* : List<string> */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(String.strip /* : unknown */) /* : unknown */.filter /* : unknown */((value) => !value.isEmpty /* : unknown */() /* : unknown */) /* : unknown */.map /* : unknown */((value) => value.substring /* : unknown */(1) /* : unknown */) /* : unknown */.map /* : unknown */(String.strip /* : unknown */) /* : unknown */.filter /* : unknown */((value) => !value.isEmpty /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    foldByDelimiter(state1, c, delimiter) {
        if (c === delimiter) {
            return state1.advance /* : unknown */() /* : unknown */;
        }
        return state1.append /* : unknown */(c) /* : unknown */;
    }
    parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, annotations) {
        return this.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent, " permits ", (s, s2) => {
            let variants = this.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(s2, this.foldValueChar /* : unknown */) /* : List<string> */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(String.strip /* : unknown */) /* : unknown */.filter /* : unknown */((value) => !value.isEmpty /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
            return this.parseStructureWithMaybeImplements /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix, state, beforeInfix, s, content1, variants, annotations) /* : Option<[CompileState, IncompleteClassSegment]> */;
        }) /* : Option<T> */.or /* : unknown */(() => this.parseStructureWithMaybeImplements /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty /* : unknown */() /* : unknown */, annotations) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : unknown */;
    }
    parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations) {
        return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent, " implements ", (s, s2) => {
            let stringList = this.parseTypeRefs /* : (arg0 : string) => List<TypeRef> */(s2) /* : List<TypeRef> */;
            return this.parseStructureWithMaybeExtends /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix, state, beforeInfix, s, content1, variants, annotations, stringList) /* : Option<[CompileState, IncompleteClassSegment]> */;
        }) /* : Option<T> */.or /* : unknown */(() => this.parseStructureWithMaybeExtends /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, Lists.empty /* : unknown */() /* : unknown */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : unknown */;
    }
    parseTypeRefs(s2) {
        return this.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(s2, this.foldValueChar /* : unknown */) /* : List<string> */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(String.strip /* : unknown */) /* : unknown */.filter /* : unknown */((value) => !value.isEmpty /* : unknown */() /* : unknown */) /* : unknown */.map /* : unknown */(TypeRef.new /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, interfaces) {
        return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent, " extends ", (s, s2) => this.parseStructureWithMaybeParams /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<TypeRef>, arg8 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix, state, beforeInfix, s, content1, variants, annotations, this.parseTypeRefs /* : (arg0 : string) => List<TypeRef> */(s2) /* : List<TypeRef> */, interfaces) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<T> */.or /* : unknown */(() => this.parseStructureWithMaybeParams /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<TypeRef>, arg8 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, Lists.empty /* : unknown */() /* : unknown */, interfaces) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : unknown */;
    }
    parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, superTypes, interfaces) {
        return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeContent.strip /* : unknown */() /* : unknown */, ")", (s) => {
            return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(s, "(", (s1, s2) => {
                let parsed = this.parseParameters /* : (arg0 : CompileState, arg1 : string) => [CompileState, List<Parameter>] */(state, s2) /* : [CompileState, List<Parameter>] */;
                return this.parseStructureWithMaybeTypeParams /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<Parameter>, arg6 : List<string>, arg7 : List<string>, arg8 : List<TypeRef>, arg9 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix, parsed[0], beforeInfix, s1, content1, parsed[1], variants, annotations, interfaces, superTypes) /* : Option<[CompileState, IncompleteClassSegment]> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */.or /* : unknown */(() => {
            return this.parseStructureWithMaybeTypeParams /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<Parameter>, arg6 : List<string>, arg7 : List<string>, arg8 : List<TypeRef>, arg9 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty /* : unknown */() /* : unknown */, variants, annotations, interfaces, superTypes) /* : Option<[CompileState, IncompleteClassSegment]> */;
        }) /* : unknown */;
    }
    parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, params, variants, annotations, interfaces, maybeSuperType) {
        return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent, "<", (name, withTypeParams) => {
            return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
                let readonly, mapper = (state1, s) => [state1, s.strip /* : unknown */() /* : unknown */];
                let typeParams = this.parseValuesOrEmpty /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state, typeParamsString, (state1, s) => new Some(mapper(state1, s) /* : [CompileState, string] */) /* : Some */) /* : [CompileState, List<T>] */;
                return this.assembleStructure /* : (arg0 : CompileState, arg1 : string, arg2 : List<string>, arg3 : string, arg4 : string, arg5 : string, arg6 : List<string>, arg7 : string, arg8 : List<Parameter>, arg9 : List<string>, arg10 : List<TypeRef>, arg11 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(typeParams[0], targetInfix, annotations, beforeInfix, name, content1, typeParams[1], afterTypeParams, params, variants, interfaces, maybeSuperType) /* : Option<[CompileState, IncompleteClassSegment]> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */.or /* : unknown */(() => {
            return this.assembleStructure /* : (arg0 : CompileState, arg1 : string, arg2 : List<string>, arg3 : string, arg4 : string, arg5 : string, arg6 : List<string>, arg7 : string, arg8 : List<Parameter>, arg9 : List<string>, arg10 : List<TypeRef>, arg11 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(state, targetInfix, annotations, beforeInfix, beforeContent, content1, Lists.empty /* : unknown */() /* : unknown */, "", params, variants, interfaces, maybeSuperType) /* : Option<[CompileState, IncompleteClassSegment]> */;
        }) /* : unknown */;
    }
    assembleStructure(state, targetInfix, annotations, beforeInfix, rawName, content, typeParams, after, rawParameters, variants, interfaces, maybeSuperType) {
        let name = rawName.strip /* : unknown */() /* : unknown */;
        if (!isSymbol(name) /* : unknown */) {
            return new None() /* : None */;
        }
        if (annotations.contains /* : unknown */("Actual") /* : unknown */) {
            return new Some([state, new Whitespace() /* : Whitespace */]) /* : Some */;
        }
        let segmentsTuple = this.parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state.pushStructName /* : unknown */([name, variants]) /* : unknown */.withTypeParams /* : unknown */(typeParams) /* : unknown */, content, (state0, input) => this.parseClassSegment /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, IncompleteClassSegment] */(state0, input, 1) /* : [CompileState, IncompleteClassSegment] */) /* : [CompileState, List<T>] */;
        let segmentsState = segmentsTuple[0];
        let segments = segmentsTuple[1];
        let parameters = this.retainDefinitions /* : (arg0 : List<Parameter>) => List<Definition> */(rawParameters) /* : List<Definition> */;
        let prototype = new StructurePrototype(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants, interfaces, maybeSuperType) /* : StructurePrototype */;
        return new Some([segmentsState.addType /* : unknown */(prototype.createObjectType /* : unknown */() /* : unknown */) /* : unknown */, prototype]) /* : Some */;
    }
    completeStructure(state, prototype) {
        let thisType = prototype.createObjectType /* : unknown */() /* : unknown */;
        let withThis = state.enterDefinitions /* : unknown */() /* : unknown */.define /* : unknown */(Definition.createSimpleDefinition /* : unknown */("this", thisType) /* : unknown */) /* : unknown */;
        return this.resolveTypeRefs /* : (arg0 : CompileState, arg1 : List<TypeRef>) => Option<[CompileState, List<Type>]> */(withThis, prototype.interfaces /* : unknown */) /* : Option<[CompileState, List<Type>]> */.flatMap /* : unknown */((interfacesTuple) => {
            return this.resolveTypeRefs /* : (arg0 : CompileState, arg1 : List<TypeRef>) => Option<[CompileState, List<Type>]> */(interfacesTuple.left /* : unknown */() /* : unknown */, prototype.superTypes /* : unknown */) /* : Option<[CompileState, List<Type>]> */.flatMap /* : unknown */((superTypesTuple) => {
                let interfaces = interfacesTuple.right /* : unknown */() /* : unknown */;
                let superTypes = superTypesTuple.right /* : unknown */() /* : unknown */;
                let bases = this.resolveBaseTypes /* : (arg0 : List<Type>) => List<ObjectRefType> */(interfaces) /* : List<ObjectRefType> */.addAllLast /* : unknown */(this.resolveBaseTypes /* : (arg0 : List<Type>) => List<ObjectRefType> */(superTypes) /* : List<ObjectRefType> */) /* : unknown */;
                let left = superTypesTuple.left /* : unknown */() /* : unknown */;
                let variantsSuper = this.findBaseNamesOfVariants /* : (arg0 : CompileState, arg1 : List<ObjectRefType>, arg2 : string) => List<string> */(left, bases, prototype.name /* : unknown */) /* : List<string> */;
                return this.mapUsingState /* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(left, prototype.segments /* : unknown */() /* : unknown */, this.createClassSegmentRule /* : () => (arg0 : CompileState, arg1 : [number, IncompleteClassSegment]) => Option<[CompileState, ClassSegment]> */() /* : (arg0 : CompileState, arg1 : [number, IncompleteClassSegment]) => Option<[CompileState, ClassSegment]> */) /* : Option<[CompileState, List<R>]> */.map /* : unknown */(this.completeStructureWithStatements /* : (arg0 : StructurePrototype, arg1 : List<string>, arg2 : StructureType, arg3 : List<Type>) => (arg0 : [CompileState, List<ClassSegment>]) => [CompileState, ClassSegment] */(prototype, variantsSuper, thisType, interfaces) /* : (arg0 : [CompileState, List<ClassSegment>]) => [CompileState, ClassSegment] */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }
    completeStructureWithStatements(prototype, variantsSuper, thisType, interfaces) {
        /* return oldStatementsTuple -> */ {
            let exited = oldStatementsTuple.left /* : unknown */() /* : unknown */.exitDefinitions /* : unknown */() /* : unknown */;
            let oldStatements = oldStatementsTuple.right /* : unknown */() /* : unknown */;
            let withEnumCategoriesDefinedTuple = this.defineEnumCategories /* : (arg0 : CompileState, arg1 : List<ClassSegment>, arg2 : string, arg3 : List<string>, arg4 : string) => [CompileState, List<ClassSegment>] */(exited, oldStatements, prototype.name /* : unknown */, prototype.variants /* : unknown */, prototype.generateToEnum /* : unknown */() /* : unknown */) /* : [CompileState, List<ClassSegment>] */;
            let withEnumCategoriesDefinedState = withEnumCategoriesDefinedTuple[0];
            let withEnumCategoriesDefined = withEnumCategoriesDefinedTuple[1];
            let withEnumCategoriesImplemented = this.implementEnumCategories /* : (arg0 : string, arg1 : List<string>, arg2 : List<ClassSegment>) => List<ClassSegment> */(prototype.name /* : unknown */, variantsSuper, withEnumCategoriesDefined) /* : List<ClassSegment> */;
            let withEnumValues = this.implementEnumValues /* : (arg0 : List<ClassSegment>, arg1 : StructureType) => List<ClassSegment> */(withEnumCategoriesImplemented, thisType) /* : List<ClassSegment> */;
            let withConstructor = this.defineConstructor /* : (arg0 : List<ClassSegment>, arg1 : List<Definition>) => List<ClassSegment> */(withEnumValues, prototype.parameters /* : unknown */() /* : unknown */) /* : List<ClassSegment> */;
            let generatedSegments = this.joinSegments /* : (arg0 : List<ClassSegment>) => string */(withConstructor) /* : string */;
            let joinedTypeParams = prototype.joinTypeParams /* : unknown */() /* : unknown */;
            let interfacesJoined = this.joinInterfaces /* : (arg0 : List<Type>) => string */(interfaces) /* : string */;
            let generatedSuperType = this.joinSuperTypes /* : (arg0 : CompileState, arg1 : StructurePrototype) => string */(withEnumCategoriesDefinedState, prototype) /* : string */;
            let generated = generatePlaceholder(prototype.beforeInfix /* : unknown */() /* : unknown */.strip /* : unknown */() /* : unknown */) /* : string */ + prototype.targetInfix /* : unknown */() /* : unknown */ + prototype.name /* : unknown */() /* : unknown */ + joinedTypeParams + generatePlaceholder(prototype.after /* : unknown */() /* : unknown */) /* : string */ + generatedSuperType + interfacesJoined + " {" + generatedSegments + "\n}\n";
            let compileState = withEnumCategoriesDefinedState.popStructName /* : unknown */() /* : unknown */;
            let definedState = compileState.addStructure /* : unknown */(generated) /* : unknown */;
            return [definedState, new Whitespace() /* : Whitespace */];
        }
        /*  */ ;
    }
    createClassSegmentRule() {
        return (state1, entry) => this.completeClassSegment /* : (arg0 : CompileState, arg1 : IncompleteClassSegment) => Option<[CompileState, ClassSegment]> */(state1, entry.right /* : unknown */() /* : unknown */) /* : Option<[CompileState, ClassSegment]> */;
    }
    resolveTypeRefs(state, refs) {
        return this.mapUsingState /* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(state, refs, (state2, tuple) => this.parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state2, tuple.right /* : unknown */() /* : unknown */.value /* : unknown */) /* : Option<[CompileState, Type]> */) /* : Option<[CompileState, List<R>]> */;
    }
    joinSuperTypes(state, prototype) {
        return prototype.superTypes /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((value) => state.resolveType /* : unknown */(value.value /* : unknown */) /* : unknown */) /* : unknown */.flatMap /* : unknown */(Queries.fromOption /* : unknown */) /* : unknown */.map /* : unknown */(Type.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((generated) => " extends " + generated) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    implementEnumValues(withConstructor, thisType) {
        return withConstructor.iterate /* : unknown */() /* : unknown */.flatMap /* : unknown */((segment) => this.flattenEnumValues /* : (arg0 : ClassSegment, arg1 : StructureType) => Query<ClassSegment> */(segment, thisType) /* : Query<ClassSegment> */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    defineEnumCategories(state, segments, name, variants, enumGenerated) {
        if (variants.isEmpty /* : unknown */() /* : unknown */) {
            return [state, segments];
        }
        let enumState = state.addStructure /* : unknown */(enumGenerated) /* : unknown */;
        let enumType = new ObjectRefType(name + "Variant") /* : ObjectRefType */;
        let enumDefinition = this.createVariantDefinition /* : (arg0 : ObjectRefType) => Definition */(enumType) /* : Definition */;
        return [enumState, segments.addFirst /* : unknown */(new Statement(1, enumDefinition) /* : Statement */) /* : unknown */];
    }
    implementEnumCategories(name, variantsBases, oldStatements) {
        return variantsBases.iterate /* : unknown */() /* : unknown */.fold /* : unknown */(oldStatements, (classSegmentList, superType) => {
            let variantTypeName = superType + "Variant";
            let variantType = new ObjectRefType(variantTypeName) /* : ObjectRefType */;
            let definition = this.createVariantDefinition /* : (arg0 : ObjectRefType) => Definition */(variantType) /* : Definition */;
            let source = new SymbolValue(variantTypeName + "." + name) /* : SymbolValue */;
            let initialization = new FieldInitialization(definition, source) /* : FieldInitialization */;
            return classSegmentList.addFirst /* : unknown */(new Statement(1, initialization) /* : Statement */) /* : unknown */;
        }) /* : unknown */;
    }
    findBaseNamesOfVariants(state, refs, name) {
        return refs.iterate /* : unknown */() /* : unknown */.map /* : unknown */((base) => state.findStructure /* : unknown */(base.name /* : unknown */) /* : unknown */) /* : unknown */.flatMap /* : unknown */(Queries.fromOption /* : unknown */) /* : unknown */.filter /* : unknown */((type) => type.hasVariant /* : unknown */(name) /* : unknown */) /* : unknown */.map /* : unknown */(StructureType.name /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    resolveBaseTypes(interfaces) {
        return interfaces.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Main.retainObjectRefType /* : unknown */) /* : unknown */.flatMap /* : unknown */(Queries.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    joinSegments(segmentsWithMaybeConstructor) {
        return segmentsWithMaybeConstructor.iterate /* : unknown */() /* : unknown */.map /* : unknown */(ClassSegment.generate /* : unknown */) /* : unknown */.collect /* : unknown */(Joiner.empty /* : unknown */() /* : unknown */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    joinInterfaces(interfaces) {
        return interfaces.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Type.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => " implements " + inner) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    flattenEnumValues(segment, thisType) {
        if (segment._Variant /* : unknown */ === Variant.) {
            let enumValues = segment;
            return enumValues.values /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((enumValue) => {
                let definition = new Definition(Lists.empty /* : unknown */() /* : unknown */, Lists.of /* : unknown */("static") /* : unknown */, enumValue.value /* : unknown */, thisType, Lists.empty /* : unknown */() /* : unknown */) /* : Definition */;
                return new Statement(1, new FieldInitialization(definition, new Invokable(new ConstructionCaller(thisType) /* : ConstructionCaller */, enumValue.values /* : unknown */, thisType) /* : Invokable */) /* : FieldInitialization */) /* : Statement */;
            }) /* : unknown */;
        }
        return Queries.from /* : unknown */(segment) /* : unknown */;
    }
    createVariantDefinition(type) {
        return Definition.createSimpleDefinition /* : unknown */("_" + type.name /* : unknown */, type) /* : unknown */;
    }
    defineConstructor(segments, parameters) {
        if (parameters.isEmpty /* : unknown */() /* : unknown */) {
            return segments;
        }
        let definitions = parameters.iterate /* : unknown */() /* : unknown */. /* : unknown */ < /* ClassSegment>map */ ((definition) => new Statement(1, definition) /* : Statement */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        let collect = /* parameters.iterate()
                .map(definition  */ -(destination,
        /*  new SymbolValue(definition.findName()));
    } */) /* : unknown */. /* : unknown */ < /* FunctionSegment>map */ ((assignment) => new Statement(2, assignment) /* : Statement */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        let func = new FunctionNode(1, new ConstructorHeader() /* : ConstructorHeader */, parameters, new Some(collect) /* : Some */) /* : FunctionNode */;
        return segments.addFirst /* : unknown */(func) /* : unknown */.addAllFirst /* : unknown */(definitions) /* : unknown */;
    }
    completeClassSegment(state1, segment) {
        /* return switch (segment) */ {
            /* case IncompleteClassSegmentWrapper wrapper -> new Some<>(new Tuple2Impl<>(state1, wrapper.segment)) */ ;
            /* case MethodPrototype methodPrototype -> this.completeMethod(state1, methodPrototype) */ ;
            /* case Whitespace whitespace -> new Some<>(new Tuple2Impl<>(state1, whitespace)) */ ;
            /* case Placeholder placeholder -> new Some<>(new Tuple2Impl<>(state1, placeholder)) */ ;
            /* case ClassDefinition classDefinition -> this.completeDefinition(state1, classDefinition) */ ;
            /* case ClassInitialization classInitialization -> this.completeInitialization(state1, classInitialization) */ ;
            /* case StructurePrototype structurePrototype -> this.completeStructure(state1, structurePrototype) */ ;
            /* case EnumValues enumValues -> new Some<>(new Tuple2Impl<>(state1, enumValues)) */ ;
        }
        /*  */ ;
    }
    completeInitialization(state1, classInitialization) {
        let definition = classInitialization.definition /* : unknown */;
        let statement = new Statement(classInitialization.depth /* : unknown */, new FieldInitialization(definition, classInitialization.value /* : unknown */) /* : FieldInitialization */) /* : Statement */;
        return new Some([state1, statement]) /* : Some */;
    }
    completeDefinition(state1, classDefinition) {
        let definition = classDefinition.definition /* : unknown */;
        let statement = new Statement(classDefinition.depth /* : unknown */, definition) /* : Statement */;
        return new Some([state1, statement]) /* : Some */;
    }
    retainDefinition(parameter) {
        if (parameter._Variant /* : unknown */ === Variant.) {
            let definition = parameter;
            return new Some(definition) /* : Some */;
        }
        return new None() /* : None */;
    }
    prefix(input, prefix, mapper) {
        if (!input.startsWith /* : unknown */(prefix) /* : unknown */) {
            return new None() /* : None */;
        }
        let slice = input.substring /* : unknown */(Strings.length /* : unknown */(prefix) /* : unknown */) /* : unknown */;
        return mapper(slice) /* : Option<T> */;
    }
    suffix(input, suffix, mapper) {
        if (!input.endsWith /* : unknown */(suffix) /* : unknown */) {
            return new None() /* : None */;
        }
        let slice = input.substring /* : unknown */(0, Strings.length /* : unknown */(input) /* : unknown */ - Strings.length /* : unknown */(suffix) /* : unknown */) /* : unknown */;
        return mapper(slice) /* : Option<T> */;
    }
    parseClassSegment(state, input, depth) {
        return this. /* : unknown */ < /* Whitespace, IncompleteClassSegment>typed */ (() => parseWhitespace(input, state) /* : Option<[CompileState, Whitespace]> */) /* : unknown */.or /* : unknown */(() => this.typed /* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this.parseClass /* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input, state) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<[CompileState, S]> */) /* : unknown */.or /* : unknown */(() => this.typed /* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this.parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input, "interface ", "interface ", state) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<[CompileState, S]> */) /* : unknown */.or /* : unknown */(() => this.typed /* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this.parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input, "record ", "class ", state) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<[CompileState, S]> */) /* : unknown */.or /* : unknown */(() => this.typed /* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this.parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input, "enum ", "class ", state) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<[CompileState, S]> */) /* : unknown */.or /* : unknown */(() => this.typed /* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this.parseField /* : (arg0 : string, arg1 : number, arg2 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input, depth, state) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<[CompileState, S]> */) /* : unknown */.or /* : unknown */(() => this.parseMethod /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, IncompleteClassSegment]> */(state, input, depth) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : unknown */.or /* : unknown */(() => this.parseEnumValues /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, IncompleteClassSegment]> */(state, input) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : unknown */.orElseGet /* : unknown */(() => [state, new Placeholder(input) /* : Placeholder */]) /* : unknown */;
    }
    parseEnumValues(state, input) {
        return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input.strip /* : unknown */() /* : unknown */, ";", (withoutEnd) => {
            return this.parseValues /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state, withoutEnd, (state2, enumValue) => {
                return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(enumValue.strip /* : unknown */() /* : unknown */, ")", (withoutValueEnd) => {
                    return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutValueEnd, "(", (s4, s2) => {
                        return this.parseValues /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state2, s2, (state1, s1) => new Some(Main.this /* : unknown */.parseArgument /* : unknown */(state1, s1, 1) /* : unknown */) /* : Some */) /* : Option<[CompileState, List<T>]> */.map /* : unknown */((arguments) => {
                            return [arguments.left /* : unknown */() /* : unknown */, new EnumValue(s4, Main.this /* : unknown */.retainValues /* : unknown */(arguments.right /* : unknown */() /* : unknown */) /* : unknown */) /* : EnumValue */];
                        }) /* : unknown */;
                    }) /* : Option<T> */;
                }) /* : Option<T> */;
            }) /* : Option<[CompileState, List<T>]> */.map /* : unknown */((tuple) => {
                return [tuple.left /* : unknown */() /* : unknown */, new EnumValues(tuple.right /* : unknown */() /* : unknown */) /* : EnumValues */];
            }) /* : unknown */;
        }) /* : Option<T> */;
    }
    typed(action) {
        return action() /* : Option<[CompileState, T]> */.map /* : unknown */((tuple) => [tuple.left /* : unknown */() /* : unknown */, tuple.right /* : unknown */() /* : unknown */]) /* : unknown */;
    }
    parseMethod(state, input, depth) {
        return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input, "(", (definitionString, withParams) => {
            return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withParams, ")", (parametersString, rawContent) => {
                return this.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state, definitionString) /* : Option<[CompileState, Definition]> */. /* : unknown */ < Tuple2 < /* CompileState, Header>>map */ ((tuple) => [tuple.left /* : unknown */() /* : unknown */, tuple.right /* : unknown */() /* : unknown */]) /* : unknown */.or /* : unknown */(() => this.parseConstructor /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Header]> */(state, definitionString) /* : Option<[CompileState, Header]> */) /* : unknown */.flatMap /* : unknown */((definitionTuple) => this.assembleMethod /* : (arg0 : number, arg1 : string, arg2 : string, arg3 : [CompileState, Header]) => Option<[CompileState, IncompleteClassSegment]> */(depth, parametersString, rawContent, definitionTuple) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : unknown */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    assembleMethod(depth, parametersString, rawContent, definitionTuple) {
        let definitionState = definitionTuple[0];
        let header = definitionTuple[1];
        let parametersTuple = this.parseParameters /* : (arg0 : CompileState, arg1 : string) => [CompileState, List<Parameter>] */(definitionState, parametersString) /* : [CompileState, List<Parameter>] */;
        let rawParameters = parametersTuple[1];
        let parameters = this.retainDefinitions /* : (arg0 : List<Parameter>) => List<Definition> */(rawParameters) /* : List<Definition> */;
        let prototype = new MethodPrototype(depth, header, parameters, rawContent.strip /* : unknown */() /* : unknown */) /* : MethodPrototype */;
        return new Some([parametersTuple[0].define /* : unknown */(prototype.createDefinition /* : unknown */() /* : unknown */) /* : unknown */, prototype]) /* : Some */;
    }
    completeMethod(state, prototype) {
        let definition = prototype.createDefinition /* : unknown */() /* : unknown */;
        let oldHeader = prototype.header /* : unknown */() /* : unknown */;
        /* Header newHeader */ ;
        if (oldHeader._UnknownVariant /* : unknown */ === UnknownVariant.) {
            let maybeDefinition = oldHeader;
            newHeader = maybeDefinition.removeAnnotations /* : unknown */() /* : unknown */;
        }
        else {
            newHeader = oldHeader;
        }
        if (prototype.content /* : unknown */() /* : unknown */ === ";" || definition.containsAnnotation /* : unknown */("Actual") /* : unknown */) {
            return new Some([state.define /* : unknown */(definition) /* : unknown */, new FunctionNode(prototype.depth /* : unknown */() /* : unknown */, newHeader, prototype.parameters /* : unknown */() /* : unknown */, new None() /* : None */) /* : FunctionNode */]) /* : Some */;
        }
        if (prototype.content /* : unknown */() /* : unknown */.startsWith /* : unknown */("{") /* : unknown */ && prototype.content /* : unknown */() /* : unknown */.endsWith /* : unknown */("}") /* : unknown */) {
            let substring = prototype.content /* : unknown */() /* : unknown */.substring /* : unknown */(1, Strings.length /* : unknown */(prototype.content /* : unknown */() /* : unknown */) /* : unknown */ - 1) /* : unknown */;
            let withDefined = state.enterDefinitions /* : unknown */() /* : unknown */.defineAll /* : unknown */(prototype.parameters /* : unknown */() /* : unknown */) /* : unknown */;
            let statementsTuple = this.parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(withDefined, substring, (state1, input1) => this.parseFunctionSegment /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1, input1, prototype.depth /* : unknown */() /* : unknown */ + 1) /* : [CompileState, FunctionSegment] */) /* : [CompileState, List<T>] */;
            let statements = statementsTuple[1];
            return new Some([statementsTuple[0].exitDefinitions /* : unknown */() /* : unknown */.define /* : unknown */(definition) /* : unknown */, new FunctionNode(prototype.depth /* : unknown */() /* : unknown */, newHeader, prototype.parameters /* : unknown */() /* : unknown */, new Some(statements) /* : Some */) /* : FunctionNode */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseConstructor(state, input) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (state.isCurrentStructName /* : unknown */(stripped) /* : unknown */) {
            return new Some([state, new ConstructorHeader() /* : ConstructorHeader */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    retainDefinitions(right) {
        return right.iterate /* : unknown */() /* : unknown */.map /* : unknown */(this.retainDefinition /* : unknown */) /* : unknown */.flatMap /* : unknown */(Queries.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    parseParameters(state, params) {
        return this.parseValuesOrEmpty /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state, params, (state1, s) => new Some(this.parseParameter /* : (arg0 : CompileState, arg1 : string) => [CompileState, Parameter] */(state1, s) /* : [CompileState, Parameter] */) /* : Some */) /* : [CompileState, List<T>] */;
    }
    parseFunctionSegments(state, input, depth) {
        return this.parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state, input, (state1, input1) => this.parseFunctionSegment /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1, input1, depth + 1) /* : [CompileState, FunctionSegment] */) /* : [CompileState, List<T>] */;
    }
    parseFunctionSegment(state, input, depth) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (stripped.isEmpty /* : unknown */() /* : unknown */) {
            return [state, new Whitespace() /* : Whitespace */];
        }
        return this.parseFunctionStatement /* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, FunctionSegment]> */(state, depth, stripped) /* : Option<[CompileState, FunctionSegment]> */.or /* : unknown */(() => this.parseBlock /* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, FunctionSegment]> */(state, depth, stripped) /* : Option<[CompileState, FunctionSegment]> */) /* : unknown */.orElseGet /* : unknown */(() => [state, new Placeholder(stripped) /* : Placeholder */]) /* : unknown */;
    }
    parseFunctionStatement(state, depth, stripped) {
        return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped, ";", (s) => {
            let tuple = this.parseStatementValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, StatementValue] */(state, s, depth) /* : [CompileState, StatementValue] */;
            let left = tuple[0];
            let right = tuple[1];
            return new Some([left, new Statement(depth, right) /* : Statement */]) /* : Some */;
        }) /* : Option<T> */;
    }
    parseBlock(state, depth, stripped) {
        return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped, "}", (withoutEnd) => {
            return this.split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this.toFirst /* : (arg0 : string) => Option<[string, string]> */(withoutEnd) /* : Option<[string, string]> */, (beforeContent, content) => {
                return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeContent, "{", (headerString) => {
                    let headerTuple = this.parseBlockHeader /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, BlockHeader] */(state, headerString, depth) /* : [CompileState, BlockHeader] */;
                    let headerState = headerTuple[0];
                    let header = headerTuple[1];
                    let statementsTuple = this.parseFunctionSegments /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, List<FunctionSegment>] */(headerState, content, depth) /* : [CompileState, List<FunctionSegment>] */;
                    let statementsState = statementsTuple[0];
                    let statements = statementsTuple[1].addAllFirst /* : unknown */(statementsState.functionSegments /* : unknown */) /* : unknown */;
                    return new Some([statementsState.clearFunctionSegments /* : unknown */() /* : unknown */, new Block(depth, header, statements) /* : Block */]) /* : Some */;
                }) /* : Option<T> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    toFirst(input) {
        let divisions = this.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input, this.foldBlockStart /* : unknown */) /* : List<string> */;
        return divisions.removeFirst /* : unknown */() /* : unknown */.map /* : unknown */((removed) => {
            let right = removed.left /* : unknown */() /* : unknown */;
            let left = removed.right /* : unknown */() /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner("") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
            return [right, left];
        }) /* : unknown */;
    }
    parseBlockHeader(state, input, depth) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        return this.parseConditional /* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : number) => Option<[CompileState, BlockHeader]> */(state, stripped, "if", depth) /* : Option<[CompileState, BlockHeader]> */.or /* : unknown */(() => this.parseConditional /* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : number) => Option<[CompileState, BlockHeader]> */(state, stripped, "while", depth) /* : Option<[CompileState, BlockHeader]> */) /* : unknown */.or /* : unknown */(() => this.parseElse /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, BlockHeader]> */(state, input) /* : Option<[CompileState, BlockHeader]> */) /* : unknown */.orElseGet /* : unknown */(() => [state, new Placeholder(stripped) /* : Placeholder */]) /* : unknown */;
    }
    parseElse(state, input) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (stripped === "else") {
            return new Some([state, new Else() /* : Else */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseConditional(state, input, prefix, depth) {
        return this.prefix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input, prefix, (withoutPrefix) => {
            return this.prefix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withoutPrefix.strip /* : unknown */() /* : unknown */, "(", (withoutValueStart) => {
                return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withoutValueStart, ")", (value) => {
                    let valueTuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state, value, depth) /* : [CompileState, Value] */;
                    let value1 = valueTuple[1];
                    return new Some([valueTuple[0], new Conditional(prefix, value1) /* : Conditional */]) /* : Some */;
                }) /* : Option<T> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    foldBlockStart(state, c) {
        let appended = state.append /* : unknown */(c) /* : unknown */;
        if (c === "{" && state.isLevel /* : unknown */() /* : unknown */) {
            return appended.advance /* : unknown */() /* : unknown */;
        }
        if (c === "{") {
            return appended.enter /* : unknown */() /* : unknown */;
        }
        if (c === "}") {
            return appended.exit /* : unknown */() /* : unknown */;
        }
        return appended;
    }
    parseStatementValue(state, input, depth) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (stripped.startsWith /* : unknown */("return ") /* : unknown */) {
            let value = stripped.substring /* : unknown */(Strings.length /* : unknown */("return ") /* : unknown */) /* : unknown */;
            let tuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state, value, depth) /* : [CompileState, Value] */;
            let value1 = tuple[1];
            return [tuple[0], new Return(value1) /* : Return */];
        }
        return this.parseAssignment /* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, StatementValue]> */(state, depth, stripped) /* : Option<[CompileState, StatementValue]> */.orElseGet /* : unknown */(() => {
            return [state, new Placeholder(stripped) /* : Placeholder */];
        }) /* : unknown */;
    }
    parseAssignment(state, depth, stripped) {
        return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(stripped, "=", (beforeEquals, valueString) => {
            let sourceTuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state, valueString, depth) /* : [CompileState, Value] */;
            let sourceState = sourceTuple[0];
            let source = sourceTuple[1];
            let destinationTuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(sourceState, beforeEquals, depth) /* : [CompileState, Value] */;
            let destinationState = destinationTuple[0];
            let destination = destinationTuple[1];
            return this.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(destinationState, beforeEquals) /* : Option<[CompileState, Definition]> */.flatMap /* : unknown */((definitionTuple) => this.parseInitialization /* : (arg0 : CompileState, arg1 : Definition, arg2 : Value) => Option<[CompileState, StatementValue]> */(definitionTuple.left /* : unknown */() /* : unknown */, definitionTuple.right /* : unknown */() /* : unknown */, source) /* : Option<[CompileState, StatementValue]> */) /* : unknown */.or /* : unknown */(() => new Some([destinationState, new Assignment(destination, source) /* : Assignment */]) /* : Some */) /* : unknown */;
        }) /* : Option<T> */;
    }
    parseInitialization(state, destination, source) {
        let definition = destination.mapType /* : unknown */((type) => {
            if (type === Primitive.Unknown /* : unknown */) {
                return this.resolveValue /* : (arg0 : CompileState, arg1 : Value) => Type */(state, source) /* : Type */;
            }
            else {
                return type;
            }
        }) /* : unknown */;
        return new Some([state.define /* : unknown */(definition) /* : unknown */, new Initialization(definition, source) /* : Initialization */]) /* : Some */;
    }
    resolveValue(state, value) {
        /* return switch (value) */ {
            /* case BooleanValue _, Not _ -> Primitive.Boolean */ ;
            /* case StringValue _ -> Primitive.String */ ;
            /* case Cast cast -> cast.type */ ;
            /* case DataAccess dataAccess -> this.resolveDataAccess(state, dataAccess) */ ;
            /* case IndexValue indexValue -> Primitive.Unknown */ ;
            /* case Invokable invokable -> this.resolveInvokable(state, invokable) */ ;
            /* case Lambda lambda -> Primitive.Unknown */ ;
            /* case Operation operation -> Primitive.Unknown */ ;
            /* case SymbolValue symbolValue -> state.resolveValue(symbolValue.value).orElse(Primitive.Unknown) */ ;
            /* case TupleNode tupleNode -> this.resolveTuple(state, tupleNode) */ ;
            /* case Placeholder _ -> Primitive.Unknown */ ;
        }
        /*  */ ;
    }
    resolveTuple(state, tupleNode) {
        return new TupleType(tupleNode.values /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((value) => this.resolveValue /* : (arg0 : CompileState, arg1 : Value) => Type */(state, value) /* : Type */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */) /* : TupleType */;
    }
    resolveInvokable(state, invokable) {
        let caller = invokable.caller /* : unknown */;
        /* return switch (caller) */ {
            /* case ConstructionCaller constructionCaller -> constructionCaller.type */ ;
            /* case Value value1 -> */ {
                let type = this.resolveValue /* : (arg0 : CompileState, arg1 : Value) => Type */(state, value1) /* : Type */;
                if (type._Variant /* : unknown */ === Variant.) {
                    let functionType = type;
                    /* yield functionType.returns */ ;
                }
                else {
                    /* yield Primitive.Unknown */ ;
                }
            }
        }
        /*  */ ;
    }
    resolveDataAccess(state, dataAccess) {
        let parentType = this.resolveValue /* : (arg0 : CompileState, arg1 : Value) => Type */(state, dataAccess.parent /* : unknown */) /* : Type */;
        if (parentType._Variant /* : unknown */ === Variant.) {
            let structureType = parentType;
            return structureType.find /* : unknown */(dataAccess.property /* : unknown */) /* : unknown */.orElse /* : unknown */(Primitive.Unknown /* : unknown */) /* : unknown */;
        }
        return Primitive.Unknown /* : unknown */;
    }
    parseValue(state, input, depth) {
        return this.parseBoolean /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state, input) /* : Option<[CompileState, Value]> */.or /* : unknown */(() => this.parseLambda /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state, input, depth) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseString /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state, input) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseDataAccess /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state, input, depth) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseSymbolValue /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state, input) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseInvokable /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state, input, depth) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseDigits /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state, input) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseInstanceOf /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state, input, depth) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state, input, depth, Operator.ADD /* : unknown */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state, input, depth, Operator.EQUALS /* : unknown */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state, input, depth, Operator.SUBTRACT /* : unknown */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state, input, depth, Operator.AND /* : unknown */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state, input, depth, Operator.OR /* : unknown */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state, input, depth) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state, input, depth) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseNot /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state, input, depth) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseMethodReference /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state, input, depth) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this.parseChar /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state, input) /* : Option<[CompileState, Value]> */) /* : unknown */.orElseGet /* : unknown */(() => [state, new Placeholder(input) /* : Placeholder */]) /* : unknown */;
    }
    parseChar(state, input) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (stripped.startsWith /* : unknown */("'") /* : unknown */ && stripped.endsWith /* : unknown */("'") /* : unknown */ && Strings.length /* : unknown */(stripped) /* : unknown */ >= 2) {
            return new Some([state, new StringValue(stripped.substring /* : unknown */(1, Strings.length /* : unknown */(stripped) /* : unknown */ - 1) /* : unknown */) /* : StringValue */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseBoolean(state, input) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (stripped === "false") {
            return new Some([state, BooleanValue.False /* : unknown */]) /* : Some */;
        }
        if (stripped === "true") {
            return new Some([state, BooleanValue.True /* : unknown */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseInstanceOf(state, input, depth) {
        return this.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input, "instanceof", (beforeKeyword, afterKeyword) => {
            let childTuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state, beforeKeyword, depth) /* : [CompileState, Value] */;
            return this.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(childTuple[0], afterKeyword) /* : Option<[CompileState, Definition]> */.map /* : unknown */(this.parseInstanceOfWithValue /* : (arg0 : number, arg1 : Value) => (arg0 : [CompileState, Definition]) => [CompileState, Value] */(depth, childTuple[1]) /* : (arg0 : [CompileState, Definition]) => [CompileState, Value] */) /* : unknown */;
        }) /* : Option<T> */;
    }
    parseInstanceOfWithValue(depth, child) {
        /* return definitionTuple -> */ {
            let definitionSTate = definitionTuple.left /* : unknown */() /* : unknown */;
            let definition = definitionTuple.right /* : unknown */() /* : unknown */;
            let type = this.resolveValue /* : (arg0 : CompileState, arg1 : Value) => Type */(definitionSTate, child) /* : Type */;
            let variant = new DataAccess(child, "_" + type.findName() + "Variant", Primitive.Unknown /* : unknown */) /* : DataAccess */;
            let generate = type.findName /* : unknown */() /* : unknown */;
            let temp = new SymbolValue(generate + "Variant." + definition.findType /* : unknown */() /* : unknown */.findName /* : unknown */() /* : unknown */) /* : SymbolValue */;
            let functionSegment = new Statement(depth + 1, new Initialization(definition, new Cast(child, definition.findType /* : unknown */() /* : unknown */) /* : Cast */) /* : Initialization */) /* : Statement */;
            return [definitionSTate.addFunctionSegment /* : unknown */(functionSegment) /* : unknown */.define /* : unknown */(definition) /* : unknown */, new Operation(variant, Operator.EQUALS /* : unknown */, temp) /* : Operation */];
        }
        /*  */ ;
    }
    parseMethodReference(state, input, depth) {
        return this.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input, "::", (s, s2) => {
            let tuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state, s, depth) /* : [CompileState, Value] */;
            return new Some([tuple[0], new DataAccess(tuple[1], s2, Primitive.Unknown /* : unknown */) /* : DataAccess */]) /* : Some */;
        }) /* : Option<T> */;
    }
    parseNot(state, input, depth) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (stripped.startsWith /* : unknown */("!") /* : unknown */) {
            let slice = stripped.substring /* : unknown */(1) /* : unknown */;
            let tuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state, slice, depth) /* : [CompileState, Value] */;
            let value = tuple[1];
            return new Some([tuple[0], new Not(value) /* : Not */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseLambda(state, input, depth) {
        return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input, "->", (beforeArrow, valueString) => {
            let strippedBeforeArrow = beforeArrow.strip /* : unknown */() /* : unknown */;
            if (isSymbol(strippedBeforeArrow) /* : boolean */) {
                let type = Primitive.Unknown /* : unknown */;
                if ( /* state.typeRegister instanceof Some */( /* var expectedType */) /* : unknown */) {
                    if (expectedType._UnknownVariant /* : unknown */ === UnknownVariant.) {
                        let functionType = expectedType;
                        type = functionType.arguments /* : unknown */.get /* : unknown */(0) /* : unknown */.orElse /* : unknown */(null) /* : unknown */;
                    }
                }
                return this.assembleLambda /* : (arg0 : CompileState, arg1 : List<Definition>, arg2 : string, arg3 : number) => Some<[CompileState, Value]> */(state, Lists.of /* : unknown */(Definition.createSimpleDefinition /* : unknown */(strippedBeforeArrow, type) /* : unknown */) /* : unknown */, valueString, depth) /* : Some<[CompileState, Value]> */;
            }
            if (strippedBeforeArrow.startsWith /* : unknown */("(") /* : unknown */ && strippedBeforeArrow.endsWith /* : unknown */(")") /* : unknown */) {
                let parameterNames = this.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(strippedBeforeArrow.substring /* : unknown */(1, Strings.length /* : unknown */(strippedBeforeArrow) /* : unknown */ - 1) /* : unknown */, this.foldValueChar /* : unknown */) /* : List<string> */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(String.strip /* : unknown */) /* : unknown */.filter /* : unknown */((value) => !value.isEmpty /* : unknown */() /* : unknown */) /* : unknown */.map /* : unknown */((name) => Definition.createSimpleDefinition /* : unknown */(name, Primitive.Unknown /* : unknown */) /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
                return this.assembleLambda /* : (arg0 : CompileState, arg1 : List<Definition>, arg2 : string, arg3 : number) => Some<[CompileState, Value]> */(state, parameterNames, valueString, depth) /* : Some<[CompileState, Value]> */;
            }
            return new None() /* : None */;
        }) /* : Option<T> */;
    }
    assembleLambda(state, definitions, valueString, depth) {
        let strippedValueString = valueString.strip /* : unknown */() /* : unknown */;
        /* Tuple2<CompileState, LambdaValue> value */ ;
        let state2 = state.defineAll /* : unknown */(definitions) /* : unknown */;
        if (strippedValueString.startsWith /* : unknown */("{") /* : unknown */ && strippedValueString.endsWith /* : unknown */("}") /* : unknown */) {
            let value1 = this.parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state2, strippedValueString.substring /* : unknown */(1, Strings.length /* : unknown */(strippedValueString) /* : unknown */ - 1) /* : unknown */, (state1, input1) => this.parseFunctionSegment /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1, input1, depth + 1) /* : [CompileState, FunctionSegment] */) /* : [CompileState, List<T>] */;
            let right = value1[1];
            value = [value1[0], new BlockLambdaValue(depth, right) /* : BlockLambdaValue */];
        }
        else {
            let value1 = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state2, strippedValueString, depth) /* : [CompileState, Value] */;
            value = [value1[0], value1[1]];
        }
        let right = value.right /* : unknown */() /* : unknown */;
        return new Some([value.left /* : unknown */() /* : unknown */, new Lambda(definitions, right) /* : Lambda */]) /* : Some */;
    }
    parseDigits(state, input) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (this.isNumber /* : (arg0 : string) => boolean */(stripped) /* : boolean */) {
            return new Some([state, new SymbolValue(stripped) /* : SymbolValue */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    isNumber(input) {
        /* String maybeTruncated */ ;
        if (input.startsWith /* : unknown */("-") /* : unknown */) {
            maybeTruncated = input.substring /* : unknown */(1) /* : unknown */;
        }
        else {
            maybeTruncated = input;
        }
        return this.areAllDigits /* : (arg0 : string) => boolean */(maybeTruncated) /* : boolean */;
    }
    areAllDigits(input) {
        /* for (var i = 0; i < Strings.length(input); i++) */ {
            let c = input.charAt /* : unknown */(i) /* : unknown */;
            if (Character.isDigit /* : unknown */(c) /* : unknown */) {
                /* continue */ ;
            }
            return false;
        }
        return true;
    }
    parseInvokable(state, input, depth) {
        return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input.strip /* : unknown */() /* : unknown */, ")", (withoutEnd) => {
            return this.split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this.toLast /* : (arg0 : string, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState) => Option<[string, string]> */(withoutEnd, "", this.foldInvocationStart /* : unknown */) /* : Option<[string, string]> */, (callerWithEnd, argumentsString) => {
                return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(callerWithEnd, "(", (callerString) => {
                    return this.assembleInvokable /* : (arg0 : CompileState, arg1 : number, arg2 : string, arg3 : string) => Some<[CompileState, Value]> */(state, depth, argumentsString, callerString.strip /* : unknown */() /* : unknown */) /* : Some<[CompileState, Value]> */;
                }) /* : Option<T> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    assembleInvokable(state, depth, argumentsString, callerString) {
        let callerTuple = this.invocationHeader /* : (arg0 : CompileState, arg1 : number, arg2 : string) => [CompileState, Caller] */(state, depth, callerString) /* : [CompileState, Caller] */;
        let oldCallerState = callerTuple[0];
        let oldCaller = callerTuple[1];
        let newCaller = this.modifyCaller /* : (arg0 : CompileState, arg1 : Caller) => Caller */(oldCallerState, oldCaller) /* : Caller */;
        let callerType = this.findCallerType /* : (arg0 : Caller, arg1 : CompileState) => FunctionType */(newCaller, oldCallerState) /* : FunctionType */;
        let argumentsTuple = this.parseValuesWithIndices /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(oldCallerState, argumentsString, (currentState, pair) => this.getTuple2Some /* : (arg0 : number, arg1 : CompileState, arg2 : [number, string], arg3 : FunctionType) => Some<[CompileState, [Argument, Type]]> */(depth, currentState, pair, callerType) /* : Some<[CompileState, [Argument, Type]]> */) /* : Option<[CompileState, List<T>]> */.orElseGet /* : unknown */(() => [oldCallerState, Lists.empty /* : unknown */() /* : unknown */]) /* : unknown */;
        let argumentsState = argumentsTuple.left /* : unknown */() /* : unknown */;
        let argumentsWithActualTypes = argumentsTuple.right /* : unknown */() /* : unknown */;
        let arguments = this.retainValues /* : (arg0 : List<Argument>) => List<Value> */(argumentsWithActualTypes.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2.left /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */) /* : List<Value> */;
        if (newCaller._Variant /* : unknown */ === Variant.) {
            if (constructionCaller.type /* : unknown */.findName /* : unknown */() /* : unknown */ === "Tuple2Impl") {
                let constructionCaller = newCaller;
                return new Some([argumentsState, new TupleNode(Lists.of /* : unknown */(arguments.get /* : unknown */(0) /* : unknown */.orElse /* : unknown */(null) /* : unknown */, arguments.get /* : unknown */(1) /* : unknown */.orElse /* : unknown */(null) /* : unknown */) /* : unknown */) /* : TupleNode */]) /* : Some */;
            }
        }
        if (newCaller._Variant /* : unknown */ === Variant.) {
            if (value._Variant /* : unknown */ === Variant.) {
                let parent = access.parent /* : unknown */;
                let property = access.property /* : unknown */;
                let parentType = this.resolveValue /* : (arg0 : CompileState, arg1 : Value) => Type */(argumentsState, parent) /* : Type */;
                if ( /* parentType instanceof TupleType */) {
                    if (property === "left") {
                        let value = newCaller;
                        let access = value;
                        return new Some([argumentsState, new IndexValue(parent, new SymbolValue("0") /* : SymbolValue */) /* : IndexValue */]) /* : Some */;
                    }
                    if (property === "right") {
                        return new Some([argumentsState, new IndexValue(parent, new SymbolValue("1") /* : SymbolValue */) /* : IndexValue */]) /* : Some */;
                    }
                }
                if (property === "equals") {
                    let first = arguments.get /* : unknown */(0) /* : unknown */.orElse /* : unknown */(null) /* : unknown */;
                    return new Some([argumentsState, new Operation(parent, Operator.EQUALS /* : unknown */, first) /* : Operation */]) /* : Some */;
                }
            }
        }
        let invokable = new Invokable(newCaller, arguments, callerType.returns /* : unknown */) /* : Invokable */;
        return new Some([argumentsState, invokable]) /* : Some */;
    }
    getTuple2Some(depth, currentState, pair, callerType) {
        let index = pair[0];
        let element = pair[1];
        let expectedType = callerType.arguments /* : unknown */.get /* : unknown */(index) /* : unknown */.orElse /* : unknown */(Primitive.Unknown /* : unknown */) /* : unknown */;
        let withExpected = currentState.withExpectedType /* : unknown */(expectedType) /* : unknown */;
        let valueTuple = this.parseArgument /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Argument] */(withExpected, element, depth) /* : [CompileState, Argument] */;
        let valueState = valueTuple[0];
        let value = valueTuple[1];
        let actualType = valueTuple[0].typeRegister /* : unknown */.orElse /* : unknown */(Primitive.Unknown /* : unknown */) /* : unknown */;
        return new Some([valueState, [value, actualType]]) /* : Some */;
    }
    retainValues(arguments) {
        return arguments.iterate /* : unknown */() /* : unknown */.map /* : unknown */(this.retainValue /* : unknown */) /* : unknown */.flatMap /* : unknown */(Queries.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    retainValue(argument) {
        if (argument._Variant /* : unknown */ === Variant.) {
            let value = argument;
            return new Some(value) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseArgument(state, element, depth) {
        if (element.isEmpty /* : unknown */() /* : unknown */) {
            return [state, new Whitespace() /* : Whitespace */];
        }
        let tuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state, element, depth) /* : [CompileState, Value] */;
        return [tuple[0], tuple[1]];
    }
    findCallerType(newCaller, state) {
        let callerType = new FunctionType(Lists.empty /* : unknown */() /* : unknown */, Primitive.Unknown /* : unknown */) /* : FunctionType */;
        /* switch (newCaller) */ {
            /* case ConstructionCaller constructionCaller -> */ {
                callerType = constructionCaller.toFunction /* : unknown */() /* : unknown */;
            }
            /* case Value value -> */ {
                let type = this.resolveValue /* : (arg0 : CompileState, arg1 : Value) => Type */(state, value) /* : Type */;
                if (type._Variant /* : unknown */ === Variant.) {
                    let functionType = type;
                    callerType = functionType;
                }
            }
        }
        return callerType;
    }
    modifyCaller(state, oldCaller) {
        if (oldCaller._Variant /* : unknown */ === Variant.) {
            let type = this.resolveValue /* : (arg0 : CompileState, arg1 : Value) => Type */(state, access.parent /* : unknown */) /* : Type */;
            if ( /* type instanceof FunctionType */) {
                let access = oldCaller;
                return access.parent /* : unknown */;
            }
        }
        return oldCaller;
    }
    invocationHeader(state, depth, callerString1) {
        if (callerString1.startsWith /* : unknown */("new ") /* : unknown */) {
            let input1 = callerString1.substring /* : unknown */(Strings.length /* : unknown */("new ") /* : unknown */) /* : unknown */;
            let map = this.parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state, input1) /* : Option<[CompileState, Type]> */.map /* : unknown */((type) => {
                let right = type.right /* : unknown */() /* : unknown */;
                return [type.left /* : unknown */() /* : unknown */, new ConstructionCaller(right) /* : ConstructionCaller */];
            }) /* : unknown */;
            if (map.isPresent /* : unknown */() /* : unknown */) {
                return map.orElse /* : unknown */(null) /* : unknown */;
            }
        }
        let tuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state, callerString1, depth) /* : [CompileState, Value] */;
        return [tuple[0], tuple[1]];
    }
    foldInvocationStart(state, c) {
        let appended = state.append /* : unknown */(c) /* : unknown */;
        if (c === "(") {
            let enter = appended.enter /* : unknown */() /* : unknown */;
            if (enter.isShallow /* : unknown */() /* : unknown */) {
                return enter.advance /* : unknown */() /* : unknown */;
            }
            return enter;
        }
        if (c === ")") {
            return appended.exit /* : unknown */() /* : unknown */;
        }
        return appended;
    }
    parseDataAccess(state, input, depth) {
        return this.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input.strip /* : unknown */() /* : unknown */, ".", (parentString, rawProperty) => {
            let property = rawProperty.strip /* : unknown */() /* : unknown */;
            if (!isSymbol(property) /* : unknown */) {
                return new None() /* : None */;
            }
            let tuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state, parentString, depth) /* : [CompileState, Value] */;
            let parent = tuple[1];
            let parentType = this.resolveValue /* : (arg0 : CompileState, arg1 : Value) => Type */(tuple[0], parent) /* : Type */;
            let type = Primitive.Unknown /* : unknown */;
            if (parentType._Variant /* : unknown */ === Variant.) {
                if ( /* structureType.find(property) instanceof Some */( /* var memberType */) /* : unknown */) {
                    let structureType = parentType;
                    type = memberType;
                }
            }
            return new Some([tuple[0], new DataAccess(parent, property, type) /* : DataAccess */]) /* : Some */;
        }) /* : Option<T> */;
    }
    parseString(state, input) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (stripped.startsWith /* : unknown */("\"") /* : unknown */ && stripped.endsWith /* : unknown */("\"") /* : unknown */) {
            return new Some([state, new StringValue(stripped.substring /* : unknown */(1, Strings.length /* : unknown */(stripped) /* : unknown */ - 1) /* : unknown */) /* : StringValue */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseSymbolValue(state, value) {
        let stripped = value.strip /* : unknown */() /* : unknown */;
        if (isSymbol(stripped) /* : boolean */) {
            return new Some([state, new SymbolValue(stripped) /* : SymbolValue */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseOperation(state, value, depth, operator) {
        return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(value, operator.sourceRepresentation /* : unknown */, (leftString, rightString) => {
            let leftTuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state, leftString, depth) /* : [CompileState, Value] */;
            let rightTuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(leftTuple[0], rightString, depth) /* : [CompileState, Value] */;
            let left = leftTuple[1];
            let right = rightTuple[1];
            return new Some([rightTuple[0], new Operation(left, operator, right) /* : Operation */]) /* : Some */;
        }) /* : Option<T> */;
    }
    parseValuesOrEmpty(state, input, mapper) {
        return this.parseValues /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state, input, mapper) /* : Option<[CompileState, List<T>]> */.orElseGet /* : unknown */(() => [state, Lists.empty /* : unknown */() /* : unknown */]) /* : unknown */;
    }
    parseValues(state, input, mapper) {
        return this.parseValuesWithIndices /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state, input, (state1, tuple) => mapper(state1, tuple.right /* : unknown */() /* : unknown */) /* : Option<[CompileState, T]> */) /* : Option<[CompileState, List<T>]> */;
    }
    parseValuesWithIndices(state, input, mapper) {
        return this.parseAllWithIndices /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state, input, this.foldValueChar /* : unknown */, mapper) /* : Option<[CompileState, List<T>]> */;
    }
    parseParameter(state, input) {
        if (Strings.isBlank /* : unknown */(input) /* : unknown */) {
            return [state, new Whitespace() /* : Whitespace */];
        }
        return this.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state, input) /* : Option<[CompileState, Definition]> */.map /* : unknown */((tuple) => [tuple.left /* : unknown */() /* : unknown */, tuple.right /* : unknown */() /* : unknown */]) /* : unknown */.orElseGet /* : unknown */(() => [state, new Placeholder(input) /* : Placeholder */]) /* : unknown */;
    }
    parseField(input, depth, state) {
        return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input.strip /* : unknown */() /* : unknown */, ";", (withoutEnd) => {
            return this.parseClassInitialization /* : (arg0 : number, arg1 : CompileState, arg2 : string) => Option<[CompileState, IncompleteClassSegment]> */(depth, state, withoutEnd) /* : Option<[CompileState, IncompleteClassSegment]> */.or /* : unknown */(() => {
                return this.parseClassDefinition /* : (arg0 : number, arg1 : CompileState, arg2 : string) => Option<[CompileState, IncompleteClassSegment]> */(depth, state, withoutEnd) /* : Option<[CompileState, IncompleteClassSegment]> */;
            }) /* : unknown */;
        }) /* : Option<T> */;
    }
    parseClassDefinition(depth, state, withoutEnd) {
        return this.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state, withoutEnd) /* : Option<[CompileState, Definition]> */.map /* : unknown */((result) => {
            return [result.left /* : unknown */() /* : unknown */, new ClassDefinition(depth, result.right /* : unknown */() /* : unknown */) /* : ClassDefinition */];
        }) /* : unknown */;
    }
    parseClassInitialization(depth, state, withoutEnd) {
        return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutEnd, "=", (s, s2) => {
            return this.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state, s) /* : Option<[CompileState, Definition]> */.map /* : unknown */((result) => {
                let valueTuple = this.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(result.left /* : unknown */() /* : unknown */, s2, depth) /* : [CompileState, Value] */;
                return [valueTuple[0], new ClassInitialization(depth, result.right /* : unknown */() /* : unknown */, valueTuple[1]) /* : ClassInitialization */];
            }) /* : unknown */;
        }) /* : Option<T> */;
    }
    parseDefinition(state, input) {
        return this.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input.strip /* : unknown */() /* : unknown */, " ", (beforeName, name) => {
            return this.split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this.toLast /* : (arg0 : string, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState) => Option<[string, string]> */(beforeName, " ", this.foldTypeSeparator /* : unknown */) /* : Option<[string, string]> */, (beforeType, type) => {
                return this.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeType, "\n", (s, s2) => {
                    let annotations = this.parseAnnotations /* : (arg0 : string) => List<string> */(s) /* : List<string> */;
                    return this.getOr /* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : string, arg4 : List<string>) => Option<[CompileState, Definition]> */(state, name, s2, type, annotations) /* : Option<[CompileState, Definition]> */;
                }) /* : Option<T> */.or /* : unknown */(() => {
                    return this.getOr /* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : string, arg4 : List<string>) => Option<[CompileState, Definition]> */(state, name, beforeType, type, Lists.empty /* : unknown */() /* : unknown */) /* : Option<[CompileState, Definition]> */;
                }) /* : unknown */;
            }) /* : Option<T> */.or /* : unknown */(() => this.assembleDefinition /* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(state, Lists.empty /* : unknown */() /* : unknown */, Lists.empty /* : unknown */() /* : unknown */, name, Lists.empty /* : unknown */() /* : unknown */, beforeName) /* : Option<[CompileState, Definition]> */) /* : unknown */;
        }) /* : Option<T> */;
    }
    getOr(state, name, beforeType, type, annotations) {
        return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeType.strip /* : unknown */() /* : unknown */, ">", (withoutTypeParamStart) => {
            return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
                let typeParams = this.parseValuesOrEmpty /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state, typeParamsString, (state1, s) => new Some([state1, s.strip /* : unknown */() /* : unknown */]) /* : Some */) /* : [CompileState, List<T>] */;
                return this.assembleDefinition /* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(typeParams[0], annotations, this.parseModifiers /* : (arg0 : string) => List<string> */(beforeTypeParams) /* : List<string> */, name, typeParams[1], type) /* : Option<[CompileState, Definition]> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */.or /* : unknown */(() => {
            return this.assembleDefinition /* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(state, annotations, this.parseModifiers /* : (arg0 : string) => List<string> */(beforeType) /* : List<string> */, name, Lists.empty /* : unknown */() /* : unknown */, type) /* : Option<[CompileState, Definition]> */;
        }) /* : unknown */;
    }
    parseModifiers(modifiers) {
        return this.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(modifiers.strip /* : unknown */() /* : unknown */, (state1, c) => this.foldByDelimiter /* : (arg0 : DivideState, arg1 : string, arg2 : string) => DivideState */(state1, c, " ") /* : DivideState */) /* : List<string> */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(String.strip /* : unknown */) /* : unknown */.filter /* : unknown */((value) => !value.isEmpty /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    toLast(input, separator, folder) {
        let divisions = this.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input, folder) /* : List<string> */;
        return divisions.removeLast /* : unknown */() /* : unknown */.map /* : unknown */((removed) => {
            let left = removed.left /* : unknown */() /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner(separator) /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
            let right = removed.right /* : unknown */() /* : unknown */;
            return [left, right];
        }) /* : unknown */;
    }
    foldTypeSeparator(state, c) {
        if (c === " " && state.isLevel /* : unknown */() /* : unknown */) {
            return state.advance /* : unknown */() /* : unknown */;
        }
        let appended = state.append /* : unknown */(c) /* : unknown */;
        if (c === /*  ' */  /* ' */) {
            return appended.enter /* : unknown */() /* : unknown */;
        }
        if (c === ">") {
            return appended.exit /* : unknown */() /* : unknown */;
        }
        return appended;
    }
    assembleDefinition(state, annotations, modifiers, rawName, typeParams, type) {
        return this.parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state.withTypeParams /* : unknown */(typeParams) /* : unknown */, type) /* : Option<[CompileState, Type]> */.flatMap /* : unknown */((type1) => {
            let stripped = rawName.strip /* : unknown */() /* : unknown */;
            if (!isSymbol(stripped) /* : unknown */) {
                return new None() /* : None */;
            }
            let newModifiers = modifiers.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((value) => !this.isAccessor /* : unknown */(value) /* : unknown */) /* : unknown */.map /* : unknown */((modifier) =>  /* modifier.equals("final") ? "readonly" : modifier */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
            let node = new Definition(annotations, newModifiers, stripped, type1.right /* : unknown */() /* : unknown */, typeParams) /* : Definition */;
            return new Some([type1.left /* : unknown */() /* : unknown */, node]) /* : Some */;
        }) /* : unknown */;
    }
    isAccessor(value) {
        return value === "private";
    }
    foldValueChar(state, c) {
        if (c === "," && state.isLevel /* : unknown */() /* : unknown */) {
            return state.advance /* : unknown */() /* : unknown */;
        }
        let appended = state.append /* : unknown */(c) /* : unknown */;
        if (c === /*  ' */ - /* ' */) {
            let peeked = appended.peek /* : unknown */() /* : unknown */;
            if (peeked === ">") {
                return appended.popAndAppendToOption /* : unknown */() /* : unknown */.orElse /* : unknown */(appended) /* : unknown */;
            }
            else {
                return appended;
            }
        }
        if (c === /*  ' */  /* '  */ || c === "(" || c === "{") {
            return appended.enter /* : unknown */() /* : unknown */;
        }
        if (c === ">" || c === ")" || c === "}") {
            return appended.exit /* : unknown */() /* : unknown */;
        }
        return appended;
    }
    parseType(state, input) {
        let stripped = input.strip /* : unknown */() /* : unknown */;
        if (stripped === "int" || stripped === "Integer") {
            return new Some([state, Primitive.Int /* : unknown */]) /* : Some */;
        }
        if (stripped === "String" || stripped === "char" || stripped === "Character") {
            return new Some([state, Primitive.String /* : unknown */]) /* : Some */;
        }
        if (stripped === "var") {
            return new Some([state, Primitive.Unknown /* : unknown */]) /* : Some */;
        }
        if (stripped === "boolean") {
            return new Some([state, Primitive.Boolean /* : unknown */]) /* : Some */;
        }
        if (stripped === "void") {
            return new Some([state, Primitive.Void /* : unknown */]) /* : Some */;
        }
        if (isSymbol(stripped) /* : boolean */) {
            return new Some([state, new SymbolType(stripped) /* : SymbolType */]) /* : Some */;
        }
        return this.parseTemplate /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state, input) /* : Option<[CompileState, Type]> */.or /* : unknown */(() => this.varArgs /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state, input) /* : Option<[CompileState, Type]> */) /* : unknown */;
    }
    varArgs(state, input) {
        return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input, "...", (s) => {
            return this.parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state, s) /* : Option<[CompileState, Type]> */.map /* : unknown */((inner) => {
                let newState = inner.left /* : unknown */() /* : unknown */;
                let child = inner.right /* : unknown */() /* : unknown */;
                return [newState, new ArrayType(child) /* : ArrayType */];
            }) /* : unknown */;
        }) /* : Option<T> */;
    }
    assembleTemplate(base, state, arguments) {
        let children = arguments.iterate /* : unknown */() /* : unknown */.map /* : unknown */(this.retainType /* : unknown */) /* : unknown */.flatMap /* : unknown */(Queries.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        if (base === "BiFunction") {
            return [state, new FunctionType(Lists.of /* : unknown */(children.get /* : unknown */(0) /* : unknown */.orElse /* : unknown */(null) /* : unknown */, children.get /* : unknown */(1) /* : unknown */.orElse /* : unknown */(null) /* : unknown */) /* : unknown */, children.get /* : unknown */(2) /* : unknown */.orElse /* : unknown */(null) /* : unknown */) /* : FunctionType */];
        }
        if (base === "Function") {
            return [state, new FunctionType(Lists.of /* : unknown */(children.get /* : unknown */(0) /* : unknown */.orElse /* : unknown */(null) /* : unknown */) /* : unknown */, children.get /* : unknown */(1) /* : unknown */.orElse /* : unknown */(null) /* : unknown */) /* : FunctionType */];
        }
        if (base === "Predicate") {
            return [state, new FunctionType(Lists.of /* : unknown */(children.get /* : unknown */(0) /* : unknown */.orElse /* : unknown */(null) /* : unknown */) /* : unknown */, Primitive.Boolean /* : unknown */) /* : FunctionType */];
        }
        if (base === "Supplier") {
            return [state, new FunctionType(Lists.empty /* : unknown */() /* : unknown */, children.get /* : unknown */(0) /* : unknown */.orElse /* : unknown */(null) /* : unknown */) /* : FunctionType */];
        }
        if (base === "Consumer") {
            return [state, new FunctionType(Lists.of /* : unknown */(children.get /* : unknown */(0) /* : unknown */.orElse /* : unknown */(null) /* : unknown */) /* : unknown */, Primitive.Void /* : unknown */) /* : FunctionType */];
        }
        if (base === "Tuple2" && children.size /* : unknown */() /* : unknown */ >= 2) {
            return [state, new TupleType(children) /* : TupleType */];
        }
        if (state.resolveType /* : unknown */(base) /* : unknown */._UnknownVariant /* : unknown */ === UnknownVariant.Some) {
            let baseType = some.value /* : unknown */;
            if (baseType._UnknownVariant /* : unknown */ === UnknownVariant.) {
                let some = state.resolveType /* : unknown */(base) /* : unknown */;
                let findableType = baseType;
                return [state, new Template(findableType, children) /* : Template */];
            }
        }
        return [state, new Template(new ObjectRefType(base) /* : ObjectRefType */, children) /* : Template */];
    }
    parseTemplate(state, input) {
        return this.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input.strip /* : unknown */() /* : unknown */, ">", (withoutEnd) => {
            return this.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutEnd, "<", (base, argumentsString) => {
                let strippedBase = base.strip /* : unknown */() /* : unknown */;
                return this.parseValues /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state, argumentsString, this.parseArgument /* : unknown */) /* : Option<[CompileState, List<T>]> */.map /* : unknown */((argumentsTuple) => {
                    return this.assembleTemplate /* : (arg0 : string, arg1 : CompileState, arg2 : List<Argument>) => [CompileState, Type] */(strippedBase, argumentsTuple.left /* : unknown */() /* : unknown */, argumentsTuple.right /* : unknown */() /* : unknown */) /* : [CompileState, Type] */;
                }) /* : unknown */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    retainType(argument) {
        if (argument._Variant /* : unknown */ === Variant.) {
            let type = argument;
            return new Some(type) /* : Some */;
        }
        else {
            return new None() /* : None<Type> */;
        }
    }
    parseArgument(state, input) {
        if (Strings.isBlank /* : unknown */(input) /* : unknown */) {
            return new Some([state, new Whitespace() /* : Whitespace */]) /* : Some */;
        }
        return this.parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state, input) /* : Option<[CompileState, Type]> */.map /* : unknown */((tuple) => [tuple.left /* : unknown */() /* : unknown */, tuple.right /* : unknown */() /* : unknown */]) /* : unknown */;
    }
    last(input, infix, mapper) {
        return this.infix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<number>, arg3 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input, infix, this.findLast /* : unknown */, mapper) /* : Option<T> */;
    }
    findLast(input, infix) {
        let index = input.lastIndexOf /* : unknown */(infix) /* : unknown */;
        if (index === -1) {
            return new None() /* : None<number> */;
        }
        return new Some(index) /* : Some */;
    }
    first(input, infix, mapper) {
        return this.infix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<number>, arg3 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input, infix, this.findFirst /* : unknown */, mapper) /* : Option<T> */;
    }
    split(splitter, splitMapper) {
        return splitter() /* : Option<[string, string]> */.flatMap /* : unknown */((splitTuple) => splitMapper(splitTuple.left /* : unknown */() /* : unknown */, splitTuple.right /* : unknown */() /* : unknown */) /* : Option<T> */) /* : unknown */;
    }
    infix(input, infix, locator, mapper) {
        return this.split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => locator(input, infix) /* : Option<number> */.map /* : unknown */((index) => {
            let left = input.substring /* : unknown */(0, index) /* : unknown */;
            let right = input.substring /* : unknown */(index + Strings.length /* : unknown */(infix) /* : unknown */) /* : unknown */;
            return [left, right];
        }) /* : unknown */, mapper) /* : Option<T> */;
    }
    findFirst(input, infix) {
        let index = input.indexOf /* : unknown */(infix) /* : unknown */;
        if (index === -1) {
            return new None() /* : None<number> */;
        }
        return new Some(index) /* : Some */;
    }
}
Main.isDebugEnabled = true;
/*  */ 
