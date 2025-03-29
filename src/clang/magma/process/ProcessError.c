package magma.process;class package magma.process;{package magma.process;

import magma.error.Error;class import magma.error.Error;{

import magma.error.Error;

public record ProcessError(Error child) implements Error {
    @Override
    public String display() {
        return child.display();
    }
}
class public record ProcessError(Error child) implements Error {
    @Override
    public String display() {
        return child.display();
    }
}{

public record ProcessError(Error child) implements Error {
    @Override
    public String display() {
        return child.display();
    }
}
