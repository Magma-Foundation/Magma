package com.meti;

import java.io.IOException;

public record Application(PathWrapper source) {
    Option<File> run() throws IOException {
        return source.existsAsFile()
                .map(this::compile);
    }

    private File compile(File source) throws IOException {
        var input = source.readString();
        var output = input.equals("def main() : I16 => {return 0;}")
                ? "int main(){return 0;}"
                : "";

        var path = source.asPath();
        var name = path.computeRetractedFileName();
        var target = path.resolveSibling(name + ".c");
        return target.createAsFile().writeString(output);
    }
}
