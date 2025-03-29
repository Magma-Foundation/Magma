package magma.io;

import magma.collect.set.Set_;
import magma.collect.stream.Stream;
import magma.option.Option;
import magma.result.Result;

public interface Path_ {
    boolean exists();

    Result<Set_<Path_>, IOError> walk();

    Option<IOError> writeString(String output);

    Option<IOError> createAsDirectories();

    Path_ resolve(String child);

    Stream<String> stream();

    Path_ relativize(Path_ child);

    boolean isRegularFile();

    String asString();

    Path_ getParent();

    Path_ getFileName();

    Result<String, IOError> readString();
}
