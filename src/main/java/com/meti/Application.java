package com.meti;

public record Application(Path source) {
    void run() throws ApplicationException {
        try {
            if (source.exists()) {
                var name = source.computeFileNameWithoutExtension();
                var target = source.resolveSibling(name + ".c");
                target.create();
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
