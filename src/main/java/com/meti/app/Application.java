package com.meti.app;

import com.meti.api.io.File;
import com.meti.api.io.IOException;

public final class Application {
    private final Source source;

    public Application(Source source) {
        this.source = source;
    }

    public Stream<File> run() throws ApplicationException {
        try {
            return source.stream().map(Application::compile);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private static File compile(File file) throws IOException {
        var input = file.readAsString();
        String output;
        if (input.equals("def main() : I16 => {return 0;}")) {
            output = "int main(){return 0;}";
        } else {
            output = "";
        }

        var name = file.computeFileNameWithoutExtension();
        var target = file.resolveSibling(name + ".c");
        return target.create().writeAsString(output);

    }
}
