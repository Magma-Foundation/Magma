package magmac.app.io;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.collect.list.JVMList;
import magmac.api.iter.Iter;
import magmac.api.result.Err;
import magmac.api.result.Ok;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SafeFiles {
    public static Option<IOException> writeString(Path target, String output) {
        try {
            Files.writeString(target, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }

    public static IOResult<Iter<Path>> walk(Path sourceDirectory) {
        try {
            var stream = Files.walk(sourceDirectory);
            return new InlineIOResult<>(new Ok<>(new JVMList<>(stream.collect(Collectors.toList())).iter()));
        } catch (IOException e) {
            return new InlineIOResult<>(new Err<>(e));
        }
    }

    public static IOResult<String> readString(Path source) {
        try {
            return new InlineIOResult<>(new Ok<>(Files.readString(source)));
        } catch (IOException e) {
            return new InlineIOResult<>(new Err<>(e));
        }
    }

    public static Option<IOException> createDirectories(Path targetParent) {
        try {
            Files.createDirectories(targetParent);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }
}
