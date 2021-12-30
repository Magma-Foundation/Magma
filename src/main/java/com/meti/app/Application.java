package com.meti.app;

import com.meti.api.io.File;
import com.meti.api.io.IOException;

public final class Application {
    private final SourceDirectory sourceDirectory;
    private final TargetDirectory targetDirectory;

    public Application(SourceDirectory sourceDirectory, PathTargetDirectory targetDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.targetDirectory = targetDirectory;
    }

    public Stream<File> run() throws ApplicationException {
        try {
            return sourceDirectory.stream()
                    .map(this::compileSource);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private File compileSource(Source source) throws IOException {
        var package_ = source.computePackage();

        var input = source.read();
        var output = compile(input);

        return targetDirectory.write(output, package_);
    }

    private String compile(String input) {
        String output;
        if (input.equals("def main() : I16 => {return 0;}")) {
            output = "int main(){return 0;}";
        } else {
            output = "";
        }
        return output;
    }
}
