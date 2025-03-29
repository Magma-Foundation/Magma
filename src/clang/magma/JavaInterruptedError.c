package magma;class package magma;{package magma;

import magma.error.Error;class import magma.error.Error;{

import magma.error.Error;
import jvm.result.Results;class import jvm.result.Results;{
import jvm.result.Results;

public record JavaInterruptedError(InterruptedException error) implements Error {
    @Override
    public String display() {
        return Results.printStackTraceToString(error);
    }
}
class public record JavaInterruptedError(InterruptedException error) implements Error {
    @Override
    public String display() {
        return Results.printStackTraceToString(error);
    }
}{

public record JavaInterruptedError(InterruptedException error) implements Error {
    @Override
    public String display() {
        return Results.printStackTraceToString(error);
    }
}
