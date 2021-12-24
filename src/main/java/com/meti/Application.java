package com.meti;

import com.meti.compile.MCCompiler;
import com.meti.compile.RootInput;
import com.meti.io.File;
import com.meti.io.PathWrapper;
import com.meti.option.Option;

import java.io.IOException;

public record Application(PathWrapper source) {
    Option<File> run() throws ApplicationException {
        return source.existsAsFile().map(Application::compile);
    }

    private static File compile(File source) throws ApplicationException {
        var input = readSource(source);
        var output = new MCCompiler(new RootInput(input)).compile();

        var path = source.asPath();
        var name = path.computeRetractedFileName();
        var target = path.resolveSibling(name + ".c");
        return writeTarget(output, target);
    }

    private static String readSource(File source) throws ApplicationException {
        try {
            return source.readString();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private static File writeTarget(String output, PathWrapper target) throws ApplicationException {
        try {
            return target.createAsFile().writeString(output);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
