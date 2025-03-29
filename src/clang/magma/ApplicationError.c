package magma;class package magma;{package magma;

import magma.error.Error;class import magma.error.Error;{

import magma.error.Error;

public class ApplicationError implements Error {
    private final Error error;

    public ApplicationError(Error error) {
        this.error = error;
    }

    @Override
    public String display() {
        return error.display();
    }
}
class public class ApplicationError implements Error {
    private final Error error;

    public ApplicationError(Error error) {
        this.error = error;
    }

    @Override
    public String display() {
        return error.display();
    }
}{

public class ApplicationError implements Error {
    private final Error error;

    public ApplicationError(Error error) {
        this.error = error;
    }

    @Override
    public String display() {
        return error.display();
    }
}
