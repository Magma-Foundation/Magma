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
    constructor() {
        this._OptionVariant = OptionVariant.None;
    }
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
/* private */ class Some {
    constructor(value) {
        this._OptionVariant = OptionVariant.Some;
        this.value = value;
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
        return other().map((otherValue) => [this.value, otherValue]);
    }
    ifPresent(consumer) {
        /* consumer.accept(this.value) */ ;
    }
}
/* private static */ class SingleHead {
    constructor(retrievableValue) {
        this.retrievableValue = retrievableValue;
        this.retrieved = false;
    }
    next() {
        if (this.retrieved) {
            return new None();
        }
        this.retrieved = true;
        return new Some(this.retrievableValue);
    }
}
/* private static */ class EmptyHead {
    next() {
        return new None();
    }
}
/* private */ class HeadedQuery {
    constructor(head) {
        this.head = head;
    }
    fold(initial, folder) {
        let current = initial;
        while (true) {
            let finalCurrent = current;
            let option = this.head.next().map((inner) => folder(finalCurrent, inner));
            if (option._UnknownVariant === UnknownVariant.Some) {
                let some = option;
                current = some.value;
            }
            else {
                return current;
            }
        }
    }
    map(mapper) {
        return new HeadedQuery(new MapHead(this.head, mapper));
    }
    collect(collector) {
        return this.fold(collector.createInitial(), collector.fold);
    }
    filter(predicate) {
        return this.flatMap((element) => {
            if (predicate(element)) {
                return new HeadedQuery(new SingleHead(element));
            }
            return new HeadedQuery(new EmptyHead());
        });
    }
    next() {
        return this.head.next();
    }
    flatMap(f) {
        return new HeadedQuery(new FlatMapHead(this.head, f));
    }
    zip(other) {
        return new HeadedQuery(new ZipHead(this.head, other));
    }
}
/* private static */ class RangeHead /*  */ {
    constructor(length) {
        this.length = length;
        this.counter = 0;
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
/* private */ class Definition /*  */ {
    constructor(annotations, modifiers, name, type, typeParams) {
        this.annotations = annotations;
        this.modifiers = modifiers;
        this.name = name;
        this.type = type;
        this.typeParams = typeParams;
    }
    static createSimpleDefinition(name, type) {
        return new Definition(Lists.empty(), Lists.empty(), name, type, Lists.empty());
    }
    findName() {
        return this.name;
    }
    findType() {
        return this.type;
    }
    generate() {
        return this.generateWithParams("");
    }
    generateType() {
        if (this.type === Primitive.Unknown) {
            return "";
        }
        return " : " + this.type.generate();
    }
    joinModifiers() {
        return this.modifiers.iterate().map((value) => value + " ").collect(new Joiner("")).orElse("");
    }
    joinTypeParams() {
        return this.typeParams.iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
    }
    mapType(mapper) {
        return new Definition(this.annotations, this.modifiers, this.name, mapper(this.type), this.typeParams);
    }
    generateWithParams(joinedParameters) {
        let joinedAnnotations = this.annotations.iterate().map((value) => "@" + value + " ").collect(Joiner.empty()).orElse("");
        let joined = this.joinTypeParams();
        let before = this.joinModifiers();
        let typeString = this.generateType();
        return joinedAnnotations + before + this.name + joined + joinedParameters + typeString;
    }
    createDefinition(paramTypes) {
        let type1 = new FunctionType(paramTypes, this.type);
        return new Definition(this.annotations, this.modifiers, this.name, type1, this.typeParams);
    }
    containsAnnotation(annotation) {
        return this.annotations.contains(annotation);
    }
    removeAnnotations() {
        return new Definition(Lists.empty(), this.modifiers, this.name, this.type, this.typeParams);
    }
    toString() {
        return "ImmutableDefinition[" + "annotations=" + this.annotations + ", " + "maybeBefore=" + this.modifiers + ", " + "findName=" + this.name + ", " + "findType=" + this.type + ", " + "typeParams=" + this.typeParams + "]";
    }
}
/* private */ class ObjectRefType /*  */ {
    constructor(name) {
        this.name = name;
    }
    generate() {
        return this.name;
    }
    replace(mapping) {
        return new ObjectRefType(this.name);
    }
    findName() {
        return this.name;
    }
}
/* private */ class TypeParam /*  */ {
    constructor(value) {
        this.value = value;
    }
    generate() {
        return this.value;
    }
    replace(mapping) {
        return mapping.find(this.value).orElse(this);
    }
    findName() {
        return "";
    }
}
/* private */ class CompileState /*  */ {
    constructor(structures, definitions, structureTypes, structNames, typeParams, typeRegister, functionSegments) {
        this.structures = structures;
        this.definitions = definitions;
        this.structureTypes = structureTypes;
        this.structNames = structNames;
        this.typeParams = typeParams;
        this.typeRegister = typeRegister;
        this.functionSegments = functionSegments;
    }
    static createInitial() {
        return new CompileState(Lists.empty(), Lists.of(Lists.empty()), Lists.empty(), Lists.empty(), Lists.empty(), new None(), Lists.empty());
    }
    resolveValue(name) {
        return this.definitions.iterateReversed().flatMap(List.iterate).filter((definition) => definition.findName() === name).next().map(Definition.findType);
    }
    addStructure(structure) {
        return new CompileState(this.structures.addLast(structure), this.definitions, this.structureTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    defineAll(definitions) {
        let defined = this.definitions.mapLast((frame) => frame.addAllLast(definitions));
        return new CompileState(this.structures, defined, this.structureTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    resolveType(name) {
        let maybe = this.structNames.last().filter((inner) => inner.left() === name);
        if (maybe._OptionVariant === OptionVariant.Some) {
            let some = maybe;
            let found = some.value;
            return new Some(new ObjectRefType(found.left()));
        }
        let maybeTypeParam = this.typeParams.iterate().filter((param) => param === name).next();
        if (maybeTypeParam._UnknownVariant === UnknownVariant.Some) {
            let some = maybeTypeParam;
            return new Some(new TypeParam(some.value));
        }
        return this.findStructure(name).map((type) => type);
    }
    define(definition) {
        return new CompileState(this.structures, this.definitions.mapLast((frame) => frame.addLast(definition)), this.structureTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    pushStructName(definition) {
        return new CompileState(this.structures, this.definitions, this.structureTypes, this.structNames.addLast(definition), this.typeParams, this.typeRegister, this.functionSegments);
    }
    withTypeParams(typeParams) {
        return new CompileState(this.structures, this.definitions, this.structureTypes, this.structNames, this.typeParams.addAllLast(typeParams), this.typeRegister, this.functionSegments);
    }
    withExpectedType(type) {
        return new CompileState(this.structures, this.definitions, this.structureTypes, this.structNames, this.typeParams, new Some(type), this.functionSegments);
    }
    popStructName() {
        return new CompileState(this.structures, this.definitions, this.structureTypes, this.structNames.removeLast().map(Tuple2.left).orElse(this.structNames), this.typeParams, this.typeRegister, this.functionSegments);
    }
    enterDefinitions() {
        return new CompileState(this.structures, this.definitions.addLast(Lists.empty()), this.structureTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    exitDefinitions() {
        let removed = this.definitions.removeLast().map(Tuple2.left).orElse(this.definitions);
        return new CompileState(this.structures, removed, this.structureTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    addType(thisType) {
        return new CompileState(this.structures, this.definitions, this.structureTypes.addLast(thisType), this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
    }
    addFunctionSegment(segment) {
        return new CompileState(this.structures, this.definitions, this.structureTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments.addLast(segment));
    }
    clearFunctionSegments() {
        return new CompileState(this.structures, this.definitions, this.structureTypes, this.structNames, this.typeParams, this.typeRegister, Lists.empty());
    }
    isCurrentStructName(stripped) {
        return stripped === this.structNames.last().map(Tuple2.left).orElse("");
    }
    findStructure(name) {
        return this.structureTypes.iterate().filter((structureType) => structureType.name === name).next();
    }
}
/* private */ class StructureType /*  */ {
    constructor(name, variants, definitions) {
        this.name = name;
        this.variants = variants;
        this.definitions = definitions;
    }
    hasVariant(name) {
        return this.variants.contains(name);
    }
    generate() {
        return this.name;
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return this.name;
    }
    find(property) {
        return this.definitions.iterate().filter((definition) => definition.name === property).map(Definition.type).next();
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
    static createInitial(input) {
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
        if (this.index < Strings.length(this.input)) {
            let c = this.input.charAt(this.index);
            return new Some([c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)]);
        }
        return new None();
    }
    popAndAppendToTuple() {
        return this.pop().map((tuple) => {
            let c = tuple.left();
            let right = tuple.right();
            return [c, right.append(c)];
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
        this.delimiter = delimiter;
    }
    static empty() {
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
                let inner = this.current.orElse(null);
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
        this.right = right;
    }
    generate() {
        return this.right.generate() + "[]";
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
        return new None();
    }
}
/* private static */ class Queries /*  */ {
    static fromOption(option) {
        let single = option.map(SingleHead.new);
        return new HeadedQuery(single.orElseGet(EmptyHead.new));
    }
    static from(elements) {
        return new HeadedQuery(new RangeHead(elements.length)).map((index) =>  /* elements[index] */);
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
        this.arguments = arguments;
        this.returns = returns;
    }
    generate() {
        let joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left() + " : " + pair.right().generate()).collect(new Joiner(", ")).orElse("");
        return "(" + joined + ") => " + this.returns.generate();
    }
    replace(mapping) {
        return new FunctionType(this.arguments.iterate().map((type) => type.replace(mapping)).collect(new ListCollector()), this.returns.replace(mapping));
    }
    findName() {
        return "";
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
        this.arguments = arguments;
    }
    generate() {
        let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).orElse("");
        return "[" + joinedArguments + "]";
    }
    replace(mapping) {
        return new TupleType(this.arguments.iterate().map((child) => child.replace(mapping)).collect(new ListCollector()));
    }
    findName() {
        return "";
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
        this.base = base;
        this.arguments = arguments;
    }
    generate() {
        let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        return this.base.generate() + joinedArguments;
    }
    replace(mapping) {
        let collect = this.arguments.iterate().map((argument) => argument.replace(mapping)).collect(new ListCollector());
        return new Template(this.base, collect);
    }
    findName() {
        return this.base.findName();
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
        this.input = input;
    }
    generate() {
        return generatePlaceholder(this.input);
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return "";
    }
    maybeCreateDefinition() {
        return new None();
    }
}
/* private */ class StringValue /*  */ {
    constructor(value) {
        this.value = value;
    }
    generate() {
        return "\"" + this.value + "\"";
    }
}
/* private */ class DataAccess /*  */ {
    constructor(parent, property) {
        this.parent = parent;
        this.property = property;
    }
    generate() {
        return this.parent.generate() + "." + this.property;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(type) {
        this.type = type;
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
        this.GREATER_THAN_OR_EQUALS = Operator();
        this.LESS_THAN = Operator();
        this.sourceRepresentation = sourceRepresentation;
        this.targetRepresentation = targetRepresentation;
    }
}
Operator.ADD = new Operator("+", "+");
Operator.AND = new Operator("&&", "&&");
Operator.EQUALS = new Operator("==", "===");
Operator.OR = new Operator("||", "||");
Operator.SUBTRACT = new Operator("-", "-");
/* private */ class Operation /*  */ {
    constructor(left, operator, right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    generate() {
        return this.left().generate() + " " + this.operator.targetRepresentation + " " + this.right().generate();
    }
}
/* private */ class Not /*  */ {
    constructor(value) {
        this.value = value;
    }
    generate() {
        return "!" + this.value.generate();
    }
}
/* private */ class BlockLambdaValue /*  */ {
    constructor(depth, statements) {
        this.depth = depth;
        this.statements = statements;
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
        this.parameters = parameters;
        this.body = body;
    }
    generate() {
        let joined = this.parameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
        return "(" + joined + ") => " + this.body.generate();
    }
}
/* private */ class Invokable /*  */ {
    constructor(caller, arguments, type) {
        this.caller = caller;
        this.arguments = arguments;
        this.type = type;
    }
    generate() {
        let joined = this.arguments.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
        return this.caller.generate() + "(" + joined + ")";
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
        this.parent = parent;
        this.child = child;
    }
    generate() {
        return this.parent.generate() + "[" + this.child.generate() + "]";
    }
}
/* private */ class SymbolValue /*  */ {
    constructor(value) {
        this.value = value;
    }
    generate() {
        return this.value;
    }
}
/* private static */ class Maps /*  */ {
}
/* private static */ class ConstructorHeader /*  */ {
    createDefinition(paramTypes) {
        return Definition.createSimpleDefinition("new", Primitive.Unknown);
    }
    generateWithParams(joinedParameters) {
        return "constructor " + joinedParameters;
    }
}
/* private */ class FunctionNode /*  */ {
    constructor(depth, header, parameters, maybeStatements) {
        this.depth = depth;
        this.header = header;
        this.parameters = parameters;
        this.maybeStatements = maybeStatements;
    }
    static joinStatements(statements) {
        return statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
    }
    generate() {
        let indent = createIndent(this.depth);
        let generatedHeader = this.header.generateWithParams(joinValues(this.parameters));
        let generatedStatements = this.maybeStatements.map(FunctionNode.joinStatements).map((inner) => " {" + inner + indent + "}").orElse(";");
        return indent + generatedHeader + generatedStatements;
    }
}
/* private */ class Block /*  */ {
    constructor(depth, header, statements) {
        this.depth = depth;
        this.header = header;
        this.statements = statements;
    }
    generate() {
        let indent = createIndent(this.depth);
        let collect = this.statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
        return indent + this.header.generate() + "{" + collect + indent + "}";
    }
}
/* private */ class Conditional /*  */ {
    constructor(prefix, value1) {
        this.prefix = prefix;
        this.value1 = value1;
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
        this.value = value;
    }
    generate() {
        return "return " + this.value.generate();
    }
}
/* private */ class Initialization /*  */ {
    constructor(definition, source) {
        this.definition = definition;
        this.source = source;
    }
    generate() {
        return "let " + this.definition.generate() + " = " + this.source.generate();
    }
}
/* private */ class FieldInitialization /*  */ {
    constructor(definition, source) {
        this.definition = definition;
        this.source = source;
    }
    generate() {
        return this.definition.generate() + " = " + this.source.generate();
    }
}
/* private */ class Assignment /*  */ {
    constructor(destination, source) {
        this.destination = destination;
        this.source = source;
    }
    generate() {
        return this.destination.generate() + " = " + this.source.generate();
    }
}
/* private */ class Statement /*  */ {
    constructor(depth, value) {
        this.depth = depth;
        this.value = value;
    }
    generate() {
        return createIndent(this.depth) + this.value.generate() + ";";
    }
}
/* private */ class MethodPrototype /*  */ {
    constructor(depth, header, parameters, content) {
        this.depth = depth;
        this.header = header;
        this.parameters = parameters;
        this.content = content;
    }
    createDefinition() {
        return this.header.createDefinition(this.findParamTypes());
    }
    findParamTypes() {
        return this.parameters().iterate().map(Definition.findType).collect(new ListCollector());
    }
    maybeCreateDefinition() {
        return new Some(this.header.createDefinition(this.findParamTypes()));
    }
}
/* private */ class IncompleteClassSegmentWrapper /*  */ {
    constructor(segment) {
        this.segment = segment;
    }
    maybeCreateDefinition() {
        return new None();
    }
}
/* private */ class ClassDefinition /*  */ {
    constructor(depth, definition) {
        this.depth = depth;
        this.definition = definition;
    }
    maybeCreateDefinition() {
        return new Some(this.definition);
    }
}
/* private */ class ClassInitialization /*  */ {
    constructor(depth, definition, value) {
        this.depth = depth;
        this.definition = definition;
        this.value = value;
    }
    maybeCreateDefinition() {
        return new Some(this.definition);
    }
}
/* private */ class TypeRef /*  */ {
    constructor(value) {
        this.value = value;
    }
}
/* private */ class StructurePrototype /*  */ {
    constructor(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants, interfaces, superTypes) {
        this.targetInfix = targetInfix;
        this.beforeInfix = beforeInfix;
        this.name = name;
        this.typeParams = typeParams;
        this.parameters = parameters;
        this.after = after;
        this.segments = segments;
        this.variants = variants;
        this.interfaces = interfaces;
        this.superTypes = superTypes;
    }
    static generateEnumEntries(variants) {
        return variants.iterate().map((inner) => "\n\t" + inner).collect(new Joiner(",")).orElse("");
    }
    createObjectType() {
        let definitionFromSegments = this.segments.iterate().map(IncompleteClassSegment.maybeCreateDefinition).flatMap(Queries.fromOption).collect(new ListCollector());
        return new StructureType(this.name, this.variants, definitionFromSegments);
    }
    maybeCreateDefinition() {
        return new None();
    }
    joinTypeParams() {
        return this.typeParams().iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
    }
    generateToEnum() {
        let variants = this.variants;
        let joined = generateEnumEntries(variants);
        return "enum " + this.name + "Variant" + " {" + joined + "\n}\n";
    }
}
/* private */ class Cast /*  */ {
    constructor(value, type) {
        this.value = value;
        this.type = type;
    }
    generate() {
        return this.value.generate() + " as " + this.type.generate();
    }
}
/* private */ class Ok {
    constructor(value) {
        this._ResultVariant = ResultVariant.Ok;
        this.value = value;
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
        this._ResultVariant = ResultVariant.Err;
        this.error = error;
    }
    mapValue(mapper) {
        return new Err(this.error);
    }
    match(whenOk, whenErr) {
        return whenErr(this.error);
    }
}
/* private */ class JVMIOError /*  */ {
    constructor(error) {
        this.error = error;
    }
    display() {
        let writer = new StringWriter();
        /* this.error.printStackTrace(new PrintWriter(writer)) */ ;
        return writer.toString();
    }
}
/* private */ class TupleNode /*  */ {
    constructor(values) {
        this.values = values;
    }
    generate() {
        let joined = this.values.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
        return "[" + joined + "]";
    }
}
/* private */ class MapHead {
    constructor(head, mapper) {
        this.head = head;
        this.mapper = mapper;
    }
    next() {
        return this.head.next().map(this.mapper);
    }
}
/* private */ class ZipHead {
    constructor(head, other) {
        this.head = head;
        this.other = other;
    }
    next() {
        return this.head.next().and(this.other.next);
    }
}
/* private */ class EnumValue /*  */ {
    constructor(value, values) {
        this.value = value;
        this.values = values;
    }
    generate() {
        let s = this.values.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
        return this.value + "(" + s + ")";
    }
}
/* private */ class EnumValues /*  */ {
    constructor(values) {
        this.values = values;
    }
    generate() {
        return this.values.iterate().map(EnumValue.generate).collect(new Joiner(", ")).orElse("");
    }
    maybeCreateDefinition() {
        return new None();
    }
}
/* public static */ class Strings /*  */ {
    static isBlank(input) {
        return input.isBlank();
    }
}
/* private */ class SymbolType /*  */ {
    constructor(value) {
        this.value = value;
    }
    generate() {
        return this.value;
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
        this.value = value;
    }
    generate() {
        return this.value;
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return this.name();
    }
}
Primitive.Int = new Primitive("number");
Primitive.String = new Primitive("string");
Primitive.Boolean = new Primitive("boolean");
Primitive.Unknown = new Primitive("unknown");
Primitive.Void = new Primitive("void");
/* private */ class BooleanValue /*  */ {
    constructor(value) {
        this.value = value;
    }
    generate() {
        return this.value;
    }
}
BooleanValue.True = new BooleanValue("true");
BooleanValue.False = new BooleanValue("false");
/* public */ class Main /*  */ {
    static generatePlaceholder(input) {
        let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
        return "/* " + replaced + " */";
    }
    static joinValues(retainParameters) {
        let inner = retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
        return "(" + inner + ")";
    }
    static createIndent(depth) {
        return "\n" + "\t".repeat(depth);
    }
    static createDebugString(type) {
        if (!Main.isDebugEnabled) {
            return "";
        }
        return generatePlaceholder(": " + type.generate());
    }
    static isSymbol(input) {
        /* for (var i = 0; i < Strings.length(input); i++) */ {
            let c = input.charAt(i);
            if (Character.isLetter(c) || ( /* i != 0  */ && Character.isDigit(c))) {
                /* continue */ ;
            }
            return false;
        }
        return true;
    }
    static parseWhitespace(input, state) {
        if (Strings.isBlank(input)) {
            return new Some([state, new Whitespace()]);
        }
        return new None();
    }
    static retainObjectRefType(type) {
        if (type._Variant === Variant.) {
            let template = type;
            return new Some(template.base);
        }
        if (type._Variant === Variant.) {
            let objectRefType = type;
            return new Some(objectRefType);
        }
        return new None();
    }
    main() {
        let parent = this.findRoot();
        let source = parent.resolve("Main.java");
        let target = parent.resolve("main.ts");
        /* source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(error -> System.err.println(error.display())) */ ;
    }
    compile(input) {
        let state = CompileState.createInitial();
        let parsed = this.parseStatements(state, input, this.compileRootSegment);
        let joined = parsed[0].structures.iterate().collect(Joiner.empty()).orElse("");
        return joined + this.generateStatements(parsed[1]);
    }
    generateStatements(statements) {
        return this.generateAll(this.mergeStatements, statements);
    }
    parseStatements(state, input, mapper) {
        return this.parseAllWithIndices(state, input, this.foldStatementChar, (state3, tuple) => new Some(mapper(state3, tuple.right()))).orElseGet(() => [state, Lists.empty()]);
    }
    generateAll(merger, elements) {
        return elements.iterate().fold("", merger);
    }
    parseAllWithIndices(state, input, folder, mapper) {
        let stringList = this.divideAll(input, folder);
        return this.mapUsingState(state, stringList, mapper);
    }
    mapUsingState(state, elements, mapper) {
        return elements.iterateWithIndices().fold(new Some([state, Lists.empty()]), this.getOptionTuple2OptionBiFunction(mapper));
    }
    getOptionTuple2OptionBiFunction(mapper) {
        return (maybeCurrent, entry) => maybeCurrent.flatMap((current) => {
            let currentState = current.left();
            let currentList = current.right();
            return mapper(currentState, entry).map((applied) => {
                return [applied.left(), currentList.addLast(applied.right())];
            });
        });
    }
    mergeStatements(cache, statement) {
        return cache + statement;
    }
    divideAll(input, folder) {
        let current = DivideState.createInitial(input);
        while (true) {
            let maybePopped = current.pop().map((tuple) => this.foldDecorated(folder, tuple));
            if (maybePopped.isPresent()) {
                current = maybePopped.orElse(current);
            }
            else {
                /* break */ ;
            }
        }
        return current.advance().segments;
    }
    foldDecorated(folder, tuple) {
        return this.foldSingleQuotes(tuple).or(() => this.foldDoubleQuotes(tuple)).orElseGet(() => folder(tuple[1], tuple[0]));
    }
    foldDoubleQuotes(tuple) {
        if (tuple[0] === "\"") {
            let current = tuple[1].append(tuple[0]);
            while (true) {
                let maybePopped = current.popAndAppendToTuple();
                if (maybePopped.isEmpty()) {
                    /* break */ ;
                }
                let popped = maybePopped.orElse(null);
                current = popped.right();
                if (popped.left() === "\\") {
                    current = current.popAndAppendToOption().orElse(current);
                }
                if (popped.left() === "\"") {
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
        let appended = tuple[1].append(tuple[0]);
        return appended.popAndAppendToTuple().map(this.foldEscaped).flatMap(DivideState.popAndAppendToOption);
    }
    foldEscaped(escaped) {
        if (escaped[0] === "\\") {
            return escaped[1].popAndAppendToOption().orElse(escaped[1]);
        }
        return escaped[1];
    }
    foldStatementChar(state, c) {
        let append = state.append(c);
        if (c === ";" && append.isLevel()) {
            return append.advance();
        }
        if (c === "}" && append.isShallow()) {
            return append.advance().exit();
        }
        if (c === "{" || c === "(") {
            return append.enter();
        }
        if (c === "}" || c === ")") {
            return append.exit();
        }
        return append;
    }
    compileRootSegment(state, input) {
        let stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return [state, ""];
        }
        return this.parseClass(stripped, state).flatMap((tuple) => this.completeClassSegment(tuple.left(), tuple.right())).map((tuple0) => [tuple0.left(), tuple0.right().generate()]).orElseGet(() => [state, generatePlaceholder(stripped)]);
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
                        return this.parseStructureWithMaybePermits(targetInfix, state, s2, beforeContent, content1, annotations);
                    }).or(() => {
                        return this.parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
                    });
                });
            });
        });
    }
    parseAnnotations(annotationsString) {
        return this.divideAll(annotationsString.strip(), (state1, c) => this.foldByDelimiter(state1, c, "\n")).iterate().map(String.strip).filter((value) => !value.isEmpty()).map((value) => value.substring(1)).map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector());
    }
    foldByDelimiter(state1, c, delimiter) {
        if (c === delimiter) {
            return state1.advance();
        }
        return state1.append(c);
    }
    parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, annotations) {
        return this.last(beforeContent, " permits ", (s, s2) => {
            let variants = this.divideAll(s2, this.foldValueChar).iterate().map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector());
            return this.parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, s, content1, variants, annotations);
        }).or(() => this.parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), annotations));
    }
    parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations) {
        return this.first(beforeContent, " implements ", (s, s2) => {
            let stringList = this.parseTypeRefs(s2);
            return this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, s, content1, variants, annotations, stringList);
        }).or(() => this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, Lists.empty()));
    }
    parseTypeRefs(s2) {
        return this.divideAll(s2, this.foldValueChar).iterate().map(String.strip).filter((value) => !value.isEmpty()).map(TypeRef.new).collect(new ListCollector());
    }
    parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, interfaces) {
        return this.first(beforeContent, " extends ", (s, s2) => this.parseStructureWithMaybeParams(targetInfix, state, beforeInfix, s, content1, variants, annotations, this.parseTypeRefs(s2), interfaces)).or(() => this.parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, Lists.empty(), interfaces));
    }
    parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, superTypes, interfaces) {
        return this.suffix(beforeContent.strip(), ")", (s) => {
            return this.first(s, "(", (s1, s2) => {
                let parsed = this.parseParameters(state, s2);
                return this.parseStructureWithMaybeTypeParams(targetInfix, parsed[0], beforeInfix, s1, content1, parsed[1], variants, annotations, interfaces, superTypes);
            });
        }).or(() => {
            return this.parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), variants, annotations, interfaces, superTypes);
        });
    }
    parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, params, variants, annotations, interfaces, maybeSuperType) {
        return this.first(beforeContent, "<", (name, withTypeParams) => {
            return this.first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
                let readonly, mapper = (state1, s) => [state1, s.strip()];
                let typeParams = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(mapper(state1, s)));
                return this.assembleStructure(typeParams[0], targetInfix, annotations, beforeInfix, name, content1, typeParams[1], afterTypeParams, params, variants, interfaces, maybeSuperType);
            });
        }).or(() => {
            return this.assembleStructure(state, targetInfix, annotations, beforeInfix, beforeContent, content1, Lists.empty(), "", params, variants, interfaces, maybeSuperType);
        });
    }
    assembleStructure(state, targetInfix, annotations, beforeInfix, rawName, content, typeParams, after, rawParameters, variants, interfaces, maybeSuperType) {
        let name = rawName.strip();
        if (!isSymbol(name)) {
            return new None();
        }
        if (annotations.contains("Actual")) {
            return new Some([state, new Whitespace()]);
        }
        let segmentsTuple = this.parseStatements(state.pushStructName([name, variants]).withTypeParams(typeParams), content, (state0, input) => this.parseClassSegment(state0, input, 1));
        let segmentsState = segmentsTuple[0];
        let segments = segmentsTuple[1];
        let parameters = this.retainDefinitions(rawParameters);
        let prototype = new StructurePrototype(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants, interfaces, maybeSuperType);
        return new Some([segmentsState.addType(prototype.createObjectType()), prototype]);
    }
    completeStructure(state, prototype) {
        let thisType = prototype.createObjectType();
        let withThis = state.enterDefinitions().define(Definition.createSimpleDefinition("this", thisType));
        return this.resolveTypeRefs(withThis, prototype.interfaces).flatMap((interfacesTuple) => {
            return this.resolveTypeRefs(interfacesTuple.left(), prototype.superTypes).flatMap((superTypesTuple) => {
                let interfaces = interfacesTuple.right();
                let superTypes = superTypesTuple.right();
                let bases = this.resolveBaseTypes(interfaces).addAllLast(this.resolveBaseTypes(superTypes));
                let left = superTypesTuple.left();
                let variantsSuper = this.findBaseNamesOfVariants(left, bases, prototype.name);
                return this.mapUsingState(left, prototype.segments(), this.createClassSegmentRule()).map(this.completeStructureWithStatements(prototype, variantsSuper, thisType, interfaces));
            });
        });
    }
    completeStructureWithStatements(prototype, variantsSuper, thisType, interfaces) {
        /* return oldStatementsTuple -> */ {
            let exited = oldStatementsTuple.left().exitDefinitions();
            let oldStatements = oldStatementsTuple.right();
            let withEnumCategoriesDefinedTuple = this.defineEnumCategories(exited, oldStatements, prototype.name, prototype.variants, prototype.generateToEnum());
            let withEnumCategoriesDefinedState = withEnumCategoriesDefinedTuple[0];
            let withEnumCategoriesDefined = withEnumCategoriesDefinedTuple[1];
            let withEnumCategoriesImplemented = this.implementEnumCategories(prototype.name, variantsSuper, withEnumCategoriesDefined);
            let withConstructor = this.defineConstructor(withEnumCategoriesImplemented, prototype.parameters());
            let withEnumValues = this.implementEnumValues(withConstructor, thisType);
            let generatedSegments = this.joinSegments(withEnumValues);
            let joinedTypeParams = prototype.joinTypeParams();
            let interfacesJoined = this.joinInterfaces(interfaces);
            let generatedSuperType = this.joinSuperTypes(withEnumCategoriesDefinedState, prototype);
            let generated = generatePlaceholder(prototype.beforeInfix().strip()) + prototype.targetInfix() + prototype.name() + joinedTypeParams + generatePlaceholder(prototype.after()) + generatedSuperType + interfacesJoined + " {" + generatedSegments + "\n}\n";
            let compileState = withEnumCategoriesDefinedState.popStructName();
            let definedState = compileState.addStructure(generated);
            return [definedState, new Whitespace()];
        }
        /*  */ ;
    }
    createClassSegmentRule() {
        return (state1, entry) => this.completeClassSegment(state1, entry.right());
    }
    resolveTypeRefs(state, refs) {
        return this.mapUsingState(state, refs, (state2, tuple) => this.parseType(state2, tuple.right().value));
    }
    joinSuperTypes(state, prototype) {
        return prototype.superTypes.iterate().map((value) => state.resolveType(value.value)).flatMap(Queries.fromOption).map(Type.generate).collect(new Joiner(", ")).map((generated) => " extends " + generated).orElse("");
    }
    implementEnumValues(withConstructor, thisType) {
        return withConstructor.iterate().flatMap((segment) => this.flattenEnumValues(segment, thisType)).collect(new ListCollector());
    }
    defineEnumCategories(state, segments, name, variants, enumGenerated) {
        if (variants.isEmpty()) {
            return [state, segments];
        }
        let enumState = state.addStructure(enumGenerated);
        let enumType = new ObjectRefType(name + "Variant");
        let enumDefinition = this.createVariantDefinition(enumType);
        return [enumState, segments.addFirst(new Statement(1, enumDefinition))];
    }
    implementEnumCategories(name, variantsBases, oldStatements) {
        return variantsBases.iterate().fold(oldStatements, (classSegmentList, superType) => {
            let variantTypeName = superType + "Variant";
            let variantType = new ObjectRefType(variantTypeName);
            let definition = this.createVariantDefinition(variantType);
            let source = new SymbolValue(variantTypeName + "." + name);
            let initialization = new FieldInitialization(definition, source);
            return classSegmentList.addFirst(new Statement(1, initialization));
        });
    }
    findBaseNamesOfVariants(state, refs, name) {
        return refs.iterate().map((base) => state.findStructure(base.name)).flatMap(Queries.fromOption).filter((type) => type.hasVariant(name)).map(StructureType.name).collect(new ListCollector());
    }
    resolveBaseTypes(interfaces) {
        return interfaces.iterate().map(Main.retainObjectRefType).flatMap(Queries.fromOption).collect(new ListCollector());
    }
    joinSegments(segmentsWithMaybeConstructor) {
        return segmentsWithMaybeConstructor.iterate().map(ClassSegment.generate).collect(Joiner.empty()).orElse("");
    }
    joinInterfaces(interfaces) {
        return interfaces.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => " implements " + inner).orElse("");
    }
    flattenEnumValues(segment, thisType) {
        if (segment._Variant === Variant.) {
            let enumValues = segment;
            return enumValues.values.iterate().map((enumValue) => {
                let definition = new Definition(Lists.empty(), Lists.of("static"), enumValue.value, thisType, Lists.empty());
                return new Statement(1, new FieldInitialization(definition, new Invokable(new ConstructionCaller(thisType), enumValue.values, thisType)));
            });
        }
        return Queries.from(segment);
    }
    createVariantDefinition(type) {
        return Definition.createSimpleDefinition("_" + type.name, type);
    }
    defineConstructor(segments, parameters) {
        if (parameters.isEmpty()) {
            return segments;
        }
        let definitions = parameters.iterate(). < /* ClassSegment>map */ ((definition) => new Statement(1, definition)).collect(new ListCollector());
        let collect = /* parameters.iterate()
                .map(definition  */ -(destination,
        /*  new SymbolValue(definition.findName()));
    } */). < /* FunctionSegment>map */ ((assignment) => new Statement(2, assignment)).collect(new ListCollector());
        let func = new FunctionNode(1, new ConstructorHeader(), parameters, new Some(collect));
        return segments.addFirst(func).addAllFirst(definitions);
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
        let definition = classInitialization.definition;
        let statement = new Statement(classInitialization.depth, new FieldInitialization(definition, classInitialization.value));
        return new Some([state1, statement]);
    }
    completeDefinition(state1, classDefinition) {
        let definition = classDefinition.definition;
        let statement = new Statement(classDefinition.depth, definition);
        return new Some([state1, statement]);
    }
    retainDefinition(parameter) {
        if (parameter._Variant === Variant.) {
            let definition = parameter;
            return new Some(definition);
        }
        return new None();
    }
    prefix(input, prefix, mapper) {
        if (!input.startsWith(prefix)) {
            return new None();
        }
        let slice = input.substring(Strings.length(prefix));
        return mapper(slice);
    }
    suffix(input, suffix, mapper) {
        if (!input.endsWith(suffix)) {
            return new None();
        }
        let slice = input.substring(0, Strings.length(input) - Strings.length(suffix));
        return mapper(slice);
    }
    parseClassSegment(state, input, depth) {
        return this. < /* Whitespace, IncompleteClassSegment>typed */ (() => parseWhitespace(input, state)).or(() => this.typed(() => this.parseClass(input, state))).or(() => this.typed(() => this.parseStructure(input, "interface ", "interface ", state))).or(() => this.typed(() => this.parseStructure(input, "record ", "class ", state))).or(() => this.typed(() => this.parseStructure(input, "enum ", "class ", state))).or(() => this.typed(() => this.parseField(input, depth, state))).or(() => this.parseMethod(state, input, depth)).or(() => this.parseEnumValues(state, input)).orElseGet(() => [state, new Placeholder(input)]);
    }
    parseEnumValues(state, input) {
        return this.suffix(input.strip(), ";", (withoutEnd) => {
            return this.parseValues(state, withoutEnd, (state2, enumValue) => {
                return this.suffix(enumValue.strip(), ")", (withoutValueEnd) => {
                    return this.first(withoutValueEnd, "(", (s4, s2) => {
                        return this.parseValues(state2, s2, (state1, s1) => new Some(Main.this.parseArgument(state1, s1, 1))).map((arguments) => {
                            return [arguments.left(), new EnumValue(s4, Main.this.retainValues(arguments.right()))];
                        });
                    });
                });
            }).map((tuple) => {
                return [tuple.left(), new EnumValues(tuple.right())];
            });
        });
    }
    typed(action) {
        return action().map((tuple) => [tuple.left(), tuple.right()]);
    }
    parseMethod(state, input, depth) {
        return this.first(input, "(", (definitionString, withParams) => {
            return this.first(withParams, ")", (parametersString, rawContent) => {
                return this.parseDefinition(state, definitionString). < Tuple2 < /* CompileState, Header>>map */ ((tuple) => [tuple.left(), tuple.right()]).or(() => this.parseConstructor(state, definitionString)).flatMap((definitionTuple) => this.assembleMethod(depth, parametersString, rawContent, definitionTuple));
            });
        });
    }
    assembleMethod(depth, parametersString, rawContent, definitionTuple) {
        let definitionState = definitionTuple[0];
        let header = definitionTuple[1];
        let parametersTuple = this.parseParameters(definitionState, parametersString);
        let rawParameters = parametersTuple[1];
        let parameters = this.retainDefinitions(rawParameters);
        let prototype = new MethodPrototype(depth, header, parameters, rawContent.strip());
        return new Some([parametersTuple[0].define(prototype.createDefinition()), prototype]);
    }
    completeMethod(state, prototype) {
        let definition = prototype.createDefinition();
        let oldHeader = prototype.header();
        /* Header newHeader */ ;
        if (oldHeader._UnknownVariant === UnknownVariant.) {
            let maybeDefinition = oldHeader;
            newHeader = maybeDefinition.removeAnnotations();
        }
        else {
            newHeader = oldHeader;
        }
        if (prototype.content() === ";" || definition.containsAnnotation("Actual")) {
            return new Some([state.define(definition), new FunctionNode(prototype.depth(), newHeader, prototype.parameters(), new None())]);
        }
        if (prototype.content().startsWith("{") && prototype.content().endsWith("}")) {
            let substring = prototype.content().substring(1, Strings.length(prototype.content()) - 1);
            let withDefined = state.enterDefinitions().defineAll(prototype.parameters());
            let statementsTuple = this.parseStatements(withDefined, substring, (state1, input1) => this.parseFunctionSegment(state1, input1, prototype.depth() + 1));
            let statements = statementsTuple[1];
            return new Some([statementsTuple[0].exitDefinitions().define(definition), new FunctionNode(prototype.depth(), newHeader, prototype.parameters(), new Some(statements))]);
        }
        return new None();
    }
    parseConstructor(state, input) {
        let stripped = input.strip();
        if (state.isCurrentStructName(stripped)) {
            return new Some([state, new ConstructorHeader()]);
        }
        return new None();
    }
    retainDefinitions(right) {
        return right.iterate().map(this.retainDefinition).flatMap(Queries.fromOption).collect(new ListCollector());
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
            return [state, new Whitespace()];
        }
        return this.parseFunctionStatement(state, depth, stripped).or(() => this.parseBlock(state, depth, stripped)).orElseGet(() => [state, new Placeholder(stripped)]);
    }
    parseFunctionStatement(state, depth, stripped) {
        return this.suffix(stripped, ";", (s) => {
            let tuple = this.parseStatementValue(state, s, depth);
            let left = tuple[0];
            let right = tuple[1];
            return new Some([left, new Statement(depth, right)]);
        });
    }
    parseBlock(state, depth, stripped) {
        return this.suffix(stripped, "}", (withoutEnd) => {
            return this.split(() => this.toFirst(withoutEnd), (beforeContent, content) => {
                return this.suffix(beforeContent, "{", (headerString) => {
                    let headerTuple = this.parseBlockHeader(state, headerString, depth);
                    let headerState = headerTuple[0];
                    let header = headerTuple[1];
                    let statementsTuple = this.parseFunctionSegments(headerState, content, depth);
                    let statementsState = statementsTuple[0];
                    let statements = statementsTuple[1].addAllFirst(statementsState.functionSegments);
                    return new Some([statementsState.clearFunctionSegments(), new Block(depth, header, statements)]);
                });
            });
        });
    }
    toFirst(input) {
        let divisions = this.divideAll(input, this.foldBlockStart);
        return divisions.removeFirst().map((removed) => {
            let right = removed.left();
            let left = removed.right().iterate().collect(new Joiner("")).orElse("");
            return [right, left];
        });
    }
    parseBlockHeader(state, input, depth) {
        let stripped = input.strip();
        return this.parseConditional(state, stripped, "if", depth).or(() => this.parseConditional(state, stripped, "while", depth)).or(() => this.parseElse(state, input)).orElseGet(() => [state, new Placeholder(stripped)]);
    }
    parseElse(state, input) {
        let stripped = input.strip();
        if (stripped === "else") {
            return new Some([state, new Else()]);
        }
        return new None();
    }
    parseConditional(state, input, prefix, depth) {
        return this.prefix(input, prefix, (withoutPrefix) => {
            return this.prefix(withoutPrefix.strip(), "(", (withoutValueStart) => {
                return this.suffix(withoutValueStart, ")", (value) => {
                    let valueTuple = this.parseValue(state, value, depth);
                    let value1 = valueTuple[1];
                    return new Some([valueTuple[0], new Conditional(prefix, value1)]);
                });
            });
        });
    }
    foldBlockStart(state, c) {
        let appended = state.append(c);
        if (c === "{" && state.isLevel()) {
            return appended.advance();
        }
        if (c === "{") {
            return appended.enter();
        }
        if (c === "}") {
            return appended.exit();
        }
        return appended;
    }
    parseStatementValue(state, input, depth) {
        let stripped = input.strip();
        if (stripped.startsWith("return ")) {
            let value = stripped.substring(Strings.length("return "));
            let tuple = this.parseValue(state, value, depth);
            let value1 = tuple[1];
            return [tuple[0], new Return(value1)];
        }
        return this.parseAssignment(state, depth, stripped).orElseGet(() => {
            return [state, new Placeholder(stripped)];
        });
    }
    parseAssignment(state, depth, stripped) {
        return this.first(stripped, "=", (beforeEquals, valueString) => {
            let sourceTuple = this.parseValue(state, valueString, depth);
            let sourceState = sourceTuple[0];
            let source = sourceTuple[1];
            let destinationTuple = this.parseValue(sourceState, beforeEquals, depth);
            let destinationState = destinationTuple[0];
            let destination = destinationTuple[1];
            return this.parseDefinition(destinationState, beforeEquals).flatMap((definitionTuple) => this.parseInitialization(definitionTuple.left(), definitionTuple.right(), source)).or(() => new Some([destinationState, new Assignment(destination, source)]));
        });
    }
    parseInitialization(state, destination, source) {
        let definition = destination.mapType((type) => {
            if (type === Primitive.Unknown) {
                return this.resolveValue(state, source);
            }
            else {
                return type;
            }
        });
        return new Some([state.define(definition), new Initialization(definition, source)]);
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
        return new TupleType(tupleNode.values.iterate().map((value) => this.resolveValue(state, value)).collect(new ListCollector()));
    }
    resolveInvokable(state, invokable) {
        let caller = invokable.caller;
        /* return switch (caller) */ {
            /* case ConstructionCaller constructionCaller -> constructionCaller.type */ ;
            /* case Value value1 -> */ {
                let type = this.resolveValue(state, value1);
                if (type._Variant === Variant.) {
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
        let parentType = this.resolveValue(state, dataAccess.parent);
        if (parentType._Variant === Variant.) {
            let structureType = parentType;
            return structureType.find(dataAccess.property).orElse(Primitive.Unknown);
        }
        return Primitive.Unknown;
    }
    parseValue(state, input, depth) {
        return this.parseBoolean(state, input).or(() => this.parseLambda(state, input, depth)).or(() => this.parseString(state, input)).or(() => this.parseDataAccess(state, input, depth)).or(() => this.parseSymbolValue(state, input)).or(() => this.parseInvokable(state, input, depth)).or(() => this.parseDigits(state, input)).or(() => this.parseInstanceOf(state, input, depth)).or(() => this.parseOperation(state, input, depth, Operator.ADD)).or(() => this.parseOperation(state, input, depth, Operator.EQUALS)).or(() => this.parseOperation(state, input, depth, Operator.SUBTRACT)).or(() => this.parseOperation(state, input, depth, Operator.AND)).or(() => this.parseOperation(state, input, depth, Operator.OR)).or(() => this.parseOperation(state, input, depth)).or(() => this.parseOperation(state, input, depth)).or(() => this.parseNot(state, input, depth)).or(() => this.parseMethodReference(state, input, depth)).or(() => this.parseChar(state, input)).orElseGet(() => [state, new Placeholder(input)]);
    }
    parseChar(state, input) {
        let stripped = input.strip();
        if (stripped.startsWith("'") && stripped.endsWith("'") && Strings.length(stripped) >= 2) {
            return new Some([state, new StringValue(stripped.substring(1, Strings.length(stripped) - 1))]);
        }
        return new None();
    }
    parseBoolean(state, input) {
        let stripped = input.strip();
        if (stripped === "false") {
            return new Some([state, BooleanValue.False]);
        }
        if (stripped === "true") {
            return new Some([state, BooleanValue.True]);
        }
        return new None();
    }
    parseInstanceOf(state, input, depth) {
        return this.last(input, "instanceof", (beforeKeyword, afterKeyword) => {
            let childTuple = this.parseValue(state, beforeKeyword, depth);
            return this.parseDefinition(childTuple[0], afterKeyword).map(this.parseInstanceOfWithValue(depth, childTuple[1]));
        });
    }
    parseInstanceOfWithValue(depth, child) {
        /* return definitionTuple -> */ {
            let definitionSTate = definitionTuple.left();
            let definition = definitionTuple.right();
            let type = this.resolveValue(definitionSTate, child);
            let variant = new DataAccess(child, "_" + type.findName() + "Variant");
            let generate = type.findName();
            let temp = new SymbolValue(generate + "Variant." + definition.findType().findName());
            let functionSegment = new Statement(depth + 1, new Initialization(definition, new Cast(child, definition.findType())));
            return [definitionSTate.addFunctionSegment(functionSegment).define(definition), new Operation(variant, Operator.EQUALS, temp)];
        }
        /*  */ ;
    }
    parseMethodReference(state, input, depth) {
        return this.last(input, "::", (s, s2) => {
            let tuple = this.parseValue(state, s, depth);
            return new Some([tuple[0], new DataAccess(tuple[1], s2)]);
        });
    }
    parseNot(state, input, depth) {
        let stripped = input.strip();
        if (stripped.startsWith("!")) {
            let slice = stripped.substring(1);
            let tuple = this.parseValue(state, slice, depth);
            let value = tuple[1];
            return new Some([tuple[0], new Not(value)]);
        }
        return new None();
    }
    parseLambda(state, input, depth) {
        return this.first(input, "->", (beforeArrow, valueString) => {
            let strippedBeforeArrow = beforeArrow.strip();
            if (isSymbol(strippedBeforeArrow)) {
                let type = Primitive.Unknown;
                if ( /* state.typeRegister instanceof Some */( /* var expectedType */)) {
                    if (expectedType._UnknownVariant === UnknownVariant.) {
                        let functionType = expectedType;
                        type = functionType.arguments.get(0).orElse(null);
                    }
                }
                return this.assembleLambda(state, Lists.of(Definition.createSimpleDefinition(strippedBeforeArrow, type)), valueString, depth);
            }
            if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")) {
                let parameterNames = this.divideAll(strippedBeforeArrow.substring(1, Strings.length(strippedBeforeArrow) - 1), this.foldValueChar).iterate().map(String.strip).filter((value) => !value.isEmpty()).map((name) => Definition.createSimpleDefinition(name, Primitive.Unknown)).collect(new ListCollector());
                return this.assembleLambda(state, parameterNames, valueString, depth);
            }
            return new None();
        });
    }
    assembleLambda(state, definitions, valueString, depth) {
        let strippedValueString = valueString.strip();
        /* Tuple2<CompileState, LambdaValue> value */ ;
        let state2 = state.defineAll(definitions);
        if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}")) {
            let value1 = this.parseStatements(state2, strippedValueString.substring(1, Strings.length(strippedValueString) - 1), (state1, input1) => this.parseFunctionSegment(state1, input1, depth + 1));
            let right = value1[1];
            value = [value1[0], new BlockLambdaValue(depth, right)];
        }
        else {
            let value1 = this.parseValue(state2, strippedValueString, depth);
            value = [value1[0], value1[1]];
        }
        let right = value.right();
        return new Some([value.left(), new Lambda(definitions, right)]);
    }
    parseDigits(state, input) {
        let stripped = input.strip();
        if (this.isNumber(stripped)) {
            return new Some([state, new SymbolValue(stripped)]);
        }
        return new None();
    }
    isNumber(input) {
        /* String maybeTruncated */ ;
        if (input.startsWith("-")) {
            maybeTruncated = input.substring(1);
        }
        else {
            maybeTruncated = input;
        }
        return this.areAllDigits(maybeTruncated);
    }
    areAllDigits(input) {
        /* for (var i = 0; i < Strings.length(input); i++) */ {
            let c = input.charAt(i);
            if (Character.isDigit(c)) {
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
        let oldCallerState = callerTuple[0];
        let oldCaller = callerTuple[1];
        let newCaller = this.modifyCaller(oldCallerState, oldCaller);
        let callerType = this.findCallerType(newCaller, oldCallerState);
        let argumentsTuple = this.parseValuesWithIndices(oldCallerState, argumentsString, (currentState, pair) => this.getTuple2Some(depth, currentState, pair, callerType)).orElseGet(() => [oldCallerState, Lists.empty()]);
        let argumentsState = argumentsTuple.left();
        let argumentsWithActualTypes = argumentsTuple.right();
        let arguments = this.retainValues(argumentsWithActualTypes.iterate().map(Tuple2.left).collect(new ListCollector()));
        if (newCaller._Variant === Variant.) {
            if (constructionCaller.type.findName() === "Tuple2Impl") {
                let constructionCaller = newCaller;
                return new Some([argumentsState, new TupleNode(Lists.of(arguments.get(0).orElse(null), arguments.get(1).orElse(null)))]);
            }
        }
        if (newCaller._Variant === Variant.) {
            if (value._Variant === Variant.) {
                let parent = access.parent;
                let property = access.property;
                let parentType = this.resolveValue(argumentsState, parent);
                if ( /* parentType instanceof TupleType */) {
                    if (property === "left") {
                        let value = newCaller;
                        let access = value;
                        return new Some([argumentsState, new IndexValue(parent, new SymbolValue("0"))]);
                    }
                    if (property === "right") {
                        return new Some([argumentsState, new IndexValue(parent, new SymbolValue("1"))]);
                    }
                }
                if (property === "equals") {
                    let first = arguments.get(0).orElse(null);
                    return new Some([argumentsState, new Operation(parent, Operator.EQUALS, first)]);
                }
            }
        }
        let invokable = new Invokable(newCaller, arguments, callerType.returns);
        return new Some([argumentsState, invokable]);
    }
    getTuple2Some(depth, currentState, pair, callerType) {
        let index = pair[0];
        let element = pair[1];
        let expectedType = callerType.arguments.get(index).orElse(Primitive.Unknown);
        let withExpected = currentState.withExpectedType(expectedType);
        let valueTuple = this.parseArgument(withExpected, element, depth);
        let valueState = valueTuple[0];
        let value = valueTuple[1];
        let actualType = valueTuple[0].typeRegister.orElse(Primitive.Unknown);
        return new Some([valueState, [value, actualType]]);
    }
    retainValues(arguments) {
        return arguments.iterate().map(this.retainValue).flatMap(Queries.fromOption).collect(new ListCollector());
    }
    retainValue(argument) {
        if (argument._Variant === Variant.) {
            let value = argument;
            return new Some(value);
        }
        return new None();
    }
    parseArgument(state, element, depth) {
        if (element.isEmpty()) {
            return [state, new Whitespace()];
        }
        let tuple = this.parseValue(state, element, depth);
        return [tuple[0], tuple[1]];
    }
    findCallerType(newCaller, state) {
        let callerType = new FunctionType(Lists.empty(), Primitive.Unknown);
        /* switch (newCaller) */ {
            /* case ConstructionCaller constructionCaller -> */ {
                callerType = constructionCaller.toFunction();
            }
            /* case Value value -> */ {
                let type = this.resolveValue(state, value);
                if (type._Variant === Variant.) {
                    let functionType = type;
                    callerType = functionType;
                }
            }
        }
        return callerType;
    }
    modifyCaller(state, oldCaller) {
        if (oldCaller._Variant === Variant.) {
            let type = this.resolveValue(state, access.parent);
            if ( /* type instanceof FunctionType */) {
                let access = oldCaller;
                return access.parent;
            }
        }
        return oldCaller;
    }
    invocationHeader(state, depth, callerString1) {
        if (callerString1.startsWith("new ")) {
            let input1 = callerString1.substring(Strings.length("new "));
            let map = this.parseType(state, input1).map((type) => {
                let right = type.right();
                return [type.left(), new ConstructionCaller(right)];
            });
            if (map.isPresent()) {
                return map.orElse(null);
            }
        }
        let tuple = this.parseValue(state, callerString1, depth);
        return [tuple[0], tuple[1]];
    }
    foldInvocationStart(state, c) {
        let appended = state.append(c);
        if (c === "(") {
            let enter = appended.enter();
            if (enter.isShallow()) {
                return enter.advance();
            }
            return enter;
        }
        if (c === ")") {
            return appended.exit();
        }
        return appended;
    }
    parseDataAccess(state, input, depth) {
        return this.last(input.strip(), ".", (parentString, rawProperty) => {
            let property = rawProperty.strip();
            if (!isSymbol(property)) {
                return new None();
            }
            let tuple = this.parseValue(state, parentString, depth);
            let parent = tuple[1];
            let parentType = this.resolveValue(tuple[0], parent);
            let type = Primitive.Unknown;
            if (parentType._Variant === Variant.) {
                if ( /* structureType.find(property) instanceof Some */( /* var memberType */)) {
                    let structureType = parentType;
                    type = memberType;
                }
            }
            return new Some([tuple[0], new DataAccess(parent, property)]);
        });
    }
    parseString(state, input) {
        let stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some([state, new StringValue(stripped.substring(1, Strings.length(stripped) - 1))]);
        }
        return new None();
    }
    parseSymbolValue(state, value) {
        let stripped = value.strip();
        if (isSymbol(stripped)) {
            return new Some([state, new SymbolValue(stripped)]);
        }
        return new None();
    }
    parseOperation(state, value, depth, operator) {
        return this.first(value, operator.sourceRepresentation, (leftString, rightString) => {
            let leftTuple = this.parseValue(state, leftString, depth);
            let rightTuple = this.parseValue(leftTuple[0], rightString, depth);
            let left = leftTuple[1];
            let right = rightTuple[1];
            return new Some([rightTuple[0], new Operation(left, operator, right)]);
        });
    }
    parseValuesOrEmpty(state, input, mapper) {
        return this.parseValues(state, input, mapper).orElseGet(() => [state, Lists.empty()]);
    }
    parseValues(state, input, mapper) {
        return this.parseValuesWithIndices(state, input, (state1, tuple) => mapper(state1, tuple.right()));
    }
    parseValuesWithIndices(state, input, mapper) {
        return this.parseAllWithIndices(state, input, this.foldValueChar, mapper);
    }
    parseParameter(state, input) {
        if (Strings.isBlank(input)) {
            return [state, new Whitespace()];
        }
        return this.parseDefinition(state, input).map((tuple) => [tuple.left(), tuple.right()]).orElseGet(() => [state, new Placeholder(input)]);
    }
    parseField(input, depth, state) {
        return this.suffix(input.strip(), ";", (withoutEnd) => {
            return this.parseClassInitialization(depth, state, withoutEnd).or(() => {
                return this.parseClassDefinition(depth, state, withoutEnd);
            });
        });
    }
    parseClassDefinition(depth, state, withoutEnd) {
        return this.parseDefinition(state, withoutEnd).map((result) => {
            return [result.left(), new ClassDefinition(depth, result.right())];
        });
    }
    parseClassInitialization(depth, state, withoutEnd) {
        return this.first(withoutEnd, "=", (s, s2) => {
            return this.parseDefinition(state, s).map((result) => {
                let valueTuple = this.parseValue(result.left(), s2, depth);
                return [valueTuple[0], new ClassInitialization(depth, result.right(), valueTuple[1])];
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
            }).or(() => this.assembleDefinition(state, Lists.empty(), Lists.empty(), name, Lists.empty(), beforeName));
        });
    }
    getOr(state, name, beforeType, type, annotations) {
        return this.suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
            return this.first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
                let typeParams = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some([state1, s.strip()]));
                return this.assembleDefinition(typeParams[0], annotations, this.parseModifiers(beforeTypeParams), name, typeParams[1], type);
            });
        }).or(() => {
            return this.assembleDefinition(state, annotations, this.parseModifiers(beforeType), name, Lists.empty(), type);
        });
    }
    parseModifiers(modifiers) {
        return this.divideAll(modifiers.strip(), (state1, c) => this.foldByDelimiter(state1, c, " ")).iterate().map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector());
    }
    toLast(input, separator, folder) {
        let divisions = this.divideAll(input, folder);
        return divisions.removeLast().map((removed) => {
            let left = removed.left().iterate().collect(new Joiner(separator)).orElse("");
            let right = removed.right();
            return [left, right];
        });
    }
    foldTypeSeparator(state, c) {
        if (c === " " && state.isLevel()) {
            return state.advance();
        }
        let appended = state.append(c);
        if (c === /*  ' */  /* ' */) {
            return appended.enter();
        }
        if (c === ">") {
            return appended.exit();
        }
        return appended;
    }
    assembleDefinition(state, annotations, modifiers, rawName, typeParams, type) {
        return this.parseType(state.withTypeParams(typeParams), type).flatMap((type1) => {
            let stripped = rawName.strip();
            if (!isSymbol(stripped)) {
                return new None();
            }
            let newModifiers = modifiers.iterate().filter((value) => !this.isAccessor(value)).map((modifier) =>  /* modifier.equals("final") ? "readonly" : modifier */).collect(new ListCollector());
            let node = new Definition(annotations, newModifiers, stripped, type1.right(), typeParams);
            return new Some([type1.left(), node]);
        });
    }
    isAccessor(value) {
        return value === "private";
    }
    foldValueChar(state, c) {
        if (c === "," && state.isLevel()) {
            return state.advance();
        }
        let appended = state.append(c);
        if (c === /*  ' */ - /* ' */) {
            let peeked = appended.peek();
            if (peeked === ">") {
                return appended.popAndAppendToOption().orElse(appended);
            }
            else {
                return appended;
            }
        }
        if (c === /*  ' */  /* '  */ || c === "(" || c === "{") {
            return appended.enter();
        }
        if (c === ">" || c === ")" || c === "}") {
            return appended.exit();
        }
        return appended;
    }
    parseType(state, input) {
        let stripped = input.strip();
        if (stripped === "int" || stripped === "Integer") {
            return new Some([state, Primitive.Int]);
        }
        if (stripped === "String" || stripped === "char" || stripped === "Character") {
            return new Some([state, Primitive.String]);
        }
        if (stripped === "var") {
            return new Some([state, Primitive.Unknown]);
        }
        if (stripped === "boolean") {
            return new Some([state, Primitive.Boolean]);
        }
        if (stripped === "void") {
            return new Some([state, Primitive.Void]);
        }
        if (isSymbol(stripped)) {
            return new Some([state, new SymbolType(stripped)]);
        }
        return this.parseTemplate(state, input).or(() => this.varArgs(state, input));
    }
    varArgs(state, input) {
        return this.suffix(input, "...", (s) => {
            return this.parseType(state, s).map((inner) => {
                let newState = inner.left();
                let child = inner.right();
                return [newState, new ArrayType(child)];
            });
        });
    }
    assembleTemplate(base, state, arguments) {
        let children = arguments.iterate().map(this.retainType).flatMap(Queries.fromOption).collect(new ListCollector());
        if (base === "BiFunction") {
            return [state, new FunctionType(Lists.of(children.get(0).orElse(null), children.get(1).orElse(null)), children.get(2).orElse(null))];
        }
        if (base === "Function") {
            return [state, new FunctionType(Lists.of(children.get(0).orElse(null)), children.get(1).orElse(null))];
        }
        if (base === "Predicate") {
            return [state, new FunctionType(Lists.of(children.get(0).orElse(null)), Primitive.Boolean)];
        }
        if (base === "Supplier") {
            return [state, new FunctionType(Lists.empty(), children.get(0).orElse(null))];
        }
        if (base === "Consumer") {
            return [state, new FunctionType(Lists.of(children.get(0).orElse(null)), Primitive.Void)];
        }
        if (base === "Tuple2" && children.size() >= 2) {
            return [state, new TupleType(children)];
        }
        if (state.resolveType(base)._UnknownVariant === UnknownVariant.Some) {
            let baseType = some.value;
            if (baseType._UnknownVariant === UnknownVariant.) {
                let some = state.resolveType(base);
                let findableType = baseType;
                return [state, new Template(findableType, children)];
            }
        }
        return [state, new Template(new ObjectRefType(base), children)];
    }
    parseTemplate(state, input) {
        return this.suffix(input.strip(), ">", (withoutEnd) => {
            return this.first(withoutEnd, "<", (base, argumentsString) => {
                let strippedBase = base.strip();
                return this.parseValues(state, argumentsString, this.parseArgument).map((argumentsTuple) => {
                    return this.assembleTemplate(strippedBase, argumentsTuple.left(), argumentsTuple.right());
                });
            });
        });
    }
    retainType(argument) {
        if (argument._Variant === Variant.) {
            let type = argument;
            return new Some(type);
        }
        else {
            return new None();
        }
    }
    parseArgument(state, input) {
        if (Strings.isBlank(input)) {
            return new Some([state, new Whitespace()]);
        }
        return this.parseType(state, input).map((tuple) => [tuple.left(), tuple.right()]);
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
        return splitter().flatMap((splitTuple) => splitMapper(splitTuple.left(), splitTuple.right()));
    }
    infix(input, infix, locator, mapper) {
        return this.split(() => locator(input, infix).map((index) => {
            let left = input.substring(0, index);
            let right = input.substring(index + Strings.length(infix));
            return [left, right];
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
Main.isDebugEnabled = true;
/*  */ 
