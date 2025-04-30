package magma;

import java.util.function.Function;
import java.util.function.Supplier;

interface CompileState {
    Main.Result<Main.Value, Main.CompileError> usingTypeStack(Main.Type found, Supplier<Main.Result<Main.Value, Main.CompileError>> supplier);

    Main.Result<Main.List<Main.String_>, Main.CompileError> withinStatements(Supplier<Main.Result<Main.List<Main.String_>, Main.CompileError>> action);

    <T> T withinTypeParams(Main.List<Main.String_> typeParams, Main.List<Main.Type> typeArgs, Supplier<T> supplier);

    <T> Main.Result<T, Main.CompileError> withinFrame(Supplier<Main.Result<T, Main.CompileError>> supplier);

    Main.Option<Main.Tuple<Main.String_, Main.Option<Main.Type>>> lookupTypeParam(Main.String_ typeParamValue);

    Main.Option<Main.Type> findTypeByName(String name);

    Main.Option<Main.StructPrototype> getCurrentRef();

    Main.Option<Main.StructType> getCurrentStructType();

    Main.String_ joinFunctions();

    Main.String_ joinStructures();

    @Deprecated
    void defineFunction(Main.Definition definition, Main.List<Main.Definition> oldParameters);

    @Deprecated
    void define(Main.Definition name1);

    Main.String_ createName(String type);

    @Deprecated
    void withFunctionPrototype(Main.Definition definition);

    @Deprecated
    void defineAll(Main.List<Main.Definition> params);

    @Deprecated
    void withRef(Main.StructPrototype prototype);

    @Deprecated
    void expandGeneric(Main.Generic generic);

    @Deprecated
    void addExpanding(Main.String_ name, Function<Main.List<Main.Type>, Main.Result<Main.Whitespace, Main.CompileError>> listResultFunction);

    @Deprecated
    void registerStruct(Main.StructType type, Main.StructNode node);

    @Deprecated
    void addFunction(Main.String_ function);

    @Deprecated
    void addStatement(Main.String_ statement);

    Main.Option<Main.List<Main.String_>> findLastStatements();

    Main.Option<Main.StructType> findCurrentStructType();

    Main.Iterator<Main.Definition> iterateDefinitions();

    Main.Option<Main.StructType> findStructType(Main.Generic generic);

    Main.Iterator<Main.Generic> stream();

    Main.Option<Main.Type> findLastType();
}
