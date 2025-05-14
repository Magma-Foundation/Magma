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
var FindableTypeVariant;
(function (FindableTypeVariant) {
    FindableTypeVariant[FindableTypeVariant["ObjectType"] = 0] = "ObjectType";
    FindableTypeVariant[FindableTypeVariant["Placeholder"] = 1] = "Placeholder";
    FindableTypeVariant[FindableTypeVariant["Template"] = 2] = "Template";
})(FindableTypeVariant || (FindableTypeVariant = {}));
var DefinitionVariant;
(function (DefinitionVariant) {
    DefinitionVariant[DefinitionVariant["ImmutableDefinition"] = 0] = "ImmutableDefinition";
})(DefinitionVariant || (DefinitionVariant = {}));
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
    constructor() {
        this._OptionVariant = OptionVariant.None /* : OptionVariant */;
    }
    map(mapper) {
        return new None() /* : None */;
    }
    isPresent() {
        return false;
    }
    orElse(other) {
        return other /* : T */;
    }
    filter(predicate) {
        return new None() /* : None */;
    }
    orElseGet(supplier) {
        return supplier /* : () => T */() /* : T */;
    }
    or(other) {
        return other /* : () => Option<T> */() /* : Option<T> */;
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
        this._OptionVariant = OptionVariant.Some /* : OptionVariant */;
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
    }
    map(mapper) {
        return new Some(mapper /* : (arg0 : T) => R */(this /* : Some */.value /* : T */) /* : R */) /* : Some */;
    }
    isPresent() {
        return true;
    }
    orElse(other) {
        return this /* : Some */.value /* : T */;
    }
    filter(predicate) {
        if (predicate /* : (arg0 : T) => boolean */(this /* : Some */.value /* : T */) /* : boolean */) {
            return this /* : Some */;
        }
        return new None() /* : None */;
    }
    orElseGet(supplier) {
        return this /* : Some */.value /* : T */;
    }
    or(other) {
        return this /* : Some */;
    }
    flatMap(mapper) {
        return mapper /* : (arg0 : T) => Option<R> */(this /* : Some */.value /* : T */) /* : Option<R> */;
    }
    isEmpty() {
        return false;
    }
    and(other) {
        return other /* : () => Option<R> */() /* : Option<R> */.map /* : (arg0 : (arg0 : R) => R) => Option<R> */((otherValue) => [this /* : Some */.value /* : T */, otherValue /* : R */]) /* : Option<R> */;
    }
    ifPresent(consumer) {
        /* consumer.accept(this.value) */ ;
    }
}
/* private static */ class SingleHead {
    constructor(retrievableValue) {
        this /* : SingleHead */.retrievableValue /* : T */ = retrievableValue /* : T */;
        this /* : SingleHead */.retrieved /* : boolean */ = false;
    }
    next() {
        if (this /* : SingleHead */.retrieved /* : boolean */) {
            return new None() /* : None */;
        }
        this /* : SingleHead */.retrieved /* : boolean */ = true;
        return new Some(this /* : SingleHead */.retrievableValue /* : T */) /* : Some */;
    }
}
/* private static */ class EmptyHead {
    next() {
        return new None() /* : None */;
    }
}
/* private */ class HeadedQuery {
    constructor(head) {
        this /* : unknown */.head /* : unknown */ = head /* : unknown */;
    }
    fold(initial, folder) {
        let current = initial /* : R */;
        while (true) {
            let finalCurrent = current /* : R */;
            let option = this /* : HeadedQuery */.head /* : Head<T> */.next /* : () => Option<T> */() /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */((inner) => folder /* : (arg0 : R, arg1 : T) => R */(finalCurrent /* : R */, inner /* : T */) /* : R */) /* : Option<R> */;
            if (option /* : Option<R> */._OptionVariant /* : unknown */ === OptionVariant.Some /* : unknown */) {
                let some = option /* : Option<R> */;
                current /* : R */ = some /* : Some<R> */.value /* : R */;
            }
            else {
                return current /* : R */;
            }
        }
    }
    map(mapper) {
        return new HeadedQuery(new MapHead(this /* : HeadedQuery */.head /* : Head<T> */, mapper /* : (arg0 : T) => R */) /* : MapHead */) /* : HeadedQuery */;
    }
    collect(collector) {
        return this /* : HeadedQuery */.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */(collector /* : Collector<T, R> */.createInitial /* : () => R */() /* : R */, collector /* : Collector<T, R> */.fold /* : unknown */) /* : R */;
    }
    filter(predicate) {
        return this /* : HeadedQuery */.flatMap /* : (arg0 : (arg0 : T) => Query<R>) => Query<R> */((element) => {
            if (predicate /* : (arg0 : T) => boolean */(element /* : T */) /* : boolean */) {
                return new HeadedQuery(new SingleHead(element /* : T */) /* : SingleHead */) /* : HeadedQuery */;
            }
            return new HeadedQuery(new EmptyHead() /* : EmptyHead */) /* : HeadedQuery */;
        }) /* : Query<R> */;
    }
    next() {
        return this /* : HeadedQuery */.head /* : Head<T> */.next /* : () => Option<T> */() /* : Option<T> */;
    }
    flatMap(f) {
        return new HeadedQuery(new FlatMapHead(this /* : HeadedQuery */.head /* : Head<T> */, f /* : (arg0 : T) => Query<R> */) /* : FlatMapHead */) /* : HeadedQuery */;
    }
    zip(other) {
        return new HeadedQuery(new ZipHead(this /* : HeadedQuery */.head /* : Head<T> */, other /* : Query<R> */) /* : ZipHead */) /* : HeadedQuery */;
    }
}
/* private static */ class RangeHead /*  */ {
    constructor(length) {
        this /* : RangeHead */.length /* : number */ = length /* : number */;
        this /* : RangeHead */.counter /* : number */ = 0 /* : number */;
    }
    next() {
        if (this /* : RangeHead */.counter /* : number */ < this /* : RangeHead */.length /* : unknown */) {
            let value = this /* : RangeHead */.counter /* : number */;
            /* this.counter++ */ ;
            return new Some(value /* : number */) /* : Some */;
        }
        return new None() /* : None */;
    }
}
/* private static */ class Lists /*  */ {
}
/* private */ class ImmutableDefinition /*  */ {
    constructor(annotations, modifiers, name, type, typeParams) {
        this._DefinitionVariant = DefinitionVariant.ImmutableDefinition /* : DefinitionVariant */;
        this /* : unknown */.annotations /* : unknown */ = annotations /* : unknown */;
        this /* : unknown */.modifiers /* : unknown */ = modifiers /* : unknown */;
        this /* : unknown */.name /* : unknown */ = name /* : unknown */;
        this /* : unknown */.type /* : unknown */ = type /* : unknown */;
        this /* : unknown */.typeParams /* : unknown */ = typeParams /* : unknown */;
    }
    static createSimpleDefinition(name, type) {
        return new ImmutableDefinition(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, name /* : string */, type /* : Type */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : ImmutableDefinition */;
    }
    findName() {
        return this /* : ImmutableDefinition */.name /* : string */;
    }
    findType() {
        return this /* : ImmutableDefinition */.type /* : Type */;
    }
    generate() {
        return this /* : ImmutableDefinition */.generateWithParams /* : (arg0 : string) => string */("") /* : string */;
    }
    generateType() {
        if (this /* : ImmutableDefinition */.type /* : Type */ === Primitive /* : Primitive */.Unknown /* : unknown */) {
            return "";
        }
        return " : " + this /* : ImmutableDefinition */.type /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    joinModifiers() {
        return this /* : ImmutableDefinition */.modifiers /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.map /* : (arg0 : (arg0 : string) => R) => Query<R> */((value) => value /* : string */ + " ") /* : Query<R> */.collect /* : unknown */(new Joiner("") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    joinTypeParams() {
        return this /* : ImmutableDefinition */.typeParams /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.collect /* : (arg0 : Collector<string, R>) => R */(new Joiner(", ") /* : Joiner */) /* : R */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    mapType(mapper) {
        return new ImmutableDefinition(this /* : ImmutableDefinition */.annotations /* : List<string> */, this /* : ImmutableDefinition */.modifiers /* : List<string> */, this /* : ImmutableDefinition */.name /* : string */, mapper /* : (arg0 : Type) => Type */(this /* : ImmutableDefinition */.type /* : Type */) /* : Type */, this /* : ImmutableDefinition */.typeParams /* : List<string> */) /* : ImmutableDefinition */;
    }
    generateWithParams(joinedParameters) {
        let joinedAnnotations = this /* : ImmutableDefinition */.annotations /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.map /* : (arg0 : (arg0 : string) => R) => Query<R> */((value) => "@" + value + " ") /* : Query<R> */.collect /* : unknown */(Joiner /* : Joiner */.empty /* : () => Joiner */() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        let joined = this /* : ImmutableDefinition */.joinTypeParams /* : () => string */() /* : string */;
        let before = this /* : ImmutableDefinition */.joinModifiers /* : () => string */() /* : string */;
        let typeString = this /* : ImmutableDefinition */.generateType /* : () => string */() /* : string */;
        return joinedAnnotations /* : unknown */ + before /* : string */ + this /* : ImmutableDefinition */.name /* : string */ + joined /* : string */ + joinedParameters /* : string */ + typeString /* : string */;
    }
    createDefinition(paramTypes) {
        let type1 = new FunctionType(paramTypes /* : List<Type> */, this /* : ImmutableDefinition */.type /* : Type */) /* : FunctionType */;
        return new ImmutableDefinition(this /* : ImmutableDefinition */.annotations /* : List<string> */, this /* : ImmutableDefinition */.modifiers /* : List<string> */, this /* : ImmutableDefinition */.name /* : string */, type1 /* : Type */, this /* : ImmutableDefinition */.typeParams /* : List<string> */) /* : ImmutableDefinition */;
    }
    containsAnnotation(annotation) {
        return this /* : ImmutableDefinition */.annotations /* : List<string> */.contains /* : (arg0 : string) => boolean */(annotation /* : string */) /* : boolean */;
    }
    removeAnnotations() {
        return new ImmutableDefinition(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, this /* : ImmutableDefinition */.modifiers /* : List<string> */, this /* : ImmutableDefinition */.name /* : string */, this /* : ImmutableDefinition */.type /* : Type */, this /* : ImmutableDefinition */.typeParams /* : List<string> */) /* : ImmutableDefinition */;
    }
    toString() {
        return "ImmutableDefinition[" + "annotations=" + this /* : ImmutableDefinition */.annotations /* : List<string> */ + ", " + "maybeBefore=" + this /* : ImmutableDefinition */.modifiers /* : List<string> */ + ", " + "findName=" + this /* : ImmutableDefinition */.name /* : string */ + ", " + "findType=" + this /* : ImmutableDefinition */.type /* : Type */ + ", " + "typeParams=" + this /* : ImmutableDefinition */.typeParams /* : List<string> */ + "]";
    }
}
/* private */ class ObjectType /*  */ {
    constructor(name, typeParams, definitions, variants) {
        this._FindableTypeVariant = FindableTypeVariant.ObjectType /* : FindableTypeVariant */;
        this /* : unknown */.name /* : unknown */ = name /* : unknown */;
        this /* : unknown */.typeParams /* : unknown */ = typeParams /* : unknown */;
        this /* : unknown */.definitions /* : unknown */ = definitions /* : unknown */;
        this /* : unknown */.variants /* : unknown */ = variants /* : unknown */;
    }
    generate() {
        return this /* : ObjectType */.name /* : string */;
    }
    replace(mapping) {
        return new ObjectType(this /* : ObjectType */.name /* : string */, this /* : ObjectType */.typeParams /* : List<string> */, this /* : ObjectType */.definitions /* : List<Definition> */.query /* : () => Query<Definition> */() /* : Query<Definition> */.map /* : (arg0 : (arg0 : Definition) => R) => Query<R> */((definition) => definition /* : Definition */.mapType /* : (arg0 : (arg0 : Type) => Type) => Definition */((type) => type /* : Type */.replace /* : (arg0 : Map<string, Type>) => Type */(mapping /* : Map<string, Type> */) /* : Type */) /* : Definition */) /* : Query<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */, this /* : ObjectType */.variants /* : List<string> */) /* : ObjectType */;
    }
    find(name) {
        return this /* : ObjectType */.definitions /* : List<Definition> */.query /* : () => Query<Definition> */() /* : Query<Definition> */.filter /* : (arg0 : (arg0 : Definition) => boolean) => Query<Definition> */((definition) => definition /* : Definition */.findName /* : () => string */() /* : string */ === name /* : string */) /* : Query<Definition> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(Definition /* : Definition */.findType /* : unknown */) /* : Option<R> */.next /* : unknown */() /* : unknown */;
    }
    findBase() {
        return new Some(this /* : ObjectType */) /* : Some */;
    }
    hasVariant(name) {
        return this /* : ObjectType */.variants /* : List<string> */() /* : unknown */.contains /* : unknown */(name /* : string */) /* : unknown */;
    }
    findName() {
        return this /* : ObjectType */.name /* : string */;
    }
}
/* private */ class TypeParam /*  */ {
    constructor(value) {
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
    }
    generate() {
        return this /* : TypeParam */.value /* : string */;
    }
    replace(mapping) {
        return mapping /* : Map<string, Type> */.find /* : (arg0 : string) => Option<Type> */(this /* : TypeParam */.value /* : string */) /* : Option<Type> */.orElse /* : (arg0 : Type) => Type */(this /* : TypeParam */) /* : Type */;
    }
    findName() {
        return "";
    }
}
/* private */ class CompileState /*  */ {
    constructor(structures, definitions, objectTypes, structNames, typeParams, typeRegister, functionSegments) {
        this /* : unknown */.structures /* : unknown */ = structures /* : unknown */;
        this /* : unknown */.definitions /* : unknown */ = definitions /* : unknown */;
        this /* : unknown */.objectTypes /* : unknown */ = objectTypes /* : unknown */;
        this /* : unknown */.structNames /* : unknown */ = structNames /* : unknown */;
        this /* : unknown */.typeParams /* : unknown */ = typeParams /* : unknown */;
        this /* : unknown */.typeRegister /* : unknown */ = typeRegister /* : unknown */;
        this /* : unknown */.functionSegments /* : unknown */ = functionSegments /* : unknown */;
    }
    static createInitial() {
        return new CompileState(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : List<T> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, new None() /* : None */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : CompileState */;
    }
    resolveValue(name) {
        return this /* : CompileState */.definitions /* : List<List<Definition>> */.iterateReversed /* : () => Query<List<Definition>> */() /* : Query<List<Definition>> */.flatMap /* : (arg0 : (arg0 : List<Definition>) => Query<R>) => Query<R> */(List /* : List */.query /* : unknown */) /* : Query<R> */.filter /* : (arg0 : (arg0 : T) => boolean) => Option<T> */((definition) => definition /* : T */.findName /* : unknown */() /* : unknown */ === name /* : string */) /* : Option<T> */.next /* : unknown */() /* : unknown */.map /* : unknown */(Definition /* : Definition */.findType /* : unknown */) /* : unknown */;
    }
    addStructure(structure) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */.addLast /* : (arg0 : string) => List<string> */(structure /* : string */) /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */) /* : CompileState */;
    }
    defineAll(definitions) {
        let defined = this /* : CompileState */.definitions /* : List<List<Definition>> */.mapLast /* : (arg0 : (arg0 : List<Definition>) => List<Definition>) => List<List<Definition>> */((frame) => frame /* : List<Definition> */.addAllLast /* : (arg0 : List<Definition>) => List<Definition> */(definitions /* : List<Definition> */) /* : List<Definition> */) /* : List<List<Definition>> */;
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, defined /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */) /* : CompileState */;
    }
    resolveType(name) {
        let maybe = this /* : CompileState */.structNames /* : List<[string, List<string>]> */.last /* : () => Option<[string, List<string>]> */() /* : Option<[string, List<string>]> */.filter /* : (arg0 : (arg0 : [string, List<string>]) => boolean) => Option<[string, List<string>]> */((inner) => inner /* : [string, List<string>] */[0 /* : number */] === name /* : string */) /* : Option<[string, List<string>]> */;
        if (maybe /* : Option<[string, List<string>]> */._OptionVariant /* : unknown */ === OptionVariant.Some /* : unknown */) {
            let some = maybe /* : Option<[string, List<string>]> */;
            let found = some /* : Some<[string, List<string>]> */.value /* : [string, List<string>] */;
            return new Some(new ObjectType(found /* : [string, List<string>] */[0 /* : number */], this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */.last /* : () => Option<List<Definition>> */() /* : Option<List<Definition>> */.orElse /* : (arg0 : List<Definition>) => List<Definition> */(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : List<Definition> */, found /* : [string, List<string>] */[1 /* : number */]) /* : ObjectType */) /* : Some */;
        }
        let maybeTypeParam = this /* : CompileState */.typeParams /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.filter /* : (arg0 : (arg0 : string) => boolean) => Query<string> */((param) => param /* : string */ === name /* : string */) /* : Query<string> */.next /* : unknown */() /* : unknown */;
        if (maybeTypeParam /* : unknown */._UnknownVariant /* : unknown */ === UnknownVariant.Some /* : unknown */) {
            let some = maybeTypeParam /* : unknown */;
            return new Some(new TypeParam(some /* : Some<[string, List<string>]> */.value /* : [string, List<string>] */) /* : TypeParam */) /* : Some */;
        }
        return this /* : CompileState */.objectTypes /* : List<ObjectType> */.query /* : () => Query<ObjectType> */() /* : Query<ObjectType> */.filter /* : (arg0 : (arg0 : ObjectType) => boolean) => Query<ObjectType> */((type) => type /* : ObjectType */.name /* : string */ === name /* : string */) /* : Query<ObjectType> */.next /* : unknown */() /* : unknown */.map /* : unknown */((type) => type /* : ObjectType */) /* : unknown */;
    }
    define(definition) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */.mapLast /* : (arg0 : (arg0 : List<Definition>) => List<Definition>) => List<List<Definition>> */((frame) => frame /* : List<Definition> */.addLast /* : (arg0 : Definition) => List<Definition> */(definition /* : Definition */) /* : List<Definition> */) /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */) /* : CompileState */;
    }
    pushStructName(definition) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */.addLast /* : (arg0 : [string, List<string>]) => List<[string, List<string>]> */(definition /* : [string, List<string>] */) /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */) /* : CompileState */;
    }
    withTypeParams(typeParams) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */.addAllLast /* : (arg0 : List<string>) => List<string> */(typeParams /* : List<string> */) /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */) /* : CompileState */;
    }
    withExpectedType(type) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */, new Some(type /* : Type */) /* : Some */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */) /* : CompileState */;
    }
    popStructName() {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */.removeLast /* : () => Option<[List<[string, List<string>]>, [string, List<string>]]> */() /* : Option<[List<[string, List<string>]>, [string, List<string>]]> */.map /* : (arg0 : (arg0 : [List<[string, List<string>]>, [string, List<string>]]) => R) => Option<R> */(Tuple2 /* : Tuple2 */.left /* : unknown */) /* : Option<R> */.orElse /* : unknown */(this /* : CompileState */.structNames /* : List<[string, List<string>]> */) /* : unknown */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */) /* : CompileState */;
    }
    enterDefinitions() {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */.addLast /* : (arg0 : List<Definition>) => List<List<Definition>> */(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */) /* : CompileState */;
    }
    exitDefinitions() {
        let removed = this /* : CompileState */.definitions /* : List<List<Definition>> */.removeLast /* : () => Option<[List<List<Definition>>, List<Definition>]> */() /* : Option<[List<List<Definition>>, List<Definition>]> */.map /* : (arg0 : (arg0 : [List<List<Definition>>, List<Definition>]) => R) => Option<R> */(Tuple2 /* : Tuple2 */.left /* : unknown */) /* : Option<R> */.orElse /* : unknown */(this /* : CompileState */.definitions /* : List<List<Definition>> */) /* : unknown */;
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, removed /* : unknown */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */) /* : CompileState */;
    }
    addType(thisType) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */.addLast /* : (arg0 : ObjectType) => List<ObjectType> */(thisType /* : ObjectType */) /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */) /* : CompileState */;
    }
    addFunctionSegment(segment) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, this /* : CompileState */.functionSegments /* : List<FunctionSegment> */.addLast /* : (arg0 : FunctionSegment) => List<FunctionSegment> */(segment /* : FunctionSegment */) /* : List<FunctionSegment> */) /* : CompileState */;
    }
    clearFunctionSegments() {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<[string, List<string>]> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : CompileState */;
    }
    isCurrentStructName(stripped) {
        return stripped /* : string */ === this /* : CompileState */.structNames /* : List<[string, List<string>]> */.last /* : () => Option<[string, List<string>]> */() /* : Option<[string, List<string>]> */.map /* : (arg0 : (arg0 : [string, List<string>]) => R) => Option<R> */(Tuple2 /* : Tuple2 */.left /* : unknown */) /* : Option<R> */.orElse /* : unknown */("") /* : unknown */;
    }
}
/* private static */ class DivideState /*  */ {
    constructor(input, index, segments, buffer, depth) {
        this /* : DivideState */.segments /* : List<string> */ = segments /* : List<string> */;
        this /* : DivideState */.buffer /* : string */ = buffer /* : string */;
        this /* : DivideState */.depth /* : number */ = depth /* : number */;
        this /* : DivideState */.input /* : string */ = input /* : string */;
        this /* : DivideState */.index /* : number */ = index /* : number */;
    }
    static createInitial(input) {
        return new DivideState(input /* : string */, 0 /* : number */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, "", 0 /* : number */) /* : DivideState */;
    }
    advance() {
        this /* : DivideState */.segments /* : List<string> */ = this /* : DivideState */.segments /* : List<string> */.addLast /* : (arg0 : string) => List<string> */(this /* : DivideState */.buffer /* : string */) /* : List<string> */;
        this /* : DivideState */.buffer /* : string */ = "";
        return this /* : DivideState */;
    }
    append(c) {
        this /* : DivideState */.buffer /* : string */ = this /* : DivideState */.buffer /* : string */ + c /* : string */;
        return this /* : DivideState */;
    }
    enter() {
        /* this.depth++ */ ;
        return this /* : DivideState */;
    }
    isLevel() {
        return this /* : DivideState */.depth /* : number */ === 0 /* : number */;
    }
    exit() {
        /* this.depth-- */ ;
        return this /* : DivideState */;
    }
    isShallow() {
        return this /* : DivideState */.depth /* : number */ === 1 /* : number */;
    }
    pop() {
        if (this /* : DivideState */.index /* : number */ < Strings /* : Strings */.length /* : unknown */(this /* : DivideState */.input /* : string */) /* : unknown */) {
            let c = this /* : DivideState */.input /* : string */.charAt /* : unknown */(this /* : DivideState */.index /* : number */) /* : unknown */;
            return new Some([c /* : unknown */, new DivideState(this /* : DivideState */.input /* : string */, this /* : DivideState */.index /* : number */ + 1 /* : number */, this /* : DivideState */.segments /* : List<string> */, this /* : DivideState */.buffer /* : string */, this /* : DivideState */.depth /* : number */) /* : DivideState */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    popAndAppendToTuple() {
        return this /* : DivideState */.pop /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */.map /* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple) => {
            let c = tuple /* : [string, DivideState] */[0 /* : number */];
            let right = tuple /* : [string, DivideState] */[1 /* : number */];
            return [c /* : unknown */, right /* : unknown */.append /* : unknown */(c /* : unknown */) /* : unknown */];
        }) /* : Option<R> */;
    }
    popAndAppendToOption() {
        return this /* : DivideState */.popAndAppendToTuple /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */.map /* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */(Tuple2 /* : Tuple2 */.right /* : unknown */) /* : Option<R> */;
    }
    peek() {
        return this /* : DivideState */.input /* : string */.charAt /* : unknown */(this /* : DivideState */.index /* : number */) /* : unknown */;
    }
}
/* private */ class Joiner /*  */ {
    constructor(delimiter) {
        this /* : unknown */.delimiter /* : unknown */ = delimiter /* : unknown */;
    }
    static empty() {
        return new Joiner("") /* : Joiner */;
    }
    createInitial() {
        return new None() /* : None */;
    }
    fold(current, element) {
        return new Some(current /* : Option<string> */.map /* : (arg0 : (arg0 : string) => R) => Option<R> */((inner) => inner /* : string */ + this /* : Joiner */.delimiter /* : string */ + element /* : string */) /* : Option<R> */.orElse /* : unknown */(element /* : string */) /* : unknown */) /* : Some */;
    }
}
/* private static */ class ListCollector {
    createInitial() {
        return Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */;
    }
    fold(current, element) {
        return current /* : List<T> */.addLast /* : (arg0 : T) => List<T> */(element /* : T */) /* : List<T> */;
    }
}
/* private static */ class FlatMapHead {
    constructor(head, mapper) {
        this /* : FlatMapHead */.mapper /* : (arg0 : T) => Query<R> */ = mapper /* : (arg0 : T) => Query<R> */;
        this /* : FlatMapHead */.current /* : Option<Query<R>> */ = new None() /* : None */;
        this /* : FlatMapHead */.head /* : Head<T> */ = head /* : Head<T> */;
    }
    next() {
        while (true) {
            if (this /* : FlatMapHead */.current /* : Option<Query<R>> */.isPresent /* : () => boolean */() /* : boolean */) {
                let inner = this /* : FlatMapHead */.current /* : Option<Query<R>> */.orElse /* : (arg0 : Query<R>) => Query<R> */( /* null */) /* : Query<R> */;
                let maybe = inner /* : Query<R> */.next /* : () => Option<R> */() /* : Option<R> */;
                if (maybe /* : Option<R> */.isPresent /* : () => boolean */() /* : boolean */) {
                    return maybe /* : Option<R> */;
                }
                else {
                    this /* : FlatMapHead */.current /* : Option<Query<R>> */ = new None() /* : None */;
                }
            }
            let outer = this /* : FlatMapHead */.head /* : Head<T> */.next /* : () => Option<T> */() /* : Option<T> */;
            if (outer /* : Option<T> */.isPresent /* : () => boolean */() /* : boolean */) {
                this /* : FlatMapHead */.current /* : Option<Query<R>> */ = outer /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(this /* : FlatMapHead */.mapper /* : (arg0 : T) => Query<R> */) /* : Option<R> */;
            }
            else {
                return new None() /* : None */;
            }
        }
    }
}
/* private */ class ArrayType /*  */ {
    constructor(right) {
        this /* : unknown */.right /* : unknown */ = right /* : unknown */;
    }
    generate() {
        return this /* : ArrayType */.right /* : Type */.generate /* : () => string */() /* : string */ + "[]";
    }
    replace(mapping) {
        return this /* : ArrayType */;
    }
    findName() {
        return "";
    }
}
/* private static final */ class Whitespace /*  */ {
    constructor() {
        this._IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.Whitespace /* : IncompleteClassSegmentVariant */;
        this._ParameterVariant = ParameterVariant.Whitespace /* : ParameterVariant */;
        this._ArgumentVariant = ArgumentVariant.Whitespace /* : ArgumentVariant */;
    }
    generate() {
        return "";
    }
    maybeCreateDefinition() {
        return new None() /* : None */;
    }
}
/* private static */ class Queries /*  */ {
    static fromOption(option) {
        let single = option /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(SingleHead /* : SingleHead */.new /* : unknown */) /* : Option<R> */;
        return new HeadedQuery(single /* : Option<Head<T>> */.orElseGet /* : (arg0 : () => Head<T>) => Head<T> */(EmptyHead /* : EmptyHead */.new /* : unknown */) /* : Head<T> */) /* : HeadedQuery */;
    }
    static from(elements) {
        return new HeadedQuery(new RangeHead(elements /* : T[] */.length /* : unknown */) /* : RangeHead */) /* : HeadedQuery */.map /* : (arg0 : (arg0 : T) => R) => Query<R> */((index) =>  /* elements[index] */) /* : Query<R> */;
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
        this /* : unknown */.arguments /* : unknown */ = arguments /* : unknown */;
        this /* : unknown */.returns /* : unknown */ = returns /* : unknown */;
    }
    generate() {
        let joined = this /* : FunctionType */.arguments /* : List<Type> */() /* : unknown */.iterateWithIndices /* : unknown */() /* : unknown */.map /* : unknown */((pair) => "arg" + pair /* : unknown */.left /* : unknown */() /* : unknown */ + " : " + pair /* : unknown */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + joined /* : unknown */ + ") => " + this /* : FunctionType */.returns /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    replace(mapping) {
        return new FunctionType(this /* : FunctionType */.arguments /* : List<Type> */.query /* : () => Query<Type> */() /* : Query<Type> */.map /* : (arg0 : (arg0 : Type) => R) => Query<R> */((type) => type /* : Type */.replace /* : (arg0 : Map<string, Type>) => Type */(mapping /* : Map<string, Type> */) /* : Type */) /* : Query<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */, this /* : FunctionType */.returns /* : Type */.replace /* : (arg0 : Map<string, Type>) => Type */(mapping /* : Map<string, Type> */) /* : Type */) /* : FunctionType */;
    }
    findName() {
        return "";
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
        this /* : unknown */.arguments /* : unknown */ = arguments /* : unknown */;
    }
    generate() {
        let joinedArguments = this /* : TupleType */.arguments /* : List<Type> */.query /* : () => Query<Type> */() /* : Query<Type> */.map /* : (arg0 : (arg0 : Type) => R) => Query<R> */(Type /* : Type */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "[" + joinedArguments + "]";
    }
    replace(mapping) {
        return new TupleType(this /* : TupleType */.arguments /* : List<Type> */.query /* : () => Query<Type> */() /* : Query<Type> */.map /* : (arg0 : (arg0 : Type) => R) => Query<R> */((child) => child /* : Type */.replace /* : (arg0 : Map<string, Type>) => Type */(mapping /* : Map<string, Type> */) /* : Type */) /* : Query<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */) /* : TupleType */;
    }
    findName() {
        return "";
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
        this._FindableTypeVariant = FindableTypeVariant.Template /* : FindableTypeVariant */;
        this /* : unknown */.base /* : unknown */ = base /* : unknown */;
        this /* : unknown */.arguments /* : unknown */ = arguments /* : unknown */;
    }
    generate() {
        let joinedArguments = this /* : Template */.arguments /* : List<Type> */.query /* : () => Query<Type> */() /* : Query<Type> */.map /* : (arg0 : (arg0 : Type) => R) => Query<R> */(Type /* : Type */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return this /* : Template */.base /* : ObjectType */.generate /* : () => string */() /* : string */ + joinedArguments /* : unknown */;
    }
    find(name) {
        return this /* : Template */.base /* : ObjectType */.find /* : (arg0 : string) => Option<Type> */(name /* : string */) /* : Option<Type> */.map /* : (arg0 : (arg0 : Type) => R) => Option<R> */((found) => {
            let mapping = this /* : Template */.base /* : ObjectType */.typeParams /* : List<string> */() /* : unknown */.query /* : unknown */() /* : unknown */.zip /* : unknown */(this /* : Template */.arguments /* : List<Type> */.query /* : () => Query<Type> */() /* : Query<Type> */) /* : unknown */.collect /* : unknown */(new MapCollector() /* : MapCollector */) /* : unknown */;
            return found /* : Type */.replace /* : (arg0 : Map<string, Type>) => Type */(mapping /* : unknown */) /* : Type */;
        }) /* : Option<R> */;
    }
    findBase() {
        return new Some(this /* : Template */.base /* : ObjectType */) /* : Some */;
    }
    replace(mapping) {
        let collect = this /* : Template */.arguments /* : List<Type> */.query /* : () => Query<Type> */() /* : Query<Type> */.map /* : (arg0 : (arg0 : Type) => R) => Query<R> */((argument) => argument /* : Type */.replace /* : (arg0 : Map<string, Type>) => Type */(mapping /* : Map<string, Type> */) /* : Type */) /* : Query<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        return new Template(this /* : Template */.base /* : ObjectType */, collect /* : unknown */) /* : Template */;
    }
    findName() {
        return this /* : Template */.base /* : ObjectType */.findName /* : () => string */() /* : string */;
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
        this._IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.Placeholder /* : IncompleteClassSegmentVariant */;
        this._FindableTypeVariant = FindableTypeVariant.Placeholder /* : FindableTypeVariant */;
        this._ValueVariant = ValueVariant.Placeholder /* : ValueVariant */;
        this._ParameterVariant = ParameterVariant.Placeholder /* : ParameterVariant */;
        this /* : unknown */.input /* : unknown */ = input /* : unknown */;
    }
    generate() {
        return generatePlaceholder /* : (arg0 : string) => string */(this /* : Placeholder */.input /* : string */) /* : string */;
    }
    type() {
        return Primitive /* : Primitive */.Unknown /* : unknown */;
    }
    find(name) {
        return new None() /* : None */;
    }
    findBase() {
        return new None() /* : None */;
    }
    replace(mapping) {
        return this /* : Placeholder */;
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
        this._ValueVariant = ValueVariant.StringValue /* : ValueVariant */;
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
    }
    generate() {
        return "\"" + this.value + "\"";
    }
    type() {
        return Primitive /* : Primitive */.Unknown /* : unknown */;
    }
}
/* private */ class DataAccess /*  */ {
    constructor(parent, property, type) {
        this._ValueVariant = ValueVariant.DataAccess /* : ValueVariant */;
        this /* : unknown */.parent /* : unknown */ = parent /* : unknown */;
        this /* : unknown */.property /* : unknown */ = property /* : unknown */;
        this /* : unknown */.type /* : unknown */ = type /* : unknown */;
    }
    generate() {
        return this /* : DataAccess */.parent /* : Value */.generate /* : () => string */() /* : string */ + "." + this /* : DataAccess */.property /* : string */ + createDebugString /* : (arg0 : Type) => string */(this /* : DataAccess */.type /* : () => Type */) /* : unknown */;
    }
    type() {
        return this /* : DataAccess */.type /* : () => Type */;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(type) {
        this._CallerVariant = CallerVariant.ConstructionCaller /* : CallerVariant */;
        this /* : unknown */.type /* : unknown */ = type /* : unknown */;
    }
    generate() {
        return "new " + this /* : ConstructionCaller */.type /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    toFunction() {
        return new FunctionType(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, this /* : ConstructionCaller */.type /* : Type */) /* : FunctionType */;
    }
}
/* private */ class Operator /*  */ {
    constructor(sourceRepresentation, targetRepresentation) {
        this.GREATER_THAN_OR_EQUALS = Operator();
        this.LESS_THAN = Operator();
        this /* : unknown */.sourceRepresentation /* : unknown */ = sourceRepresentation /* : unknown */;
        this /* : unknown */.targetRepresentation /* : unknown */ = targetRepresentation /* : unknown */;
    }
    ;
    ;
}
Operator.ADD = new Operator("+", "+") /* : Operator */;
Operator.AND = new Operator("&&", "&&") /* : Operator */;
Operator.EQUALS = new Operator("==", "===") /* : Operator */;
Operator.OR = new Operator("||", "||") /* : Operator */;
Operator.SUBTRACT = new Operator("-", "-") /* : Operator */;
/* private */ class Operation /*  */ {
    constructor(left, operator, right) {
        this._ValueVariant = ValueVariant.Operation /* : ValueVariant */;
        this /* : unknown */.left /* : unknown */ = left /* : unknown */;
        this /* : unknown */.operator /* : unknown */ = operator /* : unknown */;
        this /* : unknown */.right /* : unknown */ = right /* : unknown */;
    }
    generate() {
        return this /* : Operation */.left /* : Value */() /* : unknown */.generate /* : unknown */() /* : unknown */ + " " + this /* : Operation */.operator /* : Operator */.targetRepresentation /* : unknown */ + " " + this /* : Operation */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    type() {
        return Primitive /* : Primitive */.Unknown /* : unknown */;
    }
}
/* private */ class Not /*  */ {
    constructor(value) {
        this._ValueVariant = ValueVariant.Not /* : ValueVariant */;
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
    }
    generate() {
        return "!" + this /* : Not */.value /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    type() {
        return Primitive /* : Primitive */.Unknown /* : unknown */;
    }
}
/* private */ class BlockLambdaValue /*  */ {
    constructor(depth, statements) {
        this /* : unknown */.depth /* : unknown */ = depth /* : unknown */;
        this /* : unknown */.statements /* : unknown */ = statements /* : unknown */;
    }
    generate() {
        return "{" + this.joinStatements() + createIndent(this.depth) + "}";
    }
    joinStatements() {
        return this /* : BlockLambdaValue */.statements /* : List<FunctionSegment> */.query /* : () => Query<FunctionSegment> */() /* : Query<FunctionSegment> */.map /* : (arg0 : (arg0 : FunctionSegment) => R) => Query<R> */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(Joiner /* : Joiner */.empty /* : () => Joiner */() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameters, body) {
        this._ValueVariant = ValueVariant.Lambda /* : ValueVariant */;
        this /* : unknown */.parameters /* : unknown */ = parameters /* : unknown */;
        this /* : unknown */.body /* : unknown */ = body /* : unknown */;
    }
    generate() {
        let joined = this /* : Lambda */.parameters /* : List<Definition> */.query /* : () => Query<Definition> */() /* : Query<Definition> */.map /* : (arg0 : (arg0 : Definition) => R) => Query<R> */(Definition /* : Definition */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + joined /* : unknown */ + ") => " + this /* : Lambda */.body /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    type() {
        return Primitive /* : Primitive */.Unknown /* : unknown */;
    }
}
/* private */ class Invokable /*  */ {
    constructor(caller, arguments, type) {
        this._ValueVariant = ValueVariant.Invokable /* : ValueVariant */;
        this /* : unknown */.caller /* : unknown */ = caller /* : unknown */;
        this /* : unknown */.arguments /* : unknown */ = arguments /* : unknown */;
        this /* : unknown */.type /* : unknown */ = type /* : unknown */;
    }
    generate() {
        let joined = this /* : Invokable */.arguments /* : List<Value> */.query /* : () => Query<Value> */() /* : Query<Value> */.map /* : (arg0 : (arg0 : Value) => R) => Query<R> */(Value /* : Value */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return this /* : Invokable */.caller /* : Caller */.generate /* : () => string */() /* : string */ + "(" + joined /* : unknown */ + ")" + createDebugString /* : (arg0 : Type) => string */(this /* : Invokable */.type /* : Type */) /* : unknown */;
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
        this._ValueVariant = ValueVariant.IndexValue /* : ValueVariant */;
        this /* : unknown */.parent /* : unknown */ = parent /* : unknown */;
        this /* : unknown */.child /* : unknown */ = child /* : unknown */;
    }
    generate() {
        return this /* : IndexValue */.parent /* : Value */.generate /* : () => string */() /* : string */ + "[" + this.child.generate() + "]";
    }
    type() {
        return Primitive /* : Primitive */.Unknown /* : unknown */;
    }
}
/* private */ class SymbolValue /*  */ {
    constructor(stripped, type) {
        this._ValueVariant = ValueVariant.SymbolValue /* : ValueVariant */;
        this /* : unknown */.stripped /* : unknown */ = stripped /* : unknown */;
        this /* : unknown */.type /* : unknown */ = type /* : unknown */;
    }
    generate() {
        return this /* : SymbolValue */.stripped /* : string */ + createDebugString /* : (arg0 : Type) => string */(this /* : SymbolValue */.type /* : Type */) /* : unknown */;
    }
}
/* private static */ class Maps /*  */ {
}
/* private */ class MapCollector {
    createInitial() {
        return Maps /* : Maps */.empty /* : () => Map<K, V> */() /* : Map<K, V> */;
    }
    fold(current, element) {
        return current /* : Map<K, V> */.with /* : (arg0 : K, arg1 : V) => Map<K, V> */(element /* : [K, V] */[0 /* : number */], element /* : [K, V] */[1 /* : number */]) /* : Map<K, V> */;
    }
}
/* private static */ class ConstructorHeader /*  */ {
    createDefinition(paramTypes) {
        return ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */("new", Primitive /* : Primitive */.Unknown /* : unknown */) /* : Definition */;
    }
    generateWithParams(joinedParameters) {
        return "constructor " + joinedParameters /* : string */;
    }
}
/* private */ class FunctionNode /*  */ {
    constructor(depth, header, parameters, maybeStatements) {
        this /* : unknown */.depth /* : unknown */ = depth /* : unknown */;
        this /* : unknown */.header /* : unknown */ = header /* : unknown */;
        this /* : unknown */.parameters /* : unknown */ = parameters /* : unknown */;
        this /* : unknown */.maybeStatements /* : unknown */ = maybeStatements /* : unknown */;
    }
    static joinStatements(statements) {
        return statements /* : List<FunctionSegment> */.query /* : () => Query<FunctionSegment> */() /* : Query<FunctionSegment> */.map /* : (arg0 : (arg0 : FunctionSegment) => R) => Query<R> */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(Joiner /* : Joiner */.empty /* : () => Joiner */() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    generate() {
        let indent = createIndent /* : (arg0 : number) => string */(this /* : FunctionNode */.depth /* : number */) /* : string */;
        let generatedHeader = this /* : FunctionNode */.header /* : Header */.generateWithParams /* : (arg0 : string) => string */(joinValues /* : (arg0 : List<Definition>) => string */(this /* : FunctionNode */.parameters /* : List<Definition> */) /* : string */) /* : string */;
        let generatedStatements = this /* : FunctionNode */.maybeStatements /* : Option<List<FunctionSegment>> */.map /* : (arg0 : (arg0 : List<FunctionSegment>) => R) => Option<R> */(FunctionNode /* : FunctionNode */.joinStatements /* : unknown */) /* : Option<R> */.map /* : unknown */((inner) => " {" + inner + indent + "}") /* : unknown */.orElse /* : unknown */(";") /* : unknown */;
        return indent /* : string */ + generatedHeader /* : string */ + generatedStatements /* : unknown */;
    }
}
/* private */ class Block /*  */ {
    constructor(depth, header, statements) {
        this /* : unknown */.depth /* : unknown */ = depth /* : unknown */;
        this /* : unknown */.header /* : unknown */ = header /* : unknown */;
        this /* : unknown */.statements /* : unknown */ = statements /* : unknown */;
    }
    generate() {
        let indent = createIndent /* : (arg0 : number) => string */(this /* : Block */.depth /* : number */) /* : string */;
        let collect = this /* : Block */.statements /* : List<FunctionSegment> */.query /* : () => Query<FunctionSegment> */() /* : Query<FunctionSegment> */.map /* : (arg0 : (arg0 : FunctionSegment) => R) => Query<R> */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(Joiner /* : Joiner */.empty /* : () => Joiner */() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return indent /* : string */ + this /* : Block */.header /* : BlockHeader */.generate /* : () => string */() /* : string */ + "{" + collect + indent + "}";
    }
}
/* private */ class Conditional /*  */ {
    constructor(prefix, value1) {
        this /* : unknown */.prefix /* : unknown */ = prefix /* : unknown */;
        this /* : unknown */.value1 /* : unknown */ = value1 /* : unknown */;
    }
    generate() {
        return this /* : Conditional */.prefix /* : string */ + " (" + this.value1.generate() + ")";
    }
}
/* private static */ class Else /*  */ {
    generate() {
        return "else ";
    }
}
/* private */ class Return /*  */ {
    constructor(value) {
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
    }
    generate() {
        return "return " + this /* : Return */.value /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Initialization /*  */ {
    constructor(definition, source) {
        this /* : unknown */.definition /* : unknown */ = definition /* : unknown */;
        this /* : unknown */.source /* : unknown */ = source /* : unknown */;
    }
    generate() {
        return "let " + this /* : Initialization */.definition /* : Definition */.generate /* : () => string */() /* : string */ + " = " + this /* : Initialization */.source /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class FieldInitialization /*  */ {
    constructor(definition, source) {
        this /* : unknown */.definition /* : unknown */ = definition /* : unknown */;
        this /* : unknown */.source /* : unknown */ = source /* : unknown */;
    }
    generate() {
        return this /* : FieldInitialization */.definition /* : Definition */.generate /* : () => string */() /* : string */ + " = " + this /* : FieldInitialization */.source /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Assignment /*  */ {
    constructor(destination, source) {
        this /* : unknown */.destination /* : unknown */ = destination /* : unknown */;
        this /* : unknown */.source /* : unknown */ = source /* : unknown */;
    }
    generate() {
        return this /* : Assignment */.destination /* : Value */.generate /* : () => string */() /* : string */ + " = " + this /* : Assignment */.source /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Statement /*  */ {
    constructor(depth, value) {
        this /* : unknown */.depth /* : unknown */ = depth /* : unknown */;
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
    }
    generate() {
        return createIndent /* : (arg0 : number) => string */(this /* : Statement */.depth /* : number */) /* : string */ + this /* : Statement */.value /* : StatementValue */.generate /* : () => string */() /* : string */ + ";";
    }
}
/* private */ class MethodPrototype /*  */ {
    constructor(depth, header, parameters, content) {
        this._IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.MethodPrototype /* : IncompleteClassSegmentVariant */;
        this /* : unknown */.depth /* : unknown */ = depth /* : unknown */;
        this /* : unknown */.header /* : unknown */ = header /* : unknown */;
        this /* : unknown */.parameters /* : unknown */ = parameters /* : unknown */;
        this /* : unknown */.content /* : unknown */ = content /* : unknown */;
    }
    createDefinition() {
        return this /* : MethodPrototype */.header /* : Header */.createDefinition /* : (arg0 : List<Type>) => Definition */(this /* : MethodPrototype */.findParamTypes /* : () => List<Type> */() /* : List<Type> */) /* : Definition */;
    }
    findParamTypes() {
        return this /* : MethodPrototype */.parameters /* : List<Definition> */() /* : unknown */.query /* : unknown */() /* : unknown */.map /* : unknown */(Definition /* : Definition */.findType /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    maybeCreateDefinition() {
        return new Some(this /* : MethodPrototype */.header /* : Header */.createDefinition /* : (arg0 : List<Type>) => Definition */(this /* : MethodPrototype */.findParamTypes /* : () => List<Type> */() /* : List<Type> */) /* : Definition */) /* : Some */;
    }
}
/* private */ class IncompleteClassSegmentWrapper /*  */ {
    constructor(segment) {
        this._IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.IncompleteClassSegmentWrapper /* : IncompleteClassSegmentVariant */;
        this /* : unknown */.segment /* : unknown */ = segment /* : unknown */;
    }
    maybeCreateDefinition() {
        return new None() /* : None */;
    }
}
/* private */ class ClassDefinition /*  */ {
    constructor(depth, definition) {
        this._IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.ClassDefinition /* : IncompleteClassSegmentVariant */;
        this /* : unknown */.depth /* : unknown */ = depth /* : unknown */;
        this /* : unknown */.definition /* : unknown */ = definition /* : unknown */;
    }
    maybeCreateDefinition() {
        return new Some(this /* : ClassDefinition */.definition /* : Definition */) /* : Some */;
    }
}
/* private */ class ClassInitialization /*  */ {
    constructor(depth, definition, value) {
        this._IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.ClassInitialization /* : IncompleteClassSegmentVariant */;
        this /* : unknown */.depth /* : unknown */ = depth /* : unknown */;
        this /* : unknown */.definition /* : unknown */ = definition /* : unknown */;
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
    }
    maybeCreateDefinition() {
        return new Some(this /* : ClassInitialization */.definition /* : Definition */) /* : Some */;
    }
}
/* private */ class TypeRef /*  */ {
    constructor(value) {
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
    }
}
/* private */ class StructurePrototype /*  */ {
    constructor(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants, interfaces, superTypes) {
        this._IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.StructurePrototype /* : IncompleteClassSegmentVariant */;
        this /* : unknown */.targetInfix /* : unknown */ = targetInfix /* : unknown */;
        this /* : unknown */.beforeInfix /* : unknown */ = beforeInfix /* : unknown */;
        this /* : unknown */.name /* : unknown */ = name /* : unknown */;
        this /* : unknown */.typeParams /* : unknown */ = typeParams /* : unknown */;
        this /* : unknown */.parameters /* : unknown */ = parameters /* : unknown */;
        this /* : unknown */.after /* : unknown */ = after /* : unknown */;
        this /* : unknown */.segments /* : unknown */ = segments /* : unknown */;
        this /* : unknown */.variants /* : unknown */ = variants /* : unknown */;
        this /* : unknown */.interfaces /* : unknown */ = interfaces /* : unknown */;
        this /* : unknown */.superTypes /* : unknown */ = superTypes /* : unknown */;
    }
    static generateEnumEntries(variants) {
        return variants /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.map /* : (arg0 : (arg0 : string) => R) => Query<R> */((inner) => "\n\t" + inner /* : string */) /* : Query<R> */.collect /* : unknown */(new Joiner(",") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    createObjectType() {
        let definitionFromSegments = this /* : StructurePrototype */.segments /* : List<IncompleteClassSegment> */.query /* : () => Query<IncompleteClassSegment> */() /* : Query<IncompleteClassSegment> */.map /* : (arg0 : (arg0 : IncompleteClassSegment) => R) => Query<R> */(IncompleteClassSegment /* : IncompleteClassSegment */.maybeCreateDefinition /* : unknown */) /* : Query<R> */.flatMap /* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries /* : Queries */.fromOption /* : unknown */) /* : Option<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        return new ObjectType(this /* : StructurePrototype */.name /* : string */, this /* : StructurePrototype */.typeParams /* : List<string> */, definitionFromSegments /* : unknown */.addAllLast /* : unknown */(this /* : StructurePrototype */.parameters /* : List<Definition> */) /* : unknown */, this /* : StructurePrototype */.variants /* : List<string> */) /* : ObjectType */;
    }
    maybeCreateDefinition() {
        return new None() /* : None */;
    }
    joinTypeParams() {
        return this /* : StructurePrototype */.typeParams /* : List<string> */() /* : unknown */.query /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    generateToEnum() {
        let variants = this /* : StructurePrototype */.variants /* : List<string> */;
        let joined = generateEnumEntries /* : (arg0 : List<string>) => string */(variants /* : List<string> */) /* : string */;
        return "enum " + this.name + "Variant" + " {" + joined + "\n}\n";
    }
}
/* private */ class Cast /*  */ {
    constructor(value, type) {
        this._ValueVariant = ValueVariant.Cast /* : ValueVariant */;
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
        this /* : unknown */.type /* : unknown */ = type /* : unknown */;
    }
    generate() {
        return this /* : Cast */.value /* : Value */.generate /* : () => string */() /* : string */ + " as " + this /* : Cast */.type /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Ok {
    constructor(value) {
        this._ResultVariant = ResultVariant.Ok /* : ResultVariant */;
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
    }
    mapValue(mapper) {
        return new Ok(mapper /* : (arg0 : T) => R */(this /* : Ok */.value /* : T */) /* : R */) /* : Ok */;
    }
    match(whenOk, whenErr) {
        return whenOk /* : (arg0 : T) => R */(this /* : Ok */.value /* : T */) /* : R */;
    }
}
/* private */ class Err {
    constructor(error) {
        this._ResultVariant = ResultVariant.Err /* : ResultVariant */;
        this /* : unknown */.error /* : unknown */ = error /* : unknown */;
    }
    mapValue(mapper) {
        return new Err(this /* : Err */.error /* : X */) /* : Err */;
    }
    match(whenOk, whenErr) {
        return whenErr /* : (arg0 : X) => R */(this /* : Err */.error /* : X */) /* : R */;
    }
}
/* private */ class JVMIOError /*  */ {
    constructor(error /* IOException */) {
        this /* : unknown */.error /* : unknown */ = error /* : unknown */;
    }
    display() {
        let writer = new /* StringWriter */ () /* : content-start StringWriter content-end */;
        /* this.error.printStackTrace(new PrintWriter(writer)) */ ;
        return writer /* : content-start StringWriter content-end */.toString /* : unknown */() /* : unknown */;
    }
}
/* private */ class TupleNode /*  */ {
    constructor(values) {
        this._ValueVariant = ValueVariant.TupleNode /* : ValueVariant */;
        this /* : unknown */.values /* : unknown */ = values /* : unknown */;
    }
    generate() {
        let joined = this /* : TupleNode */.values /* : List<Value> */.query /* : () => Query<Value> */() /* : Query<Value> */.map /* : (arg0 : (arg0 : Value) => R) => Query<R> */(Value /* : Value */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "[" + joined + "]";
    }
    type() {
        return new TupleType(this /* : TupleNode */.values /* : List<Value> */.query /* : () => Query<Value> */() /* : Query<Value> */.map /* : (arg0 : (arg0 : Value) => R) => Query<R> */(Value /* : Value */.type /* : unknown */) /* : Query<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */) /* : TupleType */;
    }
}
/* private */ class MapHead {
    constructor(head, mapper) {
        this /* : unknown */.head /* : unknown */ = head /* : unknown */;
        this /* : unknown */.mapper /* : unknown */ = mapper /* : unknown */;
    }
    next() {
        return this /* : MapHead */.head /* : Head<T> */.next /* : () => Option<T> */() /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(this /* : MapHead */.mapper /* : (arg0 : T) => R */) /* : Option<R> */;
    }
}
/* private */ class ZipHead {
    constructor(head, other) {
        this /* : unknown */.head /* : unknown */ = head /* : unknown */;
        this /* : unknown */.other /* : unknown */ = other /* : unknown */;
    }
    next() {
        return this /* : ZipHead */.head /* : Head<T> */.next /* : () => Option<T> */() /* : Option<T> */.and /* : (arg0 : () => Option<R>) => Option<[T, R]> */(this /* : ZipHead */.other /* : Query<R> */.next /* : unknown */) /* : Option<[T, R]> */;
    }
}
/* private */ class EnumValue /*  */ {
    constructor(value, values) {
        this /* : unknown */.value /* : unknown */ = value /* : unknown */;
        this /* : unknown */.values /* : unknown */ = values /* : unknown */;
    }
    generate() {
        let s = this /* : EnumValue */.values /* : List<Value> */.query /* : () => Query<Value> */() /* : Query<Value> */.map /* : (arg0 : (arg0 : Value) => R) => Query<R> */(Value /* : Value */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return this /* : EnumValue */.value /* : string */ + "(" + s + ")";
    }
}
/* private */ class EnumValues /*  */ {
    constructor(values) {
        this._IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.EnumValues /* : IncompleteClassSegmentVariant */;
        this /* : unknown */.values /* : unknown */ = values /* : unknown */;
    }
    generate() {
        return this /* : EnumValues */.values /* : List<EnumValue> */.query /* : () => Query<EnumValue> */() /* : Query<EnumValue> */.map /* : (arg0 : (arg0 : EnumValue) => R) => Query<R> */(EnumValue /* : EnumValue */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    maybeCreateDefinition() {
        return new None() /* : None */;
    }
}
/* public static */ class Strings /*  */ {
    static isBlank(input) {
        return input /* : string */.isBlank /* : unknown */() /* : unknown */;
    }
}
/* private */ class Primitive /*  */ {
    constructor(value) {
        this /* : Primitive */.value /* : string */ = value /* : string */;
    }
    generate() {
        return this /* : Primitive */.value /* : string */;
    }
    replace(mapping) {
        return this /* : Primitive */;
    }
    findName() {
        return this /* : Primitive */.name /* : unknown */() /* : unknown */;
    }
}
Primitive.Int = new Primitive("number") /* : Primitive */;
Primitive.String = new Primitive("string") /* : Primitive */;
Primitive.Boolean = new Primitive("boolean") /* : Primitive */;
Primitive.Unknown = new Primitive("unknown") /* : Primitive */;
Primitive.Void = new Primitive("void") /* : Primitive */;
/* private */ class BooleanValue /*  */ {
    constructor(value) {
        this._ValueVariant = ValueVariant.BooleanValue /* : ValueVariant */;
        this /* : BooleanValue */.value /* : string */ = value /* : string */;
    }
    generate() {
        return this /* : BooleanValue */.value /* : string */;
    }
    type() {
        return Primitive /* : Primitive */.Boolean /* : unknown */;
    }
}
BooleanValue.True = new BooleanValue("true") /* : BooleanValue */;
BooleanValue.False = new BooleanValue("false") /* : BooleanValue */;
/* public */ class Main /*  */ {
    static generatePlaceholder(input) {
        let replaced = input /* : string */.replace /* : unknown */("/*", "content-start") /* : unknown */.replace /* : unknown */("*/", "content-end") /* : unknown */;
        return "/* " + replaced + " */";
    }
    static joinValues(retainParameters) {
        let inner = retainParameters /* : List<Definition> */.query /* : () => Query<Definition> */() /* : Query<Definition> */.map /* : (arg0 : (arg0 : Definition) => R) => Query<R> */(Definition /* : Definition */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + inner + ")";
    }
    static createIndent(depth) {
        return "\n" + "\t".repeat /* : unknown */(depth /* : number */) /* : unknown */;
    }
    static createDebugString(type) {
        if (!Main /* : Main */.isDebugEnabled /* : unknown */) {
            return "";
        }
        return generatePlaceholder /* : (arg0 : string) => string */(": " + type /* : Type */.generate /* : unknown */() /* : unknown */) /* : string */;
    }
    static retainFindableType(type) {
        if (type /* : Type */._TypeVariant /* : unknown */ === TypeVariant.FindableType /* : unknown */) {
            let findableType = type /* : Type */;
            return new Some(findableType /* : FindableType */) /* : Some */;
        }
        return new None() /* : None */;
    }
    static isSymbol(input) {
        /* for (var i = 0; i < Strings.length(input); i++) */ {
            let c = input /* : string */.charAt /* : unknown */( /* i */) /* : unknown */;
            if ( /* Character */.isLetter /* : unknown */(c /* : unknown */) /* : unknown */ || /*  */ ( /* i != 0  */ && /* Character */ .isDigit /* : unknown */(c /* : unknown */) /* : unknown */) /* : unknown */) {
                /* continue */ ;
            }
            return false;
        }
        return true;
    }
    static parseWhitespace(input, state) {
        if (Strings /* : Strings */.isBlank /* : (arg0 : string) => boolean */(input /* : string */) /* : boolean */) {
            return new Some([state /* : CompileState */, new Whitespace() /* : Whitespace */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    main() {
        let parent = this /* : Main */.findRoot /* : () => Path */() /* : Path */;
        let source = parent /* : Path */.resolve /* : (arg0 : string) => Path */("Main.java") /* : Path */;
        let target = parent /* : Path */.resolve /* : (arg0 : string) => Path */("main.ts") /* : Path */;
        /* source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(error -> System.err.println(error.display())) */ ;
    }
    compile(input) {
        let state = CompileState /* : CompileState */.createInitial /* : () => CompileState */() /* : CompileState */;
        let parsed = this /* : Main */.parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state /* : CompileState */, input /* : string */, this /* : Main */.compileRootSegment /* : unknown */) /* : [CompileState, List<T>] */;
        let joined = parsed /* : [CompileState, List<T>] */[0 /* : number */].structures /* : unknown */.query /* : unknown */() /* : unknown */.collect /* : unknown */(Joiner /* : Joiner */.empty /* : () => Joiner */() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return joined /* : unknown */ + this /* : Main */.generateStatements /* : unknown */(parsed /* : [CompileState, List<T>] */[1 /* : number */]) /* : unknown */;
    }
    generateStatements(statements) {
        return this /* : Main */.generateAll /* : (arg0 : (arg0 : string, arg1 : string) => string, arg1 : List<string>) => string */(this /* : Main */.mergeStatements /* : unknown */, statements /* : List<string> */) /* : string */;
    }
    parseStatements(state, input, mapper) {
        return this /* : Main */.parseAllWithIndices /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, input /* : string */, this /* : Main */.foldStatementChar /* : unknown */, (state3, tuple) => new Some(mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(state3 /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : [CompileState, T] */) /* : Some */) /* : Option<[CompileState, List<T>]> */.orElseGet /* : (arg0 : () => [CompileState, List<T>]) => [CompileState, List<T>] */(() => [state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */]) /* : [CompileState, List<T>] */;
    }
    generateAll(merger, elements) {
        return elements /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : string) => R) => R */("", merger /* : (arg0 : string, arg1 : string) => string */) /* : R */;
    }
    parseAllWithIndices(state, input, folder, mapper) {
        let stringList = this /* : Main */.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input /* : string */, folder /* : (arg0 : DivideState, arg1 : string) => DivideState */) /* : List<string> */;
        return this /* : Main */.mapUsingState /* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(state /* : CompileState */, stringList /* : List<string> */, mapper /* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */) /* : Option<[CompileState, List<R>]> */;
    }
    mapUsingState(state, elements, mapper) {
        let initial = new Some([state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */]) /* : Some */;
        return elements /* : List<T> */.iterateWithIndices /* : () => Query<[number, T]> */() /* : Query<[number, T]> */.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : [number, T]) => R) => R */(initial /* : Option<[CompileState, List<R>]> */, (tuple, element) => {
            return tuple /* : unknown */.flatMap /* : unknown */((inner) => {
                let state1 = inner /* : unknown */.left /* : unknown */() /* : unknown */;
                let right = inner /* : unknown */.right /* : unknown */() /* : unknown */;
                return mapper /* : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]> */(state1 /* : unknown */, element /* : unknown */) /* : Option<[CompileState, R]> */.map /* : (arg0 : (arg0 : [CompileState, R]) => R) => Option<R> */((applied) => {
                    return [applied /* : [CompileState, R] */[0 /* : number */], right /* : unknown */.addLast /* : unknown */(applied /* : [CompileState, R] */[1 /* : number */]) /* : unknown */];
                }) /* : Option<R> */;
            }) /* : unknown */;
        }) /* : R */;
    }
    mergeStatements(cache, statement) {
        return cache /* : string */ + statement /* : string */;
    }
    divideAll(input, folder) {
        let current = DivideState /* : DivideState */.createInitial /* : (arg0 : string) => DivideState */(input /* : string */) /* : DivideState */;
        while (true) {
            let maybePopped = current /* : DivideState */.pop /* : unknown */() /* : unknown */.map /* : unknown */((tuple) => {
                return this /* : Main */.foldSingleQuotes /* : (arg0 : [string, DivideState]) => Option<DivideState> */(tuple /* : unknown */) /* : Option<DivideState> */.or /* : (arg0 : () => Option<DivideState>) => Option<DivideState> */(() => this /* : Main */.foldDoubleQuotes /* : (arg0 : [string, DivideState]) => Option<DivideState> */(tuple /* : unknown */) /* : Option<DivideState> */) /* : Option<DivideState> */.orElseGet /* : (arg0 : () => T) => T */(() => folder /* : (arg0 : DivideState, arg1 : string) => DivideState */(tuple /* : unknown */.right /* : unknown */() /* : unknown */, tuple /* : unknown */.left /* : unknown */() /* : unknown */) /* : DivideState */) /* : T */;
            }) /* : unknown */;
            if (maybePopped /* : unknown */.isPresent /* : unknown */() /* : unknown */) {
                current /* : DivideState */ = maybePopped /* : unknown */.orElse /* : unknown */(current /* : DivideState */) /* : unknown */;
            }
            else {
                /* break */ ;
            }
        }
        return current /* : DivideState */.advance /* : unknown */() /* : unknown */.segments /* : unknown */;
    }
    foldDoubleQuotes(tuple) {
        if (tuple /* : [string, DivideState] */[0 /* : number */] === "\"") {
            let current = tuple /* : [string, DivideState] */[1 /* : number */].append /* : unknown */(tuple /* : [string, DivideState] */[0 /* : number */]) /* : unknown */;
            while (true) {
                let maybePopped = current /* : unknown */.popAndAppendToTuple /* : unknown */() /* : unknown */;
                if (maybePopped /* : unknown */.isEmpty /* : unknown */() /* : unknown */) {
                    /* break */ ;
                }
                let popped = maybePopped /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */;
                current /* : unknown */ = popped /* : unknown */.right /* : unknown */() /* : unknown */;
                if (popped /* : unknown */.left /* : unknown */() /* : unknown */ === "\\") {
                    current /* : unknown */ = current /* : unknown */.popAndAppendToOption /* : unknown */() /* : unknown */.orElse /* : unknown */(current /* : unknown */) /* : unknown */;
                }
                if (popped /* : unknown */.left /* : unknown */() /* : unknown */ === "\"") {
                    /* break */ ;
                }
            }
            return new Some(current /* : unknown */) /* : Some */;
        }
        return new None() /* : None */;
    }
    foldSingleQuotes(tuple) {
        if ( /* tuple.left() != '\'' */) {
            return new None() /* : None */;
        }
        let appended = tuple /* : [string, DivideState] */[1 /* : number */].append /* : unknown */(tuple /* : [string, DivideState] */[0 /* : number */]) /* : unknown */;
        return appended /* : unknown */.popAndAppendToTuple /* : unknown */() /* : unknown */.map /* : unknown */(this /* : Main */.foldEscaped /* : unknown */) /* : unknown */.flatMap /* : unknown */(DivideState /* : DivideState */.popAndAppendToOption /* : unknown */) /* : unknown */;
    }
    foldEscaped(escaped) {
        if (escaped /* : [string, DivideState] */[0 /* : number */] === "\\") {
            return escaped /* : [string, DivideState] */[1 /* : number */].popAndAppendToOption /* : unknown */() /* : unknown */.orElse /* : unknown */(escaped /* : [string, DivideState] */[1 /* : number */]) /* : unknown */;
        }
        return escaped /* : [string, DivideState] */[1 /* : number */];
    }
    foldStatementChar(state, c) {
        let append = state /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
        if (c /* : string */ === ";" && append /* : DivideState */.isLevel /* : unknown */() /* : unknown */) {
            return append /* : DivideState */.advance /* : () => DivideState */() /* : DivideState */;
        }
        if (c /* : string */ === "}" && append /* : DivideState */.isShallow /* : unknown */() /* : unknown */) {
            return append /* : DivideState */.advance /* : () => DivideState */() /* : DivideState */.exit /* : unknown */() /* : unknown */;
        }
        if (c /* : string */ === "{" || c /* : string */ === "(") {
            return append /* : DivideState */.enter /* : unknown */() /* : unknown */;
        }
        if (c /* : string */ === "}" || c /* : string */ === ")") {
            return append /* : DivideState */.exit /* : unknown */() /* : unknown */;
        }
        return append /* : DivideState */;
    }
    compileRootSegment(state, input) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (stripped /* : unknown */.startsWith /* : unknown */("package ") /* : unknown */ || stripped /* : unknown */.startsWith /* : unknown */("import ") /* : unknown */) {
            return [state /* : CompileState */, ""];
        }
        return this /* : Main */.parseClass /* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(stripped /* : unknown */, state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegment]> */.flatMap /* : (arg0 : (arg0 : [CompileState, IncompleteClassSegment]) => Option<R>) => Option<R> */((tuple) => this /* : Main */.completeClassSegment /* : (arg0 : CompileState, arg1 : IncompleteClassSegment) => Option<[CompileState, ClassSegment]> */(tuple /* : [CompileState, IncompleteClassSegment] */[0 /* : number */], tuple /* : [CompileState, IncompleteClassSegment] */[1 /* : number */]) /* : Option<[CompileState, ClassSegment]> */) /* : Option<R> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */((tuple0) => [tuple0 /* : T */.left /* : unknown */() /* : unknown */, tuple0 /* : T */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */]) /* : Option<R> */.orElseGet /* : unknown */(() => [state /* : CompileState */, generatePlaceholder /* : (arg0 : string) => string */(stripped /* : unknown */) /* : string */]) /* : unknown */;
    }
    parseClass(stripped, state) {
        return this /* : Main */.parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(stripped /* : string */, "class ", "class ", state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegment]> */;
    }
    parseStructure(stripped, sourceInfix, targetInfix, state) {
        return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(stripped /* : string */, sourceInfix /* : string */, (beforeInfix, right) => {
            return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(right /* : unknown */, "{", (beforeContent, withEnd) => {
                return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withEnd /* : unknown */.strip /* : unknown */() /* : unknown */, "}", (content1) => {
                    return this /* : Main */.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeInfix /* : unknown */.strip /* : unknown */() /* : unknown */, "\n", (annotationsString, s2) => {
                        let annotations = this /* : Main */.parseAnnotations /* : (arg0 : string) => List<string> */(annotationsString /* : unknown */) /* : List<string> */;
                        return this /* : Main */.parseStructureWithMaybePermits /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix /* : string */, state /* : CompileState */, s2 /* : unknown */, beforeContent /* : unknown */, content1 /* : string */, annotations /* : List<string> */) /* : Option<[CompileState, IncompleteClassSegment]> */;
                    }) /* : Option<T> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => {
                        return this /* : Main */.parseStructureWithMaybePermits /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : string */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Option<[CompileState, IncompleteClassSegment]> */;
                    }) /* : Option<T> */;
                }) /* : Option<T> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    parseAnnotations(annotationsString) {
        return this /* : Main */.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(annotationsString /* : string */.strip /* : unknown */() /* : unknown */, (state1, c) => this /* : Main */.foldByDelimiter /* : (arg0 : DivideState, arg1 : string, arg2 : string) => DivideState */(state1 /* : unknown */, c /* : unknown */, "\n") /* : DivideState */) /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.map /* : (arg0 : (arg0 : string) => R) => Query<R> */(strip /* : unknown */) /* : Query<R> */.filter /* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value) => !value /* : T */.isEmpty /* : unknown */() /* : unknown */) /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */((value) => value /* : T */.substring /* : unknown */(1 /* : number */) /* : unknown */) /* : Option<R> */.map /* : unknown */(strip /* : unknown */) /* : unknown */.filter /* : unknown */((value) => !value /* : T */.isEmpty /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    foldByDelimiter(state1, c, delimiter) {
        if (c /* : string */ === delimiter /* : string */) {
            return state1 /* : DivideState */.advance /* : () => DivideState */() /* : DivideState */;
        }
        return state1 /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    }
    parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, annotations) {
        return this /* : Main */.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent /* : string */, " permits ", (s, s2) => {
            let variants = this /* : Main */.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(s2 /* : unknown */, this /* : Main */.foldValueChar /* : unknown */) /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.map /* : (arg0 : (arg0 : string) => R) => Query<R> */(strip /* : unknown */) /* : Query<R> */.filter /* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value) => !value /* : T */.isEmpty /* : unknown */() /* : unknown */) /* : Option<T> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
            return this /* : Main */.parseStructureWithMaybeImplements /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, s /* : unknown */, content1 /* : string */, variants /* : unknown */, annotations /* : List<string> */) /* : Option<[CompileState, IncompleteClassSegment]> */;
        }) /* : Option<T> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => this /* : Main */.parseStructureWithMaybeImplements /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, annotations /* : List<string> */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<T> */;
    }
    parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations) {
        return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent /* : string */, " implements ", (s, s2) => {
            let stringList = this /* : Main */.parseTypeRefs /* : (arg0 : string) => List<TypeRef> */(s2 /* : unknown */) /* : List<TypeRef> */;
            return this /* : Main */.parseStructureWithMaybeExtends /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, s /* : unknown */, content1 /* : string */, variants /* : List<string> */, annotations /* : List<string> */, stringList /* : List<TypeRef> */) /* : Option<[CompileState, IncompleteClassSegment]> */;
        }) /* : Option<T> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => this /* : Main */.parseStructureWithMaybeExtends /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */, variants /* : List<string> */, annotations /* : List<string> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<T> */;
    }
    parseTypeRefs(s2) {
        return this /* : Main */.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(s2 /* : string */, this /* : Main */.foldValueChar /* : unknown */) /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.map /* : (arg0 : (arg0 : string) => R) => Query<R> */(strip /* : unknown */) /* : Query<R> */.filter /* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value) => !value /* : T */.isEmpty /* : unknown */() /* : unknown */) /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(TypeRef /* : TypeRef */.new /* : unknown */) /* : Option<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, interfaces) {
        return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent /* : string */, " extends ", (s, s2) => this /* : Main */.parseStructureWithMaybeParams /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<TypeRef>, arg8 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, s /* : unknown */, content1 /* : string */, variants /* : List<string> */, annotations /* : List<string> */, this /* : Main */.parseTypeRefs /* : (arg0 : string) => List<TypeRef> */(s2 /* : unknown */) /* : List<TypeRef> */, interfaces /* : List<TypeRef> */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<T> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => this /* : Main */.parseStructureWithMaybeParams /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<TypeRef>, arg8 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */, variants /* : List<string> */, annotations /* : List<string> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, interfaces /* : List<TypeRef> */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<T> */;
    }
    parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, superTypes, interfaces) {
        return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeContent /* : string */.strip /* : unknown */() /* : unknown */, ")", (s) => {
            return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(s /* : string */, "(", (s1, s2) => {
                let parsed = this /* : Main */.parseParameters /* : (arg0 : CompileState, arg1 : string) => [CompileState, List<Parameter>] */(state /* : CompileState */, s2 /* : unknown */) /* : [CompileState, List<Parameter>] */;
                return this /* : Main */.parseStructureWithMaybeTypeParams /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<Parameter>, arg6 : List<string>, arg7 : List<string>, arg8 : List<TypeRef>, arg9 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix /* : string */, parsed /* : [CompileState, List<Parameter>] */[0 /* : number */], beforeInfix /* : string */, s1 /* : unknown */, content1 /* : string */, parsed /* : [CompileState, List<Parameter>] */[1 /* : number */], variants /* : List<string> */, annotations /* : List<string> */, interfaces /* : List<TypeRef> */, superTypes /* : List<TypeRef> */) /* : Option<[CompileState, IncompleteClassSegment]> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => {
            return this /* : Main */.parseStructureWithMaybeTypeParams /* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<Parameter>, arg6 : List<string>, arg7 : List<string>, arg8 : List<TypeRef>, arg9 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, variants /* : List<string> */, annotations /* : List<string> */, interfaces /* : List<TypeRef> */, superTypes /* : List<TypeRef> */) /* : Option<[CompileState, IncompleteClassSegment]> */;
        }) /* : Option<T> */;
    }
    parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, params, variants, annotations, interfaces, maybeSuperType) {
        return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent /* : string */, "<", (name, withTypeParams) => {
            return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withTypeParams /* : unknown */, ">", (typeParamsString, afterTypeParams) => {
                let readonly, mapper = (state1, s) => [state1 /* : unknown */, s /* : unknown */.strip /* : unknown */() /* : unknown */];
                let typeParams = this /* : Main */.parseValuesOrEmpty /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state /* : CompileState */, typeParamsString /* : unknown */, (state1, s) => new Some(mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */(state1 /* : unknown */, s /* : unknown */) /* : [CompileState, string] */) /* : Some */) /* : [CompileState, List<T>] */;
                return this /* : Main */.assembleStructure /* : (arg0 : CompileState, arg1 : string, arg2 : List<string>, arg3 : string, arg4 : string, arg5 : string, arg6 : List<string>, arg7 : string, arg8 : List<Parameter>, arg9 : List<string>, arg10 : List<TypeRef>, arg11 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(typeParams /* : [CompileState, List<T>] */[0 /* : number */], targetInfix /* : string */, annotations /* : List<string> */, beforeInfix /* : string */, name /* : unknown */, content1 /* : string */, typeParams /* : [CompileState, List<T>] */[1 /* : number */], afterTypeParams /* : unknown */, params /* : List<Parameter> */, variants /* : List<string> */, interfaces /* : List<TypeRef> */, maybeSuperType /* : List<TypeRef> */) /* : Option<[CompileState, IncompleteClassSegment]> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => {
            return this /* : Main */.assembleStructure /* : (arg0 : CompileState, arg1 : string, arg2 : List<string>, arg3 : string, arg4 : string, arg5 : string, arg6 : List<string>, arg7 : string, arg8 : List<Parameter>, arg9 : List<string>, arg10 : List<TypeRef>, arg11 : List<TypeRef>) => Option<[CompileState, IncompleteClassSegment]> */(state /* : CompileState */, targetInfix /* : string */, annotations /* : List<string> */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, "", params /* : List<Parameter> */, variants /* : List<string> */, interfaces /* : List<TypeRef> */, maybeSuperType /* : List<TypeRef> */) /* : Option<[CompileState, IncompleteClassSegment]> */;
        }) /* : Option<T> */;
    }
    assembleStructure(state, targetInfix, annotations, beforeInfix, rawName, content, typeParams, after, rawParameters, variants, interfaces, maybeSuperType) {
        let name = rawName /* : string */.strip /* : unknown */() /* : unknown */;
        if (!isSymbol /* : (arg0 : string) => boolean */(name /* : unknown */) /* : unknown */) {
            return new None() /* : None */;
        }
        if (annotations /* : List<string> */.contains /* : (arg0 : string) => boolean */("Actual") /* : boolean */) {
            return new Some([state /* : CompileState */, new Whitespace() /* : Whitespace */]) /* : Some */;
        }
        let segmentsTuple = this /* : Main */.parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state /* : CompileState */.pushStructName /* : (arg0 : [string, List<string>]) => CompileState */([name /* : unknown */, variants /* : List<string> */]) /* : CompileState */.withTypeParams /* : unknown */(typeParams /* : List<string> */) /* : unknown */, content /* : string */, (state0, input) => this /* : Main */.parseClassSegment /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, IncompleteClassSegment] */(state0 /* : unknown */, input /* : unknown */, 1 /* : number */) /* : [CompileState, IncompleteClassSegment] */) /* : [CompileState, List<T>] */;
        let segmentsState = segmentsTuple /* : [CompileState, List<T>] */[0 /* : number */];
        let segments = segmentsTuple /* : [CompileState, List<T>] */[1 /* : number */];
        let parameters = this /* : Main */.retainDefinitions /* : (arg0 : List<Parameter>) => List<Definition> */(rawParameters /* : List<Parameter> */) /* : List<Definition> */;
        let prototype = new StructurePrototype(targetInfix /* : string */, beforeInfix /* : string */, name /* : unknown */, typeParams /* : List<string> */, parameters /* : List<Definition> */, after /* : string */, segments /* : unknown */, variants /* : List<string> */, interfaces /* : List<TypeRef> */, maybeSuperType /* : List<TypeRef> */) /* : StructurePrototype */;
        return new Some([segmentsState /* : unknown */.addType /* : unknown */(prototype /* : StructurePrototype */.createObjectType /* : () => ObjectType */() /* : ObjectType */) /* : unknown */, prototype /* : StructurePrototype */]) /* : Some */;
    }
    completeStructure(state, prototype) {
        let thisType = prototype /* : StructurePrototype */.createObjectType /* : () => ObjectType */() /* : ObjectType */;
        let withThis = state /* : CompileState */.enterDefinitions /* : () => CompileState */() /* : CompileState */.define /* : (arg0 : Definition) => CompileState */(ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */("this", thisType /* : ObjectType */) /* : Definition */) /* : CompileState */;
        return this /* : Main */.mapUsingState /* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(withThis /* : CompileState */, prototype /* : StructurePrototype */.interfaces /* : List<TypeRef> */, (state2, tuple) => this /* : Main */.parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state2 /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */.value /* : unknown */) /* : Option<[CompileState, Type]> */) /* : Option<[CompileState, List<R>]> */.flatMap /* : (arg0 : (arg0 : [CompileState, List<R>]) => Option<R>) => Option<R> */((interfacesTypes) => {
            let interfaces = interfacesTypes /* : [CompileState, List<R>] */[1 /* : number */];
            let bases = this /* : Main */.resolveBaseTypes /* : (arg0 : List<Type>) => List<BaseType> */(interfaces /* : unknown */) /* : List<BaseType> */;
            let variantsSuper = this /* : Main */.findSuperTypesOfVariants /* : (arg0 : List<BaseType>, arg1 : string) => List<string> */(bases /* : List<BaseType> */, prototype /* : StructurePrototype */.name /* : string */) /* : List<string> */;
            return this /* : Main */.mapUsingState /* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(interfacesTypes /* : [CompileState, List<R>] */[0 /* : number */], prototype /* : StructurePrototype */.segments /* : List<IncompleteClassSegment> */() /* : unknown */, (state1, entry) => this /* : Main */.completeClassSegment /* : (arg0 : CompileState, arg1 : IncompleteClassSegment) => Option<[CompileState, ClassSegment]> */(state1 /* : unknown */, entry /* : unknown */.right /* : unknown */() /* : unknown */) /* : Option<[CompileState, ClassSegment]> */) /* : Option<[CompileState, List<R>]> */.map /* : (arg0 : (arg0 : [CompileState, List<R>]) => R) => Option<R> */((oldStatementsTuple) => {
                let exited = oldStatementsTuple /* : [CompileState, List<R>] */[0 /* : number */].exitDefinitions /* : unknown */() /* : unknown */;
                let oldStatements = oldStatementsTuple /* : [CompileState, List<R>] */[1 /* : number */];
                let withEnumCategoriesDefined = this /* : Main */.defineEnumCategories /* : (arg0 : CompileState, arg1 : List<ClassSegment>, arg2 : string, arg3 : List<string>, arg4 : string) => [CompileState, List<ClassSegment>] */(exited /* : unknown */, oldStatements /* : unknown */, prototype /* : StructurePrototype */.name /* : string */, prototype /* : StructurePrototype */.variants /* : List<string> */, prototype /* : StructurePrototype */.generateToEnum /* : () => string */() /* : string */) /* : [CompileState, List<ClassSegment>] */;
                let withEnumCategoriesImplemented = this /* : Main */.implementEnumCategories /* : (arg0 : string, arg1 : List<string>, arg2 : List<ClassSegment>) => List<ClassSegment> */(prototype /* : StructurePrototype */.name /* : string */, variantsSuper /* : List<string> */, withEnumCategoriesDefined /* : [CompileState, List<ClassSegment>] */[1 /* : number */]) /* : List<ClassSegment> */;
                let withEnumValues = this /* : Main */.implementEnumValues /* : (arg0 : List<ClassSegment>, arg1 : ObjectType) => List<ClassSegment> */(withEnumCategoriesImplemented /* : List<ClassSegment> */, thisType /* : ObjectType */) /* : List<ClassSegment> */;
                let withConstructor = this /* : Main */.defineConstructor /* : (arg0 : List<ClassSegment>, arg1 : List<Definition>) => List<ClassSegment> */(withEnumValues /* : List<ClassSegment> */, prototype /* : StructurePrototype */.parameters /* : List<Definition> */() /* : unknown */) /* : List<ClassSegment> */;
                let generatedSegments = this /* : Main */.joinSegments /* : (arg0 : List<ClassSegment>) => string */(withConstructor /* : List<ClassSegment> */) /* : string */;
                let joinedTypeParams = prototype /* : StructurePrototype */.joinTypeParams /* : () => string */() /* : string */;
                let interfacesJoined = this /* : Main */.joinInterfaces /* : (arg0 : List<Type>) => string */(interfaces /* : unknown */) /* : string */;
                let generatedSuperType = this /* : Main */.joinSuperTypes /* : (arg0 : CompileState, arg1 : StructurePrototype) => string */(state /* : CompileState */, prototype /* : StructurePrototype */) /* : string */;
                let generated = generatePlaceholder /* : (arg0 : string) => string */(prototype /* : StructurePrototype */.beforeInfix /* : string */() /* : unknown */.strip /* : unknown */() /* : unknown */) /* : string */ + prototype /* : StructurePrototype */.targetInfix /* : string */() /* : unknown */ + prototype /* : StructurePrototype */.name /* : string */() /* : unknown */ + joinedTypeParams /* : string */ + generatePlaceholder /* : (arg0 : string) => string */(prototype /* : StructurePrototype */.after /* : string */() /* : unknown */) /* : string */ + generatedSuperType /* : string */ + interfacesJoined /* : string */ + " {" + generatedSegments + "\n}\n";
                let compileState = withEnumCategoriesDefined /* : [CompileState, List<ClassSegment>] */[0 /* : number */].popStructName /* : unknown */() /* : unknown */;
                let definedState = compileState /* : unknown */.addStructure /* : unknown */(generated /* : unknown */) /* : unknown */;
                return [definedState /* : unknown */, new Whitespace() /* : Whitespace */];
            }) /* : Option<R> */;
        }) /* : Option<R> */;
    }
    joinSuperTypes(state, prototype) {
        return prototype /* : StructurePrototype */.superTypes /* : List<TypeRef> */.query /* : () => Query<TypeRef> */() /* : Query<TypeRef> */.map /* : (arg0 : (arg0 : TypeRef) => R) => Query<R> */((value) => state /* : CompileState */.resolveType /* : (arg0 : string) => Option<Type> */(value /* : TypeRef */.value /* : unknown */) /* : Option<Type> */) /* : Query<R> */.flatMap /* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries /* : Queries */.fromOption /* : unknown */) /* : Option<R> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(Type /* : Type */.generate /* : unknown */) /* : Option<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((generated) => " extends " + generated /* : unknown */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    implementEnumValues(withConstructor, thisType) {
        return withConstructor /* : List<ClassSegment> */.query /* : () => Query<ClassSegment> */() /* : Query<ClassSegment> */.flatMap /* : (arg0 : (arg0 : ClassSegment) => Query<R>) => Query<R> */((segment) => this /* : Main */.flattenEnumValues /* : (arg0 : ClassSegment, arg1 : ObjectType) => Query<ClassSegment> */(segment /* : ClassSegment */, thisType /* : ObjectType */) /* : Query<ClassSegment> */) /* : Query<R> */.collect /* : (arg0 : Collector<T, R>) => R */(new ListCollector() /* : ListCollector */) /* : R */;
    }
    defineEnumCategories(state, segments, name, variants, enumGenerated) {
        if (variants /* : List<string> */.isEmpty /* : () => boolean */() /* : boolean */) {
            return [state /* : CompileState */, segments /* : List<ClassSegment> */];
        }
        let enumState = state /* : CompileState */.addStructure /* : (arg0 : string) => CompileState */(enumGenerated /* : string */) /* : CompileState */;
        let enumType = new ObjectType(name /* : string */ + "Variant", Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, variants /* : List<string> */) /* : ObjectType */;
        let enumDefinition = this /* : Main */.createVariantDefinition /* : (arg0 : ObjectType) => Definition */(enumType /* : ObjectType */) /* : Definition */;
        return [enumState /* : CompileState */, segments /* : List<ClassSegment> */.addFirst /* : (arg0 : ClassSegment) => List<ClassSegment> */(new Statement(1 /* : number */, enumDefinition /* : Definition */) /* : Statement */) /* : List<ClassSegment> */];
    }
    implementEnumCategories(name, variantsBases, oldStatements) {
        return variantsBases /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : string) => R) => R */(oldStatements /* : List<ClassSegment> */, (classSegmentList, superType) => {
            let variantTypeName = superType /* : unknown */ + "Variant";
            let variantType = new ObjectType(variantTypeName /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : ObjectType */;
            let definition = this /* : Main */.createVariantDefinition /* : (arg0 : ObjectType) => Definition */(variantType /* : ObjectType */) /* : Definition */;
            let source = new SymbolValue(variantTypeName /* : unknown */ + "." + name /* : string */, variantType /* : ObjectType */) /* : SymbolValue */;
            let initialization = new FieldInitialization(definition /* : Definition */, source /* : SymbolValue */) /* : FieldInitialization */;
            return classSegmentList /* : unknown */.addFirst /* : unknown */(new Statement(1 /* : number */, initialization /* : FieldInitialization */) /* : Statement */) /* : unknown */;
        }) /* : R */;
    }
    findSuperTypesOfVariants(bases, name) {
        return bases /* : List<BaseType> */.query /* : () => Query<BaseType> */() /* : Query<BaseType> */.filter /* : (arg0 : (arg0 : BaseType) => boolean) => Query<BaseType> */((type) => type /* : BaseType */.hasVariant /* : (arg0 : string) => boolean */(name /* : string */) /* : boolean */) /* : Query<BaseType> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(BaseType /* : BaseType */.findName /* : unknown */) /* : Option<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    resolveBaseTypes(interfaces) {
        return interfaces /* : List<Type> */.query /* : () => Query<Type> */() /* : Query<Type> */.map /* : (arg0 : (arg0 : Type) => R) => Query<R> */(Main /* : Main */.retainFindableType /* : unknown */) /* : Query<R> */.flatMap /* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries /* : Queries */.fromOption /* : unknown */) /* : Option<R> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(FindableType /* : FindableType */.findBase /* : unknown */) /* : Option<R> */.flatMap /* : unknown */(Queries /* : Queries */.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    joinSegments(segmentsWithMaybeConstructor) {
        return segmentsWithMaybeConstructor /* : List<ClassSegment> */.query /* : () => Query<ClassSegment> */() /* : Query<ClassSegment> */.map /* : (arg0 : (arg0 : ClassSegment) => R) => Query<R> */(ClassSegment /* : ClassSegment */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(Joiner /* : Joiner */.empty /* : () => Joiner */() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    joinInterfaces(interfaces) {
        return interfaces /* : List<Type> */.query /* : () => Query<Type> */() /* : Query<Type> */.map /* : (arg0 : (arg0 : Type) => R) => Query<R> */(Type /* : Type */.generate /* : unknown */) /* : Query<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => " implements " + inner /* : unknown */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    flattenEnumValues(segment, thisType) {
        if (segment /* : ClassSegment */._ClassSegmentVariant /* : unknown */ === ClassSegmentVariant.EnumValues /* : unknown */) {
            let enumValues = segment /* : ClassSegment */;
            return enumValues /* : EnumValues */.values /* : List<EnumValue> */.query /* : () => Query<EnumValue> */() /* : Query<EnumValue> */.map /* : (arg0 : (arg0 : EnumValue) => R) => Query<R> */((enumValue) => {
                let definition = new ImmutableDefinition(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */("static") /* : List<T> */, enumValue /* : EnumValue */.value /* : unknown */, thisType /* : ObjectType */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : ImmutableDefinition */;
                return new Statement(1 /* : number */, new FieldInitialization(definition /* : ImmutableDefinition */, new Invokable(new ConstructionCaller(thisType /* : ObjectType */) /* : ConstructionCaller */, enumValue /* : EnumValue */.values /* : unknown */, thisType /* : ObjectType */) /* : Invokable */) /* : FieldInitialization */) /* : Statement */;
            }) /* : Query<R> */;
        }
        return Queries /* : Queries */.from /* : (arg0 : T[]) => Query<T> */(segment /* : ClassSegment */) /* : Query<T> */;
    }
    createVariantDefinition(type) {
        return ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */("_" + type /* : ObjectType */.name /* : unknown */, type /* : ObjectType */) /* : Definition */;
    }
    defineConstructor(segments, parameters) {
        if (parameters /* : List<Definition> */.isEmpty /* : () => boolean */() /* : boolean */) {
            return segments /* : List<ClassSegment> */;
        }
        let definitions = parameters /* : List<Definition> */.query /* : () => Query<Definition> */() /* : Query<Definition> */. /* : unknown */ < /* ClassSegment>map */ ((definition) => new Statement(1 /* : number */, definition /* : unknown */) /* : Statement */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        let collect = /* parameters.query()
                .map(definition  */ -( /* destination */,
        /*  new SymbolValue(definition.findName(), Primitive.Unknown));
    } */) /* : unknown */. /* : unknown */ < /* FunctionSegment>map */ ((assignment) => new Statement(2 /* : number */, assignment /* : unknown */) /* : Statement */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        let func = new FunctionNode(1 /* : number */, new ConstructorHeader() /* : ConstructorHeader */, parameters /* : List<Definition> */, new Some(collect /* : unknown */) /* : Some */) /* : FunctionNode */;
        return segments /* : List<ClassSegment> */.addFirst /* : (arg0 : ClassSegment) => List<ClassSegment> */(func /* : FunctionNode */) /* : List<ClassSegment> */.addAllFirst /* : unknown */(definitions /* : List<ClassSegment> */) /* : unknown */;
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
        let definition = classInitialization /* : ClassInitialization */.definition /* : Definition */;
        let statement = new Statement(classInitialization /* : ClassInitialization */.depth /* : number */, new FieldInitialization(definition /* : Definition */, classInitialization /* : ClassInitialization */.value /* : Value */) /* : FieldInitialization */) /* : Statement */;
        return new Some([state1 /* : CompileState */, statement /* : Statement */]) /* : Some */;
    }
    completeDefinition(state1, classDefinition) {
        let definition = classDefinition /* : ClassDefinition */.definition /* : Definition */;
        let statement = new Statement(classDefinition /* : ClassDefinition */.depth /* : number */, definition /* : Definition */) /* : Statement */;
        return new Some([state1 /* : CompileState */, statement /* : Statement */]) /* : Some */;
    }
    retainDefinition(parameter) {
        if (parameter /* : Parameter */._ParameterVariant /* : unknown */ === ParameterVariant.Definition /* : unknown */) {
            let definition = parameter /* : Parameter */;
            return new Some(definition /* : Definition */) /* : Some */;
        }
        return new None() /* : None */;
    }
    prefix(input, prefix, mapper) {
        if (!input /* : string */.startsWith /* : unknown */(prefix /* : string */) /* : unknown */) {
            return new None() /* : None */;
        }
        let slice = input /* : string */.substring /* : unknown */(Strings /* : Strings */.length /* : (arg0 : string) => number */(prefix /* : string */) /* : number */) /* : unknown */;
        return mapper /* : (arg0 : string) => Option<T> */(slice /* : unknown */) /* : Option<T> */;
    }
    suffix(input, suffix, mapper) {
        if (!input /* : string */.endsWith /* : unknown */(suffix /* : string */) /* : unknown */) {
            return new None() /* : None */;
        }
        let slice = input /* : string */.substring /* : unknown */(0 /* : number */, Strings /* : Strings */.length /* : (arg0 : string) => number */(input /* : string */) /* : number */ - Strings /* : Strings */.length /* : unknown */(suffix /* : string */) /* : unknown */) /* : unknown */;
        return mapper /* : (arg0 : string) => Option<T> */(slice /* : unknown */) /* : Option<T> */;
    }
    parseClassSegment(state, input, depth) {
        return this /* : Main */. /* : unknown */ < /* Whitespace, IncompleteClassSegment>typed */ (() => parseWhitespace /* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, Whitespace]> */(input /* : string */, state /* : CompileState */) /* : Option<[CompileState, Whitespace]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.typed /* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this /* : Main */.parseClass /* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input /* : string */, state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<[CompileState, S]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.typed /* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this /* : Main */.parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input /* : string */, "interface ", "interface ", state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<[CompileState, S]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.typed /* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this /* : Main */.parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input /* : string */, "record ", "class ", state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<[CompileState, S]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.typed /* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this /* : Main */.parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input /* : string */, "enum ", "class ", state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<[CompileState, S]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.typed /* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this /* : Main */.parseField /* : (arg0 : string, arg1 : number, arg2 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input /* : string */, depth /* : number */, state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : Option<[CompileState, S]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseMethod /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, IncompleteClassSegment]> */(state /* : CompileState */, input /* : string */, depth /* : number */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseEnumValues /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, IncompleteClassSegment]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : unknown */.orElseGet /* : unknown */(() => [state /* : CompileState */, new Placeholder(input /* : string */) /* : Placeholder */]) /* : unknown */;
    }
    parseEnumValues(state, input) {
        return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, ";", (withoutEnd) => {
            return this /* : Main */.parseValues /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, withoutEnd /* : string */, (state2, enumValue) => {
                return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(enumValue /* : unknown */.strip /* : unknown */() /* : unknown */, ")", (withoutValueEnd) => {
                    return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutValueEnd /* : string */, "(", (s4, s2) => {
                        return this /* : Main */.parseValues /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state2 /* : unknown */, s2 /* : unknown */, (state1, s1) => new Some(Main /* : Main */.this /* : unknown */.parseArgument /* : unknown */(state1 /* : unknown */, s1 /* : unknown */, 1 /* : number */) /* : unknown */) /* : Some */) /* : Option<[CompileState, List<T>]> */.map /* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((arguments) => {
                            return [arguments /* : [CompileState, List<T>] */[0 /* : number */], new EnumValue(s4 /* : unknown */, Main /* : Main */.this /* : unknown */.retainValues /* : unknown */(arguments /* : [CompileState, List<T>] */[1 /* : number */]) /* : unknown */) /* : EnumValue */];
                        }) /* : Option<R> */;
                    }) /* : Option<T> */;
                }) /* : Option<T> */;
            }) /* : Option<[CompileState, List<T>]> */.map /* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((tuple) => {
                return [tuple /* : [CompileState, List<T>] */[0 /* : number */], new EnumValues(tuple /* : [CompileState, List<T>] */[1 /* : number */]) /* : EnumValues */];
            }) /* : Option<R> */;
        }) /* : Option<T> */;
    }
    typed(action) {
        return action /* : () => Option<[CompileState, T]> */() /* : Option<[CompileState, T]> */.map /* : (arg0 : (arg0 : [CompileState, T]) => R) => Option<R> */((tuple) => [tuple /* : [CompileState, T] */[0 /* : number */], tuple /* : [CompileState, T] */[1 /* : number */]]) /* : Option<R> */;
    }
    parseMethod(state, input, depth) {
        return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input /* : string */, "(", (definitionString, withParams) => {
            return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withParams /* : unknown */, ")", (parametersString, rawContent) => {
                return this /* : Main */.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state /* : CompileState */, definitionString /* : unknown */) /* : Option<[CompileState, Definition]> */. /* : unknown */ < Tuple2 /* : Tuple2 */ < /* CompileState, Header>>map */ ((tuple) => [tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */]) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseConstructor /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Header]> */(state /* : CompileState */, definitionString /* : unknown */) /* : Option<[CompileState, Header]> */) /* : unknown */.flatMap /* : unknown */((definitionTuple) => this /* : Main */.assembleMethod /* : (arg0 : number, arg1 : string, arg2 : string, arg3 : [CompileState, Header]) => Option<[CompileState, IncompleteClassSegment]> */(depth /* : number */, parametersString /* : unknown */, rawContent /* : unknown */, definitionTuple /* : unknown */) /* : Option<[CompileState, IncompleteClassSegment]> */) /* : unknown */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    assembleMethod(depth, parametersString, rawContent, definitionTuple) {
        let definitionState = definitionTuple /* : [CompileState, Header] */[0 /* : number */];
        let header = definitionTuple /* : [CompileState, Header] */[1 /* : number */];
        let parametersTuple = this /* : Main */.parseParameters /* : (arg0 : CompileState, arg1 : string) => [CompileState, List<Parameter>] */(definitionState /* : unknown */, parametersString /* : string */) /* : [CompileState, List<Parameter>] */;
        let rawParameters = parametersTuple /* : [CompileState, List<Parameter>] */[1 /* : number */];
        let parameters = this /* : Main */.retainDefinitions /* : (arg0 : List<Parameter>) => List<Definition> */(rawParameters /* : unknown */) /* : List<Definition> */;
        let prototype = new MethodPrototype(depth /* : number */, header /* : unknown */, parameters /* : List<Definition> */, rawContent /* : string */.strip /* : unknown */() /* : unknown */) /* : MethodPrototype */;
        return new Some([parametersTuple /* : [CompileState, List<Parameter>] */[0 /* : number */].define /* : unknown */(prototype /* : MethodPrototype */.createDefinition /* : () => Definition */() /* : Definition */) /* : unknown */, prototype /* : MethodPrototype */]) /* : Some */;
    }
    completeMethod(state, prototype) {
        let definition = prototype /* : MethodPrototype */.createDefinition /* : () => Definition */() /* : Definition */;
        let oldHeader = prototype /* : MethodPrototype */.header /* : Header */() /* : unknown */;
        /* Header newHeader */ ;
        if (oldHeader /* : unknown */._UnknownVariant /* : unknown */ === UnknownVariant.Definition /* : unknown */) {
            let maybeDefinition = oldHeader /* : unknown */;
            maybeDefinition /* : Definition */.removeAnnotations /* : () => Definition */() /* : Definition */;
        }
        else {
            oldHeader /* : unknown */;
        }
        if (prototype /* : MethodPrototype */.content /* : string */() /* : unknown */ === ";" || definition /* : Definition */.containsAnnotation /* : unknown */("Actual") /* : unknown */) {
            return new Some([state /* : CompileState */.define /* : (arg0 : Definition) => CompileState */(definition /* : Definition */) /* : CompileState */, new FunctionNode(prototype /* : MethodPrototype */.depth /* : number */() /* : unknown */) /* newHeader */, prototype /* : MethodPrototype */.parameters /* : List<Definition> */() /* : unknown */, new None()] /* : None */); /* : FunctionNode */
            ;
        }
        if (prototype /* : MethodPrototype */.content /* : string */() /* : unknown */.startsWith /* : unknown */("{") /* : unknown */ && prototype /* : MethodPrototype */.content /* : unknown */() /* : unknown */.endsWith /* : unknown */("}") /* : unknown */) {
            let substring = prototype /* : MethodPrototype */.content /* : string */() /* : unknown */.substring /* : unknown */(1 /* : number */, Strings /* : Strings */.length /* : (arg0 : string) => number */(prototype /* : MethodPrototype */.content /* : string */() /* : unknown */) /* : number */ - 1 /* : number */) /* : unknown */;
            let withDefined = state /* : CompileState */.enterDefinitions /* : () => CompileState */() /* : CompileState */.defineAll /* : (arg0 : List<Definition>) => CompileState */(prototype /* : MethodPrototype */.parameters /* : List<Definition> */() /* : unknown */) /* : CompileState */;
            let statementsTuple = this /* : Main */.parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(withDefined /* : CompileState */, substring /* : unknown */, (state1, input1) => this /* : Main */.parseFunctionSegment /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1 /* : unknown */, input1 /* : unknown */, prototype /* : MethodPrototype */.depth /* : number */() /* : unknown */ + 1 /* : number */) /* : [CompileState, FunctionSegment] */) /* : [CompileState, List<T>] */;
            let statements = statementsTuple /* : [CompileState, List<T>] */[1 /* : number */];
            return new Some([statementsTuple /* : [CompileState, List<T>] */[0 /* : number */].exitDefinitions /* : unknown */() /* : unknown */.define /* : unknown */(definition /* : Definition */) /* : unknown */, new FunctionNode(prototype /* : MethodPrototype */.depth /* : number */() /* : unknown */) /* newHeader */, prototype /* : MethodPrototype */.parameters /* : List<Definition> */() /* : unknown */, new Some(statements /* : unknown */)] /* : Some */); /* : FunctionNode */
            ;
        }
        return new None() /* : None */;
    }
    parseConstructor(state, input) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (state /* : CompileState */.isCurrentStructName /* : (arg0 : string) => boolean */(stripped /* : unknown */) /* : boolean */) {
            return new Some([state /* : CompileState */, new ConstructorHeader() /* : ConstructorHeader */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    retainDefinitions(right) {
        return right /* : List<Parameter> */.query /* : () => Query<Parameter> */() /* : Query<Parameter> */.map /* : (arg0 : (arg0 : Parameter) => R) => Query<R> */(this /* : Main */.retainDefinition /* : unknown */) /* : Query<R> */.flatMap /* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries /* : Queries */.fromOption /* : unknown */) /* : Option<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    parseParameters(state, params) {
        return this /* : Main */.parseValuesOrEmpty /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state /* : CompileState */, params /* : string */, (state1, s) => new Some(this /* : Main */.parseParameter /* : (arg0 : CompileState, arg1 : string) => [CompileState, Parameter] */(state1 /* : unknown */, s /* : unknown */) /* : [CompileState, Parameter] */) /* : Some */) /* : [CompileState, List<T>] */;
    }
    parseFunctionSegments(state, input, depth) {
        return this /* : Main */.parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state /* : CompileState */, input /* : string */, (state1, input1) => this /* : Main */.parseFunctionSegment /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1 /* : unknown */, input1 /* : unknown */, depth /* : number */ + 1 /* : number */) /* : [CompileState, FunctionSegment] */) /* : [CompileState, List<T>] */;
    }
    parseFunctionSegment(state, input, depth) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (stripped /* : unknown */.isEmpty /* : unknown */() /* : unknown */) {
            return [state /* : CompileState */, new Whitespace() /* : Whitespace */];
        }
        return this /* : Main */.parseFunctionStatement /* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, FunctionSegment]> */(state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : Option<[CompileState, FunctionSegment]> */.or /* : (arg0 : () => Option<[CompileState, FunctionSegment]>) => Option<[CompileState, FunctionSegment]> */(() => this /* : Main */.parseBlock /* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, FunctionSegment]> */(state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : Option<[CompileState, FunctionSegment]> */) /* : Option<[CompileState, FunctionSegment]> */.orElseGet /* : (arg0 : () => T) => T */(() => [state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */]) /* : T */;
    }
    parseFunctionStatement(state, depth, stripped) {
        return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped /* : string */, ";", (s) => {
            let tuple = this /* : Main */.parseStatementValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, StatementValue] */(state /* : CompileState */, s /* : string */, depth /* : number */) /* : [CompileState, StatementValue] */;
            let left = tuple /* : [CompileState, StatementValue] */[0 /* : number */];
            let right = tuple /* : [CompileState, StatementValue] */[1 /* : number */];
            return new Some([left /* : unknown */, new Statement(depth /* : number */, right /* : unknown */) /* : Statement */]) /* : Some */;
        }) /* : Option<T> */;
    }
    parseBlock(state, depth, stripped) {
        return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped /* : string */, "}", (withoutEnd) => {
            return this /* : Main */.split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this /* : Main */.toFirst /* : (arg0 : string) => Option<[string, string]> */(withoutEnd /* : string */) /* : Option<[string, string]> */, (beforeContent, content) => {
                return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeContent /* : unknown */, "{", (headerString) => {
                    let headerTuple = this /* : Main */.parseBlockHeader /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, BlockHeader] */(state /* : CompileState */, headerString /* : string */, depth /* : number */) /* : [CompileState, BlockHeader] */;
                    let headerState = headerTuple /* : [CompileState, BlockHeader] */[0 /* : number */];
                    let header = headerTuple /* : [CompileState, BlockHeader] */[1 /* : number */];
                    let statementsTuple = this /* : Main */.parseFunctionSegments /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, List<FunctionSegment>] */(headerState /* : unknown */, content /* : unknown */, depth /* : number */) /* : [CompileState, List<FunctionSegment>] */;
                    let statementsState = statementsTuple /* : [CompileState, List<FunctionSegment>] */[0 /* : number */];
                    let statements = statementsTuple /* : [CompileState, List<FunctionSegment>] */[1 /* : number */].addAllFirst /* : unknown */(statementsState /* : unknown */.functionSegments /* : unknown */) /* : unknown */;
                    return new Some([statementsState /* : unknown */.clearFunctionSegments /* : unknown */() /* : unknown */, new Block(depth /* : number */, header /* : unknown */, statements /* : unknown */) /* : Block */]) /* : Some */;
                }) /* : Option<T> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    toFirst(input) {
        let divisions = this /* : Main */.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input /* : string */, this /* : Main */.foldBlockStart /* : unknown */) /* : List<string> */;
        return divisions /* : List<string> */.removeFirst /* : () => Option<[string, List<string>]> */() /* : Option<[string, List<string>]> */.map /* : (arg0 : (arg0 : [string, List<string>]) => R) => Option<R> */((removed) => {
            let right = removed /* : [string, List<string>] */[0 /* : number */];
            let left = removed /* : [string, List<string>] */[1 /* : number */].query /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner("") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
            return [right /* : unknown */, left /* : unknown */];
        }) /* : Option<R> */;
    }
    parseBlockHeader(state, input, depth) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        return this /* : Main */.parseConditional /* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : number) => Option<[CompileState, BlockHeader]> */(state /* : CompileState */, stripped /* : unknown */, "if", depth /* : number */) /* : Option<[CompileState, BlockHeader]> */.or /* : (arg0 : () => Option<[CompileState, BlockHeader]>) => Option<[CompileState, BlockHeader]> */(() => this /* : Main */.parseConditional /* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : number) => Option<[CompileState, BlockHeader]> */(state /* : CompileState */, stripped /* : unknown */, "while", depth /* : number */) /* : Option<[CompileState, BlockHeader]> */) /* : Option<[CompileState, BlockHeader]> */.or /* : unknown */(() => this /* : Main */.parseElse /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, BlockHeader]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, BlockHeader]> */) /* : unknown */.orElseGet /* : unknown */(() => [state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */]) /* : unknown */;
    }
    parseElse(state, input) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (stripped /* : unknown */ === "else") {
            return new Some([state /* : CompileState */, new Else() /* : Else */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseConditional(state, input, prefix, depth) {
        return this /* : Main */.prefix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input /* : string */, prefix /* : string */, (withoutPrefix) => {
            return this /* : Main */.prefix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withoutPrefix /* : string */.strip /* : unknown */() /* : unknown */, "(", (withoutValueStart) => {
                return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withoutValueStart /* : string */, ")", (value) => {
                    let valueTuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, value /* : string */, depth /* : number */) /* : [CompileState, Value] */;
                    let value1 = valueTuple /* : [CompileState, Value] */[1 /* : number */];
                    return new Some([valueTuple /* : [CompileState, Value] */[0 /* : number */], new Conditional(prefix /* : string */, value1 /* : unknown */) /* : Conditional */]) /* : Some */;
                }) /* : Option<T> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    foldBlockStart(state, c) {
        let appended = state /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
        if (c /* : string */ === "{" && state /* : DivideState */.isLevel /* : unknown */() /* : unknown */) {
            return appended /* : DivideState */.advance /* : () => DivideState */() /* : DivideState */;
        }
        if (c /* : string */ === "{") {
            return appended /* : DivideState */.enter /* : unknown */() /* : unknown */;
        }
        if (c /* : string */ === "}") {
            return appended /* : DivideState */.exit /* : unknown */() /* : unknown */;
        }
        return appended /* : DivideState */;
    }
    parseStatementValue(state, input, depth) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (stripped /* : unknown */.startsWith /* : unknown */("return ") /* : unknown */) {
            let value = stripped /* : unknown */.substring /* : unknown */(Strings /* : Strings */.length /* : (arg0 : string) => number */("return ") /* : number */) /* : unknown */;
            let tuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, value /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
            let value1 = tuple /* : [CompileState, Value] */[1 /* : number */];
            return [tuple /* : [CompileState, Value] */[0 /* : number */], new Return(value1 /* : unknown */) /* : Return */];
        }
        return this /* : Main */.parseAssignment /* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, StatementValue]> */(state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : Option<[CompileState, StatementValue]> */.orElseGet /* : (arg0 : () => [CompileState, StatementValue]) => [CompileState, StatementValue] */(() => {
            return [state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */];
        }) /* : [CompileState, StatementValue] */;
    }
    parseAssignment(state, depth, stripped) {
        return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(stripped /* : string */, "=", (beforeEquals, valueString) => {
            let sourceTuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, valueString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
            let sourceState = sourceTuple /* : [CompileState, Value] */[0 /* : number */];
            let source = sourceTuple /* : [CompileState, Value] */[1 /* : number */];
            let destinationTuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(sourceState /* : unknown */, beforeEquals /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
            let destinationState = destinationTuple /* : [CompileState, Value] */[0 /* : number */];
            let destination = destinationTuple /* : [CompileState, Value] */[1 /* : number */];
            return this /* : Main */.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(destinationState /* : unknown */, beforeEquals /* : unknown */) /* : Option<[CompileState, Definition]> */.flatMap /* : (arg0 : (arg0 : [CompileState, Definition]) => Option<R>) => Option<R> */((definitionTuple) => this /* : Main */.parseInitialization /* : (arg0 : CompileState, arg1 : Definition, arg2 : Value) => Option<[CompileState, StatementValue]> */(definitionTuple /* : [CompileState, Definition] */[0 /* : number */], definitionTuple /* : [CompileState, Definition] */[1 /* : number */], source /* : unknown */) /* : Option<[CompileState, StatementValue]> */) /* : Option<R> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => new Some([destinationState /* : unknown */, new Assignment(destination /* : unknown */, source /* : unknown */) /* : Assignment */]) /* : Some */) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    parseInitialization(state, rawDefinition, source) {
        let definition = rawDefinition /* : Definition */.mapType /* : (arg0 : (arg0 : Type) => Type) => Definition */((type) => {
            if (type /* : Type */ === Primitive /* : Primitive */.Unknown /* : unknown */) {
                return source /* : Value */.type /* : () => Type */() /* : Type */;
            }
            else {
                return type /* : Type */;
            }
        }) /* : Definition */;
        return new Some([state /* : CompileState */.define /* : (arg0 : Definition) => CompileState */(definition /* : Definition */) /* : CompileState */, new Initialization(definition /* : Definition */, source /* : Value */) /* : Initialization */]) /* : Some */;
    }
    parseValue(state, input, depth) {
        return this /* : Main */.parseBoolean /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Value]> */.or /* : (arg0 : () => Option<[CompileState, Value]>) => Option<[CompileState, Value]> */(() => this /* : Main */.parseLambda /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */) /* : Option<[CompileState, Value]> */) /* : Option<[CompileState, Value]> */.or /* : unknown */(() => this /* : Main */.parseString /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseDataAccess /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseSymbolValue /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseInvokable /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseDigits /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseInstanceOf /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */, Operator /* : () => content-start new content-end */.ADD /* : unknown */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */, Operator /* : () => content-start new content-end */.EQUALS /* : unknown */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */, Operator /* : () => content-start new content-end */.SUBTRACT /* : unknown */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */, Operator /* : () => content-start new content-end */.AND /* : unknown */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */, Operator /* : () => content-start new content-end */.OR /* : unknown */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseOperation /* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseNot /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseMethodReference /* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */, depth /* : number */) /* : Option<[CompileState, Value]> */) /* : unknown */.or /* : unknown */(() => this /* : Main */.parseChar /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Value]> */) /* : unknown */.orElseGet /* : unknown */(() => [state /* : CompileState */, new Placeholder(input /* : string */) /* : Placeholder */]) /* : unknown */;
    }
    parseChar(state, input) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (stripped /* : unknown */.startsWith /* : unknown */("'") /* : unknown */ && stripped /* : unknown */.endsWith /* : unknown */("'") /* : unknown */ && Strings /* : Strings */.length /* : (arg0 : string) => number */(stripped /* : unknown */) /* : number */ >= 2 /* : number */) {
            return new Some([state /* : CompileState */, new StringValue(stripped /* : unknown */.substring /* : unknown */(1 /* : number */, Strings /* : Strings */.length /* : (arg0 : string) => number */(stripped /* : unknown */) /* : number */ - 1 /* : number */) /* : unknown */) /* : StringValue */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseBoolean(state, input) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (stripped /* : unknown */ === "false") {
            return new Some([state /* : CompileState */, BooleanValue /* : BooleanValue */.False /* : unknown */]) /* : Some */;
        }
        if (stripped /* : unknown */ === "true") {
            return new Some([state /* : CompileState */, BooleanValue /* : BooleanValue */.True /* : unknown */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseInstanceOf(state, input, depth) {
        return this /* : Main */.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input /* : string */, "instanceof", (s, s2) => {
            let childTuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
            return this /* : Main */.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(childTuple /* : [CompileState, Value] */[0 /* : number */], s2 /* : unknown */) /* : Option<[CompileState, Definition]> */.map /* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((definitionTuple) => {
                let value = childTuple /* : [CompileState, Value] */[1 /* : number */];
                let definition = definitionTuple /* : [CompileState, Definition] */[1 /* : number */];
                let type = value /* : unknown */.type /* : unknown */() /* : unknown */;
                let variant = new DataAccess(value /* : unknown */, "_" + type.findName() + "Variant", Primitive /* : Primitive */.Unknown /* : unknown */) /* : DataAccess */;
                let generate = type /* : unknown */.findName /* : unknown */() /* : unknown */;
                let temp = new SymbolValue(generate /* : unknown */ + "Variant." + definition /* : unknown */.findType /* : unknown */() /* : unknown */.findName /* : unknown */() /* : unknown */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : SymbolValue */;
                let functionSegment = new Statement(depth /* : number */ + 1 /* : number */, new Initialization(definition /* : unknown */, new Cast(value /* : unknown */, definition /* : unknown */.findType /* : unknown */() /* : unknown */) /* : Cast */) /* : Initialization */) /* : Statement */;
                return [definitionTuple /* : [CompileState, Definition] */[0 /* : number */].addFunctionSegment /* : unknown */(functionSegment /* : Statement */) /* : unknown */.define /* : unknown */(definition /* : unknown */) /* : unknown */, new Operation(variant /* : DataAccess */, Operator /* : () => content-start new content-end */.EQUALS /* : unknown */, temp /* : SymbolValue */) /* : Operation */];
            }) /* : Option<R> */;
        }) /* : Option<T> */;
    }
    parseMethodReference(state, input, depth) {
        return this /* : Main */.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input /* : string */, "::", (s, s2) => {
            let tuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
            return new Some([tuple /* : [CompileState, Value] */[0 /* : number */], new DataAccess(tuple /* : [CompileState, Value] */[1 /* : number */], s2 /* : unknown */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : DataAccess */]) /* : Some */;
        }) /* : Option<T> */;
    }
    parseNot(state, input, depth) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (stripped /* : unknown */.startsWith /* : unknown */("!") /* : unknown */) {
            let slice = stripped /* : unknown */.substring /* : unknown */(1 /* : number */) /* : unknown */;
            let tuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, slice /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
            let value = tuple /* : [CompileState, Value] */[1 /* : number */];
            return new Some([tuple /* : [CompileState, Value] */[0 /* : number */], new Not(value /* : unknown */) /* : Not */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseLambda(state, input, depth) {
        return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input /* : string */, "->", (beforeArrow, valueString) => {
            let strippedBeforeArrow = beforeArrow /* : unknown */.strip /* : unknown */() /* : unknown */;
            if (isSymbol /* : (arg0 : string) => boolean */(strippedBeforeArrow /* : unknown */) /* : boolean */) {
                let type = Primitive /* : Primitive */.Unknown /* : unknown */;
                if ( /* state.typeRegister instanceof Some */( /* var expectedType */) /* : unknown */) {
                    if ( /* expectedType */._UnknownVariant /* : unknown */ === UnknownVariant.FunctionType /* : unknown */) {
                        let functionType = /* expectedType */ as, FunctionType;
                        type /* : Type */ = functionType /* : FunctionType */.arguments /* : List<Type> */.get /* : (arg0 : number) => Option<Type> */(0 /* : number */) /* : Option<Type> */.orElse /* : (arg0 : Type) => Type */( /* null */) /* : Type */;
                    }
                }
                return this /* : Main */.assembleLambda /* : (arg0 : CompileState, arg1 : List<Definition>, arg2 : string, arg3 : number) => Some<[CompileState, Value]> */(state /* : CompileState */, Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */(strippedBeforeArrow /* : unknown */, type /* : Type */) /* : Definition */) /* : List<T> */, valueString /* : unknown */, depth /* : number */) /* : Some<[CompileState, Value]> */;
            }
            if (strippedBeforeArrow /* : unknown */.startsWith /* : unknown */("(") /* : unknown */ && strippedBeforeArrow /* : unknown */.endsWith /* : unknown */(")") /* : unknown */) {
                let parameterNames = this /* : Main */.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(strippedBeforeArrow /* : unknown */.substring /* : unknown */(1 /* : number */, Strings /* : Strings */.length /* : (arg0 : string) => number */(strippedBeforeArrow /* : unknown */) /* : number */ - 1 /* : number */) /* : unknown */, this /* : Main */.foldValueChar /* : unknown */) /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.map /* : (arg0 : (arg0 : string) => R) => Query<R> */(strip /* : unknown */) /* : Query<R> */.filter /* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value) => !value /* : T */.isEmpty /* : unknown */() /* : unknown */) /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */((name) => ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */(name /* : T */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : Definition */) /* : Option<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
                return this /* : Main */.assembleLambda /* : (arg0 : CompileState, arg1 : List<Definition>, arg2 : string, arg3 : number) => Some<[CompileState, Value]> */(state /* : CompileState */, parameterNames /* : unknown */, valueString /* : unknown */, depth /* : number */) /* : Some<[CompileState, Value]> */;
            }
            return new None() /* : None */;
        }) /* : Option<T> */;
    }
    assembleLambda(state, definitions, valueString, depth) {
        let strippedValueString = valueString /* : string */.strip /* : unknown */() /* : unknown */;
        /* Tuple2<CompileState, LambdaValue> value */ ;
        let state2 = state /* : CompileState */.defineAll /* : (arg0 : List<Definition>) => CompileState */(definitions /* : List<Definition> */) /* : CompileState */;
        if (strippedValueString /* : unknown */.startsWith /* : unknown */("{") /* : unknown */ && strippedValueString /* : unknown */.endsWith /* : unknown */("}") /* : unknown */) {
            let value1 = this /* : Main */.parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state2 /* : CompileState */, strippedValueString /* : unknown */.substring /* : unknown */(1 /* : number */, Strings /* : Strings */.length /* : (arg0 : string) => number */(strippedValueString /* : unknown */) /* : number */ - 1 /* : number */) /* : unknown */, (state1, input1) => this /* : Main */.parseFunctionSegment /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1 /* : unknown */, input1 /* : unknown */, depth /* : number */ + 1 /* : number */) /* : [CompileState, FunctionSegment] */) /* : [CompileState, List<T>] */;
            let right = value1 /* : [CompileState, List<T>] */[1 /* : number */];
            [value1 /* : [CompileState, List<T>] */[0 /* : number */], new BlockLambdaValue(depth /* : number */, right /* : unknown */) /* : BlockLambdaValue */];
        }
        else {
            let value1 = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state2 /* : CompileState */, strippedValueString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
            [value1 /* : [CompileState, List<T>] */[0 /* : number */], value1 /* : [CompileState, List<T>] */[1 /* : number */]];
        }
        let right = /* value */ .right /* : unknown */() /* : unknown */;
        return new Some([/* value */ .left /* : unknown */() /* : unknown */, new Lambda(definitions /* : List<Definition> */, right /* : unknown */) /* : Lambda */]) /* : Some */;
    }
    parseDigits(state, input) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (this /* : Main */.isNumber /* : (arg0 : string) => boolean */(stripped /* : unknown */) /* : boolean */) {
            return new Some([state /* : CompileState */, new SymbolValue(stripped /* : unknown */, Primitive /* : Primitive */.Int /* : unknown */) /* : SymbolValue */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    isNumber(input) {
        /* String maybeTruncated */ ;
        if (input /* : string */.startsWith /* : unknown */("-") /* : unknown */) {
            input /* : string */.substring /* : unknown */(1 /* : number */) /* : unknown */;
        }
        else {
            input /* : string */;
        }
        return this /* : Main */.areAllDigits /* : (arg0 : string) => boolean */( /* maybeTruncated */) /* : boolean */;
    }
    areAllDigits(input) {
        /* for (var i = 0; i < Strings.length(input); i++) */ {
            let c = input /* : string */.charAt /* : unknown */( /* i */) /* : unknown */;
            if ( /* Character */.isDigit /* : unknown */(c /* : unknown */) /* : unknown */) {
                /* continue */ ;
            }
            return false;
        }
        return true;
    }
    parseInvokable(state, input, depth) {
        return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, ")", (withoutEnd) => {
            return this /* : Main */.split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this /* : Main */.toLast /* : (arg0 : string, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState) => Option<[string, string]> */(withoutEnd /* : string */, "", this /* : Main */.foldInvocationStart /* : unknown */) /* : Option<[string, string]> */, (callerWithEnd, argumentsString) => {
                return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(callerWithEnd /* : unknown */, "(", (callerString) => {
                    return this /* : Main */.assembleInvokable /* : (arg0 : CompileState, arg1 : number, arg2 : string, arg3 : string) => Some<[CompileState, Value]> */(state /* : CompileState */, depth /* : number */, argumentsString /* : unknown */, callerString /* : string */.strip /* : unknown */() /* : unknown */) /* : Some<[CompileState, Value]> */;
                }) /* : Option<T> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    assembleInvokable(state, depth, argumentsString, callerString) {
        let callerTuple = this /* : Main */.invocationHeader /* : (arg0 : CompileState, arg1 : number, arg2 : string) => [CompileState, Caller] */(state /* : CompileState */, depth /* : number */, callerString /* : string */) /* : [CompileState, Caller] */;
        let oldCallerState = callerTuple /* : [CompileState, Caller] */[0 /* : number */];
        let oldCaller = callerTuple /* : [CompileState, Caller] */[1 /* : number */];
        let newCaller = this /* : Main */.modifyCaller /* : (arg0 : CompileState, arg1 : Caller) => Caller */(oldCallerState /* : unknown */, oldCaller /* : unknown */) /* : Caller */;
        let callerType = this /* : Main */.findCallerType /* : (arg0 : Caller) => FunctionType */(newCaller /* : Caller */) /* : FunctionType */;
        let argumentsTuple = this /* : Main */.parseValuesWithIndices /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(oldCallerState /* : unknown */, argumentsString /* : string */, (currentState, pair) => this /* : Main */.getTuple2Some /* : (arg0 : number, arg1 : CompileState, arg2 : [number, string], arg3 : FunctionType) => Some<[CompileState, [Argument, Type]]> */(depth /* : number */, currentState /* : unknown */, pair /* : unknown */, callerType /* : FunctionType */) /* : Some<[CompileState, [Argument, Type]]> */) /* : Option<[CompileState, List<T>]> */.orElseGet /* : (arg0 : () => [CompileState, List<T>]) => [CompileState, List<T>] */(() => [oldCallerState /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */]) /* : [CompileState, List<T>] */;
        let argumentsState = argumentsTuple /* : [CompileState, List<T>] */[0 /* : number */];
        let argumentsWithActualTypes = argumentsTuple /* : [CompileState, List<T>] */[1 /* : number */];
        let arguments = this /* : Main */.retainValues /* : (arg0 : List<Argument>) => List<Value> */(argumentsWithActualTypes /* : unknown */.query /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2 /* : Tuple2 */.left /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */) /* : List<Value> */;
        if (newCaller /* : Caller */._CallerVariant /* : unknown */ === CallerVariant.ConstructionCaller /* : unknown */) {
            if (constructionCaller /* : ConstructionCaller */.type /* : Type */.findName /* : () => string */() /* : string */ === "Tuple2Impl") {
                let constructionCaller = newCaller /* : Caller */;
                return new Some([argumentsState /* : unknown */, new TupleNode(Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(arguments /* : List<Value> */.get /* : (arg0 : number) => Option<Value> */(0 /* : number */) /* : Option<Value> */.orElse /* : (arg0 : Value) => Value */( /* null */) /* : Value */, arguments /* : List<Value> */.get /* : (arg0 : number) => Option<Value> */(1 /* : number */) /* : Option<Value> */.orElse /* : (arg0 : Value) => Value */( /* null */) /* : Value */) /* : List<T> */) /* : TupleNode */]) /* : Some */;
            }
        }
        if (newCaller /* : Caller */._CallerVariant /* : unknown */ === CallerVariant.Value /* : unknown */) {
            if (value /* : Value */._ValueVariant /* : unknown */ === ValueVariant.DataAccess /* : unknown */) {
                let parent = access /* : DataAccess */.parent /* : Value */;
                let property = access /* : DataAccess */.property /* : string */;
                let parentType = parent /* : Value */.type /* : () => Type */() /* : Type */;
                if ( /* parentType instanceof TupleType */) {
                    if (property /* : string */ === "left") {
                        let value = newCaller /* : Caller */;
                        let access = value /* : Value */;
                        return new Some([argumentsState /* : unknown */, new IndexValue(parent /* : Value */, new SymbolValue("0", Primitive /* : Primitive */.Int /* : unknown */) /* : SymbolValue */) /* : IndexValue */]) /* : Some */;
                    }
                    if (property /* : string */ === "right") {
                        return new Some([argumentsState /* : unknown */, new IndexValue(parent /* : Value */, new SymbolValue("1", Primitive /* : Primitive */.Int /* : unknown */) /* : SymbolValue */) /* : IndexValue */]) /* : Some */;
                    }
                }
                if (property /* : string */ === "equals") {
                    let first = arguments /* : List<Value> */.get /* : (arg0 : number) => Option<Value> */(0 /* : number */) /* : Option<Value> */.orElse /* : (arg0 : Value) => Value */( /* null */) /* : Value */;
                    return new Some([argumentsState /* : unknown */, new Operation(parent /* : Value */, Operator /* : () => content-start new content-end */.EQUALS /* : unknown */, first /* : Value */) /* : Operation */]) /* : Some */;
                }
            }
        }
        let invokable = new Invokable(newCaller /* : Caller */, arguments /* : List<Value> */, callerType /* : FunctionType */.returns /* : Type */) /* : Invokable */;
        return new Some([argumentsState /* : unknown */, invokable /* : Invokable */]) /* : Some */;
    }
    getTuple2Some(depth, currentState, pair, callerType) {
        let index = pair /* : [number, string] */[0 /* : number */];
        let element = pair /* : [number, string] */[1 /* : number */];
        let expectedType = callerType /* : FunctionType */.arguments /* : List<Type> */.get /* : (arg0 : number) => Option<Type> */(index /* : unknown */) /* : Option<Type> */.orElse /* : (arg0 : Type) => Type */(Primitive /* : Primitive */.Unknown /* : unknown */) /* : Type */;
        let withExpected = currentState /* : CompileState */.withExpectedType /* : (arg0 : Type) => CompileState */(expectedType /* : Type */) /* : CompileState */;
        let valueTuple = this /* : Main */.parseArgument /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Argument] */(withExpected /* : CompileState */, element /* : unknown */, depth /* : number */) /* : [CompileState, Argument] */;
        let valueState = valueTuple /* : [CompileState, Argument] */[0 /* : number */];
        let value = valueTuple /* : [CompileState, Argument] */[1 /* : number */];
        let actualType = valueTuple /* : [CompileState, Argument] */[0 /* : number */].typeRegister /* : unknown */.orElse /* : unknown */(Primitive /* : Primitive */.Unknown /* : unknown */) /* : unknown */;
        return new Some([valueState /* : unknown */, [value /* : unknown */, actualType /* : unknown */]]) /* : Some */;
    }
    retainValues(arguments) {
        return arguments /* : List<Argument> */.query /* : () => Query<Argument> */() /* : Query<Argument> */.map /* : (arg0 : (arg0 : Argument) => R) => Query<R> */(this /* : Main */.retainValue /* : unknown */) /* : Query<R> */.flatMap /* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries /* : Queries */.fromOption /* : unknown */) /* : Option<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    retainValue(argument) {
        if (argument /* : Argument */._ArgumentVariant /* : unknown */ === ArgumentVariant.Value /* : unknown */) {
            let value = argument /* : Argument */;
            return new Some(value /* : Value */) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseArgument(state, element, depth) {
        if (element /* : string */.isEmpty /* : unknown */() /* : unknown */) {
            return [state /* : CompileState */, new Whitespace() /* : Whitespace */];
        }
        let tuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, element /* : string */, depth /* : number */) /* : [CompileState, Value] */;
        return [tuple /* : [CompileState, Value] */[0 /* : number */], tuple /* : [CompileState, Value] */[1 /* : number */]];
    }
    findCallerType(newCaller) {
        let callerType = new FunctionType(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : FunctionType */;
        /* switch (newCaller) */ {
            /* case ConstructionCaller constructionCaller -> */ {
                callerType /* : FunctionType */ = /* constructionCaller */ .toFunction /* : unknown */() /* : unknown */;
            }
            /* case Value value -> */ {
                let type = /* value */ .type /* : unknown */() /* : unknown */;
                if (type /* : unknown */._UnknownVariant /* : unknown */ === UnknownVariant.FunctionType /* : unknown */) {
                    let functionType = type /* : unknown */;
                    callerType /* : FunctionType */ = functionType /* : FunctionType */;
                }
            }
        }
        return callerType /* : FunctionType */;
    }
    modifyCaller(state, oldCaller) {
        if (oldCaller /* : Caller */._CallerVariant /* : unknown */ === CallerVariant.DataAccess /* : unknown */) {
            let type = this /* : Main */.resolveType /* : (arg0 : Value, arg1 : CompileState) => Type */(access /* : DataAccess */.parent /* : Value */, state /* : CompileState */) /* : Type */;
            if ( /* type instanceof FunctionType */) {
                let access = oldCaller /* : Caller */;
                return access /* : DataAccess */.parent /* : Value */;
            }
        }
        return oldCaller /* : Caller */;
    }
    resolveType(value, state) {
        return value /* : Value */.type /* : () => Type */() /* : Type */;
    }
    invocationHeader(state, depth, callerString1) {
        if (callerString1 /* : string */.startsWith /* : unknown */("new ") /* : unknown */) {
            let input1 = callerString1 /* : string */.substring /* : unknown */(Strings /* : Strings */.length /* : (arg0 : string) => number */("new ") /* : number */) /* : unknown */;
            let map = this /* : Main */.parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */, input1 /* : string */) /* : Option<[CompileState, Type]> */.map /* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((type) => {
                let right = type /* : [CompileState, Type] */[1 /* : number */];
                return [type /* : [CompileState, Type] */[0 /* : number */], new ConstructionCaller(right /* : unknown */) /* : ConstructionCaller */];
            }) /* : Option<R> */;
            if (map /* : Option<R> */.isPresent /* : unknown */() /* : unknown */) {
                return map /* : Option<R> */.orElse /* : unknown */( /* null */) /* : unknown */;
            }
        }
        let tuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, callerString1 /* : string */, depth /* : number */) /* : [CompileState, Value] */;
        return [tuple /* : [CompileState, Value] */[0 /* : number */], tuple /* : [CompileState, Value] */[1 /* : number */]];
    }
    foldInvocationStart(state, c) {
        let appended = state /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
        if (c /* : string */ === "(") {
            let enter = appended /* : DivideState */.enter /* : unknown */() /* : unknown */;
            if (enter /* : unknown */.isShallow /* : unknown */() /* : unknown */) {
                return enter /* : unknown */.advance /* : unknown */() /* : unknown */;
            }
            return enter /* : unknown */;
        }
        if (c /* : string */ === ")") {
            return appended /* : DivideState */.exit /* : unknown */() /* : unknown */;
        }
        return appended /* : DivideState */;
    }
    parseDataAccess(state, input, depth) {
        return this /* : Main */.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, ".", (parentString, rawProperty) => {
            let property = rawProperty /* : unknown */.strip /* : unknown */() /* : unknown */;
            if (!isSymbol /* : (arg0 : string) => boolean */(property /* : unknown */) /* : unknown */) {
                return new None() /* : None */;
            }
            let tuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, parentString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
            let parent = tuple /* : [CompileState, Value] */[1 /* : number */];
            let parentType = parent /* : unknown */.type /* : unknown */() /* : unknown */;
            let type = Primitive /* : Primitive */.Unknown /* : unknown */;
            if (parentType /* : unknown */._UnknownVariant /* : unknown */ === UnknownVariant.FindableType /* : unknown */) {
                if ( /* objectType.find(property) instanceof Some */( /* var memberType */) /* : unknown */) {
                    let objectType = parentType /* : unknown */;
                    type /* : Type */ =  /* memberType */;
                }
            }
            return new Some([tuple /* : [CompileState, Value] */[0 /* : number */], new DataAccess(parent /* : unknown */, property /* : unknown */, type /* : Type */) /* : DataAccess */]) /* : Some */;
        }) /* : Option<T> */;
    }
    parseString(state, input) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (stripped /* : unknown */.startsWith /* : unknown */("\"") /* : unknown */ && stripped /* : unknown */.endsWith /* : unknown */("\"") /* : unknown */) {
            return new Some([state /* : CompileState */, new StringValue(stripped /* : unknown */.substring /* : unknown */(1 /* : number */, Strings /* : Strings */.length /* : (arg0 : string) => number */(stripped /* : unknown */) /* : number */ - 1 /* : number */) /* : unknown */) /* : StringValue */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseSymbolValue(state, value) {
        let stripped = value /* : string */.strip /* : unknown */() /* : unknown */;
        if (isSymbol /* : (arg0 : string) => boolean */(stripped /* : unknown */) /* : boolean */) {
            if ( /* state.resolveValue(stripped) instanceof Some */( /* var type */) /* : unknown */) {
                return new Some([state /* : CompileState */, new SymbolValue(stripped /* : unknown */, type /* : () => Type */) /* : SymbolValue */]) /* : Some */;
            }
            if ( /* state.resolveType(stripped) instanceof Some */( /* var type */) /* : unknown */) {
                return new Some([state /* : CompileState */, new SymbolValue(stripped /* : unknown */, type /* : () => Type */) /* : SymbolValue */]) /* : Some */;
            }
            return new Some([state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */]) /* : Some */;
        }
        return new None() /* : None */;
    }
    parseOperation(state, value, depth, operator) {
        return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(value /* : string */, operator /* : Operator */.sourceRepresentation /* : string */, (leftString, rightString) => {
            let leftTuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, leftString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
            let rightTuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(leftTuple /* : [CompileState, Value] */[0 /* : number */], rightString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
            let left = leftTuple /* : [CompileState, Value] */[1 /* : number */];
            let right = rightTuple /* : [CompileState, Value] */[1 /* : number */];
            return new Some([rightTuple /* : [CompileState, Value] */[0 /* : number */], new Operation(left /* : unknown */, operator /* : Operator */, right /* : unknown */) /* : Operation */]) /* : Some */;
        }) /* : Option<T> */;
    }
    parseValuesOrEmpty(state, input, mapper) {
        return this /* : Main */.parseValues /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, input /* : string */, mapper /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */) /* : Option<[CompileState, List<T>]> */.orElseGet /* : (arg0 : () => [CompileState, List<T>]) => [CompileState, List<T>] */(() => [state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */]) /* : [CompileState, List<T>] */;
    }
    parseValues(state, input, mapper) {
        return this /* : Main */.parseValuesWithIndices /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, input /* : string */, (state1, tuple) => mapper /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */(state1 /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Option<[CompileState, T]> */) /* : Option<[CompileState, List<T>]> */;
    }
    parseValuesWithIndices(state, input, mapper) {
        return this /* : Main */.parseAllWithIndices /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, input /* : string */, this /* : Main */.foldValueChar /* : unknown */, mapper /* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */) /* : Option<[CompileState, List<T>]> */;
    }
    parseParameter(state, input) {
        if (Strings /* : Strings */.isBlank /* : (arg0 : string) => boolean */(input /* : string */) /* : boolean */) {
            return [state /* : CompileState */, new Whitespace() /* : Whitespace */];
        }
        return this /* : Main */.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Definition]> */.map /* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((tuple) => [tuple /* : [CompileState, Definition] */[0 /* : number */], tuple /* : [CompileState, Definition] */[1 /* : number */]]) /* : Option<R> */.orElseGet /* : unknown */(() => [state /* : CompileState */, new Placeholder(input /* : string */) /* : Placeholder */]) /* : unknown */;
    }
    parseField(input, depth, state) {
        return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, ";", (withoutEnd) => {
            return this /* : Main */.parseClassInitialization /* : (arg0 : number, arg1 : CompileState, arg2 : string) => Option<[CompileState, IncompleteClassSegment]> */(depth /* : number */, state /* : CompileState */, withoutEnd /* : string */) /* : Option<[CompileState, IncompleteClassSegment]> */.or /* : (arg0 : () => Option<[CompileState, IncompleteClassSegment]>) => Option<[CompileState, IncompleteClassSegment]> */(() => {
                return this /* : Main */.parseClassDefinition /* : (arg0 : number, arg1 : CompileState, arg2 : string) => Option<[CompileState, IncompleteClassSegment]> */(depth /* : number */, state /* : CompileState */, withoutEnd /* : string */) /* : Option<[CompileState, IncompleteClassSegment]> */;
            }) /* : Option<[CompileState, IncompleteClassSegment]> */;
        }) /* : Option<T> */;
    }
    parseClassDefinition(depth, state, withoutEnd) {
        return this /* : Main */.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state /* : CompileState */, withoutEnd /* : string */) /* : Option<[CompileState, Definition]> */.map /* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((result) => {
            return [result /* : [CompileState, Definition] */[0 /* : number */], new ClassDefinition(depth /* : number */, result /* : [CompileState, Definition] */[1 /* : number */]) /* : ClassDefinition */];
        }) /* : Option<R> */;
    }
    parseClassInitialization(depth, state, withoutEnd) {
        return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutEnd /* : string */, "=", (s, s2) => {
            return this /* : Main */.parseDefinition /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state /* : CompileState */, s /* : unknown */) /* : Option<[CompileState, Definition]> */.map /* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((result) => {
                let valueTuple = this /* : Main */.parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(result /* : [CompileState, Definition] */[0 /* : number */], s2 /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
                return [valueTuple /* : [CompileState, Value] */[0 /* : number */], new ClassInitialization(depth /* : number */, result /* : [CompileState, Definition] */[1 /* : number */], valueTuple /* : [CompileState, Value] */[1 /* : number */]) /* : ClassInitialization */];
            }) /* : Option<R> */;
        }) /* : Option<T> */;
    }
    parseDefinition(state, input) {
        return this /* : Main */.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, " ", (beforeName, name) => {
            return this /* : Main */.split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this /* : Main */.toLast /* : (arg0 : string, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState) => Option<[string, string]> */(beforeName /* : unknown */, " ", this /* : Main */.foldTypeSeparator /* : unknown */) /* : Option<[string, string]> */, (beforeType, type) => {
                return this /* : Main */.last /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeType /* : unknown */, "\n", (s, s2) => {
                    let annotations = this /* : Main */.parseAnnotations /* : (arg0 : string) => List<string> */(s /* : unknown */) /* : List<string> */;
                    return this /* : Main */.getOr /* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : string, arg4 : List<string>) => Option<[CompileState, Definition]> */(state /* : CompileState */, name /* : unknown */, s2 /* : unknown */, type /* : unknown */, annotations /* : List<string> */) /* : Option<[CompileState, Definition]> */;
                }) /* : Option<T> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => {
                    return this /* : Main */.getOr /* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : string, arg4 : List<string>) => Option<[CompileState, Definition]> */(state /* : CompileState */, name /* : unknown */, beforeType /* : unknown */, type /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Option<[CompileState, Definition]> */;
                }) /* : Option<T> */;
            }) /* : Option<T> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => this /* : Main */.assembleDefinition /* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, name /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, beforeName /* : unknown */) /* : Option<[CompileState, Definition]> */) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    getOr(state, name, beforeType, type, annotations) {
        return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeType /* : string */.strip /* : unknown */() /* : unknown */, ">", (withoutTypeParamStart) => {
            return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutTypeParamStart /* : string */, "<", (beforeTypeParams, typeParamsString) => {
                let typeParams = this /* : Main */.parseValuesOrEmpty /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state /* : CompileState */, typeParamsString /* : unknown */, (state1, s) => new Some([state1 /* : unknown */, s /* : unknown */.strip /* : unknown */() /* : unknown */]) /* : Some */) /* : [CompileState, List<T>] */;
                return this /* : Main */.assembleDefinition /* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(typeParams /* : [CompileState, List<T>] */[0 /* : number */], annotations /* : List<string> */, this /* : Main */.parseModifiers /* : (arg0 : string) => List<string> */(beforeTypeParams /* : unknown */) /* : List<string> */, name /* : string */, typeParams /* : [CompileState, List<T>] */[1 /* : number */], type /* : string */) /* : Option<[CompileState, Definition]> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => {
            return this /* : Main */.assembleDefinition /* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(state /* : CompileState */, annotations /* : List<string> */, this /* : Main */.parseModifiers /* : (arg0 : string) => List<string> */(beforeType /* : string */) /* : List<string> */, name /* : string */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, type /* : string */) /* : Option<[CompileState, Definition]> */;
        }) /* : Option<T> */;
    }
    parseModifiers(modifiers) {
        return this /* : Main */.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(modifiers /* : string */.strip /* : unknown */() /* : unknown */, (state1, c) => this /* : Main */.foldByDelimiter /* : (arg0 : DivideState, arg1 : string, arg2 : string) => DivideState */(state1 /* : unknown */, c /* : unknown */, " ") /* : DivideState */) /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.map /* : (arg0 : (arg0 : string) => R) => Query<R> */(strip /* : unknown */) /* : Query<R> */.filter /* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value) => !value /* : T */.isEmpty /* : unknown */() /* : unknown */) /* : Option<T> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    toLast(input, separator, folder) {
        let divisions = this /* : Main */.divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input /* : string */, folder /* : (arg0 : DivideState, arg1 : string) => DivideState */) /* : List<string> */;
        return divisions /* : List<string> */.removeLast /* : () => Option<[List<string>, string]> */() /* : Option<[List<string>, string]> */.map /* : (arg0 : (arg0 : [List<string>, string]) => R) => Option<R> */((removed) => {
            let left = removed /* : [List<string>, string] */[0 /* : number */].query /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner(separator /* : string */) /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
            let right = removed /* : [List<string>, string] */[1 /* : number */];
            return [left /* : unknown */, right /* : unknown */];
        }) /* : Option<R> */;
    }
    foldTypeSeparator(state, c) {
        if (c /* : string */ === " " && state /* : DivideState */.isLevel /* : unknown */() /* : unknown */) {
            return state /* : DivideState */.advance /* : () => DivideState */() /* : DivideState */;
        }
        let appended = state /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
        if (c /* : string */ === /*  ' */  /* ' */) {
            return appended /* : DivideState */.enter /* : unknown */() /* : unknown */;
        }
        if (c /* : string */ === ">") {
            return appended /* : DivideState */.exit /* : unknown */() /* : unknown */;
        }
        return appended /* : DivideState */;
    }
    assembleDefinition(state, annotations, modifiers, rawName, typeParams, type) {
        return this /* : Main */.parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */.withTypeParams /* : (arg0 : List<string>) => CompileState */(typeParams /* : List<string> */) /* : CompileState */, type /* : string */) /* : Option<[CompileState, Type]> */.flatMap /* : (arg0 : (arg0 : [CompileState, Type]) => Option<R>) => Option<R> */((type1) => {
            let stripped = rawName /* : string */.strip /* : unknown */() /* : unknown */;
            if (!isSymbol /* : (arg0 : string) => boolean */(stripped /* : unknown */) /* : unknown */) {
                return new None() /* : None */;
            }
            let newModifiers = modifiers /* : List<string> */.query /* : () => Query<string> */() /* : Query<string> */.filter /* : (arg0 : (arg0 : string) => boolean) => Query<string> */((value) => !this /* : Main */.isAccessor /* : unknown */(value /* : string */) /* : unknown */) /* : Query<string> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */((modifier) =>  /* modifier.equals("final") ? "readonly" : modifier */) /* : Option<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
            let node = new ImmutableDefinition(annotations /* : List<string> */, newModifiers /* : unknown */, stripped /* : unknown */, type1 /* : [CompileState, Type] */[1 /* : number */], typeParams /* : List<string> */) /* : ImmutableDefinition */;
            return new Some([type1 /* : [CompileState, Type] */[0 /* : number */], node /* : ImmutableDefinition */]) /* : Some */;
        }) /* : Option<R> */;
    }
    isAccessor(value) {
        return value /* : string */ === "private";
    }
    foldValueChar(state, c) {
        if (c /* : string */ === "," && state /* : DivideState */.isLevel /* : unknown */() /* : unknown */) {
            return state /* : DivideState */.advance /* : () => DivideState */() /* : DivideState */;
        }
        let appended = state /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
        if (c /* : string */ === /*  ' */ - /* ' */) {
            let peeked = appended /* : DivideState */.peek /* : unknown */() /* : unknown */;
            if (peeked /* : unknown */ === ">") {
                return appended /* : DivideState */.popAndAppendToOption /* : unknown */() /* : unknown */.orElse /* : unknown */(appended /* : DivideState */) /* : unknown */;
            }
            else {
                return appended /* : DivideState */;
            }
        }
        if (c /* : string */ === /*  ' */  /* '  */ || c /* : string */ === "(" || c /* : string */ === "{") {
            return appended /* : DivideState */.enter /* : unknown */() /* : unknown */;
        }
        if (c /* : string */ === ">" || c /* : string */ === ")" || c /* : string */ === "}") {
            return appended /* : DivideState */.exit /* : unknown */() /* : unknown */;
        }
        return appended /* : DivideState */;
    }
    parseType(state, input) {
        let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
        if (stripped /* : unknown */ === "int" || stripped /* : unknown */ === "Integer") {
            return new Some([state /* : CompileState */, Primitive /* : Primitive */.Int /* : unknown */]) /* : Some */;
        }
        if (stripped /* : unknown */ === "String" || stripped /* : unknown */ === "char" || stripped /* : unknown */ === "Character") {
            return new Some([state /* : CompileState */, Primitive /* : Primitive */.String /* : unknown */]) /* : Some */;
        }
        if (stripped /* : unknown */ === "var") {
            return new Some([state /* : CompileState */, Primitive /* : Primitive */.Unknown /* : unknown */]) /* : Some */;
        }
        if (stripped /* : unknown */ === "boolean") {
            return new Some([state /* : CompileState */, Primitive /* : Primitive */.Boolean /* : unknown */]) /* : Some */;
        }
        if (stripped /* : unknown */ === "void") {
            return new Some([state /* : CompileState */, Primitive /* : Primitive */.Void /* : unknown */]) /* : Some */;
        }
        if (isSymbol /* : (arg0 : string) => boolean */(stripped /* : unknown */) /* : boolean */) {
            if ( /* state.resolveType(stripped) instanceof Some */( /* var resolved */) /* : unknown */) {
                return new Some([state /* : CompileState */, /* resolved */]) /* : Some */;
            }
            else {
                return new Some([state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */]) /* : Some */;
            }
        }
        return this /* : Main */.parseTemplate /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Type]> */.or /* : (arg0 : () => Option<[CompileState, Type]>) => Option<[CompileState, Type]> */(() => this /* : Main */.varArgs /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Type]> */) /* : Option<[CompileState, Type]> */;
    }
    varArgs(state, input) {
        return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input /* : string */, "...", (s) => {
            return this /* : Main */.parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */, s /* : string */) /* : Option<[CompileState, Type]> */.map /* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((inner) => {
                let newState = inner /* : [CompileState, Type] */[0 /* : number */];
                let child = inner /* : [CompileState, Type] */[1 /* : number */];
                return [newState /* : unknown */, new ArrayType(child /* : unknown */) /* : ArrayType */];
            }) /* : Option<R> */;
        }) /* : Option<T> */;
    }
    assembleTemplate(base, state, arguments) {
        let children = arguments /* : List<Argument> */.query /* : () => Query<Argument> */() /* : Query<Argument> */.map /* : (arg0 : (arg0 : Argument) => R) => Query<R> */(this /* : Main */.retainType /* : unknown */) /* : Query<R> */.flatMap /* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries /* : Queries */.fromOption /* : unknown */) /* : Option<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        if (base /* : string */ === "BiFunction") {
            return [state /* : CompileState */, new FunctionType(Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(children /* : unknown */.get /* : unknown */(0 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */, children /* : unknown */.get /* : unknown */(1 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : List<T> */, children /* : unknown */.get /* : unknown */(2 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : FunctionType */];
        }
        if (base /* : string */ === "Function") {
            return [state /* : CompileState */, new FunctionType(Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(children /* : unknown */.get /* : unknown */(0 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : List<T> */, children /* : unknown */.get /* : unknown */(1 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : FunctionType */];
        }
        if (base /* : string */ === "Predicate") {
            return [state /* : CompileState */, new FunctionType(Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(children /* : unknown */.get /* : unknown */(0 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : List<T> */, Primitive /* : Primitive */.Boolean /* : unknown */) /* : FunctionType */];
        }
        if (base /* : string */ === "Supplier") {
            return [state /* : CompileState */, new FunctionType(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, children /* : unknown */.get /* : unknown */(0 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : FunctionType */];
        }
        if (base /* : string */ === "Consumer") {
            return [state /* : CompileState */, new FunctionType(Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(children /* : unknown */.get /* : unknown */(0 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : List<T> */, Primitive /* : Primitive */.Void /* : unknown */) /* : FunctionType */];
        }
        if (base /* : string */ === "Tuple2" && children /* : unknown */.size /* : unknown */() /* : unknown */ >= 2 /* : number */) {
            return [state /* : CompileState */, new TupleType(children /* : unknown */) /* : TupleType */];
        }
        if (state /* : CompileState */.resolveType /* : (arg0 : string) => Option<Type> */(base /* : string */) /* : Option<Type> */._OptionVariant /* : unknown */ === OptionVariant.Some /* : unknown */) {
            let baseType = some /* : Some<Type> */.value /* : Type */;
            if (baseType /* : Type */._TypeVariant /* : unknown */ === TypeVariant.ObjectType /* : unknown */) {
                let some = state /* : CompileState */.resolveType /* : (arg0 : string) => Option<Type> */(base /* : string */) /* : Option<Type> */;
                let findableType = baseType /* : Type */;
                return [state /* : CompileState */, new Template(findableType /* : ObjectType */, children /* : unknown */) /* : Template */];
            }
        }
        return [state /* : CompileState */, new Template(new ObjectType(base /* : string */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : ObjectType */, children /* : unknown */) /* : Template */];
    }
    parseTemplate(state, input) {
        return this /* : Main */.suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, ">", (withoutEnd) => {
            return this /* : Main */.first /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutEnd /* : string */, "<", (base, argumentsString) => {
                let strippedBase = base /* : unknown */.strip /* : unknown */() /* : unknown */;
                return this /* : Main */.parseValues /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, argumentsString /* : unknown */, this /* : Main */.parseArgument /* : unknown */) /* : Option<[CompileState, List<T>]> */.map /* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((argumentsTuple) => {
                    return this /* : Main */.assembleTemplate /* : (arg0 : string, arg1 : CompileState, arg2 : List<Argument>) => [CompileState, Type] */(strippedBase /* : unknown */, argumentsTuple /* : [CompileState, List<T>] */[0 /* : number */], argumentsTuple /* : [CompileState, List<T>] */[1 /* : number */]) /* : [CompileState, Type] */;
                }) /* : Option<R> */;
            }) /* : Option<T> */;
        }) /* : Option<T> */;
    }
    retainType(argument) {
        if (argument /* : Argument */._ArgumentVariant /* : unknown */ === ArgumentVariant.Type /* : unknown */) {
            let type = argument /* : Argument */;
            return new Some(type /* : Type */) /* : Some */;
        }
        else {
            return new None() /* : None<Type> */;
        }
    }
    parseArgument(state, input) {
        if (Strings /* : Strings */.isBlank /* : (arg0 : string) => boolean */(input /* : string */) /* : boolean */) {
            return new Some([state /* : CompileState */, new Whitespace() /* : Whitespace */]) /* : Some */;
        }
        return this /* : Main */.parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Type]> */.map /* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((tuple) => [tuple /* : [CompileState, Type] */[0 /* : number */], tuple /* : [CompileState, Type] */[1 /* : number */]]) /* : Option<R> */;
    }
    last(input, infix, mapper) {
        return this /* : Main */.infix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<number>, arg3 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input /* : string */, infix /* : string */, this /* : Main */.findLast /* : unknown */, mapper /* : (arg0 : string, arg1 : string) => Option<T> */) /* : Option<T> */;
    }
    findLast(input, infix) {
        let index = input /* : string */.lastIndexOf /* : unknown */(infix /* : string */) /* : unknown */;
        if (index /* : unknown */ === -1 /* : number */) {
            return new None() /* : None<number> */;
        }
        return new Some(index /* : unknown */) /* : Some */;
    }
    first(input, infix, mapper) {
        return this /* : Main */.infix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<number>, arg3 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input /* : string */, infix /* : string */, this /* : Main */.findFirst /* : unknown */, mapper /* : (arg0 : string, arg1 : string) => Option<T> */) /* : Option<T> */;
    }
    split(splitter, splitMapper) {
        return splitter /* : () => Option<[string, string]> */() /* : Option<[string, string]> */.flatMap /* : (arg0 : (arg0 : [string, string]) => Option<R>) => Option<R> */((splitTuple) => splitMapper /* : (arg0 : string, arg1 : string) => Option<T> */(splitTuple /* : [string, string] */[0 /* : number */], splitTuple /* : [string, string] */[1 /* : number */]) /* : Option<T> */) /* : Option<R> */;
    }
    infix(input, infix, locator, mapper) {
        return this /* : Main */.split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => locator /* : (arg0 : string, arg1 : string) => Option<number> */(input /* : string */, infix /* : string */) /* : Option<number> */.map /* : (arg0 : (arg0 : number) => R) => Option<R> */((index) => {
            let left = input /* : string */.substring /* : unknown */(0 /* : number */, index /* : number */) /* : unknown */;
            let right = input /* : string */.substring /* : unknown */(index /* : number */ + Strings /* : Strings */.length /* : unknown */(infix /* : string */) /* : unknown */) /* : unknown */;
            return [left /* : unknown */, right /* : unknown */];
        }) /* : Option<R> */, mapper /* : (arg0 : string, arg1 : string) => Option<T> */) /* : Option<T> */;
    }
    findFirst(input, infix) {
        let index = input /* : string */.indexOf /* : unknown */(infix /* : string */) /* : unknown */;
        if (index /* : unknown */ === -1 /* : number */) {
            return new None() /* : None<number> */;
        }
        return new Some(index /* : unknown */) /* : Some */;
    }
}
Main.isDebugEnabled = true;
/*  */ 
