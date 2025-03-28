package magma.api.io;

import magma.api.collect.Stream;
import magma.api.option.Option;
import magma.api.result.Result;

import java.io.IOException;
import java.util.Set;

public interface Path_ {
    Set<Path_> walkExceptionally() throws IOException;

    Option<IOError> createDirectoriesSafe();

    Result<Set<Path_>, IOError> walk();

    Option<IOError> writeString(String output);

    boolean isExists();

    Stream<String> stream();

    Path_ resolve(String segment);

    Path_ relativize(Path_ child);

    boolean isRegularFile();

    String asString();

    String readString() throws IOException;

    Option<Path_> getParent();

    Path_ last();
}
