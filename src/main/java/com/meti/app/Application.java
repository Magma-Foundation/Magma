package com.meti.app;

import com.meti.api.IOException;
import com.meti.api.Path;
import com.meti.java.NIOPaths;

public record Application(Path project) {
    public void run() throws ApplicationException {
        try {
            project.ensureAsFile("{}");
            var content = project.readContentAsString();

            validateContent(content);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private void validateContent(String content) throws ApplicationException {
        if (content.isBlank()) {
            var absoluteRoot = NIOPaths.Root().absolute().toString();
            var absoluteProjectFile = project.absolute().toString();
            var format = "The project build file, 'project.json' " +
                         "exists in the current working directory of %s as %s, " +
                         "but doesn't seem to have any content.";
            throw new ApplicationException(format.formatted(absoluteRoot, absoluteProjectFile));
        }
    }
}
