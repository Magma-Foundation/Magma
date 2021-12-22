package com.meti;

import java.io.IOException;

public record Application(PathWrapper source) {
    Option<File> run() throws IOException {
        return source.existsAsFile()
                .map(Application::compile);
    }

    private static File compile(File source) throws IOException {
        var input = source.readString();
        var output = new MCCompiler(input).compile();

        var path = source.asPath();
        var name = path.computeRetractedFileName();
        var target = path.resolveSibling(name + ".c");
        return target.createAsFile().writeString(output);
    }

}
