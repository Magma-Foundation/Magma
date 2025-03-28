package magma;

import magma.app.Application;

public class Main {
    public static void main(String[] args) {
        Application.run().ifPresent(error -> System.err.println(error.display()));
    }
}
