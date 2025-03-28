package magma.api.io;

import magma.api.collect.Stream;
import magma.api.option.Option;
import magma.api.result.Result;

import java.util.Set;

public interface Path_ {
    Option<IOError> createDirectoriesSafe();

    Result<Set<Path_>, IOError> walk();

    Option<IOError> writeString(String output);

    boolean isExists();

    Stream<String> stream();

    Path_ resolve(String segment);

    Path_ relativize(Path_ child);

    boolean isRegularFile();

    String asString();

    Option<Path_> findParent();

    Path_ last();

    Result<String, IOError> readString();
}
