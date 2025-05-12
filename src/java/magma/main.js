"use strict";
var __runInitializers = (this && this.__runInitializers) || function (thisArg, initializers, value) {
    var useValue = arguments.length > 2;
    for (var i = 0; i < initializers.length; i++) {
        value = useValue ? initializers[i].call(thisArg, value) : initializers[i].call(thisArg);
    }
    return useValue ? value : void 0;
};
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
toString();
string;
generateWithParams(joinedParameters, string);
string;
createDefinition(paramTypes, (List));
Definition;
maybeBefore();
Option;
name();
string;
type();
Type;
typeParams();
List;
containsAnnotation(annotation, string);
boolean;
var IncompleteClassSegmentVariant;
(function (IncompleteClassSegmentVariant) {
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["ClassDefinition"] = 0] = "ClassDefinition";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["IncompleteClassSegmentWrapper"] = 1] = "IncompleteClassSegmentWrapper";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["MethodPrototype"] = 2] = "MethodPrototype";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["Placeholder"] = 3] = "Placeholder";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["StructurePrototype"] = 4] = "StructurePrototype";
    IncompleteClassSegmentVariant[IncompleteClassSegmentVariant["Whitespace"] = 5] = "Whitespace";
})(IncompleteClassSegmentVariant || (IncompleteClassSegmentVariant = {}));
/* private static final */ let None = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _map_decorators;
    let _isPresent_decorators;
    let _orElse_decorators;
    let _filter_decorators;
    let _orElseGet_decorators;
    let _or_decorators;
    let _flatMap_decorators;
    let _isEmpty_decorators;
    let _and_decorators;
    return _a = class None {
            map(mapper) {
                return new _a();
            }
            isPresent() {
                return false;
            }
            orElse(other) {
                return other;
            }
            filter(predicate) {
                return new _a();
            }
            orElseGet(supplier) {
                return supplier();
            }
            or(other) {
                return other();
            }
            flatMap(mapper) {
                return new _a();
            }
            isEmpty() {
                return true;
            }
            and(other) {
                return new _a();
            }
            constructor() {
                __runInitializers(this, _instanceExtraInitializers);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _map_decorators = [Override];
            _isPresent_decorators = [Override];
            _orElse_decorators = [Override];
            _filter_decorators = [Override];
            _orElseGet_decorators = [Override];
            _or_decorators = [Override];
            _flatMap_decorators = [Override];
            _isEmpty_decorators = [Override];
            _and_decorators = [Override];
            __esDecorate(_a, null, _map_decorators, { kind: "method", name: "map", static: false, private: false, access: { has: obj => "map" in obj, get: obj => obj.map }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _isPresent_decorators, { kind: "method", name: "isPresent", static: false, private: false, access: { has: obj => "isPresent" in obj, get: obj => obj.isPresent }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _orElse_decorators, { kind: "method", name: "orElse", static: false, private: false, access: { has: obj => "orElse" in obj, get: obj => obj.orElse }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _filter_decorators, { kind: "method", name: "filter", static: false, private: false, access: { has: obj => "filter" in obj, get: obj => obj.filter }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _orElseGet_decorators, { kind: "method", name: "orElseGet", static: false, private: false, access: { has: obj => "orElseGet" in obj, get: obj => obj.orElseGet }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _or_decorators, { kind: "method", name: "or", static: false, private: false, access: { has: obj => "or" in obj, get: obj => obj.or }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _flatMap_decorators, { kind: "method", name: "flatMap", static: false, private: false, access: { has: obj => "flatMap" in obj, get: obj => obj.flatMap }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _isEmpty_decorators, { kind: "method", name: "isEmpty", static: false, private: false, access: { has: obj => "isEmpty" in obj, get: obj => obj.isEmpty }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _and_decorators, { kind: "method", name: "and", static: false, private: false, access: { has: obj => "and" in obj, get: obj => obj.and }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ class Tuple2Impl {
    constructor(left, right) {
    }
}
/* private */ let Some = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _map_decorators;
    let _isPresent_decorators;
    let _orElse_decorators;
    let _filter_decorators;
    let _orElseGet_decorators;
    let _or_decorators;
    let _flatMap_decorators;
    let _isEmpty_decorators;
    let _and_decorators;
    return _a = class Some {
            constructor(value) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            map(mapper) {
                return new _a(mapper(this.value));
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
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _map_decorators = [Override];
            _isPresent_decorators = [Override];
            _orElse_decorators = [Override];
            _filter_decorators = [Override];
            _orElseGet_decorators = [Override];
            _or_decorators = [Override];
            _flatMap_decorators = [Override];
            _isEmpty_decorators = [Override];
            _and_decorators = [Override];
            __esDecorate(_a, null, _map_decorators, { kind: "method", name: "map", static: false, private: false, access: { has: obj => "map" in obj, get: obj => obj.map }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _isPresent_decorators, { kind: "method", name: "isPresent", static: false, private: false, access: { has: obj => "isPresent" in obj, get: obj => obj.isPresent }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _orElse_decorators, { kind: "method", name: "orElse", static: false, private: false, access: { has: obj => "orElse" in obj, get: obj => obj.orElse }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _filter_decorators, { kind: "method", name: "filter", static: false, private: false, access: { has: obj => "filter" in obj, get: obj => obj.filter }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _orElseGet_decorators, { kind: "method", name: "orElseGet", static: false, private: false, access: { has: obj => "orElseGet" in obj, get: obj => obj.orElseGet }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _or_decorators, { kind: "method", name: "or", static: false, private: false, access: { has: obj => "or" in obj, get: obj => obj.or }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _flatMap_decorators, { kind: "method", name: "flatMap", static: false, private: false, access: { has: obj => "flatMap" in obj, get: obj => obj.flatMap }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _isEmpty_decorators, { kind: "method", name: "isEmpty", static: false, private: false, access: { has: obj => "isEmpty" in obj, get: obj => obj.isEmpty }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _and_decorators, { kind: "method", name: "and", static: false, private: false, access: { has: obj => "and" in obj, get: obj => obj.and }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ let SingleHead = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _next_decorators;
    return _a = class SingleHead {
            constructor(value) {
                this.value = __runInitializers(this, _instanceExtraInitializers);
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
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _next_decorators = [Override];
            __esDecorate(_a, null, _next_decorators, { kind: "method", name: "next", static: false, private: false, access: { has: obj => "next" in obj, get: obj => obj.next }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ let EmptyHead = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _next_decorators;
    return _a = class EmptyHead {
            next() {
                return new None();
            }
            constructor() {
                __runInitializers(this, _instanceExtraInitializers);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _next_decorators = [Override];
            __esDecorate(_a, null, _next_decorators, { kind: "method", name: "next", static: false, private: false, access: { has: obj => "next" in obj, get: obj => obj.next }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let HeadedIterator = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _fold_decorators;
    let _map_decorators;
    let _collect_decorators;
    let _filter_decorators;
    let _next_decorators;
    let _flatMap_decorators;
    let _zip_decorators;
    return _a = class HeadedIterator {
            constructor(head) {
                __runInitializers(this, _instanceExtraInitializers);
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
                return new _a(() => this.head.next().map(mapper));
            }
            collect(collector) {
                return this.fold(collector.createInitial(), collector.fold);
            }
            filter(predicate) {
                return this.flatMap((element) => {
                    if (predicate(element)) {
                        return new _a(new SingleHead(element));
                    }
                    return new _a(new EmptyHead());
                });
            }
            next() {
                return this.head.next();
            }
            flatMap(f) {
                return new _a(new FlatMapHead(this.head, f));
            }
            zip(other) {
                return new _a(() => _a.this.head.next().and(other.next));
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _fold_decorators = [Override];
            _map_decorators = [Override];
            _collect_decorators = [Override];
            _filter_decorators = [Override];
            _next_decorators = [Override];
            _flatMap_decorators = [Override];
            _zip_decorators = [Override];
            __esDecorate(_a, null, _fold_decorators, { kind: "method", name: "fold", static: false, private: false, access: { has: obj => "fold" in obj, get: obj => obj.fold }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _map_decorators, { kind: "method", name: "map", static: false, private: false, access: { has: obj => "map" in obj, get: obj => obj.map }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _collect_decorators, { kind: "method", name: "collect", static: false, private: false, access: { has: obj => "collect" in obj, get: obj => obj.collect }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _filter_decorators, { kind: "method", name: "filter", static: false, private: false, access: { has: obj => "filter" in obj, get: obj => obj.filter }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _next_decorators, { kind: "method", name: "next", static: false, private: false, access: { has: obj => "next" in obj, get: obj => obj.next }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _flatMap_decorators, { kind: "method", name: "flatMap", static: false, private: false, access: { has: obj => "flatMap" in obj, get: obj => obj.flatMap }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _zip_decorators, { kind: "method", name: "zip", static: false, private: false, access: { has: obj => "zip" in obj, get: obj => obj.zip }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ let RangeHead = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _next_decorators;
    return _a = class RangeHead /*  */ {
            constructor(length) {
                this.length = __runInitializers(this, _instanceExtraInitializers);
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
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _next_decorators = [Override];
            __esDecorate(_a, null, _next_decorators, { kind: "method", name: "next", static: false, private: false, access: { has: obj => "next" in obj, get: obj => obj.next }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ class Lists /*  */ {
}
/* private */ let ImmutableDefinition = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _mapType_decorators;
    let _generateWithParams_decorators;
    let _createDefinition_decorators;
    let _containsAnnotation_decorators;
    return _a = class ImmutableDefinition /*  */ {
            constructor(annotations, maybeBefore, name, type, typeParams) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            createSimpleDefinition(name, type) {
                return new _a(Lists.empty(), new None(), name, type, Lists.empty());
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
                return !.maybeBefore.filter((value) => !value.isEmpty()).map(Main.generatePlaceholder).map((inner) => inner + " ").orElse("");
            }
            joinTypeParams() {
                return this.typeParams.iterate().collect(new Joiner()).map((inner) => "<" + inner + ">").orElse("");
            }
            mapType(mapper) {
                return new _a(this.annotations, this.maybeBefore, this.name, mapper(this.type), this.typeParams);
            }
            generateWithParams(joinedParameters) {
                let joinedAnnotations = this.annotations.iterate().map((value) => "@" + value + " ").collect(new Joiner()).orElse("");
                let joined = this.joinTypeParams();
                let before = this.joinBefore();
                let typeString = this.generateType();
                return joinedAnnotations + before + this.name + joined + joinedParameters + typeString;
            }
            createDefinition(paramTypes) {
                let type1 = new FunctionType(paramTypes, this.type);
                return new _a(this.annotations, this.maybeBefore, this.name, type1, this.typeParams);
            }
            containsAnnotation(annotation) {
                return this.annotations.contains(annotation);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _mapType_decorators = [Override];
            _generateWithParams_decorators = [Override];
            _createDefinition_decorators = [Override];
            _containsAnnotation_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _mapType_decorators, { kind: "method", name: "mapType", static: false, private: false, access: { has: obj => "mapType" in obj, get: obj => obj.mapType }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _generateWithParams_decorators, { kind: "method", name: "generateWithParams", static: false, private: false, access: { has: obj => "generateWithParams" in obj, get: obj => obj.generateWithParams }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _createDefinition_decorators, { kind: "method", name: "createDefinition", static: false, private: false, access: { has: obj => "createDefinition" in obj, get: obj => obj.createDefinition }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _containsAnnotation_decorators, { kind: "method", name: "containsAnnotation", static: false, private: false, access: { has: obj => "containsAnnotation" in obj, get: obj => obj.containsAnnotation }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let ObjectType = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _replace_decorators;
    let _find_decorators;
    let _findName_decorators;
    return _a = class ObjectType /*  */ {
            constructor(name, typeParams, definitions) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return this.name;
            }
            replace(mapping) {
                return new _a(this.name, this.typeParams, this.definitions.iterate().map((definition) => definition.mapType((type) => type.replace(mapping))).collect(new ListCollector()));
            }
            find(name) {
                return this.definitions.iterate().filter((definition) => definition.name().equals(name)).map(Definition.type).next();
            }
            findName() {
                return new Some(this.name);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _replace_decorators = [Override];
            _find_decorators = [Override];
            _findName_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _replace_decorators, { kind: "method", name: "replace", static: false, private: false, access: { has: obj => "replace" in obj, get: obj => obj.replace }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _find_decorators, { kind: "method", name: "find", static: false, private: false, access: { has: obj => "find" in obj, get: obj => obj.find }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _findName_decorators, { kind: "method", name: "findName", static: false, private: false, access: { has: obj => "findName" in obj, get: obj => obj.findName }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let TypeParam = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _replace_decorators;
    let _findName_decorators;
    return _a = class TypeParam /*  */ {
            constructor(value) {
                __runInitializers(this, _instanceExtraInitializers);
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
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _replace_decorators = [Override];
            _findName_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _replace_decorators, { kind: "method", name: "replace", static: false, private: false, access: { has: obj => "replace" in obj, get: obj => obj.replace }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _findName_decorators, { kind: "method", name: "findName", static: false, private: false, access: { has: obj => "findName" in obj, get: obj => obj.findName }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ class CompileState /*  */ {
    constructor(structures, definitions, objectTypes, structNames, typeParams, typeRegister, functionSegments) {
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
        if ( /* maybeTypeParam instanceof Some */( /* var value */)) {
            return new Some(new TypeParam( /* value */));
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
}
this.segments = segments;
this.buffer = buffer;
this.depth = depth;
this.input = input;
this.index = index;
DivideState(input, string);
{
    /* this(input, 0, Lists.empty(), "", 0) */ ;
}
advance();
DivideState;
{
    this.segments = this.segments.addLast(this.buffer);
    this.buffer = "";
    return this;
}
append(c, string);
DivideState;
{
    this.buffer = this.buffer + c;
    return this;
}
enter();
DivideState;
{
    /* this.depth++ */ ;
    return this;
}
isLevel();
boolean;
{
    return this.depth === 0;
}
exit();
DivideState;
{
    /* this.depth-- */ ;
    return this;
}
isShallow();
boolean;
{
    return this.depth === 1;
}
pop();
Option < [string, DivideState] > {
    : .index < this.input.length()
};
{
    let c = this.input.charAt(this.index);
    return new Some(new Tuple2Impl(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
}
return new None();
popAndAppendToTuple();
Option < [string, DivideState] > {
    return: this.pop().map((tuple) => {
        let c = tuple[0]();
        let right = tuple[1]();
        return new Tuple2Impl(c, right.append(c));
    })
};
popAndAppendToOption();
Option < DivideState > {
    return: this.popAndAppendToTuple().map(Tuple2.right)
};
peek();
string;
{
    return this.input.charAt(this.index);
}
/* private */ let Joiner = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _createInitial_decorators;
    let _fold_decorators;
    return _a = class Joiner /*  */ {
            constructor(delimiter) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            createInitial() {
                return new None();
            }
            fold(current, element) {
                return new Some(current.map((inner) => inner + this.delimiter + element).orElse(element));
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _createInitial_decorators = [Override];
            _fold_decorators = [Override];
            __esDecorate(_a, null, _createInitial_decorators, { kind: "method", name: "createInitial", static: false, private: false, access: { has: obj => "createInitial" in obj, get: obj => obj.createInitial }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _fold_decorators, { kind: "method", name: "fold", static: false, private: false, access: { has: obj => "fold" in obj, get: obj => obj.fold }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ let ListCollector = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _createInitial_decorators;
    let _fold_decorators;
    return _a = class ListCollector {
            createInitial() {
                return Lists.empty();
            }
            fold(current, element) {
                return current.addLast(element);
            }
            constructor() {
                __runInitializers(this, _instanceExtraInitializers);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _createInitial_decorators = [Override];
            _fold_decorators = [Override];
            __esDecorate(_a, null, _createInitial_decorators, { kind: "method", name: "createInitial", static: false, private: false, access: { has: obj => "createInitial" in obj, get: obj => obj.createInitial }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _fold_decorators, { kind: "method", name: "fold", static: false, private: false, access: { has: obj => "fold" in obj, get: obj => obj.fold }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ class FlatMapHead {
}
this.mapper = mapper;
this.current = new None();
this.head = head;
next();
Option < R > {
    while() {
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
};
/* private */ let ArrayType = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _replace_decorators;
    let _findName_decorators;
    return _a = class ArrayType /*  */ {
            constructor(right) {
                __runInitializers(this, _instanceExtraInitializers);
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
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _replace_decorators = [Override];
            _findName_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _replace_decorators, { kind: "method", name: "replace", static: false, private: false, access: { has: obj => "replace" in obj, get: obj => obj.replace }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _findName_decorators, { kind: "method", name: "findName", static: false, private: false, access: { has: obj => "findName" in obj, get: obj => obj.findName }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static final */ let Whitespace = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _maybeCreateDefinition_decorators;
    return _a = class Whitespace /*  */ {
            generate() {
                return "";
            }
            maybeCreateDefinition() {
                return new None();
            }
            constructor() {
                __runInitializers(this, _instanceExtraInitializers);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _maybeCreateDefinition_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _maybeCreateDefinition_decorators, { kind: "method", name: "maybeCreateDefinition", static: false, private: false, access: { has: obj => "maybeCreateDefinition" in obj, get: obj => obj.maybeCreateDefinition }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ class Iterators /*  */ {
    fromOption(option) {
        let single = option.map(SingleHead.new);
        return new HeadedIterator(single.orElseGet(EmptyHead.new));
    }
}
/* private */ let FunctionType = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _replace_decorators;
    let _findName_decorators;
    return _a = class FunctionType /*  */ {
            constructor(arguments, returns) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                let joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left() + " : " + pair.right().generate()).collect(new Joiner(", ")).orElse("");
                return "(" + joined + ") => " + this.returns.generate();
            }
            replace(mapping) {
                return new _a(this.arguments.iterate().map((type) => type.replace(mapping)).collect(new ListCollector()), this.returns);
            }
            findName() {
                return new None();
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _replace_decorators = [Override];
            _findName_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _replace_decorators, { kind: "method", name: "replace", static: false, private: false, access: { has: obj => "replace" in obj, get: obj => obj.replace }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _findName_decorators, { kind: "method", name: "findName", static: false, private: false, access: { has: obj => "findName" in obj, get: obj => obj.findName }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let TupleType = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _replace_decorators;
    let _findName_decorators;
    return _a = class TupleType /*  */ {
            constructor(arguments) {
                __runInitializers(this, _instanceExtraInitializers);
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
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _replace_decorators = [Override];
            _findName_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _replace_decorators, { kind: "method", name: "replace", static: false, private: false, access: { has: obj => "replace" in obj, get: obj => obj.replace }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _findName_decorators, { kind: "method", name: "findName", static: false, private: false, access: { has: obj => "findName" in obj, get: obj => obj.findName }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Template = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _typeParams_decorators;
    let _find_decorators;
    let _name_decorators;
    let _replace_decorators;
    let _findName_decorators;
    return _a = class Template /*  */ {
            constructor(base, arguments) {
                __runInitializers(this, _instanceExtraInitializers);
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
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _typeParams_decorators = [Override];
            _find_decorators = [Override];
            _name_decorators = [Override];
            _replace_decorators = [Override];
            _findName_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _typeParams_decorators, { kind: "method", name: "typeParams", static: false, private: false, access: { has: obj => "typeParams" in obj, get: obj => obj.typeParams }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _find_decorators, { kind: "method", name: "find", static: false, private: false, access: { has: obj => "find" in obj, get: obj => obj.find }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _name_decorators, { kind: "method", name: "name", static: false, private: false, access: { has: obj => "name" in obj, get: obj => obj.name }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _replace_decorators, { kind: "method", name: "replace", static: false, private: false, access: { has: obj => "replace" in obj, get: obj => obj.replace }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _findName_decorators, { kind: "method", name: "findName", static: false, private: false, access: { has: obj => "findName" in obj, get: obj => obj.findName }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Placeholder = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _type_decorators;
    let _typeParams_decorators;
    let _find_decorators;
    let _name_decorators;
    let _replace_decorators;
    let _findName_decorators;
    let _maybeCreateDefinition_decorators;
    return _a = class Placeholder /*  */ {
            constructor(input) {
                __runInitializers(this, _instanceExtraInitializers);
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
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _type_decorators = [Override];
            _typeParams_decorators = [Override];
            _find_decorators = [Override];
            _name_decorators = [Override];
            _replace_decorators = [Override];
            _findName_decorators = [Override];
            _maybeCreateDefinition_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _type_decorators, { kind: "method", name: "type", static: false, private: false, access: { has: obj => "type" in obj, get: obj => obj.type }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _typeParams_decorators, { kind: "method", name: "typeParams", static: false, private: false, access: { has: obj => "typeParams" in obj, get: obj => obj.typeParams }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _find_decorators, { kind: "method", name: "find", static: false, private: false, access: { has: obj => "find" in obj, get: obj => obj.find }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _name_decorators, { kind: "method", name: "name", static: false, private: false, access: { has: obj => "name" in obj, get: obj => obj.name }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _replace_decorators, { kind: "method", name: "replace", static: false, private: false, access: { has: obj => "replace" in obj, get: obj => obj.replace }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _findName_decorators, { kind: "method", name: "findName", static: false, private: false, access: { has: obj => "findName" in obj, get: obj => obj.findName }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _maybeCreateDefinition_decorators, { kind: "method", name: "maybeCreateDefinition", static: false, private: false, access: { has: obj => "maybeCreateDefinition" in obj, get: obj => obj.maybeCreateDefinition }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let StringValue = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _type_decorators;
    return _a = class StringValue /*  */ {
            constructor(stripped) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return this.stripped;
            }
            type() {
                return Primitive.Unknown;
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _type_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _type_decorators, { kind: "method", name: "type", static: false, private: false, access: { has: obj => "type" in obj, get: obj => obj.type }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let DataAccess = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _type_decorators;
    return _a = class DataAccess /*  */ {
            constructor(parent, property, type) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return this.parent.generate() + "." + this.property + createDebugString(this.type);
            }
            type() {
                return this.type;
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _type_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _type_decorators, { kind: "method", name: "type", static: false, private: false, access: { has: obj => "type" in obj, get: obj => obj.type }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let ConstructionCaller = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class ConstructionCaller /*  */ {
            constructor(type) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return "new " + this.type.generate();
            }
            toFunction() {
                return new FunctionType(Lists.empty(), this.type);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Operation = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _type_decorators;
    return _a = class Operation /*  */ {
            constructor(left, operator /* Operator */, right) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return this.left().generate() + " " + this.operator.targetRepresentation + " " + this.right().generate();
            }
            type() {
                return Primitive.Unknown;
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _type_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _type_decorators, { kind: "method", name: "type", static: false, private: false, access: { has: obj => "type" in obj, get: obj => obj.type }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Not = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _type_decorators;
    return _a = class Not /*  */ {
            constructor(value) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return "!" + this.value.generate();
            }
            type() {
                return Primitive.Unknown;
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _type_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _type_decorators, { kind: "method", name: "type", static: false, private: false, access: { has: obj => "type" in obj, get: obj => obj.type }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let BlockLambdaValue = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class BlockLambdaValue /*  */ {
            constructor(depth, statements) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return "{" + this.joinStatements() + createIndent(this.depth) + "}";
            }
            joinStatements() {
                return this.statements.iterate().map(FunctionSegment.generate).collect(new Joiner()).orElse("");
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Lambda = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _type_decorators;
    return _a = class Lambda /*  */ {
            constructor(parameters, body) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                let joined = this.parameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
                return "(" + joined + ") => " + this.body.generate();
            }
            type() {
                return Primitive.Unknown;
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _type_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _type_decorators, { kind: "method", name: "type", static: false, private: false, access: { has: obj => "type" in obj, get: obj => obj.type }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Invokable = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class Invokable /*  */ {
            constructor(caller, arguments, type) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                let joined = this.arguments.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
                return this.caller.generate() + "(" + joined + ")" + createDebugString(this.type);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let IndexValue = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _type_decorators;
    return _a = class IndexValue /*  */ {
            constructor(parent, child) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return this.parent.generate() + "[" + this.child.generate() + "]";
            }
            type() {
                return Primitive.Unknown;
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _type_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _type_decorators, { kind: "method", name: "type", static: false, private: false, access: { has: obj => "type" in obj, get: obj => obj.type }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let SymbolValue = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class SymbolValue /*  */ {
            constructor(stripped, type) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return this.stripped + createDebugString(this.type);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let JVMMap = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _find_decorators;
    let _with_decorators;
    return _a = class JVMMap {
            constructor(map) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            find(key) {
                if (this.map.containsKey(key)) {
                    return new Some(this.map.get(key));
                }
                return new None();
            }
            with(key, value) {
                /* this.map.put(key, value) */ ;
                return this;
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _find_decorators = [Override];
            _with_decorators = [Override];
            __esDecorate(_a, null, _find_decorators, { kind: "method", name: "find", static: false, private: false, access: { has: obj => "find" in obj, get: obj => obj.find }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _with_decorators, { kind: "method", name: "with", static: false, private: false, access: { has: obj => "with" in obj, get: obj => obj.with }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ class Maps /*  */ {
    empty() {
        return new JVMMap();
    }
}
/* private */ let MapCollector = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _createInitial_decorators;
    let _fold_decorators;
    return _a = class MapCollector {
            createInitial() {
                return Maps.empty();
            }
            fold(current, element) {
                return current.with(element[0](), element[1]());
            }
            constructor() {
                __runInitializers(this, _instanceExtraInitializers);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _createInitial_decorators = [Override];
            _fold_decorators = [Override];
            __esDecorate(_a, null, _createInitial_decorators, { kind: "method", name: "createInitial", static: false, private: false, access: { has: obj => "createInitial" in obj, get: obj => obj.createInitial }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _fold_decorators, { kind: "method", name: "fold", static: false, private: false, access: { has: obj => "fold" in obj, get: obj => obj.fold }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ let ConstructorHeader = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _createDefinition_decorators;
    let _generateWithParams_decorators;
    return _a = class ConstructorHeader /*  */ {
            createDefinition(paramTypes) {
                return ImmutableDefinition.createSimpleDefinition("new", Primitive.Unknown);
            }
            generateWithParams(joinedParameters) {
                return "constructor " + joinedParameters;
            }
            constructor() {
                __runInitializers(this, _instanceExtraInitializers);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _createDefinition_decorators = [Override];
            _generateWithParams_decorators = [Override];
            __esDecorate(_a, null, _createDefinition_decorators, { kind: "method", name: "createDefinition", static: false, private: false, access: { has: obj => "createDefinition" in obj, get: obj => obj.createDefinition }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _generateWithParams_decorators, { kind: "method", name: "generateWithParams", static: false, private: false, access: { has: obj => "generateWithParams" in obj, get: obj => obj.generateWithParams }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ class Method /*  */ {
}
this.depth = depth;
this.header = header;
this.parameters = parameters;
this.statements = maybeStatements;
joinStatements(statements, (List));
string;
{
    return statements.iterate().map(FunctionSegment.generate).collect(new Joiner()).orElse("");
}
generate();
string;
{
    let indent = createIndent(this.depth);
    let generatedHeader = this.header.generateWithParams(joinValues(this.parameters));
    let generatedStatements = this.statements.map(Method.joinStatements).map((inner) => " {" + inner + indent + "}").orElse(";");
    return indent + generatedHeader + generatedStatements;
}
/* private */ let Block = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class Block /*  */ {
            constructor(depth, header, statements) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                let indent = createIndent(this.depth);
                let collect = this.statements.iterate().map(FunctionSegment.generate).collect(new Joiner()).orElse("");
                return indent + this.header.generate() + "{" + collect + indent + "}";
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Conditional = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class Conditional /*  */ {
            constructor(prefix, value1) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return this.prefix + " (" + this.value1.generate() + ")";
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private static */ let Else = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class Else /*  */ {
            generate() {
                return "else ";
            }
            constructor() {
                __runInitializers(this, _instanceExtraInitializers);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Return = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class Return /*  */ {
            constructor(value) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return "return " + this.value.generate();
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Initialization = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class Initialization /*  */ {
            constructor(definition, source) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return "let " + this.definition.generate() + " = " + this.source.generate();
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Assignment = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class Assignment /*  */ {
            constructor(destination, source) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return this.destination.generate() + " = " + this.source.generate();
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Statement = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class Statement /*  */ {
            constructor(depth, value) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return createIndent(this.depth) + this.value.generate() + ";";
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let MethodPrototype = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _maybeCreateDefinition_decorators;
    return _a = class MethodPrototype /*  */ {
            constructor(depth, header, parameters, content) {
                __runInitializers(this, _instanceExtraInitializers);
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
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _maybeCreateDefinition_decorators = [Override];
            __esDecorate(_a, null, _maybeCreateDefinition_decorators, { kind: "method", name: "maybeCreateDefinition", static: false, private: false, access: { has: obj => "maybeCreateDefinition" in obj, get: obj => obj.maybeCreateDefinition }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let IncompleteClassSegmentWrapper = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _maybeCreateDefinition_decorators;
    return _a = class IncompleteClassSegmentWrapper /*  */ {
            constructor(segment) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            maybeCreateDefinition() {
                return new None();
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _maybeCreateDefinition_decorators = [Override];
            __esDecorate(_a, null, _maybeCreateDefinition_decorators, { kind: "method", name: "maybeCreateDefinition", static: false, private: false, access: { has: obj => "maybeCreateDefinition" in obj, get: obj => obj.maybeCreateDefinition }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let ClassDefinition = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _maybeCreateDefinition_decorators;
    return _a = class ClassDefinition /*  */ {
            constructor(definition, depth) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            maybeCreateDefinition() {
                return new Some(this.definition);
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _maybeCreateDefinition_decorators = [Override];
            __esDecorate(_a, null, _maybeCreateDefinition_decorators, { kind: "method", name: "maybeCreateDefinition", static: false, private: false, access: { has: obj => "maybeCreateDefinition" in obj, get: obj => obj.maybeCreateDefinition }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let StructurePrototype = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _maybeCreateDefinition_decorators;
    return _a = class StructurePrototype /*  */ {
            constructor(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            createObjectType() {
                let definitionFromSegments = this.segments.iterate().map(IncompleteClassSegment.maybeCreateDefinition).flatMap(Iterators.fromOption).collect(new ListCollector());
                return new ObjectType(this.name, this.typeParams, definitionFromSegments.addAllLast(this.parameters));
            }
            maybeCreateDefinition() {
                return new None();
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _maybeCreateDefinition_decorators = [Override];
            __esDecorate(_a, null, _maybeCreateDefinition_decorators, { kind: "method", name: "maybeCreateDefinition", static: false, private: false, access: { has: obj => "maybeCreateDefinition" in obj, get: obj => obj.maybeCreateDefinition }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Cast = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    return _a = class Cast /*  */ {
            constructor(value, type) {
                __runInitializers(this, _instanceExtraInitializers);
            }
            generate() {
                return this.value.generate() + " as " + this.type.generate();
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ let Primitive = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let __decorators;
    let __initializers = [];
    let __extraInitializers = [];
    let __decorators_1;
    let __initializers_1 = [];
    let __extraInitializers_1 = [];
    let __decorators_2;
    let __initializers_2 = [];
    let __extraInitializers_2 = [];
    let _generate_decorators;
    let _replace_decorators;
    let _findName_decorators;
    return _a = class Primitive /*  */ {
            constructor(value) {
                this. = (__runInitializers(this, _instanceExtraInitializers), __runInitializers(this, __initializers, void 0));
                this. = (__runInitializers(this, __extraInitializers), __runInitializers(this, __initializers_1, void 0));
                this. = (__runInitializers(this, __extraInitializers_1), __runInitializers(this, __initializers_2, void 0));
                this.value = __runInitializers(this, __extraInitializers_2);
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
            _generate_decorators = [Override];
            _replace_decorators = [Override];
            _findName_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _replace_decorators, { kind: "method", name: "replace", static: false, private: false, access: { has: obj => "replace" in obj, get: obj => obj.replace }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _findName_decorators, { kind: "method", name: "findName", static: false, private: false, access: { has: obj => "findName" in obj, get: obj => obj.findName }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(null, null, __decorators, { kind: "field", name: "", static: false, private: false, access: { has: obj => "" in obj, get: obj => obj., set: (obj, value) => { obj. = value; } }, metadata: _metadata }, __initializers, __extraInitializers);
            __esDecorate(null, null, __decorators_1, { kind: "field", name: "", static: false, private: false, access: { has: obj => "" in obj, get: obj => obj., set: (obj, value) => { obj. = value; } }, metadata: _metadata }, __initializers_1, __extraInitializers_1);
            __esDecorate(null, null, __decorators_2, { kind: "field", name: "", static: false, private: false, access: { has: obj => "" in obj, get: obj => obj., set: (obj, value) => { obj. = value; } }, metadata: _metadata }, __initializers_2, __extraInitializers_2);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* private */ class Operator /*  */ {
    constructor(sourceRepresentation, targetRepresentation) {
        this.sourceRepresentation = sourceRepresentation;
        this.targetRepresentation = targetRepresentation;
    }
}
/* private */ let BooleanValue = (() => {
    var _a;
    let _instanceExtraInitializers = [];
    let _generate_decorators;
    let _type_decorators;
    return _a = class BooleanValue /*  */ {
            constructor(value) {
                this.value = __runInitializers(this, _instanceExtraInitializers);
                this.value = value;
            }
            generate() {
                return this.value;
            }
            type() {
                return Primitive.Boolean;
            }
        },
        (() => {
            const _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _generate_decorators = [Override];
            _type_decorators = [Override];
            __esDecorate(_a, null, _generate_decorators, { kind: "method", name: "generate", static: false, private: false, access: { has: obj => "generate" in obj, get: obj => obj.generate }, metadata: _metadata }, null, _instanceExtraInitializers);
            __esDecorate(_a, null, _type_decorators, { kind: "method", name: "type", static: false, private: false, access: { has: obj => "type" in obj, get: obj => obj.type }, metadata: _metadata }, null, _instanceExtraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
})();
/* public */ class Main /*  */ {
    main() {
        let parent = /* Paths */ .get(".", "src", "java", "magma");
        let source = parent.resolve("Main.java");
        let target = parent.resolve("main.ts");
        let input = /* Files */ .readString(source);
        /* Files.writeString(target, compile(input)) */ ;
        /* new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                .inheritIO()
                .start()
                .waitFor() */ ;
    }
}
/* catch (IOException | InterruptedException e) */ {
    /* throw new RuntimeException(e) */ ;
}
compile(input, string);
string;
{
    let state = new CompileState();
    let parsed = parseStatements(state, input, Main.compileRootSegment);
    let joined = parsed[0]().structures.iterate().collect(new Joiner()).orElse("");
    return joined + generateStatements(parsed[1]());
}
generateStatements(statements, (List));
string;
{
    return generateAll(Main.mergeStatements, statements);
}
parseStatements(state, CompileState, input, string, mapper, (arg0, arg1) => [CompileState, T]);
[CompileState, (List)];
{
    return parseAllWithIndices(state, input, Main.foldStatementChar, (state3, tuple) => new Some(mapper(state3, tuple.right()))).orElseGet(() => new Tuple2Impl(state, Lists.empty()));
}
generateAll(merger, (arg0, arg1) => string, elements, (List));
string;
{
    return elements.iterate().fold("", merger);
}
parseAllWithIndices(state, CompileState, input, string, folder, (arg0, arg1) => DivideState, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    let, stringList: (List) = divideAll(input, folder),
    return: mapUsingState(state, stringList, mapper)
};
mapUsingState(state, CompileState, elements, (List), mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    let, initial: (Option) = new Some(new Tuple2Impl(state, Lists.empty())),
    return: elements.iterateWithIndices().fold(initial, (tuple, element) => {
        return tuple.flatMap((inner) => {
            let state1 = inner.left();
            let right = inner.right();
            return mapper(state1, element).map((applied) => {
                return new Tuple2Impl(applied[0](), right.addLast(applied[1]()));
            });
        });
    })
};
mergeStatements(cache, string, statement, string);
string;
{
    return cache + statement;
}
divideAll(input, string, folder, (arg0, arg1) => DivideState);
List < string > {
    let, current: DivideState = new DivideState(input),
    while() {
        let maybePopped = current.pop().map((tuple) => {
            return foldSingleQuotes(tuple).or(() => foldDoubleQuotes(tuple)).orElseGet(() => folder(tuple[1](), tuple[0]()));
        });
        if (maybePopped.isPresent()) {
            current = maybePopped.orElse(current);
        }
        else {
            /* break */ ;
        }
    },
    return: current.advance().segments
};
foldDoubleQuotes(tuple, [string, DivideState]);
Option < DivideState > {
    if(tuple, [], ) { }
}() === ; /*  '\"' */
{
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
foldSingleQuotes(tuple, [string, DivideState]);
Option < DivideState > {
    if( /* tuple.left() != '\'' */) {
        return new None();
    },
    let, appended = tuple[1]().append(tuple[0]()),
    return: appended.popAndAppendToTuple().map(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption)
};
foldEscaped(escaped, [string, DivideState]);
DivideState;
{
    if (escaped[0]() ===  /*  '\\' */) {
        return escaped[1]().popAndAppendToOption().orElse(escaped[1]());
    }
    return escaped[1]();
}
foldStatementChar(state, DivideState, c, string);
DivideState;
{
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
compileRootSegment(state, CompileState, input, string);
[CompileState, string];
{
    let stripped = input.strip();
    if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
        return new Tuple2Impl(state, "");
    }
    return parseClass(stripped, state).flatMap((tuple) => completeClassSegment(tuple[0](), tuple[1]())).map((tuple0) => new Tuple2Impl(tuple0.left(), tuple0.right().generate())).orElseGet(() => new Tuple2Impl(state, generatePlaceholder(stripped)));
}
parseClass(stripped, string, state, CompileState);
Option < [CompileState, IncompleteClassSegment] > {
    return: parseStructure(stripped, "class ", "class ", state)
};
parseStructure(stripped, string, sourceInfix, string, targetInfix, string, state, CompileState);
Option < [CompileState, IncompleteClassSegment] > {
    return: first(stripped, sourceInfix, (beforeInfix, right) => {
        return first(right, "{", (beforeContent, withEnd) => {
            return suffix(withEnd.strip(), "}", (content1) => {
                return last(beforeInfix.strip(), "\n", (annotationsString, s2) => {
                    let annotations = parseAnnotations(annotationsString);
                    return parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, annotations);
                }).or(() => {
                    return parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
                });
            });
        });
    })
};
parseAnnotations(annotationsString, string);
List < string > {
    return: divideAll(annotationsString.strip(), Main.foldByDelimiter).iterate().map() /* String */.strip, : .filter((value) => !value.isEmpty()).map((value) => value.substring(1)).map() /* String */.strip, : .filter((value) => !value.isEmpty()).collect(new ListCollector())
};
foldByDelimiter(state1, DivideState, c, string);
DivideState;
{
    if (c ===  /*  '\n' */) {
        return state1.advance();
    }
    return state1.append(c);
}
parseStructureWithMaybePermits(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, annotations, (List));
Option < [CompileState, IncompleteClassSegment] > {
    return: last(beforeContent, " permits ", (s, s2) => {
        let variants = divideAll(s2, Main.foldValueChar).iterate().map() /* String */.strip;
    }).filter((value) => !value.isEmpty()).collect(new ListCollector()),
    return: parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, s, content1, variants, annotations)
};
or(() => {
    return parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), annotations);
});
parseStructureWithMaybeImplements(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, variants, (List), annotations, (List));
Option < [CompileState, IncompleteClassSegment] > {
    return: first(beforeContent, " implements ", (s, s2) => {
        return parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, s, content1, variants, annotations);
    }).or(() => {
        return parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations);
    })
};
parseStructureWithMaybeExtends(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, variants, (List), annotations, (List));
Option < [CompileState, IncompleteClassSegment] > {
    return: first(beforeContent, " extends ", (s, s2) => {
        return parseStructureWithMaybeParams(targetInfix, state, beforeInfix, s, content1, variants, annotations);
    }).or(() => {
        return parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations);
    })
};
parseStructureWithMaybeParams(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, variants, (List), annotations, (List));
Option < [CompileState, IncompleteClassSegment] > {
    return: suffix(beforeContent.strip(), ")", (s) => {
        return first(s, "(", (s1, s2) => {
            let parsed = parseParameters(state, s2);
            return parseStructureWithMaybeTypeParams(targetInfix, parsed[0](), beforeInfix, s1, content1, parsed[1](), variants, annotations);
        });
    }).or(() => {
        return parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), variants, annotations);
    })
};
parseStructureWithMaybeTypeParams(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, params, (List), variants, (List), annotations, (List));
Option < [CompileState, IncompleteClassSegment] > {
    return: first(beforeContent, "<", (name, withTypeParams) => {
        return first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
            let mapper = (state1, s) => new Tuple2Impl(state1, s.strip());
            let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(mapper(state1, s)));
            return assembleStructure(typeParams[0](), targetInfix, annotations, beforeInfix, name, content1, typeParams[1](), afterTypeParams, params, variants);
        });
    }).or(() => {
        return assembleStructure(state, targetInfix, annotations, beforeInfix, beforeContent, content1, Lists.empty(), "", params, variants);
    })
};
assembleStructure(state, CompileState, targetInfix, string, annotations, (List), beforeInfix, string, rawName, string, content, string, typeParams, (List), after, string, rawParameters, (List), variants, (List));
Option < [CompileState, IncompleteClassSegment] > {
    let, name = rawName.strip(),
    if(, isSymbol) { }
}(name);
{
    return new None();
}
if (annotations.contains("Actual")) {
    return new Some(new Tuple2Impl(state, new Whitespace()));
}
let segmentsTuple = parseStatements(state.pushStructName(name).withTypeParams(typeParams), content, (state0, input) => parseClassSegment(state0, input, 1));
let segmentsState = segmentsTuple[0]();
let segments = segmentsTuple[1]();
let parameters = retainDefinitions(rawParameters);
let prototype = new StructurePrototype(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants);
return new Some(new Tuple2Impl(segmentsState.addType(prototype.createObjectType()), prototype));
completeStructure(state, CompileState, prototype, StructurePrototype);
Option < [CompileState, ClassSegment] > {
    let, thisType: ObjectType = prototype.createObjectType(),
    let, state2: CompileState = state.enterDefinitions().define(ImmutableDefinition.createSimpleDefinition("this", thisType)),
    return: mapUsingState(state2, prototype.segments(), (state1, entry) => completeClassSegment(state1, entry.right())).map((completedTuple) => {
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
        let withMaybeConstructor = atttachConstructor(prototype);
        let parsed2 = withMaybeConstructor.iterate().map(ClassSegment.generate).collect(new Joiner()).orElse("");
        let joinedTypeParams = prototype.typeParams().iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        let generated = generatePlaceholder(prototype.beforeInfix().strip()) + prototype.targetInfix() + prototype.name() + joinedTypeParams + generatePlaceholder(prototype.after()) + " {" + parsed2 + "\n}\n";
        let compileState = /* withEnum */ .popStructName();
        let definedState = compileState.addStructure(generated);
        return new Tuple2Impl(definedState, new Whitespace());
    })
};
atttachConstructor(prototype, StructurePrototype, segments, (List));
List < ClassSegment > {
    if(prototype) { }, : .parameters().isEmpty()
};
{
    segments;
}
{
    segments.addFirst(new Method(1, new ConstructorHeader(), prototype.parameters(), new Some(Lists.empty())));
}
return /* withMaybeConstructor */;
completeClassSegment(state1, CompileState, segment, IncompleteClassSegment);
Option < [CompileState, ClassSegment] > {
/* return switch (segment) */ };
/* return switch (segment) */ {
    /* case IncompleteClassSegmentWrapper wrapper -> new Some<>(new Tuple2Impl<>(state1, wrapper.segment)) */ ;
    /* case MethodPrototype methodPrototype -> completeMethod(state1, methodPrototype) */ ;
    /* case Whitespace whitespace -> new Some<>(new Tuple2Impl<>(state1, whitespace)) */ ;
    /* case Placeholder placeholder -> new Some<>(new Tuple2Impl<>(state1, placeholder)) */ ;
    /* case ClassDefinition classDefinition -> completeDefinition(state1, classDefinition) */ ;
    /* case StructurePrototype structurePrototype -> completeStructure(state1, structurePrototype) */ ;
}
/*  */ ;
completeDefinition(state1, CompileState, classDefinition, ClassDefinition);
Option < [CompileState, ClassSegment] > {
    let, definition: StatementValue = classDefinition.definition,
    let, statement: Statement = new Statement(classDefinition.depth, definition),
    return: new Some(new Tuple2Impl(state1, statement))
};
retainDefinition(parameter, Parameter);
Option < Definition > {
    if(parameter) { }, : ._variant === ParameterVariant.Definition
};
{
    let definition = parameter;
    return new Some(definition);
}
return new None();
isSymbol(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) */ {
        let c = input.charAt( /* i */);
        if ( /* Character */.isLetter(c) || /*  */ ( /* i != 0  */ && /* Character */ .isDigit(c))) {
            /* continue */ ;
        }
        return false;
    }
    return true;
}
prefix(input, string, prefix, string, mapper, (arg0) => Option);
Option < T > {
    if(, input) { }, : .startsWith(prefix)
};
{
    return new None();
}
let slice = input.substring(prefix.length());
return mapper(slice);
suffix(input, string, suffix, string, mapper, (arg0) => Option);
Option < T > {
    if(, input) { }, : .endsWith(suffix)
};
{
    return new None();
}
let slice = input.substring(0, input.length() - suffix.length());
return mapper(slice);
parseClassSegment(state, CompileState, input, string, depth, number);
[CompileState, IncompleteClassSegment];
{
    return Main. < /* Whitespace, IncompleteClassSegment>typed */ (() => parseWhitespace(input, state)).or(() => typed(() => parseClass(input, state))).or(() => typed(() => parseStructure(input, "interface ", "interface ", state))).or(() => typed(() => parseStructure(input, "record ", "class ", state))).or(() => typed(() => parseStructure(input, "enum ", "class ", state))).or(() => parseMethod(state, input, depth)).or(() => typed(() => parseDefinitionStatement(input, depth, state))).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
}
typed(action, () => Option);
Option < [CompileState, S] > {
    return: action().map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]()))
};
parseWhitespace(input, string, state, CompileState);
Option < [CompileState, Whitespace] > {
    if(input) { }, : .isBlank()
};
{
    return new Some(new Tuple2Impl(state, new Whitespace()));
}
return new None();
parseMethod(state, CompileState, input, string, depth, number);
Option < [CompileState, IncompleteClassSegment] > {
    return: first(input, "(", (definitionString, withParams) => {
        return first(withParams, ")", (parametersString, rawContent) => {
            return parseDefinition(state, definitionString). < Tuple2 < /* CompileState, Header>>map */ ((tuple) => new Tuple2Impl(tuple.left(), tuple.right())).or(() => parseConstructor(state, definitionString)).flatMap((definitionTuple) => assembleMethod(depth, parametersString, rawContent, definitionTuple));
        });
    })
};
assembleMethod(depth, number, parametersString, string, rawContent, string, definitionTuple, [CompileState, Header]);
Option < [CompileState, IncompleteClassSegment] > {
    let, definitionState = definitionTuple[0](),
    let, header = definitionTuple[1](),
    let, parametersTuple: [CompileState, (List)] = parseParameters(definitionState, parametersString),
    let, rawParameters = parametersTuple[1](),
    let, parameters: (List) = retainDefinitions(rawParameters),
    let, prototype: MethodPrototype = new MethodPrototype(depth, header, parameters, rawContent.strip()),
    return: new Some(new Tuple2Impl(parametersTuple[0]().define(prototype.createDefinition()), prototype))
};
completeMethod(state, CompileState, prototype, MethodPrototype);
Option < [CompileState, ClassSegment] > {
    let, definition: Definition = prototype.createDefinition(),
    if(prototype) { }, : .content().equals(";") || definition.containsAnnotation("Actual")
};
{
    return new Some(new Tuple2Impl(state.define(definition), new Method(prototype.depth(), prototype.header(), prototype.parameters(), new None())));
}
if (prototype.content().startsWith("{") && prototype.content().endsWith("}")) {
    let substring = prototype.content().substring(1, prototype.content().length() - 1);
    let withDefined = state.enterDefinitions().defineAll(prototype.parameters());
    let statementsTuple = parseStatements(withDefined, substring, (state1, input1) => parseFunctionSegment(state1, input1, prototype.depth() + 1));
    let statements = statementsTuple[1]();
    return new Some(new Tuple2Impl(statementsTuple[0]().exitDefinitions().define(definition), new Method(prototype.depth(), prototype.header(), prototype.parameters(), new Some(statements))));
}
return new None();
parseConstructor(state, CompileState, input, string);
Option < [CompileState, Header] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .equals(state.structNames.last().orElse(""))
};
{
    return new Some(new Tuple2Impl(state, new ConstructorHeader()));
}
return new None();
joinValues(retainParameters, (List));
string;
{
    let inner = retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
    return "(" + inner + ")";
}
retainDefinitions(right, (List));
List < Definition > {
    return: right.iterate().map(Main.retainDefinition).flatMap(Iterators.fromOption).collect(new ListCollector())
};
parseParameters(state, CompileState, params, string);
[CompileState, (List)];
{
    return parseValuesOrEmpty(state, params, (state1, s) => new Some(parseParameter(state1, s)));
}
parseFunctionSegments(state, CompileState, input, string, depth, number);
[CompileState, (List)];
{
    return parseStatements(state, input, (state1, input1) => parseFunctionSegment(state1, input1, depth + 1));
}
parseFunctionSegment(state, CompileState, input, string, depth, number);
[CompileState, FunctionSegment];
{
    let stripped = input.strip();
    if (stripped.isEmpty()) {
        return new Tuple2Impl(state, new Whitespace());
    }
    return parseFunctionStatement(state, depth, stripped).or(() => parseBlock(state, depth, stripped)).orElseGet(() => new Tuple2Impl(state, new Placeholder(stripped)));
}
parseFunctionStatement(state, CompileState, depth, number, stripped, string);
Option < [CompileState, FunctionSegment] > {
    return: suffix(stripped, ";", (s) => {
        let tuple = parseStatementValue(state, s, depth);
        let left = tuple[0]();
        let right = tuple[1]();
        return new Some(new Tuple2Impl(left, new Statement(depth, right)));
    })
};
parseBlock(state, CompileState, depth, number, stripped, string);
Option < [CompileState, FunctionSegment] > {
    return: suffix(stripped, "}", (withoutEnd) => {
        return split(() => toFirst(withoutEnd), (beforeContent, content) => {
            return suffix(beforeContent, "{", (headerString) => {
                let headerTuple = parseBlockHeader(state, headerString, depth);
                let headerState = headerTuple[0]();
                let header = headerTuple[1]();
                let statementsTuple = parseFunctionSegments(headerState, content, depth);
                let statementsState = statementsTuple[0]();
                let statements = statementsTuple[1]().addAllFirst(statementsState.functionSegments);
                return new Some(new Tuple2Impl(statementsState.clearFunctionSegments(), new Block(depth, header, statements)));
            });
        });
    })
};
toFirst(input, string);
Option < [string, string] > {
    let, divisions: (List) = divideAll(input, Main.foldBlockStart),
    return: divisions.removeFirst().map((removed) => {
        let right = removed[0]();
        let left = removed[1]().iterate().collect(new Joiner("")).orElse("");
        return new Tuple2Impl(right, left);
    })
};
parseBlockHeader(state, CompileState, input, string, depth, number);
[CompileState, BlockHeader];
{
    let stripped = input.strip();
    return parseConditional(state, stripped, "if", depth).or(() => parseConditional(state, stripped, "while", depth)).or(() => parseElse(state, input)).orElseGet(() => new Tuple2Impl(state, new Placeholder(stripped)));
}
parseElse(state, CompileState, input, string);
Option < [CompileState, BlockHeader] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .equals("else")
};
{
    return new Some(new Tuple2Impl(state, new Else()));
}
return new None();
parseConditional(state, CompileState, input, string, prefix, string, depth, number);
Option < [CompileState, BlockHeader] > {
    return: prefix(input, prefix, (withoutPrefix) => {
        return prefix(withoutPrefix.strip(), "(", (withoutValueStart) => {
            return suffix(withoutValueStart, ")", (value) => {
                let valueTuple = parseValue(state, value, depth);
                let value1 = valueTuple[1]();
                return new Some(new Tuple2Impl(valueTuple[0](), new Conditional(prefix, value1)));
            });
        });
    })
};
foldBlockStart(state, DivideState, c, string);
DivideState;
{
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
parseStatementValue(state, CompileState, input, string, depth, number);
[CompileState, StatementValue];
{
    let stripped = input.strip();
    if (stripped.startsWith("return ")) {
        let value = stripped.substring("return ".length());
        let tuple = parseValue(state, value, depth);
        let value1 = tuple[1]();
        return new Tuple2Impl(tuple[0](), new Return(value1));
    }
    return parseAssignment(state, depth, stripped).orElseGet(() => {
        return new Tuple2Impl(state, new Placeholder(stripped));
    });
}
parseAssignment(state, CompileState, depth, number, stripped, string);
Option < [CompileState, StatementValue] > {
    return: first(stripped, "=", (beforeEquals, valueString) => {
        let sourceTuple = parseValue(state, valueString, depth);
        let sourceState = sourceTuple[0]();
        let source = sourceTuple[1]();
        return parseDefinition(sourceState, beforeEquals).flatMap((definitionTuple) => parseInitialization(definitionTuple[0](), definitionTuple[1](), source)).or(() => parseAssignment(depth, beforeEquals, sourceState, source));
    })
};
parseAssignment(depth, number, beforeEquals, string, sourceState, CompileState, source, Value);
Option < [CompileState, StatementValue] > {
    let, destinationTuple: [CompileState, Value] = parseValue(sourceState, beforeEquals, depth),
    let, destinationState = destinationTuple[0](),
    let, destination = destinationTuple[1](),
    return: new Some(new Tuple2Impl(destinationState, new Assignment(destination, source)))
};
parseInitialization(state, CompileState, rawDefinition, Definition, source, Value);
Option < [CompileState, StatementValue] > {
    let, definition: Definition = rawDefinition.mapType((type) => {
        if (type.equals(Primitive.Unknown)) {
            return source.type();
        }
        else {
            return type;
        }
    }),
    return: new Some(new Tuple2Impl(state.define(definition), new Initialization(definition, source)))
};
parseValue(state, CompileState, input, string, depth, number);
[CompileState, Value];
{
    return parseBoolean(state, input).or(() => parseLambda(state, input, depth)).or(() => parseString(state, input)).or(() => parseDataAccess(state, input, depth)).or(() => parseSymbolValue(state, input)).or(() => parseInvokable(state, input, depth)).or(() => parseDigits(state, input)).or(() => parseInstanceOf(state, input, depth)).or(() => parseOperation(state, input, depth, Operator.ADD)).or(() => parseOperation(state, input, depth, Operator.EQUALS)).or(() => parseOperation(state, input, depth, Operator.SUBTRACT)).or(() => parseOperation(state, input, depth, Operator.AND)).or(() => parseOperation(state, input, depth, Operator.OR)).or(() => parseOperation(state, input, depth)).or(() => parseOperation(state, input, depth)).or(() => parseNot(state, input, depth)).or(() => parseMethodReference(state, input, depth)).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
}
parseBoolean(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .equals("false")
};
{
    return new Some(new Tuple2Impl(state, BooleanValue.False));
}
if (stripped.equals("true")) {
    return new Some(new Tuple2Impl(state, BooleanValue.True));
}
return new None();
parseInstanceOf(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last(input, "instanceof", (s, s2) => {
        let childTuple = parseValue(state, s, depth);
        return parseDefinition(childTuple[0](), s2).map((definitionTuple) => {
            let value = childTuple[1]();
            let definition = definitionTuple[1]();
            let variant = new DataAccess(value, "_variant", Primitive.Unknown);
            let type = value.type();
            let generate = type.findName().orElse("");
            let temp = new SymbolValue(generate + "Variant." + definition.type().findName().orElse(""), Primitive.Unknown);
            let functionSegment = new Statement(depth + 1, new Initialization(definition, new Cast(value, definition.type())));
            return new Tuple2Impl(definitionTuple[0]().addFunctionSegment(functionSegment).define(definition), new Operation(variant, Operator.EQUALS, temp));
        });
    })
};
parseMethodReference(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last(input, "::", (s, s2) => {
        let tuple = parseValue(state, s, depth);
        return new Some(new Tuple2Impl(tuple[0](), new DataAccess(tuple[1](), s2, Primitive.Unknown)));
    })
};
parseNot(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .startsWith("!")
};
{
    let slice = stripped.substring(1);
    let tuple = parseValue(state, slice, depth);
    let value = tuple[1]();
    return new Some(new Tuple2Impl(tuple[0](), new Not(value)));
}
return new None();
parseLambda(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: first(input, "->", (beforeArrow, valueString) => {
        let strippedBeforeArrow = beforeArrow.strip();
        if (isSymbol(strippedBeforeArrow)) {
            let type = Primitive.Unknown;
            if ( /* state.typeRegister instanceof Some */( /* var expectedType */)) {
                if ( /* expectedType */._variant === Variant.FunctionType) {
                    let functionType = /* expectedType */ as, FunctionType;
                    type = functionType.arguments.get(0).orElse( /* null */);
                }
            }
            return assembleLambda(state, Lists.of(ImmutableDefinition.createSimpleDefinition(strippedBeforeArrow, type)), valueString, depth);
        }
        if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")) {
            let parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main.foldValueChar).iterate().map() /* String */.strip;
        }
    }).filter((value) => !value.isEmpty()).map((name) => ImmutableDefinition.createSimpleDefinition(name, Primitive.Unknown)).collect(new ListCollector()),
    return: assembleLambda(state, parameterNames, valueString, depth)
};
return new None();
;
assembleLambda(state, CompileState, definitions, (List), valueString, string, depth, number);
Some < [CompileState, Value] > {
    let, strippedValueString = valueString.strip(),
    let, state2: CompileState = state.defineAll(definitions),
    if(strippedValueString) { }, : .startsWith("{") && strippedValueString.endsWith("}")
};
{
    let value1 = parseStatements(state2, strippedValueString.substring(1, strippedValueString.length() - 1), (state1, input1) => parseFunctionSegment(state1, input1, depth + 1));
    let right = value1[1]();
    new Tuple2Impl(value1[0](), new BlockLambdaValue(depth, right));
}
{
    let value1 = parseValue(state2, strippedValueString, depth);
    new Tuple2Impl(value1[0](), value1[1]());
}
let right = /* value */ .right();
return new Some(new Tuple2Impl(left(), new Lambda(definitions, right)));
parseDigits(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input.strip(),
    if(isNumber) { }
}(stripped);
{
    return new Some(new Tuple2Impl(state, new SymbolValue(stripped, Primitive.Int)));
}
return new None();
isNumber(input, string);
boolean;
{
    /* String maybeTruncated */ ;
    if (input.startsWith("-")) {
        input.substring(1);
    }
    else {
        input;
    }
    return areAllDigits( /* maybeTruncated */);
}
areAllDigits(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) */ {
        let c = input.charAt( /* i */);
        if ( /* Character */.isDigit(c)) {
            /* continue */ ;
        }
        return false;
    }
    return true;
}
parseInvokable(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: suffix(input.strip(), ")", (withoutEnd) => {
        return split(() => toLast(withoutEnd, "", Main.foldInvocationStart), (callerWithEnd, argumentsString) => {
            return suffix(callerWithEnd, "(", (callerString) => {
                return assembleInvokable(state, depth, argumentsString, callerString.strip());
            });
        });
    })
};
assembleInvokable(state, CompileState, depth, number, argumentsString, string, callerString, string);
Some < [CompileState, Value] > {
    let, callerTuple: [CompileState, Caller] = invocationHeader(state, depth, callerString),
    let, oldCallerState = callerTuple[0](),
    let, oldCaller = callerTuple[1](),
    let, newCaller: Caller = modifyCaller(oldCallerState, oldCaller),
    let, callerType: FunctionType = findCallerType(newCaller),
    let, argumentsTuple: T = parseValuesWithIndices(oldCallerState, argumentsString, (currentState, pair) => {
        let index = pair.left();
        let element = pair.right();
        let expectedType = callerType.arguments.get(index).orElse(Primitive.Unknown);
        let withExpected = currentState.withExpectedType(expectedType);
        let valueTuple = parseArgument(withExpected, element, depth);
        let valueState = valueTuple[0]();
        let value = valueTuple[1]();
        let actualType = valueTuple[0]().typeRegister.orElse(Primitive.Unknown);
        return new Some(new Tuple2Impl(valueState, new Tuple2Impl(value, actualType)));
    }).orElseGet(() => new Tuple2Impl(oldCallerState, Lists.empty())),
    let, argumentsState = argumentsTuple.left(),
    let, argumentsWithActualTypes = argumentsTuple.right(),
    let, arguments = argumentsWithActualTypes.iterate().map(Tuple2.left).map(Main.retainValue).flatMap(Iterators.fromOption).collect(new ListCollector()),
    let, invokable: Invokable = new Invokable(newCaller, arguments, callerType.returns),
    return: new Some(new Tuple2Impl(argumentsState, invokable))
};
retainValue(argument, Argument);
Option < Value > {
    if(argument) { }, : ._variant === ArgumentVariant.Value
};
{
    let value = argument;
    return new Some(value);
}
return new None();
parseArgument(state, CompileState, element, string, depth, number);
[CompileState, Argument];
{
    if (element.isEmpty()) {
        return new Tuple2Impl(state, new Whitespace());
    }
    let tuple = parseValue(state, element, depth);
    return new Tuple2Impl(tuple[0](), tuple[1]());
}
findCallerType(newCaller, Caller);
FunctionType;
{
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
modifyCaller(state, CompileState, oldCaller, Caller);
Caller;
{
    if (oldCaller._variant === CallerVariant.DataAccess) {
        let type = resolveType(access.parent, state);
        if ( /* type instanceof FunctionType */) {
            let access = oldCaller;
            return access.parent;
        }
    }
    return oldCaller;
}
resolveType(value, Value, state, CompileState);
Type;
{
    return value.type();
}
invocationHeader(state, CompileState, depth, number, callerString1, string);
[CompileState, Caller];
{
    if (callerString1.startsWith("new ")) {
        let input1 = callerString1.substring("new ".length());
        let map = parseType(state, input1).map((type) => {
            let right = type[1]();
            return new Tuple2Impl(type[0](), new ConstructionCaller(right));
        });
        if (map.isPresent()) {
            return map.orElse( /* null */);
        }
    }
    let tuple = parseValue(state, callerString1, depth);
    return new Tuple2Impl(tuple[0](), tuple[1]());
}
foldInvocationStart(state, DivideState, c, string);
DivideState;
{
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
parseDataAccess(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last(input.strip(), ".", (parentString, rawProperty) => {
        let property = rawProperty.strip();
        if (!isSymbol(property)) {
            return new None();
        }
        let tuple = parseValue(state, parentString, depth);
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
    })
};
parseString(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .startsWith("\"") && stripped.endsWith("\"")
};
{
    return new Some(new Tuple2Impl(state, new StringValue(stripped)));
}
return new None();
parseSymbolValue(state, CompileState, value, string);
Option < [CompileState, Value] > {
    let, stripped = value.strip(),
    if(isSymbol) { }
}(stripped);
{
    if ( /* state.resolveValue(stripped) instanceof Some */( /* var type */)) {
        return new Some(new Tuple2Impl(state, new SymbolValue(stripped, type)));
    }
    if ( /* state.resolveType(stripped) instanceof Some */( /* var type */)) {
        return new Some(new Tuple2Impl(state, new SymbolValue(stripped, type)));
    }
    return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
}
return new None();
parseOperation(state, CompileState, value, string, depth, number, operator);
Option < [CompileState, Value] > {
    return: first(value, operator.sourceRepresentation, (leftString, rightString) => {
        let leftTuple = parseValue(state, leftString, depth);
        let rightTuple = parseValue(leftTuple[0](), rightString, depth);
        let left = leftTuple[1]();
        let right = rightTuple[1]();
        return new Some(new Tuple2Impl(rightTuple[0](), new Operation(left, operator, right)));
    })
};
parseValuesOrEmpty(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
[CompileState, (List)];
{
    return parseValues(state, input, mapper).orElseGet(() => new Tuple2Impl(state, Lists.empty()));
}
parseValues(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return: parseValuesWithIndices(state, input, (state1, tuple) => mapper(state1, tuple.right()))
};
parseValuesWithIndices(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return: parseAllWithIndices(state, input, Main.foldValueChar, mapper)
};
parseParameter(state, CompileState, input, string);
[CompileState, Parameter];
{
    if (input.isBlank()) {
        return new Tuple2Impl(state, new Whitespace());
    }
    return parseDefinition(state, input).map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]())).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
}
createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat(depth);
}
parseDefinitionStatement(input, string, depth, number, state, CompileState);
Option < [CompileState, IncompleteClassSegment] > {
    return: suffix(input.strip(), ";", (withoutEnd) => {
        return parseDefinition(state, withoutEnd).map((result) => {
            let definition = result[1]();
            return new Tuple2Impl(result[0](), new ClassDefinition(definition, depth));
        });
    })
};
parseDefinition(state, CompileState, input, string);
Option < [CompileState, Definition] > {
    return: last(input.strip(), " ", (beforeName, name) => {
        return split(() => toLast(beforeName, " ", Main.foldTypeSeparator), (beforeType, type) => {
            return last(beforeType, "\n", (s, s2) => {
                let annotations = parseAnnotations(s);
                return getOr(state, name, s2, type, annotations);
            }).or(() => {
                return getOr(state, name, beforeType, type, Lists.empty());
            });
        }).or(() => assembleDefinition(state, Lists.empty(), new None(), name, Lists.empty(), beforeName));
    })
};
getOr(state, CompileState, name, string, beforeType, string, type, string, annotations, (List));
Option < [CompileState, Definition] > {
    return: suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
        return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
            let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(new Tuple2Impl(state1, s.strip())));
            return assembleDefinition(typeParams[0](), annotations, new Some(beforeTypeParams), name, typeParams[1](), type);
        });
    }).or(() => {
        return assembleDefinition(state, annotations, new Some(beforeType), name, Lists.empty(), type);
    })
};
toLast(input, string, separator, string, folder, (arg0, arg1) => DivideState);
Option < [string, string] > {
    let, divisions: (List) = divideAll(input, folder),
    return: divisions.removeLast().map((removed) => {
        let left = removed[0]().iterate().collect(new Joiner(separator)).orElse("");
        let right = removed[1]();
        return new Tuple2Impl(left, right);
    })
};
foldTypeSeparator(state, DivideState, c, string);
DivideState;
{
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
assembleDefinition(state, CompileState, annotations, (List), beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
Option < [CompileState, Definition] > {
    return: parseType(state.withTypeParams(typeParams), type).map((type1) => {
        let node = new ImmutableDefinition(annotations, beforeTypeParams, name.strip(), type1[1](), typeParams);
        return new Tuple2Impl(type1[0](), node);
    })
};
foldValueChar(state, DivideState, c, string);
DivideState;
{
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
parseType(state, CompileState, input, string);
Option < [CompileState, Type] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .equals("int") || stripped.equals("Integer")
};
{
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
if (isSymbol(stripped)) {
    if ( /* state.resolveType(stripped) instanceof Some */( /* var resolved */)) {
        return new Some(new Tuple2Impl(state));
    }
    else {
        return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
    }
}
return parseTemplate(state, input).or(() => varArgs(state, input));
varArgs(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix(input, "...", (s) => {
        return parseType(state, s).map((inner) => {
            let newState = inner[0]();
            let child = inner[1]();
            return new Tuple2Impl(newState, new ArrayType(child));
        });
    })
};
assembleTemplate(base, string, state, CompileState, arguments, (List));
[CompileState, Type];
{
    let children = arguments.iterate().map(Main.retainType).flatMap(Iterators.fromOption).collect(new ListCollector());
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
parseTemplate(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix(input.strip(), ">", (withoutEnd) => {
        return first(withoutEnd, "<", (base, argumentsString) => {
            let strippedBase = base.strip();
            return parseValues(state, argumentsString, Main.argument).map((argumentsTuple) => {
                return assembleTemplate(strippedBase, argumentsTuple[0](), argumentsTuple[1]());
            });
        });
    })
};
retainType(argument, Argument);
Option < Type > {
    if(argument) { }, : ._variant === ArgumentVariant.Type
};
{
    let type = argument;
    return new Some(type);
}
{
    return new None();
}
argument(state, CompileState, input, string);
Option < [CompileState, Argument] > {
    if(input) { }, : .isBlank()
};
{
    return new Some(new Tuple2Impl(state, new Whitespace()));
}
return parseType(state, input).map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]()));
last(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix(input, infix, Main.findLast, mapper)
};
findLast(input, string, infix, string);
Option < number > {
    let, index = input.lastIndexOf(infix),
    if(index) { }
} === -1;
{
    return new None();
}
return new Some(index);
first(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix(input, infix, Main.findFirst, mapper)
};
split(splitter, () => Option, splitMapper, (arg0, arg1) => Option);
Option < T > {
    return: splitter().flatMap((splitTuple) => splitMapper(splitTuple[0](), splitTuple[1]()))
};
infix(input, string, infix, string, locator, (arg0, arg1) => Option, mapper, (arg0, arg1) => Option);
Option < T > {
    return: split(() => locator(input, infix).map((index) => {
        let left = input.substring(0, index);
        let right = input.substring(index + infix.length());
        return new Tuple2Impl(left, right);
    }), mapper)
};
findFirst(input, string, infix, string);
Option < number > {
    let, index = input.indexOf(infix),
    if(index) { }
} === -1;
{
    return new None();
}
return new Some(index);
generatePlaceholder(input, string);
string;
{
    let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
    return "/* " + replaced + " */";
}
createDebugString(type, Type);
string;
{
    if (!Main.isDebug) {
        return "";
    }
    return generatePlaceholder(": " + type.generate());
}
/*  */ 
