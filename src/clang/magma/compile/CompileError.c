package magma.compile;class package magma.compile;{package magma.compile;

import magma.compile.context.Context;class import magma.compile.context.Context;{

import magma.compile.context.Context;
import magma.error.Error;class import magma.error.Error;{
import magma.error.Error;

public class CompileError implements Error {
    private final String message;
    private final Context context;

    public CompileError(String message, Context context) {
        this.message = message;
        this.context = context;
    }

    @Override
    public String display() {
        return message + ": " + context.display();
    }
}
class public class CompileError implements Error {
    private final String message;
    private final Context context;

    public CompileError(String message, Context context) {
        this.message = message;
        this.context = context;
    }

    @Override
    public String display() {
        return message + ": " + context.display();
    }
}{

public class CompileError implements Error {
    private final String message;
    private final Context context;

    public CompileError(String message, Context context) {
        this.message = message;
        this.context = context;
    }

    @Override
    public String display() {
        return message + ": " + context.display();
    }
}
