package com.meti;

import com.meti.source.Source;
import com.meti.stream.StreamException;

import java.io.IOException;

public class Application {
    private final Source source;

    public Application(Source source) {
        this.source = source;
    }

    void run() throws ApplicationException {
        try {
            source.stream().forEach(this::compileScript);
        } catch (StreamException | IOException e) {
            throw new ApplicationException(e);
        }
    }

    private void compileScript(Script script) throws ApplicationException {
        var name = script.extractName();
        var input = readInput(script);

        var compiler = new Compiler(name);
        var output = compiler.compile(input);
        script.write(output);
    }

    private String readInput(Script script) throws ApplicationException {
        try {
            return script.read();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
