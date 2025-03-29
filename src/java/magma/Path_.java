package magma;

import magma.collect.Set_;
import magma.collect.stream.Stream;

import java.io.IOException;
import java.nio.file.Files;

public interface Path_ {
    default boolean exists() {
        return Files.exists((Paths.toNative(this)));
    }

    Set_<Path_> walk() throws IOException;

    void writeString(String output) throws IOException;

    void createAsDirectories() throws IOException;

    Path_ resolve(String child);

    Stream<String> stream();

    Path_ relativize(Path_ child);

    boolean isRegularFile();

    String asString();

    Path_ getParent();

    Path_ getFileName();

    String readString() throws IOException;
}
