package magma.io;

import magma.collect.set.Set_;
import magma.collect.stream.Stream;

import java.io.IOException;

public interface Path_ {
    boolean exists();

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
