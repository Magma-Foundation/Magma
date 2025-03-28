package magma.api.io;

import magma.api.collect.Set_;
import magma.api.collect.Stream;
import magma.api.option.Option;
import magma.api.result.Result;

public interface Path_ {
    Option<IOError> createDirectoriesSafe();

    Result<Set_<Path_>, IOError> walk();

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
