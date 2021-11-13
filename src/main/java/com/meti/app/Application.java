package com.meti.app;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.io.IOException;

public record Application(Path Path) {
    Option<Path> run() throws ApplicationException {
        if (Path.exists()) {
            var input = readSource();
            var output = new Compiler(input).compile();
            var target = Path.extendWith("c");
            return writeTarget(output, new NIOPath(target));
        } else {
            return new None<>();
        }
    }

    private String readSource() throws ApplicationException {
        try {
            return Path.readAsString();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private Option<Path> writeTarget(String output, Path Path) throws ApplicationException {
        try {
            Path.writeAsString(output);
            return new Some<>(Path);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

}
