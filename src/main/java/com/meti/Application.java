package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;

public final class Application {
    private final PathGateway sourceGateway;
    private final PathGateway targetGateway;

    public Application(PathGateway sourceGateway, PathGateway targetGateway) {
        this.sourceGateway = sourceGateway;
        this.targetGateway = targetGateway;
    }

    Result<Optional<Path>> run() {
        var sources = sourceGateway.collectSources().unwrapValue();

        /*
        TODO: simplify using map-reduce
         */
        var targets = new HashSet<Path>();
        for (var source : sources) {
            var result = compile(source);
            if(result.isOk()) {
                targets.add(result.unwrapValue());
            } else {
                return Result.err(result.unwrapErr());
            }
        }

        return Result.ok(targets.stream().findFirst());
    }

    private Result<Path> compile(Source source) {
        try {
            var target = targetGateway.resolveChild(source);
            if (!Files.exists(target)) {
                Files.createFile(target);
            }
            return Result.ok(target);
        } catch (IOException e) {
            return Result.err(e);
        }
    }
}